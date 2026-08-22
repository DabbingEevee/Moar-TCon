package com.existingeevee.moretcon.other.pwt.net;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SyncPersistantTickersMessage implements IMessage {
	
	NBTTagCompound data = new NBTTagCompound();

	public SyncPersistantTickersMessage() {

	}

	public SyncPersistantTickersMessage(NBTTagCompound data) {
		this.data = data.copy();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			int len = buf.readInt();
			String tag = buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
			this.data = JsonToNBT.getTagFromJson(tag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		String payloadString = data.toString();
		buf.writeInt(payloadString.length());
		buf.writeCharSequence(payloadString, StandardCharsets.UTF_8);
	}
}