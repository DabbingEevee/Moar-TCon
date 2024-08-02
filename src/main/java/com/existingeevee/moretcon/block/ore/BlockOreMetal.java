package com.existingeevee.moretcon.block.ore;

import com.existingeevee.moretcon.block.blocktypes.BlockBase;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockOreMetal extends BlockBase {

	Item toDrop;
	Item smeltResult;
	int dropAmount;

	public BlockOreMetal(String name, int harvest) {
		this(name, 1, null);
	}

	public BlockOreMetal(String name, int harvest, Item toDrop) {
		super(name, Material.ROCK, harvest);
		this.toDrop = toDrop;
	}

	public ItemStack getOreDrop() {
		return new ItemStack(this.toDrop);
	}

}