package com.existingeevee.moretcon.traits.traits;

import java.util.UUID;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Weightless extends AbstractTrait {

	public Weightless() {
		super(Misc.createNonConflictiveName("weightless"), 0);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private static final AttributeModifier WEIGHTLESS = new AttributeModifier(UUID.fromString("aed073df-79af-4de9-b62c-44b5fcc4df1d"), "weightless", 1.00, 2).setSaved(false);

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleWeightless(LivingUpdateEvent event) {
		IAttributeInstance attr = event.getEntityLiving().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED);
		if (attr == null) {
			return;
		}
		if (this.isToolWithTrait(event.getEntityLiving().getHeldItemMainhand())) {
			if (!attr.hasModifier(WEIGHTLESS))
				event.getEntityLiving().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(WEIGHTLESS);
			;
		} else {
			if (attr.hasModifier(WEIGHTLESS))
				event.getEntityLiving().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(WEIGHTLESS);
			;
		}
	}
}
