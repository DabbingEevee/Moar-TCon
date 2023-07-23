package com.existingeevee.moretcon.materials;

import java.lang.reflect.Field;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

/**
 * Encapsulates the standard actions for integrating a material/item into tcon
 */
public class MTMaterialIntegration extends MaterialIntegration {

	private final static Field field$preInit = ObfuscationReflectionHelper.findField(MaterialIntegration.class, "preInit");

	public MTMaterialIntegration(Material material) {
		this(material, null);
	}

	public MTMaterialIntegration(Material material, Fluid fluid) {
		super(material, fluid);
	}

	public MTMaterialIntegration(Material material, Fluid fluid, String oreSuffix) {
		super(material, fluid, oreSuffix);
	}

	public MTMaterialIntegration(String oreRequirement, Material material, Fluid fluid, String oreSuffix) {
		super(oreRequirement, material, fluid, oreSuffix);
	}

	public MTMaterialIntegration(Material material, Fluid fluid, String oreSuffix, String... oreRequirement) {
		super(material, fluid, oreSuffix, oreRequirement);
	}

	@Override
	public void preInit() {
		try {
			if (field$preInit.getBoolean(this)) {
				return;
			}

			field$preInit.set(this, true);

			if (!TConstruct.pulseManager.isPulseLoaded(TinkerSmeltery.PulseId)) {
				fluid = null;
			}

			if (fluid != null) {
				Fluid registeredFluid = FluidRegistry.getFluid(fluid.getName());
				// we register a bucket for the fluid if it's not done because we need it
				if (!FluidRegistry.getBucketFluids().contains(registeredFluid)) {
					FluidRegistry.addBucketForFluid(registeredFluid);
				}
			}

			if (material != null) {
				TinkerRegistry.addMaterial(material);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
