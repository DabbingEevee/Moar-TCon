package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Voidic extends AbstractTrait {

	public Voidic() {
		super(Misc.createNonConflictiveName("voidic"), 0);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		if (player.getPosition().getY() < 7) {
			newDamage += damage * 0.25;
		}
		return super.damage(tool, player, target, damage, newDamage, isCritical);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {

		if (Math.random() < 0.025 && world.isRemote && entity instanceof EntityLivingBase) {
			if (entity.getPosition().getY() < 4) {
				tool.damageItem(-1, (EntityLivingBase) entity);
			}
		}
		super.onUpdate(tool, world, entity, itemSlot, isSelected);
	}
}
