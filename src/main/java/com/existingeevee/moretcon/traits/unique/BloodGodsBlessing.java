package com.existingeevee.moretcon.traits.unique;

import com.existingeevee.moretcon.effects.ModPotions;
import com.existingeevee.moretcon.other.Misc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class BloodGodsBlessing extends AbstractTrait {

	public BloodGodsBlessing() {
		super(Misc.createNonConflictiveName("bloodgodsblessing"), 0);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (target.isDead || target.getHealth() >= 0) {
			int lvl = -1;
			
			if (player.isPotionActive(ModPotions.bloodgodsblessing)) {
				lvl = player.getActivePotionEffect(ModPotions.bloodgodsblessing).getAmplifier();
				player.removeActivePotionEffect(ModPotions.bloodgodsblessing);
			}
			
			player.addPotionEffect(new PotionEffect(ModPotions.bloodgodsblessing, 5 * 20, lvl + 1, false, false));
		}
	}

}
