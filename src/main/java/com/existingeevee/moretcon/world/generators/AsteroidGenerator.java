package com.existingeevee.moretcon.world.generators;

import java.util.Random;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.world.IBlockStateProvider;
import com.existingeevee.moretcon.world.WorldGenModifier;
import com.existingeevee.moretcon.world.WorldgenContext;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class AsteroidGenerator extends WorldGenModifier {

	@Override
	public void generate(IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, WorldgenContext ctx) {
		World world = ctx.world;
		Random random = ctx.rand;
		int chunkX = ctx.chunkX;
		int chunkZ = ctx.chunkZ;

		if (!(world.provider.getDimensionType().getId() == DimensionType.THE_END.getId()) || !(peekNextInt(random, 125) == 0)) {
			return;
		}

		IBlockStateProvider blockToGen = provider(random);
		int blockAmount = random.nextInt(125) + 25;
		int chancesToSpawn = 1;
		int minHeight = 100;
		int maxHeight = 200;
		BlockMatcher blockToReplace = BlockMatcher.forBlock(Blocks.AIR);
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight) {
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
		}

		WorldGenMinable generator = new WorldGenMinable(Blocks.STONE.getDefaultState(), blockAmount,
				blockToReplace) {

			@Override
			public boolean generate(World worldIn, Random rand, BlockPos position) {
				float f = rand.nextFloat() * 4F;
				double d0 = position.getX() + 8 + MathHelper.sin(f) * blockAmount / 8.0F;
				double d1 = position.getX() + 8 - MathHelper.sin(f) * blockAmount / 8.0F;
				double d2 = position.getZ() + 8 + MathHelper.cos(f) * blockAmount / 8.0F;
				double d3 = position.getZ() + 8 - MathHelper.cos(f) * blockAmount / 8.0F;
				double d4 = position.getY() + rand.nextInt(3) - 2;
				double d5 = position.getY() + rand.nextInt(3) - 2;

				for (int i = 0; i < blockAmount; ++i) {
					float f1 = (float) i / (float) blockAmount;
					double d6 = d0 + (d1 - d0) * f1;
					double d7 = d4 + (d5 - d4) * f1;
					double d8 = d2 + (d3 - d2) * f1;
					double d9 = rand.nextDouble() * blockAmount / 16.0D;
					double d10 = (MathHelper.sin(3f * f1 * rand.nextInt(4)) + 1.0F) * d9 + 1.0D;
					double d11 = (MathHelper.sin(4f * f1 * rand.nextInt(4)) + 1.0F) * d9 + 1.0D;
					int j = MathHelper.floor(d6 - d10 / 2.0D);
					int k = MathHelper.floor(d7 - d11 / 2.0D);
					int l = MathHelper.floor(d8 - d10 / 2.0D);
					int i1 = MathHelper.floor(d6 + d10 / 2.0D);
					int j1 = MathHelper.floor(d7 + d11 / 2.0D);
					int k1 = MathHelper.floor(d8 + d10 / 2.0D);

					for (int l1 = j; l1 <= i1; ++l1) {
						double d12 = (l1 + 0.5D - d6) / (d10 / 2.0D);

						if (d12 * d12 < 1.0D) {
							for (int i2 = k; i2 <= j1; ++i2) {
								double d13 = (i2 + 0.5D - d7) / (d11 / 2.0D);

								if (d12 * d12 + d13 * d13 < 1.0D) {
									for (int j2 = l; j2 <= k1; ++j2) {
										double d14 = (j2 + 0.5D - d8) / (d10 / 2.0D);

										if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
											BlockPos blockpos = new BlockPos(l1, i2, j2);

											IBlockState state = worldIn.getBlockState(blockpos);
											if (state.getBlock().isReplaceableOreGen(state, worldIn, blockpos,
													blockToReplace)) {
												worldIn.setBlockState(blockpos, blockToGen.getNextBlock(rand), 2);
											}
										}
									}
								}
							}
						}
					}
				}

				return true;
			}
		};
		int heightdiff = maxHeight - minHeight + 1;
		for (int i = 0; i < chancesToSpawn; i++) {
			int x = chunkX * 16 + random.nextInt(16);
			int y = minHeight + random.nextInt(heightdiff);
			int z = chunkZ * 16 + random.nextInt(16);
			int r = random.nextInt(4);
			for (int j = 0; j < r; j++) {
				generator.generate(world, random, new BlockPos(x, y + (random.nextInt(10) - 5), z));
			}
		}

	}

	private static IBlockStateProvider provider(Random random) {
		return new IBlockStateProvider.RandomBlockStateProvider(
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.END_STONE.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.END_STONE.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.END_STONE.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.END_STONE.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.END_STONE.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.END_STONE.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.OBSIDIAN.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.OBSIDIAN.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.OBSIDIAN.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.END_STONE.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.END_STONE.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.END_STONE.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.END_STONE.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.END_STONE.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.END_STONE.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.OBSIDIAN.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.OBSIDIAN.getDefaultState()),
				new IBlockStateProvider.ConstantBlockStateProvider(Blocks.OBSIDIAN.getDefaultState()),
				new IBlockStateProvider.AmountBlockStateProvider(
						new IBlockStateProvider.ConstantBlockStateProvider(
								ModBlocks.oreGravitoniumDense.getDefaultState()),
						new IBlockStateProvider.ConstantBlockStateProvider(
								ModBlocks.oreGravitonium.getDefaultState()),
						random.nextInt(2) + 1));
	}
}
