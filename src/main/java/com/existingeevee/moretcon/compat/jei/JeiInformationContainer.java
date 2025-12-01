package com.existingeevee.moretcon.compat.jei;

import java.util.function.BooleanSupplier;

import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IIngredientType;

public class JeiInformationContainer<T> extends JeiCustomContainer {

	public final IIngredientType<T> ingredient;
	public final T stack;
	public final String information;

	public JeiInformationContainer(IIngredientType<T> ingredient, T stack, String information,
			BooleanSupplier shouldLoad) {
		super(null, shouldLoad);
		this.ingredient = ingredient;
		this.stack = stack;
		this.information = information;
	}

	@Override
	public void onRun(IModRegistry r) {
		r.addIngredientInfo(stack, ingredient, "jei_info." + information + ".desc");
	}
}
