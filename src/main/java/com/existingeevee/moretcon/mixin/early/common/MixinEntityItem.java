package com.existingeevee.moretcon.mixin.early.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.other.EntityItemUpdateEvent;
import com.existingeevee.moretcon.other.MixinEarlyAccessor;
import com.existingeevee.moretcon.traits.traits.abst.IAdditionalTraitMethods;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

@Mixin(EntityItem.class)
public abstract class MixinEntityItem extends Entity{

	public MixinEntityItem(World worldIn) {
		super(worldIn);
	}

	@Inject(method = "onUpdate()V", at = @At("HEAD"), cancellable = true)
	protected void moretcon$HEAD_Inject$onUpdate(CallbackInfo ci) {
		EntityItem $this = (EntityItem) (Object) this;		
		ItemStack tool = $this.getItem();
		
		if (MinecraftForge.EVENT_BUS.post(new EntityItemUpdateEvent($this))) {
			ci.cancel();
		}
		
		if (!MixinEarlyAccessor.isITinkerable(tool))
			return;
		
		for (Object t : MixinEarlyAccessor.getTraits(tool)) {
			if (t instanceof IAdditionalTraitMethods) {
				((IAdditionalTraitMethods) t).onEntityItemTick(tool, $this);
			}
		}
	}

}
