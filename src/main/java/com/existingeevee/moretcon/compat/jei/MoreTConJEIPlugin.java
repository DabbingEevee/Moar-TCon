package com.existingeevee.moretcon.compat.jei;

import com.existingeevee.moretcon.inits.ModMaterials;
import com.google.common.collect.Lists;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;

@JEIPlugin
public class MoreTConJEIPlugin implements IModPlugin {

	boolean hasRan = false;

	IModRegistry reg;

	@Override
	public void register(IModRegistry registry) {
		reg = registry;

		if (!hasRan) {
			hasRan = true;
		}
		
		JeiInit.init(registry);
		for (JeiCustomContainer cont : JeiInit.CUSTOM) {
			if (cont.shouldLoad.getAsBoolean()) {
				cont.onRun(registry);
			}
		}
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		if (!ModMaterials.materialInertialRedirector.getUniqueToolPart().isEmpty())
			reg.getIngredientRegistry().addIngredientsAtRuntime(VanillaTypes.ITEM, Lists.newArrayList(ModMaterials.materialInertialRedirector.getUniqueToolPart()));
	}
}
