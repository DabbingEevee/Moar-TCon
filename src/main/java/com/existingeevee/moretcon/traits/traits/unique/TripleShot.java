package com.existingeevee.moretcon.traits.traits.unique;

import com.existingeevee.math.Quaternion;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import slimeknights.tconstruct.library.capability.projectile.TinkerProjectileHandler;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.tools.ranged.ProjectileCore;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;

public class TripleShot extends AbstractProjectileTrait {

	public TripleShot() {
		super(MiscUtils.createNonConflictiveName("tripleshot"), 0xFFFFFF);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static final ThreadLocal<Boolean> IS_ALREADY_PROCING = ThreadLocal.withInitial(() -> false);

	@Override
	public void onLaunch(EntityProjectileBase projectileBase, World world, EntityLivingBase shooter) {
		if (IS_ALREADY_PROCING.get()) {
			return;
		}

		if (projectileBase != null && shooter != null) {
			TinkerProjectileHandler ticProjectile = projectileBase.tinkerProjectile;
			if (!world.isRemote && ticProjectile.getItemStack().getItem() instanceof ProjectileCore) {
				IS_ALREADY_PROCING.set(true);
				shootProjectile(ticProjectile, shooter, -11.25f);
				shootProjectile(ticProjectile, shooter, 11.25f);
				IS_ALREADY_PROCING.set(false);
			}
			if (shooter instanceof EntityPlayer) {
				((EntityPlayer) shooter).getCooldownTracker().setCooldown(ticProjectile.getLaunchingStack().getItem(), 10);
			}
		}
	}

	public static void shootProjectile(TinkerProjectileHandler ticProjectile, EntityLivingBase shooter, float angle) {
		EntityProjectileBase proj = ((ProjectileCore) ticProjectile.getItemStack().getItem()).getProjectile(ticProjectile.getItemStack().copy(), ticProjectile.getLaunchingStack().copy(), shooter.world, shooter instanceof EntityPlayer ? (EntityPlayer) shooter : null, 2.1f, 0f, 1f, false);
		shooter.world.spawnEntity(proj);
		proj.pickupStatus = PickupStatus.CREATIVE_ONLY;

		Vec3d vec31 = shooter.getVectorForRotation(shooter.rotationPitch - 90, shooter.rotationYaw);
		Quaternion quaternion = new Quaternion(vec31, angle, true);
		Vec3d vec3 = shooter.getLookVec();
		vec3 = quaternion.transformVector(vec3);
		proj.shoot(vec3.x, vec3.y, vec3.z, 2f, 0);
	}
}