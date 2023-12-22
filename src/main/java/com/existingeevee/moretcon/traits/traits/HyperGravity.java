package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.StaticVars;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import slimeknights.tconstruct.library.traits.AbstractTrait;

@SuppressWarnings("deprecation")
public class HyperGravity extends AbstractTrait {

	public HyperGravity() {
		super(MiscUtils.createNonConflictiveName("hypergravity"), 0);
	}

	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical) {
		StaticVars.double1 = newKnockback;
		return newKnockback;
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		double dx = player.posX - target.posX;
		double dz = player.posZ - target.posZ;
		double lenXY = Math.sqrt(dx * dx + dz * dz);
		double kb = StaticVars.double1;
		double accelerateVal = kb * 0.9;

		if (!player.world.isRemote) {
			target.motionX += dx / lenXY * accelerateVal;
			target.motionY += accelerateVal / 8;
			target.motionZ += dz / lenXY * accelerateVal;
			target.velocityChanged = true;
		}
		if (target instanceof EntityPlayerMP) {
			((EntityPlayerMP) target).connection.sendPacket(new SPacketEntityVelocity(target));
		}
	}

	@Override
	public int getPriority() {
		return Integer.MAX_VALUE / 2; //we need this to run last. divide by 2 to prevent wierd overflow issues
	}
}
