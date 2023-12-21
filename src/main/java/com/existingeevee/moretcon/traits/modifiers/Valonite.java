package com.existingeevee.moretcon.traits.modifiers;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import thebetweenlands.api.item.CorrosionHelper;
import thebetweenlands.api.item.ICorrodible;

public class Valonite extends ModifierTrait {

	public Valonite() {
		super(MiscUtils.createNonConflictiveName("modValonite"), 0xcab1ca);
		this.addItem("gemValonite");
		
		this.addAspects(new ModifierAspect.SingleAspect(this));
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (tool.serializeNBT().getCompoundTag("tag").getBoolean("resetDurabilityModValonite")) {
			tool.serializeNBT().getCompoundTag("tag").setBoolean("resetDurabilityModValonite", false);
			tool.serializeNBT().getCompoundTag("tag").setInteger("DamageModValonite", tool.getItemDamage());
		}

		if (tool.getItemDamage() > tool.serializeNBT().getCompoundTag("tag").getInteger("DamageModValonite")) {
			if (random.nextFloat() < 0.5) {
				tool.setItemDamage(tool.serializeNBT().getCompoundTag("tag").getInteger("DamageModValonite"));
			}
			tool.serializeNBT().getCompoundTag("tag").setInteger("DamageModValonite", tool.getItemDamage());
		} else if (tool.getItemDamage() < tool.serializeNBT().getCompoundTag("tag").getInteger("DamageModValonite")) {
			tool.serializeNBT().getCompoundTag("tag").setInteger("DamageModValonite", tool.getItemDamage());
		}

	    if (!tool.isEmpty() && tool.getItem() instanceof ICorrodible && CorrosionHelper.isCorrosionEnabled()) {
			ICorrodible corrodible = (ICorrodible) tool.getItem();
	    	if (corrodible.getCorrosion(tool) > tool.serializeNBT().getCompoundTag("tag").getInteger("DamageModValonite")) {
				if (random.nextFloat() < 0.5) {
					corrodible.setCorrosion(tool, tool.serializeNBT().getCompoundTag("tag").getInteger("DamageModValonite"));
				}
				tool.serializeNBT().getCompoundTag("tag").setInteger("CorrosionModValonite", corrodible.getCorrosion(tool));
			} else if (corrodible.getCorrosion(tool) < tool.serializeNBT().getCompoundTag("tag").getInteger("CorrosionModValonite")) {
				tool.serializeNBT().getCompoundTag("tag").setInteger("CorrosionModValonite", corrodible.getCorrosion(tool));
			}
	    }
	}
	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		super.applyEffect(rootCompound, modifierTag);
		// sets up tags to handle damage handling

		ItemStack stack = new ItemStack(rootCompound);
		int corrosion = 0;
		if (!stack.isEmpty() && stack.getItem() instanceof ICorrodible && CorrosionHelper.isCorrosionEnabled()) {
			corrosion = ((ICorrodible) stack.getItem()).getCorrosion(stack);
		}
		
	    rootCompound.setBoolean("resetDurabilityModValonite", true);
	    rootCompound.setInteger("CorrosionModValonite", corrosion);
	}
}