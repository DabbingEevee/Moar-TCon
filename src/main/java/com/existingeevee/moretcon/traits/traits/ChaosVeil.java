package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

//TODO
public class ChaosVeil extends AbstractTrait {

	public ChaosVeil() { 
		super(MiscUtils.createNonConflictiveName("chaos_veil"), 0);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		//newDamage += (player.getEntityWorld().provider.getDimension() == AetherConfig.dimension.aether_dimension_id ? damage / 4f : 0);
		return super.damage(tool, player, target, damage, newDamage, isCritical);
	}
}
