package com.wordpress.craftminemods.tconmaterial.block.fluid;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

public class FluidMaterials 
{
    public static final Material MOLTEN_FUSIONITE = new MaterialLiquid(
        MapColor.BLUE_STAINED_HARDENED_CLAY).LAVA.setReplaceable();
}