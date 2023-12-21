package com.existingeevee.moretcon.traits.traits;

import java.util.Random;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Leeching extends AbstractTrait {

	public Leeching() {
		super(MiscUtils.createNonConflictiveName("leeching"), -1);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (new Random().nextInt(3) == 0) {
			player.setHealth(player.getHealth() + damageDealt * 0.1f);
		}
	}
}
