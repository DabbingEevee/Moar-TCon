package com.existingeevee.moretcon.traits.traits.armor;

import com.existingeevee.moretcon.other.WorldGravityUtils;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.traits.abst.IAdditionalTraitMethods;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Weightless extends AbstractArmorTrait implements IAdditionalTraitMethods {

	public Weightless() {
		super(MiscUtils.createNonConflictiveName("weightless"), TextFormatting.WHITE);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void onEntityItemTick(ItemStack tool, EntityItem entity) {		
		if (WorldGravityUtils.getWorldGravitiationalAcceleration(entity.world, entity.getPositionVector()) == -0.08)
			entity.motionY += 0.039f;
	}

	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent e) {
		EntityLivingBase entity = e.getEntityLiving();

		if (entity.isElytraFlying() || (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isFlying) || entity.onGround)
			return;
		
		double gravity = WorldGravityUtils.getWorldGravitiationalAcceleration(e.getEntityLiving().world, entity.getPositionVector());

		int count = 0;
		
		for (ItemStack s : e.getEntityLiving().getArmorInventoryList()) {
			if (this.isToolWithTrait(s) && !ToolHelper.isBroken(s)) {
				count++;
			}
		}
		if (count > 0) {
			entity.motionY -= gravity * (entity.isSneaking() ? 0.5 : 0.75f) / 4f * count; // ~0.25 gravity
			entity.fallDistance = Math.min(3, entity.fallDistance);
		}
	}

	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent e) {
		double level = 0;
		if (e.getEntityLiving() instanceof EntityPlayer)
			level = ArmorHelper.getArmorAbilityLevel((EntityPlayer) e.getEntityLiving(), this.identifier);

		if (level > 0)
			e.setDamageMultiplier(0.0f);
	}
}