package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class Supercritical extends AbstractTraitLeveled {

	public Supercritical(int lvl) {
		super(MiscUtils.createNonConflictiveName("supercritical"), 0, 3, lvl);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		NBTTagList tagList = TagUtil.getModifiersTagList(tool);
		int index = TinkerUtil.getIndexInCompoundList(tagList, this.getModifierIdentifier());

		if (index > -1 && isCritical) {
			ModifierNBT modifier = ModifierNBT.readTag(tagList.getCompoundTagAt(index));
			float unmodifiedDamage = damage / 1.5f;
			
			return newDamage + unmodifiedDamage * (modifier.level * 0.25f);
		}
		return newDamage;
	}
	
	@Override
	public int getPriority() {
		return -2; //we need this to run last. 
	}
}
