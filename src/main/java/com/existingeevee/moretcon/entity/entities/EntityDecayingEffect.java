package com.existingeevee.moretcon.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.existingeevee.moretcon.ModInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class EntityDecayingEffect extends EntityLiving {

	private static final DataParameter<Boolean> REVERSED = EntityDataManager
			.<Boolean>createKey(EntityDecayingEffect.class, DataSerializers.BOOLEAN);

	private static final DataParameter<String> TYPE = EntityDataManager.<String>createKey(EntityDecayingEffect.class,
			DataSerializers.STRING);

	private double damage, radius;
	// private EnumDecayingEffectType type;
	private int ticker, frame;
	private UUID attacker;
	private boolean hasSliced;

	public EntityDecayingEffect(World worldIn) {
		this(worldIn, EnumDecayingEffectType.DEFAULT, 4, 1.5, UUID.nameUUIDFromBytes("dummy".getBytes()));
	}

	public EntityDecayingEffect(World worldIn, EnumDecayingEffectType type, double damage, double radius,
			UUID attacker) {
		this(worldIn, type, damage, radius, attacker, 0, 0, false);
	}

	public EntityDecayingEffect(World worldIn, EnumDecayingEffectType type, double damage, double radius, UUID attacker,
			float anglePitch, float angleYaw, boolean randomizeDirection) {
		super(worldIn);
		this.damage = damage;
		this.radius = radius;
		this.attacker = attacker;
		this.rotationYaw = angleYaw + (float) (randomizeDirection ? (Math.random() - 0.5d) * 90 : 0);
		this.rotationPitch = anglePitch + (float) (randomizeDirection ? (Math.random() - 0.5d) * 27.5 : 0);
		this.setReversed(new Random().nextBoolean());
		this.setType(type);
		this.ticker = 0;
		this.frame = 0;
		this.hasSliced = false;
		this.setSize((float) radius * 2, 0.6F);
		this.setNoAI(true);
	}

	@Override
	public boolean isMovementBlocked() {
		return true;
	}

	@Override
	public void travel(float strafe, float vertical, float forward) {
		return;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}

	public void collideWithNearbyEntities() {
		// trolled
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.D);
	}

	public int getFrame() {
		return frame;
	}

	public int getMaxFrame() {
		EnumDecayingEffectType type = this.getType();
		if (type == null)
			type = EnumDecayingEffectType.DEFAULT;
		return type.getMaxFrame();
	}

	public int getTicksPerFrame() {
		EnumDecayingEffectType type = this.getType();
		if (type == null)
			type = EnumDecayingEffectType.DEFAULT;
		return type.ticksPerFrame();
	}

	public double getRadius() {
		return radius;
	}

	public EnumDecayingEffectType getType() {
		EnumDecayingEffectType type = EnumDecayingEffectType.valueOf(this.dataManager.get(TYPE));
		if (type == null) 
			type = EnumDecayingEffectType.DEFAULT;
		return type;
	}

	public void setType(EnumDecayingEffectType type) {
		if (type == null)
			type = EnumDecayingEffectType.DEFAULT;
		this.dataManager.set(TYPE, type.name());
	}
//		Logging.log(type.toString());

	public UUID getAttacker() {
		return attacker;
	}

	public void setAttacker(UUID attacker) {
		this.attacker = attacker;
	}

	public List<Entity> getAffectedEntities() {
		return this.world.getEntitiesInAABBexcluding(this,
				this.getEntityBoundingBox().expand(0.5, 0, 0.5).expand(-0.5, 1.25, -0.5),
				e -> !e.getUniqueID().toString().equals(this.attacker.toString()));
	}

	@Override
	protected void onDeathUpdate() {
		this.setDead();
	}

	@Override
	public void onUpdate() {
		if (!hasSliced) {
			EntityPlayer attackerEntity = this.world.getPlayerEntityByUUID(attacker);
			for (Entity e : getAffectedEntities()) {
				if (attackerEntity != null) {
					e.attackEntityFrom(DamageSource.causePlayerDamage(attackerEntity), (float) damage);
				} else {
					e.attackEntityFrom(DamageSource.GENERIC, (float) damage);
				}
			}
			this.hasSliced = true;
		}

		if (this.getHealth() <= 0.0F) {
			this.onDeathUpdate();
		}

		if (this.ticker > this.getTicksPerFrame()) {
			if (this.frame >= this.getMaxFrame() - 1) {
				this.setPosition(0, -Integer.MIN_VALUE, 0);
				this.setDead();
				return;
			}
			this.ticker = 0;
			this.frame++;
		}
		this.ticker++;
		// super.onUpdate();
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.damage = 4;
		this.radius = 1.5;
		this.attacker = UUID.nameUUIDFromBytes("dummy".getBytes());
		this.ticker = 0;
		this.frame = 0;
		this.dataManager.register(REVERSED, Boolean.valueOf(false));
		this.dataManager.register(TYPE, EnumDecayingEffectType.DEFAULT.name());
		this.hasSliced = false;
		this.setNoAI(true);
		this.setSize((float) radius * 2, 0.6F);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("DecayingEffectData", NBT.TAG_COMPOUND)) {
			NBTTagCompound data = compound.getCompoundTag("DecayingEffectData");
			this.damage = data.getDouble("damage");
			this.radius = data.getDouble("radius");
			this.ticker = data.getInteger("ticker");
			this.frame = data.getInteger("frame");
			this.setReversed(data.getBoolean("reverse"));
			this.setType(EnumDecayingEffectType.valueOf(data.getString("type")));
			this.attacker = data.getUniqueId("attacker");
			this.hasSliced = data.getBoolean("has_sliced");
			this.setSize((float) radius * 2, 0.6F);
			this.setNoAI(true);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		NBTTagCompound data = new NBTTagCompound();
		data.setDouble("damage", damage);
		data.setDouble("radius", radius);
		data.setInteger("ticker", ticker);
		data.setInteger("frame", frame);
		data.setBoolean("reverse", this.isReversed());
		data.setUniqueId("attacker", attacker);
		data.setBoolean("has_sliced", hasSliced);
		data.setString("type", getType() == null ? EnumDecayingEffectType.DEFAULT.name() : getType().name());
		compound.setTag("DecayingEffectData", data);
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public int getTicker() {
		return ticker;
	}

	// public float getAngleYaw() {
	// return angleYaw;
	// }

	// public float getAnglePitch() {
	// return anglePitch;
	// }

	public boolean isReversed() {
		return ((Boolean) this.dataManager.get(REVERSED)).booleanValue();
	}

	public void setReversed(boolean isReversed) {
		this.dataManager.set(REVERSED, Boolean.valueOf(isReversed));
	}

	public static enum EnumDecayingEffectType {
		DEFAULT(new ResourceLocation(ModInfo.MODID, "textures/other/decaying_effect/defaults/default"), 7, 1),
		BLOODY_ARC(new ResourceLocation(ModInfo.MODID, "textures/other/decaying_effect/bloody_arcs/bloody_arc"), 7, 1),
		FIERY_SLASH(null, 7, 1); //TODO

		private final int ticksPerFrame;
		private final ResourceLocation resource;
		private final int maxFrame;

		EnumDecayingEffectType(ResourceLocation resource, int maxFrame, int ticksPerFrame) {
			this.ticksPerFrame = ticksPerFrame;
			this.resource = resource;
			this.maxFrame = maxFrame;

		}

		public int getTicksPerFrame() {
			return ticksPerFrame;
		}

		public ResourceLocation getResource() {
			return resource;
		}

		public int getMaxFrame() {
			return maxFrame;
		}
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return new ArrayList<ItemStack>();
	}

	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {

	}

	@Override
	public EnumHandSide getPrimaryHand() {
		return EnumHandSide.RIGHT;
	}
}
