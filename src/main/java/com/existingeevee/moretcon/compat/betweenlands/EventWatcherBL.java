package com.existingeevee.moretcon.compat.betweenlands;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.tools.ranged.IProjectile;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import slimeknights.tconstruct.library.utils.ToolHelper;
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
								if (ModTraits.modShocking.canApplyCustom(stack) && !ToolHelper.getTraits(stack).contains(ModTraits.modShocking) && TagUtil.getToolStats(stack).modifiers >= 1) {
									item.setEntityInvulnerable(true);
									ToolNBT toolnbt = TagUtil.getToolStats(stack);
									toolnbt.modifiers--;
									TagUtil.setToolTag(stack.serializeNBT().getCompoundTag("tag"), toolnbt.get());
									stack.serializeNBT().getCompoundTag("tag").getCompoundTag("TinkerData").getTagList("Modifiers", 8).appendTag(new NBTTagString(ModTraits.modShocking.getModifierIdentifier()));
									ToolBuilder.addTrait(stack.serializeNBT().getCompoundTag("tag"), ModTraits.modShocking, ModTraits.modShocking.getColor());
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
