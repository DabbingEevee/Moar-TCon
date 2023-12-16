package com.existingeevee.moretcon.traits.traits.abst;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public abstract class DurabilityShieldTrait extends NumberTrackerTrait {

	public DurabilityShieldTrait(String identifier, int color) {
		super(identifier, color);
	}
	
	@Override
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
		int damageNegated = Math.min(getNumber(tool), newDamage);
		removeNumber(tool, damageNegated);
		return newDamage - damageNegated;
	}

	@Override
	public int getPriority() {
		return /*its over*/ 9000; //!!!!!
	}
}
