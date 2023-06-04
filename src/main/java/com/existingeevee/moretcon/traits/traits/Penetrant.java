package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Penetrant extends AbstractTrait {

	public Penetrant() {
		super(Misc.createNonConflictiveName("penetrant"), 0x9d763f);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		double amount = target.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue();
		return (float) (newDamage + amount / 4);
	}
}
