package com.existingeevee.moretcon.world.generators;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.world.WorldGenModifier;
import com.existingeevee.moretcon.world.WorldgenContext;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class MainOreGenerator extends WorldGenModifier {

	@Override
	public void generate(IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, WorldgenContext ctx) {
		runGenerator(ModBlocks.oreNaturalVoidSpar.getDefaultState(), 3, 3, 1, 3, BlockMatcher.forBlock(Blocks.BEDROCK), ctx);
		runGenerator(ModBlocks.oreIrradium.getDefaultState(), 4, 3, 12, 32, BlockMatcher.forBlock(Blocks.STONE), ctx);
		runGenerator(ModBlocks.oreFusionite.getDefaultState(), 3, 3, 1, 6, BlockMatcher.forBlock(Blocks.STONE), ctx);
		runGenerator(ModBlocks.oreBedrockFusionite.getDefaultState(), 3, 3, 1, 3, BlockMatcher.forBlock(Blocks.BEDROCK), ctx);
		runGenerator(ModBlocks.oreGallium.getDefaultState(), 3, 1, 12, 24, BlockMatcher.forBlock(Blocks.STONE), ctx);
		runGenerator(ModBlocks.oreVoidSpar.getDefaultState(), 3, 3, 1, 3, BlockMatcher.forBlock(Blocks.STONE), ctx);
		runGenerator(ModBlocks.oreElectarite.getDefaultState(), 4, 3, 24, 48, BlockMatcher.forBlock(Blocks.STONE), false, ctx);
		runGenerator(ModBlocks.oreZracohlium.getDefaultState(), 4, 3, 24, 72, BlockMatcher.forBlock(Blocks.END_STONE), false, ctx);
		runGenerator(ModBlocks.oreEnderal.getDefaultState(), 4, 3, 12, 32, BlockMatcher.forBlock(Blocks.END_STONE), ctx);
		runGenerator(ModBlocks.oreEchostone.getDefaultState(), 4, 6, 12, 48, BlockMatcher.forBlock(Blocks.END_STONE), ctx);
		runGenerator(ModBlocks.oreGarstone.getDefaultState(), 3, 2, 75, 100, BlockMatcher.forBlock(Blocks.STONE), ctx);
		runGenerator(ModBlocks.oreEtherstone.getDefaultState(), 3, 3, 198, 202, BlockMatcher.forBlock(ModBlocks.blockOtherstone), ctx);
		runGenerator(ModBlocks.oreGeodesium.getDefaultState(), 4, 3, 128, 150, BlockMatcher.forBlock(ModBlocks.blockBrinkstone), ctx);
		runGenerator(ModBlocks.oreAnthracite.getDefaultState(), 6, 6, 128, 150, BlockMatcher.forBlock(ModBlocks.blockBrinkstone), ctx);
		runGenerator(ModBlocks.oreHallowsite.getDefaultState(), 3, 4, 45, 90, BlockMatcher.forBlock(Blocks.NETHERRACK), ctx);
		
		if (ctx.rand.nextInt(2) == 0) {
			runGenerator(ModBlocks.oreEbonite.getDefaultState(), 3, 3, 1, 3, BlockMatcher.forBlock(Blocks.BEDROCK), ctx);
			runGenerator(ModBlocks.oreGalliumEthereal.getDefaultState(), 3, 1, 198, 202, BlockMatcher.forBlock(ModBlocks.blockOtherstone), ctx);
		}
		
		if (ctx.rand.nextInt(4) == 0) {
			runGenerator(ModBlocks.oreIonstone.getDefaultState(), 3, 1, 198, 202, BlockMatcher.forBlock(ModBlocks.blockOtherstone), ctx);
			runGenerator(ModBlocks.oreVacuuite.getDefaultState(), 3, 1, 3, 7, BlockMatcher.forBlock(ModBlocks.blockOtherstone), ctx);
		}
	}

}
