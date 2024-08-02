package com.existingeevee.moretcon.other.utils;

import java.util.function.Supplier;

public class LambdaHelper {

	//This is here so things dont crap themselves when initalized at the wrong time (ie mixins).
	public static boolean runBooleanSupplier(Supplier<Boolean> supp) {
		return supp == null ? false : supp.get();
	}

}
