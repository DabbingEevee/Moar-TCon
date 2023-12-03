package com.existingeevee.moretcon.item;

import com.existingeevee.moretcon.other.utils.FireproofItemUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

public class ItemEnchantmentModifier extends ItemBase {

	public ItemEnchantmentModifier(String itemName) {
		super(itemName);//TODO maybe
	}

	public ItemEnchantmentModifier(String itemName, CreativeTabs tab) {
		super(itemName, tab);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		FireproofItemUtil.onUpdateSafe(entityItem);
		return true; //ItemEnchantedBook
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return true;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
		return false; //do in anvil
	}

}
