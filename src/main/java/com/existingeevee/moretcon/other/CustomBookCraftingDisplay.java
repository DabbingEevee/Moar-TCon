package com.existingeevee.moretcon.other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.materials.Material;

public class CustomBookCraftingDisplay {

	private static final Map<Material, List<DisplayData>> DATA = new HashMap<>();

	public static void register(Material mat, DisplayData disp) {
		DATA.computeIfAbsent(mat, m -> new ArrayList<>()).add(disp);
	}

	public static boolean has(Material mat) {
		return DATA.containsKey(mat) && !DATA.get(mat).isEmpty();
	}
	
	public static Collection<DisplayData> getData(Material mat) {
		return Collections.unmodifiableCollection(DATA.getOrDefault(mat, new ArrayList<>()));
	}
	
	public static abstract class DisplayData {
		public abstract String getRenderedString();
		public abstract ItemStack getRenderedStack();
	}
	
	public static class SimpleDisplayData extends DisplayData {
		private final String translationKey;
		private final Supplier<ItemStack> stack;
		private final List<Supplier<Object>> formatters = new ArrayList<>();
		
		@SafeVarargs
		public SimpleDisplayData(String translationKey, Supplier<ItemStack> stack, Supplier<Object>... formatters) {
			this.translationKey = translationKey;
			this.stack = stack;
			
			for (Supplier<Object> o : formatters) {
				this.formatters.add(o);
			}
		}
		
		@Override
		public String getRenderedString() {
			Object[] objects = formatters.stream().map(Supplier::get).toArray();
			return I18n.format(translationKey, objects);
		}
		
		@Override
		public ItemStack getRenderedStack() {
			return stack.get();
		}
	}
}
