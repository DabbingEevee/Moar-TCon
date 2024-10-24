package com.existingeevee.moretcon.block.blocktypes.unique;

import java.util.Random;

import com.existingeevee.moretcon.client.actions.VoidCoreAction;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockVoidCore extends Block {

	public BlockVoidCore() {
		super(Material.BARRIER);

		this.setUnlocalizedName(MiscUtils.createNonConflictiveName("blockvoidcore"));
		this.setHarvestLevel("pickaxe", 0);
		this.setBlockUnbreakable();
		this.setResistance(Float.POSITIVE_INFINITY);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.scheduleUpdate(pos, this, 1);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!this.isValid(worldIn, pos)) {
			worldIn.setBlockToAir(pos);
		} else {
			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
			new VoidCoreAction().run(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, null);
		}
	}

	public boolean isValid(World worldIn, BlockPos pos) {
		if (!isAir(worldIn, pos.up(1)) || !isAir(worldIn, pos.up(2)) || !isAir(worldIn, pos.down(1)) || !isAir(worldIn, pos.down(2))) {
			return false;
		}

		int active = 0;

		if (worldIn.getBlockState(pos.up(3)).getBlock() instanceof BlockVoidColumn) {
			BlockVoidColumn block = (BlockVoidColumn) worldIn.getBlockState(pos.up(3)).getBlock();
			if (block.isTopActive(worldIn, pos.up(3))) {
				active += 1;
			}
		}
		if (worldIn.getBlockState(pos.down(3)).getBlock() instanceof BlockVoidColumn) {
			BlockVoidColumn block = (BlockVoidColumn) worldIn.getBlockState(pos.down(3)).getBlock();
			if (block.isBottomActive(worldIn, pos.down(3))) {
				active += 1;
			}
		}
		return active >= 2;
	}

	private static boolean isAir(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock().isAir(worldIn.getBlockState(pos), worldIn, pos);
	}
}
