package com.existingeevee.moretcon.world.generators;

import java.util.Random;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.world.WorldGenModifier;
import com.gildedgames.the_aether.blocks.BlocksAether;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class AetherOreGenerator extends WorldGenModifier {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		runGenerator(ModBlocks.oreArkenium.getDefaultState(), 8, 3, 5, 250,
				BlockMatcher.forBlock(BlocksAether.holystone), world, random, chunkX, chunkZ);		
	}

}
