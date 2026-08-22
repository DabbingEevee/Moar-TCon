package com.existingeevee.moretcon.other.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ScreenshakeHandler {

	public static final ScreenshakeHandler INSTANCE = ClientHelper.isClient() ? new ScreenshakeHandlerClient() : new ScreenshakeHandler();

	public static class ScreenshakeHandlerClient extends ScreenshakeHandler {

		private double shakeIntensity;

		private int fallOffTime = -1;
		private int fallOffTimeMax = -1;
		private double fallOffShakeIntensity = 0;
		
		private float shakeEyeOffsetX = 0;
		private float shakeEyeOffsetY = 0;

		private ScreenshakeHandlerClient() {
			MinecraftForge.EVENT_BUS.register(this);
		}

		@Override
		public void screenShakePerTick(double shakeIntensity) {
			if (shakeIntensity < 0)
				throw new IllegalArgumentException("shakeIntensity must be greater than or equal to zero");

			if (shakeIntensity > this.shakeIntensity)
				this.shakeIntensity = shakeIntensity;
		}
		
		@Override
		public void screenShakeWithFalloff(double shakeIntensity, int falloffTime) {
			if (shakeIntensity < 0)
				throw new IllegalArgumentException("shakeIntensity must be greater than or equal to zero");

			if (falloffTime <= 0)
				throw new IllegalArgumentException("falloffTime must be greater than zero");

			double curShakeIntensity = fallOffShakeIntensity * fallOffTime / fallOffTimeMax;

			if (shakeIntensity > curShakeIntensity) {
				this.fallOffShakeIntensity = shakeIntensity;
				this.fallOffTimeMax = falloffTime;
				this.fallOffTime = falloffTime;
			}
		}

		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void onClientTick(TickEvent.ClientTickEvent event) {
			EntityPlayerSP player = Minecraft.getMinecraft().player;

			if (player != null) {
				if (shakeIntensity >= 0) {

					player.rotationPitch -= shakeEyeOffsetX;
					player.rotationYaw -= shakeEyeOffsetY;

					shakeEyeOffsetX = (float) (Math.random() * shakeIntensity - shakeIntensity / 2);
					shakeEyeOffsetY = (float) (Math.random() * shakeIntensity - shakeIntensity / 2);

					player.rotationPitch += shakeEyeOffsetX;
					player.rotationYaw += shakeEyeOffsetY;

					shakeIntensity = 0;
				}
			}
		}
		
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void handleFalloff(TickEvent.ClientTickEvent event) {
			EntityPlayerSP player = Minecraft.getMinecraft().player;

			if (player != null && fallOffTime > 0) {
				if (event.phase == Phase.START) {
					// do the per tick lmao
					// also decrement fallOffTime
					// shitty oneliners go!!
					screenShakePerTick(fallOffShakeIntensity * fallOffTime-- / fallOffTimeMax);
				}
			} else {
				// guess there is no player (they left the world or ect). we should reset
				fallOffTime = -1;
				fallOffTimeMax = -1;
				fallOffShakeIntensity = 0;
			}
		}
	}

	public void screenShakePerTick(double shakeIntensity) {
	}
	
	public void screenShakeWithFalloff(double shakeIntensity, int falloffTime) {
	}

	private ScreenshakeHandler() {
	}
}