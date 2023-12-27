package com.existingeevee.moretcon.mixin.softdep.thebetweenlands;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ToolCore;
import thebetweenlands.api.item.IBigSwingAnimation;
import thebetweenlands.api.item.IExtendedReach;
import thebetweenlands.common.registries.ItemRegistry;

@Mixin(ToolCore.class)
public class MixinToolCore implements IBigSwingAnimation, IExtendedReach {

	//I promise to not do too much of these invasive mixins
	
	@Unique
	@Override
	public boolean shouldUseBigSwingAnimation(ItemStack stack) {
		return ModTraits.inertia.isToolWithTrait(stack);
	}

	@Unique
	@Override
	public float getSwingSpeedMultiplier(EntityLivingBase entity, ItemStack stack) {
		if (!ModTraits.inertia.isToolWithTrait(stack)) {
			return 1;
		}
		
		boolean isTool = true;
		
		if (stack.getItem() instanceof ToolCore) {
			ToolCore tool = (ToolCore) stack.getItem();
			isTool = tool.hasCategory(Category.HARVEST);
		}
		if (isTool)
			return 0.225f;
		
		return 0.35f;
	}

	@Override
	public double getReach() {
		return 1; //Hooopefully nothing bad happens
	}
	
	@Override
	public void onLeftClick(EntityPlayer player, ItemStack stack) {
		if (!ModTraits.inertia.isToolWithTrait(stack)) {
			return;
		}
		
		boolean isTool = true;
		
		if (stack.getItem() instanceof ToolCore) {
			ToolCore tool = (ToolCore) stack.getItem();
			isTool = tool.hasCategory(Category.HARVEST);
		}
		if (isTool) {
			((IExtendedReach) ItemRegistry.ANCIENT_BATTLE_AXE).onLeftClick(player, stack);
		} else {
			((IExtendedReach) ItemRegistry.ANCIENT_GREATSWORD).onLeftClick(player, stack);
		}
    }
}
