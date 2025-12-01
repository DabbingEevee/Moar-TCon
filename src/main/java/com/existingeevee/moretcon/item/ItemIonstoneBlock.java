package com.existingeevee.moretcon.item;

import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.other.ICustomSlotRenderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemIonstoneBlock extends ItemBlock implements ICustomSlotRenderer {

	public ItemIonstoneBlock(Block block) {
		super(block);
	}

	@Override
	public boolean shouldRender(ItemStack stack) {
		return ((ICustomSlotRenderer) ModItems.gemIonstone).shouldRender(stack);
	}
	
	@Override
	public void render(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
		((ICustomSlotRenderer) ModItems.gemIonstone).render(stack, x, y, bakedmodel);
	}

	@Override
	public void postRender(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
		((ICustomSlotRenderer) ModItems.gemIonstone).postRender(stack, x, y, bakedmodel);
	}
}
