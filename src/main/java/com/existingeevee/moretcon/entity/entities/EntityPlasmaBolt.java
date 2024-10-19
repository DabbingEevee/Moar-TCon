package com.existingeevee.moretcon.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import com.existingeevee.moretcon.client.particle.ParticlePlasmaBolt;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;

//More of a tracker than anything. handles plasma projectiles
public class EntityPlasmaBolt extends Entity {

	private static final DataParameter<Integer> TARGET_ID = EntityDataManager.createKey(EntityPlasmaBolt.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> IMPACT_TIME = EntityDataManager.createKey(EntityPlasmaBolt.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> TIME_ALIVE = EntityDataManager.createKey(EntityPlasmaBolt.class, DataSerializers.VARINT);

	private static final DataParameter<Float> TARGET_X = EntityDataManager.createKey(EntityPlasmaBolt.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> TARGET_Y = EntityDataManager.createKey(EntityPlasmaBolt.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> TARGET_Z = EntityDataManager.createKey(EntityPlasmaBolt.class, DataSerializers.FLOAT);

	protected float px, py, pz;

	private UUID targetUUID;
	private Entity target;

	private UUID attackerUUID;
	private ItemStack sourceStack = ItemStack.EMPTY;
	private float damage = 2;
	
	@SideOnly(Side.CLIENT)
	public ParticlePlasmaBolt linkedParticle;
	
	public EntityPlasmaBolt(World worldIn) {
		super(worldIn);
		this.setSize(0.001f, 0.001f);
	}

	public EntityPlasmaBolt(World worldIn, EntityLivingBase shooter, EntityLivingBase target, ItemStack sourceStack) {
		this(worldIn);

		this.attackerUUID = shooter.getUniqueID();
		this.sourceStack = sourceStack;
		this.target = target;
		this.targetUUID = target.getUniqueID();

		// run twice to set prevLocs properly
		updateTarget();
		updateTarget();
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(TARGET_ID, -1);
		this.dataManager.register(IMPACT_TIME, 10);
		this.dataManager.register(TIME_ALIVE, 0);

		this.dataManager.register(TARGET_X, 0f);
		this.dataManager.register(TARGET_Y, 0f);
		this.dataManager.register(TARGET_Z, 0f);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.targetUUID = compound.getUniqueId("Target");
		this.dataManager.set(IMPACT_TIME, compound.getInteger("ImpactTime"));
		this.dataManager.set(TIME_ALIVE, compound.getInteger("TimeAlive"));

		this.sourceStack = new ItemStack(compound.getCompoundTag("Stack"));
		this.damage = compound.getFloat("Damage");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setUniqueId("Target", this.targetUUID);
		compound.setInteger("ImpactTime", this.dataManager.get(IMPACT_TIME));
		compound.setInteger("TimeAlive", this.dataManager.get(TIME_ALIVE));
		
		compound.setTag("Stack", sourceStack.serializeNBT());
		compound.setFloat("Damage", damage);
	}

	@Override
	public void onUpdate() {
		updateTarget();
				
		if (getImpactTime() == getTimeAlive()) {
			hitTarget();
		} else if (getImpactTime() < getTimeAlive()) {
			this.setDead();
		}
		
		if (!world.isRemote)
			this.setTimeAlive(getTimeAlive() + 1);
	}

	public void hitTarget() {		
		EntityLivingBase entity = (EntityLivingBase) target;
		DamageSource source = getDamageSource();
		EntityPlayer player = source.getTrueSource() instanceof EntityPlayer ? (EntityPlayer) source.getTrueSource() : null;

		Random rand = new Random(this.getUniqueID().getMostSignificantBits());
		
		if (target == null)
			return;

		List<ITrait> traits = new ArrayList<>(TinkerUtil.getTraitsOrdered(sourceStack));
		traits.remove(ModTraits.plasmaMissiles);
		
		traits.removeIf(p -> rand.nextInt(5) != 0);
		
		float dmg = damage;
		float dmgOrig = dmg;

		if (player != null) {
			for (ITrait t : traits) {
				dmg = t.damage(sourceStack, player, entity, dmgOrig, dmg, false);
			}
		}

		entity.hurtResistantTime = 0;
		float hpBefore = entity.getHealth();
		boolean wasHit = entity.attackEntityFrom(source, dmg);

		if (player != null) {
			for (ITrait t : traits) {
				entity.hurtResistantTime = 0;
				t.onHit(sourceStack, player, entity, dmg, false);
			}

			for (ITrait t : traits) {
				t.afterHit(sourceStack, player, entity, hpBefore - entity.getHealth(), false, wasHit);
			}
		}
	}

	public DamageSource getDamageSource() {
		if (attackerUUID == null) {
			return DamageSource.GENERIC;
		}

		EntityLivingBase attackerEntity = this.world.getPlayerEntityByUUID(attackerUUID);
		if (attackerEntity == null) {
			attackerEntity = this.world.getEntities(EntityLivingBase.class, e -> e.getUniqueID().equals(attackerUUID)).stream().findAny().orElse(null);
		}

		if (attackerEntity instanceof EntityPlayer) {
			return DamageSource.causePlayerDamage((EntityPlayer) attackerEntity);
		} else {
			return DamageSource.GENERIC;
		}
	}

	public int getImpactTime() {
		return this.dataManager.get(IMPACT_TIME);
	}

	public int getTimeAlive() {
		return this.dataManager.get(TIME_ALIVE);
	}

	public float getDamage() {
		return this.damage;
	}
	
	public EntityPlasmaBolt setImpactTime(int impactTime) {
		this.dataManager.set(IMPACT_TIME, impactTime);
		return this;
	}

	public EntityPlasmaBolt setTimeAlive(int timeAlive) {
		this.dataManager.set(TIME_ALIVE, timeAlive);
		return this;
	}

	public EntityPlasmaBolt setDamage(float damage) {
		this.damage = damage;
		return this;
	}

	
	public void updateTarget() {
		if (target == null && targetUUID != null) {
			if (!world.isRemote) {
				Stream<Entity> stream = this.world.getLoadedEntityList().stream().filter(e -> e.getUniqueID().equals(targetUUID));
				target = stream.findAny().orElse(null);

				if (target != null) {
					this.dataManager.set(TARGET_ID, target.getEntityId());
				}
			} else {
				target = world.getEntityByID(this.dataManager.get(TARGET_ID));
			}
		}
		
		px = this.dataManager.get(TARGET_X);
		py = this.dataManager.get(TARGET_Y);
		pz = this.dataManager.get(TARGET_Z);

		if (target != null) {
			
			Vec3d center = MiscUtils.getCenter(target.getEntityBoundingBox());
			
			this.dataManager.set(TARGET_X, (float) center.x);
			this.dataManager.set(TARGET_Y, (float) center.y);
			this.dataManager.set(TARGET_Z, (float) center.z);
		}
	}

	public Vec3d getPrevTarget() {
		return new Vec3d(px, py, pz);
	}
	
	public Vec3d getTarget() {
		return new Vec3d(this.dataManager.get(TARGET_X), this.dataManager.get(TARGET_Y), this.dataManager.get(TARGET_Z));
	}
}
