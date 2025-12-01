package com.existingeevee.moretcon.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.WeakHashMap;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.ICustomSlotRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMDGel extends ItemShakeRender implements ICustomSlotRenderer {

	@SideOnly(Side.CLIENT)
	WeakHashMap<ItemStack, MDGelGuiParticleRenderer> renderers = new WeakHashMap<>();

	public ItemMDGel() {
		super("matterDeconstructionGel");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void render(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
		renderers.computeIfAbsent(stack, s -> new MDGelGuiParticleRenderer()).render(x, y);;
		super.render(stack, x, y, bakedmodel);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void postRender(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
		super.postRender(stack, x, y, bakedmodel);
	}

	protected static final Random rand = new Random();

	@SideOnly(Side.CLIENT)
	public static class MDGelGuiParticleRenderer extends Gui {
		public static final ResourceLocation DOT = new ResourceLocation(ModInfo.MODID, "other/dot");

		List<MDGelGuiParticle> list = new ArrayList<>();
		long gtToSpawnNext = -1;

		public void render(int x, int y) {
			long cTime = Minecraft.getSystemTime();
			if (cTime > gtToSpawnNext) {
				for (int i = 0; i < 5; i++)
					list.add(new MDGelGuiParticle());
				gtToSpawnNext = cTime + rand.nextInt(75) + 50;
			}

			for (int i = 0; i < list.size(); i++) {
				MDGelGuiParticle particle = list.get(i);

				if (cTime > particle.gtStart + particle.lifeMax) {
					list.remove(i--);
				} else {

					GlStateManager.pushMatrix();
					GlStateManager.translate(x + 8, y + 8, 0);
					GlStateManager.rotate((float) (particle.theta / Math.PI * 180), 0, 0, 1);

					float lifeLived = cTime - particle.gtStart;

					float ageRatio = (lifeLived / particle.lifeMax);
					
					float xOff = 12 * ageRatio;
					float yOff = particle.amp * (float) Math.sin(xOff) * 8;

					renderDot(xOff, yOff, lifeLived, Math.cos(ageRatio * ageRatio * ageRatio));
										
					GlStateManager.popMatrix();

				}
			}
		}

		public void renderDot(double x, double y, double r, double s) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

			GlStateManager.disableDepth();
			GlStateManager.depthMask(false);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.disableAlpha();

			GlStateManager.disableLighting();

			TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
			TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite(DOT.toString());
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			GlStateManager.translate(x, y, 0);
			GlStateManager.pushMatrix();
			GlStateManager.rotate((float) r, 0, 0, 1);
			GlStateManager.scale(s, s, 0);
			this.drawTexturedModalRect(-0.5, -0.5, textureatlassprite, 1, 1);
			GlStateManager.popMatrix();

			GlStateManager.enableLighting();

			GlStateManager.enableAlpha();
			GlStateManager.depthMask(true);
			GlStateManager.enableDepth();
		}

		public void drawTexturedModalRect(double xCoord, double yCoord, TextureAtlasSprite textureSprite, double widthIn, double heightIn) {
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.pos((double) (xCoord + 0), (double) (yCoord + heightIn), (double) this.zLevel).tex((double) textureSprite.getMinU(), (double) textureSprite.getMaxV()).endVertex();
			bufferbuilder.pos((double) (xCoord + widthIn), (double) (yCoord + heightIn), (double) this.zLevel).tex((double) textureSprite.getMaxU(), (double) textureSprite.getMaxV()).endVertex();
			bufferbuilder.pos((double) (xCoord + widthIn), (double) (yCoord + 0), (double) this.zLevel).tex((double) textureSprite.getMaxU(), (double) textureSprite.getMinV()).endVertex();
			bufferbuilder.pos((double) (xCoord + 0), (double) (yCoord + 0), (double) this.zLevel).tex((double) textureSprite.getMinU(), (double) textureSprite.getMinV()).endVertex();
			tessellator.draw();
		}
	}

	@SideOnly(Side.CLIENT)
	public static class MDGelGuiParticle {
		float rRot0 = (float) (rand.nextDouble() * Math.PI * 2);
		float theta = (float) (rand.nextDouble() * Math.PI * 2);
		float amp = (float) (rand.nextDouble() * 0.2);
		int lifeMax = rand.nextInt(1000) + 250;
		long gtStart = Minecraft.getSystemTime();
	}

}
