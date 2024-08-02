package com.existingeevee.moretcon.world;

import java.util.Random;

import net.minecraft.world.World;

public class WorldgenContext {

	public WorldgenContext(World world, int chunkX, int chunkZ, Random rand) {
		super();
		this.world = world;
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.rand = rand;
	}

	public final World world;

	public final int chunkX, chunkZ;

	public final Random rand;
}
