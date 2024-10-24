package com.existingeevee.moretcon.block.blocktypes;

import java.util.Random;

import com.existingeevee.moretcon.inits.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockElectarite extends BlockBase {

	public BlockElectarite(String itemName, Material material, int harvestLevel) {
		super(itemName, material, harvestLevel);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (pos.getY() >= 245) {
			worldIn.scheduleUpdate(pos, this, (int) (Math.random() * 60 + this.tickRate(worldIn)));
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (pos.getY() >= 245) {
			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
			if (worldIn.canSeeSky(pos.up()) && worldIn.isThundering()) {
				worldIn.setBlockToAir(pos);
				worldIn.addWeatherEffect(new EntityLightningBolt(worldIn, pos.getX(), pos.getY(), pos.getZ(), false));
				if (!worldIn.isRemote) {
					int amount = (int) (Math.random() + 1);
					for (int i = 0; i < amount; i++) {
						EntityItem entityitem = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(ModItems.solidLightning));
						entityitem.setDefaultPickupDelay();
						entityitem.setEntityInvulnerable(true);
						worldIn.spawnEntity(entityitem);
					}
				}
			}
		}
	}

	@Override
	public int tickRate(World worldIn) {
		return 200;
	}
}
