package com.existingeevee.moretcon.inits.misc;

import com.existingeevee.moretcon.inits.ModFluids;
import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.item.ItemBase;
import com.existingeevee.moretcon.other.ModTabs;
import com.existingeevee.moretcon.other.sponge.SpongeRegistry;
import com.existingeevee.moretcon.other.utils.CompatManager;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreIngredient;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.shared.TinkerFluids;

public class ModSponges {
	public static Item gravitoniumSponge;
	public static Item gravitoniumSpongeTrichromadentium;
	public static Item gravitoniumSpongeAtronium;
	public static Item gravitoniumSpongeSolarsteel;
	public static Item gravitoniumSpongeValasium;
	
	public static void init() {
		gravitoniumSponge = new ItemBase("gravitoniumSponge").setTab(ModTabs.moarTConMisc);
		ModItems.registerItems(gravitoniumSponge);

		if (CompatManager.loadMain) {

			gravitoniumSpongeTrichromadentium = SpongeRegistry.getSponge(SpongeRegistry.createSpongeRecipe("trichromadentium", "oreTrichromadentium", new ItemStack(ModItems.ingotTrichromadentium), new OreIngredient("gemEnderal"),
					SpongeRegistry.createSpongeStep(() -> TinkerFluids.iron, Material.VALUE_Ingot * 8),
					SpongeRegistry.createSpongeStep(() -> ModFluids.liquidIrradium, Material.VALUE_Ingot * 8),
					SpongeRegistry.createSpongeStep(() -> TinkerFluids.cobalt, Material.VALUE_Ingot * 8),
					SpongeRegistry.createSpongeStep(() -> TinkerFluids.obsidian, Material.VALUE_Ingot * 8)));

			gravitoniumSpongeAtronium = SpongeRegistry.getSponge(SpongeRegistry.createSpongeRecipe("atronium", "oreAtronium", new ItemStack(ModItems.ingotAtronium), new OreIngredient("gemErythynite"), 
					SpongeRegistry.createSpongeStep(() -> ModFluids.liquidGallium, Material.VALUE_Ingot * 2),
					SpongeRegistry.createSpongeStep(() -> ModFluids.liquidFusionite, Material.VALUE_Ingot),
					SpongeRegistry.createSpongeStep(() -> ModFluids.liquidRuneSteel, Material.VALUE_Ingot * 4),
					SpongeRegistry.createSpongeStep(() -> TinkerFluids.gold, Material.VALUE_Ingot * 8)));

			gravitoniumSpongeSolarsteel = SpongeRegistry.getSponge(SpongeRegistry.createSpongeRecipe("solarsteel", "oreSolarsteel", new ItemStack(ModItems.ingotSolsteel), new OreIngredient("gemIgniglomerate"),
					SpongeRegistry.createSpongeStep(() -> ModFluids.liquidFusionite, Material.VALUE_Ingot * 4),
					SpongeRegistry.createSpongeStep(() -> ModFluids.liquidHydrogen, Material.VALUE_Ingot * 8),
					SpongeRegistry.createSpongeStep(() -> TinkerFluids.steel, Material.VALUE_Ingot * 3)).addResultOreDict("oreSolsteel"));

			gravitoniumSpongeValasium = SpongeRegistry.getSponge(SpongeRegistry.createSpongeRecipe("valasium", "oreValasium", new ItemStack(ModItems.ingotValasium), new OreIngredient("ingotSteel"),
					SpongeRegistry.createSpongeStep(() -> ModFluids.liquidFusionite, Material.VALUE_Ingot * 2),
					SpongeRegistry.createSpongeStep(() -> ModFluids.liquidGallium, Material.VALUE_Ingot * 4),
					SpongeRegistry.createSpongeStep(() -> ModFluids.liquidGravitonium, Material.VALUE_Block),
					SpongeRegistry.createSpongeStep(() -> TinkerFluids.cobalt, Material.VALUE_Block * 4),
					SpongeRegistry.createSpongeStep(() -> TinkerFluids.steel, Material.VALUE_Block * 4)));

			ModItems.registerItems(
					gravitoniumSpongeTrichromadentium,
					gravitoniumSpongeAtronium,
					gravitoniumSpongeSolarsteel,
					gravitoniumSpongeValasium
					);
		}

	}
}
