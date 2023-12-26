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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
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

public class BetweenPickaxe extends AoeToolCore implements ICorrodible, IAnimatorRepairable, IBetweenTinkerTool {

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE,
			Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE,
			Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE,
			Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE,
			Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE,
			Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON,
			Blocks.STONE_PRESSURE_PLATE);

	public static final ImmutableSet<net.minecraft.block.material.Material> effective_materials = ImmutableSet.of(
			net.minecraft.block.material.Material.IRON, net.minecraft.block.material.Material.ANVIL,
			net.minecraft.block.material.Material.ROCK, net.minecraft.block.material.Material.ICE,
			net.minecraft.block.material.Material.GLASS, net.minecraft.block.material.Material.PACKED_ICE,
			net.minecraft.block.material.Material.PISTON);

	// Pick-head, binding, tool-rod
	public BetweenPickaxe() {
		this(PartMaterialType.handle(TinkerTools.toolRod), PartMaterialType.head(ModTools.betweenPickHead),
				PartMaterialType.extra(TinkerTools.binding));

		this.setUnlocalizedName(MiscUtils.createNonConflictiveName("blpick"));
		TinkerRegistry.registerToolCrafting(this);
		CorrosionHelper.addCorrosionPropertyOverrides(this);
	}

	@Override
	public void setCorrosion(ItemStack stack, int corrosion) {
		boolean bad = this.getCorrosion(stack) < corrosion;
		
		if (bad && Math.random() < 0.5 && ToolHelper.getTraits(stack).contains(ModTraits.modValonite))
			return;
		NBTTagCompound nbt = NBTHelper.getStackNBTSafe(stack);
		nbt.setInteger(CorrosionHelper.ITEM_CORROSION_NBT_TAG, corrosion);
	}
	
	public BetweenPickaxe(PartMaterialType... requiredComponents) {
		super(requiredComponents);

		addCategory(Category.HARVEST);

		// set the toolclass, actual harvestlevel is done by the overridden callback
		this.setHarvestLevel("pickaxe", 0);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if (this.isInCreativeTab(tab)) {
			addDefaultSubItems(subItems);
			addInfiTool(subItems, "Between-InfiHarvester");
		}
	}

	@Override
	public boolean isEffective(IBlockState state) {
		return effective_materials.contains(state.getMaterial()) || EFFECTIVE_ON.contains(state.getBlock());
	}

	@Override
	public float damagePotential() {
		return 1f;
	}

	@Override
	public double attackSpeed() {
		return 1.2f;
	}

	@Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        //net.minecraft.block.material.Material material = state.getMaterial();
        float str = !(this.isEffective(state)) ? super.getDestroySpeed(stack, state) : ToolHelper.calcDigSpeed(stack, state);
        str = CorrosionHelper.getDestroySpeed(str, stack, state);
        return str;
    }

	@Override
	protected ToolNBT buildTagData(List<Material> materials) {
		return buildDefaultTag(materials);
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
