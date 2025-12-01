package com.existingeevee.moretcon.other;

import com.existingeevee.moretcon.ModInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@FunctionalInterface
public interface ICustomSlotRenderer {

	@SideOnly(Side.CLIENT)
	default boolean shouldRender(ItemStack stack) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	void render(ItemStack stack, int x, int y, IBakedModel bakedmodel);

	@SideOnly(Side.CLIENT)
	default void postRender(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
	}
	
	public static final ResourceLocation CIRCLE_GLOW = new ResourceLocation(ModInfo.MODID, "other/bg_glow_circle");
	public static final ResourceLocation EXTREME_GLOW = new ResourceLocation(ModInfo.MODID, "other/bg_glow_extreme");
	public static final ResourceLocation BIG_CIRCLE_GLOW = new ResourceLocation(ModInfo.MODID, "other/bg_glow_circle_big");
	public static final ResourceLocation OVAL_GLOW = new ResourceLocation(ModInfo.MODID, "other/bg_glow_oval");

	@SideOnly(Side.CLIENT)
	public static void simpleRender(ItemStack stack, int x, int y, IBakedModel bakedmodel, GlowType type, int hex) {
		if (hex == Integer.MIN_VALUE) {
			return;
		}

		float ticks = (float) ((Minecraft.getMinecraft().world.getTotalWorldTime()
				+ Minecraft.getMinecraft().getRenderPartialTicks()) % (40 * Math.PI));

		float mult = (float) (0.5f * Math.cos(0.05f * ticks) + 0.5f);

		float b = 0.25F + 0.5f * mult;

		GlStateManager.color(((hex & 0xFF0000) >> 16) / 255f, ((hex & 0x00FF00) >> 8) / 255f,
				(hex & 0x0000FF) / 255f, b);
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableAlpha();

		GlStateManager.disableLighting();

		TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
		TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite(CIRCLE_GLOW.toString());
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x, y, textureatlassprite, 16, 16);

		if (type != GlowType.CIRCLE) {
			textureatlassprite = texturemap.getAtlasSprite(ModInfo.MODID + ":other/bg_glow_" + type.toString().toLowerCase());
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x - 8, y - 8, textureatlassprite, 32, 32);
		}

		GlStateManager.enableLighting();

		GlStateManager.enableAlpha();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();

		// GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static enum GlowType {
		CIRCLE, OVAL, EXTREME, CIRCLE_BIG;
	}
}
