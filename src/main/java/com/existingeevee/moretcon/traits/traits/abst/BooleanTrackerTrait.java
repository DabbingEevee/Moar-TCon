package com.existingeevee.moretcon.traits.traits.abst;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.NBT;

public abstract class BooleanTrackerTrait extends AdditionalDisplayTrait {

	public BooleanTrackerTrait(String identifier, int color) {
		this(identifier, color, 1, 1);
	}
	
	public BooleanTrackerTrait(String identifier, int color, int lvlmax, int lvl) {
		super(identifier, color, lvlmax, lvl);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getStringToRender(ItemStack tool) {
		return I18n.translateToLocal("booltracker." + (this.isActive(tool) ? "active" : "inactive") + ".name");
	}
	
	public boolean isActive(ItemStack tool) {
		NBTTagCompound comp = tool.getOrCreateSubCompound(this.getModifierIdentifier());
		return comp.hasKey("state", NBT.TAG_BYTE) ? comp.getBoolean("state") : getDefaultState(tool);
	}
	
	public boolean setActive(ItemStack tool, boolean newState) {
		NBTTagCompound comp = tool.getOrCreateSubCompound(this.getModifierIdentifier());
		comp.setBoolean("state", newState);
		return newState;
	}

	public abstract boolean getDefaultState(ItemStack tool);

}
