package com.existingeevee.moretcon.mathtest;

import net.minecraft.init.Biomes;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class MathTest {
	public static void main(String... args) {
		// System.out.println(Arrays.toString(LinearAlgebraUtils.gramSchmidt(new VecNd(0, 0, 1, 1), new VecNd(0, 1, 1, 0), new VecNd(1, 1, 0, 0))));
//		System.out.println(Arrays.toString(new VecNd[] { new VecNd(0, 0, 1, 1).scale(1. / Math.sqrt(2)), new VecNd(0, 1, 1. / 2, -1. / 2).scale(Math.sqrt(2d / 3)), new VecNd(3, 1, -1, 1).scale(1d / (2 * Math.sqrt(3))) }));
		
		Bootstrap.register();
		
		System.out.println(calcAridiculousness(Biomes.EXTREME_HILLS));
	}

	protected static float calcAridiculousness(Biome biome) {
		return (float) (Math.pow(1.25, 3d * (0.5f + biome.getDefaultTemperature() - biome.getRainfall())) - 1.25d);
	}
}
