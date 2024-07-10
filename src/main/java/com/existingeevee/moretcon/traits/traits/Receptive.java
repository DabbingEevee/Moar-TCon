package com.existingeevee.moretcon.traits.traits;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

import com.existingeevee.moretcon.other.utils.ArrowReferenceHelper;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.traits.abst.AdditionalDisplayTrait;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.IProjectileTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Receptive extends AdditionalDisplayTrait implements IProjectileTrait {

	// To track:
	// Arrows: attack
	// Bows: Drawspeed, Range, Bonus Damage
	// Tools: Attack, Mining Speed

	public Receptive() {
		super(MiscUtils.createNonConflictiveName("receptive"), 0); // BowCore TraitAlien

		MinecraftForge.EVENT_BUS.register(this);
	}

	public static final int POINT_CAP = 8000;

	public static final float MAX_DMG_MULT = 2.5f;
	public static final float MAX_MINING_MULT = 2.0f;

	public static final float MAX_DRAWSPEED_MULT = 2f;
	public static final float MAX_RANGE_MULT = 2f;
	public static final float MAX_BONUS_DMG_MULT = 2.0f;

	public int getMining(ItemStack stack) {
		NBTTagCompound comp = stack.getOrCreateSubCompound(this.getModifierIdentifier());
		return comp.hasKey("mining", NBT.TAG_INT) ? comp.getInteger("mining") : 0;
	}

	public int setMining(ItemStack stack, int amount) {
		NBTTagCompound comp = stack.getOrCreateSubCompound(this.getModifierIdentifier());
		comp.setInteger("mining", amount);
		recalculateStats(TagUtil.getTagSafe(stack), this.getAttack(stack), amount);
		return amount;
	}

	public int getAttack(ItemStack stack) {
		NBTTagCompound comp = stack.getOrCreateSubCompound(this.getModifierIdentifier());
		return comp.hasKey("attack", NBT.TAG_INT) ? comp.getInteger("attack") : 0;
	}

	public int setAttack(ItemStack stack, int amount) {
		NBTTagCompound comp = stack.getOrCreateSubCompound(this.getModifierIdentifier());
		comp.setInteger("attack", amount);
		recalculateStats(TagUtil.getTagSafe(stack), amount, this.getMining(stack));
		return amount;
	}

	public void recalculateStats(NBTTagCompound root, int attack, int mining) {
		NBTTagCompound comp = root.getCompoundTag(this.getModifierIdentifier());
		root.setTag(this.getModifierIdentifier(), comp);
		NBTTagCompound modification = comp.getCompoundTag("StatModifications");

		ToolNBT nbt = TinkerUtil.hasCategory(root, Category.LAUNCHER) ? new ProjectileLauncherNBT(TagUtil.getToolTag(root)) : TagUtil.getToolStats(root);

		// Undo the stat modification, taking any stat modifications by other traits
		// into account
		if (comp.hasKey("StatModifications")) {
			float atkDiscrepency = nbt.attack - modification.getFloat("AttackOrig");
			nbt.attack = modification.getFloat("AttackOrig") / modification.getFloat("DmgMult") + atkDiscrepency;

			float speedDiscrepency = nbt.speed - modification.getFloat("SpeedOrig");
			nbt.speed = modification.getFloat("SpeedOrig") / modification.getFloat("MiningMult") + speedDiscrepency;

			if (nbt instanceof ProjectileLauncherNBT) {
				ProjectileLauncherNBT launcher = (ProjectileLauncherNBT) nbt;

				float drawDiscrepency = launcher.drawSpeed - modification.getFloat("DrawOrig");
				launcher.drawSpeed = modification.getFloat("DrawOrig") / modification.getFloat("DrawspeedMult") + drawDiscrepency;

				float rangeDiscrepency = launcher.range - modification.getFloat("RangeOrig");
				launcher.range = modification.getFloat("RangeOrig") / modification.getFloat("RangeMult") + rangeDiscrepency;

				float bnDmgDiscrepency = launcher.bonusDamage - modification.getFloat("BnDmgOrig");
				launcher.bonusDamage = modification.getFloat("BnDmgOrig") / modification.getFloat("BonusDmgMult") + bnDmgDiscrepency;
			}
		}

		// calculate the new multipliers
		float dmgMult = 1 + attack * 1f / POINT_CAP * (MAX_DMG_MULT - 1);
		float miningMult = 1 + mining * 1f / POINT_CAP * (MAX_MINING_MULT - 1);

		float drawMult = 1 + attack * 1f / POINT_CAP * (MAX_DRAWSPEED_MULT - 1);
		float rangeMult = 1 + attack * 1f / POINT_CAP * (MAX_RANGE_MULT - 1);
		float bnDmgMult = 1 + attack * 1f / POINT_CAP * (MAX_BONUS_DMG_MULT - 1);

		// apply them and update StatModifications
		nbt.attack *= dmgMult;
		modification.setFloat("AttackOrig", nbt.attack);
		modification.setFloat("DmgMult", dmgMult);

		nbt.speed *= miningMult;
		modification.setFloat("SpeedOrig", nbt.speed);
		modification.setFloat("MiningMult", miningMult);

		if (nbt instanceof ProjectileLauncherNBT) {
			ProjectileLauncherNBT launcher = (ProjectileLauncherNBT) nbt;

			launcher.drawSpeed *= drawMult;
			modification.setFloat("DrawOrig", launcher.drawSpeed);
			modification.setFloat("DrawspeedMult", drawMult);

			launcher.range *= rangeMult;
			modification.setFloat("RangeOrig", launcher.range);
			modification.setFloat("RangeMult", rangeMult);

			launcher.bonusDamage *= bnDmgMult;
			modification.setFloat("BnDmgOrig", launcher.bonusDamage);
			modification.setFloat("BonusDmgMult", bnDmgMult);
		}

		TagUtil.setToolTag(root, nbt.get());
		comp.setTag("StatModifications", modification);
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		super.applyEffect(rootCompound, modifierTag);

		// we dont want modifications to messup the recalc
		rootCompound.getCompoundTag(this.getModifierIdentifier()).removeTag("StatModifications");
		this.recalculateStats(rootCompound, rootCompound.getCompoundTag(this.getModifierIdentifier()).getInteger("attack"), rootCompound.getCompoundTag(this.getModifierIdentifier()).getInteger("mining"));
	}

	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed) {
		if (target instanceof EntityLivingBase && ((EntityLivingBase) target).getHealth() <= 0) { // Murder!! YAYYY
			ItemStack origArrowStack = ArrowReferenceHelper.getProjectileStack(projectile.tinkerProjectile);
			if (!origArrowStack.isEmpty()) {
				this.addAttack(origArrowStack);
			}
		}
	}

	private void addAttack(ItemStack stack) {
		int attack = this.getAttack(stack);
		int mining = this.getMining(stack);
		if (attack + mining < POINT_CAP) {
			this.setAttack(stack, attack + 1);
		}
	}

	private void addMining(ItemStack stack) {
		int attack = this.getAttack(stack);
		int mining = this.getMining(stack);
		if (attack + mining < POINT_CAP) {
			this.setAttack(stack, mining + 1);
		}
	}

	private static final Field recentlyHit$EntityLivingBase = ObfuscationReflectionHelper.findField(EntityLivingBase.class, "field_70718_bc");
	private static final Field attackingPlayer$EntityLivingBase = ObfuscationReflectionHelper.findField(EntityLivingBase.class, "field_70717_bb");

	@SubscribeEvent
	public void onDie(LivingDeathEvent event) {
		try {
			EntityLivingBase killerLiving = (EntityLivingBase) attackingPlayer$EntityLivingBase.get(event.getEntityLiving());
			if (killerLiving != null && recentlyHit$EntityLivingBase.getInt(event.getEntityLiving()) > 0) {
				for (EnumHand hand : EnumHand.values()) {
					ItemStack stack = killerLiving.getHeldItem(hand);
					if (ToolHelper.getTraits(stack).contains(this)) {
						this.addAttack(stack);
						return;
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		if (random.nextInt(10) == 0)
			this.addMining(tool);
	}

	@Override
	public void onLaunch(EntityProjectileBase projectileBase, World world, EntityLivingBase shooter) {
		
	}

	@Override
	public void onProjectileUpdate(EntityProjectileBase projectile, World world, ItemStack toolStack) {
		
	}

	@Override
	public void onMovement(EntityProjectileBase projectile, World world, double slowdown) {
		
	}

	private static final DecimalFormat FORMATTER = new DecimalFormat("0.###");
	
	@Override
	public String getStringToRender(ItemStack tool) {
		double total = this.getAttack(tool) + this.getMining(tool);
		return FORMATTER.format(100 * total / POINT_CAP) + "%";
	}
}
