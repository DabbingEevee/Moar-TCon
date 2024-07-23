package com.existingeevee.moretcon.reforges.reforges;

import java.util.UUID;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.reforges.AbstractReforge;
import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class HeavyReforge extends AbstractReforge {

	public HeavyReforge() {
		super(MiscUtils.createNonConflictiveName("reforgeheavy"), 0x898989);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		return newDamage + damage * 0.15f;
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
		event.setNewSpeed(event.getOriginalSpeed() * 0.15f + event.getNewSpeed());
	}

	public static final UUID HEAVY_SLOW = UUID.fromString("afde8469-ae40-df76-9099-002f90370708");
	public static final UUID HEAVY_SPED = UUID.fromString("afde8469-ae40-df76-9383-002f90370708");

	@Override
	public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
		if (slot != EntityEquipmentSlot.MAINHAND)
			return;

		attributeMap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(HEAVY_SLOW, "slow modifier", -0.1, 2));
		attributeMap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(HEAVY_SPED, "sped modifier", -0.1, 2));
	}
}
