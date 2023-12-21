package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.ModTraits;
import com.existingeevee.moretcon.traits.traits.abst.DurabilityShieldTrait;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Overslime extends DurabilityShieldTrait {

	public Overslime() {
		super(MiscUtils.createNonConflictiveName("overslime"), 0);
	}

	@Override
	public int getNumberMax(ItemStack stack) {
		int amount = (Math.max(10, ToolHelper.getCurrentDurability(stack) / 10) + 40);
		if (ModTraits.overcast.isToolWithTrait(stack))
			amount *= 2;
		return amount;
	}

	@Override
	public int getNumber(ItemStack stack) {
		NBTTagCompound comp = stack.getOrCreateSubCompound(this.identifier);
		return comp.hasKey("remaining", NBT.TAG_INT) ? comp.getInteger("remaining") : 0;
	}

	@Override
	public int setNumber(ItemStack stack, int amount) {
		NBTTagCompound comp = stack.getOrCreateSubCompound(this.identifier);
		comp.setInteger("remaining", amount);
		return amount;
	}

	@Override
	public boolean isToolWithTrait(ItemStack itemStack) { 
		return super.isToolWithTrait(itemStack);
	}
}
