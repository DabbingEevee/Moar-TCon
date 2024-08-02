package com.existingeevee.moretcon.mixin.early.common;

import javax.annotation.Nonnull;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.existingeevee.moretcon.block.blocktypes.BlockCobbledBedrock;
import com.existingeevee.moretcon.other.OverrideItemUseEvent;
import com.existingeevee.moretcon.other.utils.ReequipHack;
import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

@Mixin(ForgeHooks.class)
public abstract class MixinForgeHooks {

	@Redirect(method = "onPlaceItemIntoWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;onItemUse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;", ordinal = 0, remap = true), remap = false)
	private static EnumActionResult moretcon$INVOKE_Redirect$onPlaceItemIntoWorld(Item item, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		OverrideItemUseEvent event = new OverrideItemUseEvent(player, hand, pos, facing, new Vec3d(hitX, hitY, hitZ));
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getUseItem() == Result.ALLOW) {
			return EnumActionResult.SUCCESS;
		}
		if (event.getUseItem() == Result.DENY) {
			return EnumActionResult.FAIL;
		}
		return item.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Inject(method = "blockStrength", at = @At("HEAD"), cancellable = true, remap = false)
	private static void moretcon$HEAD_Inject$blockStrength(@Nonnull IBlockState state, @Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos, CallbackInfoReturnable<Float> ci) {
		if (ModTraits.bottomsEnd.getToolCoreClass().isInstance(player.getHeldItemMainhand().getItem())) {
			float hardness = -1;
			boolean isBedrock = state.getBlock() == Blocks.BEDROCK || state.getBlock().getRegistryName().toString().equals("thebetweenlands:betweenlands_bedrock");

			boolean isSoftBedrock = state.getBlock() instanceof BlockCobbledBedrock;

			boolean hasTrait = ModTraits.bottomsEnd.isToolWithTrait(player.getHeldItemMainhand()) && !ModTraits.bottomsEnd.isStackBroken(player.getHeldItemMainhand());

			if (isSoftBedrock) {
				hardness = 30;
			} else if (isBedrock) {
				hardness = 50;
			}

			if (hardness >= 0 && hasTrait) {
				ci.setReturnValue(player.getDigSpeed(state, pos) / hardness / 30F);
			}
		}
	}

	@Inject(method = "canContinueUsing", at = @At("HEAD"), cancellable = true, remap = false)
	private static void moretcon$HEAD_Inject$canContinueUsing(ItemStack from, ItemStack to, CallbackInfoReturnable<Boolean> ci) {
		if (ReequipHack.HAS_PROCESSED.get()) {
			return;
		}
		ci.setReturnValue(ReequipHack.canContinueUsing(from, to));
	}
}