package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.inits.ModPotions;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Hyperheat extends AbstractTrait {
	public Hyperheat() {
		super(MiscUtils.createNonConflictiveName("hyperheat"), 0x0066ff);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit) {
			target.addPotionEffect(new PotionEffect(ModPotions.hyperflames, 200, (int) Math.min(damageDealt / 3, ConfigHandler.hyperheatMaximumStack)));
		}
	}
}
