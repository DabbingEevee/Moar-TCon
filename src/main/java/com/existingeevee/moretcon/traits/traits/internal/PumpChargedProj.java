package com.existingeevee.moretcon.traits.traits.internal;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.DamageScalar;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.events.TinkerProjectileImpactEvent;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;

public class PumpChargedProj extends AbstractProjectileTrait {

	public PumpChargedProj() {
		super(MiscUtils.createNonConflictiveName("pumpcharged_projectile"), 0);
		MinecraftForge.EVENT_BUS.register(this);
		TinkerRegistry.addTrait(this);
	}

	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed) {
		int pumps = !ammoStack.hasTagCompound() ? 0 : ammoStack.getTagCompound().getInteger(ModInfo.MODID + ".Pumps");

		if (pumps >= 2) {
			//a bit of vector math to calculate the exact interception between the arrow and the target
			Vec3d projCenter = MiscUtils.getCenter(projectile.getEntityBoundingBox());
			Vec3d entCenter = MiscUtils.getCenter(target.getEntityBoundingBox());
			
			Vec3d diffNorm = entCenter.subtract(projCenter).normalize();
						
			RayTraceResult hit = target.getEntityBoundingBox().calculateIntercept(projCenter.subtract(diffNorm), entCenter.add(diffNorm));
			
			if (hit != null && !world.isRemote) {
				world.createExplosion(attacker, hit.hitVec.x, hit.hitVec.y, hit.hitVec.z, 1f, false);
			}
		}
		
		if (LAST_PROJ.get() != null) {
			LAST_PROJ.set(null);
		}
	}
	
	@Override
	public void onProjectileUpdate(EntityProjectileBase projectile, World world, ItemStack toolStack) {
		int pumps = !toolStack.hasTagCompound() ? 0 : toolStack.getTagCompound().getInteger(ModInfo.MODID + ".Pumps");

		if (!world.isRemote && projectile.inGround && pumps > 2) {
			boolean pushed = false;

			if (ModTraits.polyshotProj.isToolWithTrait(toolStack)) {
				NBTTagCompound comp = projectile.getEntityData().getCompoundTag(ModTraits.polyshotProj.getModifierIdentifier());
				double distTraveled = comp.getDouble("DistTraveled");
				float mult = (float) (2 * Math.exp(-(PolyshotProj.DSQ_SCALAR * distTraveled) * (PolyshotProj.DSQ_SCALAR * distTraveled)));
				DamageScalar.push(mult);
				pushed = true;
			}
			try {

				Vec3d projCenter = MiscUtils.getCenter(projectile.getEntityBoundingBox());
															
				world.createExplosion(projectile.shootingEntity, projCenter.x, projCenter.y, projCenter.z, 1f, false);
				
				
			} finally {
				if (pushed) {
					DamageScalar.pop();
				}
			}
		}
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
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLivingDamage(LivingHurtEvent event) {
		if (LAST_PROJ.get() != null) {
			EntityProjectileBase proj = LAST_PROJ.get();
			ItemStack stack = proj.tinkerProjectile.getItemStack();

			if (!this.isToolWithTrait(stack)) {
				return;
			}
			
			int pumps = !stack.hasTagCompound() ? 0 : stack.getTagCompound().getInteger(ModInfo.MODID + ".Pumps");
			float mult = 1 + (pumps * 0.5f);
			
			event.setAmount(event.getAmount() * mult + pumps);
		}
	}

	@Override
	public int getPriority() {
		return -100; // we want this to fast
	}
}
