package com.wordpress.craftminemods.tconmaterial.tconstuff.traitclasses;

import com.wordpress.craftminemods.tconmaterial.potion_effect.ModPotions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;

import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.tools.harvest.TinkerHarvestTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;


public class ColdFire extends AbstractTrait {
	
	//public static final Trait hammer_sickle = new Trait(identifier, color);
	public ColdFire() {
		super("cold_fire", 0x0066ff);
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		target.addPotionEffect(new PotionEffect(ModPotions.coldflames, Math.round(damageDealt) + 10 , 1));
		
	}
}