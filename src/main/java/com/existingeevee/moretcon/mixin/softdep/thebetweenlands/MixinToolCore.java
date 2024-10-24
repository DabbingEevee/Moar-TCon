package com.existingeevee.moretcon.mixin.softdep.thebetweenlands;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.existingeevee.moretcon.MoreTCon;
import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.utils.ToolHelper;
import thebetweenlands.api.item.IBigSwingAnimation;
import thebetweenlands.api.item.IExtendedReach;
import thebetweenlands.common.registries.ItemRegistry;

@Mixin(ToolCore.class)
public class MixinToolCore implements IBigSwingAnimation, IExtendedReach {

	//I promise to not do too much of these invasive mixins

	@Unique
	@Override
	public boolean shouldUseBigSwingAnimation(ItemStack stack) {
		if ((ConfigHandler.inertiaOnlyWorksOnAdvancedTools && TinkerRegistry.getToolStationCrafting().contains(stack.getItem())) || MoreTCon.proxy.isClientSneaking() || ToolHelper.isBroken(stack)) {
			return false;
		}

		boolean isMelee = false;
		if (stack.getItem() instanceof ToolCore) {
			ToolCore tool = (ToolCore) stack.getItem();
			isMelee = !tool.hasCategory(Category.NO_MELEE);
		}

		return  isMelee && ModTraits.inertia.isToolWithTrait(stack);
	}

	@Unique
	@Override
	public float getSwingSpeedMultiplier(EntityLivingBase entity, ItemStack stack) {
		if (ConfigHandler.inertiaOnlyWorksOnAdvancedTools && TinkerRegistry.getToolStationCrafting().contains(stack.getItem())) {
			return 1; //this is a simple tool. nope
		}
		if (!ModTraits.inertia.isToolWithTrait(stack) || entity.isSneaking() || ToolHelper.isBroken(stack)) {
			return 1;
		}

		boolean isTool = true;
		boolean isMelee = true;

		if (stack.getItem() instanceof ToolCore) {
			ToolCore tool = (ToolCore) stack.getItem();
			isTool = tool.hasCategory(Category.HARVEST);
			isMelee = !tool.hasCategory(Category.NO_MELEE);
		}

		if (!isMelee) {
			return 1;
		}

		if (isTool) {
			return 0.225f;
		}

		return 0.35f;
	}

	@Unique
	@Override
	public double getReach() {
		return 1; //Hooopefully nothing bad happens
	}

	@Unique
	@Override
	public void onLeftClick(EntityPlayer player, ItemStack stack) {
		if (ConfigHandler.inertiaOnlyWorksOnAdvancedTools && TinkerRegistry.getToolStationCrafting().contains(stack.getItem())) {
			return; //this is a simple tool. nope
		}
		if (!ModTraits.inertia.isToolWithTrait(stack) || player.isSneaking() || ToolHelper.isBroken(stack)) {
			return;
		}

		boolean isTool = true;
		boolean isMelee = true;
		if (stack.getItem() instanceof ToolCore) {
			ToolCore tool = (ToolCore) stack.getItem();
			isTool = tool.hasCategory(Category.HARVEST);
			isMelee = !tool.hasCategory(Category.NO_MELEE);
		}
		if (!isMelee) {
			return;
		}

		if (isTool) {
			((IExtendedReach) ItemRegistry.ANCIENT_BATTLE_AXE).onLeftClick(player, stack);
		} else {
			((IExtendedReach) ItemRegistry.ANCIENT_GREATSWORD).onLeftClick(player, stack);
		}
    }
}
