package com.existingeevee.moretcon.traits.traits.abst;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;

public interface IAdditionalTraitMethods {

	default void onPickup(EntityProjectileBase projectileBase, ItemStack ammo, EntityLivingBase entity) {
		
	}
	
	default void onAmmoConsumed(ItemStack ammo, @Nullable EntityLivingBase entity) {
		
	}
	
	default List<String> getAdditionalInfo(ItemStack toolOrPart) { //this is called for both the tool AND the part. 
		return Lists.newArrayList();
	}
	
    default void onLeftClick(EntityPlayer player, ItemStack stack) {
    	
    }
}
