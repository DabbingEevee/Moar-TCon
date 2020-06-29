package com.wordpress.craftminemods.tconmaterial.other;

import com.wordpress.craftminemods.tconmaterial.item.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Tabs {
	
	public static void init() {
		materials = (new CreativeTabs("materials") {
			@Override 
			@SideOnly(Side.CLIENT)
			public ItemStack getTabIconItem() {
				return new ItemStack(ModItems.ingotFusionite, (int)(1));
			}
			
		});
	}

	public static CreativeTabs materials;
	
}
