package com.existingeevee.moretcon.other.utils;

import com.existingeevee.moretcon.config.ConfigHandler;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

public class CompatManager {
	//CompatManager.twilightforest
	public static boolean twilightforest;
	public static boolean iceandfire;
	public static boolean plustic;
	public static boolean jokes;
	public static boolean thebetweenlands;
	public static boolean loadMain;
	public static boolean aether_legacy;
	public static boolean baubles;
	public static boolean conarm;
	public static boolean oredict;
	public static boolean tic3backport;

	public static void init() {
		CompatManager.twilightforest = (Loader.isModLoaded("twilightforest") && ConfigHandler.shouldAllowTwilightForest);
		CompatManager.iceandfire = (Loader.isModLoaded("iceandfire") && ConfigHandler.shouldAllowIceAndFire);
		CompatManager.plustic = (Loader.isModLoaded("plustic") && ConfigHandler.shouldAllowPlusTiC);
		CompatManager.thebetweenlands = (Loader.isModLoaded("thebetweenlands") && ConfigHandler.shouldAllowBetweenLands);
		CompatManager.aether_legacy = (Loader.isModLoaded("aether_legacy") && ConfigHandler.shouldAllowAether);
		CompatManager.baubles = (Loader.isModLoaded("baubles") && ConfigHandler.shouldAllowBaubles);
		CompatManager.conarm = (Loader.isModLoaded("conarm") && ConfigHandler.shouldAllowConstructsArmory);
		CompatManager.jokes = ConfigHandler.shouldAllowJokeItems;
		CompatManager.loadMain = ConfigHandler.shouldAllowMainContent;
		CompatManager.oredict = ConfigHandler.shouldAllowOreDictionary;
		CompatManager.tic3backport = ConfigHandler.shouldAllowTiC3ContentBackport;
	}

	public static boolean isOredictLoaded(String dictEntry) {
		return OreDictionary.doesOreNameExist(dictEntry) && ConfigHandler.shouldAllowOreDictionary;
	}
}
