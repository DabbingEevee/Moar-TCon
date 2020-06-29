package com.wordpress.craftminemods.tconmaterial.potion_effect;

import com.wordpress.craftminemods.tconmaterial.potion_effect.effectclasses.PotionColdFlames;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModPotions {
	public static Potion coldflames = new PotionColdFlames();
	
	public static void init() {
		ForgeRegistries.POTIONS.registerAll(
					coldflames
				);
		
	}
}
