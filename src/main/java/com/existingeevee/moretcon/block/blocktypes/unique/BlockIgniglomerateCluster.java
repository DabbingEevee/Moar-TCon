package com.existingeevee.moretcon.block.blocktypes.unique;

import java.util.Random;

import com.existingeevee.moretcon.block.ore.BlockOre;
import com.existingeevee.moretcon.client.actions.IgniglomerateAction;
import com.existingeevee.moretcon.inits.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockIgniglomerateCluster extends BlockOre {

	public BlockIgniglomerateCluster() {
		super("oreIgniglomerate", 5, ModItems.gemIgniglomerate, 1);
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if (!entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase) entityIn)) {
			entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 4.0F);
		} else if ((!entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase && EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase) entityIn))) {
			entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
		}

		super.onEntityWalk(worldIn, pos, entityIn);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.scheduleUpdate(pos, this, 1);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
		IBlockState above = worldIn.getBlockState(pos.up());
		if (above.getMaterial() == Material.LAVA && !worldIn.isRemote) {
			IgniglomerateAction.INSTANCE.run(worldIn, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, null);
		}
	}
}
