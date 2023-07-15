package com.existingeevee.moretcon.traits.traits.abst;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public abstract class DurabilityShieldTrait extends AbstractTrait {

	protected boolean showShieldRemaining = true;
	
	public DurabilityShieldTrait(String identifier, int color) {
		super(identifier, color);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public abstract int getShieldMax(ItemStack stack);

	public abstract int getShieldRemaining(ItemStack stack);

	public abstract int setShieldRemaining(ItemStack stack, int amount);

	public int addShieldRemaining(ItemStack stack, int amount) {
		return setShieldRemaining(stack, getShieldRemaining(stack) + amount);
	}

	public int subtractShieldRemaining(ItemStack stack, int amount) {
		return addShieldRemaining(stack, -amount);
	}

	@Override
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
		int damageNegated = Math.min(getShieldRemaining(tool), newDamage);
		subtractShieldRemaining(tool, damageNegated);
		return newDamage - damageNegated;
	}

	@Override
	public int getPriority() {
		return /*its over*/ 9000; //!!!!!
	}

	@Override
	public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
		if (!showShieldRemaining) 
			return super.getTooltip(modifierTag, detailed);
		
		StringBuilder sb = new StringBuilder();

		ModifierNBT data = ModifierNBT.readTag(modifierTag);

		sb.append(getLocalizedName());
		if (data.level > 1) {
			sb.append(" ");
			sb.append(TinkerUtil.getRomanNumeral(data.level));
		}
		if (!detailed)
			sb.append(": -{-toreplace.moretcon.shield." + this.getIdentifier() + "-}-");

		return sb.toString();
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onItemTooltipEvent(ItemTooltipEvent event) {
		if (!showShieldRemaining) 
			return;
		ItemStack tool = event.getItemStack();
		if (!this.isToolWithTrait(tool))
			return;
		for (int i = 0; i < event.getToolTip().size(); i++) {
			String str = event.getToolTip().get(i);
			String[] splitString = str.split(": ");
			if (splitString.length >= 2 && splitString[1].equals("-{-toreplace.moretcon.shield." + this.getIdentifier() + "-}-")) {
				splitString[1] = getShieldRemaining(tool) + "/" + getShieldMax(tool);
				event.getToolTip().set(i, String.join(": ", splitString));
				return;
			}
		}
	}
}
