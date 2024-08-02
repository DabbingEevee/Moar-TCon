package com.existingeevee.moretcon.mixin.softdep.thebetweenlands;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ToolHelper;
import thebetweenlands.common.block.structure.BlockEnergyBarrier;

@Mixin(BlockEnergyBarrier.class)
public class MixinBlockEnergyBarrier {

	@Unique
	private static Entity entityStatic;

	@ModifyVariable(method = { "onEntityCollidedWithBlock", "onEntityCollision", "func_180634_a" }, at = @At("STORE"), ordinal = 0, remap = false)
	public EnumHand moretcon$STORE_ModifyVariable$onEntityCollidedWithBlock(EnumHand handOrig) {
		Entity entity = entityStatic;

		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
			if (ToolHelper.getTraits(stack).contains(ModTraits.shockwaving)) {
				return EnumHand.MAIN_HAND;
			}
		}
		return handOrig;
	}

	@Inject(at = @At(value = "HEAD"), method = { "onEntityCollidedWithBlock", "onEntityCollision", "func_180634_a" }, remap = false)
	public void moretcon$HEAD_Inject$onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity, CallbackInfo ci) {
		entityStatic = entity;
	}
}
