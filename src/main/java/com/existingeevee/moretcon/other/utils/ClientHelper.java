package com.existingeevee.moretcon.other.utils;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientHelper {

	public static boolean isClient() {
		boolean isclient = true;
		try {
			emptyClientFunc();
		} catch (NoSuchMethodError e) {
			isclient = false;
		}
		return isclient;
	}

	public static boolean inThirdPerson() {
		if (!isClient())
			return true;
		return inThirdPersonClient();
	}

	public static boolean isLocalPlayer(EntityPlayer player) {
		if (!isClient())
			return false;

		return getLocalPlayer() == player;
	}

	public static boolean isLocalPlayerUsingItem(ItemStack stack) {
		if (!isClient())
			return false;

		EntityPlayer player = getLocalPlayer();
		if (player == null)
			return false;

		if (stack != null) {
			return player.getActiveItemStack() == stack;
		} else {
			return player.getActiveItemStack() != null;
		}
	}

	public static World getLocalWorld() {
		if (!isClient())
			return null;

		return getLocalWorldUnsafe();
	}
	
	public static EntityPlayer getLocalPlayer() {
		if (!isClient())
			return null;

		return getLocalPlayerUnsafe();
	}

	public static boolean isLocalPlayerSneaking() {
		if (!isClient())
			return false;

		EntityPlayer player = getLocalPlayer();
		return player == null ? false : player.isSneaking();
	}

	public static void preventPlayerSlowdown(Entity player, float originalSpeed, Item item) {
		if (!isClient())
			return;
		preventPlayerSlowdownClient(player, originalSpeed, item);
	}

	// DO NOT CALL THE BELOW PLS. NO REFLECTY BITS
	@Deprecated
	@SideOnly(Side.CLIENT)
	private static World getLocalWorldUnsafe() {
		return Minecraft.getMinecraft().world;
	}

	@Deprecated
	@SideOnly(Side.CLIENT)
	private static void preventPlayerSlowdownClient(Entity player, float originalSpeed, Item item) {
		// has to be done in onUpdate because onTickUsing is too early and gets
		// overwritten. bleh.
		if (player instanceof EntityPlayerSP) {
			EntityPlayerSP playerSP = (EntityPlayerSP) player;
			ItemStack usingItem = playerSP.getActiveItemStack();
			if (!usingItem.isEmpty() && usingItem.getItem() == item) {
				// no slowdown from charging it up
				playerSP.movementInput.moveForward *= originalSpeed * 5.0F;
				playerSP.movementInput.moveStrafe *= originalSpeed * 5.0F;
			}
		}
	}

	@Deprecated
	@SideOnly(Side.CLIENT)
	private static void emptyClientFunc() {
	}

	@Deprecated
	@SideOnly(Side.CLIENT)
	private static boolean inThirdPersonClient() {
		return Minecraft.getMinecraft().gameSettings.thirdPersonView != 0;
	}

	@Nullable
	@Deprecated
	@SideOnly(Side.CLIENT)
	private static EntityPlayer getLocalPlayerUnsafe() {
		return Minecraft.getMinecraft().player;
	}
}