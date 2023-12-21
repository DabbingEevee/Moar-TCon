package com.existingeevee.moretcon.traits.traits;

import java.util.List;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.google.common.collect.ImmutableList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;


public class Hardcore extends AbstractTrait {

	public Hardcore() {
		super(MiscUtils.createNonConflictiveName("hardcore"), 0);
	}

    private int multiplier(ItemStack tool) {
        double percent = ((double) ToolHelper.getCurrentDurability(tool)) / ((double) ToolHelper.getMaxDurability(tool));

        if (percent <= 0.2) return 6;
        if (percent <= 0.4) return 4;
        if (percent <= 0.6) return 3;
        if (percent <= 0.8) return 2;
        return 1;
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if(ToolHelper.isToolEffective2(tool, event.getState())) {
            event.setNewSpeed(event.getNewSpeed() * multiplier(tool));
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        String loc = String.format(LOC_Extra, getModifierIdentifier());
        return ImmutableList.of(Util.translateFormatted(loc, Util.df.format(multiplier(tool))));
    }
}