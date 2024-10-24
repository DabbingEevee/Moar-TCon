package com.existingeevee.moretcon.reforges.reforges;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.reforges.AbstractReforge;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ConsistantReforge extends AbstractReforge {

	public ConsistantReforge() {
		super(MiscUtils.createNonConflictiveName("reforgeconsistant"), 0xe7aa1b);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		return newDamage + damage * this.getResonanceLevel(tool) * 0.05f;
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
		event.setNewSpeed(this.getResonanceLevel(tool) * 0.25f + event.getNewSpeed());
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		this.incrResonanceLevel(tool, state.getBlock().getRegistryName().toString() + state.getBlock().getMetaFromState(state));
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        ResourceLocation resourcelocation = EntityList.getKey(target);
		this.incrResonanceLevel(tool, resourcelocation == null ? "null_entityid" : resourcelocation.toString());
	}

	String getResonanceAction(ItemStack tool) {
		if (!tool.hasTagCompound() || !tool.getTagCompound().hasKey(this.getModifierIdentifier(), NBT.TAG_COMPOUND)) {
			return "none";
		}
		NBTTagCompound data = tool.getOrCreateSubCompound(this.getModifierIdentifier());
		return data.getString("Action");
	}

	int getResonanceLevel(ItemStack tool) {
		if (!tool.hasTagCompound() || !tool.getTagCompound().hasKey(this.getModifierIdentifier(), NBT.TAG_COMPOUND)) {
			return 0;
		}
		NBTTagCompound data = tool.getOrCreateSubCompound(this.getModifierIdentifier());
		return data.getInteger("Level");
	}

	void incrResonanceLevel(ItemStack tool, String action) {
		String prev = this.getResonanceAction(tool);
		int prevLevel = this.getResonanceLevel(tool);
		if (!action.equals(prev)) {
			prevLevel = -1;
		}
		NBTTagCompound data = tool.getOrCreateSubCompound(this.getModifierIdentifier());
		data.setInteger("Level", Math.min(prevLevel + 1, 10));
		data.setString("Action", action);
	}
}
