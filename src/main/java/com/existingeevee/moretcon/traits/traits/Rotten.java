package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import thebetweenlands.api.item.ICorrodible;

public class Rotten extends AbstractTrait {

	public Rotten() {
		super(MiscUtils.createNonConflictiveName("rotten"), 0);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		if (tool.getItem() instanceof ICorrodible) {
			ICorrodible corrodible = ((ICorrodible) tool.getItem());
			double corrodsivePercent = (double) corrodible.getCorrosion(tool) / (double) corrodible.getMaxCorrosion(tool);
			double bonusDMG = (corrodsivePercent * 8);
			newDamage += bonusDMG;
		}
	    return super.damage(tool, player, target, damage, newDamage, isCritical);
	}

	@Override
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
		if (tool.getItem() instanceof ICorrodible) {
			ICorrodible corrodible = ((ICorrodible) tool.getItem());
			double corrodsivePercent = (double) corrodible.getCorrosion(tool) / (double) corrodible.getMaxCorrosion(tool);
			double bonusDMG = (corrodsivePercent * 10D);
			newDamage += Math.round(bonusDMG);
		}
		return newDamage;
	}

}
