package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class Fragile extends AbstractTrait {

	public Fragile() {
		super(MiscUtils.createNonConflictiveName("fragile"), 0);
	}

	@Override
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
		return newDamage * 5;
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		if (!TinkerUtil.hasTrait(rootCompound, identifier)) {
			ToolNBT data = TagUtil.getToolStats(rootCompound);
			data.attack *= 1.4;
			data.speed *= 1.4;
			data.attackSpeedMultiplier *= 1.1f;
			
			TagUtil.setToolTag(rootCompound, data.get());
		}
		super.applyEffect(rootCompound, modifierTag);
	}
}