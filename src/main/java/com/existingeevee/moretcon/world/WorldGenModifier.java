package com.existingeevee.moretcon.world;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.inits.ModBlocks;
import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public abstract class WorldGenModifier {

	public abstract void generate(IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, WorldgenContext ctx);

	public static void runGenerator(IBlockState blockToGen, int blockAmount, int chancesToSpawn, int minHeight, int maxHeight, Predicate<IBlockState> blockToReplace, boolean spawnExposed, WorldgenContext ctx) {
		runGenerator(new IBlockStateProvider.ConstantBlockStateProvider(blockToGen), blockAmount, chancesToSpawn, minHeight, maxHeight, blockToReplace, spawnExposed, ctx);
	}

	public static void runGenerator(IBlockState blockToGen, int blockAmount, int chancesToSpawn, int minHeight, int maxHeight, Predicate<IBlockState> blockToReplace, WorldgenContext ctx) {
		runGenerator(new IBlockStateProvider.ConstantBlockStateProvider(blockToGen), blockAmount, chancesToSpawn, minHeight, maxHeight, blockToReplace, true, ctx);
	}

	public static Random cloneRandom(Random rand) {
		long theSeed = 0;
		try {
			Field field = Random.class.getDeclaredField("seed");
			field.setAccessible(true);
			theSeed = (((AtomicLong) field.get(rand)).get() ^ 0x5DEECE66DL);
		} catch (Exception e) {
		}
		return new Random(theSeed);
	}

	public static int peekNextInt(Random rand, int bound) {
		return cloneRandom(rand).nextInt(bound);
	}

	public static void runGenerator(IBlockStateProvider blockToGen, int blockAmount, int chancesToSpawn, int minHeight, int maxHeight, Predicate<IBlockState> blockToReplace, boolean spawnExposed, WorldgenContext ctx) {
		if (!ConfigHandler.disableOreGen) {

			if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight) {
				throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
			}

			int heightdiff = maxHeight - minHeight + 1;
			for (int m = 0; m < chancesToSpawn; m++) {
				int x = ctx.chunkX * 16 + ctx.rand.nextInt(16);
				int y = minHeight + ctx.rand.nextInt(heightdiff);
				int z = ctx.chunkZ * 16 + ctx.rand.nextInt(16);

				BlockPos position = new BlockPos(x, y, z);

				float f = ctx.rand.nextFloat() * (float) Math.PI;
				double d0 = position.getX() + 8 + MathHelper.sin(f) * blockAmount / 8.0F;
				double d1 = position.getX() + 8 - MathHelper.sin(f) * blockAmount / 8.0F;
				double d2 = position.getZ() + 8 + MathHelper.cos(f) * blockAmount / 8.0F;
				double d3 = position.getZ() + 8 - MathHelper.cos(f) * blockAmount / 8.0F;
				double d4 = position.getY() + ctx.rand.nextInt(3) - 2;
				double d5 = position.getY() + ctx.rand.nextInt(3) - 2;

				for (int i = 0; i < blockAmount; ++i) {
					float f1 = (float) i / (float) blockAmount;
					double d6 = d0 + (d1 - d0) * f1;
					double d7 = d4 + (d5 - d4) * f1;
					double d8 = d2 + (d3 - d2) * f1;
					double d9 = ctx.rand.nextDouble() * blockAmount / 16.0D;
					double d10 = (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
					double d11 = (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
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

											IBlockState state = ctx.world.getBlockState(blockpos);
											if (state.getBlock().isReplaceableOreGen(state, ctx.world, blockpos, blockToReplace)) {
												boolean shouldSpawn = true;

												if (!spawnExposed) {
													for (EnumFacing facing : EnumFacing.values()) {
														if (!ctx.world.isBlockFullCube(blockpos.offset(facing))) {
															shouldSpawn = false;
														}
													}
												}

												if (shouldSpawn) {
													IBlockState state1 = blockToGen.getNextBlock(ctx.rand);
													if (state1.getBlock() == ModBlocks.oreVacuuite)
														System.out.println(blockpos);
													ctx.world.setBlockState(blockpos, state1, 2);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
