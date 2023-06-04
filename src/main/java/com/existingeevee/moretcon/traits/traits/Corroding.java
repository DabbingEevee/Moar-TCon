package com.existingeevee.moretcon.traits.traits;

import java.util.Random;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import thebetweenlands.api.item.ICorrodible;

public class Corroding extends AbstractTrait {

	public Corroding() {
		super(Misc.createNonConflictiveName("corroding"), 0);
        MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		rootCompound.setBoolean("NeedAdditonalCorrodingData", true);
		super.applyEffect(rootCompound, modifierTag);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (tool.getItem() instanceof ICorrodible) {
			ICorrodible corrodible = (ICorrodible) tool.getItem();
			if (tool.getTagCompound().getBoolean("NeedAdditonalCorrodingData")) {
				tool.getTagCompound().setBoolean("NeedAdditonalCorrodingData", false);
				tool.getTagCompound().setInteger("CorrodingPriorCorrosionValue", corrodible.getCorrosion(tool));
			} else {
				if (corrodible.getCorrosion(tool) > tool.getTagCompound().getInteger("CorrodingPriorCorrosionValue")) {
					tool.getTagCompound().setInteger("CorrodingPriorCorrosionValue", corrodible.getCorrosion(tool));
					if (new Random().nextInt(2) == 0) corrodible.setCoating(tool, (int) (corrodible.getCoating(tool) + new Random().nextInt(3) + 1));
				} else if (corrodible.getCorrosion(tool) < tool.getTagCompound().getInteger("CorrodingPriorCorrosionValue")) {
					tool.getTagCompound().setInteger("CorrodingPriorCorrosionValue", corrodible.getCorrosion(tool));
				}
			}
		}
	}
}