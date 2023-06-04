package com.existingeevee.moretcon.other.jei;

import java.util.function.BooleanSupplier;

import mezz.jei.api.IModRegistry;

public class JeiBlacklistContainer<T> extends JeiCustomContainer {

	public final T stack;
	
	public JeiBlacklistContainer(T stack, BooleanSupplier shouldLoad) {
		super(null, shouldLoad);
		this.stack = stack;
	}
	
	@Override
	public void onRun(IModRegistry r) {
		r.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(stack);
	}
}
