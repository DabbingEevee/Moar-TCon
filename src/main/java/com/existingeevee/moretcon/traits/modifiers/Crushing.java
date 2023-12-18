package com.existingeevee.moretcon.traits.modifiers;

import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.other.Misc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;

public class Crushing extends ModifierTrait {

	public Crushing() {
		super(Misc.createNonConflictiveName("modcrushing"), 0x555555);
		this.addItem(ModItems.crushingModifier);
		this.addAspects(new ModifierAspect.SingleAspect(this));
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		float actualDMG = newDamage;
		if (player instanceof EntityPlayer) 
			actualDMG *= ((EntityPlayer) player).getCooledAttackStrength(0.5f);
		target.getEntityData().setFloat(this.getIdentifier(), actualDMG);
		return super.damage(tool, player, target, damage, newDamage * 0.95f, isCritical);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit) {
			System.out.println(target.getEntityData().getFloat(this.getIdentifier()) * 0.05f);
			DamageSource source = player instanceof EntityPlayer ? DamageSource.causePlayerDamage((EntityPlayer) player) : DamageSource.causeMobDamage(player);
			Misc.trueDamage(target, target.getEntityData().getFloat(this.getIdentifier()) * 0.05f, source, false);
		}
	}

	@Override
	public int getPriority() {
		return 9000;
	}
}

//Misc.executeInNTicks(() -> {
//int d = target.hurtResistantTime;
//target.hurtResistantTime = 0;
//target.hurtResistantTime = d;
//}, 1);
