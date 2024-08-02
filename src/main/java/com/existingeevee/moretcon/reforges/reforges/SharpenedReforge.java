package com.existingeevee.moretcon.reforges.reforges;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.reforges.AbstractReforge;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class SharpenedReforge extends AbstractReforge {

	public SharpenedReforge() {
		super(MiscUtils.createNonConflictiveName("reforgesharpened"), 0xc8d6e3);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		return newDamage + damage * (isBarelyDamaged(tool) ? 0.2f : 0.1f);
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
		event.setNewSpeed(event.getOriginalSpeed() * (isBarelyDamaged(tool) ? 0.2f : 0.1f) + event.getNewSpeed());
	}

	boolean isBarelyDamaged(ItemStack tool) {
		return tool.getItemDamage() <= tool.getMaxDamage() * 0.05f;
	}
}
