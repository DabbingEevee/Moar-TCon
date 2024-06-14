package com.existingeevee.moretcon.mixin.early.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.other.fires.CustomFireHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

	@Shadow
    Minecraft mc;
	
	@Inject(method = "renderOverlays(F)V", at = @At(value = "TAIL"))
	public void moretcon$INVOKE_Inject$renderOverlays(float partialTicks, CallbackInfo ci) {
		GlStateManager.disableAlpha();
		if (!this.mc.player.isSpectator()) {
			CustomFireHelper.hookRenderCustomFire(partialTicks);
		}
		GlStateManager.enableAlpha();
	}
}
