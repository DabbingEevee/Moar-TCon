package com.wordpress.craftminemods.tconmaterial.block;

import java.awt.Color;

import com.wordpress.craftminemods.tconmaterial.VersionInfo;
import com.wordpress.craftminemods.tconmaterial.other.Tabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBase extends Block{
	
	public BlockBase(String itemName, Material material, int harvestLevel) {
		
		super(material);
		setupItem(this, itemName.toLowerCase(), harvestLevel);

		
	}
	
	public void setupItem(final BlockBase blockBase, final String itemName, final int harvestLevel) {
		blockBase.setUnlocalizedName(itemName);
		blockBase.setCreativeTab(Tabs.materials);
		blockBase.setHarvestLevel("pickaxe", harvestLevel);
		//ModelLoader.setCustomModelResourceLocation(blockBase, 0, new ModelResourceLocation(VersionInfo.MODID + ":" + itemName ,"inventory"));
	}

	class ItemBlockBase extends ItemBlock {
		
		public ItemBlockBase(Block block) {
			super(block);
		}
		
		@Override
		public String getUnlocalizedName(ItemStack itemStack) {
			return super.getUnlocalizedName();
		}
	}
	
}

