package com.existingeevee.moretcon.fluid;

import java.util.Random;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.block.ISimpleBlockItemProvider;
import com.existingeevee.moretcon.inits.ModFluidBlocks;
import com.existingeevee.moretcon.inits.ModFluids;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluid extends BlockFluidClassic implements ISimpleBlockItemProvider {
	private Random renderRng = new Random();

	public BlockFluid(String name, Fluid fluid, Material material) {
		super(fluid, material);
		setUnlocalizedName(ModInfo.MODID + "." + name.toLowerCase());
		// setRegistryName(name);
		/**
		 * BlockInit.BLOCKS.add(this); ItemInit.ITEMS.add(new
		 * ItemBlock(this).setRegistryName(name));
		 **/
	}

	public static Vec3d color;

	@Override
	public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
		int color = (getFluid() instanceof LiquidFluid) ? ((LiquidFluid) getFluid()).getCColor() : getFluid().getColor();
		if (getFluid().equals(ModFluids.liquidPenguinite)) {
			renderRng.setSeed(("" + (Math.ceil(entity.getPositionVector().x * 10.0) / 10D) + (Math.ceil(entity.getPositionVector().y * 10.0) / 10D) + (Math.ceil(entity.getPositionVector().z * 10.0) / 10D)).hashCode());
			color = (renderRng.nextBoolean() ? 0x000000 : 0xffffff);

		}

		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		return new Vec3d(red, green, blue);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (this.equals(ModFluidBlocks.blockLiquidEmber) && rand.nextBoolean()) {
			worldIn.spawnParticle(EnumParticleTypes.LAVA, true, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), rand.nextFloat() / 10, rand.nextFloat() / 10, rand.nextFloat() / 10);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (entityIn instanceof EntityLivingBase) {
			if (state.getBlock().equals(ModFluidBlocks.blockLiquidGallium)) {
				entityIn.extinguish();
				if (!((EntityLivingBase) entityIn).isPotionActive(MobEffects.FIRE_RESISTANCE))
					((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1, 0, true, false));
			}
		}
	}
}
