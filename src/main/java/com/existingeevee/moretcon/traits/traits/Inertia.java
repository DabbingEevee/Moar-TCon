package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import thebetweenlands.common.registries.ItemRegistry;

public class Inertia extends AbstractTrait {

	public Inertia() {
		super(MiscUtils.createNonConflictiveName("inertia"), 0x000000);
	}

	@Override
	public boolean isToolWithTrait(ItemStack itemStack) {
		return super.isToolWithTrait(itemStack);
	}

	@Override
	public String getLocalizedDesc() {
		return Util.translate(LOC_Desc, getIdentifier() + (ConfigHandler.inertiaOnlyWorksOnAdvancedTools ? ".advancedtoolsonly" : ""));
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (ConfigHandler.inertiaOnlyWorksOnAdvancedTools && TinkerRegistry.getToolStationCrafting().contains(tool.getItem())) {
			return; //this is a simple tool. nope
		}

		boolean isTool = true;

		if (tool.getItem() instanceof ToolCore) {
			ToolCore core = (ToolCore) tool.getItem();
			isTool = core.hasCategory(Category.HARVEST);
		}
		if (isTool) {
			ItemRegistry.ANCIENT_BATTLE_AXE.onUpdate(tool, world, entity, itemSlot, isSelected);
		} else {
			ItemRegistry.ANCIENT_GREATSWORD.onUpdate(tool, world, entity, itemSlot, isSelected);
		}
	}

	public boolean canSweepBreak(ItemStack stack, ToolCore core, IBlockState state, EntityPlayerMP player, BlockPos pos) {
		if (ConfigHandler.inertiaOnlyWorksOnAdvancedTools && TinkerRegistry.getToolStationCrafting().contains(core)) {
			return false; //this is a simple tool. nope
		}

		boolean canHarvest = ToolHelper.isToolEffective2(stack, state) &&
				state.getBlockHardness(player.world, pos) <= 2.25F &&
				state.getPlayerRelativeBlockHardness(player, player.world, pos) > 0.01F;
		return canHarvest;
	}
}
