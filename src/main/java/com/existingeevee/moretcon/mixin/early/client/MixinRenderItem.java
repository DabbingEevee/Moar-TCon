package com.existingeevee.moretcon.mixin.early.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.other.ICustomSlotRenderer;
import com.existingeevee.moretcon.other.SlotRendererRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mixin(RenderItem.class)
public abstract class MixinRenderItem {

	@SideOnly(Side.CLIENT)
	@Inject(at = @At("HEAD"), method = "renderItemModelIntoGUI", cancellable = true)
	protected void moretcon$HEAD_Inject$renderItemModelIntoGUI(ItemStack stack, int x, int y, IBakedModel bakedmodel,
			CallbackInfo ci) {

		ICustomSlotRenderer renderer = SlotRendererRegistry.get(stack.getItem());

		if (renderer != null) {
			if (!renderer.shouldRender(stack)) {
				renderer = null;
			}
		}

		if (renderer == null && stack.getItem() instanceof ICustomSlotRenderer)
			renderer = (ICustomSlotRenderer) stack.getItem();

		if (renderer != null) {
			if (!renderer.shouldRender(stack))
				return;

			int renderSize = renderer.renderSize(stack);

			renderer.preRender(stack);

			GlStateManager.disableDepth();
			GlStateManager.depthMask(false);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.disableAlpha();

			TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
			TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite(renderer.spriteLocation(stack));
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x - (renderSize - 16) / 2, y - (renderSize - 16) / 2, textureatlassprite, renderSize, renderSize);

			GlStateManager.enableAlpha();
			GlStateManager.depthMask(true);
			GlStateManager.enableDepth();

			renderer.postRender(stack);
		}
	}
}
