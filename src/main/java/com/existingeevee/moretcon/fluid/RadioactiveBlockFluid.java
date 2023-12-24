package com.existingeevee.moretcon.fluid;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.block.ISimpleBlockItemProvider;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class RadioactiveBlockFluid extends BlockFluidClassic implements ISimpleBlockItemProvider {
	public RadioactiveBlockFluid(String name, Fluid fluid, Material material) {
		super(fluid, material);
		this.setUnlocalizedName(ModInfo.MODID + "." + name.toLowerCase());
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (entityIn instanceof EntityLivingBase) {
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 30, 4, false, true));
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 30, 0, false, true));
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 30, 0, false, true));
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 30, 2, false, true));
		}
	}
}
