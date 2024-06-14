package com.existingeevee.moretcon.traits.traits.unique;

import com.existingeevee.moretcon.entity.entities.EntityDecayingEffect;
import com.existingeevee.moretcon.entity.entities.EntityDecayingEffect.EnumDecayingEffectType;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Seismishock extends AbstractTrait {

	public Seismishock() {
		super(MiscUtils.createNonConflictiveName("seismishock"), 0);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
		if (isCritical) {
			if (!target.world.isRemote) {
				EntityDecayingEffect en = new EntityDecayingEffect(target.getEntityWorld(), EnumDecayingEffectType.SHOCKWAVE, damage * 0.5, 2f, player.getUniqueID());

				en.setPosition(target.posX, target.posY, target.posZ);
				target.getEntityWorld().spawnEntity(en);
			}
			target.world.playSound(null, target.getPosition(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.PLAYERS, 1, 2);
		}
	}
}
