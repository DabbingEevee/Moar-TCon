package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.Misc;
import com.gildedgames.the_aether.items.ItemsAether;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class SkysBlessing extends AbstractTrait {

	public SkysBlessing() {
		super(Misc.createNonConflictiveName("skys_blessing"), 0);
	}

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if (!world.isRemote && world.rand.nextInt(20) == 0) {
        	world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemsAether.ambrosium_shard, 1)));
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if(!target.isEntityAlive() && !target.getEntityWorld().isRemote && random.nextInt(20) == 0) {
        	target.world.spawnEntity(new EntityItem(target.getEntityWorld(), target.getPosition().getX(), target.getPosition().getY(), target.getPosition().getZ(), new ItemStack(ItemsAether.ambrosium_shard, 1)));
        }
    }
}
