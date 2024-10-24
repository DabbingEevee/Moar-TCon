package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import slimeknights.tconstruct.library.traits.AbstractTrait;


public class Magical extends AbstractTrait {

	public Magical() {
		super(MiscUtils.createNonConflictiveName("magical"), 0x0066ff);
	}

	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical) {
		if (random.nextBoolean() || random.nextBoolean()) {
			return newKnockback;
		}
		target.setVelocity(target.motionX, target.motionY + 2 * newKnockback, target.motionZ);
        if (target instanceof EntityPlayerMP) {
            ((EntityPlayerMP)target).connection.sendPacket(new SPacketEntityVelocity(target));
        }
		return newKnockback + 2.5f;
	}
}