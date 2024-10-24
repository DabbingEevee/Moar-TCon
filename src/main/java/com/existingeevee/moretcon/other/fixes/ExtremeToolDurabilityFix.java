package com.existingeevee.moretcon.other.fixes;

import com.existingeevee.moretcon.config.ConfigHandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ranged.IAmmo;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class ExtremeToolDurabilityFix {

	@SubscribeEvent
	public void onTinkerCraftingEvent(TinkerCraftingEvent e) {
		int cap = ConfigHandler.maxToolDurability;
		if (cap < 0) {
			return;
		}
		if (e.getItemStack().getItem() instanceof ToolCore) {
			if (e.getItemStack().getItem() instanceof IAmmo || ConfigHandler.shouldDurabilityCapNonProjectiles) {
				int max = ToolHelper.getMaxDurability(e.getItemStack());
				NBTTagCompound comp = TagUtil.getToolTag(e.getItemStack());
				comp.setInteger(Tags.DURABILITY, Math.min(max, cap));
			}
		}
	}
}
