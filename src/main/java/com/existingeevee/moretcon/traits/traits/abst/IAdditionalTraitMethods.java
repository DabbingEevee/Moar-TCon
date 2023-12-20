package com.existingeevee.moretcon.traits.traits.abst;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;

public interface IAdditionalTraitMethods {

	default void onPickup(EntityProjectileBase projectileBase, ItemStack ammo, EntityLivingBase entity) {
		
	}
	
	default void onAmmoConsumed(ItemStack ammo, @Nullable EntityLivingBase entity) {
		
	}
}
