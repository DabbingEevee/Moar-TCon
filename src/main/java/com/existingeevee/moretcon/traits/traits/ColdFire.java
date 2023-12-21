package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.fires.CustomFireEffect;
import com.existingeevee.moretcon.other.fires.CustomFireHelper;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;


public class ColdFire extends AbstractTrait {

	public ColdFire() {
		super(MiscUtils.createNonConflictiveName("cold_fire"), 0x0066ff);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		CustomFireHelper.setAblaze(target, CustomFireEffect.COLD_FLAMES, Math.max(Math.round(damageDealt * 5) + 100, 40));
	}
}