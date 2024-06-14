package com.existingeevee.moretcon.mixin.late.common;

import javax.annotation.Nonnull;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.other.StaticVars;
import com.existingeevee.moretcon.traits.traits.abst.IAdditionalTraitMethods;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

@SuppressWarnings("deprecation")
@Mixin(EntityProjectileBase.class)
public class MixinEntityProjectileBase {

	@Inject(at = @At(value = "TAIL"), method = { "onCollideWithPlayer", "func_70100_b_" }, remap = false)
	public void moretcon$TAIL_Inject$onCollideWithPlayer(@Nonnull EntityPlayer player, CallbackInfo ci) {
		EntityProjectileBase $this = (EntityProjectileBase) (Object) this;
		if ($this.isDead && !StaticVars.lastArrowPickup.isEmpty()) {

			for (ITrait t : ToolHelper.getTraits(StaticVars.lastArrowPickup)) {
				if (t instanceof IAdditionalTraitMethods) {
					((IAdditionalTraitMethods) t).onPickup($this, StaticVars.lastArrowPickup, player);
				}
			}
			StaticVars.lastArrowPickup = ItemStack.EMPTY;
		}
	}

}
