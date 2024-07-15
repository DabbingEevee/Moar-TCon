package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Supercritical extends AbstractTraitLeveled {

	public Supercritical(int lvl) {
		super(MiscUtils.createNonConflictiveName("supercritical"), 0, 4, lvl);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onCritCheck(CriticalHitEvent ev) {
		ItemStack tool = ev.getEntityPlayer().getHeldItemMainhand();
		
		if (!ToolHelper.isBroken(tool)) {
			NBTTagList tagList = TagUtil.getModifiersTagList(tool);
			int index = TinkerUtil.getIndexInCompoundList(tagList, getModifierIdentifier());

			if (index > -1) {
				ModifierNBT modifier = ModifierNBT.readTag(tagList.getCompoundTagAt(index));

				ev.setDamageModifier(ev.getDamageModifier() + modifier.level * 0.25f);
			}
		}
	}
}
