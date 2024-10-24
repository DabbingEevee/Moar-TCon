package com.existingeevee.moretcon.traits.traits.unique;

import java.util.List;

import com.existingeevee.moretcon.entity.entities.EntityDecayingEffect;
import com.existingeevee.moretcon.entity.entities.EntityDecayingEffect.EnumDecayingEffectType;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class Seismishock extends AbstractTrait {

	public Seismishock() {
		super(MiscUtils.createNonConflictiveName("seismishock"), 0);
	}

	@Override
	public void onHit(ItemStack stack, EntityLivingBase playerIn, EntityLivingBase target, float damage, boolean isCritical) {
		if (isCritical) {
			if (!target.world.isRemote) {
				EntityDecayingEffect decayingEffect = new EntityDecayingEffect(target.getEntityWorld(), EnumDecayingEffectType.SHOCKWAVE, 0, 2f, playerIn.getUniqueID());
				decayingEffect.setPosition(target.posX, target.posY, target.posZ);
				target.getEntityWorld().spawnEntity(decayingEffect);

				for (Entity e : decayingEffect.getAffectedEntities()) {

					if (!(e instanceof EntityLivingBase)) {
						continue;
					}

					EntityLivingBase entity = (EntityLivingBase) e;

					List<ITrait> traits = TinkerUtil.getTraitsOrdered(stack);

					float dmg = damage * 0.5f;
					float dmgOrig = dmg;

					for (ITrait t : traits) {
						dmg = t.damage(stack, playerIn, entity, dmgOrig, dmg, false);

					}

					float hpBefore = entity.getHealth();
					boolean wasHit = entity.attackEntityFrom(decayingEffect.getDamageSource(), dmg);

					for (ITrait t : traits) {
						t.onHit(stack, playerIn, entity, dmg, false);

					}

					for (ITrait t : traits) {
						t.afterHit(stack, playerIn, entity, hpBefore - entity.getHealth(), false, wasHit);
					}
				}
			}
			target.world.playSound(null, target.getPosition(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.PLAYERS, 1, 2);
		}
	}
}
