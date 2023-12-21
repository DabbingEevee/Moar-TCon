package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.fires.CustomFireEffect;
import com.existingeevee.moretcon.other.fires.CustomFireHelper;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;


public class Haunted extends AbstractTrait {

	public Haunted() {
		super(MiscUtils.createNonConflictiveName("haunted"), 0);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		CustomFireHelper.setAblaze(target, CustomFireEffect.SPIRIT_FIRE, 100);
	}
}