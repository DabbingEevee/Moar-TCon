package com.existingeevee.moretcon.other.utils;

import java.lang.reflect.Constructor;

import net.minecraft.block.material.MapColor;

public class MapHelper {
	
	public static MapColor getMapColor(int color) {
		int i;
		for (i = 0; i < 64; i ++) {
			if (MapColor.COLORS[i] == null) {
				break;
			}
			if (colorCompare(MapColor.COLORS[i].colorValue, color)) {
				return MapColor.COLORS[i];
			}
		}
		if (i > 63) {
			return MapColor.TNT;			
		}
		try {
			Constructor<MapColor> mp = MapColor.class.getDeclaredConstructor(int.class, int.class);
			mp.setAccessible(true);
			return mp.newInstance(i, color);
		} catch (Exception e) {
			return MapColor.TNT;
		}
				
	}
	
	private static boolean colorCompare(int c1, int c2) {
		if (c1 == c2) {
			return true;
		}
		int r1 = (c1 >> 16) & 0xFF;
		int g1 = (c1 >> 8) & 0xFF;
		int b1 = c1 & 0xFF;
		int r2 = (c2 >> 16) & 0xFF;
		int g2 = (c2 >> 8) & 0xFF;
		int b2 = c2 & 0xFF;
		if (Math.abs(r1 - r2) < 40 & Math.abs(g1 - g2) < 40 & Math.abs(b1 - b2) < 40) {
			return true;
		}
		
		return false;
	}
}
