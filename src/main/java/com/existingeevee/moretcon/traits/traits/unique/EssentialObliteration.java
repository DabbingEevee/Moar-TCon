package com.existingeevee.moretcon.traits.traits.unique;

import com.existingeevee.moretcon.other.Misc;
import com.existingeevee.moretcon.traits.traits.abst.NumberTrackerTrait;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EssentialObliteration extends NumberTrackerTrait {

	public EssentialObliteration() {
		super(Misc.createNonConflictiveName("essential_obliteration"), 0);
	}

	@Override
	public int getNumberMax(ItemStack stack) {
		return 50;
	}

	@Override
	public int getNumberRemaining(ItemStack stack) {
		NBTTagCompound comp = stack.getOrCreateSubCompound(this.identifier);
		return comp.hasKey("remaining", NBT.TAG_INT) ? comp.getInteger("remaining") : 0;
	}

	@Override
	public int setNumberRemaining(ItemStack stack, int amount) {
		NBTTagCompound comp = stack.getOrCreateSubCompound(this.identifier);
		comp.setInteger("remaining", amount);
		return amount;
	}

	@Override
	public boolean isToolWithTrait(ItemStack itemStack) {
		return super.isToolWithTrait(itemStack);
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
		if (event.getEntityLiving().isSneaking()) {
			event.setNewSpeed(event.getNewSpeed() * 0.25f);
		} else if (this.getNumberRemaining(tool) > 0) {
			event.setNewSpeed(event.getNewSpeed() * 5f);
		}
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		if (player.isSneaking()) {
			this.addNumberRemaining(tool, 5);
		} else if (this.getNumberRemaining(tool) > 0) {
			this.subtractNumberRemaining(tool, 1);
		}
	}
}
