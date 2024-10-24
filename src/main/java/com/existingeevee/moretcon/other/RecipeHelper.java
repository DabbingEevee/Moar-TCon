package com.existingeevee.moretcon.other;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.GameData;

public class RecipeHelper {

	public static IRecipe create9x9CompressRecipe(String name, Ingredient in, ItemStack out) {
		out = out.copy();
		out.setCount(1);
		IRecipe recipe = new ShapelessRecipes(
				name,
				out.copy(),
				NonNullList.withSize(9, in)).setRegistryName(name);
		return recipe;
	}

	public static IRecipe create9x9DecompressRecipe(String name, Ingredient in, ItemStack out) {
		out = out.copy();
		out.setCount(9);
		IRecipe recipe = new ShapelessRecipes(
				name,
				out.copy(),
				NonNullList.withSize(1, in)).setRegistryName(name);
		return recipe;
	}

	public static IRecipe[] create9x9BlockIngotNugget(String name, ItemStack in, ItemStack ii, ItemStack ib) {
		OreIngredient n = new OreIngredient("nugget" + capitalise(name));
		OreIngredient i = new OreIngredient("ingot" + capitalise(name));
		OreIngredient b = new OreIngredient("block" + capitalise(name));

		IRecipe[] recipes = new IRecipe[4];

		recipes[0] = create9x9DecompressRecipe(name.toLowerCase() + "_ingot_to_nugget", i, in);
		recipes[1] = create9x9CompressRecipe(name.toLowerCase() + "_nugget_to_ingot", n, ii);

		recipes[2] = create9x9DecompressRecipe(name.toLowerCase() + "_block_to_ingot", b, ii);
		recipes[3] = create9x9CompressRecipe(name.toLowerCase() + "_ingot_to_block", i, ib);
		return recipes;
	}

	@SafeVarargs
	public static IRecipe createRecipe(String name, ItemStack out, String[] recipe, final Pair<Character, Ingredient>... key) {
		Map<Character, Ingredient> map = new HashMap<>();
		Arrays.stream(key).forEach(p -> map.put(p.getKey(), p.getValue()));

		if (recipe.length > 3 || recipe.length <= 0 || !allEqualLength(recipe) || recipe[0].length() <= 0) {
			throw new IllegalArgumentException("invalid length for recipe");
		}

		NonNullList<Ingredient> list = NonNullList.create();

		for (String s : recipe) {
			for (char c : s.toCharArray()) {
				list.add(map.getOrDefault(c, Ingredient.EMPTY));
			}
		}

		return new ShapedRecipes(name, recipe[0].length(), recipe.length, list, out).setRegistryName(GameData.checkPrefix(name, true));
	}

	private static boolean allEqualLength(String[] array) {
		int i;
		for (i = 0; i < array.length - 1; i++) {
			if (array[i].length() != array[i + 1].length()) {
				return false;
			}
		}
		return true;
	}

	@SafeVarargs
	public static IRecipe createRecipe(String name, ItemStack out, final Pair<Integer, Ingredient>... values) {
		if (values.length > 9 || values.length <= 0) {
			throw new IllegalArgumentException("invalid length for recipe");
		}

		NonNullList<Ingredient> list = NonNullList.create();

		for (Pair<Integer, Ingredient> s : values) {
			for (int i = 0; i < s.getLeft(); i++) {
				list.add(s.getRight() == null ? Ingredient.EMPTY : s.getRight());
			}
		}

		return new ShapelessRecipes(name, out, list).setRegistryName(GameData.checkPrefix(name, true));
	}

	public static String capitalise(String str) {
		if (str == null) {
			return null;
		} else if (str.length() == 0) {
			return "";
		} else {
			return new StringBuilder(str.length()).append(Character.toTitleCase(str.charAt(0))).append(str, 1, str.length()).toString();
		}
	}
}
