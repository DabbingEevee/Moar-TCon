package com.existingeevee.moretcon.other;

import net.minecraft.item.ItemStack;

//DO NOT USE THIS IF YOU ARE AN ADDON DEV. PERIOD.
//THESE ARE TEMP DATA STORED TO BE USED IN DIFFERENT METHODS
//THIS IS *VERY* JANKY
@Deprecated
public class StaticVars {

	public static final ThreadLocal<ItemStack> lastArrowPickup = ThreadLocal.withInitial(() -> ItemStack.EMPTY);

	public static final ThreadLocal<ItemStack> lastArrowFired = ThreadLocal.withInitial(() -> ItemStack.EMPTY);
}
