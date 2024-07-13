package com.existingeevee.moretcon.traits.traits.unique;

import java.lang.reflect.Field;
import java.util.List;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
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
		if (!wasHit || IS_ALREADY_PROCING.get() || !(player instanceof EntityPlayer))
			return;
		this.proc((EntityPlayer) player, target);
	}

	@SubscribeEvent
	public void onMouseClick(PlayerInteractEvent.LeftClickBlock event) {
		ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
		if (this.isToolWithTrait(stack) && !ToolHelper.isBroken(stack)) {
			proc(event.getEntityPlayer(), null);
		}
	}

	public void proc(EntityPlayer player, Entity ignore) {
		double maxRange = 4.0D;

		Vec3d start = player.getPositionEyes(0.5f);
		Vec3d lookVec = player.getLook(0.5f);
		Vec3d end = start.add(lookVec.scale(maxRange));
		AxisAlignedBB area = new AxisAlignedBB(start, end);
		List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, area);

		for (Entity e : entities) {
			if (!(e instanceof EntityLivingBase))
				continue;

			if (e.equals(player) || e == player.getRidingEntity() || e == ignore)
				continue;

			RayTraceResult intercept = e.getEntityBoundingBox().calculateIntercept(start, end);
			
			if (intercept != null) {
				try {
					IS_ALREADY_PROCING.set(true);
					int orig = ticksSinceLastAtt.getInt(player);
					player.attackTargetEntityWithCurrentItem(e);
					ticksSinceLastAtt.setInt(player, orig);
					IS_ALREADY_PROCING.set(false);
				} catch (IllegalArgumentException | IllegalAccessException er) {
					er.printStackTrace();
				}
			}
		}
	}
}