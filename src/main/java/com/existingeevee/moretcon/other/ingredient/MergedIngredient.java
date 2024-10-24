package com.existingeevee.moretcon.other.ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class MergedIngredient extends Ingredient {

	private Ingredient[] ingredients = new Ingredient[0];

	private IntList matchingStacksPacked = null;

	public MergedIngredient(Ingredient... ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public ItemStack[] getMatchingStacks() {
		List<ItemStack> stacks = new ArrayList<>();
		for (Ingredient i : ingredients) {
			stacks.addAll(Arrays.asList(i.getMatchingStacks()));
		}
		return stacks.stream().toArray(ItemStack[]::new);
	}

	@Override
	public boolean apply(@Nullable ItemStack p_apply_1_) {
		if (p_apply_1_ == null) {
			return false;
		} else {
			for (Ingredient i : ingredients) {
				if (i.apply(p_apply_1_)) {
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public IntList getValidItemStacksPacked() {
		if (this.matchingStacksPacked == null) {

			List<Integer> list = new ArrayList<>();

			for (Ingredient i : ingredients) {
				list.addAll(i.getValidItemStacksPacked());
			}

			this.matchingStacksPacked = new IntArrayList(list);
			this.matchingStacksPacked.sort(IntComparators.NATURAL_COMPARATOR);
		}

		return this.matchingStacksPacked;
	}

	@Override
	protected void invalidate() {
		this.matchingStacksPacked = null;
	}

	@Override
	public boolean isSimple() {
		boolean isSimple = true;
		for (Ingredient i : ingredients) {
			if (!i.isSimple()) {
				isSimple = false;
				break;
			}
		}
		return isSimple;
	}
}
