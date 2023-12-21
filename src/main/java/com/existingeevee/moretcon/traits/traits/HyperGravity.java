package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class HyperGravity extends AbstractTrait {

	public HyperGravity() {
		super(MiscUtils.createNonConflictiveName("hypergravity"), 0);
	}

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
    	double dx = player.posX - target.posX;
		double dz = player.posZ - target.posZ;
		double lenXY = Math.sqrt(dx*dx + dz*dz);
		double kb = 1; //a^2 + b^2 = c^2
		for (ITrait trait : ToolHelper.getTraits(tool)) {
			kb = trait.knockBack(tool, player, target, damageDealt, 1, (float) kb, wasCritical);
		}
		double accelerateVal = kb * 0.9;

		if(!player.world.isRemote) {
			target.motionX += dx / lenXY * accelerateVal;
			target.motionY += accelerateVal / 8;
			target.motionZ += dz / lenXY * accelerateVal;
			target.velocityChanged = true;
		}
        if (target instanceof EntityPlayerMP) {
            ((EntityPlayerMP)target).connection.sendPacket(new SPacketEntityVelocity(target));
        }
    }
}
