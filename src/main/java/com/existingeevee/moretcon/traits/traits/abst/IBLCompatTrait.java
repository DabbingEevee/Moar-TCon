package com.existingeevee.moretcon.traits.traits.abst;

import net.minecraft.item.ItemStack;

public interface IBLCompatTrait {

	default int onToolCorrode(ItemStack tool, int amount, int newAmount) {
		return newAmount;
	}

	default int onToolUncorrode(ItemStack tool, int amount, int newAmount) {
		return newAmount;
	}
}
