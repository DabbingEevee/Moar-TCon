package com.existingeevee.moretcon.client.particle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.entity.entities.EntityPlasmaBolt;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleDragonBreath;
import net.minecraft.client.particle.ParticleEndRod;
import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.client.particle.ParticleFirework.Overlay;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ParticlePlasmaBolt extends Particle {

	private final TextureManager textureManager;
	private static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.MODID, "textures/other/plasma_bolt.png");

	private static final ParticlePortal.Factory PORTAL_FACTORY = new ParticlePortal.Factory();
	private static final ParticleEndRod.Factory END_ROD_FACTORY = new ParticleEndRod.Factory();
	private static final ParticleDragonBreath.Factory BREATH_FACTORY = new ParticleDragonBreath.Factory();

	private EntityPlasmaBolt bolt;

	public ParticlePlasmaBolt(World worldIn, double posXIn, double posYIn, double posZIn, EntityPlasmaBolt bolt) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.canCollide = false;
		this.particleAngle = (float) (Math.random() * Math.PI * 2.0F);
		this.particleScale = (float) (Math.random() * 2 + 2);
		this.textureManager = Minecraft.getMinecraft().getTextureManager();
		this.bolt = bolt;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.move(this.motionX, this.motionY, this.motionZ);

		this.particleAngle += Math.PI / 20;

		for (int i = 0; i < 3; i++)
			Minecraft.getMinecraft().effectRenderer.addEffect(PORTAL_FACTORY.createParticle(-1, world, posX + MiscUtils.randomN1T1() * 0.2, posY + MiscUtils.randomN1T1() * 0.2, posZ + MiscUtils.randomN1T1() * 0.2, 0, 0, 0));
		if (Math.random() < 0.3)
			Minecraft.getMinecraft().effectRenderer.addEffect(END_ROD_FACTORY.createParticle(-1, world, posX, posY, posZ, 0, 0, 0));

		if (bolt == null || !bolt.isEntityAlive()) {
			this.setExpired();
		}
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void setExpired() {
		Constructor<Overlay> __init__$Overlay = ObfuscationReflectionHelper.findConstructor(Overlay.class, World.class, double.class, double.class, double.class);

		for (int i = 0; i < 5; i++)
			Minecraft.getMinecraft().effectRenderer.addEffect(BREATH_FACTORY.createParticle(-1, world, posX, posY, posZ, MiscUtils.randomN1T1() * 0.2, MiscUtils.randomN1T1() * 0.2, MiscUtils.randomN1T1() * 0.2));

		try {
			Minecraft.getMinecraft().effectRenderer.addEffect(__init__$Overlay.newInstance(world, posX, posY, posZ));
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
		}
		
		super.setExpired();
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
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		if (particleScale <= 0)
			return;

		float f = 0;
		float f1 = 1;
		float f2 = 0;
		float f3 = 1;

		float f4 = 0.1F * (this.particleAge >= this.particleMaxAge ? (this.particleScale - 0.25f * partialTicks) : this.particleScale);

		float f5 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
		float f6 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
		float f7 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);
		int i = this.getBrightnessForRender(partialTicks);
		int j = i >> 16 & 65535;
		int k = i & 65535;
		Vec3d[] avec3d = new Vec3d[] { new Vec3d((double) (-rotationX * f4 - rotationXY * f4), (double) (-rotationZ * f4), (double) (-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double) (-rotationX * f4 + rotationXY * f4), (double) (rotationZ * f4), (double) (-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double) (rotationX * f4 + rotationXY * f4), (double) (rotationZ * f4), (double) (rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double) (rotationX * f4 - rotationXY * f4), (double) (-rotationZ * f4), (double) (rotationYZ * f4 - rotationXZ * f4)) };

		if (this.particleAngle != 0.0F) {
			float f8 = this.particleAngle;// + 0.01f * ((float) Math.PI * 2f) * partialTicks;
			float f9 = MathHelper.cos(f8 * 0.5F);
			float f10 = MathHelper.sin(f8 * 0.5F) * (float) cameraViewDir.x;
			float f11 = MathHelper.sin(f8 * 0.5F) * (float) cameraViewDir.y;
			float f12 = MathHelper.sin(f8 * 0.5F) * (float) cameraViewDir.z;
			Vec3d vec3d = new Vec3d((double) f10, (double) f11, (double) f12);

			for (int l = 0; l < 4; ++l) {
				avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((double) (f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale((double) (2.0F * f9)));
			}
		}

		buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		textureManager.bindTexture(TEXTURE);
		buffer.pos((double) f5 + avec3d[0].x, (double) f6 + avec3d[0].y, (double) f7 + avec3d[0].z).tex((double) f1, (double) f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		buffer.pos((double) f5 + avec3d[1].x, (double) f6 + avec3d[1].y, (double) f7 + avec3d[1].z).tex((double) f1, (double) f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		buffer.pos((double) f5 + avec3d[2].x, (double) f6 + avec3d[2].y, (double) f7 + avec3d[2].z).tex((double) f, (double) f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		buffer.pos((double) f5 + avec3d[3].x, (double) f6 + avec3d[3].y, (double) f7 + avec3d[3].z).tex((double) f, (double) f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		Tessellator.getInstance().draw();
	}
}
