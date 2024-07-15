package com.existingeevee.moretcon.traits.traits.trigger;

import java.util.List;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.traits.traits.abst.ITriggerableTrait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class TraitTriggerHelper {

	public static void trigger(ItemStack tool, EntityLivingBase wielder, @Nullable EntityLivingBase target, TraitTrigger trigger) {
		List<ITrait> traits = ToolHelper.getTraits(tool);
		for (ITrait trait : traits) {
			if (trait instanceof ITriggerableTrait) {
				ITriggerableTrait triggerable = (ITriggerableTrait) trait;
				triggerable.trigger(tool, wielder, trigger);
			}
		}
	}
}
