package com.wordpress.craftminemods.tconmaterial.block.fluid;

import com.wordpress.craftminemods.tconmaterial.block.ModBlocks;
import com.wordpress.craftminemods.tconmaterial.other.Logging;
import com.wordpress.craftminemods.tconmaterial.other.RegisterHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.shared.TinkerFluids;

public class Fluids {
	
	public static final FluidBase moltenFusionite = (FluidBase) new FluidBase("MoltenFusionite", 0x0066ff, 0, false).setMaterial(Material.LAVA).setDensity(1100).setGaseous(false).setLuminosity(9).setViscosity(2000).setTemperature(900);
	private static void registerFluids(Fluid... fluid) {
		for (Fluid f : fluid) {
			Fluids.addFluids(f);
		}
	}
	public static void init() {
		Fluids.registerFluids(
/**-------------------------------------**/
				moltenFusionite
/**-------------------------------------**/
				);
	}
	

	private static void addFluids(Fluid fluid) {

		RegisterHelper.registerFluid(fluid);
		ModBlocks.totalBlocks++;
	}
}
