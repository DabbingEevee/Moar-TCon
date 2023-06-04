package com.existingeevee.moretcon.fluid;

import com.existingeevee.moretcon.ModInfo;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class LiquidFluid extends Fluid {
	int ccolor = 0xffffff;

	public LiquidFluid(String name) {
		this(name, new ResourceLocation(ModInfo.MODID + ":blocks/fluids/" + name + "_still"), new ResourceLocation(ModInfo.MODID + ":blocks/fluids/" + name + "_flowing"));
	}

	public LiquidFluid(String name, ResourceLocation still, ResourceLocation flow) {
		super(name, still, flow);
		this.setUnlocalizedName(ModInfo.MODID + "." + name.toLowerCase());
	}

	public int getCColor() {
		return ccolor;
	}

	public LiquidFluid setCColor(int ccolor) {
		this.ccolor = ccolor;
		return this;
	}
}