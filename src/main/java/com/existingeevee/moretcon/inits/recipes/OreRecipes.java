package com.existingeevee.moretcon.inits.recipes;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent.Register;

public class OreRecipes {

	public static void init(Register<IRecipe> event) {
		Misc.registerBlockNuggetIngotRecipeOre("Rotiron", event);
		Misc.registerBlockNuggetIngotRecipeOre("Irradium", event);
		Misc.registerBlockNuggetIngotRecipeOre("Gallium", event);
		Misc.registerBlockNuggetIngotRecipeOre("Atronium", event);
		Misc.registerBlockNuggetIngotRecipeOre("Steel", event);
		Misc.registerBlockNuggetIngotRecipeOre("Trichromadentium", event);
		Misc.registerBlockNuggetIngotRecipeOre("Gravitonium", event);
		Misc.registerBlockNuggetIngotRecipeOre("Solarsteel", event);
		Misc.registerBlockNuggetIngotRecipeOre("Runesteel", event);
		Misc.registerBlockNuggetIngotRecipeOre("Ebonite", event);
		
		Misc.register9x9Recipes("gemBloodstone", "blockBloodstone", event);
		Misc.register9x9Recipes("gemEchostone", "blockEchostone", event);
		Misc.register9x9Recipes("gemIgniglomerate", "blockIgniglomerate", event);
		Misc.register9x9Recipes("gemVoidSpar", "blockVoidSpar", event);
		Misc.register9x9Recipes("gemGarstone", "blockGarstone", event);
		Misc.register9x9Recipes("gemEnderal", "blockEnderal", event);
		Misc.register9x9Recipes("gemErythynite", "blockErythynite", event);
		Misc.register9x9Recipes("gemEtherstone", "blockEtherstone", event);
		
		Misc.registerBlockNuggetIngotRecipeOre("Penguinite", event);
		
		
		Misc.registerBlockNuggetIngotRecipeOre("Swampsteel", event);
		Misc.registerBlockNuggetIngotRecipeOre("Rotiron", event);

		
		Misc.registerBlockNuggetIngotRecipeOre("ValkyrieMetal", event);
		Misc.registerBlockNuggetIngotRecipeOre("Arkenium", event);
		
		Misc.register9x9Recipes("gemGravitite", "blockGravitite", event);
	}

}
