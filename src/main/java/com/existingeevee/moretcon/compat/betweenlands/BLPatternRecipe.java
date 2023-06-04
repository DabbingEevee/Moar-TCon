package com.existingeevee.moretcon.compat.betweenlands;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolPart;
import thebetweenlands.common.recipe.misc.AnimatorRecipe;

public class BLPatternRecipe extends AnimatorRecipe {

	private ToolPart input;
	private ToolPart output;

	public BLPatternRecipe(ToolPart input, ToolPart output) {
		super(createPatternFromToolPart(input), 64, 64, createPatternFromToolPart(output));
		this.input = input;
		this.output = output;
	}

	private static ItemStack createPatternFromToolPart(ToolPart toolpart) {
		ItemStack stack = new ItemStack(Item.getByNameOrId("tconstruct:pattern"));
		Pattern.setTagForPart(stack, toolpart);
		return stack;
	}

	@Override
	public boolean matchesInput(ItemStack stack) {
		if (stack != null && stack.getItem() instanceof Pattern && stack.getItem().getRegistryName().toString().equals("tconstruct:pattern")) {
			if (stack.serializeNBT().getCompoundTag("tag").getString("PartType").equals(input.getRegistryName().toString())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getRequiredFuel(ItemStack stack) {
		return 64;
	}

	@Override
	public int getRequiredLife(ItemStack stack) {
		return 64;
	}

	@Override
	public Entity getRenderEntity(ItemStack stack) {
		return null;
	}

	@Override
	public ItemStack getResult(ItemStack stack) {
		return Pattern.setTagForPart(stack.copy(), output);
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
