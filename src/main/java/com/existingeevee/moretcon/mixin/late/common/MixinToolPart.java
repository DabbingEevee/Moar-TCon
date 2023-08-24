package com.existingeevee.moretcon.mixin.late.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.existingeevee.moretcon.materials.UniqueMaterial;

import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.common.item.SharpeningKit;

@Mixin(value = { ToolPart.class, SharpeningKit.class })
public abstract class MixinToolPart {

	@Inject(method = "canUseMaterial", at = @At("HEAD"), remap = false, cancellable = true)
	public void moretcon$HEAD_Inject$canUseMaterial(Material mat, CallbackInfoReturnable<Boolean> ci) {
		if (mat instanceof UniqueMaterial) {
			ToolPart $this = (ToolPart) (Object) this;
			UniqueMaterial unique = (UniqueMaterial) mat;
			ci.setReturnValue(unique.getPartType() == $this);
		}
	}
}
