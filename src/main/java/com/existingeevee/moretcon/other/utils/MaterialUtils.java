package com.existingeevee.moretcon.other.utils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import com.existingeevee.moretcon.other.MoreTConLogger;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
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
			READDS.put(entry.getKey(), entry.getValue());

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
		ObfuscationReflectionHelper.setPrivateValue(Material.class, material, repItem, "representativeItem");
		return material;
	}
	
	public static Material forceSetFluid(Fluid fluid, Material material) {
		ObfuscationReflectionHelper.setPrivateValue(Material.class, material, fluid, "fluid");
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

	public static final Map<String, Material> READDS = new LinkedHashMap<>();
	public static final Map<String, Material> materials$TinkerRegistry = ObfuscationReflectionHelper.getPrivateValue(TinkerRegistry.class, null, "materials");

	public static void completeReadds() {
		READDS.forEach((s, m) -> materials$TinkerRegistry.remove(s));
		materials$TinkerRegistry.putAll(READDS);
	}
}
