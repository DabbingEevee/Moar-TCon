package com.existingeevee.moretcon.compat.betweenlands;

import java.util.Random;

import com.existingeevee.moretcon.inits.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thebetweenlands.common.recipe.misc.AnimatorRecipe;

public class BLSwampSteelRecipe extends AnimatorRecipe {


	public BLSwampSteelRecipe() {
		super(new ItemStack(ModItems.ingotRotiron), 1, 4);
	}

	@Override
	public boolean matchesInput(ItemStack stack) {
		return stack.getItem().getRegistryName().equals(ModItems.ingotRotiron.getRegistryName());
	}

	@Override
	public int getRequiredFuel(ItemStack stack) {
		return 2;
	}

	@Override
	public int getRequiredLife(ItemStack stack) {
		return 4;
	}

	@Override
	public Entity getRenderEntity(ItemStack stack) {
		return null;
	}

	private static final Random rand = new Random();

	@Override
	public ItemStack getResult(ItemStack stack) {
		return new ItemStack(ModItems.nuggetSwampSteel, rand.nextInt(9) + 1);
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
