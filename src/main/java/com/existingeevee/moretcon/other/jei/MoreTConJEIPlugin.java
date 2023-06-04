package com.existingeevee.moretcon.other.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class MoreTConJEIPlugin implements IModPlugin {
	@Override
	public void register(IModRegistry registry) {

		
		JeiInit.init(registry);
		
		for (JeiCustomContainer cont : JeiInit.CUSTOM) {
			if (cont.shouldLoad.getAsBoolean()) {
				cont.onRun(registry);
			}
		}
		
		/*if (CompatManager.thebetweenlands) {


			IIngredientType<FluidStack> itemType = registry.getIngredientRegistry().getIngredientType(FluidStack.class);

			registry.addIngredientInfo(new FluidStack(ModFluids.liquidMummySludge, 1000), itemType,
					"Obtained by melting down a Dreadful Peat Mummy.");
		}*/
	}
}
