package com.wordpress.craftminemods.tconmaterial.tconstuff.traitclasses;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.modifiers.ProjectileModifierTrait;

public class Impactor extends ProjectileModifierTrait{

	public Impactor() {
		super("modImpactor", 0xFFFFFF);
		// TODO Auto-generated constructor stub
	}

	  @Override
	  public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed) {
		  if(target instanceof EntityLivingBase) {
			  target.attackEntityFrom(DamageSource.causeArrowDamage(projectile, target), (float) (impactSpeed*2));

		  }
	  }  
	  
}

//end bricks
