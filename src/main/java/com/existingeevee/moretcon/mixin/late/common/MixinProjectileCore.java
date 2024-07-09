package com.existingeevee.moretcon.mixin.late.common;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.existingeevee.moretcon.traits.traits.abst.IAdditionalTraitMethods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.tools.ranged.ProjectileCore;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

@Mixin(ProjectileCore.class)
public class MixinProjectileCore {

	@Inject(at = @At(value = "RETURN"), method = "useAmmo", remap = false)
	public void moretcon$RETURN_Inject$useAmmo(ItemStack stack, @Nullable EntityLivingBase player, CallbackInfoReturnable<Boolean> ci) {
		for (ITrait t : ToolHelper.getTraits(stack)) {
			if (t instanceof IAdditionalTraitMethods) {
				((IAdditionalTraitMethods) t).onAmmoConsumed(stack, player);
			}
		}
	}
}
