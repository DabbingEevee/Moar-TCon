package com.existingeevee.moretcon.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleFlame;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleIgniglomerateFlame extends ParticleFlame {

	public ParticleIgniglomerateFlame(World worldIn, double x, double y, double z, int life) {
		super(worldIn, x, y, z, 0, 0, 0);
		if (life >= 0)
			this.particleMaxAge = life;
		commonSetup();
	}

	private void commonSetup() {
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.canCollide = false;
		this.particleGravity = 0;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		IBlockState state = this.world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ));

		boolean flag = state.getMaterial() == Material.LAVA;
		boolean flag2 = !flag && state.getMaterial().blocksMovement();

		if (this.particleAge++ >= this.particleMaxAge || flag2) {
			this.setExpired();
		}

		if (!flag) {
			this.particleAge += 119;
		}

		this.move(this.motionX, this.motionY, this.motionZ);
	}

	public void addMotion(double x, double y, double z) {
		this.motionX += x;
		this.motionY += y;
		this.motionZ += z;
	}

}
