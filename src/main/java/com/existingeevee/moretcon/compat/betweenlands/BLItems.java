package com.existingeevee.moretcon.compat.betweenlands;

import com.existingeevee.moretcon.inits.ModFluids;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.other.utils.RegisterHelper;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import thebetweenlands.common.item.tools.ItemSpecificBucket;

public class BLItems {

	public static Item blFilledMoltenSulfur;

	public static void initBL() {initBL(false);}

	public static void initBL(boolean isClient) {

		blFilledMoltenSulfur = new ItemSpecificBucket(ModFluids.liquidBurningSulfurFlow).setUnlocalizedName(MiscUtils.createNonConflictiveName("blFilledMoltenSulfur").toLowerCase());
		RegisterHelper.registerItem(blFilledMoltenSulfur);
		

		if (isClient) {
			ModelLoader.setCustomModelResourceLocation(BLItems.blFilledMoltenSulfur, 0, new ModelResourceLocation(BLItems.blFilledMoltenSulfur.getRegistryName() + "_weedwood", "inventory"));
			ModelLoader.setCustomModelResourceLocation(BLItems.blFilledMoltenSulfur, 1, new ModelResourceLocation(BLItems.blFilledMoltenSulfur.getRegistryName() + "_syrmorite", "inventory"));
		}
	}
}
