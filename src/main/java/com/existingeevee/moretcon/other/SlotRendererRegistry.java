package com.existingeevee.moretcon.other;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;

public class SlotRendererRegistry {

	private static final Map<Item, ICustomSlotRenderer> MAP = new HashMap<>();

	public static void register(Item item, ICustomSlotRenderer renderers) {
		MAP.put(item, renderers);
	}

	public static ICustomSlotRenderer get(Item item) {
		if (MAP.containsKey(item)) {
			return MAP.get(item);
		} else if (item instanceof ICustomSlotRenderer) {
			return (ICustomSlotRenderer) item;
		}
		return null;
	}

}
