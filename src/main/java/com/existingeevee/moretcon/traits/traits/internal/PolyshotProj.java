package com.existingeevee.moretcon.traits.traits.internal;

import java.util.UUID;

import com.existingeevee.moretcon.client.actions.BlastClientAction;
import com.existingeevee.moretcon.other.recoil.RecoilHandler;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.capability.projectile.TinkerProjectileHandler;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.events.TinkerProjectileImpactEvent;
import slimeknights.tconstruct.library.tools.ranged.ProjectileCore;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;

public class PolyshotProj extends AbstractProjectileTrait {

	public PolyshotProj() {
		super(MiscUtils.createNonConflictiveName("polyshot_projectile"), 0);
		MinecraftForge.EVENT_BUS.register(this);
		TinkerRegistry.addTrait(this);
	}

	public static final ThreadLocal<Boolean> IS_ALREADY_PROCING = ThreadLocal.withInitial(() -> false);

	@Override
	public void onLaunch(EntityProjectileBase projectileBase, World world, EntityLivingBase shooter) {
		if (IS_ALREADY_PROCING.get()) {
			return;
		}

		if (projectileBase != null && shooter != null) {			
			if (shooter instanceof EntityPlayer && !world.isRemote) {
				RecoilHandler.INSTANCE.recoil((EntityPlayer) shooter, 7);
				BlastClientAction.INSTANCE.run(world, projectileBase.posX, projectileBase.posY, projectileBase.posZ, BlastClientAction.fromVec3d(new Vec3d(projectileBase.motionX, projectileBase.motionY, projectileBase.motionZ).normalize()));
			}
			world.playSound(null, projectileBase.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 2, 2);
			
			
			
			TinkerProjectileHandler ticProjectile = projectileBase.tinkerProjectile;

			float speed = ticProjectile.getPower();

			if (!world.isRemote && ticProjectile.getItemStack().getItem() instanceof ProjectileCore) {
				IS_ALREADY_PROCING.set(true);

				int toShoot = random.nextInt(3) + 5;

				UUID volleyId = UUID.randomUUID();

				modifyProjectile(projectileBase, shooter, speed, volleyId);
				for (int i = 0; i < toShoot - 1; i++) {
					spawnProjectile(projectileBase, shooter, speed, volleyId);
				}
				IS_ALREADY_PROCING.set(false);
			}
		}
	}

	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed) {
		if (LAST_PROJ.get() != null) {
			LAST_PROJ.set(null);
		}
	}

	public void spawnProjectile(EntityProjectileBase projectileBase, EntityLivingBase shooter, float power, UUID volleyId) {
		ItemStack stack = projectileBase.tinkerProjectile.getItemStack();
		ItemStack launcher = projectileBase.tinkerProjectile.getLaunchingStack();

		EntityProjectileBase proj = ((ProjectileCore) stack.getItem()).getProjectile(stack, launcher, shooter.world, shooter instanceof EntityPlayer ? (EntityPlayer) shooter : null, 2.1f, 0f, 1f, false);

		proj.setIsCritical(power >= 1);
		shooter.world.spawnEntity(proj);
		proj.pickupStatus = PickupStatus.CREATIVE_ONLY;
		modifyProjectile(proj, shooter, power, volleyId);
	}

	public void modifyProjectile(EntityProjectileBase projectileBase, EntityLivingBase shooter, float power, UUID volleyId) {
		// Reset projectile motion
		projectileBase.motionX = 0;
		projectileBase.motionY = 0;
		projectileBase.motionZ = 0;

		float velo = power * 3;

		// Reshoot
		projectileBase.shoot(shooter, shooter.rotationPitch, shooter.rotationYaw, 0, velo, 25f);

		NBTTagCompound comp = projectileBase.getEntityData().getCompoundTag(this.getModifierIdentifier());
		comp.setUniqueId(this.getIdentifier() + ".Volley", volleyId);
	}

	@Override
	public void onProjectileUpdate(EntityProjectileBase projectile, World world, ItemStack toolStack) {
		NBTTagCompound comp = projectile.getEntityData().getCompoundTag(this.getModifierIdentifier());

		if (projectile.pickupStatus == PickupStatus.CREATIVE_ONLY) {
			if (projectile.ticksExisted > 20 * 20)
				projectile.setDead();
			if (projectile.inGround || projectile.onGround) {
				int tick = comp.getInteger("TickOnGround");
				if (tick > 60)
					projectile.setDead();

				comp.setInteger("TickOnGround", tick + 1);
			} else {
				comp.setInteger("TickOnGround", 0);
			}

		}

		Vec3d vel = new Vec3d(projectile.motionX, projectile.motionY, projectile.motionZ);

		if (!projectile.inGround)
			comp.setDouble("DistTraveled", comp.getDouble("DistTraveled") + vel.lengthVector());

		projectile.getEntityData().setTag(this.getModifierIdentifier(), comp);
	}

	public static final ThreadLocal<EntityProjectileBase> LAST_PROJ = ThreadLocal.withInitial(() -> null);

	@SubscribeEvent
	public void onTinkerProjectileImpactEvent(TinkerProjectileImpactEvent event) {
		if (event.getEntity() instanceof EntityProjectileBase) {
			ItemStack stack = ((EntityProjectileBase) event.getEntity()).tinkerProjectile.getItemStack();

			if (!this.isToolWithTrait(stack)) {
				return;
			}

			RayTraceResult result = event.getRayTraceResult();
			if (result != null && result.entityHit != null) {
				LAST_PROJ.set((EntityProjectileBase) event.getEntity());

				NBTTagCompound comp = result.entityHit.getEntityData().getCompoundTag(this.getModifierIdentifier());

				UUID uuid = comp.getUniqueId(this.getIdentifier() + ".LastVolley");
				UUID volleyID = event.getEntity().getEntityData().getUniqueId(this.getIdentifier() + ".Volley");

				if (uuid == volleyID) {
					result.entityHit.hurtResistantTime = 0;
				}
			}
		}
	}

	private static final double DSQ_SCALAR = 0.125 / Math.sqrt(-Math.log(0.5));

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLivingDamage(LivingHurtEvent event) {

		if (LAST_PROJ.get() != null) {
			EntityProjectileBase proj = LAST_PROJ.get();
			ItemStack stack = proj.tinkerProjectile.getItemStack();

			if (!this.isToolWithTrait(stack)) {
				return;
			}

			NBTTagCompound comp = proj.getEntityData().getCompoundTag(this.getModifierIdentifier());
			double distTraveled = comp.getDouble("DistTraveled");

			float mult = (float) (2 * Math.exp(-(DSQ_SCALAR * distTraveled) * (DSQ_SCALAR * distTraveled)));

			event.setAmount(event.getAmount() * mult);
		}
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		return newDamage;
	}

	@Override
	public int getPriority() {
		return -6942069; // we want this to run asap
	}
}
