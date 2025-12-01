package com.existingeevee.moretcon.inits.recipes;

import org.apache.commons.lang3.tuple.Pair;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.other.BreakApartBoltCoreRecipe;
import com.existingeevee.moretcon.other.RecipeHelper;
import com.existingeevee.moretcon.other.ingredient.IngredientFluidStack;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreIngredient;

public class MiscRecipes {

	public static void init(Register<IRecipe> event) {
		event.getRegistry().register(new BreakApartBoltCoreRecipe().setRegistryName("break_bolt_core"));
		
		event.getRegistry().register(
				RecipeHelper.createRecipe("reforge_station", new ItemStack(ModBlocks.blockReforgeStation),
						new String[] {
								"BSB",
								"RAR",
								"R R"
						},
						Pair.of('S', new OreIngredient("blockSeared")),
						Pair.of('A', Ingredient.fromItem(Item.getItemFromBlock(Blocks.ANVIL))),
						Pair.of('B', Ingredient.fromItem(Item.getItemFromBlock(ModBlocks.blockCobbledBedrock))),
						Pair.of('R', new OreIngredient("brinkstone"))));

		
		event.getRegistry().register(
				RecipeHelper.createRecipe("hydrogenrichredstonepowder", new ItemStack(ModItems.hydrogenRichRedstonePowder, 4),
						new String[] {
							    "111",
							    "101",
							    "111"
						},
						Pair.of('0', new IngredientFluidStack(FluidRegistry.WATER, 1000)),
						Pair.of('1', new OreIngredient("dustRedstone"))
						
						));
		
		event.getRegistry().register(
				RecipeHelper.createRecipe("hydrogenrichredstonepowder2", new ItemStack(ModItems.hydrogenRichRedstonePowder, 18),
						new String[] {
							    " 1 ",
							    "101",
							    " 1 "
						},
						Pair.of('0', new IngredientFluidStack(FluidRegistry.WATER, 1000)),
						Pair.of('1', new OreIngredient("blockRedstone"))
						
						));
	}
}
