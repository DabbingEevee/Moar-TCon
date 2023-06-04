package com.existingeevee.moretcon.inits;

import com.existingeevee.moretcon.block.blocktypes.BlockBase;
import com.existingeevee.moretcon.block.blocktypes.BlockFalling;
import com.existingeevee.moretcon.block.blocktypes.HotBlockBase;
import com.existingeevee.moretcon.block.blocktypes.RadiationBlockBase;
import com.existingeevee.moretcon.block.ore.BlockBedrockOre;
import com.existingeevee.moretcon.block.ore.BlockBedrockOreMetal;
import com.existingeevee.moretcon.block.ore.BlockOre;
import com.existingeevee.moretcon.block.ore.BlockOreMetal;
import com.existingeevee.moretcon.block.tile.BlockGravitoniumFaucet;
import com.existingeevee.moretcon.other.ModTabs;
import com.existingeevee.moretcon.other.MoreTConLogger;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.RegisterHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	public static int totalBlocks;
/*---------------------------------------*/
	public static Block blockFusionite = ((BlockBase) new BlockBase("blockFusionite", Material.IRON, 3).setHardness(10).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block oreFusionite = ((BlockBase) new BlockOreMetal("oreFusionite", 4, ModItems.ingotFusionite).setHardness(7.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block blockIrradium = ((BlockBase) new RadiationBlockBase("blockIrradium", Material.IRON, 3).setHardness(10).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block oreIrradium = ((BlockBase) new BlockOreMetal("oreIrradium", 2, ModItems.ingotIrradium).setHardness(7.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block blockSolarSteel = ((BlockBase) new HotBlockBase("blockSolarSteel", Material.IRON, 3).setCanBurn(false).setHardness(10).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block blockPenguinite = ((BlockBase) new BlockBase("blockPenguinite", Material.IRON, 3).setHardness(10).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block oreVoidSpar = ((BlockBase) new BlockOre("oreVoidSpar", 3, ModItems.gemVoidSpar, 1).setHardness(7.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block blockVoidSpar = ((BlockBase) new BlockBase("blockVoidSpar", Material.IRON, 4).setHardness(10).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block oreEnderal = ((BlockBase) new BlockOre("oreEnderal", 4, ModItems.gemEnderal, 1).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block blockEnderal = ((BlockBase) new BlockBase("blockEnderal", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block blockGarstone = ((BlockBase) new BlockBase("blockGarstone", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block oreGarstone = ((BlockBase) new BlockOre("oreGarstone", 4, ModItems.gemGarstone, 1).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block blockRuneSteel = ((BlockBase) new BlockBase("blockRuneSteel", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block blockGallium = ((BlockBase) new BlockBase("blockGallium", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block oreGallium = ((BlockBase) new BlockOreMetal("oreGallium", 5, ModItems.ingotGallium).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block blockCragravel = ((BlockBase) new BlockFalling("blockCragravel", Material.GROUND, 0).setHardness(0.6f).setResistance(0.6f).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false).setCreativeTab(ModTabs.moarTConMisc);
	public static Block blockSiltClay = ((BlockBase) new BlockBase("blockSiltClay", Material.CLAY ,5).setHardness(0.6f).setResistance(0.6f).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false).setCreativeTab(ModTabs.moarTConMisc);
	public static Block oreArkenium = ((BlockBase) new BlockOreMetal("oreArkenium", 2, ModItems.ingotArkenium).setHardness(7.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block blockArkenium = ((BlockBase) new BlockBase("blockArkenium", Material.IRON, 2).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block blockSteel = ((BlockBase) new BlockBase("blockSteel", Material.IRON, 1).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block blockGravitite = ((BlockBase) new BlockBase("blockGravitite", Material.IRON, 3).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block blockValkyrieMetal = ((BlockBase) new BlockBase("blockValkyrieMetal", Material.IRON, 3).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block blockTrichromadentium = ((BlockBase) new BlockBase("blockTrichromadentium", Material.IRON, 6).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials));
	public static Block blockAtronium = ((BlockBase) new BlockBase("blockAtronium", Material.IRON, 6).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials));
	public static Block blockGravitoniumFaucet = new BlockGravitoniumFaucet();
	public static Block oreGravitonium = ((BlockBase) new BlockOreMetal("oreGravitonium", 4, ModItems.ingotGravitonium).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block oreGravitoniumDense = ((BlockBase) new BlockBase("oreGravitoniumDense", Material.ROCK, 4).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block blockGravitonium = ((BlockBase) new BlockBase("blockGravitonium", Material.IRON, 4).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials));
	public static Block blockSwampSteel = ((BlockBase) new BlockBase("blockSwampSteel", Material.IRON, 4).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials));
	public static Block blockRotiron = ((BlockBase) new BlockBase("blockRotiron", Material.IRON, 3).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials));
//	public static Block blockDestabilizedBedrock = new BlockBreakableBedrock().setResistance(Integer.MAX_VALUE).setHardness(50);
	public static Block oreNaturalVoidSpar = ((BlockBase) new BlockBedrockOre("oreNaturalVoidSpar", 4, ModItems.gemVoidSpar, 1, 1).setHardness(50).setResistance(Integer.MAX_VALUE).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block oreBloodstone = ((BlockBase) new BlockBedrockOre("oreBloodstone", 4, ModItems.gemBloodstone, 1, 1).setHardness(50).setResistance(Integer.MAX_VALUE).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block blockBloodstone = ((BlockBase) new BlockBase("blockBloodstone", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block oreEchostone = ((BlockBase) new BlockOre("oreEchostone", 5, ModItems.gemEchostone, 1).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block blockEchostone = ((BlockBase) new BlockBase("blockEchostone", Material.IRON, 6).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);
	public static Block oreIgniglomerate = ((BlockBase) new BlockOre("oreIgniglomerate", 5, ModItems.gemIgniglomerate, 1).setCanBurn(false).setHardness(9.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false).setLightLevel(1);
	public static Block blockIgniglomerate = ((BlockBase) new HotBlockBase("blockIgniglomerate", Material.IRON, 6).setCanBurn(false).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true).setLightLevel(1);
	public static Block oreEbonite = ((BlockBase) new BlockBedrockOreMetal("oreEbonite", 5, ModItems.ingotEbonite).setHardness(7.5f).setResistance(10).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(false);
	public static Block blockEbonite = ((BlockBase) new BlockBase("blockEbonite", Material.IRON, 5).setHardness(12).setResistance(20).setCreativeTab(ModTabs.moarTConMaterials)).canBeBeacon(true);



	//public static Block oreFakeFluidTest = ((BlockBase) new BlockFakeFluid("blockFakeFluid", Material.IRON, 3).setHardness(12).setResistance(20).setCreativeTab(Tabs.materials)).canBeBeacon(false);

	/*---------------------------------------*/


	public static void registerBlocks(Block... block) {
		for (Block i : block) {
			ModBlocks.addBlock(i);
		}
	}
	public static void init() {
		if (CompatManager.loadMain) {
		ModBlocks.registerBlocks(
/**-------------------------------------**/
				blockFusionite,
				oreFusionite,
				blockIrradium,
				oreIrradium,
				blockSolarSteel,
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
				blockGravitoniumFaucet,
				blockTrichromadentium,
				oreGravitonium,
				oreGravitoniumDense,
				blockGravitonium,
				blockSteel,
			//	blockDestabilizedBedrock,
				oreBloodstone,
				blockBloodstone,
				oreEchostone,
				blockEchostone,
				oreIgniglomerate,
				blockIgniglomerate,
				blockAtronium,
				oreEbonite,
				blockEbonite
/**-------------------------------------**/
				);		
		}
		if(CompatManager.twilightforest) {
			ModBlocks.registerBlocks(
/**-------------------------------------**/
					blockPenguinite
/**-------------------------------------**/
									);
		}

		if(CompatManager.thebetweenlands) {
			ModBlocks.registerBlocks(
/**-------------------------------------**/
					blockSiltClay,
					blockCragravel,
					blockSwampSteel,
					blockRotiron
/**-------------------------------------**/
									);
		}
		if(CompatManager.jokes) {
			ModBlocks.registerBlocks(
/**-------------------------------------**/

/**-------------------------------------**/
									);
		}
		if(CompatManager.aether_legacy) {
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
