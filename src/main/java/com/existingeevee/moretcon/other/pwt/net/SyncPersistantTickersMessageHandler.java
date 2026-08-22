package com.existingeevee.moretcon.other.pwt.net;

import com.existingeevee.moretcon.other.pwt.PersistantTickerSavedData;
import com.existingeevee.moretcon.other.utils.ClientHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncPersistantTickersMessageHandler implements IMessageHandler<SyncPersistantTickersMessage, IMessage> {
	
	@Override
	public IMessage onMessage(SyncPersistantTickersMessage message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			World world = ClientHelper.getLocalWorld();
			if (world == null)
				return;
			
			PersistantTickerSavedData.get(world).readFromNBT(message.data);
		});
		return null;
	}
}