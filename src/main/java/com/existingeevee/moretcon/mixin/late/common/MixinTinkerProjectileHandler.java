package com.existingeevee.moretcon.mixin.late.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.existingeevee.moretcon.other.StaticVars;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.capability.projectile.TinkerProjectileHandler;
import slimeknights.tconstruct.library.tools.ranged.IAmmo;

@SuppressWarnings("deprecation")
@Mixin(TinkerProjectileHandler.class)
public class MixinTinkerProjectileHandler {

	@Inject(at = @At(value = "RETURN"), method = "pickup", remap = false, locals = LocalCapture.CAPTURE_FAILHARD)
	public void moretcon$RETURN_Redirect$pickup(EntityLivingBase entity, boolean simulate, CallbackInfoReturnable<Boolean> ci, ItemStack stack) {
		if (!simulate && stack.getItem() instanceof IAmmo) {
			StaticVars.lastPickedUpArrow = stack;
		}
	}
}
