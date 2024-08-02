package com.existingeevee.moretcon.other.fires;

import net.minecraft.nbt.NBTTagCompound;

public class CustomFireInfo {

	int time = 0;
	CustomFireEffect effect = null;

	boolean forcedInvalid = false;

	public CustomFireInfo(CustomFireEffect effect, int time, boolean forcedInvalid) {
		this.effect = effect;
		this.time = time;
		this.forcedInvalid = forcedInvalid;
	}

	public CustomFireInfo(NBTTagCompound tag) {
		this(CustomFireEffect.registeredEffects.get(tag.getString("type")), tag.getInteger("time"), tag.getBoolean("forced_invalid"));
	}

	public CustomFireEffect getEffect() {
		return this.effect;
	}

	public boolean isInvalid() {
		return time <= 0 || effect == null || forcedInvalid;
	}

	public CustomFireInfo decrementTime() {
		if (isInvalid()) {
			return this;
		}
		time--;
		return this;
	}

	public NBTTagCompound asTag() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("type", effect.id);
		tag.setInteger("time", time);
		tag.setBoolean("forced_invalid", forcedInvalid);
		return tag;
	}

	@Override
	public String toString() {
		return effect + " " + time;
	}
}
