package com.existingeevee.moretcon.other;

import net.minecraft.item.ItemStack;

public interface ICustomSlotRenderer {

	default boolean shouldRender(ItemStack stack) {
		return true;
	}
	
	default int renderSize(ItemStack stack) {
		return 16;
	}

	String spriteLocation(ItemStack stack);

	default void preRender(ItemStack stack) {
		
	}

	default void postRender(ItemStack stack) {
		
	}
	
}
