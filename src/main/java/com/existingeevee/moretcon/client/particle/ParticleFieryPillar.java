package com.existingeevee.moretcon.client.particle;

import com.existingeevee.moretcon.ModInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleFieryPillar extends Particle {

	private static final ResourceLocation FIRE_OUTER = new ResourceLocation(ModInfo.MODID, "textures/other/particle/fiery_pillar/outer.png");
	private static final ResourceLocation FIRE_INNER = new ResourceLocation(ModInfo.MODID, "textures/other/particle/fiery_pillar/inner.png");

	double minY = 0;
	double rOff = 0;
	
	public ParticleFieryPillar(World worldIn, double x, double y, double z, double minY) {
		super(worldIn, x, y, z, 0, 0, 0);
		this.minY = minY - 1;
		this.particleMaxAge = 20 + (int) (Math.random() * 20);
		
		this.rOff = Math.random() * 20;
		
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
	}

	@Override
	public int getFXLayer() {
		return 3;
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
		try {
			double interpEntPosX = entityIn.prevPosX + partialTicks * (entityIn.posX - entityIn.prevPosX);
			double interpEntPosY = entityIn.prevPosY + partialTicks * (entityIn.posY - entityIn.prevPosY);
			double interpEntPosZ = entityIn.prevPosZ + partialTicks * (entityIn.posZ - entityIn.prevPosZ);

			double interpParPosX = prevPosX + partialTicks * (posX - prevPosX);
			double interpParPosY = prevPosY + partialTicks * (posY - prevPosY);
			double interpParPosZ = prevPosZ + partialTicks * (posZ - prevPosZ);

			double dx = interpParPosX - interpEntPosX;
			double dy = interpParPosY - interpEntPosY;
			double dz = interpParPosZ - interpEntPosZ;

			double ageRatio = (this.particleAge + partialTicks) / this.particleMaxAge;
			double ageRatioInv = 1 - (this.particleAge + partialTicks) / this.particleMaxAge;
			double mult = Math.max(0, ageRatioInv * ageRatioInv);
			double size = 0.5 + 0.5 * ageRatio;
			
			int lightCoords = this.getBrightnessForRender(partialTicks);

			Minecraft.getMinecraft().getTextureManager().bindTexture(FIRE_INNER);
			renderPillar(dx, dy, dz, partialTicks, posX, posY, posZ, this.particleAge, 0.6, 0.6 * size, minY, lightCoords, (float) mult, rOff);
			Minecraft.getMinecraft().getTextureManager().bindTexture(FIRE_OUTER);
			renderPillar(dx, dy, dz, partialTicks, posX, posY, posZ, this.particleAge, 1, 1 * size, minY, lightCoords, (float) mult, rOff);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static void renderPillar(double dx, double dy, double dz, float partial, double posX1, double posY1, double posZ1, int age, double speedMult, double radMult, double posY2, int lightCoord, float alpha, double rOffset) {
		float length = (float) (posY2 - posY1);

		int lj = lightCoord >> 16 & 65535;
		int lk = lightCoord & 65535;
		        
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(516, 0.003921569F);
		
		float f5 = (float) -((age + partial + rOffset) * 0.01F * speedMult);
		float f6 = (float) (length / 32.0F - ((float) age + partial + rOffset) * 0.01F * speedMult);

		bufferbuilder.begin(5, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

		for (int j = 0; j <= 8; ++j) {
			float cirX = MathHelper.sin((float) (j % 8) * ((float) Math.PI * 2F) / 8.0F) * 0.75F;
			float cirZ = MathHelper.cos((float) (j % 8) * ((float) Math.PI * 2F) / 8.0F) * 0.75F;
	        
			float f9 = (float) (j % 8) / 8.0F;
			bufferbuilder.pos(dx + cirX * radMult, dy + 0, dz + cirZ * radMult).tex(f9, f5).color(1f, 1f, 1f, alpha).lightmap(lj, lk).endVertex();
			bufferbuilder.pos(dx + cirX * radMult, dy + length, dz + cirZ * radMult).tex(f9, f6).color(1f, 1f, 1f, alpha).lightmap(lj, lk).endVertex();
		}

		tessellator.draw();

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1F);
        
	}
}
