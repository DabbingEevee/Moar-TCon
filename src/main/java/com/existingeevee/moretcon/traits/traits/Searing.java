package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class Searing extends AbstractTraitLeveled {

	public Searing(int level) {
		super(MiscUtils.createNonConflictiveName("searing"), 0, 3, level);
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
		NBTTagList tagList = TagUtil.getModifiersTagList(tool);
		int index = TinkerUtil.getIndexInCompoundList(tagList, getModifierIdentifier());

		if (index > -1) {
			ModifierNBT modifier = ModifierNBT.readTag(tagList.getCompoundTagAt(index));

			ItemStack stack = event.getState().getBlock().getItem(event.getEntityPlayer().world, event.getPos(), event.getState());
			MeltingRecipe recipe = TinkerRegistry.getMelting(stack);

			if (recipe != null) {
		          event.setNewSpeed(event.getNewSpeed() + modifier.level * 2);
			}
		}
	}
}
