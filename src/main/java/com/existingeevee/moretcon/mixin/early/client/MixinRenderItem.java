package com.existingeevee.moretcon.mixin.early.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.other.SlotRendererRegistry;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mixin(RenderItem.class)
public abstract class MixinRenderItem {

	@SideOnly(Side.CLIENT)
	@Inject(at = @At("HEAD"), method = "renderItemModelIntoGUI", cancellable = true)
	protected void moretcon$HEAD_Inject$renderItemModelIntoGUI(ItemStack stack, int x, int y, IBakedModel bakedmodel, CallbackInfo ci) {
		SlotRendererRegistry.render(stack, x, y, bakedmodel);
	}
	
	@SideOnly(Side.CLIENT)
	@Inject(at = @At("TAIL"), method = "renderItemModelIntoGUI", cancellable = true)
	protected void moretcon$TAIL_Inject$renderItemModelIntoGUI(ItemStack stack, int x, int y, IBakedModel bakedmodel, CallbackInfo ci) {
		SlotRendererRegistry.postRender(stack, x, y, bakedmodel);
	}
}
