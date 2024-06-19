package com.existingeevee.moretcon.other;

import static net.minecraftforge.fml.common.eventhandler.Event.Result.DEFAULT;
import static net.minecraftforge.fml.common.eventhandler.Event.Result.DENY;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

public class OverrideItemUseEvent extends PlayerEvent {
	private Result useItem = DEFAULT;
	private final Vec3d hitVec;

	private final EnumHand hand;
	private final BlockPos pos;
	@Nullable
	private final EnumFacing face;

	public OverrideItemUseEvent(EntityPlayer player, EnumHand hand, BlockPos pos, EnumFacing face, Vec3d hitVec) {
        super(Preconditions.checkNotNull(player, "Null player in OverrideItemUseEvent!"));
		this.hand = Preconditions.checkNotNull(hand, "Null hand in OverrideItemUseEvent!");
		this.pos = Preconditions.checkNotNull(pos, "Null position in OverrideItemUseEvent!");
		this.face = face;
		this.hitVec = hitVec;
	}

	public Vec3d getHitVec() {
		return hitVec;
	}

	public Result getUseItem() {
		return useItem;
	}

	public void setUseItem(Result triggerItem) {
		this.useItem = triggerItem;
	}

	@Override
	public void setCanceled(boolean canceled) {
		super.setCanceled(canceled);
		if (canceled) {
			useItem = DENY;
		}
	}

	@Nonnull
	public EnumHand getHand() {
		return hand;
	}

	@Nonnull
	public ItemStack getItemStack() {
		return getEntityPlayer().getHeldItem(hand);
	}

	@Nonnull
	public BlockPos getPos() {
		return pos;
	}

	@Nullable
	public EnumFacing getFace() {
		return face;
	}

	public World getWorld() {
		return getEntityPlayer().getEntityWorld();
	}

	public Side getSide() {
		return getWorld().isRemote ? Side.CLIENT : Side.SERVER;
	}
}
