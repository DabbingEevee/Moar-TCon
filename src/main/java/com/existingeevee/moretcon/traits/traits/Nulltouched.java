package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class Nulltouched extends AbstractTraitLeveled {

	public Nulltouched(int level) {
		super(MiscUtils.createNonConflictiveName("nulltouched"), String.valueOf(level), 0x00ed00, 3, 1); 
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
		ModifierNBT tag = ModifierNBT.readTag(TinkerUtil.getModifierTag(tool, this.getModifierIdentifier()));
		attackEntitySecondary(DamageSource.OUT_OF_WORLD, 0.5f * tag.level, target, false, true);
	}
}