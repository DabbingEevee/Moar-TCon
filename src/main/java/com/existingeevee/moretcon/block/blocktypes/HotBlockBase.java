package com.existingeevee.moretcon.block.blocktypes;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HotBlockBase extends BlockBase {

	public HotBlockBase(String itemName, Material material, int harvestLevel) {

		super(itemName, material, harvestLevel);

		this.setTickRandomly(true);
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
}
