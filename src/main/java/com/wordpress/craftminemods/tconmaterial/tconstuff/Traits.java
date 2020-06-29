package com.wordpress.craftminemods.tconmaterial.tconstuff;

import com.wordpress.craftminemods.tconmaterial.other.RegisterHelper;
import com.wordpress.craftminemods.tconmaterial.tconstuff.traitclasses.Impactor;
import com.wordpress.craftminemods.tconmaterial.tconstuff.traitclasses.ColdFire;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public class Traits {
	public static ColdFire coldFire = new ColdFire();
	public static Impactor modImpactor = new Impactor();
	static {
		modImpactor.addItem(Blocks.ANVIL, 1);
	}
	
	private static void registerModifier(IModifier... mod) {
		for (IModifier i : mod) {
			RegisterHelper.registerModifier(i);
		}
	}

	public static void init() {
		registerModifier(
				modImpactor
				);
	}
			
}
