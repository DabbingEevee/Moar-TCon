package com.existingeevee.moretcon.block.ore;

import com.existingeevee.moretcon.block.blocktypes.BlockEtheralBase;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockEtheralOreMetal extends BlockEtheralBase {

	Item toDrop;
	Item smeltResult;
	int dropAmount;

	public BlockEtheralOreMetal(String name, int harvest) {
		this(name, 1, null);
	}

	public BlockEtheralOreMetal(String name, int harvest, Item toDrop) {
		super(name, Material.ROCK, harvest);
		this.toDrop = toDrop;
	}

	public ItemStack getOreDrop() {
		return new ItemStack(this.toDrop);
	}

}