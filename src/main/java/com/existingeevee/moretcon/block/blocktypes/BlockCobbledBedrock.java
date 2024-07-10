package com.existingeevee.moretcon.block.blocktypes;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCobbledBedrock extends BlockBase {
	public BlockCobbledBedrock(String itemName, Material material, int harvestLevel) {
		super(itemName, material, harvestLevel);
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		if (entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityWitherSkull) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isToolEffective(String type, IBlockState state) {
		return false;
	}
}
