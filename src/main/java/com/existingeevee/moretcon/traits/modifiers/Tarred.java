package com.existingeevee.moretcon.traits.modifiers;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;

public class Tarred extends ModifierTrait  {

	public Tarred() {
		super(MiscUtils.createNonConflictiveName("modtarred"), 0x333333);
		this.addItem("dripTar", 5, 1);
		this.addAspects(new ModifierAspect.SingleAspect(this));
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof EntityLivingBase) {
			if (((EntityLivingBase) entity).getHeldItemMainhand().equals(tool)) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 1, 1));
			}
		}
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
		if (random.nextFloat() < 0.75) {
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30 * 20, Math.round(random.nextFloat() * 2) + 1, false, true));
		}
		if (random.nextFloat() < 0.10) {
			target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 30 * 20, 1, false, true));
		}
	}
}