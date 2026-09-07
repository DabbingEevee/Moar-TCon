package com.existingeevee.moretcon.traits.traits.unique;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.LeftClickEvent;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.ModTraits;
import com.existingeevee.moretcon.traits.traits.abst.IAdditionalTraitMethods;
import com.existingeevee.moretcon.traits.traits.abst.NumberTrackerTrait;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.capability.projectile.TinkerProjectileHandler;
import slimeknights.tconstruct.library.events.TinkerToolEvent.OnBowShoot;

public class PumpCharged extends NumberTrackerTrait implements IAdditionalTraitMethods {

	public PumpCharged() {
		super(MiscUtils.createNonConflictiveName("pumpcharged"), 0);
		this.showDisplay = false;
	}

	@Override
	public boolean modifyLauncherProjectile(ItemStack launchingStack, ItemStack parent, ItemStack copy, TinkerProjectileHandler tinkerProjectileHandler) {
		int pumps = launchingStack.hasTagCompound() ? this.getNumber(launchingStack) : 0;		
		if (pumps > 0) {

			NBTTagCompound comp = parent.hasTagCompound() ? parent.getTagCompound() : new NBTTagCompound();
			comp.setInteger(ModInfo.MODID + ".Pumps", pumps);
			parent.setTagCompound(comp);

			ModTraits.pumpChargedProj.apply(parent);
			return true;
		}
		return false;
	}

	@Override
	public int getNumberMax(ItemStack stack) {
		return 3;
	}

	@SubscribeEvent
	public void onLeftClick(LeftClickEvent event) {		
		if (!event.getEntityPlayer().isSneaking() || event.getEntity().world.isRemote)
			return;

		ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();

		if (event.getEntityPlayer().getCooldownTracker().hasCooldown(stack.getItem()))
			return;
		
		if (this.isToolWithTrait(stack)) {
			event.getEntityPlayer().getCooldownTracker().setCooldown(stack.getItem(), 10);
			this.addNumber(stack, 1);
		}
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!isSelected && this.getNumber(tool) != 0) {
			this.setNumber(tool, 0);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onBowShoot(OnBowShoot event) {
//		if (this.isToolWithTrait(event.itemStack) && this.getNumber(event.itemStack) >= this.getNumberMax(event.itemStack)) {
//			event.setProjectileCount(0);
//		}		//handle in finish shooting
	}
}
