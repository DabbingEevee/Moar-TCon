package com.existingeevee.moretcon.world.generators;

import java.util.Random;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.world.IBlockStateProvider;
import com.existingeevee.moretcon.world.WorldGenModifier;
import com.existingeevee.moretcon.world.WorldgenContext;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class NetherSpikeGenerator extends WorldGenModifier {

	@Override
	public void generate(IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, WorldgenContext ctx) {
		World world = ctx.world;
		Random random = ctx.rand;

		if ((world.provider.getDimensionType().getId() != DimensionType.NETHER.getId()) || !(peekNextInt(random, 25) == 0)) {
			return;
		}

		IBlockStateProvider provider = provider(random);

		BlockPos origin = new BlockPos(random.nextInt(16) + ctx.chunkX * 16 + 8, 128, random.nextInt(16) + ctx.chunkZ * 16 + 8);

		BlockPos position = origin.up(random.nextInt(4));
		int i = random.nextInt(4) + 7;
		int j = i / 4 + random.nextInt(2);

		if (j > 1 && random.nextInt(60) == 0) {
			position = position.up(10 + random.nextInt(30));
		}

		for (int k = 0; k < i; ++k) {
			float f = (1.0F - (float) k / (float) i) * j;
			int l = MathHelper.ceil(f);

			for (int i1 = -l; i1 <= l; ++i1) {
				float f1 = MathHelper.abs(i1) - 0.25F;

				for (int j1 = -l; j1 <= l; ++j1) {
					float f2 = MathHelper.abs(j1) - 0.25F;

					if ((i1 == 0 && j1 == 0 || f1 * f1 + f2 * f2 <= f * f)
							&& (i1 != -l && i1 != l && j1 != -l && j1 != l || random.nextFloat() <= 0.75F)) {
						IBlockState iblockstate = world.getBlockState(position.add(i1, k, j1));
						Block block = iblockstate.getBlock();

						if (block != Blocks.BEDROCK) {
							world.setBlockState(position.add(i1, k, j1), provider.getNextBlock(random), 2);
						}

						if (k != 0 && l > 1) {
							iblockstate = world.getBlockState(position.add(i1, -k, j1));
							block = iblockstate.getBlock();

							if (block != Blocks.BEDROCK) {
								world.setBlockState(position.add(i1, -k, j1), provider.getNextBlock(random), 2);
							}
						}
					}
				}
			}
		}

		int k1 = j - 1;

		if (k1 < 0) {
			k1 = 0;
		} else if (k1 > 1) {
			k1 = 1;
		}

		for (int l1 = -k1; l1 <= k1; ++l1) {
			for (int i2 = -k1; i2 <= k1; ++i2) {
				BlockPos blockpos = position.add(l1, -1, i2);
				int j2 = 50;

				if (Math.abs(l1) == 1 && Math.abs(i2) == 1) {
					j2 = random.nextInt(5);
				}

				while (blockpos.getY() > 129) {
					IBlockState iblockstate1 = world.getBlockState(blockpos);
					if (iblockstate1.getBlock() == Blocks.BEDROCK) {
						break;
					}
					world.setBlockState(blockpos, provider.getNextBlock(random), 2);
					//this.setBlockAndNotifyAdequately(world, blockpos, Blocks.PACKED_ICE.getDefaultState());
					blockpos = blockpos.down();
					--j2;

					if (j2 <= 0) {
						blockpos = blockpos.down(random.nextInt(5) + 1);
						j2 = random.nextInt(5);
					}
				}
			}
		}
	}

	private static IBlockStateProvider provider(Random random) {
		return new IBlockStateProvider.RandomBlockStateProvider(
				new IBlockStateProvider.ConstantBlockStateProvider(ModBlocks.oreBloodstone.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.BEDROCK.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.BEDROCK.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.OBSIDIAN.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.NETHERRACK.getDefaultState())
				);
	}
}
