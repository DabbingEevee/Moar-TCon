package com.existingeevee.moretcon.forging;

import com.existingeevee.moretcon.materials.CompositeRegistry;
import com.existingeevee.moretcon.materials.UniqueMaterial;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.MaterialItem;
import slimeknights.tconstruct.library.tools.ToolPart;

public class ItemUnfinishedPart extends MaterialItem {

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		//Ignored
	}

	public boolean canUseMaterial(Material mat) {
		return !mat.isCraftable() &&
				!CompositeRegistry.getComposite(mat).isPresent() &&
				!(mat instanceof UniqueMaterial);
	}
	
	public ToolPart getPartType(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound(); 
		if (tag != null) {
			String type = tag.getString("ToolType");
			Item typeItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(type));
			if (typeItem instanceof ToolPart) {
				return (ToolPart) typeItem;
			}
		}
		return null;
	}
	
	public boolean isHeated(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound(); 
		if (tag != null) {
			return tag.getBoolean("Heated");
		}
		return false;
	}
	
	public boolean isHeated(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound(); 
		if (tag != null) {
			return tag.getBoolean("Heated");
		}
		return false;
	}
}
