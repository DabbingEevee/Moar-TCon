package com.existingeevee.moretcon.traits.traits;

import java.util.UUID;

import com.existingeevee.moretcon.inits.ModPotions;
import com.existingeevee.moretcon.item.ItemPerimimoss;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Mosstacular extends AbstractTrait {

	protected static final AttributeModifier MOSS_MODIFIER = new AttributeModifier(UUID.fromString("aedaaadf-79af-4de9-b62c-44aaacc4333f"), "moss", 0.25, 1);
	
	public Mosstacular() {
		super(MiscUtils.createNonConflictiveName("mosstacular"), 0xffffff);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent e) {
		if (!(e.getEntityLiving() instanceof EntityPlayer) || e.getEntity().world.isRemote)
			return;

		EntityPlayer playerIn = (EntityPlayer) e.getEntityLiving();

		IAttributeInstance attr = playerIn.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH);
		
		if (ItemPerimimoss.hasMosstacular(playerIn) && e.getEntityLiving().isPotionActive(ModPotions.mossy)) {
			onUpdateIfInHotbar(playerIn);
			if (!attr.hasModifier(MOSS_MODIFIER)) {
				attr.applyModifier(MOSS_MODIFIER);
			}
		} else {
			if (attr.hasModifier(MOSS_MODIFIER)) {
				attr.removeModifier(MOSS_MODIFIER);
			}
		}
	}

	public void onUpdateIfInHotbar(EntityPlayer playerIn) {
		if (playerIn.ticksExisted % 60 == 0)
			playerIn.heal(1);
		
		if (playerIn.getAbsorptionAmount() <= 0)
			playerIn.setAbsorptionAmount(1);
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
		if (event.getEntityLiving().isPotionActive(ModPotions.mossy)) {
			event.setNewSpeed(event.getOriginalSpeed() + event.getOriginalSpeed() * 0.5f);
		}
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		if (player.isPotionActive(ModPotions.mossy)) {
			newDamage += damage * 0.5 + 2;
		}
		return newDamage;
	}

	@Override
	public boolean isToolWithTrait(ItemStack itemStack) {
		return super.isToolWithTrait(itemStack);
	}
}
