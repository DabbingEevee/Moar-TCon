package com.existingeevee.moretcon.traits.traits.unique;

import java.util.List;

import com.existingeevee.moretcon.effects.ModPotions;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.traits.abst.IAdditionalTraitMethods;
import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class BloodGodsBlessing extends AbstractTrait implements IAdditionalTraitMethods {

	public BloodGodsBlessing() {
		super(MiscUtils.createNonConflictiveName("bloodgodsblessing"), 0);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (target.isDead || target.getHealth() >= 0) {
			int lvl = -1;
			
			if (player.isPotionActive(ModPotions.bloodgodsblessing)) {
				lvl = player.getActivePotionEffect(ModPotions.bloodgodsblessing).getAmplifier();
				player.removeActivePotionEffect(ModPotions.bloodgodsblessing);
			}
			
			player.addPotionEffect(new PotionEffect(ModPotions.bloodgodsblessing, 5 * 20, lvl + 1, false, false));
		}
	}
	
	@Override
	public List<String> getAdditionalInfo(ItemStack tool) {
		
		List<String> list = Lists.newArrayList();
	
		list.add("");
		
		list.add(CustomFontColor.encodeColor(0xea8f8c) + "He may be gone, but he will always live in our hearts.");
		list.add(CustomFontColor.encodeColor(0xea8f8c) + "RIP Technoblade (1999-2022)");
		list.add("");
		
		return list;
	}

}
