package com.existingeevee.moretcon.entity.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.existingeevee.moretcon.ModInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDecayingEffect extends EntityLiving {

	private static final DataParameter<Boolean> REVERSED = EntityDataManager.<Boolean>createKey(EntityDecayingEffect.class, DataSerializers.BOOLEAN);
	private static final DataParameter<String> TYPE = EntityDataManager.<String>createKey(EntityDecayingEffect.class, DataSerializers.STRING);
	private static final DataParameter<Float> Y_TRANSLATION = EntityDataManager.<Float>createKey(EntityDecayingEffect.class, DataSerializers.FLOAT);

	private double damage, radius;
	private int ticker, frame;
	private UUID attacker;
	private boolean hasSliced;
	
	public EntityDecayingEffect(World worldIn) {
		this(worldIn, EnumDecayingEffectType.DEFAULT, 4, 1.5, UUID.nameUUIDFromBytes("dummy".getBytes()));
	}

	public EntityDecayingEffect(World worldIn, EnumDecayingEffectType type, double damage, double radius, UUID attacker) {
		this(worldIn, type, damage, radius, attacker, 0, 0, false);
	}

	public EntityDecayingEffect(World worldIn, EnumDecayingEffectType type, double damage, double radius, UUID attacker, float anglePitch, float angleYaw, boolean randomizeDirection) {
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
		return type.getTicksPerFrame();
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

	public EntityDecayingEffect setType(EnumDecayingEffectType type) {
		if (type == null)
			type = EnumDecayingEffectType.DEFAULT;
		this.dataManager.set(TYPE, type.name());
		return this;
	}

	public UUID getAttacker() {
		return attacker;
	}

	public EntityDecayingEffect setAttacker(UUID attacker) {
		this.attacker = attacker;
		return this;
	}

	public List<Entity> getAffectedEntities() {
		return this.world.getEntitiesInAABBexcluding(this,
				this.getEntityBoundingBox().expand(0.5, 0, 0.5).expand(-0.5, 1.25, -0.5),
				e -> !e.getUniqueID().equals(this.attacker) && e instanceof EntityLivingBase);
	}

	@Override
	protected void onDeathUpdate() {
		this.setDead();
	}

	@Override
	public void onUpdate() {
		if (!hasSliced) {
			if (damage > 0) {
				EntityPlayer attackerEntity = this.world.getPlayerEntityByUUID(attacker);
				for (Entity e : getAffectedEntities()) {
					if (attackerEntity != null) {
						e.attackEntityFrom(DamageSource.causePlayerDamage(attackerEntity), (float) damage);
					} else {
						e.attackEntityFrom(DamageSource.GENERIC, (float) damage);
					}
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
		this.dataManager.register(Y_TRANSLATION, 0f);
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
			this.setYTranslation(data.getFloat("y_translation"));
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
		data.setFloat("y_translation", this.getYTranslation());
		data.setUniqueId("attacker", attacker);
		data.setBoolean("has_sliced", hasSliced);
		data.setString("type", getType() == null ? EnumDecayingEffectType.DEFAULT.name() : getType().name());
		compound.setTag("DecayingEffectData", data);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBrightnessForRender() {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
		if (!this.world.isBlockLoaded(blockpos$mutableblockpos)) {
			return 0;
		} else {
			float f = 1;
			f = MathHelper.clamp(f, 0.0F, 1.0F);
			int i = this.world.getCombinedLight(this.getPosition(), 0);
			int j = i & 255;
			int k = i >> 16 & 255;
			j = j + (int) (f * 15.0F * 16.0F);

			if (j > 240) {
				j = 240;
			}

			return j | k << 16;
		}
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

	public boolean isReversed() {
		return this.dataManager.get(REVERSED);
	}

	public void setReversed(boolean isReversed) {
		this.dataManager.set(REVERSED, isReversed);
	}

	public static enum EnumDecayingEffectType {
		DEFAULT(new ResourceLocation(ModInfo.MODID, "textures/other/decaying_effect/defaults/default"), 7, 1),
		BLOODY_ARC(new ResourceLocation(ModInfo.MODID, "textures/other/decaying_effect/bloody_arcs/bloody_arc"), 7, 1),
		SHOCKWAVE(new ResourceLocation(ModInfo.MODID, "textures/other/decaying_effect/shockwave/shockwave"), 7, 1),
		FIERY_SLASH(new ResourceLocation(ModInfo.MODID, "textures/other/decaying_effect/fiery_slash/fiery_slash"), 7, 1); //TODO

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

	public float getYTranslation() {
		return this.dataManager.get(Y_TRANSLATION);
	}

	public EntityDecayingEffect setYTranslation(float yTranslation) {
		this.dataManager.set(Y_TRANSLATION, yTranslation);
		return this;
	}
}
