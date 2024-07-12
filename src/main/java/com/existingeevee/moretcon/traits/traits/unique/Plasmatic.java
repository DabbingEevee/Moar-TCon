package com.existingeevee.moretcon.traits.traits.unique;

import java.lang.reflect.Field;
import java.util.List;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Plasmatic extends AbstractTrait {

	public Plasmatic() {
		super(MiscUtils.createNonConflictiveName("plasmatic"), 0);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical) {
		target.setFire(5);
	}

	public static final ThreadLocal<Boolean> IS_ALREADY_PROCING = ThreadLocal.withInitial(() -> false);

	private static final Field ticksSinceLastAtt = ObfuscationReflectionHelper.findField(EntityLivingBase.class, "field_184617_aD");

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (!wasHit || IS_ALREADY_PROCING.get())
			return;
		this.proc(player, target);
	}

	@SubscribeEvent
	public void onMouseClick(PlayerInteractEvent.LeftClickBlock event) {
		ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
		if (this.isToolWithTrait(stack) && !ToolHelper.isBroken(stack)) {
			proc(event.getEntityPlayer(), null);
		}
	}
	
	public void proc(EntityLivingBase player, Entity ignore) {
		Vec3d playerVision = player.getLookVec();
		AxisAlignedBB reachDistance = player.getEntityBoundingBox().grow(4.0D);
		List<Entity> locatedEntities = player.world.getEntitiesWithinAABB(Entity.class, reachDistance);
		double foundLen = 0.0D;
		for (Entity ent : locatedEntities) {
			if (ent == player || ent == ignore) {
				continue;
			}

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
			if (dot < 1.0 - 0.125 / len) {
				continue;
			}
			if (foundLen == 0.0 || len < foundLen) {
				try {
					IS_ALREADY_PROCING.set(true);
					int orig = ticksSinceLastAtt.getInt(player);
					((EntityPlayer) player).attackTargetEntityWithCurrentItem(ent);
					ticksSinceLastAtt.setInt(player, orig);
					IS_ALREADY_PROCING.set(false);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
}