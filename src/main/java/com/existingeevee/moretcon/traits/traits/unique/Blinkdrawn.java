package com.existingeevee.moretcon.traits.traits.unique;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class Blinkdrawn extends AbstractTrait {

	public Blinkdrawn() {
		super(MiscUtils.createNonConflictiveName("blinkdrawn"), 0);
	}

	@Override
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
		return newDamage + 3;
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		super.applyEffect(rootCompound, modifierTag);
		if (TinkerUtil.hasCategory(rootCompound, Category.LAUNCHER)) {
			ProjectileLauncherNBT launcherData = new ProjectileLauncherNBT(TagUtil.getToolTag(rootCompound));
			launcherData.drawSpeed = Float.MAX_VALUE; //YES
			launcherData.bonusDamage -= 6; //Very large penalty
			TagUtil.setToolTag(rootCompound, launcherData.get());
		}
	}
}