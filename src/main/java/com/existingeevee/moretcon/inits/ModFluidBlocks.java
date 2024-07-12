package com.existingeevee.moretcon.inits;

import com.existingeevee.moretcon.fluid.BlockFluid;
import com.existingeevee.moretcon.fluid.CustomFireBlockFluid;
import com.existingeevee.moretcon.fluid.RadioactiveBlockFluid;
import com.existingeevee.moretcon.other.BlockMaterials;
import com.existingeevee.moretcon.other.fires.CustomFireEffect;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.RegisterHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.DamageSource;

public class ModFluidBlocks {
	/* ------------------------------------- */
	public static Block blockLiquidFusionite;
	public static Block blockLiquidValasium;
	public static Block blockLiquidIrradium;
	public static Block blockLiquidGallium;
	public static Block blockLiquidPenguinite;
	public static Block blockLiquidSolarSteel;
	public static Block blockLiquidHydrogen;
	public static Block blockLiquidSyrmorite;
	public static Block blockLiquidOctine;
	public static Block blockBurningSulfurFlow;
	public static Block blockLiquidEmber;
	public static Block blockLiquidIronwood;
	public static Block blockLiquidRuneSteel;
	public static Block blockLiquidArkenium;
	public static Block blockLiquidValkyrieMetal;
	public static Block blockLiquidGravitonium;
	public static Block blockLiquidTrichromadentium;
	public static Block blockLiquidAtronium;
	public static Block blockLiquidEbonite;
	public static Block blockLiquidSwampSteel;
	public static Block blockLiquidRotiron;
	public static Block blockLiquidHallowsite;
	public static Block blockLiquidAncientAlloy;
	public static Block blockRottenSludge;
	public static Block blockMummySludge;
	public static Block blockBetweenSludge;
	public static Block blockLiquidGoldenAmber;
	public static Block blockLiquidFusionLava;
	public static Block blockLiquidBlightsteel;
	public static Block blockLiquifiedSouls;
	public static Block blockSanguiseelium;
	/* ------------------------------------- */
	private static void registerBlocks(Block... block) {
		for (Block i : block) {
			ModFluidBlocks.addBlock(i);
		}
	}

	public static void init() {
		if (CompatManager.loadMain) {
			blockLiquidFusionite = new CustomFireBlockFluid("liquidfusionite", ModFluids.liquidFusionite, CustomFireEffect.COLD_FIRE, Material.LAVA).setSource(new DamageSource("coldfire").setFireDamage());
			blockLiquidIrradium = new RadioactiveBlockFluid("liquidirradium", ModFluids.liquidIrradium, Material.LAVA);
			blockLiquidSolarSteel = new BlockFluid("liquidsolarsteel", ModFluids.liquidSolsteel, Material.LAVA);
			blockLiquidHydrogen = new BlockFluid("liquidhydrogen", ModFluids.liquidHydrogen, BlockMaterials.GAS);
			blockLiquidGallium = new BlockFluid("liquidgallium", ModFluids.liquidGallium, Material.LAVA);
			blockLiquidRuneSteel = new BlockFluid("liquidrunesteel", ModFluids.liquidRuneSteel, Material.LAVA);
			blockLiquidGravitonium = new BlockFluid("liquidgravitonium", ModFluids.liquidGravitonium, Material.LAVA);
			blockLiquidTrichromadentium = new BlockFluid("liquidtrichromadentium", ModFluids.liquidTrichromadentium, Material.LAVA);
			blockLiquidAtronium = new BlockFluid("liquidatronium", ModFluids.liquidAtronium, Material.LAVA);
			blockLiquidEbonite = new BlockFluid("liquidebonite", ModFluids.liquidEbonite, Material.LAVA);			
			blockLiquidValasium = new BlockFluid("liquidvalasium", ModFluids.liquidValasium, Material.LAVA);			
			blockLiquidHallowsite = new CustomFireBlockFluid("liquidhallowsite", ModFluids.liquidHallowsite, CustomFireEffect.SPIRIT_FIRE, Material.LAVA).setSource(new DamageSource("haunted").setFireDamage());			
			blockLiquidBlightsteel = new BlockFluid("liquidblightsteel", ModFluids.liquidBlightsteel, Material.LAVA);			
			blockSanguiseelium = new BlockFluid("liquidsanguiseelium", ModFluids.liquidSanguiseelium, Material.LAVA);			
			
			blockLiquidFusionLava = new CustomFireBlockFluid("liquidfusionlava", ModFluids.liquidFusionLava, CustomFireEffect.COLD_FIRE, Material.LAVA).setSource(new DamageSource("coldfire").setFireDamage());			
			blockLiquifiedSouls = new BlockFluid("liquifiedsouls", ModFluids.liquidLiquifiedSouls, Material.WATER);			
			
		    ModFluidBlocks.registerBlocks(
/* ------------------------------------- */
				blockLiquidFusionite,
				blockLiquidIrradium,
				blockLiquidSolarSteel,
				blockLiquidHydrogen,
				blockLiquidGallium,
				blockLiquidRuneSteel,
				blockLiquidGravitonium,
				blockLiquidTrichromadentium,
				blockLiquidAtronium,
				blockLiquidEbonite,
				blockLiquidFusionLava,
				blockLiquidValasium,
				blockLiquidHallowsite,
				blockLiquidBlightsteel,
				blockSanguiseelium,
				blockLiquifiedSouls
/* ------------------------------------- */
				);
		}
		if(CompatManager.twilightforest) {
			blockLiquidPenguinite = new BlockFluid("LiquidPenguinite".toLowerCase(), ModFluids.liquidPenguinite, Material.LAVA);
			blockLiquidIronwood = new BlockFluid("LiquidIronWood".toLowerCase(), ModFluids.liquidIronwood, Material.LAVA);
			ModFluidBlocks.registerBlocks(
/* ------------------------------------- */
					blockLiquidPenguinite,
					blockLiquidIronwood
/* ------------------------------------- */
					);
		}
		if(CompatManager.aether_legacy) {
			blockLiquidArkenium = new BlockFluid("liquidarkenium", ModFluids.liquidArkenium, Material.LAVA);
			blockLiquidValkyrieMetal = new BlockFluid("liquidvalkyriemetal", ModFluids.liquidValkyrieMetal, Material.LAVA);
			blockLiquidGoldenAmber = new BlockFluid("liquidgoldenamber", ModFluids.liquidGoldenAmber, Material.WATER);
			ModFluidBlocks.registerBlocks(
/* ------------------------------------- */
					blockLiquidArkenium,
					blockLiquidValkyrieMetal,
					blockLiquidGoldenAmber
			/* ------------------------------------- */
					);
		}
		if(CompatManager.thebetweenlands) {
			blockBurningSulfurFlow = new BlockFluid("burningSulfurFlow".toLowerCase(), ModFluids.liquidBurningSulfurFlow, Material.LAVA);
			blockLiquidOctine = new BlockFluid("liquidoctine", ModFluids.liquidOctine, Material.LAVA);
			blockLiquidSyrmorite = new BlockFluid("liquidsyrmorite", ModFluids.liquidSyrmorite, Material.LAVA);
			blockLiquidEmber = new BlockFluid("liquidember", ModFluids.liquidEmber, Material.LAVA);
			blockLiquidSwampSteel = new BlockFluid("liquidswampsteel", ModFluids.liquidSwampSteel, Material.LAVA);
			blockLiquidRotiron = new BlockFluid("liquidrotiron", ModFluids.liquidRotiron, Material.LAVA);
			blockRottenSludge = new BlockFluid("rottensludge", ModFluids.liquidRottenSludge, Material.WATER);
			blockMummySludge = new BlockFluid("mummysludge", ModFluids.liquidMummySludge, Material.WATER);
			blockBetweenSludge = new BlockFluid("betweensludge", ModFluids.liquidBetweenSludge, Material.WATER);
			blockLiquidAncientAlloy = new BlockFluid("liquidancientalloy", ModFluids.liquidAncientAlloy, Material.LAVA);
			
			ModFluidBlocks.registerBlocks(
/* ------------------------------------- */
					blockBurningSulfurFlow,
					blockLiquidSyrmorite,
					blockLiquidOctine,
					blockLiquidEmber,
					blockLiquidSwampSteel,
					blockLiquidRotiron,
					blockRottenSludge,
					blockMummySludge,
					blockBetweenSludge,
					blockLiquidAncientAlloy
/* ------------------------------------- */
					);
		}
	}
	private static void addBlock(Block block) {

		RegisterHelper.registerBlock(block);
		ModBlocks.totalBlocks++;
	}
}
