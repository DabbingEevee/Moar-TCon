package com.existingeevee.moretcon.inits.misc;

import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.item.ItemBase;
import com.existingeevee.moretcon.other.ModTabs;
import com.existingeevee.moretcon.other.sponge.SpongeRegistry;
import com.existingeevee.moretcon.other.utils.CompatManager;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModSponges {
	public static Item gravitoniumSponge;
	public static Item gravitoniumSpongeTrichromadentium;
	public static Item gravitoniumSpongeAtronium;

	public static void init() {
		gravitoniumSponge = new ItemBase("gravitoniumSponge").setTab(ModTabs.moarTConMisc);
		ModItems.registerItems(gravitoniumSponge);
		
		if (CompatManager.loadMain) {

			gravitoniumSpongeTrichromadentium = SpongeRegistry.getSponge(SpongeRegistry.createSpongeRecipe("trichromadentium", "oreTrichromadentium", new ItemStack(ModItems.ingotTrichromadentium),
					SpongeRegistry.createSpongeStep("iron", slimeknights.tconstruct.library.materials.Material.VALUE_Block * 8),
					SpongeRegistry.createSpongeStep("liquidirradium", slimeknights.tconstruct.library.materials.Material.VALUE_Block * 8),
					SpongeRegistry.createSpongeStep("cobalt", slimeknights.tconstruct.library.materials.Material.VALUE_Block * 8),
					SpongeRegistry.createSpongeStep("obsidian", slimeknights.tconstruct.library.materials.Material.VALUE_Ingot * 32)));

			gravitoniumSpongeAtronium = SpongeRegistry.getSponge(SpongeRegistry.createSpongeRecipe("atronium", "oreAtronium", new ItemStack(ModItems.ingotAtronium),
					SpongeRegistry.createSpongeStep("liquidfusionite", slimeknights.tconstruct.library.materials.Material.VALUE_Block),
					SpongeRegistry.createSpongeStep("liquidgallium", slimeknights.tconstruct.library.materials.Material.VALUE_Block),
					SpongeRegistry.createSpongeStep("liquidrunesteel", slimeknights.tconstruct.library.materials.Material.VALUE_Block * 4),
					SpongeRegistry.createSpongeStep("gold", slimeknights.tconstruct.library.materials.Material.VALUE_Block * 8)));

			
			ModItems.registerItems(gravitoniumSpongeTrichromadentium, gravitoniumSpongeAtronium);
		}

	}
}
