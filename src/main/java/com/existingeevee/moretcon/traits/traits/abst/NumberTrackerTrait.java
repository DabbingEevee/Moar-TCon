package com.existingeevee.moretcon.traits.traits.abst;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public abstract class NumberTrackerTrait extends AdditionalDisplayTrait {

	protected boolean showNumberRemaining = true;

	public NumberTrackerTrait(String identifier, int color) {
		super(identifier, color);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public abstract int getNumberMax(ItemStack stack);

	public abstract int getNumber(ItemStack stack);

	public abstract int setNumber(ItemStack stack, int amount);

	public int addNumber(ItemStack stack, int amount) {
		return setNumber(stack, Math.max(0, Math.min(this.getNumberMax(stack), getNumber(stack) + amount)));
	}

	public int removeNumber(ItemStack stack, int amount) {
		return addNumber(stack, -amount);
	}

	@Override
	public String getStringToRender(ItemStack tool) {
		return getNumber(tool) + "/" + getNumberMax(tool);
	}
}
