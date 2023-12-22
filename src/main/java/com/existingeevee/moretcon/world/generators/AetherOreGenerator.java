package com.existingeevee.moretcon.world.generators;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.world.WorldGenModifier;
import com.existingeevee.moretcon.world.WorldgenContext;
import com.gildedgames.the_aether.blocks.BlocksAether;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class AetherOreGenerator extends WorldGenModifier {

	@Override
	public void generate(IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, WorldgenContext ctx) {
		runGenerator(ModBlocks.oreArkenium.getDefaultState(), 8, 3, 5, 250, BlockMatcher.forBlock(BlocksAether.holystone), ctx);		
	}

}
