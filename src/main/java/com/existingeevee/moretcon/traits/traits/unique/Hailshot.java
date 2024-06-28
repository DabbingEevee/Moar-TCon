package com.existingeevee.moretcon.traits.traits.unique;

import java.util.ConcurrentModificationException;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.other.utils.SoundHandler;
import com.existingeevee.moretcon.traits.traits.abst.BooleanTrackerTrait;
import com.existingeevee.moretcon.traits.traits.abst.IAdditionalTraitMethods;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.traits.IProjectileTrait;

@SuppressWarnings("deprecation")
public class Hailshot extends BooleanTrackerTrait implements IProjectileTrait, IAdditionalTraitMethods {

	public Hailshot() {
		super(MiscUtils.createNonConflictiveName("hailshot"), 0);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void onLaunch(EntityProjectileBase projectileBase, World world, EntityLivingBase shooter) {
		if (shooter.isSneaking() && isActive(projectileBase.tinkerProjectile.getItemStack())) {
			this.setEmpowered(projectileBase, true);
		}
	}

	@Override
	public void onAmmoConsumed(ItemStack ammo, @Nullable EntityLivingBase entity) {
		if (isActive(ammo) && (entity == null ? true : entity.isSneaking())) {
			setActive(ammo, false);
		}
	}

	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed) {
		if (target instanceof EntityLivingBase) {
			((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 5 * 20, 3));
			if (((EntityLivingBase) target).getHealth() <= 0)
				this.setCanEmpower(attacker, true);
		}

		if (isEmpowered(projectile)) {
			this.setEmpowered(projectile, false);
			world.playSound(null, projectile.posX, projectile.posY, projectile.posZ, SoundHandler.ICY_EXPLOSION, SoundCategory.PLAYERS, 3, 0.5f);

			for (Entity e : world.getEntitiesInAABBexcluding(target, projectile.getEntityBoundingBox().grow(2), e -> e != attacker && e != projectile && e != target)) {
				if (!(e instanceof EntityLivingBase))
					continue;

				float origPower = projectile.tinkerProjectile.getPower();
				float damageMultiplier = (float) (0.5 * Math.pow(Math.E, -0.25 * target.getDistanceSq(e)) + 0.25);

				projectile.tinkerProjectile.setPower(origPower * damageMultiplier);
				projectile.onHitEntity(new RayTraceResult(e));
				projectile.tinkerProjectile.setPower(origPower);

			}
		}
	}

	public boolean isEmpowered(EntityProjectileBase projectileBase) {
		return projectileBase.getTags().contains(getModifierIdentifier() + ".empowered");
	}

	public void setEmpowered(EntityProjectileBase projectileBase, boolean state) {
		if (state) {
			projectileBase.getTags().add(getModifierIdentifier() + ".empowered");
		} else {
			projectileBase.getTags().remove(getModifierIdentifier() + ".empowered");
		}
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (this.canEmpower(entity) && !this.isActive(tool)) {
			this.setActive(tool, true);
			this.setCanEmpower(entity, false);
		}
	}

	@SubscribeEvent
	public void onTick(TickEvent.PlayerTickEvent e) {
		if (e.phase == Phase.END) {
			try {
				this.setCanEmpower(e.player, false);
			} catch (ConcurrentModificationException ex) {
			}
		}
	}

	@Override
	public void onProjectileUpdate(EntityProjectileBase projectile, World world, ItemStack toolStack) {
	}

	@Override
	public void onMovement(EntityProjectileBase projectile, World world, double slowdown) {
	}

	@Override
	public boolean getDefaultState(ItemStack tool) {
		return false;
	}

	@Override
	public String getStringToRender(ItemStack tool) {
		return this.isActive(tool) ? I18n.translateToLocal("booltracker.empowered.name") : null;
	}

	private boolean canEmpower(Entity shooter) {
		if (shooter == null)
			return false;
		return shooter.getTags().contains(getModifierIdentifier() + ".can_empower");
	}

	private void setCanEmpower(Entity attacker, boolean newState) {
		if (newState) {
			attacker.getTags().add(getModifierIdentifier() + ".can_empower");
		} else {
			attacker.getTags().remove(getModifierIdentifier() + ".can_empower");
		}
	}
}
