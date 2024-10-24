package com.existingeevee.moretcon.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class MoreTConJEIPlugin implements IModPlugin {

	boolean hasRan = false;

	@Override
	public void register(IModRegistry registry) {

		if (!hasRan) {
			hasRan = true;

			JeiInit.init(registry);
			for (JeiCustomContainer cont : JeiInit.CUSTOM) {
				if (cont.shouldLoad.getAsBoolean()) {
					cont.onRun(registry);
				}
			}
		}
	}
}
