package com.existingeevee.moretcon.tools.tooltypes;

import java.util.List;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.compat.betweenlands.IBetweenTinkerTool;
import com.existingeevee.moretcon.inits.ModTools;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.ModTraits;
import com.google.common.collect.Multimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.SwordCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTools;
import thebetweenlands.api.item.CorrosionHelper;
import thebetweenlands.api.item.IAnimatorRepairable;
import thebetweenlands.api.item.ICorrodible;
import thebetweenlands.util.NBTHelper;

public class BetweenSword extends SwordCore implements ICorrodible, IAnimatorRepairable, IBetweenTinkerTool {

	public static final float DURABILITY_MODIFIER = 1.1f;

	public BetweenSword() {
		super(PartMaterialType.handle(
				TinkerTools.toolRod), PartMaterialType.head(ModTools.betweenSwordBlade),
				PartMaterialType.extra(TinkerTools.wideGuard));
		this.setUnlocalizedName(MiscUtils.createNonConflictiveName("blsword"));
		TinkerRegistry.registerToolCrafting(this);
		CorrosionHelper.addCorrosionPropertyOverrides(this);



		addCategory(Category.WEAPON);
	}

	@Override
	public float damagePotential() {
		return 1.0f;
	}

	@Override
	public double attackSpeed() {
		return 1.6d; // default vanilla sword speed
	}
	
	@Override
	public void setCorrosion(ItemStack stack, int corrosion) {
		boolean bad = this.getCorrosion(stack) < corrosion;
		
		if (bad && Math.random() < 0.5 && ToolHelper.getTraits(stack).contains(ModTraits.modValonite))
			return;
		NBTTagCompound nbt = NBTHelper.getStackNBTSafe(stack);
		nbt.setInteger(CorrosionHelper.ITEM_CORROSION_NBT_TAG, corrosion);
	}
	
	// sword sweep attack
	@Override
	public boolean dealDamage(ItemStack stack, EntityLivingBase player, Entity entity, float damage) {
		// deal damage first
		boolean hit = super.dealDamage(stack, player, entity, damage);
		// and then sweep
		if (hit && !ToolHelper.isBroken(stack)) {
			// sweep code from EntityPlayer#attackTargetEntityWithCurrentItem()
			// basically: no crit, no sprinting and has to stand on the ground for sweep.
			// Also has to move regularly slowly
			double d0 = (double) (player.distanceWalkedModified - player.prevDistanceWalkedModified);
			boolean flag = true;
			if (player instanceof EntityPlayer) {
				flag = ((EntityPlayer) player).getCooledAttackStrength(0.5F) > 0.9f;
			}
			boolean flag2 = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder()
					&& !player.isInWater() && !player.isPotionActive(MobEffects.BLINDNESS) && !player.isRiding();
			if (flag && !player.isSprinting() && !flag2 && player.onGround && d0 < (double) player.getAIMoveSpeed()) {
				for (EntityLivingBase entitylivingbase : player.getEntityWorld().getEntitiesWithinAABB(
						EntityLivingBase.class, entity.getEntityBoundingBox().expand(1.0D, 0.25D, 1.0D))) {
					if (entitylivingbase != player && entitylivingbase != entity
							&& !player.isOnSameTeam(entitylivingbase)
							&& player.getDistanceSq(entitylivingbase) < 9.0D) {
						entitylivingbase.knockBack(player, 0.4F,
								(double) MathHelper.sin(player.rotationYaw * 0.017453292F),
								(double) (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
						super.dealDamage(stack, player, entitylivingbase, 1f);
					}
				}

				player.getEntityWorld().playSound(null, player.posX, player.posY, player.posZ,
						SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
				if (player instanceof EntityPlayer) {
					((EntityPlayer) player).spawnSweepParticles();
				}
			}
		}

		return hit;
	}

	@Override
	public float getRepairModifierForPart(int index) {
		return DURABILITY_MODIFIER;
	}

	@Override
	public ToolNBT buildTagData(List<Material> materials) {
		ToolNBT data = buildDefaultTag(materials);
		// 2 base damage, like vanilla swords
		data.attack += 1f;
		data.durability *= DURABILITY_MODIFIER;
		return data;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		net.minecraft.block.material.Material material = state.getMaterial();
		float str = material != net.minecraft.block.material.Material.WOOD
				&& material != net.minecraft.block.material.Material.PLANTS
				&& material != net.minecraft.block.material.Material.VINE ? super.getDestroySpeed(stack, state)
						: ToolHelper.calcDigSpeed(stack, state);
		str = CorrosionHelper.getDestroySpeed(str, stack, state);
		return str;
	}

	@Override
	public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
		return CorrosionHelper.shouldCauseBlockBreakReset(oldStack, newStack);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return CorrosionHelper.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity holder, int slot, boolean isHeldItem) {
        super.onUpdate(itemStack, world, holder, slot, isHeldItem);
		CorrosionHelper.updateCorrosion(itemStack, world, holder, slot, isHeldItem);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		return CorrosionHelper.getAttributeModifiers(super.getAttributeModifiers(slot, stack), slot, stack,
				ATTACK_DAMAGE_MODIFIER, ToolHelper.getActualAttack(stack));
	}

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        boolean shift = Util.isShiftKeyDown();
        boolean ctrl = Util.isCtrlKeyDown();
    	if (!shift && !ctrl) {
    		CorrosionHelper.addCorrosionTooltips(stack, tooltip, flagIn.isAdvanced());
    		tooltip.add("");
    	}
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

	@Override
	public boolean isRepairableByAnimator(ItemStack stack) {
		return true;
	}

	@Override
	public int getMinRepairFuelCost(ItemStack stack) {
		return Math.round(stack.getMaxDamage() / 120) + 1;
	}

	@Override
	public int getFullRepairFuelCost(ItemStack stack) {
		return Math.round(stack.getMaxDamage() / 75) + 1;
	}

	@Override
	public int getFullRepairLifeCost(ItemStack arg0) {
		return Math.round(arg0.getMaxDamage() / 75) + 1;
	}

	@Override
	public int getMinRepairLifeCost(ItemStack arg0) {
		return Math.round(arg0.getMaxDamage() / 60) + 1;
	}
}
