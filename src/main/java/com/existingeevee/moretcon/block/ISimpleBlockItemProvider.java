package com.existingeevee.moretcon.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public interface ISimpleBlockItemProvider {

	default public ItemBlock createBlockItem() {
		return new ItemBlock((Block) this);
	}
	
}
