package com.existingeevee.moretcon.tools.tooltypes;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.existingeevee.moretcon.compat.betweenlands.IBetweenTinkerTool;
import com.existingeevee.moretcon.inits.ModTools;
import com.existingeevee.moretcon.other.Misc;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
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

public class BetweenShovel extends AoeToolCore implements ICorrodible, IAnimatorRepairable, IBetweenTinkerTool {

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND,
			Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND,
			Blocks.GRASS_PATH, Blocks.CONCRETE_POWDER);

	public static final ImmutableSet<net.minecraft.block.material.Material> effective_materials = ImmutableSet.of(
			net.minecraft.block.material.Material.GRASS, net.minecraft.block.material.Material.GROUND,
			net.minecraft.block.material.Material.SAND, net.minecraft.block.material.Material.CRAFTED_SNOW,
			net.minecraft.block.material.Material.SNOW, net.minecraft.block.material.Material.CLAY,
			net.minecraft.block.material.Material.CAKE);

	public BetweenShovel() {
		this(PartMaterialType.handle(TinkerTools.toolRod), PartMaterialType.head(ModTools.betweenShovelHead),
				PartMaterialType.extra(TinkerTools.binding));
		this.setUnlocalizedName(Misc.createNonConflictiveName("blshovel"));
		TinkerRegistry.registerToolCrafting(this);
		CorrosionHelper.addCorrosionPropertyOverrides(this);

	}

	protected BetweenShovel(PartMaterialType... requiredComponents) {
		super(requiredComponents);

		addCategory(Category.HARVEST);

		setHarvestLevel("shovel", 0);
	}

	@Override
	public boolean isEffective(IBlockState state) {
		return effective_materials.contains(state.getMaterial()) || EFFECTIVE_ON.contains(state.getBlock());
	}

	// grass paths
	@Nonnull
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
			float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (ToolHelper.isBroken(stack)) {
			return EnumActionResult.FAIL;
		}

		EnumActionResult result = Items.DIAMOND_SHOVEL.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		if (result == EnumActionResult.SUCCESS) {
			TinkerToolEvent.OnShovelMakePath.fireEvent(stack, player, world, pos);
		}

		// only do the AOE path if the selected block is grass or grass path
		Block block = world.getBlockState(pos).getBlock();
		if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
			for (BlockPos aoePos : getAOEBlocks(stack, world, player, pos)) {
				// stop if the tool breaks during the process
				if (ToolHelper.isBroken(stack)) {
					break;
				}

				EnumActionResult aoeResult = Items.DIAMOND_SHOVEL.onItemUse(player, world, aoePos, hand, facing, hitX,
						hitY, hitZ);
				// if we pass on an earlier block, check if another block succeeds here instead
				if (result != EnumActionResult.SUCCESS) {
					result = aoeResult;
				}

				if (aoeResult == EnumActionResult.SUCCESS) {
					TinkerToolEvent.OnShovelMakePath.fireEvent(stack, player, world, aoePos);
				}
			}
		}

		return result;
	}

	@Override
	public double attackSpeed() {
		return 1f;
	}

	@Override
	public float damagePotential() {
		return 0.9f;
	}

	@Override
	protected ToolNBT buildTagData(List<Material> materials) {
		return buildDefaultTag(materials);
	}

	@Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        //net.minecraft.block.material.Material material = state.getMaterial();
        float str = !(this.isEffective(state)) ? super.getDestroySpeed(stack, state) : ToolHelper.calcDigSpeed(stack, state);
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
