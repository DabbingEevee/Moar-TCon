package com.existingeevee.moretcon.block.blocktypes.unique;

import java.util.Random;

import com.existingeevee.moretcon.block.blocktypes.BlockBase;
import com.existingeevee.moretcon.particle.ParticleDustLuminous;
import com.existingeevee.moretcon.particle.ParticleSpawner;
import com.existingeevee.moretcon.particle.ParticleSpawner.ParticleProvider;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockVoidPrismTop extends BlockBase {

	public BlockVoidPrismTop() {
		super("blockvoidprismtop", Material.IRON, 2);
	}

	@SideOnly(Side.CLIENT)
	private static final ParticleProvider outerSupplier = (world, x, y, z) -> {
		ParticleDustLuminous p = new ParticleDustLuminous(world, x, y, z, 5, 1.000f, 0.507f, 0.000f, 0.25f);
		p.addMotion(0, -.45, 0);
		return p;
	};

	@SideOnly(Side.CLIENT)
	private static final ParticleProvider innerSupplier = (world, x, y, z) -> {
		ParticleDustLuminous p = new ParticleDustLuminous(world, x, y, z, 2f, 1.000f, 0.0f, 0.000f, 0.85f);
		p.addMotion(0, -.45, 0);
		return p;
	};

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {

		int time = (int) (5 + Math.round(Math.random() * 10));
		
		Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleSpawner(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, time, outerSupplier));
		Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleSpawner(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, time, innerSupplier));
	}

}
