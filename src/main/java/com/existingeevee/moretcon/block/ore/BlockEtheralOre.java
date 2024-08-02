package com.existingeevee.moretcon.block.ore;

import java.util.Random;

import com.existingeevee.moretcon.block.blocktypes.BlockEtheralBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockEtheralOre extends BlockEtheralBase {

	public Item toDrop;
	int minDropAmount = 1;
	int maxDropAmount = 0;
	Item smeltResult;

	public BlockEtheralOre(String name, int harvest) {
		this(name, 1, null, 1, 1);
	}

	public BlockEtheralOre(String name, int harvest, Item toDrop) {
		this(name, 1, toDrop, 1, 1);
	}

	public BlockEtheralOre(String name, int harvest, Item toDrop, int dropAmount) {
		this(name, 1, toDrop, dropAmount, dropAmount);
	}

	public BlockEtheralOre(String name, int harvest, Item toDrop, int minDropAmount, int maxDropAmount) {
		super(name, Material.ROCK, harvest);
		this.toDrop = toDrop;
		this.minDropAmount = minDropAmount;
		this.maxDropAmount = maxDropAmount;
		this.canBeReplaced = false;
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		if (entity instanceof net.minecraft.entity.boss.EntityDragon) {
			return false;
		} else if ((entity instanceof EntityWither) || (entity instanceof EntityWitherSkull)) {
			return false;
		}
		return true;
	}

	@Override
	public int quantityDropped(Random random) {
		if (this.minDropAmount > this.maxDropAmount) {
			int i = this.minDropAmount;
			this.minDropAmount = this.maxDropAmount;
			this.maxDropAmount = i;
		}
		return this.minDropAmount + random.nextInt(this.maxDropAmount - this.minDropAmount + 1);
	}

	public ItemStack getOreDrop() {
		return new ItemStack(this.toDrop);
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {
		if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getDefaultState(), random, fortune)) {
			int i = random.nextInt(fortune + 2) - 1;
			if (i < 0) {
				i = 0;
			}
			return this.quantityDropped(random) * (i + 1);
		} else {
			return this.quantityDropped(random);
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return toDrop == null ? Item.getItemFromBlock(this) : toDrop;
	}
}