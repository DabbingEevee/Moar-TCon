package com.existingeevee.moretcon.conditions;

import java.util.function.BooleanSupplier;

import com.existingeevee.moretcon.other.utils.CompatManager;
import com.google.gson.JsonObject;

import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class ConditionALInstalled implements IConditionFactory {
	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		return () -> CompatManager.aether_legacy;
	}
}
