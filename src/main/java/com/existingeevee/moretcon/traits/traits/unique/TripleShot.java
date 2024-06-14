package com.existingeevee.moretcon.traits.traits.unique;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
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

	//@ObjectHolder("tconstruct:shuriken")
	//public static final Item shuriken = null;

	@Override
	public void onLaunch(EntityProjectileBase projectileBase, World world, EntityLivingBase shooter) {

		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

		int found = 0;

		for (StackTraceElement e : stacktrace) {
			if (e.getClassName().equals(this.getClass().getName())) {
				if (found++ >= 1) {
					return;
				}
			}
		}

		if(projectileBase != null && shooter != null) {
			TinkerProjectileHandler ticProjectile = projectileBase.tinkerProjectile;
			if(!world.isRemote) {
				EntityProjectileBase projOne = ((ProjectileCore) ticProjectile.getItemStack().getItem()).getProjectile(ticProjectile.getItemStack().copy(), ticProjectile.getLaunchingStack().copy(), projectileBase.getEntityWorld(), shooter instanceof EntityPlayer ? (EntityPlayer) shooter : null, 2.1f, 0f, 1f, false);
				world.spawnEntity(projOne);
				projOne.pickupStatus = PickupStatus.CREATIVE_ONLY;
				projOne.shoot(shooter, shooter.rotationPitch, shooter.rotationYaw - 11.25f, 0, 2f, 0);

			    EntityProjectileBase projTwo = ((ProjectileCore) ticProjectile.getItemStack().getItem()).getProjectile(ticProjectile.getItemStack().copy(), ticProjectile.getLaunchingStack().copy(), projectileBase.getEntityWorld(), shooter instanceof EntityPlayer ? (EntityPlayer) shooter : null, 2.1f, 0f, 1f, false);
			    world.spawnEntity(projTwo);
				projTwo.pickupStatus = PickupStatus.CREATIVE_ONLY;
				projTwo.shoot(shooter, shooter.rotationPitch, shooter.rotationYaw + 11.25f, 0, 2f, 0);
			}
			if (shooter instanceof EntityPlayer) {
				((EntityPlayer) shooter).getCooldownTracker().setCooldown(ticProjectile.getLaunchingStack().getItem(), 10);
			}
		}
	}


	/*private static Entity copyEntity(Entity e) {
		NBTTagCompound data = e.writeToNBT(new NBTTagCompound());
		Entity entityCopy = EntityRegistry.getEntry(e.getClass()).newInstance(e.world);
		entityCopy.readFromNBT(data);
		entityCopy.setUniqueId(MathHelper.getRandomUUID());
		return entityCopy;
	}*/
}

//end bricks
