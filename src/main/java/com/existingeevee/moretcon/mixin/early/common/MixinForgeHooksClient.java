package com.existingeevee.moretcon.mixin.early.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.existingeevee.moretcon.other.utils.ReequipHack;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

@Mixin(ForgeHooksClient.class)
public class MixinForgeHooksClient {

	@Inject(method = "shouldCauseBlockBreakReset", at = @At("HEAD"), cancellable = true, remap = false)
	private static void moretcon$HEAD_Inject$shouldCauseBlockBreakReset(ItemStack from, ItemStack to, CallbackInfoReturnable<Boolean> ci) {
		if (ReequipHack.HAS_PROCESSED.get()) {
			return;
		}
		ci.setReturnValue(ReequipHack.shouldCauseBlockBreakReset(from, to));
	}

	@Inject(method = "shouldCauseReequipAnimation", at = @At("HEAD"), cancellable = true, remap = false)
	private static void moretcon$HEAD_Inject$shouldCauseReequipAnimation(ItemStack from, ItemStack to, int slot, CallbackInfoReturnable<Boolean> ci) {
		if (ReequipHack.HAS_PROCESSED.get()) {
			return;
		}
		ci.setReturnValue(ReequipHack.shouldCauseReequipAnimation(from, to, slot));
	}

}
