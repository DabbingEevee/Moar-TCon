package com.existingeevee.moretcon.mixin.late.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.existingeevee.moretcon.materials.UniqueMaterial;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.common.item.SharpeningKit;

@Mixin(value = { ToolPart.class, SharpeningKit.class })
public abstract class MixinToolPart {

	@Inject(method = "canUseMaterial", at = @At("HEAD"), remap = false, cancellable = true)
	public void moretcon$HEAD_Inject$canUseMaterial(Material mat, CallbackInfoReturnable<Boolean> ci) {
		if (mat instanceof UniqueMaterial) {
			ToolPart $this = (ToolPart) (Object) this;
			UniqueMaterial unique = (UniqueMaterial) mat;
			ci.setReturnValue(unique.getPartType() == $this);
		}
	}
	
	//we have to do this instead of a refmap bc mixingradle cant handle dependancies super well
	@Inject(method = { "getSubItems", "func_150895_a" }, at = @At("TAIL"), remap = false)
	public void moretcon$TAIL_Inject$getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems, CallbackInfo ci) {
		ToolPart $this = (ToolPart) (Object) this;
		for (int i = 0; i < subItems.size(); i++) {
			Material mat = $this.getMaterial(subItems.get(i));
			if (mat instanceof UniqueMaterial) { //we handle in our own tab
				if ($this instanceof SharpeningKit || tab != CreativeTabs.SEARCH) {
					subItems.remove(i--);
					continue;
				}
			}
		}
	}
}
