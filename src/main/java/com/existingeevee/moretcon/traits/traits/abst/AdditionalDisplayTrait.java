package com.existingeevee.moretcon.traits.traits.abst;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public abstract class AdditionalDisplayTrait extends AbstractTraitLeveled {

	protected boolean showNumberRemaining = true;
	
	public AdditionalDisplayTrait(String identifier, int color) {
		this(identifier, color, 1, 1);
	}
	
	public AdditionalDisplayTrait(String identifier, int color, int lvlmax, int lvl) {
		super(identifier, color, lvlmax, lvl);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
		if (!showNumberRemaining) 
			return super.getTooltip(modifierTag, detailed);
		
		StringBuilder sb = new StringBuilder();

		ModifierNBT data = ModifierNBT.readTag(modifierTag);

		sb.append(getLocalizedName());
		if (data.level > 1) {
			sb.append(" ");
			sb.append(TinkerUtil.getRomanNumeral(data.level));
		}
		if (!detailed)
			sb.append(": -{-toreplace.moretcon.display." + data.identifier + "-}-");

		return sb.toString();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onItemTooltipEvent(ItemTooltipEvent event) {
		if (!showNumberRemaining) 
			return;
		ItemStack tool = event.getItemStack();
		if (!this.isToolWithTrait(tool))
			return;
		for (int i = 0; i < event.getToolTip().size(); i++) {
			String str = event.getToolTip().get(i);
			String[] splitString = str.split(": ");
			if (splitString.length >= 2 && splitString[1].equals("-{-toreplace.moretcon.display." + this.getModifierIdentifier() + "-}-")) {
				splitString[1] = this.getStringToRender(tool);
				event.getToolTip().set(i, String.join(": ", splitString));
				return;
			}
		}
	}
	
	public abstract String getStringToRender(ItemStack tool);
}
