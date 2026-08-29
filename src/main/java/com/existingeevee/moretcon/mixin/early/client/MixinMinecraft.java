package com.existingeevee.moretcon.mixin.early.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.NetworkHandler;
import com.existingeevee.moretcon.other.LeftClickEvent;
import com.existingeevee.moretcon.other.LeftClickEvent.NotifyEmptyLeftClickMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.MinecraftForge;

@Mixin(Minecraft.class)
public class MixinMinecraft {

	@Shadow
	int leftClickCounter;

	@Shadow
	RayTraceResult objectMouseOver;

	@Shadow
	EntityPlayerSP player;

	@Inject(method = "clickMouse()V", at = @At("HEAD"))
	private void eevees_arsenal$HEAD_Inject$clickMouse(CallbackInfo ci) {
		if (this.leftClickCounter <= 0) {
			if (this.objectMouseOver != null && !this.player.isRowingBoat()) {
				EntityPlayer player = Minecraft.getMinecraft().player;
				float strength = player.getCooledAttackStrength(Minecraft.getMinecraft().getRenderPartialTicks());

				MinecraftForge.EVENT_BUS.post(new LeftClickEvent(player, strength, objectMouseOver.typeOfHit));
				NetworkHandler.HANDLE.sendToServer(new NotifyEmptyLeftClickMessage(strength, objectMouseOver.typeOfHit));
			}
		}
	}
}
