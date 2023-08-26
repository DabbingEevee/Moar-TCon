package com.existingeevee.moretcon.mixin.early.common;

import javax.annotation.Nonnull;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CancellationException;

import com.existingeevee.moretcon.other.OverrideItemUseEvent;
import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
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

	@Redirect(method = "onPlaceItemIntoWorld", at = @At(value = "INVOKE", target = "onItemUse", ordinal = 0))
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
		try {
			if (ModTraits.bottomsEnd.getToolCoreClass().isInstance(player.getHeldItemMainhand().getItem())) {
				float hardness = state.getBlockHardness(world, pos);
				boolean isBedrock = state.getBlock() == Blocks.BEDROCK;
				boolean hasTrait = ModTraits.bottomsEnd.isToolWithTrait(player.getHeldItemMainhand());
				boolean canHarvest = ModTraits.bottomsEnd.isToolWithTrait(player.getHeldItemMainhand());
				
				if (isBedrock && hasTrait && canHarvest) {
					hardness = 50;
					ci.setReturnValue(player.getDigSpeed(state, pos) / hardness / 30F);
				}	
			}
		} catch (CancellationException e) {
			e.printStackTrace();
		}
	}

}

/*
 * @Inject(method = "canToolHarvestBlock", at = @At("HEAD"), cancellable = true)
 * public static void moretcon$canToolHarvestBlock$InjectHEAD(IBlockAccess
 * world, BlockPos pos, @Nonnull ItemStack stack,
 * CallbackInfoReturnable<Boolean> ci) { // boolean IBlockState state =
 * world.getBlockState(pos); boolean isBedrock = state.getBlock() ==
 * Blocks.BEDROCK; boolean canBreakBedrock =
 * ToolHelper.getTraits(stack).stream().anyMatch(t ->
 * t.getIdentifier().equals(ModTraits.bottomsEnd.getIdentifier())); if
 * (isBedrock && canBreakBedrock) { ci.setReturnValue(true); } }
 */
