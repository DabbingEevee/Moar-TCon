package com.existingeevee.moretcon.effects;

import com.existingeevee.moretcon.other.utils.CompatManager;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModPotions {	
	public static Potion corrosive;
	public static Potion bloodgodsblessing;

	public static Potion bleeding;

	public static Potion hyperflames;

	public static void init() {
		//coldflames = new PotionColdFlames();
		bloodgodsblessing = new PotionBloodGodsBlessing();
		bleeding = new PotionBleeding();	
		hyperflames = new PotionHyperflames();	
		if (CompatManager.loadMain) {
			ForgeRegistries.POTIONS.registerAll(
						bleeding,
						hyperflames
					);
		}
		if (CompatManager.jokes) {
			ForgeRegistries.POTIONS.registerAll(
					bloodgodsblessing
					);
		}
		if (CompatManager.thebetweenlands) {
			/*corrosive = new PotionCorosiveBlast();
			ForgeRegistries.POTIONS.registerAll(
					corrosive
				);*/
		}

	}
}
