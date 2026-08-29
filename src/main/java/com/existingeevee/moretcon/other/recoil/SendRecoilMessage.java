package com.existingeevee.moretcon.other.recoil;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SendRecoilMessage implements IMessage, IMessageHandler<SendRecoilMessage, IMessage> {
	
	public float recoilAngle = 0;

	public SendRecoilMessage() {

	}

	public SendRecoilMessage(float recoilAngle) {
		this.recoilAngle = recoilAngle;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		recoilAngle = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(recoilAngle);
	}

	@Override
	public IMessage onMessage(SendRecoilMessage message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			recoil(message.recoilAngle);
		});
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	private static void recoil(float angle) {
		RecoilHandler.INSTANCE.recoil(Minecraft.getMinecraft().player, angle);
	}
}