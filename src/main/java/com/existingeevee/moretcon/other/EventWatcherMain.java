package com.existingeevee.moretcon.other;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.materials.UniqueMaterial;
import com.existingeevee.moretcon.traits.ModTraits;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolCraftingEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolModifyEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolPartReplaceEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

@SuppressWarnings({ "deprecation", "unused" })
public class EventWatcherMain {

	private Object begoneWarning = null;

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleUniqueToolParts(ToolCraftingEvent event) {
		for (ItemStack part : event.getToolParts()) {
			if (TinkerUtil.getMaterialFromStack(part) instanceof UniqueMaterial
					&& !event.getItemStack().getItem().getRegistryName().toString()
							.equals(((UniqueMaterial) TinkerUtil.getMaterialFromStack(part)).getToolResLoc())) {
				event.setCanceled(I18n.translateToLocal("text.err.unique.not_correct_tool"));// "You can only use unique tool parts on the correct tool.");
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleUniqueToolParts(ToolPartReplaceEvent event) {
		for (ItemStack part : event.getToolParts()) {
			if (TinkerUtil.getMaterialFromStack(part) instanceof UniqueMaterial
					&& !event.getItemStack().getItem().getRegistryName().toString()
							.equals(((UniqueMaterial) TinkerUtil.getMaterialFromStack(part)).getToolResLoc())) {
				event.setCanceled(I18n.translateToLocal("text.err.unique.not_correct_tool"));
			}
		}
	}

	public static boolean sent = false;

	@SubscribeEvent
	@SideOnly(value = Side.CLIENT)
	public void sendBeta(WorldTickEvent e) {
		if (ModInfo.BETA && !sent && Minecraft.getMinecraft().player != null) {
			sent = true;
			Minecraft.getMinecraft().player.sendStatusMessage(
					new TextComponentString("[" + "\u00A7" + ChatFormatting.BLUE.getChar() + ModInfo.NAME + "\u00A7"
							+ ChatFormatting.RED.getChar() + " " + I18n.translateToLocal("text.beta.name") + "\u00A7"
							+ ChatFormatting.WHITE.getChar() + "] " + I18n.translateToLocal("text.beta.desc")),
					false);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleToolModifyEvent(ToolModifyEvent event) {
		Material pre = Misc.getUniqueEmbossment(event.getToolBeforeModification());
		Material post = Misc.getUniqueEmbossment(event.getItemStack());
		if (pre == null && (post instanceof UniqueMaterial)) {
			if (!UniqueMaterial.getToolFromResourceLocation(new ResourceLocation(((UniqueMaterial) post).getToolResLoc()))
					.getRegistryName().equals(event.getItemStack().getItem().getRegistryName()))
				event.setCanceled(I18n.translateToLocal("text.err.unique.not_correct_tool"));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(value = Side.CLIENT)
	public void handleToolTips(ItemTooltipEvent event) {
		try {
			if (event.getItemStack().getItem() instanceof ToolPart) {
				if (TinkerUtil.getMaterialFromStack(event.getItemStack()) instanceof UniqueMaterial) {
					UniqueMaterial mat = (UniqueMaterial) TinkerUtil.getMaterialFromStack(event.getItemStack());
					if (UniqueMaterial.getToolFromResourceLocation(new ResourceLocation(mat.getToolResLoc())) != null) {
						int i = 1;
						event.getToolTip().add(i++, "");

						if (!(mat.getPartResLoc().equals(event.getItemStack().getItem().getRegistryName().toString()))) {
							event.getToolTip().add(i++, "§4§l" + I18n.translateToLocal("text.err.unique.unobtainable")); // ");
							event.getToolTip().add(i++, "");
						}   
						event.getToolTip().add(i++, "§7" + I18n.translateToLocal("text.err.unique.only_make").replace("__s__", UniqueMaterial
								.getToolFromResourceLocation(new ResourceLocation(mat.getToolResLoc())).getLocalizedName()));
						event.getToolTip().add(i++, "");

					}
				}
				if (TinkerUtil.getMaterialFromStack(event.getItemStack()) != null
						&& TinkerUtil.getMaterialFromStack(event.getItemStack())
								.hasTrait(ModTraits.bloodGodsBlessing.getIdentifier(), null)) {
					int i = event.getToolTip().size();
					event.getToolTip().add(i, "");
					;
					event.getToolTip().add(i, CustomFontColor.encodeColor(0xea8f8c) + "RIP Technoblade (1999-2022)");
					event.getToolTip().add(i, CustomFontColor.encodeColor(0xea8f8c)
							+ "He may be gone, but he will always live in our hearts.");
					event.getToolTip().add(i, "");
				}

			}
			if (event.getItemStack().getItem() instanceof ToolCore) {
				for (int i = 1; i < event.getToolTip().size() + 1; i++) {
					String last = StringUtils.stripControlCodes(event.getToolTip().get(event.getToolTip().size() - i));
					boolean found = false;
					// tooltip.bl.circlegem.green=§aGreen Gem§r
					// tooltip.bl.circlegem.crimson=§cCrimson Gem§r
					// tooltip.bl.circlegem.aqua=§9Aqua Gem§r
					for (String str : new String[] {
							StringUtils.stripControlCodes(I18n.translateToLocal("tooltip.bl.circlegem.green")),
							StringUtils.stripControlCodes(I18n.translateToLocal("tooltip.bl.circlegem.crimson")),
							StringUtils.stripControlCodes(I18n.translateToLocal("tooltip.bl.circlegem.aqua")) }) {
						if (last.equals(str)) {
							event.getToolTip().remove(event.getToolTip().size() - i);
							found = true;
							break;
						}
					}
					if (found)
						break;
				}
				if (ToolHelper.getTraits(event.getItemStack()).contains(ModTraits.bloodGodsBlessing)) {
					int i = event.getToolTip().size();
					event.getToolTip().add(i, "");
					event.getToolTip().add(i, CustomFontColor.encodeColor(0xea8f8c) + "RIP Technoblade (1999-2022)");
					event.getToolTip().add(i, CustomFontColor.encodeColor(0xea8f8c)
							+ "He may be gone, but he will always live in our hearts.");
					event.getToolTip().add(i, "");
				}
			}
		} catch (NullPointerException e) {
		}
	}
}

/*
 * ArrayList<UniqueMaterial> postChange = new ArrayList<UniqueMaterial>();
 * NBTTagList tagList =
 * event.getItemStack().serializeNBT().getCompoundTag("tag").getCompoundTag(
 * "TinkerData").getTagList("Modifiers", NBT.TAG_STRING); for (NBTBase tag :
 * tagList) { if
 * (!((NBTTagString)tag).getString().toString().startsWith("extratrait"))
 * continue; String tagString =
 * ((NBTTagString)tag).getString().toString().replaceFirst("extratrait", "");
 * for (Material mat : TinkerRegistry.getAllMaterials()) { if
 * ((TinkerRegistry.getTrait(tagString.replaceFirst(mat.getIdentifier(), "")) !=
 * null) && mat instanceof UniqueMaterial) { postChange.add((UniqueMaterial)
 * mat); } } } ArrayList<UniqueMaterial> preChange = new
 * ArrayList<UniqueMaterial>();
 * 
 * NBTTagList tagListPre =
 * event.getToolBeforeModification().serializeNBT().getCompoundTag("tag").
 * getCompoundTag("TinkerData").getTagList("Modifiers", NBT.TAG_STRING); for
 * (NBTBase tag : tagListPre) { //Logging.log(tag.toString()); if
 * (!((NBTTagString)tag).getString().toString().startsWith("extratrait"))
 * continue; String tagString =
 * ((NBTTagString)tag).getString().toString().replaceFirst("extratrait", "");
 * for (Material mat : TinkerRegistry.getAllMaterials()) { if
 * ((TinkerRegistry.getTrait(tagString.replaceFirst(mat.getIdentifier(), "")) !=
 * null) && mat instanceof UniqueMaterial) { preChange.add((UniqueMaterial)
 * mat); } } }
 * 
 * ArrayList<UniqueMaterial> changes = new ArrayList<UniqueMaterial>();
 * 
 * for (UniqueMaterial mat : postChange) { boolean shouldContinue = false; for
 * (UniqueMaterial mat2 : preChange) { if
 * (mat.getIdentifier().equals(mat2.getIdentifier())) shouldContinue = true;
 * break; } if (shouldContinue) continue; changes.add(mat); } if (changes.size()
 * != 0) { if (!UniqueMaterial.getToolFromResourceLocation(new
 * ResourceLocation(changes.get(0).toolResLoc)).getRegistryName().equals(event.
 * getItemStack().getItem().getRegistryName()))
 * event.setCanceled("You can only use unique tool parts on the correct tool.");
 * }
 */
