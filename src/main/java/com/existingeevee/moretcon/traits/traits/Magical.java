package com.existingeevee.moretcon.traits.traits;

import java.util.Random;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import slimeknights.tconstruct.library.traits.AbstractTrait;


public class Magical extends AbstractTrait {

	//public static final Trait hammer_sickle = new Trait(identifier, color);
	public Magical() {
		super(Misc.createNonConflictiveName("magical"), 0x0066ff);
	}

	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical) {
		if (new Random().nextBoolean() || new Random().nextBoolean()) return newKnockback;
		target.setVelocity(target.motionX, target.motionY + 2 * newKnockback, target.motionZ);
        if (target instanceof EntityPlayerMP) {
            ((EntityPlayerMP)target).connection.sendPacket(new SPacketEntityVelocity(target));
        }
		return newKnockback + 2.5f;
	}
}