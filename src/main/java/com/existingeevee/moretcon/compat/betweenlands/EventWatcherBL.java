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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import slimeknights.mantle.util.TagHelper;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.tools.ranged.IProjectile;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import thebetweenlands.common.entity.EntityBLLightningBolt;

public class EventWatcherBL {

	private static List<EntityBLLightningBolt> lightningInstances = new ArrayList<EntityBLLightningBolt>();

	@SubscribeEvent
	public void onWorldStarted(WorldEvent.Load e) {
		lightningInstances = new ArrayList<EntityBLLightningBolt>();
	}

	@SubscribeEvent
	public void onWorldTick(WorldTickEvent w) {
		for (Entity e : new ArrayList<>(w.world.loadedEntityList)) {
			if (e instanceof EntityBLLightningBolt) {
				if (!EventWatcherBL.lightningInstances.contains((EntityBLLightningBolt) e)) {
					EventWatcherBL.lightningInstances.add((EntityBLLightningBolt) e);
				}
			}
		}
		try {
			for (EntityBLLightningBolt e : new ArrayList<EntityBLLightningBolt>(EventWatcherBL.lightningInstances)) {
				if (!e.isEntityAlive() && e.getEntityWorld().equals(w.world)) {
					BlockPos pos = e.getPosition();
					double range = 5.0D;
					List<Entity> nearbyEntities = e.getEntityWorld().getEntitiesWithinAABBExcludingEntity(e, new AxisAlignedBB(pos.getX() - range, pos.getY() - range, pos.getZ() - range, pos.getX() + range, pos.getY() + range, pos.getZ() + range));
					for (Entity entity : nearbyEntities) {
						if (entity instanceof EntityItem) {
							EntityItem item = ((EntityItem) entity);
							ItemStack stack = item.getItem();
							if (stack.getItem() instanceof IProjectile) {
								if (ModTraits.modShocked.canApplyCustom(stack) && TagUtil.getToolStats(stack).modifiers >= 1) {
									ModTraits.modShocked.apply(stack);
									/* TileToolForge
									ToolNBT toolnbt = TagUtil.getToolStats(stack);
									toolnbt.modifiers--;
									TagUtil.setToolTag(stack, toolnbt.get());*/
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
