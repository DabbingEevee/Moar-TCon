package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Withered extends AbstractTrait {

	public Withered() {
		super(MiscUtils.createNonConflictiveName("withered"), 0);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit) {
			int prevLevel = 0;
			if (target.isPotionActive(MobEffects.WITHER)) {
				prevLevel = target.getActivePotionEffect(MobEffects.WITHER).getAmplifier();
			}
			if (!target.world.isRemote) {
				if (target.world.rand.nextInt(4) == 0) {
					target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 5 * 20, Math.min(prevLevel + 1, 2)));
				} else {
					if (prevLevel > 0) {
						target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 5 * 20, prevLevel));
					}
				}
			}
		}
	}
}
