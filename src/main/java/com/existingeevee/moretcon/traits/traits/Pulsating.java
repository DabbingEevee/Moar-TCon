package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Pulsating extends AbstractTrait {

	public Pulsating() {
		super(MiscUtils.createNonConflictiveName("pulsating"), 0x00725a);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		NBTTagCompound comp = tool.getOrCreateSubCompound(this.getModifierIdentifier());

		float x = comp.getFloat("x");
		float ed = (Math.round(20 * Math.sin(x += 0.2)) + 20) / 10;
		comp.setFloat("x", Math.round(x * 10f) / 10f);

		return newDamage + ed;
	}

}
