package com.existingeevee.moretcon.traits.traits.unique;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.Entity;
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
		Entity entity = event.getSource().getImmediateSource();
		
		if (tool.getItem() instanceof ToolCore && entity != null) {
			int ticksSinceLastSwing = ObfuscationReflectionHelper.getPrivateValue(EntityLivingBase.class, player, "field_184617_aD");
			ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, player, Integer.MAX_VALUE, "field_184617_aD");
			entity.hurtResistantTime = 0;
			ToolHelper.attackEntity(tool, (ToolCore) tool.getItem(), player, entity, null, false);
			ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, player, ticksSinceLastSwing, "field_184617_aD");
		}
	}
}