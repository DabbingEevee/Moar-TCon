package com.existingeevee.moretcon.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotRendererRegistry {

	private static final Map<Item, ICustomSlotRenderer> MAP = new HashMap<>();
	private static final List<Pair<Predicate<ItemStack>, ICustomSlotRenderer>> PREDICATE = new ArrayList<>();

	public static void register(Item item, ICustomSlotRenderer renderers) {
		MAP.put(item, renderers);
	}

	public static void register(Predicate<ItemStack> item, ICustomSlotRenderer renderers) {
		PREDICATE.add(Pair.of(item, renderers));
	}
	
	public static ICustomSlotRenderer get(ItemStack item) {
		if (MAP.containsKey(item.getItem())) {
			return MAP.get(item.getItem());
		} else if (item.getItem() instanceof ICustomSlotRenderer) {
			return (ICustomSlotRenderer) item.getItem();
		}
		Optional<Pair<Predicate<ItemStack>, ICustomSlotRenderer>> optional = PREDICATE.stream().filter(e -> e.getKey().test(item)).findFirst();
		return optional.isPresent() ? optional.get().getValue() : null;
	}

}
