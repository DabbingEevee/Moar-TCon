package com.existingeevee.moretcon.other.ingredient;

import java.util.Arrays;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreIngredient;

public class FallbackIngredient extends Ingredient {

	private Ingredient[] ingredients = new Ingredient[0];
	
	public FallbackIngredient(Block lastResort, String... oreIngredients) {
		this(Ingredient.fromStacks(new ItemStack(lastResort)), oreIngredients);
	}
	public FallbackIngredient(Item lastResort, String... oreIngredients) {
		this(Ingredient.fromStacks(new ItemStack(lastResort)), oreIngredients);
	}
	
	public FallbackIngredient(ItemStack lastResort, String... oreIngredients) {
		this(Ingredient.fromStacks(lastResort), oreIngredients);
	}
	
	public FallbackIngredient(Ingredient lastResort, String... oreIngredients) {
		ingredients = new Ingredient[oreIngredients.length + 1];
		int i = 0;
		for (OreIngredient ing : Arrays.stream(oreIngredients).map(OreIngredient::new).toArray(OreIngredient[]::new)) {
			ingredients[i++] = ing;
		}
		ingredients[i++] = lastResort;
	}
	
	public FallbackIngredient(Ingredient... ingredients) {
		this.ingredients = ingredients;
	}

	public ItemStack[] getMatchingStacks() {
		return getFirstValidIngredient().getMatchingStacks();
	}

	public boolean apply(@Nullable ItemStack p_apply_1_) {
		return getFirstValidIngredient().apply(p_apply_1_);
	}

	public IntList getValidItemStacksPacked() {
		return getFirstValidIngredient().getValidItemStacksPacked();
	}

	private Ingredient getFirstValidIngredient() {
		for (Ingredient i : ingredients) {
			if (i.getMatchingStacks().length > 0) {
				return i;
			}
		}
		return Ingredient.EMPTY;
	}
	
	public boolean isSimple() {
		return getFirstValidIngredient().isSimple();
	}
}
