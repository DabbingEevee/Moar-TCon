package com.wordpress.craftminemods.tconmaterial.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class LiquidFluid extends Fluid 
{
	public LiquidFluid(String name, ResourceLocation still, ResourceLocation flow) 
	{
		super(name, still, flow);
		this.setUnlocalizedName(name);
	}
}