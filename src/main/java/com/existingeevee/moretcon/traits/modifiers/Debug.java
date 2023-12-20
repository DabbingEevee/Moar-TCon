package com.existingeevee.moretcon.traits.modifiers;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;

public class Debug extends ModifierTrait {

	public Debug() {
		super(Misc.createNonConflictiveName("moddebug"), 0xFFFFFF);
		this.addItem(Item.getItemFromBlock(Blocks.COMMAND_BLOCK));
		
		this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this));
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (isSelected && tool.serializeNBT().getCompoundTag("tag").getBoolean("logIt")) {
			if (!world.isRemote) {
				entity.sendMessage(new TextComponentString(tool.serializeNBT().toString()));
			}
			tool.serializeNBT().getCompoundTag("tag").setBoolean("logIt", false);
		}
		if (!isSelected && !tool.serializeNBT().getCompoundTag("tag").getBoolean("logIt")) {
			tool.serializeNBT().getCompoundTag("tag").setBoolean("logIt", true);
		}
	}
}


