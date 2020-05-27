package com.wordpress.craftminemods.tconmaterial.block.fluid;

import com.wordpress.craftminemods.tconmaterial.VersionInfo;

import net.minecraft.block.material.Material;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidBase extends Fluid
{
    protected static int mapColor = 0xFFFFFFFF;
    protected static boolean doesVaporize = true;
    protected static float overlayAlpha = 0.2F;
    protected static SoundEvent emptySound = SoundEvents.ITEM_BUCKET_EMPTY;
    protected static SoundEvent fillSound = SoundEvents.ITEM_BUCKET_FILL;
    protected static Material material = Material.WATER;
    public FluidBase(String fluidName) 
    {
        super(fluidName.toLowerCase(), new ResourceLocation(VersionInfo.MODID, "blocks/" + fluidName.toLowerCase() + "_still"), new ResourceLocation(VersionInfo.MODID, "blocks/" + fluidName.toLowerCase() + "_flowing"));  //fluidName + "_still", fluidName + "_still");
    } 
    public FluidBase(String fluidName, int mapColor) 
    {
        this(fluidName);
        setColor(mapColor);
    }
 
    public FluidBase(String fluidName, int mapColor, float overlayAlpha) 
    {
        this(fluidName, mapColor);
        setAlpha(overlayAlpha);
    }
    public FluidBase(String fluidName, int mapColor, float overlayAlpha, boolean doesVaporize) 
    {
        this(fluidName, mapColor);
        setDoesVapoize(doesVaporize);
    }
    @Override
    public int getColor()
    {
        return mapColor;
    }
 
    public FluidBase setColor(int parColor)
    {
        mapColor = parColor;
        return this;
    }
 
    public boolean getDoesVapoize()
    {
        return doesVaporize;
    }
 
    public FluidBase setDoesVapoize(boolean parDoesVaporize)
    {
    	doesVaporize = parDoesVaporize;
        return this;
    }    
    
    public float getAlpha()
    {
        return overlayAlpha;
    }
 
    public FluidBase setAlpha(float parOverlayAlpha)
    {
        overlayAlpha = parOverlayAlpha;
        return this;
    }
 
    @Override
    public FluidBase setEmptySound(SoundEvent parSound)
    {
        emptySound = parSound;
        return this;
    }
 
    @Override
    public SoundEvent getEmptySound()
    {
        return emptySound;
    }
 
    @Override
    public FluidBase setFillSound(SoundEvent parSound)
    {
        fillSound = parSound;
        return this;
    }
 
    @Override
    public SoundEvent getFillSound()
    {
        return fillSound;
    }
 
    public FluidBase setMaterial(Material parMaterial)
    {
        material = parMaterial;
        return this;
    }
 
    public Material getMaterial()
    {
        return material;
    }
 
    @Override
    public boolean doesVaporize(FluidStack fluidStack)
    {
        if (block == null)
            return false;
        return this.doesVaporize;
    }
    
}