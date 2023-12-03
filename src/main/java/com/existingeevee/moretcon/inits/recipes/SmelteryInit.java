package com.existingeevee.moretcon.inits.recipes;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.inits.ModFluids;
import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.gildedgames.the_aether.items.ItemsAether;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.shared.TinkerFluids;
import twilightforest.entity.passive.EntityTFPenguin;

public class SmelteryInit {
	public static void init() {
		if (CompatManager.loadMain) {
			TinkerRegistry.registerMelting(ModItems.hydrogenRichRedstonePowder, ModFluids.liquidHydrogen, Material.VALUE_Ingot);
			TinkerRegistry.registerMelting(ModItems.rawSteel, TinkerFluids.steel, Material.VALUE_Ingot);
			TinkerRegistry.registerMelting(ModBlocks.oreGravitoniumDense, ModFluids.liquidGravitonium, Material.VALUE_Ore() * 4);
			TinkerRegistry.registerAlloy(new FluidStack(ModFluids.liquidRuneSteel, 1), new FluidStack(TinkerFluids.obsidian, 1), new FluidStack(TinkerFluids.ardite, 9), new FluidStack(TinkerFluids.steel, 9), new FluidStack(TinkerFluids.gold, 2));
			TinkerRegistry.registerAlloy(new FluidStack(ModFluids.liquidFusionLava, 1), new FluidStack(ModFluids.liquidFusionite, 18), new FluidStack(FluidRegistry.LAVA, 125), new FluidStack(TinkerFluids.ardite, 9));
			TinkerRegistry.registerSmelteryFuel(new FluidStack(ModFluids.liquidFusionLava, 16), 1024);
			TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(ModItems.matterReconstructionGel, 1), RecipeMatch.of(new ItemStack(ModItems.hydrogenRichRedstonePowder)), TinkerFluids.knightslime, Material.VALUE_Ingot * 4, true, true));
			TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(ModItems.rawSteel, 1), RecipeMatch.of(new ItemStack(ModItems.carbonPile)), TinkerFluids.iron, Material.VALUE_Ingot, true, true));
		}
		if(CompatManager.twilightforest) {
	    	TinkerRegistry.registerEntityMelting(EntityTFPenguin.class, new FluidStack(ModFluids.liquidPenguinite, 16));
		}
		if(CompatManager.thebetweenlands) {
			TinkerRegistry.registerMelting("gemSulfur", ModFluids.liquidBurningSulfurFlow, Material.VALUE_Ingot);
			TinkerRegistry.registerMelting("blockSulfur", ModFluids.liquidBurningSulfurFlow, Material.VALUE_Block);
			TinkerRegistry.registerMelting("oreSulfur", ModFluids.liquidBurningSulfurFlow, Material.VALUE_Ore());

			TinkerRegistry.registerSmelteryFuel(new FluidStack(ModFluids.liquidBurningSulfurFlow, 8), 4);
			TinkerRegistry.registerSmelteryFuel(new FluidStack(ModFluids.liquidEmber, 1), 64);
		}
		if(CompatManager.aether_legacy) {
			TinkerRegistry.registerMelting(new ItemStack(ItemsAether.valkyrie_boots, 1, 0), ModFluids.liquidValkyrieMetal, Material.VALUE_Ingot * 4);
			TinkerRegistry.registerMelting(new ItemStack(ItemsAether.valkyrie_helmet, 1, 0), ModFluids.liquidValkyrieMetal, Material.VALUE_Ingot * 5);
			TinkerRegistry.registerMelting(new ItemStack(ItemsAether.valkyrie_leggings, 1, 0), ModFluids.liquidValkyrieMetal, Material.VALUE_Ingot * 7);
			TinkerRegistry.registerMelting(new ItemStack(ItemsAether.valkyrie_chestplate, 1, 0), ModFluids.liquidValkyrieMetal, Material.VALUE_Ingot * 8);
			TinkerRegistry.registerMelting(new ItemStack(ItemsAether.valkyrie_axe, 1, 0), ModFluids.liquidValkyrieMetal, Material.VALUE_Ingot * 3);
			TinkerRegistry.registerMelting(new ItemStack(ItemsAether.valkyrie_lance, 1, 0), ModFluids.liquidValkyrieMetal, Material.VALUE_Ingot * 2);
			TinkerRegistry.registerMelting(new ItemStack(ItemsAether.valkyrie_pickaxe, 1, 0), ModFluids.liquidValkyrieMetal, Material.VALUE_Ingot * 3);

			TinkerRegistry.registerMelting(new ItemStack(ItemsAether.golden_amber, 1, 0), ModFluids.liquidGoldenAmber, Material.VALUE_Ingot);
		}
	}
}
