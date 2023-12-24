package com.existingeevee.moretcon.fluid;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.block.ISimpleBlockItemProvider;
import com.existingeevee.moretcon.other.fires.CustomFireEffect;
import com.existingeevee.moretcon.other.fires.CustomFireHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class CustomFireBlockFluid extends BlockFluidClassic implements ISimpleBlockItemProvider {
	DamageSource source = DamageSource.LAVA;
	float damage = 4;
	boolean bypassFireImmunity = false;

	final CustomFireEffect eff;

	public CustomFireBlockFluid(String name, Fluid fluid, CustomFireEffect eff, Material material) {
		super(fluid, material);
		this.setUnlocalizedName(ModInfo.MODID + "." + name.toLowerCase());
		this.eff = eff;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	public void handle(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) {
			return;
		}
		if (!entity.isImmuneToFire() || bypassFireImmunity) {
			entity.attackEntityFrom(source, 4.0F);
			CustomFireHelper.setAblaze(entity, eff, 15 * 20);
		}
	}

	public DamageSource getDamageSource() {
		return source;
	}

	public CustomFireBlockFluid setSource(DamageSource ds) {
		this.source = ds;
		return this;
	}

	public float getDamage() {
		return damage;
	}

	public CustomFireBlockFluid setDamage(float damage) {
		this.damage = damage;
		return this;
	}
}
