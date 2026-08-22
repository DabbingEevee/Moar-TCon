package com.existingeevee.moretcon.other.pwt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PersistantTickerInstance {

	private final PersistantTicker ticker;
	private NBTTagCompound data = new NBTTagCompound();
	private boolean valid = true;
	
	public PersistantTickerInstance(PersistantTicker ticker, Object... objects) {
		this.ticker = ticker;
		this.data = new NBTTagCompound();
		ticker.init(data, objects);
	}

	public PersistantTickerInstance(PersistantTicker ticker, NBTTagCompound data) {
		this.ticker = ticker;
		this.data = data;
	}
	
	public void tick(World world) {
		if (valid) {
			ticker.tick(data, world);
			valid = ticker.isValid(data, world);
		}
	}

	public PersistantTicker getTicker() {
		return ticker;
	}
	
	public NBTTagCompound getData() {
		return data;
	}
	
	public boolean isValid() {
		return valid;
	}
}
