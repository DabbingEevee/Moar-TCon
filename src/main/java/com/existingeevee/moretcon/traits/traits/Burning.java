package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import thebetweenlands.common.capability.circlegem.CircleGemHelper;
import thebetweenlands.common.capability.circlegem.CircleGemType;

public class Burning extends AbstractTrait {

	public Burning() {
		super(MiscUtils.createNonConflictiveName("burning"), 0x00ed00);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (player.world.rand.nextFloat() < getOctineToolFireChance(tool, target, player)) {
			target.setFire(5);
		}
		super.afterHit(tool, target, player, damageDealt, wasHit, wasHit);
	}

	@Override
	public void onBlock(ItemStack tool, EntityPlayer player, LivingHurtEvent event) {
		if (player.getActiveItemStack().equals(tool)) {
			DamageSource source = event.getSource();
			if (source.getImmediateSource() != null) {
				source.getImmediateSource().setFire(4);
			}
		}
		super.onBlock(tool, player, event);
	}

	public static float getOctineToolFireChance(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		return CircleGemHelper.getGem(stack) == CircleGemType.CRIMSON ? 0.5F : 0.25F;
	}
}