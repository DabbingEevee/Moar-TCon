package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class Overgrowth extends AbstractTraitLeveled {

	public Overgrowth(int levels) {
		super(MiscUtils.createNonConflictiveName("overgrowth"), 0, 3, levels);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {		
		if (!world.isRemote && world.getWorldTime() % 20 == 0 && entity instanceof EntityLivingBase) {
			
			EntityLivingBase living = (EntityLivingBase) entity;
			if (living.getActiveItemStack() == tool || !ModTraits.overslime.isToolWithTrait(tool)) 
				return;

			int current = ModTraits.overslime.getNumber(tool);
			int cap = ModTraits.overslime.getNumberMax(tool);
		
			ModifierNBT tag = ModifierNBT.readTag(TinkerUtil.getModifierTag(tool, this.getModifierIdentifier()));
			
			if (current < cap && random.nextFloat() < (tag.level * 0.05)) {
				ModTraits.overslime.addNumber(tool, 1);
			}
		}
	}
}
