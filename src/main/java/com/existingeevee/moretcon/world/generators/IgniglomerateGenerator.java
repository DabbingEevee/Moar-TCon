package com.existingeevee.moretcon.world.generators;

import java.util.Random;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.world.WorldGenModifier;
import com.existingeevee.moretcon.world.WorldgenContext;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class IgniglomerateGenerator extends WorldGenModifier {

	@Override
	public void generate(IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, WorldgenContext ctx) {
		World world = ctx.world;
		Random random = ctx.rand;
		
		if (!(world.provider.getDimensionType().getId() == DimensionType.NETHER.getId()))
			return;

		if (!(peekNextInt(random, 16) == 0))
			return;

		int x = ctx.chunkX * 16 + random.nextInt(16);
		int y = random.nextInt(15) + 20;
		int z = ctx.chunkZ * 16 + random.nextInt(16);

		BlockPos pos = new BlockPos(x, y, z);

		if (world.getBlockState(pos).getMaterial().equals(Material.LAVA)) {
			
			for (EnumFacing facing : EnumFacing.values()) {
				if (!world.getBlockState(pos.offset(facing)).getMaterial().equals(Material.LAVA))
					return;
			}

			world.setBlockState(pos, ModBlocks.oreIgniglomerate.getDefaultState(), 2);
		}

	}
}
