package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Blighted extends AbstractTrait {

	public Blighted() {
		super(MiscUtils.createNonConflictiveName("blighted"), 0);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		int totalDebuffLevels = 0;
		
		for (PotionEffect pot : target.getActivePotionEffects()) {
			if (pot.getPotion().isBadEffect()) {
				totalDebuffLevels += 1 + pot.getAmplifier();
			}
		}
		
		return newDamage * (1 + 0.03f * totalDebuffLevels);
	}

}
