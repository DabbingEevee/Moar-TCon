package com.existingeevee.moretcon.traits.modifiers;

import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.other.Misc;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;
import thebetweenlands.common.capability.circlegem.CircleGemType;
import thebetweenlands.common.registries.ItemRegistry;

public class RedGem extends ToolModifier {

	public RedGem() {
		super(Misc.createNonConflictiveName("modredgem"), 0xFF0000);
		this.addItem(ItemRegistry.CRIMSON_MIDDLE_GEM);
		if (ConfigHandler.middleGemsRequireModifierSlots) {
			this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this), ModifierAspect.freeModifier);
		} else {
			this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this));
		}	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
	    ToolNBT data = TagUtil.getToolStats(rootCompound);
		rootCompound.setInteger("Gem", CircleGemType.CRIMSON.id);
	    TagUtil.setToolTag(rootCompound, data.get());
	}

	@Override
	public boolean canApplyCustom(ItemStack stack) throws TinkerGuiException {
	    if (stack.serializeNBT().getCompoundTag("tag").hasKey("Gem", 99)) {
	    	throw new TinkerGuiException("Middle Gem modifiers can only be applied once.");
	    }
	    return super.canApplyCustom(stack);
	}

}