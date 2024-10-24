package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Darkened extends AbstractTrait {

	public Darkened() {
		super(MiscUtils.createNonConflictiveName("darkened"), 0);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
    	float multiplier = (16f - player.world.getLight(player.getPosition())) / 16f;
    	float multExtraDmg = newDamage * multiplier;
    	float addExtraDmg = 8f - player.world.getLight(player.getPosition()) / 2f;
		return newDamage + Math.max(multExtraDmg, addExtraDmg);
	}

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if(ToolHelper.isToolEffective2(tool, event.getState())) {
        	float multiplier = 2f - event.getEntity().world.getLight(event.getEntity().getPosition()) / 16f;
            event.setNewSpeed(event.getNewSpeed() * multiplier);
        }
    }
}
