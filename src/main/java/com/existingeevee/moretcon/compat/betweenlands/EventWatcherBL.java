package com.existingeevee.moretcon.compat.betweenlands;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import slimeknights.tconstruct.library.tools.ranged.IProjectile;
import slimeknights.tconstruct.library.utils.TagUtil;
import thebetweenlands.common.entity.EntityBLLightningBolt;

public class EventWatcherBL {

	private static List<EntityBLLightningBolt> lightningInstances = new ArrayList<>();

	@SubscribeEvent
	public void onWorldStarted(WorldEvent.Load e) {
		lightningInstances = new ArrayList<>();
	}



	@SubscribeEvent
	public void onWorldTick(WorldTickEvent w) {
		for (Entity e : new ArrayList<>(w.world.loadedEntityList)) {
			if (e instanceof EntityBLLightningBolt) {

				boolean effectOnly = ObfuscationReflectionHelper.getPrivateValue(EntityBLLightningBolt.class, (EntityBLLightningBolt) e, "effectOnly");
				if (effectOnly) {
					continue;
				}

				if (!EventWatcherBL.lightningInstances.contains(e)) {
					EventWatcherBL.lightningInstances.add((EntityBLLightningBolt) e);
				}
			}
		}
		try {
			for (EntityBLLightningBolt e : new ArrayList<>(EventWatcherBL.lightningInstances)) {
				if (!e.isEntityAlive() && e.getEntityWorld().equals(w.world)) {
					BlockPos pos = e.getPosition();
					double range = 5.0D;
					List<Entity> nearbyEntities = e.getEntityWorld().getEntitiesWithinAABBExcludingEntity(e, new AxisAlignedBB(pos.getX() - range, pos.getY() - range, pos.getZ() - range, pos.getX() + range, pos.getY() + range, pos.getZ() + range));
					for (Entity entity : nearbyEntities) {
						if (entity instanceof EntityItem) {
							ItemStack stack = ((EntityItem) entity).getItem();
							if (stack.getItem() instanceof IProjectile) {
								if (ModTraits.modShocked.canApplyCustom(stack) && TagUtil.getToolStats(stack).modifiers >= 1) {
									ModTraits.modShocked.apply(stack);
								}
							}
						}
					}
					EventWatcherBL.lightningInstances.remove(e);
				}
			}
		} catch (ConcurrentModificationException e) {
		}
	}
}
