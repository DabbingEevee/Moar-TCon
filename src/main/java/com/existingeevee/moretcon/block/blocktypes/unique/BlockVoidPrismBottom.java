package com.existingeevee.moretcon.block.blocktypes.unique;

import java.util.Random;

import com.existingeevee.moretcon.block.blocktypes.BlockBase;
import com.existingeevee.moretcon.client.actions.VoidPrismBottomAction;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockVoidPrismBottom extends BlockBase {

	public BlockVoidPrismBottom() {
		super("blockvoidprismbottom", Material.ANVIL, 2);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.scheduleUpdate(pos, this, 1);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
		IBlockState above = worldIn.getBlockState(pos.up());
		if (isActive(worldIn, pos) && state.getBlock().isAir(above, worldIn, pos.up()))
			new VoidPrismBottomAction().run(worldIn, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5);
	}
	
	public boolean isActive(World worldIn, BlockPos pos) {
		return pos.getY() <= 0 && worldIn.provider.getDimension() == -1;
	}
}
