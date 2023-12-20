package com.existingeevee.moretcon.particle;

import com.existingeevee.moretcon.block.blocktypes.unique.VoidConductor;
import com.existingeevee.moretcon.inits.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.particle.ParticleRedstone;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleBeamDust extends ParticleRedstone {

	public ParticleBeamDust(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float scale, int life,
			float red, float green, float blue, float alpha) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, scale, red, green, blue);
		this.particleAlpha = alpha;
		if (life >= 0)
			this.particleMaxAge = life;
		this.commonSetup();
	}

	public ParticleBeamDust(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float p_i46349_8_,
			float p_i46349_9_, float p_i46349_10_, float alpha) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i46349_8_, p_i46349_9_, p_i46349_10_);
		this.particleAlpha = alpha;
		this.commonSetup();

	}

	private void commonSetup() {
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.canCollide = false;
		this.particleGravity = 0;

	}

	@Override
	public int getBrightnessForRender(float partialTick) {
		float f = 1;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		int i = super.getBrightnessForRender(partialTick);
		int j = i & 255;
		int k = i >> 16 & 255;
		j = j + (int) (f * 15.0F * 16.0F);

		if (j > 240) {
			j = 240;
		}

		return j | k << 16;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		Block block = this.world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).getBlock();

		boolean flag = block == ModBlocks.blockVoidColumn || block instanceof VoidConductor;
		boolean flag2 = !flag && block.getDefaultState().getMaterial().blocksMovement();

		if (this.particleAge++ >= this.particleMaxAge || flag2) {
			this.setExpired();
		}

		if (!flag) {
			this.particleAge += 119;
		}

		this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
		this.move(this.motionX, this.motionY, this.motionZ);
	}


	public void addMotion(double x, double y, double z) {
		this.motionX += x;
		this.motionY += y;
		this.motionZ += z;
	}

}
