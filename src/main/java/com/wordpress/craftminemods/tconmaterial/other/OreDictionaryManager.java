package com.wordpress.craftminemods.tconmaterial.other;

import com.wordpress.craftminemods.tconmaterial.block.ModBlocks;
import com.wordpress.craftminemods.tconmaterial.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryManager {
	public static void init() {
		//Fusionite
		registerOre("blockFusionite", ModBlocks.blockFusionite);
		registerOre("oreFusionite", ModBlocks.oreFusionite);
		registerOre("ingotFusionite", ModItems.ingotFusionite);
		registerOre("nuggetFusionite", ModItems.nuggetFusionite);

	}
	private static void registerOre(String strName, Item... items) {
		for (Item i : items) {
			OreDictionary.registerOre(strName, i);
		}
	}
	
	private static void registerOre(String strName, Block... blocks) {
		for (Block b : blocks) {
			OreDictionary.registerOre(strName, b);
		}
	}
}
