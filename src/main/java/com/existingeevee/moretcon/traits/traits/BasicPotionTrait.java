package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.math.Equation;
import com.existingeevee.math.Monomial;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class BasicPotionTrait extends AbstractTrait {

	private Equation amplifier = new Monomial(1, 0);
	private Equation duration = new Monomial(1, 0);
	private Potion potion = null;
	
	public BasicPotionTrait(Equation amplifierCalc, Equation durationCalc, Potion pot, String id, int col) {
		super(MiscUtils.createNonConflictiveName(id), col);
		if (amplifierCalc != null) this.amplifier = amplifierCalc;
		if (durationCalc != null) this.duration = durationCalc;
		this.potion = pot;
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (potion == null) return;
		target.addPotionEffect(new PotionEffect(potion, (int) amplifier.evaluate(damageDealt) , (int) duration.evaluate(damageDealt)));
	}
}
