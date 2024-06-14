package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class MagicallyReactive extends AbstractTrait {

	public MagicallyReactive() {
		super(MiscUtils.createNonConflictiveName("magically_reactive"), 0x9d763f);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		int amount = 0;
		
		for (ItemStack stack : target.getEquipmentAndArmor()) {
			amount += stack.isItemEnchanted() ? 1 : 0;
		}
		
		return newDamage + amount;
	}
}
