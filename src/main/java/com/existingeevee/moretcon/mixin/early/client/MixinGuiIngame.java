package com.existingeevee.moretcon.mixin.early.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.other.utils.ReequipHack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.item.ItemStack;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

	@Shadow
	Minecraft mc;

	@Shadow
	int remainingHighlightTicks;
	@Shadow
	ItemStack highlightingItemStack = ItemStack.EMPTY;

	@Unique
	private static final ThreadLocal<Boolean> IS_HANDLED = ThreadLocal.withInitial(() -> false);

	@Inject(method = "updateTick()V", at = @At(value = "HEAD"))
	public void moretcon$HEAD_Inject$updateTick(CallbackInfo ci) {
		if (IS_HANDLED.get())
			return;
		IS_HANDLED.set(true);

		EntityPlayerSP player = mc.player;
		mc.player = null;

		((GuiIngame) (Object) this).updateTick();

		mc.player = player;
		IS_HANDLED.set(false);

		if (this.mc.player != null) {
			ItemStack itemstack = this.mc.player.inventory.getCurrentItem();

			if (itemstack.isEmpty()) {
				this.remainingHighlightTicks = 0;
			} else if (ReequipHack.similarStackForActionBar(highlightingItemStack, itemstack)) {
				if (this.remainingHighlightTicks > 0) {
					--this.remainingHighlightTicks;
				}
			} else {
				this.remainingHighlightTicks = 40;
			}

			this.highlightingItemStack = itemstack;
		}
	}

	
}
