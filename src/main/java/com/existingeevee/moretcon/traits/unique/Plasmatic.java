package com.existingeevee.moretcon.traits.unique;

import java.util.List;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Plasmatic extends AbstractTrait {

	public Plasmatic() {
		super(Misc.createNonConflictiveName("plasmatic"), 0);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical) {
		target.setFire(5);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (!wasHit) return;
		
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        int amount = 0;

        for(StackTraceElement i : elements) {
            if (i.getClassName().equals(this.getClass().getName())) {
                amount++;
            }

            if (amount > 1) {
                return;
            }
            
        }
        
        Vec3d playerVision = player.getLookVec();
        AxisAlignedBB reachDistance = player.getEntityBoundingBox().grow(4.0D);
        List<Entity> locatedEntities = player.world.getEntitiesWithinAABB(Entity.class, reachDistance);
        double foundLen = 0.0D;
        for (Object o : locatedEntities) {
            if (o == player) {
                continue;
            }
            Entity ent = (Entity) o;
            if (!ent.canBeCollidedWith()) {
                continue;
            }
            Vec3d vec = new Vec3d(ent.posX - player.posX, ent.getEntityBoundingBox().minY + ent.height / 2f - player.posY - player.getEyeHeight(), ent.posZ - player.posZ);
            double len = vec.lengthVector();
            if (len > 4F) {
                continue;
            }
            vec = vec.normalize();
            double dot = playerVision.dotProduct(vec);
            if (dot < 1.0 - 0.125 / len || !player.canEntityBeSeen(ent)) {
                continue;
            }
            if (foundLen == 0.0 || len < foundLen) {
                ((EntityPlayer) player).attackTargetEntityWithCurrentItem(ent);
            }
        }
	}
}
//particle flame ~ ~.5 ~ 0 0 0 -0.05 100
//particle smoke ~ ~.5 ~ 0 0 0 -0.05 300
//particle fireworksSpark ~ ~ ~ 0 0 0 0.1 100