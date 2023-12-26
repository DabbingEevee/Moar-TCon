package com.existingeevee.moretcon.traits.modifiers;

import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;
import thebetweenlands.common.handler.OverworldItemHandler;

public class Betweenified extends ToolModifier {

	public Betweenified() {
		super(MiscUtils.createNonConflictiveName("modbetweenified"), 0x0b2e05);
		this.addItem(ModItems.betweenifiedModifier);
		this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this),
				ModifierAspect.freeModifier, ModifierAspect.freeModifier, ModifierAspect.freeModifier);
	}

	@Override
	public boolean canApplyCustom(ItemStack stack) throws TinkerGuiException {
		if (!OverworldItemHandler.isToolWeakened(stack)) {
			throw new TinkerGuiException("This tool is already at full efficiency in the Betweenlands.");
		}
		return super.canApplyCustom(stack);
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
	    ToolNBT data = TagUtil.getToolStats(rootCompound);
		rootCompound.setBoolean("moretcon.betweenified", true);
	    TagUtil.setToolTag(rootCompound, data.get());
	}
}