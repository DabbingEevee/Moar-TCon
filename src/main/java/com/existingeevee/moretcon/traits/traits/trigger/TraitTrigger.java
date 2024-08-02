package com.existingeevee.moretcon.traits.traits.trigger;

import net.minecraftforge.common.util.EnumHelper;

public enum TraitTrigger {

	IGNITION,
	FROSTBITE,
	CORRUPTED,
	PULVERIZED;

	public static TraitTrigger getOrAdd(String type) {
		if (TraitTrigger.valueOf(type) != null) {
			return TraitTrigger.valueOf(type);
		}
		return EnumHelper.addEnum(TraitTrigger.class, type, new Class<?>[0]);
	}
}
