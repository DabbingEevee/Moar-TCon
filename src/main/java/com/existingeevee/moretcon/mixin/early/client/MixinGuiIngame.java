package com.existingeevee.moretcon.mixin.early.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.existingeevee.moretcon.other.utils.ReequipHack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.item.ItemStack;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

	@Shadow
	Minecraft mc;
	@Shadow
	int remainingHighlightTicks;
	@Shadow
	ItemStack highlightingItemStack;
	@Shadow
    protected int updateCounter;
	@Shadow
    protected int overlayMessageTime;
	@Shadow
    protected int titlesTimer;
	@Shadow
    protected String displayedTitle;
	@Shadow
    protected String displayedSubTitle;

	@Overwrite
	public void updateTick() {
		if (this.overlayMessageTime > 0) {
			--this.overlayMessageTime;
		}

		if (this.titlesTimer > 0) {
			--this.titlesTimer;

			if (this.titlesTimer <= 0) {
				this.displayedTitle = "";
				this.displayedSubTitle = "";
			}
		}

		++this.updateCounter;

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
