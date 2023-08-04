package com.existingeevee.moretcon.inits;

import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.item.ItemBase;
import com.existingeevee.moretcon.item.ItemCompositeRep;
import com.existingeevee.moretcon.item.ItemDebugTool;
import com.existingeevee.moretcon.item.ItemNonflamable;
import com.existingeevee.moretcon.other.ModTabs;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.RegisterHelper;

import net.minecraft.item.Item;

public class ModItems {
	public static int totalItems;
	/*---------------------------------------*/

	//Metals
	public static final Item nuggetFusionite = new ItemBase("nuggetFusionite");
	public static final Item ingotFusionite = new ItemBase("ingotFusionite");
	public static final Item dustFusionite = new ItemBase("dustFusionite");

	public static final Item nuggetRotiron = new ItemBase("nuggetRotiron");
	public static final Item ingotRotiron = new ItemBase("ingotRotiron");
	public static final Item dustRotiron = new ItemBase("dustRotiron");

	public static final Item nuggetSwampSteel = new ItemBase("nuggetSwampSteel");
	public static final Item ingotSwampSteel = new ItemBase("ingotSwampSteel");
	public static final Item dustSwampSteel = new ItemBase("dustSwampSteel");

	public static final Item ingotGallium = new ItemBase("ingotGallium");
	public static final Item nuggetGallium = new ItemBase("nuggetGallium");
	public static final Item dustGallium = new ItemBase("dustGallium");

	public static final Item nuggetIrradium = new ItemBase("nuggetIrradium");
	public static final Item dustIrradium = new ItemBase("dustIrradium");
	public static final Item ingotIrradium = new ItemBase("ingotIrradium");

	public static final Item ingotGravitonium = new ItemBase("ingotGravitonium");
	public static final Item nuggetGravitonium = new ItemBase("nuggetGravitonium");
	public static final Item dustGravitonium = new ItemBase("dustGravitonium");

	public static final Item ingotRuneSteel = new ItemBase("ingotRuneSteel");
	public static final Item dustRuneSteel = new ItemBase("dustRuneSteel");
	public static final Item nuggetRuneSteel = new ItemBase("nuggetRuneSteel");

	public static final Item nuggetSolsteel = new ItemNonflamable("nuggetSolarSteel");
	public static final Item ingotSolsteel = new ItemNonflamable("ingotSolarSteel");
	public static final Item dustSolarSteel = new ItemNonflamable("dustSolarSteel");

	public static final Item ingotValkyrieMetal = new ItemBase("ingotValkyrieMetal");
	public static final Item nuggetValkyrieMetal = new ItemBase("nuggetValkyrieMetal");
	public static final Item dustValkyrieMetal = new ItemBase("dustValkyrieMetal");

	public static final Item nuggetPenguinite = new ItemBase("nuggetPenguinite");
	public static final Item ingotPenguinite = new ItemBase("ingotPenguinite");
	public static final Item dustPenguinite = new ItemBase("dustPenguinite");

	public static final Item ingotArkenium = new ItemBase("ingotArkenium");
	public static final Item nuggetArkenium = new ItemBase("nuggetArkenium");
	public static final Item dustArkenium = new ItemBase("dustArkenium");

	public static final Item ingotAtronium = new ItemBase("ingotAtronium");
	public static final Item nuggetAtronium = new ItemBase("nuggetAtronium");
	public static final Item dustAtronium = new ItemBase("dustAtronium");

	public static final Item ingotEbonite = new ItemBase("ingotEbonite");
	public static final Item nuggetEbonite = new ItemBase("nuggetEbonite");
	public static final Item dustEbonite = new ItemBase("dustEbonite");

	public static final Item carbonPile = new ItemBase("carbonPile").setTab(ModTabs.moarTConMisc);;
	public static final Item ingotSteel = new ItemBase("ingotSteel");
	public static final Item nuggetSteel = new ItemBase("nuggetSteel");
	public static final Item dustSteel = new ItemBase("dustSteel");
	public static final Item rawSteel = new ItemBase("rawSteel").setTab(ModTabs.moarTConMisc);

	public static final Item ingotTrichromadentium = new ItemBase("ingotTrichromadentium");
	public static final Item nuggetTrichromadentium = new ItemBase("nuggetTrichromadentium");
	public static final Item dustTrichromadentium = new ItemBase("dustTrichromadentium");

	public static final Item dustIronwood = new ItemBase("dustIronwood");

	public static final Item dustFiery = new ItemBase("dustFiery");

	public static final Item dustKnightmetal = new ItemBase("dustKnightmetal");

	public static final Item dustOctine = new ItemBase("dustOctine");

	public static final Item dustSyrmorite = new ItemBase("dustSyrmorite");

	//Gems
	public static final Item gemVoidSpar = new ItemBase("gemVoidSpar");
	public static final Item gemEnderal = new ItemBase("gemEnderal");
	public static final Item gemGarstone = new ItemBase("gemGarstone");
	public static final Item gemBloodstone = new ItemBase("gemBloodstone");
	public static final Item gemEchostone = new ItemBase("gemEchostone");
	public static final Item gemIgniglomerate = new ItemNonflamable("gemIgniglomerate");
	public static final Item gemErythynite = new ItemBase("gemErythynite");
	public static final Item gemEtherstone = new ItemBase("gemEtherstone");;

	//Internal
	public static final Item iconLightning = new ItemBase("lightning").setTab(null);

	//Ingriedients
	public static final Item itemSiltClay = new ItemBase("itemSiltClay").setTab(ModTabs.moarTConMisc);;
	public static final Item spaceTimeDisruptionPowder = new ItemBase("spaceTimeDisruptionPowder").setTab(ModTabs.moarTConMisc);;
	public static final Item hydrogenRichRedstonePowder = new ItemBase("hydrogenRichRedstonePowder").setTab(ModTabs.moarTConMisc);;
	public static final Item crushedShockwaveSword = new ItemBase("crushedShockwaveSword").setTab(ModTabs.moarTConMisc);
	public static final Item matterReconstructionGel = new ItemBase("matterReconstructionGel").setTab(ModTabs.moarTConMisc);
	public static final Item sulfurBucketSyrmorite = new ItemBase("sulfurBucketSyrmorite").setTab(ModTabs.moarTConMisc).setMaxStackSize(1);
	public static final Item sulfurBucketIron = new ItemBase("sulfurBucketIron").setTab(ModTabs.moarTConMisc).setMaxStackSize(1);
	public static final Item betweenicCore = new ItemBase("betweenicCore").setTab(ModTabs.moarTConMisc);

	//Modifier items
	public static final Item betweenifiedModifier = new ItemBase("betweenifiedModifier").setTab(ModTabs.moarTConMisc);

	/*---------------------------------------*/

	public static void registerItems(Item... items) {
		for (Item i : items) {
			ModItems.addItem(i);
		}
	}

	public static void init() {
		ModItems.registerItems(
				new ItemCompositeRep(),
				new ItemDebugTool()
		);
		
		if (CompatManager.loadMain) {
			ModItems.registerItems(
					/**-------------------------------------**/
					ingotFusionite,
					nuggetFusionite,

					ingotIrradium,
					nuggetIrradium,

					ingotSolsteel,
					nuggetSolsteel,

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
					gemEtherstone,

					matterReconstructionGel,
					carbonPile,
					rawSteel,
					spaceTimeDisruptionPowder,
					hydrogenRichRedstonePowder

			//repItemEnderexamite,
			//repItemShadowglass,

			/**-------------------------------------**/

			);
			if (ConfigHandler.shouldLoadDust) {
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
						dustEbonite);
			}
		}
		if (CompatManager.twilightforest) {
			ModItems.registerItems(
					nuggetPenguinite,
					//repItemFerroherb,
					ingotPenguinite);
			if (ConfigHandler.shouldLoadDust && ConfigHandler.shouldLoadDustForCompatability) {
				ModItems.registerItems(
						dustPenguinite,
						dustIronwood,
						dustFiery,
						dustKnightmetal);
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
						dustSwampSteel);
			}

		}

		if (CompatManager.aether_legacy) {
			ModItems.registerItems(
					ingotArkenium,
					nuggetArkenium,
					ingotValkyrieMetal,
					nuggetValkyrieMetal);
			if (ConfigHandler.shouldLoadDust && ConfigHandler.shouldLoadDustForCompatability) {
				ModItems.registerItems(
						dustArkenium,
						dustValkyrieMetal);
			}

		}
		//Logging.log("Loaded a total of " + totalItems + " different items.");
	}

	private static void addItem(Item item) {
		RegisterHelper.registerItem(item);
		totalItems++;
	}
}
