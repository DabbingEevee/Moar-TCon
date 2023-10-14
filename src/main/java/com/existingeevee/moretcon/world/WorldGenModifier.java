package com.existingeevee.moretcon.world;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import com.existingeevee.moretcon.config.ConfigHandler;
import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;

public abstract class WorldGenModifier {

	public abstract void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider);
	
	public static void runGenerator(IBlockState blockToGen, int blockAmount, int chancesToSpawn, int minHeight, int maxHeight, Predicate<IBlockState> blockToReplace, World world, Random rand, int chunkX, int chunkZ) {
		runGenerator(new IBlockStateProvider.ConstantBlockStateProvider(blockToGen), blockAmount, chancesToSpawn, minHeight, maxHeight, blockToReplace, world, rand, chunkX, chunkZ);

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
	
	public static void runGenerator(IBlockStateProvider blockToGen, int blockAmount, int chancesToSpawn, int minHeight,
			int maxHeight, Predicate<IBlockState> blockToReplace, World world, Random rand, int chunkX, int chunkZ) {
		if (!ConfigHandler.disableOreGen) {

			if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
				throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

			WorldGenMinable generator = new WorldGenMinable(Blocks.STONE.getDefaultState(), blockAmount, blockToReplace) {

				@Override
				public boolean generate(World worldIn, Random rand, BlockPos position) {
					float f = rand.nextFloat() * (float) Math.PI;
					double d0 = (double) ((float) (position.getX() + 8) + MathHelper.sin(f) * blockAmount / 8.0F);
					double d1 = (double) ((float) (position.getX() + 8) - MathHelper.sin(f) * blockAmount / 8.0F);
					double d2 = (double) ((float) (position.getZ() + 8) + MathHelper.cos(f) * blockAmount / 8.0F);
					double d3 = (double) ((float) (position.getZ() + 8) - MathHelper.cos(f) * blockAmount / 8.0F);
					double d4 = (double) (position.getY() + rand.nextInt(3) - 2);
					double d5 = (double) (position.getY() + rand.nextInt(3) - 2);

					for (int i = 0; i < blockAmount; ++i) {
						float f1 = (float) i / (float) blockAmount;
						double d6 = d0 + (d1 - d0) * (double) f1;
						double d7 = d4 + (d5 - d4) * (double) f1;
						double d8 = d2 + (d3 - d2) * (double) f1;
						double d9 = rand.nextDouble() * (double) blockAmount / 16.0D;
						// * rand.nextInt(8)  * rand.nextInt(8)
						double d10 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
						double d11 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
						int j = MathHelper.floor(d6 - d10 / 2.0D);
						int k = MathHelper.floor(d7 - d11 / 2.0D);
						int l = MathHelper.floor(d8 - d10 / 2.0D);
						int i1 = MathHelper.floor(d6 + d10 / 2.0D);
						int j1 = MathHelper.floor(d7 + d11 / 2.0D);
						int k1 = MathHelper.floor(d8 + d10 / 2.0D);

						for (int l1 = j; l1 <= i1; ++l1) {
							double d12 = ((double) l1 + 0.5D - d6) / (d10 / 2.0D);

							if (d12 * d12 < 1.0D) {
								for (int i2 = k; i2 <= j1; ++i2) {
									double d13 = ((double) i2 + 0.5D - d7) / (d11 / 2.0D);

									if (d12 * d12 + d13 * d13 < 1.0D) {
										for (int j2 = l; j2 <= k1; ++j2) {
											double d14 = ((double) j2 + 0.5D - d8) / (d10 / 2.0D);

											if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
												BlockPos blockpos = new BlockPos(l1, i2, j2);

												IBlockState state = worldIn.getBlockState(blockpos);
												if (state.getBlock().isReplaceableOreGen(state, worldIn, blockpos,
														blockToReplace)) {
													//MoreTConLogger.log(blockpos.getX() + " " + blockpos.getY() + " " + blockpos.getZ());
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
				int x = chunkX * 16 + rand.nextInt(16);
				int y = minHeight + rand.nextInt(heightdiff);
				int z = chunkZ * 16 + rand.nextInt(16);

				generator.generate(world, rand, new BlockPos(x, y, z));
			}
		}
	}
}
