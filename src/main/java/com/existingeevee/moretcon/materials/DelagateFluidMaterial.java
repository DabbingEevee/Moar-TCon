package com.existingeevee.moretcon.materials;

import java.util.function.Supplier;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.materials.Material;

public class DelagateFluidMaterial extends Material {

	public DelagateFluidMaterial(String identifier, int color, boolean hidden) {
		super(identifier, color, hidden);
	}

	public DelagateFluidMaterial(String identifier, int color) {
		super(identifier, color);
	}

	public DelagateFluidMaterial(String identifier, TextFormatting textColor) {
		super(identifier, textColor);
	}

	Supplier<Fluid> fluidSupplier = null;

	public DelagateFluidMaterial setFluid(Supplier<Fluid> fluidSupplier) {
		this.fluidSupplier = fluidSupplier;
		return this;
	}

	@Override
	public boolean hasFluid() {
		return fluidSupplier != null ? fluidSupplier.get() != null : super.hasFluid();
	}

	@Override
	public Fluid getFluid() {
		return fluidSupplier != null ? fluidSupplier.get() : super.getFluid();
	}
}
