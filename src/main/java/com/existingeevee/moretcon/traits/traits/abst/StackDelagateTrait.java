package com.existingeevee.moretcon.traits.traits.abst;

import java.util.function.Supplier;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class StackDelagateTrait extends AbstractTrait {

	final Supplier<ItemStack> supplier;
	final boolean lazyLoad;

	boolean onMine = false;
	boolean onHit = false;
	boolean onUpdate = false;

	public StackDelagateTrait(String identifier, int color, Supplier<ItemStack> supplier, boolean lazyLoad) {
		super(identifier, color);
		this.supplier = supplier;
		this.lazyLoad = lazyLoad;
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		if (onMine && player instanceof EntityPlayer) {
			getItemStack().onBlockDestroyed(world, state, pos, (EntityPlayer) player);
		}
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (onHit && player instanceof EntityPlayer) {
			getItemStack().hitEntity(target, (EntityPlayer) player);
		}
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (onUpdate) {
			getItemStack().updateAnimation(world, entity, itemSlot, isSelected);
		}
	}

	public StackDelagateTrait setOnMine() {
		this.onMine = true;
		return this;
	}

	public StackDelagateTrait setOnHit() {
		this.onHit = true;
		return this;
	}

	public StackDelagateTrait setOnUpdate() {
		this.onUpdate = true;
		return this;
	}

	private ItemStack lazyLoadStack;

	protected ItemStack getItemStack() {
		if (!lazyLoad) {
			return supplier.get();
		}

		if (lazyLoadStack == null) {
			lazyLoadStack = supplier.get();
		}
		return lazyLoadStack;
	}

}
