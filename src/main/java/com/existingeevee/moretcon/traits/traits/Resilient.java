package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;


public class Resilient extends AbstractTrait {

	public Resilient() {
		super(Misc.createNonConflictiveName("resilient"), 0x8e3acf);
	}
	@Override
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
	    if (random.nextBoolean() && random.nextBoolean() && !entity.isPotionActive(MobEffects.MINING_FATIGUE)) {
	    	entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, damage * 40, 0, false, true));
	    	return (newDamage - 1 >= 0) ? newDamage - 1 : 0;
	    }
	    return newDamage;
	}
}