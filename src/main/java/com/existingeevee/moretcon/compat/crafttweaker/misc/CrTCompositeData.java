package com.existingeevee.moretcon.compat.crafttweaker.misc;

import java.util.function.Supplier;

import com.existingeevee.moretcon.materials.CompositeRegistry.CompositeData;

import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.materials.Material;

public class CrTCompositeData extends CompositeData {

	public CrTCompositeData(Supplier<Material> from, Supplier<Material> result, Supplier<Fluid> catalyst, boolean onlyOne) {
		super(from, result, catalyst, onlyOne);
	}

}
