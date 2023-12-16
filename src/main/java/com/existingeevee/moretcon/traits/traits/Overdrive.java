package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Overdrive extends AbstractTrait {

	public Overdrive() {
		super(Misc.createNonConflictiveName("overdrive"), 0);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		target.hurtResistantTime = 0;
	}
}
