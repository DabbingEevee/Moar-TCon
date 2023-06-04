package com.existingeevee.moretcon.world.generators;

import java.util.Random;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.world.WorldGenModifier;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class IgniglomerateGenerator extends WorldGenModifier {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (!(world.provider.getDimensionType().getId() == DimensionType.NETHER.getId())) return;
				
		if (!(peekNextInt(random, 16) == 0)) return;
				
		int x = chunkX * 16 + random.nextInt(16);
		int y = random.nextInt(15) + 20;
		int z = chunkZ * 16 + random.nextInt(16);

		BlockPos pos = new BlockPos(x,y,z);
		
		if (world.getBlockState(pos).getMaterial().equals(Material.LAVA)) {			
			if (!world.getBlockState(pos.up()).getMaterial().equals(Material.LAVA)) return;
			if (!world.getBlockState(pos.down()).getMaterial().equals(Material.LAVA)) return;
			if (!world.getBlockState(pos.north()).getMaterial().equals(Material.LAVA)) return;
			if (!world.getBlockState(pos.east()).getMaterial().equals(Material.LAVA)) return;
			if (!world.getBlockState(pos.south()).getMaterial().equals(Material.LAVA)) return;
			if (!world.getBlockState(pos.west()).getMaterial().equals(Material.LAVA)) return;

			world.setBlockState(pos, ModBlocks.oreIgniglomerate.getDefaultState(), 2);
		}
		
	}
}
