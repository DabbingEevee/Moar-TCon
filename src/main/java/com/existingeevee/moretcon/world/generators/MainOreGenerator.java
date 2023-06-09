package com.existingeevee.moretcon.world.generators;

import java.util.Random;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.world.WorldGenModifier;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class MainOreGenerator extends WorldGenModifier {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		runGenerator(ModBlocks.oreNaturalVoidSpar.getDefaultState(), 3, 3, 1, 3,
				BlockMatcher.forBlock(Blocks.BEDROCK), world, random, chunkX, chunkZ);
		runGenerator(ModBlocks.oreIrradium.getDefaultState(), 4, 3, 12, 32, BlockMatcher.forBlock(Blocks.STONE),
				world, random, chunkX, chunkZ);
		runGenerator(ModBlocks.oreFusionite.getDefaultState(), 3, 3, 1, 3, BlockMatcher.forBlock(Blocks.STONE),
				world, random, chunkX, chunkZ);
		runGenerator(ModBlocks.oreGallium.getDefaultState(), 3, 1, 12, 24, BlockMatcher.forBlock(Blocks.STONE),
				world, random, chunkX, chunkZ);
		runGenerator(ModBlocks.oreVoidSpar.getDefaultState(), 3, 3, 1, 3, BlockMatcher.forBlock(Blocks.STONE),
				world, random, chunkX, chunkZ);
		runGenerator(ModBlocks.oreEnderal.getDefaultState(), 4, 3, 12, 32, BlockMatcher.forBlock(Blocks.END_STONE),
				world, random, chunkX, chunkZ);
		runGenerator(ModBlocks.oreEchostone.getDefaultState(), 4, 6, 12, 48, BlockMatcher.forBlock(Blocks.END_STONE),
				world, random, chunkX, chunkZ);
		runGenerator(ModBlocks.oreGarstone.getDefaultState(), 3, 2, 75, 100, BlockMatcher.forBlock(Blocks.STONE),
				world, random, chunkX, chunkZ);		
		runGenerator(ModBlocks.oreEtherstone.getDefaultState(), 3, 3, 198, 202,
				BlockMatcher.forBlock(ModBlocks.blockEtheralStone), world, random, chunkX, chunkZ);
		
		if (random.nextInt(4) == 0) {
			runGenerator(ModBlocks.oreEbonite.getDefaultState(), 3, 3, 1, 3, BlockMatcher.forBlock(Blocks.BEDROCK),
					world, random, chunkX, chunkZ);
		}
	}

}
