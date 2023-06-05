package com.existingeevee.moretcon.inits;

import com.existingeevee.moretcon.item.ItemBase;
import com.existingeevee.moretcon.item.ItemDebugTool;
import com.existingeevee.moretcon.item.ItemNonflamable;
import com.existingeevee.moretcon.other.ModTabs;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.ConfigHandler;
import com.existingeevee.moretcon.other.utils.RegisterHelper;

import net.minecraft.item.Item;

public class ModItems {
	public static int totalItems;
/*---------------------------------------*/
	
	//Metals
	public static Item nuggetFusionite = new ItemBase("nuggetFusionite");
	public static Item ingotFusionite = new ItemBase("ingotFusionite");
	public static Item dustFusionite = new ItemBase("dustFusionite");

	public static Item nuggetRotiron = new ItemBase("nuggetRotiron");
	public static Item ingotRotiron = new ItemBase("ingotRotiron");
	public static Item dustRotiron = new ItemBase("dustRotiron");

	public static Item nuggetSwampSteel = new ItemBase("nuggetSwampSteel");
	public static Item ingotSwampSteel = new ItemBase("ingotSwampSteel");
	public static Item dustSwampSteel = new ItemBase("dustSwampSteel");

	public static Item ingotGallium = new ItemBase("ingotGallium");
	public static Item nuggetGallium = new ItemBase("nuggetGallium");
	public static Item dustGallium = new ItemBase("dustGallium");

	public static Item nuggetIrradium = new ItemBase("nuggetIrradium");
	public static Item dustIrradium = new ItemBase("dustIrradium");
	public static Item ingotIrradium = new ItemBase("ingotIrradium");

	public static Item ingotGravitonium = new ItemBase("ingotGravitonium");
	public static Item nuggetGravitonium = new ItemBase("nuggetGravitonium");
	public static Item dustGravitonium = new ItemBase("dustGravitonium");

	public static Item ingotRuneSteel = new ItemBase("ingotRuneSteel");
	public static Item dustRuneSteel = new ItemBase("dustRuneSteel");
	public static Item nuggetRuneSteel = new ItemBase("nuggetRuneSteel");

	public static Item nuggetSolarSteel = new ItemNonflamable("nuggetSolarSteel");
	public static Item ingotSolarSteel = new ItemNonflamable("ingotSolarSteel");
	public static Item dustSolarSteel = new ItemNonflamable("dustSolarSteel");

	public static Item ingotValkyrieMetal = new ItemBase("ingotValkyrieMetal");
	public static Item nuggetValkyrieMetal = new ItemBase("nuggetValkyrieMetal");
	public static Item dustValkyrieMetal = new ItemBase("dustValkyrieMetal");

	public static Item nuggetPenguinite = new ItemBase("nuggetPenguinite");
	public static Item ingotPenguinite = new ItemBase("ingotPenguinite");
	public static Item dustPenguinite = new ItemBase("dustPenguinite");

	public static Item ingotArkenium = new ItemBase("ingotArkenium");
	public static Item nuggetArkenium = new ItemBase("nuggetArkenium");
	public static Item dustArkenium = new ItemBase("dustArkenium");

	public static Item ingotAtronium = new ItemBase("ingotAtronium");
	public static Item nuggetAtronium = new ItemBase("nuggetAtronium");
	public static Item dustAtronium = new ItemBase("dustAtronium");
	
	public static Item ingotEbonite = new ItemBase("ingotEbonite");
	public static Item nuggetEbonite = new ItemBase("nuggetEbonite");
	public static Item dustEbonite = new ItemBase("dustEbonite");
	
	public static Item carbonPile = new ItemBase("carbonPile").setTab(ModTabs.moarTConMisc);;
	public static Item ingotSteel = new ItemBase("ingotSteel");
	public static Item nuggetSteel = new ItemBase("nuggetSteel");
	public static Item dustSteel = new ItemBase("dustSteel");
	public static Item rawSteel = new ItemBase("rawSteel").setTab(ModTabs.moarTConMisc);

	public static Item ingotTrichromadentium = new ItemBase("ingotTrichromadentium");
	public static Item nuggetTrichromadentium = new ItemBase("nuggetTrichromadentium");
	public static Item dustTrichromadentium = new ItemBase("dustTrichromadentium");

	public static Item dustIronwood = new ItemBase("dustIronwood");
	
	public static Item dustFiery = new ItemBase("dustFiery");

	public static Item dustKnightmetal = new ItemBase("dustKnightmetal");

	public static Item dustOctine = new ItemBase("dustOctine");

	public static Item dustSyrmorite = new ItemBase("dustSyrmorite");

	//Gems
	public static Item gemVoidSpar = new ItemBase("gemVoidSpar");
	public static Item gemEnderal = new ItemBase("gemEnderal");
	public static Item gemGarstone = new ItemBase("gemGarstone");
	public static Item gemBloodstone = new ItemBase("gemBloodstone");
	public static Item gemEchostone = new ItemBase("gemEchostone");
	public static Item gemIgniglomerate = new ItemNonflamable("gemIgniglomerate");
	public static Item gemErythynite = new ItemBase("gemErythynite");

	//Internal
	public static Item iconLightning = new ItemBase("lightning").setTab(null);

	//Representative Items
	public static Item repItemEnderexamite = new ItemBase("repitemenderexamite").setTab(null);
	public static Item repItemFerroherb = new ItemBase("repitemferroherb").setTab(null);
	public static Item repItemShadowglass = new ItemBase("repItemShadowglass").setTab(null);

	//Ingriedients
	public static Item itemSiltClay = new ItemBase("itemSiltClay").setTab(ModTabs.moarTConMisc);;
	public static Item spaceTimeDisruptionPowder = new ItemBase("spaceTimeDisruptionPowder").setTab(ModTabs.moarTConMisc);;
	public static Item hydrogenRichRedstonePowder = new ItemBase("hydrogenRichRedstonePowder").setTab(ModTabs.moarTConMisc);;
	public static Item crushedShockwaveSword = new ItemBase("crushedShockwaveSword").setTab(ModTabs.moarTConMisc);
	public static Item matterReconstructionGel = new ItemBase("matterReconstructionGel").setTab(ModTabs.moarTConMisc);
	public static Item sulfurBucketSyrmorite = new ItemBase("sulfurBucketSyrmorite").setTab(ModTabs.moarTConMisc).setMaxStackSize(1);
	public static Item sulfurBucketIron = new ItemBase("sulfurBucketIron").setTab(ModTabs.moarTConMisc).setMaxStackSize(1);
	public static Item betweenicCore = new ItemBase("betweenicCore").setTab(ModTabs.moarTConMisc);

	//Modifier items
	public static Item betweenifiedModifier = new ItemBase("betweenifiedModifier").setTab(ModTabs.moarTConMisc);

/*---------------------------------------*/

	public static void registerItems(Item... items) {
		for (Item i : items) {
			ModItems.addItem(i);
		}
	}
	public static void init() {
		if (CompatManager.loadMain) {
			ModItems.registerItems(
/**-------------------------------------**/
					ingotFusionite,
					nuggetFusionite,
					
					ingotIrradium,
					nuggetIrradium,
					
					ingotSolarSteel,
					nuggetSolarSteel,
					
					ingotGallium,
					nuggetGallium,
					
					ingotSteel,
					nuggetSteel,
					
					ingotGravitonium,
					nuggetGravitonium,
					
					ingotRuneSteel,
					nuggetRuneSteel,
					
					ingotTrichromadentium,
					nuggetTrichromadentium,
					
					ingotAtronium,
					nuggetAtronium,
					
					ingotEbonite,
					nuggetEbonite,
					
					gemVoidSpar,
					gemEnderal,
					gemGarstone,
					gemBloodstone,
					gemEchostone,
					gemIgniglomerate,
					gemErythynite,
					
					matterReconstructionGel,
					carbonPile,
					rawSteel,
					spaceTimeDisruptionPowder,
					hydrogenRichRedstonePowder,
					
					repItemEnderexamite,
					repItemShadowglass,
					
					new ItemDebugTool()

/**-------------------------------------**/

				);
			if(ConfigHandler.shouldLoadDust) {
				ModItems.registerItems(
						dustFusionite,
						dustIrradium,
						dustSolarSteel,
						dustGallium,
						dustSteel,
						dustGravitonium,
						dustRuneSteel,
						dustTrichromadentium,
						dustAtronium,
						dustEbonite
				);
			}
		}
		if(CompatManager.twilightforest) {
			ModItems.registerItems(
					nuggetPenguinite,
					repItemFerroherb,
					ingotPenguinite
					);
			if (ConfigHandler.shouldLoadDust && ConfigHandler.shouldLoadDustForCompatability) {
				ModItems.registerItems(
						dustPenguinite,
						dustIronwood,
						dustFiery,
						dustKnightmetal
				);
			}
		}

		if (CompatManager.thebetweenlands) {
			ModItems.registerItems(
					ingotRotiron,
					nuggetRotiron,
					
					ingotSwampSteel,
					nuggetSwampSteel,
					
					itemSiltClay,
					crushedShockwaveSword,

					sulfurBucketSyrmorite,
					sulfurBucketIron,
					
					iconLightning,
					betweenifiedModifier,
					betweenicCore

			);
			if (ConfigHandler.shouldLoadDust && ConfigHandler.shouldLoadDustForCompatability) {
				ModItems.registerItems(
						dustOctine,
						dustSyrmorite,
						dustRotiron,
						dustSwampSteel
				);
			}

		}

		if(CompatManager.aether_legacy) {
			ModItems.registerItems(
					ingotArkenium,
					nuggetArkenium,
					ingotValkyrieMetal,
					nuggetValkyrieMetal
					);
			if (ConfigHandler.shouldLoadDust && ConfigHandler.shouldLoadDustForCompatability) {
				ModItems.registerItems(
						dustArkenium,
						dustValkyrieMetal
				);
			}

		}
		//Logging.log("Loaded a total of " + totalItems + " different items.");
	}


	private static void addItem(Item item) {
		RegisterHelper.registerItem(item);
		totalItems++;
	}
}
