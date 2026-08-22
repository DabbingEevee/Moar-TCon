package com.existingeevee.moretcon.other.pwt;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class PersistantTicker {

	private static final Map<ResourceLocation, PersistantTicker> MAP = new HashMap<>();

	public static PersistantTicker getTicker(ResourceLocation rl) {
		return MAP.get(rl);
	}

	private final ResourceLocation key;

	public PersistantTicker(String namespace, String path) {
		this(new ResourceLocation(namespace, path));
	}

	public PersistantTicker(ResourceLocation key) {
		this.key = key;
		if (!MAP.containsKey(key)) {
			MAP.put(key, this);
		} else {
			throw new RuntimeException("duplicate entry detected");
		}
	}

	public abstract boolean isValid(NBTTagCompound tag, World world);

	public abstract void tick(NBTTagCompound tag, World world);

	public abstract void init(NBTTagCompound tag, Object... objects);

	public final ResourceLocation getKey() {
		return key;
	}
}
