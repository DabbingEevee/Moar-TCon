package com.existingeevee.moretcon.reforges;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.tools.ToolCore;

public class ReforgeHandler {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onToolTip(ItemTooltipEvent event) {
		if (event.getItemStack().getItem() instanceof ToolCore) {
			AbstractReforge reforge = ReforgeHelper.getReforge(event.getItemStack());
			
			if (reforge == null)
				return; 
			
			String name = CustomFontColor.encodeColor(reforge.getColor()) + reforge.getLocalizedPrefix() + ChatFormatting.RESET;
			
			List<String> tooltip = event.getToolTip();
			tooltip.set(0, name + " " + tooltip.get(0));
		}
	}
	
}
