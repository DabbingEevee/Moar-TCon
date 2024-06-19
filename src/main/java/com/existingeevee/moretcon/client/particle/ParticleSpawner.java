package com.existingeevee.moretcon.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ParticleSpawner extends Particle {

	private ParticleProvider particleSupplier;
	
	public ParticleSpawner(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, int life, ParticleProvider particleSupplier) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0, 0, 0);
		this.particleSupplier = particleSupplier;
		this.particleMaxAge = life;
		this.commonSetup();
		
	}

	private void commonSetup() {
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.canCollide = false;
		this.particleGravity = 0;
	}

	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge) {
			this.setExpired();
		}
		
		Minecraft.getMinecraft().effectRenderer.addEffect(particleSupplier.provide(this.world, this.posX, this.posY, this.posZ));
		
		this.motionY -= 0.04D * (double) this.particleGravity;
		this.move(this.motionX, this.motionY, this.motionZ);
		
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		//does not render
	}
		
	@FunctionalInterface
	public static interface ParticleProvider {
		Particle provide(World world, double x, double y, double z);
	}

}
