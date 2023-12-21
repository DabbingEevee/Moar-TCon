package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Liquid extends AbstractTrait {

	public Liquid() {
		super(MiscUtils.createNonConflictiveName("liquid"), 0);
	}

	@Override
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
	    return ((newDamage - damage) + (Math.round((entity.world.getBiome(entity.getPosition()).getTemperature(entity.getPosition()) + 2) / 2) * damage));
	}
	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		newDamage += (player.world.getBiome(player.getPosition()).getTemperature(player.getPosition()) + 2);
		return super.damage(tool, player, target, damage, newDamage, isCritical);
	}
}
