package com.existingeevee.moretcon.block.blocktypes.unique;

import java.util.Random;

import com.existingeevee.moretcon.block.blocktypes.BlockBase;
import com.existingeevee.moretcon.client.actions.VoidPrismTopAction;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockVoidPrismTop extends BlockBase {

	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 2.3D, 1.0D);

	public BlockVoidPrismTop() {
		super("blockvoidprismtop", Material.ANVIL, 2);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.scheduleUpdate(pos, this, 1);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
		IBlockState below = worldIn.getBlockState(pos.down());
		if (isActive(worldIn, pos) && state.getBlock().isAir(below, worldIn, pos.down()))
			new VoidPrismTopAction().run(worldIn, pos.getX() + 0.5, pos.getY() - .1, pos.getZ() + 0.5, null);
	}

	public boolean isActive(World worldIn, BlockPos pos) {
		return pos.getY() >= 255 && worldIn.provider.getDimension() == -1;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
