package com.existingeevee.moretcon.traits.modifiers.misc;

import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolModifyEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class MatterDeconstructionGel extends Modifier {

	public MatterDeconstructionGel() {
		super(MiscUtils.createNonConflictiveName("MatterDeconstructionGel".toLowerCase()));
		this.addItem(ModItems.matterDeconstructionGel);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		rootCompound.setBoolean("ToDepair", true);
	}

	@Override
	public boolean canApplyCustom(ItemStack stack) throws TinkerGuiException {
		boolean toDepair = TagUtil.getTagSafe(stack).getBoolean("ToDepair");

		int maxDamage = stack.getMaxDamage();
		int damage = ToolHelper.isBroken(stack) ? maxDamage : stack.getItemDamage();
		int durability = ToolHelper.getMaxDurability(stack) - damage;
			
		boolean shouldAllow = durability > 1 && !toDepair;

		if (!shouldAllow) {
			return false;
		}
		return true;
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleToolModifyEvent(ToolModifyEvent event) {				
		NBTTagCompound comp = TagUtil.getTagSafe(event.getItemStack());
		
		boolean toDepair = comp.getBoolean("ToDepair");

		if (toDepair) {
			int maxDamage = event.getItemStack().getMaxDamage();
			
			event.getItemStack().setItemDamage(maxDamage - 1);
			
			NBTTagList list = comp.getCompoundTag("TinkerData").getTagList("Modifiers", NBT.TAG_STRING);
			
			for (int i = 0; i < list.tagCount(); i++) {
				NBTBase base = list.get(i);
				if (((NBTTagString) base).getString().equals(this.getIdentifier())) {
					list.removeTag(i--);
				} 
			}
			comp.removeTag("ToDepair");
		}
	}
}
