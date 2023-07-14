package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class DummyTrait extends AbstractTrait {

	public DummyTrait(String identifier, int color) {
		super(Misc.createNonConflictiveName(identifier), color);
	}

	@Override
	public boolean isToolWithTrait(ItemStack itemStack) { //Making it easy bc its all these traits are good for
		return super.isToolWithTrait(itemStack);
	}

}
