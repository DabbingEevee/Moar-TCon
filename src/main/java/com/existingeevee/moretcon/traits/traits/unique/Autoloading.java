package com.existingeevee.moretcon.traits.traits.unique;

import com.existingeevee.moretcon.other.utils.MirrorUtils;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
import slimeknights.tconstruct.library.tools.ranged.BowCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.melee.item.FryPan;

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
			
			boolean fullyDrawn = false;
			
			if (stack.getItem() instanceof BowCore) {
				BowCore core = (BowCore) stack.getItem();
				fullyDrawn = core.getDrawbackProgress(stack, event.getEntityLiving()) >= 1;
			} else if (stack.getItem() instanceof FryPan) {
				int ticks = stack.getItem().getMaxItemUseDuration(stack) - event.getEntityLiving().getItemInUseCount();
				fullyDrawn = ticks >= 1;
			}
			
			if (fullyDrawn) {
				event.getEntityLiving().stopActiveHand();
			}
		}
	}
	
	private static final MirrorUtils.IField<Integer> rightClickDelayTimer$Minecraft = MirrorUtils.reflectObfusField(Minecraft.class, "field_71467_ac");
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
	    Minecraft mc = Minecraft.getMinecraft();

	    if (mc.player == null) {
	        return;
	    }

	    ItemStack stack = mc.player.getHeldItemMainhand();

	    if (this.isToolWithTrait(stack)) {
	        int delay = rightClickDelayTimer$Minecraft.get(mc);
	        
	        if (delay > 1) {
	        	rightClickDelayTimer$Minecraft.set(mc, 1);
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
