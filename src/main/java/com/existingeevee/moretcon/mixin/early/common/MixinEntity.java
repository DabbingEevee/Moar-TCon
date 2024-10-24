package com.existingeevee.moretcon.mixin.early.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.fluid.CustomFireBlockFluid;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

@Mixin(Entity.class)
public class MixinEntity {

	@Inject(method = "setOnFireFromLava", at = @At("HEAD"), cancellable = true)
	protected void moretcon$HEAD_Inject$setOnFireFromLava(CallbackInfo ci) {
		Entity $this = (Entity) (Object) this;

		CustomFireBlockFluid fluid = MiscUtils.getBlockTypeInBB($this.world, $this.getEntityBoundingBox().grow(-0.1D, -0.4D, -0.1D), CustomFireBlockFluid.class);

		if (fluid != null && $this instanceof EntityLivingBase) {
			fluid.handle((EntityLivingBase) $this);
			ci.cancel();
		}
	}

}
