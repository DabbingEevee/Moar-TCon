package com.existingeevee.moretcon.traits.traits.unique;

import java.util.UUID;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.traits.abst.NumberTrackerTrait;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class EssentialObliteration extends NumberTrackerTrait {

	public EssentialObliteration() {
		super(MiscUtils.createNonConflictiveName("essential_obliteration"), 0);
	}

	@Override
	public int getNumberMax(ItemStack stack) {
		return 50;
	}

	@Override
	public boolean isToolWithTrait(ItemStack itemStack) {
		return super.isToolWithTrait(itemStack);
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
		if (event.getEntityLiving().isSneaking()) {
			event.setNewSpeed(event.getNewSpeed() * 0.0625f);
		} else if (this.getNumber(tool) > 0) {
			event.setNewSpeed(event.getNewSpeed() * 20f);
		}
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		if (player.isSneaking()) {
			int maxToAdd = this.getNumberMax(tool) - this.getNumber(tool);
			this.addNumber(tool, Math.min(5, maxToAdd));
		} else if (this.getNumber(tool) > 0) {
			this.removeNumber(tool, 1);
		}
	}

	protected static final AttributeModifier ESSENCORE_MINING_REACH = new AttributeModifier(UUID.fromString("AB3F34D3-645C-4F38-A497-9C13A34DB5CF"), "reach modifier", 20, 0);

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		if ((e.getEntityLiving() instanceof EntityPlayer)) {
			ItemStack stack = e.getEntityLiving().getHeldItemMainhand();
			if (this.isToolWithTrait(stack) && !e.getEntityLiving().isSneaking() && this.getNumber(stack) > 0 && !ToolHelper.isBroken(stack)) {
				if (!e.getEntityLiving().getAttributeMap().getAttributeInstance(EntityPlayer.REACH_DISTANCE).hasModifier(ESSENCORE_MINING_REACH)) {
					e.getEntityLiving().getAttributeMap().getAttributeInstance(EntityPlayer.REACH_DISTANCE).applyModifier(ESSENCORE_MINING_REACH);
				}
			} else {
				if (e.getEntityLiving().getAttributeMap().getAttributeInstance(EntityPlayer.REACH_DISTANCE).hasModifier(ESSENCORE_MINING_REACH)) {
					e.getEntityLiving().getAttributeMap().getAttributeInstance(EntityPlayer.REACH_DISTANCE).removeModifier(ESSENCORE_MINING_REACH);
				}
			}
		}
	}
}
