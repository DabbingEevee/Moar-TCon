package com.existingeevee.moretcon.other;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;

public interface ICustomSlotRenderer {

	default boolean shouldRender(ItemStack stack) {
		return true;
	}

	void render(ItemStack stack, int x, int y, IBakedModel bakedmodel);
}
