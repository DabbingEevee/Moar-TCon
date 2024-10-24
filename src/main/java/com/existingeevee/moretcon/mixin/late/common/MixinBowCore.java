package com.existingeevee.moretcon.mixin.late.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.tools.ranged.BowCore;
import slimeknights.tconstruct.library.utils.ToolHelper;

@Mixin(BowCore.class)
public abstract class MixinBowCore {

	@Unique
	private static final ItemStack SOLID_LIGHTNING_RENDER = new ItemStack(ModItems.solidLightning);

	@Inject(method = "getAmmoToRender", at = @At("HEAD"), cancellable = true, remap = false)
	public void moretcon$HEAD_Inject$getAmmoToRender(ItemStack weapon, EntityLivingBase player, CallbackInfoReturnable<ItemStack> ci) {
		if (player == null) {
			return;
		}

		boolean isPulling = player.isHandActive() && player.getActiveItemStack() == weapon;
		boolean canApplyTrait = ModTraits.dematerializing.isToolWithTrait(weapon) && !ToolHelper.isBroken(weapon);
		boolean isFullyDrawn = this.getDrawbackProgress(weapon, player) >= 1;
		if (isPulling && canApplyTrait && isFullyDrawn) {
			ci.setReturnValue(SOLID_LIGHTNING_RENDER);
		}
	}

	@Shadow(remap = false)
	abstract float getDrawbackProgress(ItemStack itemstack, EntityLivingBase entityIn);

}
