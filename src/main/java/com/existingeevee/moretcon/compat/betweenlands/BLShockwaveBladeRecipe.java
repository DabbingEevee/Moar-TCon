package com.existingeevee.moretcon.compat.betweenlands;

import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.inits.ModMaterials;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thebetweenlands.common.recipe.misc.AnimatorRecipe;

public class BLShockwaveBladeRecipe extends AnimatorRecipe {


	public BLShockwaveBladeRecipe() {
		super(new ItemStack(ModItems.crushedShockwaveSword), 64, 128);
	}

	@Override
	public boolean matchesInput(ItemStack stack) {
		return stack.getItem().getRegistryName().equals(ModItems.crushedShockwaveSword.getRegistryName());
	}

	@Override
	public int getRequiredFuel(ItemStack stack) {
		return 64;
	}

	@Override
	public int getRequiredLife(ItemStack stack) {
		return 128;
	}

	@Override
	public Entity getRenderEntity(ItemStack stack) {
		return null;
	}

	@Override
	public ItemStack getResult(ItemStack stack) {
		return ModMaterials.materialShockwave.getUniqueToolPart();
	}

	@Override
	public Class<? extends Entity> getSpawnEntityClass(ItemStack stack) {
		return null;
	}

	@Override
	public ItemStack onAnimated(World world, BlockPos pos, ItemStack stack) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean onRetrieved(World world, BlockPos pos, ItemStack stack) {
		return true;
	}

	@Override
	public boolean getCloseOnFinish(ItemStack stack) {
		return false;
	}

}
