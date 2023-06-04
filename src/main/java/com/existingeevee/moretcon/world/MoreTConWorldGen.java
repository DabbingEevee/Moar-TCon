package com.existingeevee.moretcon.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.ConfigHandler;
import com.existingeevee.moretcon.world.generators.AetherOreGenerator;
import com.existingeevee.moretcon.world.generators.AsteroidGenerator;
import com.existingeevee.moretcon.world.generators.IgniglomerateGenerator;
import com.existingeevee.moretcon.world.generators.MainOreGenerator;
import com.existingeevee.moretcon.world.generators.NetherPrismGenerator;
import com.existingeevee.moretcon.world.generators.NetherSpikeGenerator;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class MoreTConWorldGen implements IWorldGenerator {
	
	private static final List<WorldGenModifier> modifiers = new ArrayList<WorldGenModifier>();
	
	public MoreTConWorldGen() {
		if (ConfigHandler.disableOreGen) return;
		
		if (CompatManager.aether_legacy) {
			modifiers.add(new AetherOreGenerator());
		}
		if (CompatManager.loadMain) {
			modifiers.add(new MainOreGenerator());
			modifiers.add(new AsteroidGenerator());
			modifiers.add(new NetherSpikeGenerator());
			modifiers.add(new IgniglomerateGenerator());
			modifiers.add(new NetherPrismGenerator());
		}
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (ConfigHandler.disableOreGen) return;
		modifiers.forEach(w -> w.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider));
	}
}