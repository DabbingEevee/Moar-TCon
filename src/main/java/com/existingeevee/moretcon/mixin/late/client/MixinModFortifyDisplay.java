package com.existingeevee.moretcon.mixin.late.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.traits.TraitClient;

import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.tools.modifiers.ModFortifyDisplay;

@Mixin(ModFortifyDisplay.class)
public class MixinModFortifyDisplay {

	@Inject(at = @At("RETURN"), method = "<init>()V", remap = false)
	private void moretcon$RETURN_Inject$__init__(CallbackInfo info) {
		TraitClient.register((IModifier) this);
	}
	
}
