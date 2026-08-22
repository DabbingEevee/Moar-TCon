package com.existingeevee.moretcon.other.pwt;

import java.util.ArrayList;
import java.util.List;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.NetworkHandler;
import com.existingeevee.moretcon.other.pwt.net.SyncPersistantTickersMessage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

public class PersistantTickerSavedData extends WorldSavedData {

	private static final String DATA_NAME = ModInfo.MODID + "_persistant_tickers";

	protected List<PersistantTickerInstance> data = new ArrayList<>();

	public PersistantTickerSavedData(String s) {
		super(s);
	}
	
	public PersistantTickerSavedData() {
		super(DATA_NAME);
	}

	public static PersistantTickerSavedData get(World world) {
		MapStorage storage = world.getPerWorldStorage();
		PersistantTickerSavedData instance = (PersistantTickerSavedData) storage.getOrLoadData(PersistantTickerSavedData.class, DATA_NAME);

		if (instance == null) {
			instance = new PersistantTickerSavedData();
			storage.setData(DATA_NAME, instance);
		}
		return instance;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		data = new ArrayList<>();

		for (NBTBase t : nbt.getTagList("Storage", NBT.TAG_COMPOUND)) {
			if (t instanceof NBTTagCompound) {
				NBTTagCompound ctag = (NBTTagCompound) t;
				PersistantTicker ticker = PersistantTicker.getTicker(new ResourceLocation(ctag.getString("Type")));
				if (ticker == null)
					continue;
				PersistantTickerInstance instance = new PersistantTickerInstance(ticker, ctag.getCompoundTag("Data"));
				data.add(instance);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();
		for (PersistantTickerInstance pti : data) {
			NBTTagCompound instance = new NBTTagCompound();
			instance.setString("Type", pti.getTicker().getKey().toString());
			instance.setTag("Data", pti.getData().copy());
			list.appendTag(instance);
		}
		compound.setTag("Storage", list);
		return compound;
	}

	public void syncData(World world) {
		this.markDirty();
		if (world instanceof World && !world.isRemote)
			NetworkHandler.HANDLE.sendToDimension(new SyncPersistantTickersMessage(this.writeToNBT(new NBTTagCompound())), world.provider.getDimension());
	}

	protected void tickAll(World level) {
		data.forEach(s -> s.tick(level));
		data.removeIf(s -> !s.isValid());
		this.markDirty();
	}
}