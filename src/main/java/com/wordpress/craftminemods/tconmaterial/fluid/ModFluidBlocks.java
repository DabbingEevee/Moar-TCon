package com.wordpress.craftminemods.tconmaterial.fluid;

import com.wordpress.craftminemods.tconmaterial.block.ModBlocks;
import com.wordpress.craftminemods.tconmaterial.other.RegisterHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModFluidBlocks {
	
	/* ------------------------------------- */
	public static Block blockLiquidFusionite = new BlockFluid("liquidfusionite", ModFluids.liquidFusionite, Material.LAVA);

	/* ------------------------------------- */	
	private static void registerBlocks(Block... block) {
		for (Block i : block) {
			ModFluidBlocks.addBlock(i);
		}
	}
	
	public static void init() {		
		ModFluidBlocks.registerBlocks(
/* ------------------------------------- */
				blockLiquidFusionite
/* ------------------------------------- */
				);
	}
	private static void addBlock(Block block) {

		RegisterHelper.registerBlock(block);
		ModBlocks.totalBlocks++;
	}
}
