package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Fireslime extends AbstractTrait {

	public Fireslime() {
		super(MiscUtils.createNonConflictiveName("fireslime"), 0);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isRemote && entity instanceof EntityLivingBase) {

			EntityLivingBase living = (EntityLivingBase) entity;
			if (living.getActiveItemStack() == tool || !ModTraits.overslime.isToolWithTrait(tool)) {
				return;
			}

			int current = ModTraits.overslime.getNumber(tool);
			int cap = ModTraits.overslime.getNumberMax(tool);

			if (current < cap && random.nextFloat() < 0.05 && entity.isBurning()) {
				ModTraits.overslime.addNumber(tool, 1);
			}
		}
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
		if (event.getEntity().isBurning()) {
			event.setNewSpeed(event.getNewSpeed() * 3f);
		}
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		if (player.isBurning()) {
			newDamage *= 1.5;
		}
		return newDamage;
	}
}
