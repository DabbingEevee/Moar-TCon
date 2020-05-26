package com.wordpress.craftminemods.tconmaterial.other;

import com.wordpress.craftminemods.tconmaterial.block.ModBlocks;
import com.wordpress.craftminemods.tconmaterial.item.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltingManager {
	public static void init() {
		GameRegistry.addSmelting(new ItemStack(ModBlocks.oreFusionite, (int) (1)), new ItemStack(ModItems.ingotFusionite, (int) (1)), 1F);
	}
}
