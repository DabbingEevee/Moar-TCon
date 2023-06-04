package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.Misc;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.util.EnumLogType;
import com.gildedgames.the_aether.items.ItemsAether;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Treetap extends AbstractTrait {

	public Treetap() {
		super(Misc.createNonConflictiveName("treetap"), 0);
	}

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {

    	if ((state.getProperties().containsKey(PropertyEnum.create("aether_logs", EnumLogType.class))) && (state.getValue(PropertyEnum.create("aether_logs", EnumLogType.class)) == EnumLogType.Oak) && !world.isRemote && state.getBlock().equals(BlocksAether.aether_log) && wasEffective) {
            world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemsAether.golden_amber, 1 + random.nextInt(2))));
        }
    }
}
