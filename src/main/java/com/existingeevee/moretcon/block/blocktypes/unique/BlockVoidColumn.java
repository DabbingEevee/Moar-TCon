package com.existingeevee.moretcon.block.blocktypes.unique;

import java.util.Random;

import com.existingeevee.moretcon.block.blocktypes.BlockBase;
import com.existingeevee.moretcon.client.actions.VoidPrismBottomAction;
import com.existingeevee.moretcon.client.actions.VoidPrismTopAction;
import com.existingeevee.moretcon.inits.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockVoidColumn extends BlockBase implements VoidConductor {

	public BlockVoidColumn() {
		super("blockvoidcolumn", Material.ANVIL, 2);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.scheduleUpdate(pos, this, 1); 
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));

		IBlockState above = worldIn.getBlockState(pos.up());
		IBlockState below = worldIn.getBlockState(pos.down());
		
		if (!above.getBlock().isAir(above, worldIn, pos.up()) && !below.getBlock().isAir(below, worldIn, pos.down())) {
			return; //No need to check if both is filled.
		}
		
		boolean topActive = isTopActive(worldIn, pos);
		boolean bottomActive = isBottomActive(worldIn, pos);

		if (topActive && below.getBlock().isAir(below, worldIn, pos.down())) {
			new VoidPrismTopAction().run(worldIn, pos.getX() + 0.5, pos.getY() - .1, pos.getZ() + 0.5, null);
		}

		if (bottomActive && above.getBlock().isAir(above, worldIn, pos.up())) {
			new VoidPrismBottomAction().run(worldIn, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, null);
		}
		
		if (bottomActive && checkForTop(worldIn, pos)) {
			worldIn.setBlockState(pos.up(3), ModBlocks.blockVoidCore.getDefaultState());
		}
	}

	public boolean checkForTop(World worldIn, BlockPos pos) {
		for (int i = 1; i <= 5; i++) {
			BlockPos ipos = new BlockPos(pos.getX(), pos.getY() + i, pos.getZ());

			IBlockState state = worldIn.getBlockState(ipos);
			Block block = state.getBlock();

			if (block == ModBlocks.blockVoidCore && i == 3) { 
				continue;
			} else if (!block.isAir(state, worldIn, ipos)) {
				return false;
			}
		}

		BlockPos ipos = new BlockPos(pos.getX(), pos.getY() + 6, pos.getZ());

		IBlockState state = worldIn.getBlockState(ipos);
		Block block = state.getBlock();

		if (block instanceof BlockVoidColumn) {
			return this.isTopActive(worldIn, ipos);
		}

		return false;
	}

	public boolean isBottomActive(World worldIn, BlockPos pos) {
		int y = pos.getY();

		for (int i = y - 1; i >= 0; i--) {
			BlockPos ipos = new BlockPos(pos.getX(), i, pos.getZ());
			Block block = worldIn.getBlockState(ipos).getBlock();
			if (block instanceof BlockVoidPrismBottom) {
				return ((BlockVoidPrismBottom) block).isActive(worldIn, ipos);
			}
			if (!(block instanceof VoidConductor)) {
				return false;
			}
		}

		return false;
	}

	public boolean isTopActive(World worldIn, BlockPos pos) {
		int y = pos.getY();

		for (int i = y + 1; i <= 255; i++) {
			BlockPos ipos = new BlockPos(pos.getX(), i, pos.getZ());
			Block block = worldIn.getBlockState(ipos).getBlock();
			if (block instanceof BlockVoidPrismTop) {
				return ((BlockVoidPrismTop) block).isActive(worldIn, ipos);
			}
			if (!(block instanceof VoidConductor)) {
				return false;
			}
		}

		return false;
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

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
}
