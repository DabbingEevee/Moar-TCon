package com.existingeevee.moretcon.other.recoil;

import com.existingeevee.moretcon.NetworkHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Adapted from MrCrayfish's Gun Mod
 */
public class RecoilHandler {
	public static final RecoilHandler INSTANCE = new RecoilHandler();

	private float cameraRecoil;
	private float progressCameraRecoil;

	private RecoilHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void recoil(EntityPlayer player, float angle) {
		if (player == null) {
			return;
		}
		if (player.world.isRemote) {
			this.cameraRecoil = angle;
			this.progressCameraRecoil = 0F;
		} else if (player instanceof EntityPlayerMP) {
			NetworkHandler.HANDLE.sendTo(new MessageSendRecoil(angle), (EntityPlayerMP) player);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		if (event.phase != TickEvent.Phase.END || this.cameraRecoil <= 0)
			return;

		Minecraft mc = Minecraft.getMinecraft();
		if (mc.player == null)
			return;

		float recoilAmount = this.cameraRecoil * mc.getRenderPartialTicks() * 0.15F;
		float startProgress = this.progressCameraRecoil / this.cameraRecoil;
		float endProgress = (this.progressCameraRecoil + recoilAmount) / this.cameraRecoil;

		float pitch = mc.player.rotationPitch;
		float newPitch = pitch;
		if (startProgress < 0.2F) {
			newPitch = pitch - ((endProgress - startProgress) / 0.2F) * this.cameraRecoil;
		} else {
			newPitch = pitch + ((endProgress - startProgress) / 0.8F) * this.cameraRecoil;
		}

		mc.player.rotationPitch = newPitch;
		this.progressCameraRecoil += recoilAmount;

		if (this.progressCameraRecoil >= this.cameraRecoil) {
			this.cameraRecoil = 0;
			this.progressCameraRecoil = 0;
		}
	}
}