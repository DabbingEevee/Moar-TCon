package com.existingeevee.moretcon.traits.traits;

import java.util.ArrayList;
import java.util.List;

import com.existingeevee.moretcon.NetworkHandler;
import com.existingeevee.moretcon.other.ExtendedAttackMessage;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Approximate extends AbstractTraitLeveled {

	public Approximate(int level) {
		super(MiscUtils.createNonConflictiveName("approximate"), 0, 3, level);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public boolean shouldHaveApprox(ItemStack stack) {
		return this.isToolWithTrait(stack) && !ToolHelper.isBroken(stack);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onMouseClick(PlayerInteractEvent.LeftClickEmpty event) {
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = player.getHeldItemMainhand();
		NBTTagList tagList = TagUtil.getModifiersTagList(stack);
		int index = TinkerUtil.getIndexInCompoundList(tagList, getModifierIdentifier());

		if (index > -1 && shouldHaveApprox(stack)) {
			ModifierNBT modifier = ModifierNBT.readTag(tagList.getCompoundTagAt(index));
			List<Entity> exclude = player.getRidingEntity() == null ? null : Lists.newArrayList(player.getRidingEntity());
			RayTraceResult result = rayTraceWithExp(player, 4.5, exclude, modifier.level * 0.25);
			if (result != null && result.entityHit != null) {
				if (!player.isCreative()) {
					ToolHelper.damageTool(stack, 1, player);
				}
				NetworkHandler.HANDLER.sendToServer(new ExtendedAttackMessage(result.entityHit));
			}
		}
	}
	
	public static RayTraceResult rayTraceWithExp(EntityLivingBase entityLiving, double maxRange, List<Entity> exclude, double exp) {
		return rayTraceWithExp(entityLiving, maxRange, exclude, true, exp);
	}

	public static RayTraceResult rayTraceWithExp(EntityLivingBase entityLiving, double maxRange, List<Entity> exclude, boolean affectedByBlocks, double exp) {
		Vec3d start = entityLiving.getPositionEyes(0.5f);
		Vec3d lookVec = entityLiving.getLookVec();

		exclude = exclude == null ? new ArrayList<>() : new ArrayList<>(exclude);
		exclude.add(entityLiving);

		return rayTraceWithExp(start, lookVec, entityLiving.world, maxRange, exclude, affectedByBlocks, false, exp);
	}

	public static RayTraceResult rayTraceWithExp(Vec3d start, Vec3d direction, World world, double maxRange, List<Entity> exclude, boolean affectedByBlocks, boolean ignoreNoBounding, double exp) {
		Vec3d end = start.add(direction.scale(maxRange));
		RayTraceResult firstTrace = affectedByBlocks ? world.rayTraceBlocks(start, end, false, ignoreNoBounding, true) : null;
		AxisAlignedBB area = new AxisAlignedBB(start, firstTrace != null ? firstTrace.hitVec : end);
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(null, area);

		Entity closestValid = null;
		double closestDistSq = Double.MAX_VALUE;

		for (Entity e : entities) {
			if (!(e instanceof EntityLivingBase) || (exclude != null && exclude.contains(e))) {
				continue;
			}

			RayTraceResult intercept = e.getEntityBoundingBox().grow(exp).calculateIntercept(start, end);

			if (intercept != null) {
				double distSq = intercept.hitVec.squareDistanceTo(start);
				if (closestDistSq > distSq) {
					closestValid = e;
					closestDistSq = distSq;
				}
			}
		}

		if (closestValid != null) {
			return new RayTraceResult(closestValid);
		} else if (firstTrace != null) {
			return firstTrace;
		} else {
			return new RayTraceResult(RayTraceResult.Type.MISS, end, EnumFacing.DOWN, new BlockPos(end));
		}
	}

}
