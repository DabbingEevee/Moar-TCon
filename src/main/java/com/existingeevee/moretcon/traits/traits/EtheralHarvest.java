package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class EtheralHarvest extends AbstractTrait {
	
	public EtheralHarvest() {
		super(Misc.createNonConflictiveName("etheralharvest".toLowerCase()), 0);
	}

	@Override
	public boolean isToolWithTrait(ItemStack is) {
		return super.isToolWithTrait(is);
	}
}
