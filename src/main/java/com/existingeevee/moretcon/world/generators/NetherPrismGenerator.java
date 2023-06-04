package com.existingeevee.moretcon.world.generators;

import java.util.Random;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.other.MoreTConLogger;
import com.existingeevee.moretcon.world.IBlockStateProvider;
import com.existingeevee.moretcon.world.WorldGenModifier;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class NetherPrismGenerator extends WorldGenModifier {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.getDimensionType().getId() != DimensionType.NETHER.getId()) {
			return;
		}
		if (!(peekNextInt(random, 25) == 0)) {
			return;
		}
		
		IBlockStateProvider provider = provider(random);
		
		BlockPos origin = new BlockPos(random.nextInt(16) + chunkX * 16, 160 + random.nextInt(64), random.nextInt(16) + chunkZ * 16);

		//MoreTConLogger.log(origin + "");
		
		int size = random.nextInt(8) + 12;
		
		int sqRad = 0;
		
		for (int i = 0; i < size / 2; i++) {
			if (i % 2 == 0 && i != 0)
				sqRad++;
			for (int j = -sqRad; j < sqRad + 1; j++) {
				for (int k = -sqRad; k < sqRad + 1; k++) {
					BlockPos pos = origin.add(j, i, k);
					
					IBlockState iblockstate = world.getBlockState(pos);
					Block block = iblockstate.getBlock();

					if (block.isAir(iblockstate, world, pos)) {
						world.setBlockState(pos, provider.getNextBlock(random), 2);
					}			
				}
			}
		}
		for (int i = 0; i < size / 2; i++) {
			if (i % 2 == 0)
				sqRad--;
			for (int j = -sqRad; j < sqRad + 1; j++) {
				for (int k = -sqRad; k < sqRad + 1; k++) {
					BlockPos pos = origin.add(j, i + size / 2, k);
					
					IBlockState iblockstate = world.getBlockState(pos);
					Block block = iblockstate.getBlock();

					if (block.isAir(iblockstate, world, pos)) {
						world.setBlockState(pos, provider.getNextBlock(random), 2);
					}			
				}
			}
		}
	}

	private static IBlockStateProvider provider(Random random) {
		return new IBlockStateProvider.RandomBlockStateProvider(
				new IBlockStateProvider.ConstantBlockStateProvider(ModBlocks.oreErythynite.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.BEDROCK.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.OBSIDIAN.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.OBSIDIAN.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.OBSIDIAN.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.NETHERRACK.getDefaultState())
				);
	}
}
