package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.Misc;
import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;

public class Overgrowth extends AbstractTraitLeveled {

	public Overgrowth(int levels) {
		super(Misc.createNonConflictiveName("overgrowth"), 0, 3, levels);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {		
		if (!world.isRemote && world.getWorldTime() % 20 == 0 && entity instanceof EntityLivingBase) {
			
			EntityLivingBase living = (EntityLivingBase) entity;
			if (living.getActiveItemStack() == tool || !ModTraits.overslime.isToolWithTrait(tool)) 
				return;

			int current = ModTraits.overslime.getNumberRemaining(tool);
			int cap = ModTraits.overslime.getNumberMax(tool);
		
			if (current < cap && random.nextFloat() < (this.levels * 0.05)) {
				ModTraits.overslime.addNumberRemaining(tool, 1);
			}
		}
	}
}
