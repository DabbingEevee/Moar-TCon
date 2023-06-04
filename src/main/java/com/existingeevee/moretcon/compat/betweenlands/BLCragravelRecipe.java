package com.existingeevee.moretcon.compat.betweenlands;

import com.existingeevee.moretcon.inits.ModBlocks;

import net.minecraft.item.ItemStack;
import thebetweenlands.api.recipes.IPestleAndMortarRecipe;
import thebetweenlands.common.registries.BlockRegistry;

public class BLCragravelRecipe implements IPestleAndMortarRecipe {

	@Override
	public ItemStack getOutput(ItemStack input) {
		return new ItemStack(ModBlocks.blockCragravel);
	}

	@Override
	public ItemStack getInputs() {
		return new ItemStack(BlockRegistry.CRAGROCK);
	}

	@Override
	public boolean matchesOutput(ItemStack stack) {
		return stack.getItem().getRegistryName().equals(ModBlocks.blockCragravel.getRegistryName());
	}

	@Override
	public boolean matchesInput(ItemStack stack) {
		return stack.getItem().getRegistryName().equals(BlockRegistry.CRAGROCK.getRegistryName());
	}
}
