package com.existingeevee.moretcon.item;

import com.existingeevee.moretcon.other.ICustomSlotRenderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemVacuuiteBlock extends ItemBlock implements ICustomSlotRenderer {

	public ItemVacuuiteBlock(Block block) {
		super(block);
	}

	@Override
	public boolean shouldRender(ItemStack stack) {
		return true;
	}
	
	@Override
	public void render(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
		ICustomSlotRenderer.simpleRender(stack, x, y, bakedmodel, GlowType.CIRCLE_BIG, 0x300a6a);
	}
}
