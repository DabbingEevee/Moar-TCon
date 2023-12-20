package com.existingeevee.moretcon.block.ore;

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

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		if (entity instanceof net.minecraft.entity.boss.EntityDragon) {
			return false;
		} else if ((entity instanceof EntityWither) || (entity instanceof EntityWitherSkull)) {
			return false;
		}
		return true;
	}

}