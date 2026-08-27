package com.existingeevee.moretcon.mixin.early.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.existingeevee.moretcon.other.utils.ReequipHack;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.item.ItemStack;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

	@Shadow
	ItemStack highlightingItemStack;
	
	@Definition(id = "areItemStackTagsEqual", method = "Lnet/minecraft/item/ItemStack;areItemStackTagsEqual(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z")
	@Expression("areItemStackTagsEqual(?, ?)")
	@ModifyExpressionValue(method = "updateTick", at = @At("MIXINEXTRAS:EXPRESSION"))
	private boolean moretcon$EXPRESSION_areItemStackTagsEqual_ModifyExpressionValue$updateTick(boolean original, @Local ItemStack itemstack) {
		return original || ReequipHack.similarStackForActionBar(highlightingItemStack, itemstack);
	}

}
