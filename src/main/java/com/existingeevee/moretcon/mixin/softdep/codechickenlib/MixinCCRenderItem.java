package com.existingeevee.moretcon.mixin.softdep.codechickenlib;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.other.SlotRendererRegistry;

import codechicken.lib.render.item.CCRenderItem;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mixin(CCRenderItem.class)
public abstract class MixinCCRenderItem extends RenderItem {

	public MixinCCRenderItem(TextureManager p_i46552_1_, ModelManager p_i46552_2_, ItemColors p_i46552_3_) {
		super(p_i46552_1_, p_i46552_2_, p_i46552_3_);
	}

	@SideOnly(Side.CLIENT)
	@Inject(at = @At("HEAD"), method = "renderItemModelIntoGUI", cancellable = true)
	protected void moretcon$HEAD_Inject$renderItemModelIntoGUI(ItemStack stack, int x, int y, IBakedModel bakedmodel, CallbackInfo ci) {
		if (!this.isValidModel(bakedmodel)) { // We only have this execute IF chickenlib is handling it
			return; // Otherwise its handled in the vanilla RenderItem
		}

		SlotRendererRegistry.render(stack, x, y, bakedmodel);
	}

	@SideOnly(Side.CLIENT)
	@Inject(at = @At("TAIL"), method = "renderItemModelIntoGUI", cancellable = true)
	protected void moretcon$TAIL_Inject$renderItemModelIntoGUI(ItemStack stack, int x, int y, IBakedModel bakedmodel, CallbackInfo ci) {
		if (!this.isValidModel(bakedmodel)) { // We only have this execute IF chickenlib is handling it
			return; // Otherwise its handled in the vanilla RenderItem
		}
		
		SlotRendererRegistry.postRender(stack, x, y, bakedmodel);
	}
	
	@Shadow(remap = false)
	abstract boolean isValidModel(IBakedModel model);
}
