package com.existingeevee.moretcon.traits.traits.unique;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.traits.abst.IAdditionalTraitMethods;
import com.existingeevee.moretcon.traits.traits.abst.NumberTrackerTrait;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.traits.IProjectileTrait;
import thebetweenlands.common.entity.mobs.EntityTinySludgeWormHelper;

public class Wormed extends NumberTrackerTrait implements IAdditionalTraitMethods, IProjectileTrait {

	public Wormed() {
		super(MiscUtils.createNonConflictiveName("wormed"), 0);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {		
		if (!world.isRemote && world.getWorldTime() % 20 == 0 && entity instanceof EntityLivingBase) {
			
			EntityLivingBase living = (EntityLivingBase) entity;
			if (living.getActiveItemStack() == tool) 
				return;

			int current = getNumber(tool);
			int cap = getNumberMax(tool);
		
			if (current < cap && random.nextFloat() < 0.025) {
				addNumber(tool, 1);
			}
		}
	}
	
	@Override
	public void onPickup(EntityProjectileBase projectileBase, ItemStack ammo, EntityLivingBase entity) {
		if (getNumber(ammo) < getNumberMax(ammo) && projectileBase.getTags().contains(this.getModifierIdentifier() + ".active")) {
			addNumber(ammo, 1);
		}
	}
	
	@Override
	public void onLaunch(EntityProjectileBase projectileBase, World world, @Nullable EntityLivingBase shooter) {
		if (getNumber(projectileBase.tinkerProjectile.getItemStack()) > 0 && (shooter == null ? true : shooter.isSneaking())) {
			projectileBase.getTags().add(this.getModifierIdentifier() + ".active");
		}
	}
	
	@Override
	public void onAmmoConsumed(ItemStack ammo, @Nullable EntityLivingBase entity) {
		if (getNumber(ammo) > 0 && (entity == null ? true : entity.isSneaking())) {
			removeNumber(ammo, 1);
		}
	}

	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed) {
		if (!world.isRemote && target instanceof EntityLivingBase && projectile.getTags().contains(this.getModifierIdentifier() + ".active")) {
			EntityTinySludgeWormHelper worm = new EntityTinySludgeWormHelper(world);
			worm.setLocationAndAngles(projectile.posX, projectile.posY, projectile.posZ, projectile.rotationYaw, projectile.rotationPitch);
			worm.setAttackTarget((EntityLivingBase) target);
			if (attacker instanceof EntityPlayer) {
				worm.setOwnerId(attacker.getUniqueID());
			}
			world.spawnEntity(worm);
			projectile.setDead();
		}
	}

	@Override
	public int getNumberMax(ItemStack stack) {
		return 10;
	}

	@Override
	public int getDefaultNumber(ItemStack stack) {
		return getNumberMax(stack);
	}
	
	@Override
	public void onProjectileUpdate(EntityProjectileBase projectile, World world, ItemStack toolStack) {
		//donbcar
	}

	@Override
	public void onMovement(EntityProjectileBase projectile, World world, double slowdown) {
		//donbcar
	}
}