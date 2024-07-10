package com.existingeevee.moretcon.traits.traits.unique;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Offense extends AbstractTrait {

	public Offense() {
		super(MiscUtils.createNonConflictiveName("offense"), 0);
	}

	@Override
	public void onBlock(ItemStack tool, EntityPlayer player, LivingHurtEvent event) {
		if (tool.getItem() instanceof ToolCore) {
			int ticksSinceLastSwing = ObfuscationReflectionHelper.getPrivateValue(EntityLivingBase.class, player, "field_184617_aD");
			ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, player, Integer.MAX_VALUE, "field_184617_aD");
			event.getSource().getImmediateSource().hurtResistantTime = 0;
			ToolHelper.attackEntity(tool, (ToolCore) tool.getItem(), player, event.getSource().getImmediateSource(), null, false);
			ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, player, ticksSinceLastSwing, "field_184617_aD");
		}
	}
}