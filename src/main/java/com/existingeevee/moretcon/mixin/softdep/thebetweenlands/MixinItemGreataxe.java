package com.existingeevee.moretcon.mixin.softdep.thebetweenlands;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.tools.ToolCore;
import thebetweenlands.common.item.tools.ItemGreataxe;

//This is a hack to allow tool to break effective blocks

@Mixin(ItemGreataxe.class)
public class MixinItemGreataxe {

	@Unique
	private static ItemStack stackStatic;

	@Unique
	private static EntityPlayerMP playerStatic;

	@Unique
	private static BlockPos blockPosStatic;

	@Unique
	private static List<BlockPos> toCheckStatic;

	@Unique
	private static final BlockPos DUMMY = new BlockPos(0, 0, 0);

	@Inject(at = @At(value = "HEAD"), method = { "onUpdate", "func_77663_a" }, remap = false)
	public void moretcon$HEAD_Inject$onUpdate(ItemStack stack, World world, Entity holder, int slot, boolean isHeldItem, CallbackInfo ci) {
		stackStatic = stack;
		if (holder instanceof EntityPlayerMP && !holder.world.isRemote) {
			playerStatic = (EntityPlayerMP) holder;
		}
	}

	@ModifyVariable(method = { "onUpdate", "func_77663_a" }, at = @At("STORE"), ordinal = 0, remap = false)
	public BlockPos moretcon$STORE_ModifyVariable$onUpdate(BlockPos pos) {
		blockPosStatic = pos;
		return pos;
	}

	@ModifyVariable(method = { "onUpdate", "func_77663_a" }, at = @At("STORE"), ordinal = 0, remap = false)
	public List<BlockPos> moretcon$STORE_ModifyVariable$onUpdate(List<BlockPos> toCheck) {
		toCheckStatic = toCheck;
		return toCheck;
	}

	private final static int CUTOFF = 115; //fuck this is janky

	@ModifyVariable(method = { "onUpdate", "func_77663_a" }, at = @At("STORE"), ordinal = 0, remap = false)
	public IBlockState moretcon$STORE_ModifyVariable$onUpdate(IBlockState state) {
		
		ItemStack stack = stackStatic;
		EntityPlayerMP player = playerStatic;
		BlockPos pos = blockPosStatic;
		List<BlockPos> toCheck = toCheckStatic;
		
		if (stack != null && stack.getItem() instanceof ToolCore) {	
			if (Thread.currentThread().getStackTrace()[2].getLineNumber() > CUTOFF) {
				return state;
			}

			ToolCore core = (ToolCore) stack.getItem();
			boolean canHarvest = ModTraits.inertia.canSweepBreak(stack, core, state, player, pos);
			if (canHarvest) {
				toCheck.add(pos);
			}
			return Blocks.BARRIER.getDefaultState(); //we handle these seperately regardless
		}
		return state;
	}

}
