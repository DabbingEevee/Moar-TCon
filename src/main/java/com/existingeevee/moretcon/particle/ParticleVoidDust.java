package com.existingeevee.moretcon.particle;

import java.util.Random;

import net.minecraft.client.particle.ParticleRedstone;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleVoidDust extends ParticleRedstone {

	private boolean inner = false;

	private boolean spinDir = false;
	
	private static final Random rand = new Random();
	
	public ParticleVoidDust(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float scale, int life,
			float red, float green, float blue, float alpha, boolean inner) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, scale, red, green, blue);
		this.particleAlpha = alpha;
		if (life >= 0)
			this.particleMaxAge = life;
		this.commonSetup();
		this.inner = inner;
		if (inner) {
			spinDir = rand.nextBoolean();
		}
	}

	public ParticleVoidDust(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float p_i46349_8_,
			float p_i46349_9_, float p_i46349_10_, float alpha, boolean inner) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i46349_8_, p_i46349_9_, p_i46349_10_);
		this.particleAlpha = alpha;
		this.commonSetup();
		this.inner = inner;
		if (inner) {
			spinDir = rand.nextBoolean();
		}
	}

	private void commonSetup() {
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.canCollide = false;
		this.particleGravity = 0;

		this.particleAngle = (float) (Math.random() * 2 * Math.PI);
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

	private static double blend(double a, double b, double p) {
		return a * (1 - p) + b * p;
	}

	private double[] a = { 0.0001, 0.0001, 0.0001 };
	private double[] b = { 36 / 255., 2 / 255., 173 / 255. };

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge) {
			this.setExpired();
		}

		this.prevParticleAngle = this.particleAngle;
		this.particleAngle += (spinDir ? -1 : 1) * Math.PI / (20 * 5);
		if (!inner) {
			this.particleRed = (float) blend(a[0], b[0], this.particleAge / 1. / this.particleMaxAge);
			this.particleGreen = (float) blend(a[1], b[1], this.particleAge / 1. / this.particleMaxAge);
			this.particleBlue = (float) blend(a[2], b[2], this.particleAge / 1. / this.particleMaxAge);
		}
		this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
		this.move(this.motionX, this.motionY, this.motionZ);
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		if (inner) {
			super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
			return;
		}

		rotationX = 1;
		rotationZ = 0;
		rotationYZ = 0;
		rotationXY = 0;
		rotationXZ = -1;

		float f0 = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge * 32.0F;
		f0 = MathHelper.clamp(f0, 0.0F, 1.0F);

		float f = (float) this.particleTextureIndexX / 16.0F;
		float f1 = f + 0.0624375F;
		float f2 = (float) this.particleTextureIndexY / 16.0F;
		float f3 = f2 + 0.0624375F;
		float f4 = 0.1F * this.particleScale;

		if (this.particleTexture != null) {
			f = this.particleTexture.getMinU();
			f1 = this.particleTexture.getMaxU();
			f2 = this.particleTexture.getMinV();
			f3 = this.particleTexture.getMaxV();
		}

		float f5 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
		float f6 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
		float f7 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);
		int i = this.getBrightnessForRender(partialTicks);
		int j = i >> 16 & 65535;
		int k = i & 65535;
		Vec3d[] avec3d = new Vec3d[] { new Vec3d((double) (-rotationX * f4 - rotationXY * f4), (double) (-rotationZ * f4), (double) (-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double) (-rotationX * f4 + rotationXY * f4), (double) (rotationZ * f4), (double) (-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double) (rotationX * f4 + rotationXY * f4), (double) (rotationZ * f4), (double) (rotationYZ * f4 + rotationXZ * f4)),
				new Vec3d((double) (rotationX * f4 - rotationXY * f4), (double) (-rotationZ * f4), (double) (rotationYZ * f4 - rotationXZ * f4)) };

		if (this.particleAngle != 0.0F) {
			float f8 = this.particleAngle + (this.particleAngle - this.prevParticleAngle) * partialTicks;
			float f9 = MathHelper.cos(f8 * 0.5F);
			float f11 = MathHelper.sin(f8 * 0.5F);
			Vec3d vec3d = new Vec3d(0, (double) f11, 0);

			for (int l = 0; l < 4; ++l) {
				avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((double) (f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale((double) (2.0F * f9)));
			}
		}

		buffer.pos((double) f5 + avec3d[0].x, (double) f6 + avec3d[0].y, (double) f7 + avec3d[0].z).tex((double) f1, (double) f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		buffer.pos((double) f5 + avec3d[1].x, (double) f6 + avec3d[1].y, (double) f7 + avec3d[1].z).tex((double) f1, (double) f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		buffer.pos((double) f5 + avec3d[2].x, (double) f6 + avec3d[2].y, (double) f7 + avec3d[2].z).tex((double) f, (double) f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		buffer.pos((double) f5 + avec3d[3].x, (double) f6 + avec3d[3].y, (double) f7 + avec3d[3].z).tex((double) f, (double) f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
	}

	public void addMotion(double x, double y, double z) {
		this.motionX += x;
		this.motionY += y;
		this.motionZ += z;
	}

}
