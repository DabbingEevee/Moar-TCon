package com.wordpress.craftminemods.tconmaterial.fluid;

import com.wordpress.craftminemods.tconmaterial.block.ModBlocks;
import com.wordpress.craftminemods.tconmaterial.other.Logging;
import com.wordpress.craftminemods.tconmaterial.other.RegisterHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class ModFluids {
	
	public static int totalFluids;
	/**-------------------------------------**/
	public static Fluid liquidFusionite = new LiquidFluid("liquidfusionite", new ResourceLocation("tconmaterial:blocks/fluids/liquidfusionite_still"), new ResourceLocation("tconmaterial:blocks/fluids/liquidfusionite_flowing"));
	/**-------------------------------------**/

	private static void registerFluids(Fluid... fluid) {
		for (Fluid i : fluid) {
			ModFluids.addFluid(i);
		}
	}
	public static void init() {
		ModFluids.registerFluids(
/**-------------------------------------**/
				liquidFusionite
				
/**-------------------------------------**/
				);		
		
	}
	

	private static void addFluid(Fluid fluid) {

		RegisterHelper.registerFluid(fluid);
	}


}
