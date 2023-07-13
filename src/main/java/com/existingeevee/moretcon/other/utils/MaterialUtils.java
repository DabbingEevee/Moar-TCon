package com.existingeevee.moretcon.other.utils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import com.existingeevee.moretcon.other.MoreTConLogger;

import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

public class MaterialUtils {
	@SuppressWarnings("unchecked")
	public static boolean readdTinkerMaterial(Material material) {
		boolean success = false;
		Field mat = null;
		try {
			mat = TinkerRegistry.class.getDeclaredField("materials");
			mat.setAccessible(true);
			Map<String, Material> fieldValue = (Map<String, Material>) mat.get(TinkerRegistry.class);
			Entry<String, Material> entry = null;
			for (Entry<String, Material> e : fieldValue.entrySet()) {
				if (e.getValue().identifier.equals(material.identifier)) {
					success = true;
					entry = e;
					break;
				}
			}
			readd.put(entry.getKey(), entry.getValue());

			if (!success) {
				MoreTConLogger.log("Unable to readd material \"" + material.identifier + "\" as it was never registered",
						Level.ERROR);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
			MoreTConLogger.log("Unable to readd material \"" + material.identifier + "\" as an error was encountered",
					Level.ERROR);
			e1.printStackTrace();
		}
		return success;
	}

	public static Material forceSetRepItem(ItemStack repItem, Material material) {
		Field f = null;
		try {
			for (Field pF : Material.class.getDeclaredFields()) {
				if (pF.getName().equals("representativeItem")) {
					f = pF;
					break;
				}
			}
			f.setAccessible(true);
			f.set(material, repItem);
		} catch (NullPointerException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return material;
	}
	public static boolean removeTinkerMaterial(Material material) {
		return removeTinkerMaterial(material.getIdentifier());
	}

	@SuppressWarnings("unchecked")
	public static boolean removeTinkerMaterial(String identifier) {
		boolean success = false;
		Field mat = null;
		try {
			mat = TinkerRegistry.class.getDeclaredField("materials");
			mat.setAccessible(true);
			Map<String, Material> fieldValue = (Map<String, Material>) mat.get(TinkerRegistry.class);
			success = fieldValue.entrySet().removeIf(m -> m.getValue().identifier.equals(identifier));
			if (!success) {
				MoreTConLogger.log("Unable to remove material \"" + identifier + "\" as it was never registered",
						Level.ERROR);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
			MoreTConLogger.log("Unable to remove material \"" + identifier + "\" as an error was encountered",
					Level.ERROR);
			e1.printStackTrace();
		}
		return success;
	}

	public static Map<String, Material> readd = new LinkedHashMap<>();

	public static void completeReadds() {
		try {
			Field mat = TinkerRegistry.class.getDeclaredField("materials");
			mat.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<String, Material> fieldValue = (Map<String, Material>) mat.get(TinkerRegistry.class);
			readd.forEach((s, m) -> fieldValue.remove(s));
			fieldValue.putAll(readd);
		} catch (NoSuchFieldException | IllegalAccessException e) {

		}
	}
}
