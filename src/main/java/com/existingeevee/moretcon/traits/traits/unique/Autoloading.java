package com.existingeevee.moretcon.traits.traits.unique;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
import slimeknights.tconstruct.library.tools.ranged.BowCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Autoloading extends AbstractTrait {

	public Autoloading() {
		super(MiscUtils.createNonConflictiveName("autoloading"), 0);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		super.applyEffect(rootCompound, modifierTag);
		if (TinkerUtil.hasCategory(rootCompound, Category.LAUNCHER)) {
			ProjectileLauncherNBT launcherData = new ProjectileLauncherNBT(TagUtil.getToolTag(rootCompound));
			launcherData.drawSpeed = Math.min(launcherData.drawSpeed * 2, Float.MAX_VALUE);
			TagUtil.setToolTag(rootCompound, launcherData.get());
		}
	}

	@SubscribeEvent
	public void onUseTick(LivingUpdateEvent event) {
		ItemStack stack = event.getEntityLiving().getActiveItemStack();

		if (this.isToolWithTrait(stack) && !ToolHelper.isBroken(stack)) {
			if (stack.getItem() instanceof BowCore) {
				BowCore core = (BowCore) stack.getItem();
				boolean fullyDrawn = core.getDrawbackProgress(stack, event.getEntityLiving()) >= 1;

				if (fullyDrawn) {
					event.getEntityLiving().stopActiveHand();
				}
			}
		}
	}
}

/*
 * if (false || speed > 100 && event.getEntityLiving() instanceof EntityPlayer) { //full auto time
						if (event.getEntityLiving().world.getTotalWorldTime() % 3 == 0) {
							ItemStack ammo = core.findAmmo(stack, event.getEntityLiving());
							if (!ammo.isEmpty()) {
								core.shootProjectile(ammo, stack, event.getEntityLiving().world, (EntityPlayer) event.getEntityLiving(), core.getMaxItemUseDuration(stack) - event.getEntityLiving().getItemInUseCount());
							} else {
								event.getEntityLiving().stopActiveHand();
							}
						}
					} else */
