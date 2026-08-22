package com.existingeevee.moretcon.other.pwt;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.NetworkHandler;
import com.existingeevee.moretcon.other.pwt.net.SyncPersistantTickersMessage;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

@EventBusSubscriber(modid = ModInfo.MODID)
public class PersistantTickingHandler {

	@SubscribeEvent
	public static void onTick(TickEvent.WorldTickEvent event) {
		if (event.phase != Phase.END)
			return;
		PersistantTickerSavedData data = PersistantTickerSavedData.get(event.world);
		data.tickAll(event.world);
				
		if (event.world.getTotalWorldTime() % (10 * 20) == 0)
			data.syncData(event.world);
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		if (event.player instanceof EntityPlayerMP) {
			sync((EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
		if (event.player instanceof EntityPlayerMP) {
			sync((EntityPlayerMP) event.player);
		}

	}

	public static void sync(EntityPlayerMP player) {
		WorldSavedData data = PersistantTickerSavedData.get(player.world);
		if (data != null)
			NetworkHandler.HANDLE.sendTo(new SyncPersistantTickersMessage(data.writeToNBT(new NBTTagCompound())), player);
	}
	
	public static void addPersistantTicker(World lvl, PersistantTickerInstance inst) {
		if (!lvl.isRemote) {
			PersistantTickerSavedData data = PersistantTickerSavedData.get(lvl);
			data.data.add(inst);
			data.syncData(lvl);
		}
	}

}
