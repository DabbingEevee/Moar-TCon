package com.existingeevee.moretcon.traits.modifiers;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ProjectileModifierTrait;
import slimeknights.tconstruct.library.tools.ranged.IProjectile;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.tools.TinkerTraits;

public class Impactor extends ProjectileModifierTrait {

	public Impactor() {
		super(Misc.createNonConflictiveName("modimpactor"), 0xFFFFFF);
		this.addItem(Blocks.ANVIL, 1);
		
		this.addAspects(ModifierAspect.projectileOnly);//this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this), ModifierAspect.freeModifier);
	}
 
	  @Override
	  public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed) {
		  float speed = (float) impactSpeed;
		  if (TinkerUtil.hasTrait(TagUtil.getTagSafe(ammoStack), TinkerTraits.endspeed.getIdentifier())) speed = 5;
		  if(target instanceof EntityLivingBase) {
			  if (!(target.hurtResistantTime > 0) || target.hurtResistantTime == 20) {
					DamageSource source = attacker instanceof EntityPlayer ? DamageSource.causePlayerDamage((EntityPlayer) attacker) : DamageSource.causeMobDamage(attacker);
					Misc.trueDamage((EntityLivingBase) target, speed, source, true);
					//((EntityLivingBase) target).setHealth(((EntityLivingBase) target).getHealth() - speed);
			  }
		  }
		  super.afterHit(projectile, world, ammoStack, attacker, target, impactSpeed);
	  }

	  @Override
	  public boolean canApplyCustom(ItemStack stack) {
		  if (stack.getItem() instanceof IProjectile) {
			  return super.canApplyCustom(stack);
		  }
		  return false;
	  }
}

//end bricks
