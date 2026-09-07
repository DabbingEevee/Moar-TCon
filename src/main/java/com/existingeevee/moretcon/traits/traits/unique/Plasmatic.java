package com.existingeevee.moretcon.traits.traits.unique;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Plasmatic extends AbstractTrait {

	public Plasmatic() {
		super(MiscUtils.createNonConflictiveName("plasmatic"), 0);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static final ThreadLocal<Boolean> IS_ALREADY_PROCING = ThreadLocal.withInitial(() -> false);

	private static final Field ticksSinceLastAtt = ObfuscationReflectionHelper.findField(EntityLivingBase.class, "field_184617_aD");

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (!wasHit || IS_ALREADY_PROCING.get() || !(player instanceof EntityPlayer)) {
			return;
		}
		this.proc((EntityPlayer) player, target);
	}

	@SubscribeEvent
	public void onMouseClick(PlayerInteractEvent.LeftClickBlock event) {
		ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
		if (this.isToolWithTrait(stack) && !ToolHelper.isBroken(stack)) {
			proc(event.getEntityPlayer(), null);
		}
	}

	public void proc(EntityPlayer player, EntityLivingBase origTarget) {
		double maxRange = 4.0D;
		boolean needsDamage = origTarget == null;

		Vec3d start = player.getPositionEyes(0.5f);
		Vec3d lookVec = player.getLook(0.5f);
		Vec3d end = start.add(lookVec.scale(maxRange));
		AxisAlignedBB area = MiscUtils.vectorBound(start, end.add(lookVec.scale(2)));
		List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, area);

		List<EntityLivingBase> toProcOn = new ArrayList<>();

		for (Entity e : entities) {
			if (!(e instanceof EntityLivingBase) || e.equals(player) || e == player.getRidingEntity() || e == origTarget) {
				continue;
			}

			RayTraceResult intercept = e.getEntityBoundingBox().calculateIntercept(start, end);

			if (intercept != null && intercept.hitVec.squareDistanceTo(start) <= start.squareDistanceTo(end)) {
				toProcOn.add((EntityLivingBase) e);
			}
		}

		for (EntityLivingBase e : toProcOn) {
			try {
				IS_ALREADY_PROCING.set(true);
				int orig = ticksSinceLastAtt.getInt(player);
				try {
					ItemStack stack = player.getHeldItemMainhand();
					Integer damage = stack.isItemStackDamageable() || !needsDamage ? null : stack.getItemDamage();					
					
					if (stack.getItem() instanceof ToolCore) {
						ToolHelper.attackEntity(stack, (ToolCore) stack.getItem(), player, e, null, true);
					} else {
						player.attackTargetEntityWithCurrentItem(e);
					}

					if (damage != null) {
						stack.setItemDamage(damage);
						needsDamage = false;
					}
				} catch (Exception er) {
					// even if a buggy hit happens i think its okay to skip it.
				}
				ticksSinceLastAtt.setInt(player, orig);
				IS_ALREADY_PROCING.set(false);
			} catch (IllegalArgumentException | IllegalAccessException er) {
				er.printStackTrace();
			}
		}
		
		//deal in the bonus damage
		if (origTarget != null)
			toProcOn.add(origTarget);
		
		double x = toProcOn.size() + 1;
		double bonusDmg = x * Math.log(x + 1);
		for (EntityLivingBase e : toProcOn) {
			int hrt = e.hurtResistantTime;
			e.hurtResistantTime = 0;
			e.attackEntityFrom(DamageSource.causeMobDamage(player), (float) bonusDmg);
			e.hurtResistantTime = hrt;
		}
	}
}