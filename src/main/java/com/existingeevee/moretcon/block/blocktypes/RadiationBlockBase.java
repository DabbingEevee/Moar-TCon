package com.existingeevee.moretcon.block.blocktypes;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RadiationBlockBase extends BlockBase {

	public RadiationBlockBase(String itemName, Material material, int harvestLevel) {

		super(itemName, material, harvestLevel);
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof EntityLivingBase) {
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 30, 4, false, true));
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 30, 0, false, true));
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 30, 0, false, true));
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 30, 2, false, true));
		}
	}
}
