package com.existingeevee.moretcon.traits.traits.abst;

import com.existingeevee.moretcon.traits.traits.trigger.TraitTrigger;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface ITriggerableTrait {

	void trigger(ItemStack tool, EntityLivingBase wielder, TraitTrigger trigger);

}
