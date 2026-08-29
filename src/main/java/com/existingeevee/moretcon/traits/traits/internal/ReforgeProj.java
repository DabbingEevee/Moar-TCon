package com.existingeevee.moretcon.traits.traits.internal;

import java.util.List;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.reforges.reforges.BasicReforge;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class ReforgeProj extends AbstractProjectileTrait {

	public ReforgeProj() {
		super(MiscUtils.createNonConflictiveName("reforged_projectile"), 0);
		MinecraftForge.EVENT_BUS.register(this);
		TinkerRegistry.addTrait(this);
	}

	public static final ThreadLocal<EntityProjectileBase> LAST_PROJ = ThreadLocal.withInitial(() -> null);

	@Override
	public void onLaunch(EntityProjectileBase projectileBase, World world, @Nullable EntityLivingBase shooter) {
		List<ITrait> traits = TinkerUtil.getTraitsOrdered(projectileBase.tinkerProjectile.getLaunchingStack());

		double vel = 0;
		
		for (ITrait t : traits) {
			if (t instanceof BasicReforge && ((BasicReforge) t).isRanged()) {
				BasicReforge.IS_FROM_FIRED_PROJECTILE.set(true);
				vel += ((BasicReforge) t).getVelocity();
				BasicReforge.IS_FROM_FIRED_PROJECTILE.set(false);
			}
		}
				
		projectileBase.motionX *= 1 + vel;
		projectileBase.motionY *= 1 + vel;
		projectileBase.motionZ *= 1 + vel;
	}
	
	@Override
	public int getPriority() {
		return 140;
	}
}
