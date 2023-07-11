package com.existingeevee.moretcon.inits.recipes;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.other.BiValue;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.ConfigHandler;
import com.existingeevee.moretcon.other.utils.RegisterHelper;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thebetweenlands.common.item.misc.ItemMisc.EnumItemMisc;
import twilightforest.item.TFItems;

public class FurnaceInit {
	public static void init() {
		
		for (BiValue<Block, ItemStack> val : RegisterHelper.oreDrops) {
			GameRegistry.addSmelting(val.getA(), val.getB(), 10);
		}
				
		if (CompatManager.twilightforest) {
			if (ConfigHandler.shouldLoadDust && ConfigHandler.shouldLoadDustForCompatability) {
				GameRegistry.addSmelting(new ItemStack(ModItems.dustKnightmetal, 1), new ItemStack(TFItems.knightmetal_ingot, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustFiery, 1), new ItemStack(TFItems.fiery_ingot, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustPenguinite, 1), new ItemStack(ModItems.ingotPenguinite, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustIronwood, 1), new ItemStack(TFItems.ironwood_ingot, 1), 0F);
			}
		}
		if (CompatManager.loadMain) {
			if (ConfigHandler.shouldLoadDust) {
				GameRegistry.addSmelting(new ItemStack(ModItems.dustFusionite, 1), new ItemStack(ModItems.ingotFusionite, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustIrradium, 1), new ItemStack(ModItems.ingotIrradium, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustSolarSteel, 1), new ItemStack(ModItems.ingotSolsteel, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustGallium, 1), new ItemStack(ModItems.ingotGallium, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustRuneSteel, 1), new ItemStack(ModItems.ingotRuneSteel, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustGravitonium, 1), new ItemStack(ModItems.ingotGravitonium, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustTrichromadentium, 1), new ItemStack(ModItems.ingotTrichromadentium, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustAtronium, 1), new ItemStack(ModItems.ingotAtronium, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustSteel, 1), new ItemStack(ModItems.ingotSteel, 1), 0F);
			}
			GameRegistry.addSmelting(new ItemStack(ModItems.rawSteel, 1), new ItemStack(ModItems.ingotSteel, 1), 0F);
			GameRegistry.addSmelting(new ItemStack(ModBlocks.oreGravitoniumDense, 1), new ItemStack(ModItems.ingotGravitonium, 4), 0F);
		}
		if (CompatManager.aether_legacy) {
			if (ConfigHandler.shouldLoadDust && ConfigHandler.shouldLoadDustForCompatability) {
				GameRegistry.addSmelting(new ItemStack(ModItems.dustArkenium, 1), new ItemStack(ModItems.ingotArkenium, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustValkyrieMetal, 1), new ItemStack(ModItems.ingotValkyrieMetal, 1), 0F);
			}
		}
		if (CompatManager.thebetweenlands) {
			if (ConfigHandler.shouldLoadDust && ConfigHandler.shouldLoadDustForCompatability) {
				GameRegistry.addSmelting(new ItemStack(ModItems.dustSwampSteel, 1), new ItemStack(ModItems.ingotSwampSteel, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustOctine, 1), new ItemStack(ModItems.ingotSwampSteel, 1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustSyrmorite, 1), EnumItemMisc.SYRMORITE_INGOT.create(1), 0F);
				GameRegistry.addSmelting(new ItemStack(ModItems.dustRotiron, 1), new ItemStack(ModItems.ingotRotiron, 1), 0F);
			}
		}
	}
}
