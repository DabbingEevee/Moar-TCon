package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.inits.ModPotions;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.traits.abst.IBombTrait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Voltrend extends AbstractTrait implements IBombTrait {

	public Voltrend() {
		super(MiscUtils.createNonConflictiveName("voltrend"), 0xffffff);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit) {
			int prevLevel = 0;
			if (target.isPotionActive(ModPotions.charged)) {
				prevLevel = target.getActivePotionEffect(ModPotions.charged).getAmplifier();
			}
			if (!target.world.isRemote) {
				if (target.world.rand.nextInt(4) == 0) {
					target.addPotionEffect(new PotionEffect(ModPotions.charged, 5 * 20, Math.min(prevLevel + 1, 2)));
				} else {
					if (prevLevel > 0) {
						target.addPotionEffect(new PotionEffect(ModPotions.charged, 5 * 20, prevLevel));
					}
				}
			}
		}
	}
}