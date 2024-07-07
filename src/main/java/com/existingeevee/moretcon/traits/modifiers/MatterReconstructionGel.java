package com.existingeevee.moretcon.traits.modifiers;

import java.util.ArrayList;

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

public class MatterReconstructionGel extends Modifier {

	public MatterReconstructionGel() {
		super(MiscUtils.createNonConflictiveName("MatterReconstructionGel".toLowerCase()));
		this.addItem(ModItems.matterReconstructionGel);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		rootCompound.setBoolean("ToRepair", true);
	}

	@Override
	public boolean canApplyCustom(ItemStack stack) {
		if (stack.serializeNBT().getCompoundTag("tag").getBoolean("ToRepair") || stack.getItemDamage() <= 0) {
			return false;
		}
		return true;
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleToolModifyEvent(ToolModifyEvent event) {
		if (event.getItemStack().serializeNBT().getCompoundTag("tag").getBoolean("ToRepair")) {
			
			int maxDamage = event.getItemStack().getMaxDamage();
			
			int repairedAmount = Integer.max(event.getItemStack().getItemDamage() - Math.max(256, maxDamage / 10), 0);
			event.getItemStack().setItemDamage(repairedAmount);
			event.getItemStack().serializeNBT().getCompoundTag("tag").removeTag("ToRepair");
			NBTTagList list = event.getItemStack().serializeNBT().getCompoundTag("tag").getCompoundTag("TinkerData").getTagList("Modifiers", NBT.TAG_STRING);
			ArrayList<Integer> toRemove = new ArrayList<Integer>();
			int i = 0;
			for (NBTBase base : list) {
				if (((NBTTagString) base).getString().equals(this.getIdentifier())) {
					toRemove.add(i);
				}
				i++;
			}
			toRemove.forEach(j -> list.removeTag(j));
		}
	}
}
