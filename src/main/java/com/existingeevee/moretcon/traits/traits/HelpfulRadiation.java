package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class HelpfulRadiation extends AbstractTrait {

	public HelpfulRadiation() {
		super(MiscUtils.createNonConflictiveName("helpful_radiation"), 0x00ed00);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (target.world.isRemote)
			return;
		if (random.nextBoolean()) {
			target.addPotionEffect(new PotionEffect(MobEffects.GLOWING, Math.round(damageDealt) * 8, 0));
			target.addPotionEffect(new PotionEffect(MobEffects.WITHER, Math.round(damageDealt) * 8, 1));
			target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, Math.round(damageDealt) * 8, 0));
		}
		if (random.nextBoolean()) {
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, Math.round(damageDealt) * 8, 0));
		}
		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
	}
}