package com.existingeevee.moretcon.compat.jei;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import mezz.jei.api.IModRegistry;

public class JeiCustomContainer {

	private final Consumer<IModRegistry> onRun;
	public final BooleanSupplier shouldLoad;
	
	public JeiCustomContainer(Consumer<IModRegistry> onRun, BooleanSupplier shouldLoad) {
		this.onRun = onRun;
		this.shouldLoad = shouldLoad;
	}
	
	public void onRun(IModRegistry r) {
		this.onRun.accept(r);
	}
}
