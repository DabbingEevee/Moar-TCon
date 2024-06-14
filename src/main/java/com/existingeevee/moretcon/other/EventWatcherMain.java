package com.existingeevee.moretcon.other;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.traits.traits.abst.IAdditionalTraitMethods;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

@SuppressWarnings("deprecation")
public class EventWatcherMain {

	public static boolean sent = false;

	@SubscribeEvent
	@SideOnly(value = Side.CLIENT)
	public void sendBeta(WorldTickEvent e) {
		if (ModInfo.BETA && !sent && Minecraft.getMinecraft().player != null) {
			sent = true;
			TextComponentString linkComponent = new TextComponentString(ModInfo.ISSUE_TRACKER);
			linkComponent.setStyle(linkComponent.getStyle().setUnderlined(true).setColor(TextFormatting.BLUE).setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, ModInfo.ISSUE_TRACKER)));

			String[] strings = ("[" + "\u00A7" + ChatFormatting.BLUE.getChar() + ModInfo.NAME + "\u00A7"
					+ ChatFormatting.RED.getChar() + " " + I18n.translateToLocal("text.beta.name") + "\u00A7"
					+ ChatFormatting.WHITE.getChar() + "] " + I18n.translateToLocal("text.beta.desc")).split("__n__");

			for (int i = 0; i < strings.length - 1; i++) {
				Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(strings[i]), false);
			}
			if (strings.length >= 1) {
				Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(strings[strings.length - 1]).appendSibling(linkComponent), false);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(value = Side.CLIENT)
	public void handleToolTips(ItemTooltipEvent event) {
		if (event.getItemStack().getItem() instanceof ToolPart) {
			Material mat = TinkerUtil.getMaterialFromStack(event.getItemStack());
			if (mat != null) {
				for (ITrait t : mat.getAllTraits()) {
					if (t instanceof IAdditionalTraitMethods) {
						event.getToolTip().addAll(((IAdditionalTraitMethods) t).getAdditionalInfo(event.getItemStack()));
					}
				}
			}

		} else if (event.getItemStack().getItem() instanceof ToolCore) {
			for (ITrait t : ToolHelper.getTraits(event.getItemStack())) {
				if (t instanceof IAdditionalTraitMethods) {
					event.getToolTip().addAll(((IAdditionalTraitMethods) t).getAdditionalInfo(event.getItemStack()));
				}
			}
		}
	}
}
