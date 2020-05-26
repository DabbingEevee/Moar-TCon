package com.wordpress.craftminemods.tconmaterial.item;

import java.util.List;

import com.wordpress.craftminemods.tconmaterial.other.Logging;
import com.wordpress.craftminemods.tconmaterial.other.RegisterHelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModItems {
	public static int totalItems;
/*---------------------------------------*/
	public static Item nuggetFusionite = new ItemBase("nuggetFusionite");

	public static Item ingotFusionite = new ItemBase("ingotFusionite");
/*---------------------------------------*/
	
/*---------------------------------------*/	
	
	private static void registerItems(Item... items) {
		for (Item i : items) {
			ModItems.addItem(i);
		}
	}
	public static void init() {
		ModItems.registerItems(
/**-------------------------------------**/
				ingotFusionite,	
/**-------------------------------------**/
				nuggetFusionite
/**-------------------------------------**/
				);
		Logging.Log("Loaded a total of " + totalItems + " different items.");
	}
	

	private static void addItem(Item item) {
		RegisterHelper.registerItem(item);
		totalItems++;
	}
}
