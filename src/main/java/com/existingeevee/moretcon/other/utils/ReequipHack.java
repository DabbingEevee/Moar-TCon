package com.existingeevee.moretcon.other.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeHooks;

public class ReequipHack {

	private static final Set<String> IGNORED_NBT = new HashSet<>();

	public static void registerIgnoredKey(String string) {
		IGNORED_NBT.add(string);
	}

	public static final ThreadLocal<Boolean> HAS_PROCESSED = ThreadLocal.withInitial(() -> false);

	public static boolean canContinueUsing(ItemStack from, ItemStack to) {
		HAS_PROCESSED.set(true);
		boolean ret = ForgeHooks.canContinueUsing(getProcessedStack(from), getProcessedStack(to));
		HAS_PROCESSED.set(false);
		return ret;
	}

	public static boolean shouldCauseReequipAnimation(ItemStack from, ItemStack to, int slot) {
		HAS_PROCESSED.set(true);
		boolean ret = ForgeHooksClient.shouldCauseReequipAnimation(getProcessedStack(from), getProcessedStack(to), slot);
		HAS_PROCESSED.set(false);
		return ret;
	}

	public static boolean shouldCauseBlockBreakReset(ItemStack from, ItemStack to) {
		HAS_PROCESSED.set(true);
		boolean ret = ForgeHooksClient.shouldCauseBlockBreakReset(getProcessedStack(from), getProcessedStack(to));
		HAS_PROCESSED.set(false);
		return ret;
	}

	public static boolean similarStackForActionBar(ItemStack from, ItemStack to) {
		from = getProcessedStack(from);
		to = getProcessedStack(to);

		return !from.isEmpty() && to.getItem() == from.getItem() && ItemStack.areItemStackTagsEqual(to, from) && (to.isItemStackDamageable() || to.getMetadata() == from.getMetadata());
	}

	private static final Map<String, ItemStack> SAVED_STACKS = Maps.newHashMap();

	public static ItemStack getProcessedStack(ItemStack stack) {
		if (stack.hasTagCompound()) { // Boring ahh item. We ignore
			ItemStack copy = stack.copy(); // We dont want to mess anything up otherwise
			for (String s : IGNORED_NBT) {
				if (copy.getTagCompound().hasKey(s)) {
					copy.getTagCompound().removeTag(s);
					String stackKey = copy.serializeNBT().toString();
					stack = SAVED_STACKS.computeIfAbsent(stackKey, str -> copy); // Okay yeah we need to take action
				}
			}
		}
		return stack;
	}
}
