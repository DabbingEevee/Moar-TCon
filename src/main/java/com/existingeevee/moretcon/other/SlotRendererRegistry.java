package com.existingeevee.moretcon.other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotRendererRegistry {

	private static final List<Pair<Predicate<ItemStack>, ICustomSlotRenderer>> PREDICATES = new ArrayList<>();

	public static void register(Item item, ICustomSlotRenderer renderer) {
		register(s -> s.getItem() == item, renderer);
	}

	public static void register(Predicate<ItemStack> item, ICustomSlotRenderer renderer) {
		PREDICATES.add(Pair.of(item, renderer));
	}

	public static Collection<ICustomSlotRenderer> get(ItemStack item) {
		List<ICustomSlotRenderer> list = Lists.newArrayList();
		if (item.getItem() instanceof ICustomSlotRenderer) {
			list.add((ICustomSlotRenderer) item.getItem());
		}

		for (Pair<Predicate<ItemStack>, ICustomSlotRenderer> p : PREDICATES) {
			if (p.getKey().test(item)) {
				list.add(p.getValue());
			}
		}

		return list;
	}

	public static final ThreadLocal<LinkedList<ICustomSlotRenderer>> RENDERED = new ThreadLocal<>();

	public static void render(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
		Collection<ICustomSlotRenderer> renderers = get(stack);

		LinkedList<ICustomSlotRenderer> rendered = new LinkedList<>();
		boolean renderedAny = false;

		for (ICustomSlotRenderer renderer : renderers) {
			if (renderer.shouldRender(stack)) {
				renderer.render(stack, x, y, bakedmodel);
				rendered.add(renderer);
				renderedAny = true;
			}
		}

		if (renderedAny) {
			RENDERED.set(rendered);
		}
	}

	public static void postRender(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
		LinkedList<ICustomSlotRenderer> rendered = RENDERED.get();

		if (rendered == null) {
			return;
		}

		RENDERED.remove();

		Iterator<ICustomSlotRenderer> it = rendered.descendingIterator();

		while (it.hasNext()) {
			it.next().postRender(stack, x, y, bakedmodel);
		}
	}
}
