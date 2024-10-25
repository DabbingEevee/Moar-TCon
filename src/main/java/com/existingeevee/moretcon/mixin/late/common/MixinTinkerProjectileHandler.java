package com.existingeevee.moretcon.mixin.late.common;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.existingeevee.moretcon.other.StaticVars;
import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.capability.projectile.TinkerProjectileHandler;
import slimeknights.tconstruct.library.tools.ranged.IAmmo;
import slimeknights.tconstruct.library.traits.IProjectileTrait;

@Mixin(TinkerProjectileHandler.class)
public abstract class MixinTinkerProjectileHandler {

	@Shadow(remap = false)
	private ItemStack parent;

	@Shadow(remap = false)
	private List<IProjectileTrait> projectileTraitList;

	@Shadow(remap = false)
	abstract void updateTraits();

	@Inject(at = @At(value = "RETURN"), method = "pickup", remap = false, locals = LocalCapture.CAPTURE_FAILHARD)
	public void moretcon$RETURN_Inject$pickup(EntityLivingBase entity, boolean simulate, CallbackInfoReturnable<Boolean> ci, ItemStack stack) {
		if (!simulate && stack.getItem() instanceof IAmmo) {
			StaticVars.lastArrowPickup.set(stack);
		}
	}

	@Inject(at = @At(value = "RETURN"), method = "setLaunchingStack", remap = false)
	public void moretcon$RETURN_Inject$setLaunchingStack(ItemStack launchingStack, CallbackInfo ci) {
		if (ModTraits.polyshot.isToolWithTrait(launchingStack)) { 
			ModTraits.polyshotProj.apply(parent);
			updateTraits();
		}
	}
}
