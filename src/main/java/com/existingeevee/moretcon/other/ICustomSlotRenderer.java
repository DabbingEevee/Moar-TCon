package com.existingeevee.moretcon.other;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICustomSlotRenderer {

	@SideOnly(Side.CLIENT)
	default boolean shouldRender(ItemStack stack) {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	void render(ItemStack stack, int x, int y, IBakedModel bakedmodel);
}
