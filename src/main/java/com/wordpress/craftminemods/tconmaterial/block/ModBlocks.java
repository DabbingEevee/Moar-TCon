package com.wordpress.craftminemods.tconmaterial.block;

import com.wordpress.craftminemods.tconmaterial.other.Logging;
import com.wordpress.craftminemods.tconmaterial.other.RegisterHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;

public class ModBlocks {
	public static int totalBlocks;
/*---------------------------------------*/
	public static Block blockFusionite = new BlockBase("blockFusionite", Material.IRON, 3).setHardness(10).setResistance(20);
	public static Block oreFusionite = new BlockBase("oreFusionite", Material.ROCK, 2).setHardness(7.5f).setResistance(10);
	
/*---------------------------------------*/
	
	
	private static void registerBlocks(Block... block) {
		for (Block i : block) {
			ModBlocks.addBlock(i);
		}
	}
	public static void init() {
		ModBlocks.registerBlocks(
/**-------------------------------------**/
				blockFusionite,	
				oreFusionite
/**-------------------------------------**/
				);
		Logging.Log("Loaded a total of " + totalBlocks + " different blocks.");
	}
	

	private static void addBlock(Block block) {

		RegisterHelper.registerBlock(block);
		totalBlocks++;
	}
}
