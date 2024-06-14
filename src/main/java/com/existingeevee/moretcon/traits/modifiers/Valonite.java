package com.existingeevee.moretcon.traits.modifiers;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;

public class Valonite extends ModifierTrait {

	public Valonite() {
		super(MiscUtils.createNonConflictiveName("modValonite"), 0xcab1ca);
		this.addItem("gemValonite");

		this.addAspects(new ModifierAspect.SingleAspect(this));
	}

	@Override
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
		if (!entity.world.isRemote && random.nextBoolean()) {
			return 0;
		}
		return newDamage;
	}
}