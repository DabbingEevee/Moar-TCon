package com.existingeevee.moretcon.mixin.early.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.existingeevee.moretcon.other.OverrideItemUseEvent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

@Mixin(ItemStack.class)
public class MixinItemStack {
//(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;
	@Redirect(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;onItemUse(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Lnet/minecraft/util/EnumActionResult;", ordinal = 0))
	public EnumActionResult moretcon$INVOKE_Redirect$onItemUse(Item item, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		OverrideItemUseEvent event = new OverrideItemUseEvent(player, hand, pos, side, new Vec3d(hitX, hitY, hitZ));
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getUseItem() == Result.ALLOW) {
			return EnumActionResult.SUCCESS;
		}
		if (event.getUseItem() == Result.DENY) { 
			return EnumActionResult.FAIL;
		}
		return item.onItemUse(player, worldIn, pos, hand, side, hitX, hitY, hitZ);
	}

}
