package com.existingeevee.moretcon.inits;

import com.existingeevee.moretcon.block.BlockCustomFire;
import com.existingeevee.moretcon.block.blocktypes.BlockBase;
import com.existingeevee.moretcon.block.blocktypes.BlockBrinkstonePlant;
import com.existingeevee.moretcon.block.blocktypes.BlockElectarite;
import com.existingeevee.moretcon.block.blocktypes.BlockEtherealBase;
import com.existingeevee.moretcon.block.blocktypes.BlockFallingBase;
import com.existingeevee.moretcon.block.blocktypes.BlockHot;
import com.existingeevee.moretcon.block.blocktypes.BlockMossyBrinkstone;
import com.existingeevee.moretcon.block.blocktypes.BlockRadioactive;
import com.existingeevee.moretcon.block.blocktypes.BlockReforgeStation;
import com.existingeevee.moretcon.block.blocktypes.unique.BlockGravitoniumFaucet;
import com.existingeevee.moretcon.block.blocktypes.unique.BlockIgniglomerateCluster;
import com.existingeevee.moretcon.block.blocktypes.unique.BlockOreGravitonium;
import com.existingeevee.moretcon.block.blocktypes.unique.BlockPerimidumOre;
import com.existingeevee.moretcon.block.blocktypes.unique.BlockRunesteel;
import com.existingeevee.moretcon.block.blocktypes.unique.BlockVoidColumn;
import com.existingeevee.moretcon.block.blocktypes.unique.BlockVoidCore;
import com.existingeevee.moretcon.block.blocktypes.unique.BlockVoidPrismBottom;
import com.existingeevee.moretcon.block.blocktypes.unique.BlockVoidPrismTop;
import com.existingeevee.moretcon.block.ore.BlockBedrockOre;
import com.existingeevee.moretcon.block.ore.BlockBedrockOreMetal;
import com.existingeevee.moretcon.block.ore.BlockEtherealOre;
import com.existingeevee.moretcon.block.ore.BlockEtherealOreMetal;
import com.existingeevee.moretcon.block.ore.BlockOre;
import com.existingeevee.moretcon.block.ore.BlockOreMetal;
import com.existingeevee.moretcon.other.ClusterTickingHandler.IClusterType;
import com.existingeevee.moretcon.other.ModTabs;
import com.existingeevee.moretcon.other.MoreTConLogger;
import com.existingeevee.moretcon.other.fires.CustomFireEffect;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.other.utils.RegisterHelper;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.DamageSource;

public class ModBlocks {
	public static int totalBlocks;
	/*---------------------------------------*/
	public static final Block blockFusionite = ((BlockBase) new BlockBase("blockFusionite", Material.IRON, 3).setCanSustainFire(true).setFireTransformer(() -> ModBlocks.fireCold).setHardness(10).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreFusionite = ((BlockBase) new BlockOreMetal("oreFusionite", 4, ModItems.ingotFusionite).setCanSustainFire(true).setFireTransformer(() -> ModBlocks.fireCold).setHardness(7.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block oreBedrockFusionite = ((BlockBase) new BlockBedrockOreMetal("oreBedrockFusionite", 4, ModItems.ingotFusionite).setCanSustainFire(true).setFireTransformer(() -> ModBlocks.fireCold).setResistance(Float.MAX_VALUE).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block blockIrradium = ((BlockBase) new BlockRadioactive("blockIrradium", Material.IRON, 3).setHardness(10).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreIrradium = ((BlockBase) new BlockOreMetal("oreIrradium", 2, ModItems.ingotIrradium).setHardness(7.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block blockSolsteel = ((BlockBase) new BlockHot("blockSolarSteel", Material.IRON, 3).setCanBurn(false).setHardness(10).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block blockPenguinite = ((BlockBase) new BlockBase("blockPenguinite", Material.IRON, 3).setHardness(10).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreVoidSpar = ((BlockBase) new BlockOre("oreVoidSpar", 3, ModItems.gemVoidSpar, 3).setHardness(7.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block oreNaturalVoidSpar = ((BlockBase) new BlockBedrockOre("oreNaturalVoidSpar", 4, ModItems.gemVoidSpar, 1, 1).setResistance(Float.MAX_VALUE).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block blockVoidSpar = ((BlockBase) new BlockBase("blockVoidSpar", Material.IRON, 4).setHardness(10).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreEnderal = ((BlockBase) new BlockOre("oreEnderal", 4, ModItems.gemEnderal, 1).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block blockEnderal = ((BlockBase) new BlockBase("blockEnderal", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block blockGarstone = ((BlockBase) new BlockBase("blockGarstone", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreGarstone = ((BlockBase) new BlockOre("oreGarstone", 4, ModItems.gemGarstone, 1).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block blockRuneSteel = ((BlockBase) new BlockRunesteel("blockRuneSteel", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block blockGallium = ((BlockBase) new BlockBase("blockGallium", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreGallium = ((BlockBase) new BlockOreMetal("oreGallium", 5, ModItems.ingotGallium).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block oreGalliumEthereal = ((BlockBase) new BlockEtherealOreMetal("oreGalliumEthereal", 5, ModItems.ingotGallium).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block oreArkenium = ((BlockBase) new BlockOreMetal("oreArkenium", 2, ModItems.ingotArkenium).setHardness(7.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block blockArkenium = ((BlockBase) new BlockBase("blockArkenium", Material.IRON, 2).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block blockSteel = ((BlockBase) new BlockBase("blockSteel", Material.IRON, 1).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block blockGravitite = ((BlockBase) new BlockBase("blockGravitite", Material.IRON, 3).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block blockValkyrieMetal = ((BlockBase) new BlockBase("blockValkyrieMetal", Material.IRON, 3).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block blockTrichromadentium = (new BlockBase("blockTrichromadentium", Material.IRON, 6).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block blockAtronium = (new BlockBase("blockAtronium", Material.IRON, 6).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block oreGravitonium = ((BlockBase) new BlockOreGravitonium("oreGravitonium", 4, ModItems.ingotGravitonium).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block oreGravitoniumDense = ((BlockBase) new BlockBase("oreGravitoniumDense", Material.ROCK, 4).setClusterDate(() -> (IClusterType) oreGravitonium, 4).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block blockGravitonium = (new BlockBase("blockGravitonium", Material.IRON, 4).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block blockSwampSteel = (new BlockBase("blockSwampSteel", Material.IRON, 4).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block blockRotiron = (new BlockBase("blockRotiron", Material.IRON, 3).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block oreBloodstone = ((BlockBase) new BlockBedrockOre("oreBloodstone", 4, ModItems.gemBloodstone, 1, 1).setResistance(Float.MAX_VALUE).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block blockBloodstone = ((BlockBase) new BlockBase("blockBloodstone", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreEchostone = ((BlockBase) new BlockOre("oreEchostone", 5, ModItems.gemEchostone, 1).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block blockEchostone = ((BlockBase) new BlockBase("blockEchostone", Material.IRON, 6).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreIgniglomerate = ((BlockBase) new BlockIgniglomerateCluster().setCanBurn(false).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false).setLightLevel(1);
	public static final Block blockIgniglomerate = ((BlockBase) new BlockHot("blockIgniglomerate", Material.IRON, 6).setCanBurn(false).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true).setLightLevel(1);
	public static final Block oreEbonite = ((BlockBase) new BlockBedrockOreMetal("oreEbonite", 5, ModItems.ingotEbonite).setResistance(Float.MAX_VALUE).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block blockEbonite = ((BlockBase) new BlockBase("blockEbonite", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreErythynite = ((BlockBase) new BlockOre("oreErythynite", 5, ModItems.gemErythynite, 1).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false).setLightLevel(1);
	public static final Block blockErythynite = ((BlockBase) new BlockBase("blockErythynite", Material.IRON, 6).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true).setLightLevel(3);
	public static final Block oreEtherstone = (new BlockEtherealOre("oreEtherstone", 8, ModItems.gemEtherstone).setHardness(40).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block blockEtherstone = ((BlockBase) new BlockBase("blockEtherstone", Material.IRON, 8).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true).setLightLevel(0.5f);
	public static final Block oreElectarite = (new BlockOre("oreElectarite", 4, ModItems.gemElectarite).setHardness(12).setResistance(8).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block blockElectarite = ((BlockBase) new BlockElectarite("blockElectarite", Material.IRON, 4).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreHallowsite = (new BlockOreMetal("oreHallowsite", 4, ModItems.ingotHallowsite).setCanSustainFire(true).setFireTransformer(() -> ModBlocks.fireSpirit).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block blockHallowsite = ((BlockBase) new BlockBase("blockHallowsite", Material.IRON, 4).setCanSustainFire(true).setFireTransformer(() -> ModBlocks.fireSpirit).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block blockAncientAlloy = (new BlockBase("blockAncientAlloy", Material.IRON, 4).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block blockValasium = ((BlockBase) new BlockBase("blockValasium", Material.IRON, 6).setHardness(10).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block blockBlightsteel = ((BlockBase) new BlockBase("blockBlightsteel", Material.IRON, 6).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block blockSanguiseelium = ((BlockBase) new BlockBase("blockSanguiseelium", Material.IRON, 6).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreZracohlium = ((BlockBase) new BlockOreMetal("oreZracohlium", 5, ModItems.ingotZracohlium).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static final Block blockZracohlium = ((BlockBase) new BlockRadioactive("blockZracohlium", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block blockSlimesteel = ((BlockBase) new BlockBase("blockSlimesteel", Material.IRON, 3).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreMonolite = (new BlockOre("oreMonolite", 5, ModItems.gemMonolite).setHardness(12).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block blockMonolite = ((BlockBase) new BlockBase("blockMonolite", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true).setLightLevel(0.5f);
	public static final Block blockPorksteel = ((BlockBase) new BlockBase("blockPorksteel", Material.IRON, 2).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block orePerimidum = ((BlockBase) new BlockPerimidumOre().setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false).setLightLevel(1);
	public static final Block blockPerimidum = ((BlockBase) new BlockBase("blockPerimidum", Material.IRON, 5).setClusterDate(() -> (IClusterType) orePerimidum, 2).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block blockGeodesium = ((BlockBase) new BlockBase("blockGeodesium", Material.IRON, 6).setLightLevel(1).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreGeodesium = ((BlockBase) new BlockOreMetal("oreGeodesium", 6, ModItems.ingotGeodesium).setHardness(9.5f).setLightLevel(12).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);  
	public static final Block oreAnthracite = (new BlockOre("oreAnthracite", 5, ModItems.gemAnthracite).setHardness(12).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block blockAnthracite = ((BlockBase) new BlockBase("blockAnthracite", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static final Block oreIonstone = (new BlockEtherealOre("oreIonstone", 8, ModItems.gemIonstone).setHardness(40).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block blockIonstone = ((BlockBase) new BlockBase("blockIonstone", Material.IRON, 8).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true).setLightLevel(0.5f);
	public static final Block oreVacuuite = (new BlockEtherealOre("oreVacuuite", 8, ModItems.gemVacuuite).setHardness(40).setCreativeTab(ModTabs.moarTConMaterials));
	public static final Block blockVacuuite = ((BlockBase) new BlockBase("blockVacuuite", Material.IRON, 8).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	
	public static final Block blockOtherstone = (new BlockEtherealBase("blockOtherstone", Material.ROCK, 1).setHardness(30).setCreativeTab(ModTabs.moarTConWorld));
	public static final Block blockCobbledBedrock = (new BlockBase("blockCobbledBedrock", Material.ROCK, 4).setResistance(Float.MAX_VALUE).setHardness(40).setCreativeTab(ModTabs.moarTConWorld));
	public static final Block blockCobbledBetweenBedrock = (new BlockBase("blockCobbledBetweenBedrock", Material.ROCK, 4).setResistance(Float.MAX_VALUE).setHardness(40).setCreativeTab(ModTabs.moarTConWorld));
	public static final Block blockBrokenSand = (new BlockFallingBase("blockBrokenSand", Material.SAND, 0).setHarvestLevelC("shovel", 0).setSoundType(SoundType.SAND).setHardness(10).setCreativeTab(ModTabs.moarTConWorld));
	public static final Block blockBrinkstone = (new BlockBase("blockBrinkstone", Material.ROCK, 3).setHardness(15).setCreativeTab(ModTabs.moarTConWorld));
	public static final Block blockMossyBrinkstone = (new BlockMossyBrinkstone("blockMossyBrinkstone", Material.ROCK, 3).setHardness(15).setLightLevel(1).setCreativeTab(ModTabs.moarTConWorld));
	public static final Block blockPerimigrowth = (new BlockBrinkstonePlant("blockPerimigrowth").setLightLevel(1).setCreativeTab(ModTabs.moarTConWorld));
	public static final Block blockPerimishroom = (new BlockBrinkstonePlant("blockPerimishroom").setLightLevel(1).setCreativeTab(ModTabs.moarTConWorld));
	public static final Block blockDarkBrinkstone = (new BlockBase("blockDarkBrinkstone", Material.ROCK, 3).setHardness(15).setLightLevel(1).setCreativeTab(ModTabs.moarTConWorld));

	public static final Block blockGravitoniumFaucet = new BlockGravitoniumFaucet();
	public static final Block blockCragravel = ((BlockBase) new BlockFallingBase("blockCragravel", Material.GROUND, 0).setHarvestLevelC("shovel", 0).setHardness(0.6f).setResistance(0.6f)).canBeBeacon(false).setCreativeTab(ModTabs.moarTConMisc);
	public static final Block blockSiltClay = ((BlockBase) new BlockBase("blockSiltClay", Material.CLAY, 0).setHarvestLevelC("shovel", 0).setHardness(0.6f).setResistance(0.6f)).canBeBeacon(false).setCreativeTab(ModTabs.moarTConMisc);

	public static final Block blockVoidPrismTop = new BlockVoidPrismTop().setHardness(10).setResistance(20);
	public static final Block blockVoidPrismBottom = new BlockVoidPrismBottom().setHardness(10).setResistance(20);
	public static final Block blockVoidColumn = new BlockVoidColumn().setHardness(10).setResistance(20);
	public static final Block blockVoidCore = new BlockVoidCore();

	public static final Block blockReforgeStation = new BlockReforgeStation().setUnlocalizedName(MiscUtils.createNonConflictiveName("blockreforgestation"));
	
	public static final Block fireCold = new BlockCustomFire("fireCold", CustomFireEffect.COLD_FIRE).setBypassFireImmunity(true).setDamage(4).setCustomEffect(e -> e.attackEntityFrom(new DamageSource("coldfire").setFireDamage(), Math.max(4, e.getHealth() / 10))).setSource(new DamageSource("coldfire").setFireDamage());
	public static final Block fireSpirit = new BlockCustomFire("fireSpirit", CustomFireEffect.SPIRIT_FIRE).setDamage(4).setSource(new DamageSource("haunted").setFireDamage());
	/*---------------------------------------*/

	public static void registerBlocks(Block... block) {
		for (Block i : block) {
			ModBlocks.addBlock(i);
		}
	}

	public static void init() {		
		ModBlocks.registerBlocks(
				blockGravitoniumFaucet,
				blockReforgeStation
		//FYI these will be back in the future. just not now.
		//blockVoidPrismTop,
		//blockVoidPrismBottom,
		//blockVoidColumn,
		//blockVoidCore
		);
		
		if (CompatManager.tic3backport) {
			ModBlocks.registerBlocks(
					blockSlimesteel
			);
		}

		if (CompatManager.loadMain) {
			ModBlocks.registerBlocks(
					/**-------------------------------------**/
					blockFusionite,
					oreBedrockFusionite,
					oreFusionite,
					blockValasium,
					blockIrradium,
					oreIrradium,
					blockSolsteel,
					oreVoidSpar,
					blockVoidSpar,
					oreNaturalVoidSpar,
					oreEnderal,
					blockEnderal,
					blockGarstone,
					oreGarstone,
					blockGallium,
					oreGallium,
					blockRuneSteel,
					blockTrichromadentium,
					oreGravitonium,
					oreGravitoniumDense,
					blockGravitonium,
					blockSteel,
					oreBloodstone,
					blockBloodstone,
					oreEchostone,
					blockEchostone,
					oreIgniglomerate,
					blockIgniglomerate,
					blockAtronium,
					oreEbonite,
					blockEbonite,
					oreErythynite,
					blockErythynite,
					oreEtherstone,
					blockEtherstone,
					oreElectarite,
					blockElectarite,
					oreHallowsite,
					blockHallowsite,
					blockBlightsteel,
					blockSanguiseelium,
					oreZracohlium,
					blockZracohlium,
					oreMonolite,
					blockMonolite,
					blockPorksteel,
					orePerimidum,
					blockPerimidum,
					oreGeodesium,
					blockGeodesium,
					oreAnthracite,
					blockAnthracite,
					oreIonstone,
					blockIonstone,
					oreGalliumEthereal,
					oreVacuuite,
					blockVacuuite,
					/**-------------------------------------**/
					blockCobbledBedrock,
					blockBrinkstone,
					blockOtherstone,
					blockBrokenSand,
					blockMossyBrinkstone,
					blockPerimishroom,
					blockPerimigrowth,
					blockDarkBrinkstone,
					/**-------------------------------------**/
					fireCold,
					fireSpirit
					);
		}
		if (CompatManager.twilightforest) {
			ModBlocks.registerBlocks(
					/**-------------------------------------**/
					blockPenguinite
			/**-------------------------------------**/
			);
		}

		if (CompatManager.thebetweenlands) {
			ModBlocks.registerBlocks(
					/**-------------------------------------**/
					blockSiltClay,
					blockCragravel,
					blockSwampSteel,
					blockRotiron,
					blockAncientAlloy,
					blockCobbledBetweenBedrock
			/**-------------------------------------**/
			);
		}
		if (CompatManager.easterEggs) {
			ModBlocks.registerBlocks(
			/**-------------------------------------**/

			/**-------------------------------------**/
			);
		}
		if (CompatManager.aether_legacy) {
			ModBlocks.registerBlocks(
					/**-------------------------------------**/
					oreArkenium,
					blockArkenium,
					blockGravitite,
					blockValkyrieMetal
			/**-------------------------------------**/
			);
		}
		ModTileEntities.init();
		MoreTConLogger.log("Loaded a total of " + totalBlocks + " different blocks.");

	}

	private static void addBlock(Block block) {

		RegisterHelper.registerBlock(block);
		totalBlocks++;
	}
}
