package com.existingeevee.moretcon.compat.betweenlands;

import com.existingeevee.moretcon.inits.ModItems;

import net.minecraft.item.ItemStack;
import thebetweenlands.api.recipes.IPestleAndMortarRecipe;
import thebetweenlands.common.registries.ItemRegistry;

public class BLCrushedShockwaveRecipe implements IPestleAndMortarRecipe {

	@Override
	public ItemStack getOutput(ItemStack input) {
		//return ((UniqueMaterial) Materials.materialShockwave).getUniqueToolPart();
		return new ItemStack(ModItems.crushedShockwaveSword);
	}

	@Override
	public ItemStack getInputs() {
		return new ItemStack(ItemRegistry.SHOCKWAVE_SWORD);
	}

	@Override
	public boolean matchesOutput(ItemStack stack) {
		return stack.getItem().getRegistryName().equals(ModItems.crushedShockwaveSword.getRegistryName());

				//stack.getItem().getRegistryName().equals(CrashyBLItems.betweenSwordBlade.getRegistryName()) &&
			//	TinkerUtil.getMaterialFromStack(stack).getIdentifier().equals(Materials.materialShockwave.getIdentifier());
	}

	@Override
	public boolean matchesInput(ItemStack stack) {
		return stack.getItem().getRegistryName().equals(ItemRegistry.SHOCKWAVE_SWORD.getRegistryName());
	}
}
