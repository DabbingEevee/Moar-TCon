package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.inits.ModPotions;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Slicing extends AbstractTrait {
	
	public Slicing() {
		super(MiscUtils.createNonConflictiveName("slicing"), -1);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		target.addPotionEffect(new PotionEffect(ModPotions.bleeding, Math.round(damageDealt) * 20, 0));
	}
}
