package com.existingeevee.moretcon.traits.traits.abst;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public abstract class NumberTrackerTrait extends AdditionalDisplayTrait {

	public NumberTrackerTrait(String identifier, int color, int lvlmax, int lvl) {
		super(identifier, color, lvlmax, lvl);
	}
	
	public NumberTrackerTrait(String identifier, int color) {
		super(identifier, color);
	}

	public abstract int getNumberMax(ItemStack stack);

	public int getNumber(ItemStack stack) {
		NBTTagCompound comp = stack.getOrCreateSubCompound(this.getModifierIdentifier());
		return comp.hasKey("remaining", NBT.TAG_INT) ? comp.getInteger("remaining") : 0;
	}

	public int setNumber(ItemStack stack, int amount) {
		NBTTagCompound comp = stack.getOrCreateSubCompound(this.getModifierIdentifier());
		comp.setInteger("remaining", amount);
		return amount;
	}
	
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
