package com.existingeevee.moretcon.traits.traits.internal;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.events.TinkerProjectileImpactEvent;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;

public class PumpChargedProj extends AbstractProjectileTrait {

	public PumpChargedProj() {
		super(MiscUtils.createNonConflictiveName("polyshot_projectile"), 0);
		MinecraftForge.EVENT_BUS.register(this);
		TinkerRegistry.addTrait(this);
	}

	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed) {
		if (LAST_PROJ.get() != null) {
			LAST_PROJ.set(null);
		}
	}

	public static final ThreadLocal<EntityProjectileBase> LAST_PROJ = ThreadLocal.withInitial(() -> null);

	@SubscribeEvent
	public void onTinkerProjectileImpactEvent(TinkerProjectileImpactEvent event) {
		if (event.getEntity() instanceof EntityProjectileBase) {
			ItemStack stack = ((EntityProjectileBase) event.getEntity()).tinkerProjectile.getItemStack();

			if (!this.isToolWithTrait(stack)) {
				return;
			}

			RayTraceResult result = event.getRayTraceResult();
			if (result != null && result.entityHit != null) {
				LAST_PROJ.set((EntityProjectileBase) event.getEntity());
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLivingDamage(LivingHurtEvent event) {

		if (LAST_PROJ.get() != null) {
			EntityProjectileBase proj = LAST_PROJ.get();
			ItemStack stack = proj.tinkerProjectile.getItemStack();

			if (!this.isToolWithTrait(stack)) {
				return;
			}

			float mult = 1 + (stack.hasTagCompound() ? 0 : stack.getTagCompound().getInteger(ModInfo.MODID + ".Pumps") * 0.5f);

			event.setAmount(event.getAmount() * mult);
		}
	}

	@Override
	public int getPriority() {
		return -100; // we want this to fast
	}
}
