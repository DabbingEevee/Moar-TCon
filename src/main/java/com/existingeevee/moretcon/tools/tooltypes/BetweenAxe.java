package com.existingeevee.moretcon.tools.tooltypes;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.compat.betweenlands.IBetweenTinkerTool;
import com.existingeevee.moretcon.inits.ModTools;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.ModTraits;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.particle.Particles;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.AoeToolCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTools;
import thebetweenlands.api.item.CorrosionHelper;
import thebetweenlands.api.item.IAnimatorRepairable;
import thebetweenlands.api.item.ICorrodible;
import thebetweenlands.util.NBTHelper;

public class BetweenAxe extends AoeToolCore implements ICorrodible, IAnimatorRepairable, IBetweenTinkerTool {

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG,
			Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER,
			Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE);

	public static final ImmutableSet<net.minecraft.block.material.Material> effective_materials = ImmutableSet.of(
			net.minecraft.block.material.Material.WOOD, net.minecraft.block.material.Material.VINE,
			net.minecraft.block.material.Material.PLANTS, net.minecraft.block.material.Material.GOURD,
			net.minecraft.block.material.Material.CACTUS);

	public BetweenAxe() {
		this(
			PartMaterialType.handle(TinkerTools.toolRod),
			PartMaterialType.head(ModTools.betweenAxeHead),
			PartMaterialType.extra(TinkerTools.binding)
			);
		this.setUnlocalizedName(MiscUtils.createNonConflictiveName("blaxe"));
		TinkerRegistry.registerToolCrafting(this);
		CorrosionHelper.addCorrosionPropertyOverrides(this);
		//this.setRegistryName("blaxe");

	}

	protected BetweenAxe(PartMaterialType... requiredComponents) {
		super(requiredComponents);

		addCategory(Category.HARVEST);
		addCategory(Category.WEAPON);

		this.setHarvestLevel("axe", 0);
	}

	@Override
	public void setCorrosion(ItemStack stack, int corrosion) {
		boolean bad = this.getCorrosion(stack) < corrosion;
		
		if (bad && Math.random() < 0.5 && ToolHelper.getTraits(stack).contains(ModTraits.modValonite))
			return;
		NBTTagCompound nbt = NBTHelper.getStackNBTSafe(stack);
		nbt.setInteger(CorrosionHelper.ITEM_CORROSION_NBT_TAG, corrosion);
	}
	
	@Override
	public boolean isEffective(IBlockState state) {
		return effective_materials.contains(state.getMaterial()) || EFFECTIVE_ON.contains(state.getBlock());
	}

	@Override
	public float damagePotential() {
		return 1.1f;
	}

	@Override
	public boolean isRepairableByAnimator(ItemStack stack) {
		return true;
	}

	@Override
	public double attackSpeed() {
		return 1.1f; // a bit faster than vanilla axes
	}

	@Override
	public float knockback() {
		return 1.3f;
	}
	
	@Override
	public void afterBlockBreak(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase player, int damage, boolean wasEffective) {
		// breaking leaves does not reduce durability
		if (state.getBlock().isLeaves(state, world, pos)) {
			damage = 0;
		}
		super.afterBlockBreak(stack, world, state, pos, player, damage, wasEffective);
	}

	@Override
	public boolean dealDamage(ItemStack stack, EntityLivingBase player, Entity entity, float damage) {
		boolean hit = super.dealDamage(stack, player, entity, damage);

		if (hit && readyForSpecialAttack(player)) {
			TinkerTools.proxy.spawnAttackParticle(Particles.HATCHET_ATTACK, player, 0.8d);
		}

		return hit;
	}

	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity,
			EntityLivingBase attacker) {
		return true;
	}

	@Override
	protected ToolNBT buildTagData(List<Material> materials) {
		ToolNBT data = buildDefaultTag(materials);
		data.attack += 0.5f;
		return data;
	}

	@Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        net.minecraft.block.material.Material material = state.getMaterial();
        float str = material != net.minecraft.block.material.Material.WOOD && material != net.minecraft.block.material.Material.PLANTS && material != net.minecraft.block.material.Material.VINE ? super.getDestroySpeed(stack, state) : ToolHelper.calcDigSpeed(stack, state);
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
    	Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

    	if(slot == EntityEquipmentSlot.MAINHAND && !ToolHelper.isBroken(stack)) {
    		multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", ToolHelper.getActualAttack(stack), 0));
    		multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", ToolHelper.getActualAttackSpeed(stack) - 4d, 0));
    	}

    	TinkerUtil.getTraitsOrdered(stack).forEach(trait -> trait.getAttributeModifiers(slot, stack, multimap));

    	return CorrosionHelper.getAttributeModifiers(multimap, slot, stack, ATTACK_DAMAGE_MODIFIER, ToolHelper.getActualAttack(stack));

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