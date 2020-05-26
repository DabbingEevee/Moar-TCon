package com.wordpress.craftminemods.tconmaterial.item;

import com.wordpress.craftminemods.tconmaterial.VersionInfo;
import com.wordpress.craftminemods.tconmaterial.other.Tabs;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBase extends Item{
	
	private List<String> list;

	public ItemBase(String itemName) {
		
		super();
		setupItem(this, itemName.toLowerCase());
		

	}
	
	public void setupItem(final Item item, final String itemName) {
		item.setUnlocalizedName(itemName);
		item.setCreativeTab(Tabs.materials);
	}
	
	
	

	
	
}
