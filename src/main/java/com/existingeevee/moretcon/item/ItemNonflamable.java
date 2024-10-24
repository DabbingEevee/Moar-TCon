package com.existingeevee.moretcon.item;

import com.existingeevee.moretcon.other.utils.FireproofItemUtil;

import net.minecraft.entity.item.EntityItem;

public class ItemNonflamable extends ItemBase {

	public ItemNonflamable(String itemName) {
		super(itemName);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		FireproofItemUtil.onUpdateSafe(entityItem);
		return true;
	}

}
