package com.existingeevee.moretcon.inits;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.materials.CompositeRegistry;
import com.existingeevee.moretcon.materials.CompositeRegistry.CompositeData;
import com.existingeevee.moretcon.materials.MTMaterialIntegration;
import com.existingeevee.moretcon.materials.UniqueMaterial;
import com.existingeevee.moretcon.other.MoreTConLogger;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.MaterialUtils;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.other.utils.RegisterHelper;
import com.existingeevee.moretcon.traits.ModTraits;

import landmaster.plustic.tools.stats.BatteryCellMaterialStats;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.materials.ArrowShaftMaterialStats;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.BowStringMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.FletchingMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.materials.ProjectileMaterialStats;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerTraits;
import thebetweenlands.common.registries.BlockRegistry;
import twilightforest.compat.TConstruct;

public class ModMaterials implements MaterialTypes {

	public static int totalMaterials;

	public static final Material materialWeedwood = new Material(MiscUtils.createNonConflictiveName("weedwood"), 0xcc9900);
	public static final Material materialIronwood = new Material(MiscUtils.createNonConflictiveName("ironwood"), 0xcc9900);
	public static final Material materialFusionite = new Material(MiscUtils.createNonConflictiveName("fusionite"), 0x3399ff);
	public static final Material materialSyrmorite = new Material(MiscUtils.createNonConflictiveName("syrmorite"), 0x234187);
	public static final Material materialValonite = new Material(MiscUtils.createNonConflictiveName("valonite"), 0xcab1ca);
	public static final Material materialOctine = new Material(MiscUtils.createNonConflictiveName("octine"), 0xff8206);
	public static final Material materialEmberlight = new Material(MiscUtils.createNonConflictiveName("emberlight"), 0xe8926d);
	public static final Material materialReedRope = new Material(MiscUtils.createNonConflictiveName("reedrope"), 0x000b4e0b);
	public static final Material materialAnglerTooth = new Material(MiscUtils.createNonConflictiveName("anglertooth"), 0x00cfcf99);
	public static final Material materialDragonFlyWing = new Material(MiscUtils.createNonConflictiveName("dragonflywing"), 0x00bed6db);
	public static final Material materialSpaceTimeDisruption = new Material(MiscUtils.createNonConflictiveName("spacetimedisruption"), 0x400080);
	public static final Material materialIrradium = new Material(MiscUtils.createNonConflictiveName("irradium"), 0x00ed00);
	public static final Material materialSolsteel = new Material(MiscUtils.createNonConflictiveName("solarsteel"), 0xffad33);
	public static final Material materialPenguinite = new Material(MiscUtils.createNonConflictiveName("penguinite"), 0xe6e6ffff);
	public static final Material materialVoidSpar = new Material(MiscUtils.createNonConflictiveName("voidspar"), 0xb37e15ff);
	public static final Material materialGarstone = new Material(MiscUtils.createNonConflictiveName("garstone"), 0xff0a0a);
	public static final Material materialBloodstone = new Material(MiscUtils.createNonConflictiveName("bloodstone"), 0x660a0a);
	public static final Material materialGallium = new Material(MiscUtils.createNonConflictiveName("gallium"), 0xffffff);
	public static final Material materialSlimyBone = new Material(MiscUtils.createNonConflictiveName("slimybone"), 0xede6bf);
	public static final Material materialEnderal = new Material(MiscUtils.createNonConflictiveName("enderal"), 0xb32d0080);
	public static final Material materialEnderexamite = new Material(MiscUtils.createNonConflictiveName("enderexamite"), 0x8e3acf);
	public static final Material materialFerroherb = new Material(MiscUtils.createNonConflictiveName("ferroherb"), 0x4c5e3a);
	public static final Material materialRuneSteel = new Material(MiscUtils.createNonConflictiveName("runeSteel"), 0xb6905e);
	public static final Material materialArkenium = new Material(MiscUtils.createNonConflictiveName("arkenium"), 0x595959);
	public static final Material materialSkyroot = new Material(MiscUtils.createNonConflictiveName("skyroot"), 0x6c633e);
	public static final Material materialHolystone = new Material(MiscUtils.createNonConflictiveName("holystone"), 0xa3a3a3);
	public static final Material materialZanite = new Material(MiscUtils.createNonConflictiveName("zanite"), 0x6611dd);
	public static final Material materialGravitite = new Material(MiscUtils.createNonConflictiveName("gravitite"), 0xcc55aa);
	public static final Material materialValkyrieMetal = new Material(MiscUtils.createNonConflictiveName("valkyriemetal"), 0xeaee57);
	public static final Material materialAmberwood = new Material(MiscUtils.createNonConflictiveName("amberwood"), 0xeaee57);
	public static final Material materialGravitonium = new Material(MiscUtils.createNonConflictiveName("gravitonium"), 0x00aa00);
	public static final Material materialTrichromadentium = new Material(MiscUtils.createNonConflictiveName("trichromadentium"), 0x777777);
	public static final Material materialSwampSteel = new Material(MiscUtils.createNonConflictiveName("swampsteel"), 0x003906);
	public static final Material materialRotiron = new Material(MiscUtils.createNonConflictiveName("rotiron"), 0x002400);
	public static final Material materialEchostone = new Material(MiscUtils.createNonConflictiveName("echostone"), 0x00725a);
	public static final Material materialIgniglomerate = new Material(MiscUtils.createNonConflictiveName("igniglomerate"), 0xB33B00);
	public static final Material materialAtronium = new Material(MiscUtils.createNonConflictiveName("atronium"), 0xd99857);
	public static final Material materialEbonite = new Material(MiscUtils.createNonConflictiveName("ebonite"), 0x270339);
	public static final Material materialErythynite = new Material(MiscUtils.createNonConflictiveName("erythynite"), 0x00ff00);
	public static final Material materialShadowglass = new Material(MiscUtils.createNonConflictiveName("shadowglass"), 0x2a275e);
	public static final Material materialEtherstone = new Material(MiscUtils.createNonConflictiveName("etherstone"), 0xf1ea3b);
	public static final Material materialValasium = new Material(MiscUtils.createNonConflictiveName("valasium"), 0x9ab7d6);
	public static final Material materialElectarite = new Material(MiscUtils.createNonConflictiveName("electarite"), 0x6666FF);
	public static final Material materialHallowsite = new Material(MiscUtils.createNonConflictiveName("hallowsite"), 0x34eb98);

	public static final Material materialNahuatl = new Material(MiscUtils.createNonConflictiveName("nahuatl"), 0x3B2754);
	public static final Material materialSlimewood = new Material(MiscUtils.createNonConflictiveName("slimewood"), 0x96dd8f);

	//TODO
	// public static final Material materialQueensslime = new Material(Misc.createNonConflictiveName("queensslime"), 0x565808); //Need Custom
	// public static final Material materialHepatizon = new Material(Misc.createNonConflictiveName("hepatizon"), 0xf1ea3b);
	// public static final Material materialSlimesteel = new Material(Misc.createNonConflictiveName("slimesteel"), 0x47efea);
	// public static final Material materialSearedStone = new Material(Misc.createNonConflictiveName("searedstone"), 0x4f4a47);
	// public static final Material materialScorchedStone = new Material(Misc.createNonConflictiveName("scorchedstone"), 0x53453c);
	// public static final Material materialBloodbone = new Material(Misc.createNonConflictiveName("bloodbone"), 0xb80000);
	// public static final Material materialNecroticBone = new Material(Misc.createNonConflictiveName("necrobone"), 0x343434);
	// public static final Material materialBlazingBone = new Material(Misc.createNonConflictiveName("blazingbone"), 0xefc62f);

	public static final UniqueMaterial materialPlasma = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("plasma"), 0xff0000, "tconstruct:sword_blade",
			"tconstruct:broadsword");

	public static final UniqueMaterial materialShockwave = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("shockwave"), 0xa1ddd8,
			ConfigHandler.registerBetweenTinkerTools ? "moretcon:blswordblade" : "tconstruct:sword_blade",
			ConfigHandler.registerBetweenTinkerTools ? "moretcon:blsword" : "tconstruct:broadsword",
			() -> new ItemStack(BlockRegistry.ANIMATOR), "shockwave");

	public static final UniqueMaterial materialTriblade = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("triblade"), 0x6d6d6d, "tconstruct:knife_blade",
			"tconstruct:shuriken");

	public static final UniqueMaterial materialTrailblazer = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("trailblazer"), 0xff6c00, "tconstruct:knife_blade",
			"tconstruct:shuriken");

	public static final UniqueMaterial materialMirrored = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("mirrored"), 0xfafaff, "tconstruct:fletching",
			"tconstruct:arrow");

	public static final UniqueMaterial materialTechnoblade = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("technoblade"), 0xea8f8c, "tconstruct:sword_blade",
			"tconstruct:broadsword");

	public static final UniqueMaterial materialCrimson = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("crimson"), 0xaa0000, "tconstruct:tough_tool_rod",
			"plustic:katana");

	public static final UniqueMaterial materialEssencore = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("essencore"), 0x65c9ff, "tconstruct:pick_head",
			"tconstruct:pickaxe");

	public static final UniqueMaterial materialWormed = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("wormed"), 0xd99857, "tconstruct:arrow_head",
			"tconstruct:arrow",
			() -> new ItemStack(BlockRegistry.WEEDWOOD_WORKBENCH), "crafting_table");
	
	static {
	    BowMaterialStats whyWouldYouMakeABowOutOfThis = new BowMaterialStats(0.2f, 0.4f, -1f);

		if (CompatManager.tic3backport) {
			materialNahuatl.setCastable(false);
			materialNahuatl.setCraftable(false);
			materialNahuatl.addTrait(ModTraits.slicing, HEAD);
			materialNahuatl.addTrait(ModTraits.darkened, HEAD);
			materialNahuatl.addTrait(TinkerTraits.ecological, HEAD);
			materialNahuatl.addTrait(TinkerTraits.dense, HEAD);
			materialNahuatl.addTrait(TinkerTraits.ecological);
			materialNahuatl.addTrait(TinkerTraits.dense);
			materialNahuatl.addStats(new HeadMaterialStats(350, 4.5f, 3f, 2));
			materialNahuatl.addStats(new HandleMaterialStats(0.9f, 125));
			materialNahuatl.addStats(new ExtraMaterialStats(75));
			materialNahuatl.addStats(new BowMaterialStats(0.7f, 0.85f, 4f));
			materialNahuatl.addStats(new ArrowShaftMaterialStats(1.2f, 32));
			CompositeRegistry.registerComposite(new CompositeData(() -> TinkerMaterials.wood, () -> materialNahuatl, () -> TinkerFluids.obsidian, false).setMultiplier(2));

			materialSlimewood.setCastable(false);
			materialSlimewood.setCraftable(false);
			materialSlimewood.addTrait(ModTraits.overgrowth, HEAD);
			materialSlimewood.addTrait(ModTraits.overslime, HEAD);
			materialSlimewood.addTrait(TinkerTraits.ecological, HEAD);
			materialSlimewood.addTrait(TinkerTraits.crumbling, HEAD);
			materialSlimewood.addTrait(TinkerTraits.slimeyGreen);
			materialSlimewood.addTrait(ModTraits.overslime);
			materialSlimewood.addStats(new HeadMaterialStats(375, 4f, 1f, 1));
			materialSlimewood.addStats(new HandleMaterialStats(1.3f, 125));
			materialSlimewood.addStats(new ExtraMaterialStats(75));
			materialSlimewood.addStats(new BowMaterialStats(1f, 0.95f, 2f));
			materialSlimewood.addStats(new ArrowShaftMaterialStats(1.2f, 28));
			CompositeRegistry.registerComposite(() -> TinkerMaterials.wood, () -> materialSlimewood, () -> TinkerFluids.blueslime);

			//Overslime for the win!
			TinkerMaterials.slime.addTrait(ModTraits.overslime, HEAD);
			TinkerMaterials.blueslime.addTrait(ModTraits.overslime, HEAD);
			TinkerMaterials.knightslime.addTrait(ModTraits.overslime, HEAD);

			TinkerMaterials.knightslime.addTrait(ModTraits.overcast, HEAD);

			TinkerMaterials.slime.addTrait(ModTraits.overgrowth2, HEAD);
			TinkerMaterials.slime.addTrait(ModTraits.overgrowth);

			TinkerMaterials.blueslime.addTrait(ModTraits.overgrowth2, HEAD);
			TinkerMaterials.blueslime.addTrait(ModTraits.overgrowth);
		}

		if (CompatManager.plustic) {
			materialCrimson.addStats(new HandleMaterialStats(4f, 1024));
			materialCrimson.addTrait(ModTraits.bloodyArc);
			materialCrimson.addTrait(ModTraits.slicing);
		}

		if (CompatManager.jokes) {
			// Thank you for everything, old friend
			materialTechnoblade.addStats(new HeadMaterialStats(12288, 8f, 15f, 5));
			materialTechnoblade.addTrait(TinkerTraits.baconlicious);
			materialTechnoblade.addTrait(ModTraits.bloodGodsBlessing);
		}

		if (CompatManager.loadMain) {			
			materialFusionite.addItem("oreFusionite", 1, Material.VALUE_Ore());
			materialFusionite.setFluid(ModFluids.liquidFusionite);
			materialFusionite.addCommonItems("Fusionite");
			materialFusionite.setCastable(true);
			materialFusionite.setCraftable(false);
			materialFusionite.setRepresentativeItem("ingotFusionite");
			materialFusionite.addTrait(ModTraits.coldFire, HEAD);
			materialFusionite.addTrait(ModTraits.luminescent, HEAD);
			materialFusionite.addTrait(TinkerTraits.enderference, HEAD);
			materialFusionite.addTrait(TinkerTraits.alien);
			materialFusionite.addTrait(TinkerTraits.dense);
			materialFusionite.addTrait(ModTraits.luminescent);
			materialFusionite.addStats(new HeadMaterialStats(500, 5.0f, 8.0f, 6));
			materialFusionite.addStats(new HandleMaterialStats(3.0f, 20));
			materialFusionite.addStats(new ExtraMaterialStats(20));
			materialFusionite.addStats(new ArrowShaftMaterialStats(3.0f, 20));
			materialFusionite.addStats(new ProjectileMaterialStats());
			materialFusionite.addStats(new BowMaterialStats(1.5f, 1.0f, 7f));
			if (CompatManager.plustic) {
				materialFusionite.addStats(new LaserMediumMaterialStats(10, 20));
				materialFusionite.addStats(new BatteryCellMaterialStats(100000));
			}

			materialValasium.addItem("oreValasium", 1, Material.VALUE_Ore());
			materialValasium.setFluid(ModFluids.liquidValasium);
			materialValasium.addCommonItems("Valasium");
			materialValasium.setCastable(true);
			materialValasium.setCraftable(false);
			materialValasium.setRepresentativeItem("ingotValasium");
			materialValasium.addTrait(ModTraits.kineticBattery, HEAD);
			materialValasium.addTrait(ModTraits.shielding, HEAD);
			materialValasium.addTrait(TinkerTraits.alien, HEAD);
			materialValasium.addTrait(TinkerTraits.alien);
			materialValasium.addTrait(TinkerTraits.dense);
			if (CompatManager.plustic) {
				materialValasium.addStats(new LaserMediumMaterialStats(15, 20));
				materialValasium.addStats(new BatteryCellMaterialStats(2000));
			}
			materialValasium.addStats(new HeadMaterialStats(1600, 20f, 16f, 8));
			materialValasium.addStats(new HandleMaterialStats(4f, 60));
			materialValasium.addStats(new ExtraMaterialStats(500));
			materialValasium.addStats(new ArrowShaftMaterialStats(3f, 75));
			materialValasium.addStats(new BowMaterialStats(1.75f, 1.7f, 15f));

			materialIrradium.addItem("oreIrradium", 1, Material.VALUE_Ore());
			materialIrradium.setFluid(ModFluids.liquidIrradium);
			materialIrradium.addCommonItems("Irradium");
			materialIrradium.setCastable(true);
			materialIrradium.setCraftable(false);
			materialIrradium.setRepresentativeItem("ingotIrradium");
			materialIrradium.addTrait(ModTraits.radioactive, HANDLE);
			materialIrradium.addTrait(ModTraits.luminescent, HANDLE);
			materialIrradium.addTrait(ModTraits.radioactive, EXTRA);
			materialIrradium.addTrait(ModTraits.luminescent, EXTRA);
			materialIrradium.addTrait(TinkerTraits.momentum);
			materialIrradium.addTrait(ModTraits.luminescent);
			materialIrradium.addStats(new HeadMaterialStats(700, 6f, 4f, 5));
			if (CompatManager.plustic) {
				materialIrradium.addStats(new LaserMediumMaterialStats(8, 25));
				materialIrradium.addStats(new BatteryCellMaterialStats(480000));
			}
			materialIrradium.addStats(new HandleMaterialStats(3f, 40));
			materialIrradium.addStats(new ExtraMaterialStats(40));
			materialIrradium.addStats(new ArrowShaftMaterialStats(3f, 20));
			materialIrradium.addStats(new ProjectileMaterialStats());
			materialIrradium.addStats(new BowMaterialStats(1.25f, 1.25f, 3f));

			materialSolsteel.setFluid(ModFluids.liquidSolsteel);
			materialSolsteel.addCommonItems("Solarsteel");
			materialSolsteel.setCastable(true);
			materialSolsteel.setCraftable(false);
			materialSolsteel.setRepresentativeItem("ingotSolarsteel");
			materialSolsteel.addTrait(TinkerTraits.flammable, HEAD);
			materialSolsteel.addTrait(TinkerTraits.autosmelt, HEAD);
			materialSolsteel.addTrait(ModTraits.luminescent, HEAD);
			materialSolsteel.addTrait(TinkerTraits.superheat);
			materialSolsteel.addTrait(TinkerTraits.dense);
			materialSolsteel.addTrait(ModTraits.luminescent);
			if (CompatManager.plustic) {
				materialSolsteel.addStats(new LaserMediumMaterialStats(15, 20));
				materialSolsteel.addStats(new BatteryCellMaterialStats(750000));
			}
			materialSolsteel.addStats(new HeadMaterialStats(4500, 22f, 20f, 7));
			materialSolsteel.addStats(new HandleMaterialStats(4f, 600));
			materialSolsteel.addStats(new ExtraMaterialStats(600));
			materialSolsteel.addStats(new ArrowShaftMaterialStats(3f, 50));
			materialSolsteel.addStats(new BowMaterialStats(1.25f, 5.0f, 20f));

			materialTrichromadentium.addItem("oreTrichromadentium", 1, Material.VALUE_Ore());
			materialTrichromadentium.addCommonItems("Trichromadentium");
			materialTrichromadentium.setFluid(ModFluids.liquidTrichromadentium);
			materialTrichromadentium.setCastable(true);
			materialTrichromadentium.setCraftable(false);
			materialTrichromadentium.setRepresentativeItem("ingotTrichromadentium");
			materialTrichromadentium.addTrait(ModTraits.luminescent);
			materialTrichromadentium.addTrait(ModTraits.trichromic);
			materialTrichromadentium.addTrait(TinkerTraits.unnatural);
			materialTrichromadentium.addTrait(TinkerTraits.coldblooded);
			if (CompatManager.plustic) {
				materialTrichromadentium.addStats(new LaserMediumMaterialStats(15, 20));
				materialTrichromadentium.addStats(new BatteryCellMaterialStats(2000));
			}
			materialTrichromadentium.addStats(new HeadMaterialStats(1800, 20f, 18f, 8));
			materialTrichromadentium.addStats(new HandleMaterialStats(4f, 60));
			materialTrichromadentium.addStats(new ExtraMaterialStats(500));
			materialTrichromadentium.addStats(new ArrowShaftMaterialStats(3f, 75));
			materialTrichromadentium.addStats(new BowMaterialStats(1.2f, 2.0f, 17f));

			materialAtronium.addItem("oreAtronium", 1, Material.VALUE_Ore());
			materialAtronium.addCommonItems("Atronium");
			materialAtronium.setFluid(ModFluids.liquidAtronium);
			materialAtronium.setCastable(true);
			materialAtronium.setCraftable(false);
			materialAtronium.setRepresentativeItem("ingotAtronium");
			materialAtronium.addTrait(TinkerTraits.lightweight);
			materialAtronium.addTrait(ModTraits.magicallyReactive);
			materialAtronium.addTrait(ModTraits.penetrant);
			materialAtronium.addTrait(ModTraits.etheralHarvest, HEAD);
			materialAtronium.addTrait(TinkerTraits.lightweight, HEAD);
			materialAtronium.addTrait(ModTraits.magicallyReactive, HEAD);
			materialAtronium.addTrait(ModTraits.penetrant, HEAD);
			materialAtronium.addStats(new HeadMaterialStats(1500, 18f, 17f, 8));
			materialAtronium.addStats(new HandleMaterialStats(3f, 180));
			materialAtronium.addStats(new ExtraMaterialStats(750));
			materialAtronium.addStats(new ArrowShaftMaterialStats(2f, 120));
			materialAtronium.addStats(new BowMaterialStats(1f, 1.25f, 16f));

			materialEbonite.addItem("oreEbonite", 1, Material.VALUE_Ore());
			materialEbonite.setFluid(ModFluids.liquidEbonite);
			materialEbonite.addCommonItems("Ebonite");
			materialEbonite.setCastable(true);
			materialEbonite.setCraftable(false);
			materialEbonite.setRepresentativeItem("ingotEbonite");
			materialEbonite.addTrait(ModTraits.darkened, HEAD);
			materialEbonite.addTrait(ModTraits.luminescent, HEAD);
			materialEbonite.addTrait(TinkerTraits.poisonous, HEAD);
			materialEbonite.addTrait(ModTraits.bottomsEnd, HEAD);
			materialEbonite.addTrait(ModTraits.luminescent);
			materialEbonite.addTrait(TinkerTraits.dense);
			materialEbonite.addTrait(TinkerTraits.established);
			materialEbonite.addStats(new HeadMaterialStats(750, 6.0f, 9.0f, 7));
			materialEbonite.addStats(new HandleMaterialStats(2.5f, 60));
			materialEbonite.addStats(new ExtraMaterialStats(25));
			materialEbonite.addStats(new ArrowShaftMaterialStats(1.5f, 20));
			materialEbonite.addStats(new ProjectileMaterialStats());
			materialEbonite.addStats(new BowMaterialStats(1f, 1.25f, 7f));
			if (CompatManager.plustic) {
				materialEbonite.addStats(new LaserMediumMaterialStats(12, 20));
				materialEbonite.addStats(new BatteryCellMaterialStats(120000));
			}

			//TODO rework
			materialSpaceTimeDisruption.addItem(ModItems.spaceTimeDisruptionPowder, 1, Material.VALUE_Fragment);
			materialSpaceTimeDisruption.setCastable(false);
			materialSpaceTimeDisruption.setCraftable(true);
			materialSpaceTimeDisruption.setRepresentativeItem(ModItems.spaceTimeDisruptionPowder);
			materialSpaceTimeDisruption.addTrait(TinkerTraits.lightweight);
			materialSpaceTimeDisruption.addTrait(TinkerTraits.dense);
			materialSpaceTimeDisruption.addTrait(ModTraits.luminescent);
			if (CompatManager.plustic) {
				materialSpaceTimeDisruption.addStats(new LaserMediumMaterialStats(13, 25));
				materialSpaceTimeDisruption.addStats(new BatteryCellMaterialStats(520000));
			}
			materialSpaceTimeDisruption.addStats(new HeadMaterialStats(5, 10f, 8f, 6));
			materialSpaceTimeDisruption.addStats(new HandleMaterialStats(3f, 2));
			materialSpaceTimeDisruption.addStats(new ExtraMaterialStats(4));
			materialSpaceTimeDisruption.addStats(new BowMaterialStats(Float.POSITIVE_INFINITY, 3f, 8f)); //well see....
			materialSpaceTimeDisruption.addStats(new BowStringMaterialStats(3f));
			materialSpaceTimeDisruption.addStats(new ArrowShaftMaterialStats(3f, 64));
			materialSpaceTimeDisruption.addStats(new ProjectileMaterialStats());

			materialVoidSpar.addItem("gemVoidSpar", 1, Material.VALUE_Ingot);
			materialVoidSpar.addItem("blockVoidSpar", 1, Material.VALUE_Block);
			materialVoidSpar.setCastable(false);
			materialVoidSpar.setCraftable(true);
			materialVoidSpar.setRepresentativeItem("gemVoidSpar");
			materialVoidSpar.addTrait(ModTraits.voidic, HEAD);
			materialVoidSpar.addTrait(ModTraits.bottomsEnd, HEAD);
			materialVoidSpar.addTrait(ModTraits.luminescent, HEAD);
			materialVoidSpar.addTrait(ModTraits.luminescent);
			materialVoidSpar.addTrait(TinkerTraits.dense);
			materialVoidSpar.addTrait(TinkerTraits.ecological);
			materialVoidSpar.addStats(new HeadMaterialStats(300, 6f, 10f, 5));
			materialVoidSpar.addStats(new HandleMaterialStats(2f, -50));
			materialVoidSpar.addStats(new ExtraMaterialStats(2));
			materialVoidSpar.addStats(new ArrowShaftMaterialStats(1f, 10));
			materialVoidSpar.addStats(new ProjectileMaterialStats());
			materialVoidSpar.addStats(whyWouldYouMakeABowOutOfThis);

			materialGarstone.addItem("gemGarstone", 1, Material.VALUE_Ingot);
			materialGarstone.addItem("blockGarstone", 1, Material.VALUE_Block);
			materialGarstone.setCastable(false);
			materialGarstone.setCraftable(true);
			materialGarstone.setRepresentativeItem("gemGarstone");
			materialGarstone.addTrait(TinkerTraits.coldblooded, HEAD);
			materialGarstone.addTrait(ModTraits.luminescent, HEAD);
			materialGarstone.addTrait(TinkerTraits.jagged, HEAD);
			materialGarstone.addTrait(TinkerTraits.unnatural);
			materialGarstone.addTrait(ModTraits.luminescent);
			materialGarstone.addStats(new HeadMaterialStats(500, 6f, 10f, 5));
			materialGarstone.addStats(new HandleMaterialStats(2f, -50));
			materialGarstone.addStats(new ExtraMaterialStats(2));
			materialGarstone.addStats(new ArrowShaftMaterialStats(1f, 10));
			materialGarstone.addStats(new ProjectileMaterialStats());
			materialGarstone.addStats(whyWouldYouMakeABowOutOfThis);

			materialEchostone.addItem("gemEchostone", 1, Material.VALUE_Ingot);
			materialEchostone.addItem("blockEchostone", 1, Material.VALUE_Block);
			materialEchostone.setCastable(false);
			materialEchostone.setCraftable(true);
			materialEchostone.setRepresentativeItem("gemEchostone");
			materialEchostone.addTrait(TinkerTraits.unnatural);
			materialEchostone.addTrait(ModTraits.afterimage, HEAD);
			materialEchostone.addTrait(ModTraits.luminescent, HEAD);
			materialEchostone.addTrait(ModTraits.luminescent);
			materialEchostone.addStats(new HeadMaterialStats(500, 6f, 10f, 5));
			materialEchostone.addStats(new HandleMaterialStats(2f, -50));
			materialEchostone.addStats(new ExtraMaterialStats(2));
			materialEchostone.addStats(new ArrowShaftMaterialStats(1f, 10));
			materialEchostone.addStats(new ProjectileMaterialStats());
			materialEchostone.addStats(whyWouldYouMakeABowOutOfThis);

			materialBloodstone.addItem("gemBloodstone", 1, Material.VALUE_Ingot);
			materialBloodstone.addItem("blockBloodstone", 1, Material.VALUE_Block);
			materialBloodstone.setCastable(false);
			materialBloodstone.setCraftable(true);
			materialBloodstone.setRepresentativeItem("gemBloodstone");
			materialBloodstone.addTrait(TinkerTraits.coldblooded, HEAD);
			materialBloodstone.addTrait(ModTraits.bottomsEnd, HEAD);
			materialBloodstone.addTrait(ModTraits.slicing, HEAD);
			materialBloodstone.addTrait(ModTraits.leeching);
			materialBloodstone.addStats(new HeadMaterialStats(750, 7f, 12f, 6));
			materialBloodstone.addStats(new HandleMaterialStats(1.125f, -35));
			materialBloodstone.addStats(new ExtraMaterialStats(75));
			materialBloodstone.addStats(new ArrowShaftMaterialStats(2.35f, 80));
			materialBloodstone.addStats(new ProjectileMaterialStats());
			materialBloodstone.addStats(whyWouldYouMakeABowOutOfThis);

			materialErythynite.addItem("gemErythynite", 1, Material.VALUE_Ingot);
			materialErythynite.addItem("blockErythynite", 1, Material.VALUE_Block);
			materialErythynite.setCastable(false);
			materialErythynite.setCraftable(true);
			materialErythynite.setRepresentativeItem("gemErythynite");
			materialErythynite.addTrait(TinkerTraits.unnatural, HEAD);
			materialErythynite.addTrait(ModTraits.hardcore, HEAD);
			materialErythynite.addTrait(ModTraits.luminescent, HEAD);
			materialErythynite.addTrait(ModTraits.leeching);
			materialErythynite.addTrait(ModTraits.weightless);
			materialErythynite.addTrait(TinkerTraits.enderference);
			materialErythynite.addTrait(ModTraits.luminescent);
			materialErythynite.addStats(new HeadMaterialStats(600, 7.5f, 10f, 7));
			materialErythynite.addStats(new HandleMaterialStats(2.2f, 60));
			materialErythynite.addStats(new ExtraMaterialStats(30));
			materialErythynite.addStats(new ArrowShaftMaterialStats(1.5f, 20));
			materialErythynite.addStats(new ProjectileMaterialStats());
			if (CompatManager.plustic) {
				materialErythynite.addStats(new LaserMediumMaterialStats(12, 20));
			}
			materialErythynite.addStats(whyWouldYouMakeABowOutOfThis);

			materialGravitonium.addItem("oreGravitonium", 1, Material.VALUE_Ore());
			materialGravitonium.addItem("oreGravitoniumDense", 1, Material.VALUE_Ore() * 4);
			materialGravitonium.setFluid(ModFluids.liquidGravitonium);
			materialGravitonium.addCommonItems("Gravitonium");
			materialGravitonium.setCastable(true);
			materialGravitonium.setCraftable(false);
			materialGravitonium.setRepresentativeItem("ingotGravitonium");
			materialGravitonium.addTrait(ModTraits.hypergravity);
			materialGravitonium.addTrait(ModTraits.luminescent);
			materialGravitonium.addStats(new HeadMaterialStats(700, 16f, 12f, 5));
			if (CompatManager.plustic) {
				materialGravitonium.addStats(new LaserMediumMaterialStats(12, 25));
				materialGravitonium.addStats(new BatteryCellMaterialStats(480000));
			}
			materialGravitonium.addStats(new HandleMaterialStats(3f, 400));
			materialGravitonium.addStats(new ExtraMaterialStats(300));
			materialGravitonium.addStats(new ArrowShaftMaterialStats(3f, 50));
			materialGravitonium.addStats(new ProjectileMaterialStats());
			materialGravitonium.addStats(new BowMaterialStats(1.3f, 0.9f, 12f));

			materialGallium.addCommonItems("Gallium");
			materialGallium.setFluid(ModFluids.liquidGallium);
			materialGallium.setCastable(true);
			materialGallium.setCraftable(true);
			materialGallium.setRepresentativeItem("ingotGallium");
			materialGallium.addTrait(TinkerTraits.aquadynamic);
			materialGallium.addTrait(ModTraits.liquid);
			materialGallium.addTrait(TinkerTraits.crumbling);
			materialGallium.addStats(new HeadMaterialStats(100, 6f, 10f, 5));
			materialGallium.addStats(new HandleMaterialStats(2f, -50));
			materialGallium.addStats(new ExtraMaterialStats(2));
			materialGallium.addStats(new ArrowShaftMaterialStats(1f, 10));
			materialGallium.addStats(new ProjectileMaterialStats());
			materialGallium.addStats(whyWouldYouMakeABowOutOfThis);

			materialRuneSteel.addCommonItems("RuneSteel");
			materialRuneSteel.setFluid(ModFluids.liquidRuneSteel);
			materialRuneSteel.setCastable(true);
			materialRuneSteel.setCraftable(false);
			materialRuneSteel.addTrait(ModTraits.magical);
			materialRuneSteel.addTrait(ModTraits.shielding);
			materialRuneSteel.setRepresentativeItem("ingotRunesteel");
			materialRuneSteel.addStats(new HeadMaterialStats(900, 18f, 14f, 7));
			materialRuneSteel.addStats(new HandleMaterialStats(4f, 60));
			materialRuneSteel.addStats(new ExtraMaterialStats(60));
			materialRuneSteel.addStats(new ArrowShaftMaterialStats(3f, 20));
			materialRuneSteel.addStats(new ProjectileMaterialStats());
			materialRuneSteel.addStats(new BowMaterialStats(0.75f, 2.5f, 12f));

			materialEnderal.addItem("gemEnderal", 1, Material.VALUE_Ingot);
			materialEnderal.addItem("blockEnderal", 1, Material.VALUE_Block);
			materialEnderal.setCastable(false);
			materialEnderal.setCraftable(true);
			materialEnderal.setRepresentativeItem("gemEnderal");
			materialEnderal.addTrait(TinkerTraits.enderference);
			materialEnderal.addTrait(TinkerTraits.endspeed, PROJECTILE);
			materialEnderal.addTrait(TinkerTraits.enderference, HEAD);
			materialEnderal.addTrait(TinkerTraits.jagged, HEAD);
			materialEnderal.addTrait(ModTraits.pulsating, HEAD);
			materialEnderal.addTrait(ModTraits.luminescent, HEAD);
			materialEnderal.addTrait(ModTraits.luminescent);
			materialEnderal.addStats(new HeadMaterialStats(390, 6f, 10f, 5));
			materialEnderal.addStats(new HandleMaterialStats(2f, -50));
			materialEnderal.addStats(new ExtraMaterialStats(2));
			materialEnderal.addStats(new ArrowShaftMaterialStats(1f, 10));
			materialEnderal.addStats(new ProjectileMaterialStats());
			materialEnderal.addStats(whyWouldYouMakeABowOutOfThis);

			materialEnderexamite.setCastable(false);
			materialEnderexamite.setCraftable(false);
			materialEnderexamite.addTrait(TinkerTraits.slimeyBlue, HEAD);
			materialEnderexamite.addTrait(ModTraits.pulsating, HEAD);
			materialEnderexamite.addTrait(ModTraits.shielding, HEAD);
			materialEnderexamite.addTrait(TinkerTraits.crumbling);
			materialEnderexamite.addTrait(TinkerTraits.endspeed, PROJECTILE);
			materialEnderexamite.addTrait(ModTraits.resilient);
			materialEnderexamite.addStats(new HeadMaterialStats(1300, 7f, 11f, 5));
			materialEnderexamite.addStats(new HandleMaterialStats(2.25f, 20));
			materialEnderexamite.addStats(new ExtraMaterialStats(12));
			materialEnderexamite.addStats(new ArrowShaftMaterialStats(1f, 15));
			materialEnderexamite.addStats(new ProjectileMaterialStats());
			materialEnderexamite.addStats(new BowMaterialStats(0.75f, 2.5f, 12f));
			CompositeRegistry.registerComposite(() -> materialEnderal, () -> materialEnderexamite, () -> TinkerFluids.knightslime);

			materialShadowglass.setCastable(false);
			materialShadowglass.setCraftable(false);
			materialShadowglass.addTrait(TinkerTraits.jagged, HEAD);
			materialShadowglass.addTrait(ModTraits.darkened, HEAD);
			materialShadowglass.addTrait(TinkerTraits.unnatural);
			materialShadowglass.addTrait(ModTraits.hardcore);
			materialShadowglass.addStats(new HeadMaterialStats(1500, 8f, 12f, 7));
			materialShadowglass.addStats(new HandleMaterialStats(2.3f, 20));
			materialShadowglass.addStats(new ExtraMaterialStats(15));
			materialShadowglass.addStats(new ArrowShaftMaterialStats(1.2f, 18));
			materialShadowglass.addStats(new ProjectileMaterialStats());
			materialShadowglass.addStats(new BowMaterialStats(2f, 0.9f, 10f));
			CompositeRegistry.registerComposite(() -> materialErythynite, () -> materialShadowglass, () -> ModFluids.liquidEbonite);

			materialPlasma.addStats(new HeadMaterialStats(4096, 6f, 12f, 5));
			materialPlasma.addTrait(ModTraits.plasmatic);
			materialPlasma.addTrait(ModTraits.luminescent);

			materialMirrored.addStats(new FletchingMaterialStats(0.95f, 3f));
			materialMirrored.addTrait(ModTraits.mirroring);

			materialTriblade.addStats(new ProjectileMaterialStats());
			materialTriblade.addStats(new HeadMaterialStats(2056, 3f, 8f, 5));
			materialTriblade.addStats(new ExtraMaterialStats(128));
			materialTriblade.addTrait(ModTraits.tripleshot);

			materialTrailblazer.addStats(new ProjectileMaterialStats());
			materialTrailblazer.addStats(new HeadMaterialStats(2056 * 2, 6f, 4f, 7));
			materialTrailblazer.addStats(new ExtraMaterialStats(512 * 2));
			materialTrailblazer.addTrait(ModTraits.aerialFlames);
			materialTrailblazer.addTrait(ModTraits.luminescent);

			materialIgniglomerate.addItem("gemIgniglomerate", 1, Material.VALUE_Ingot);
			materialIgniglomerate.addItem("blockIgniglomerate", 1, Material.VALUE_Block);
			materialIgniglomerate.setCastable(false);
			materialIgniglomerate.setCraftable(true);
			materialIgniglomerate.setRepresentativeItem("gemIgniglomerate");
			materialIgniglomerate.addTrait(ModTraits.hyperheat, HEAD);
			materialIgniglomerate.addTrait(ModTraits.luminescent, HEAD);
			materialIgniglomerate.addTrait(TinkerTraits.autosmelt, HANDLE);
			materialIgniglomerate.addTrait(ModTraits.luminescent, HANDLE);
			materialIgniglomerate.addTrait(TinkerTraits.autosmelt, EXTRA);
			materialIgniglomerate.addTrait(ModTraits.luminescent, EXTRA);
			materialIgniglomerate.addTrait(ModTraits.luminescent);
			materialIgniglomerate.addStats(new HeadMaterialStats(700, 6f, 7f, 7));
			materialIgniglomerate.addStats(new HandleMaterialStats(1.25f, -10));
			materialIgniglomerate.addStats(new ExtraMaterialStats(250));
			materialIgniglomerate.addStats(new ArrowShaftMaterialStats(1f, 10));
			materialIgniglomerate.addStats(new ProjectileMaterialStats());
			materialIgniglomerate.addStats(whyWouldYouMakeABowOutOfThis);

			materialEtherstone.addItem("gemEtherstone", 1, Material.VALUE_Ingot);
			materialEtherstone.addItem("blockEtherstone", 1, Material.VALUE_Block);
			materialEtherstone.setCastable(false);
			materialEtherstone.setCraftable(true);
			materialEtherstone.setRepresentativeItem("gemEtherstone");
			materialEtherstone.addTrait(ModTraits.etheralHarvest, HEAD);
			materialEtherstone.addTrait(ModTraits.darkened, HEAD);
			materialEtherstone.addTrait(ModTraits.overdrive);
			materialEtherstone.addTrait(ModTraits.hardcore);
			materialEtherstone.addTrait(ModTraits.voidic);
			materialEtherstone.addTrait(ModTraits.luminescent, HEAD);
			materialEtherstone.addTrait(ModTraits.luminescent);
			materialEtherstone.addStats(new HeadMaterialStats(1450, 17.5f, 18f, 8));
			materialEtherstone.addStats(new HandleMaterialStats(4f, -50));
			materialEtherstone.addStats(new ExtraMaterialStats(500));
			materialEtherstone.addStats(new ProjectileMaterialStats());
			materialEtherstone.addStats(whyWouldYouMakeABowOutOfThis);

			materialEssencore.addStats(new HeadMaterialStats(4096, 10f, 12f, 7));
			materialEssencore.addTrait(ModTraits.essentialObliteration);
			materialEssencore.addTrait(ModTraits.luminescent);
			
			materialElectarite.addItem("gemElectarite", 1, Material.VALUE_Ingot);
			materialElectarite.addItem("blockElectarite", 1, Material.VALUE_Block);
			materialElectarite.setCastable(false);
			materialElectarite.setCraftable(true);
			materialElectarite.setRepresentativeItem("gemElectarite");
			materialElectarite.addTrait(ModTraits.luminescent, HEAD);
			materialElectarite.addTrait(ModTraits.electrified, HEAD);
			materialElectarite.addTrait(TinkerTraits.enderference, HEAD);
			materialElectarite.addTrait(TinkerTraits.enderference);
			materialElectarite.addTrait(TinkerTraits.unnatural);
			materialElectarite.addTrait(ModTraits.luminescent);
			materialElectarite.addStats(new HeadMaterialStats(620, 6f, 7f, 5));
			materialElectarite.addStats(new HandleMaterialStats(2.2f, 10));
			materialElectarite.addStats(new ExtraMaterialStats(70));
			materialElectarite.addStats(new ArrowShaftMaterialStats(1.2f, 10));
			materialElectarite.addStats(new ProjectileMaterialStats());
			materialElectarite.addStats(whyWouldYouMakeABowOutOfThis);

			materialHallowsite.addCommonItems("Hallowsite");
			materialHallowsite.setFluid(ModFluids.liquidHallowsite);
			materialHallowsite.setCastable(true);
			materialHallowsite.setCraftable(false);
			materialHallowsite.setRepresentativeItem("ingotHallowsite");
			materialHallowsite.addTrait(ModTraits.haunted, HEAD);
			materialHallowsite.addTrait(ModTraits.soulforged2, HEAD);
			materialHallowsite.addTrait(ModTraits.soulforged);
			materialHallowsite.addStats(new HeadMaterialStats(620, 6f, 7f, 5));
			materialHallowsite.addStats(new HandleMaterialStats(2.2f, 10));
			materialHallowsite.addStats(new ExtraMaterialStats(70));
			materialHallowsite.addStats(new ArrowShaftMaterialStats(1.2f, 10));
			materialHallowsite.addStats(new ProjectileMaterialStats());
			materialHallowsite.addStats(new BowMaterialStats(1.1f, 1.4f, 8f));

		}
		if (CompatManager.aether_legacy) { // TODO
			materialZanite.addItem("gemZanite", 1, Material.VALUE_Ingot);
			materialZanite.addItem("blockZanite", 1, Material.VALUE_Block);
			materialZanite.setCastable(false);
			materialZanite.setCraftable(true);
			materialZanite.setRepresentativeItem("gemZanite");
			materialZanite.addTrait(ModTraits.aetheric);
			materialZanite.addTrait(ModTraits.hardcore);
			materialZanite.addTrait(ModTraits.aetheric, HEAD);
			materialZanite.addTrait(ModTraits.treetap, HEAD);
			materialZanite.addTrait(TinkerTraits.jagged, HEAD);
			materialZanite.addTrait(ModTraits.hardcore, HEAD);
			materialZanite.addStats(new HeadMaterialStats(210, 2.00f, 4.00f, 2));
			materialZanite.addStats(new HandleMaterialStats(0.9f, 65));
			materialZanite.addStats(new ExtraMaterialStats(50));
			materialZanite.addStats(whyWouldYouMakeABowOutOfThis);

			materialSkyroot.addItem("plankWoodSkyroot", 1, Material.VALUE_Ingot);
			materialSkyroot.addItem("logWoodSkyroot", 1, Material.VALUE_Ingot * 4);
			materialSkyroot.addItem("stickWoodSkyroot", 1, Material.VALUE_Ingot / 2);
			materialSkyroot.setCastable(false);
			materialSkyroot.setCraftable(true);
			materialSkyroot.setRepresentativeItem("plankWoodSkyroot");
			materialSkyroot.addTrait(ModTraits.aetheric);
			materialSkyroot.addTrait(ModTraits.rootplicating, HEAD);
			materialSkyroot.addTrait(TinkerTraits.ecological, HEAD);
			materialSkyroot.addTrait(ModTraits.aetheric, HEAD);
			materialSkyroot.addStats(new HeadMaterialStats(40, 2f, 2f, 0));
			materialSkyroot.addStats(new HandleMaterialStats(1.2f, 25));
			materialSkyroot.addStats(new ExtraMaterialStats(15));
			materialSkyroot.addStats(new BowMaterialStats(0.6f, 1.1f, 2f));
			materialSkyroot.addStats(new ArrowShaftMaterialStats(1.2f, 16));

			materialAmberwood.setCastable(false);
			materialAmberwood.setCraftable(false);
			materialAmberwood.addTrait(ModTraits.aetheric);
			materialAmberwood.addTrait(ModTraits.rootplicating, HEAD);
			materialAmberwood.addTrait(ModTraits.treetap, HEAD);
			materialAmberwood.addTrait(TinkerTraits.established, HEAD);
			materialAmberwood.addTrait(TinkerTraits.ecological);
			materialAmberwood.addTrait(TinkerTraits.writable);
			materialAmberwood.addStats(new HeadMaterialStats(170, 4f, 4f, 2));
			materialAmberwood.addStats(new HandleMaterialStats(1.2f, 125));
			materialAmberwood.addStats(new ExtraMaterialStats(75));
			materialAmberwood.addStats(new BowMaterialStats(0.7f, 1.6f, 4f));
			materialAmberwood.addStats(new ArrowShaftMaterialStats(1.2f, 32));
			CompositeRegistry.registerComposite(() -> materialSkyroot, () -> materialAmberwood, () -> ModFluids.liquidGoldenAmber);

			materialArkenium.addCommonItems("Arkenium");
			materialArkenium.setFluid(ModFluids.liquidArkenium);
			materialArkenium.setCastable(true);
			materialArkenium.setCraftable(false);
			materialArkenium.addTrait(TinkerTraits.lightweight);
			materialArkenium.addTrait(TinkerTraits.holy, HEAD);
			materialArkenium.addTrait(TinkerTraits.sharp, HEAD);
			materialArkenium.addTrait(ModTraits.aetheric, HEAD);
			materialArkenium.addTrait(ModTraits.aetheric);
			materialArkenium.setRepresentativeItem("ingotArkenium");
			materialArkenium.addStats(new HeadMaterialStats(1250, 10.0f, 5.25f, 4));
			materialArkenium.addStats(new HandleMaterialStats(1.1f, 100));
			materialArkenium.addStats(new ExtraMaterialStats(125));
			materialArkenium.addStats(new ArrowShaftMaterialStats(3.5f, 25));
			materialArkenium.addStats(new ProjectileMaterialStats());
			materialArkenium.addStats(new BowMaterialStats(0.5f, 2.75f, 5f));

			materialValkyrieMetal.addCommonItems("ValkyrieMetal");
			materialValkyrieMetal.setFluid(ModFluids.liquidValkyrieMetal);
			materialValkyrieMetal.setCastable(true);
			materialValkyrieMetal.setCraftable(false);
			materialValkyrieMetal.addTrait(ModTraits.reaching, HEAD);
			materialValkyrieMetal.addTrait(ModTraits.treetap, HEAD);
			materialValkyrieMetal.addTrait(ModTraits.aetheric);
			materialValkyrieMetal.setRepresentativeItem("ingotValkyrieMetal");
			materialValkyrieMetal.addStats(new HeadMaterialStats(1250, 10.0f, 5.25f, 4));
			materialValkyrieMetal.addStats(new HandleMaterialStats(1.1f, 100));
			materialValkyrieMetal.addStats(new ExtraMaterialStats(125));
			materialValkyrieMetal.addStats(new ArrowShaftMaterialStats(3.5f, 25));
			materialValkyrieMetal.addStats(new ProjectileMaterialStats());
			materialValkyrieMetal.addStats(new BowMaterialStats(0.5f, 2.75f, 5f));

			materialHolystone.addItem("holystone", 1, Material.VALUE_Ingot);
			materialHolystone.setCastable(false);
			materialHolystone.setCraftable(true);
			materialHolystone.setRepresentativeItem("holystone");
			materialHolystone.addTrait(TinkerTraits.cheapskate, HEAD);
			materialHolystone.addTrait(ModTraits.aetheric, HEAD);
			materialHolystone.addTrait(ModTraits.blessed, HEAD);
			materialHolystone.addTrait(ModTraits.aetheric);
			materialHolystone.addTrait(TinkerTraits.cheapskate);
			materialHolystone.addStats(whyWouldYouMakeABowOutOfThis);
			materialHolystone.addStats(new HeadMaterialStats(130, 3f, 3f, 1));
			materialHolystone.addStats(new HandleMaterialStats(0.25f, -50));
			materialHolystone.addStats(new ExtraMaterialStats(30));

			materialGravitite.addItem("blockGravitite", 1, Material.VALUE_Block);
			materialGravitite.addItem("gemGravitite", 1, Material.VALUE_Ingot);
			materialGravitite.setCastable(false);
			materialGravitite.setCraftable(true);
			materialGravitite.setRepresentativeItem("gemGravitite");
			materialGravitite.addTrait(TinkerTraits.insatiable);
			materialGravitite.addTrait(ModTraits.aetheric, HEAD);
			materialGravitite.addTrait(ModTraits.aetheric);
			materialGravitite.addTrait(ModTraits.antigravity, HEAD);
			materialGravitite.addTrait(ModTraits.treetap, HEAD);
			materialGravitite.addStats(new HeadMaterialStats(950, 7.50f, 5.00f, 3));
			materialGravitite.addStats(new HandleMaterialStats(0.9f, 90));
			materialGravitite.addStats(new ExtraMaterialStats(90));
			materialGravitite.addStats(whyWouldYouMakeABowOutOfThis);
			
		}
		if (CompatManager.twilightforest) {
			materialIronwood.addCommonItems("Ironwood");
			materialIronwood.setCastable(true);
			materialIronwood.setFluid(ModFluids.liquidIronwood);
			materialIronwood.setCraftable(false);
			materialIronwood.setRepresentativeItem("ingotIronwood");
			materialIronwood.addTrait(TConstruct.twilit);
			materialIronwood.addTrait(ModTraits.shielding);
			materialIronwood.addTrait(TinkerTraits.ecological);
			materialIronwood.addStats(new HeadMaterialStats(100, 10f, 5f, 4));
			materialIronwood.addStats(new HandleMaterialStats(1f, 25));
			materialIronwood.addStats(new ExtraMaterialStats(25));
			materialIronwood.addStats(new ArrowShaftMaterialStats(1f, 1));
			materialIronwood.addStats(new ProjectileMaterialStats());
			materialIronwood.addStats(new BowMaterialStats(1f, 2.75f, 7f));

			materialFerroherb.setCastable(false);
			materialFerroherb.setCraftable(false);
			materialFerroherb.addTrait(TConstruct.synergy);
			materialFerroherb.addTrait(TConstruct.twilit);
			materialFerroherb.addTrait(TinkerTraits.ecological);
			materialFerroherb.addTrait(TConstruct.twilit, HEAD);
			materialFerroherb.addTrait(TinkerTraits.ecological, HEAD);
			materialFerroherb.addTrait(TinkerTraits.sharp, HEAD);
			materialFerroherb.addTrait(TinkerTraits.splintering, HEAD);
			materialFerroherb.addStats(new HeadMaterialStats(200, 10f, 7f, 4));
			materialFerroherb.addStats(new HandleMaterialStats(1.125f, 100));
			materialFerroherb.addStats(new ExtraMaterialStats(125));
			materialFerroherb.addStats(new ArrowShaftMaterialStats(1f, 25));
			materialFerroherb.addStats(new ProjectileMaterialStats());
			CompositeRegistry.registerComposite(() -> TConstruct.steeleaf, () -> materialFerroherb, () -> ModFluids.liquidIronwood);
			materialIronwood.addStats(new BowMaterialStats(1.2f, 2.75f, 8f));

			materialPenguinite.addCommonItems("Penguinite");
			materialPenguinite.setCastable(true);
			materialPenguinite.setFluid(ModFluids.liquidPenguinite);
			materialPenguinite.setCraftable(false);
			materialPenguinite.setRepresentativeItem("ingotPenguinite");
			materialPenguinite.addTrait(TConstruct.twilit);
			materialPenguinite.addTrait(TinkerTraits.coldblooded);
			materialPenguinite.addTrait(TinkerTraits.freezing);
			materialPenguinite.addStats(new HeadMaterialStats(100, 10f, 7f, 4));
			materialPenguinite.addStats(new HandleMaterialStats(1f, 25));
			materialPenguinite.addStats(new ExtraMaterialStats(25));
			materialPenguinite.addStats(new ArrowShaftMaterialStats(1f, 1));
			materialPenguinite.addStats(new ProjectileMaterialStats());
			materialIronwood.addStats(new BowMaterialStats(0.7f, 1f, 6f));
		}

		if (CompatManager.thebetweenlands) {

			materialSyrmorite.addItem("oreSyrmorite", 1, Material.VALUE_Ore());
			materialSyrmorite.setFluid(ModFluids.liquidSyrmorite);
			materialSyrmorite.addCommonItems("Syrmorite");
			materialSyrmorite.setCastable(true);
			materialSyrmorite.setCraftable(false);
			materialSyrmorite.setRepresentativeItem("ingotSyrmorite");
			materialSyrmorite.addTrait(TinkerTraits.coldblooded);
			materialSyrmorite.addTrait(TinkerTraits.magnetic);
			materialSyrmorite.addStats(new HeadMaterialStats(800, 5f, 4f, 2));
			materialSyrmorite.addStats(new HandleMaterialStats(1.125f, -50));
			materialSyrmorite.addStats(new ExtraMaterialStats(-25));
			materialSyrmorite.addStats(new ProjectileMaterialStats());
			materialSyrmorite.addStats(new BowMaterialStats(0.7f, 1f, 3f));

			materialRotiron.setFluid(ModFluids.liquidRotiron);
			materialRotiron.addCommonItems("Rotiron");
			materialRotiron.setCastable(true);
			materialRotiron.setCraftable(false);
			materialRotiron.setRepresentativeItem("ingotRotiron");
			materialRotiron.addTrait(ModTraits.oxide, HEAD);
			materialRotiron.addTrait(ModTraits.rotten, HEAD);
			materialRotiron.addTrait(TinkerTraits.dense);
			materialRotiron.addTrait(TinkerTraits.magnetic);
			materialRotiron.addStats(new HeadMaterialStats(850, 5.25f, 5f, 2));
			materialRotiron.addStats(new HandleMaterialStats(1.125f, -35));
			materialRotiron.addStats(new ExtraMaterialStats(-20));
			materialRotiron.addStats(new ProjectileMaterialStats());
			materialRotiron.addStats(new BowMaterialStats(0.7f, 1f, 4f));

			materialSwampSteel.setFluid(ModFluids.liquidSwampSteel);
			materialSwampSteel.addCommonItems("Swampsteel");
			materialSwampSteel.setCastable(true);
			materialSwampSteel.setCraftable(false);
			materialSwampSteel.setRepresentativeItem("ingotSwampsteel");
			materialSwampSteel.addTrait(ModTraits.corroding, HEAD);
			materialSwampSteel.addTrait(TinkerTraits.dense, HEAD);
			materialSwampSteel.addTrait(TinkerTraits.established);
			materialSwampSteel.addTrait(TinkerTraits.heavy);
			materialSwampSteel.addStats(new HeadMaterialStats(1200, 5.25f, 4.5f, 3));
			materialSwampSteel.addStats(new HandleMaterialStats(1.5f, -25));
			materialSwampSteel.addStats(new ExtraMaterialStats(-12));
			materialSwampSteel.addStats(new ProjectileMaterialStats());
			materialSwampSteel.addStats(new BowMaterialStats(0.7f, 1f, 4f));

			materialOctine.addItem("oreOctine", 1, Material.VALUE_Ore());
			materialOctine.setFluid(ModFluids.liquidOctine);
			materialOctine.addCommonItems("Octine");
			materialOctine.setCastable(true);
			materialOctine.setCraftable(false);
			materialOctine.setRepresentativeItem("ingotOctine");
			materialOctine.addTrait(TinkerTraits.superheat);
			materialOctine.addTrait(ModTraits.burning);
			materialOctine.addStats(new HeadMaterialStats(900, 5f, 4f, 2));
			materialOctine.addStats(new HandleMaterialStats(1.25f, -50));
			materialOctine.addStats(new ExtraMaterialStats(-25));
			materialOctine.addStats(new ProjectileMaterialStats());
			materialOctine.addStats(new BowMaterialStats(0.7f, 1f, 3f));

			materialValonite.addItem("gemValonite", 1, Material.VALUE_Ingot);
			materialValonite.addItem("blockValonite", 1, Material.VALUE_Block);
			materialValonite.addItem("nuggetValonite", 1, Material.VALUE_Nugget);
			materialValonite.setCastable(false);
			materialValonite.setCraftable(true);
			materialValonite.setRepresentativeItem("gemValonite");
			materialValonite.addTrait(TinkerTraits.dense);
			materialValonite.addTrait(ModTraits.shielding);
			materialValonite.addTrait(TinkerTraits.sharp);
			materialValonite.addStats(new HeadMaterialStats(975, 6f, 6.125f, 3));
			materialValonite.addStats(new HandleMaterialStats(1f, -75));
			materialValonite.addStats(new ExtraMaterialStats(-25));
			materialValonite.addStats(new ProjectileMaterialStats());
			materialValonite.addStats(whyWouldYouMakeABowOutOfThis);

			materialEmberlight.setCastable(false);
			materialEmberlight.setCraftable(false);
			materialEmberlight.addTrait(TinkerTraits.dense);
			materialEmberlight.addTrait(TinkerTraits.sharp);
			materialEmberlight.addTrait(ModTraits.hardcore);
			materialEmberlight.addTrait(ModTraits.burning);
			materialEmberlight.addTrait(ModTraits.resilient);
			materialEmberlight.addStats(new HeadMaterialStats(1200, 7.0f, 10.0f, 4));
			materialEmberlight.addStats(new HandleMaterialStats(2.0f, 20));
			materialEmberlight.addStats(new ExtraMaterialStats(12));
			materialEmberlight.addStats(new ArrowShaftMaterialStats(1.0f, 15));
			materialEmberlight.addStats(new ProjectileMaterialStats());
			CompositeRegistry.registerComposite(() -> materialValonite, () -> materialEmberlight, () -> ModFluids.liquidEmber);
			materialEmberlight.addStats(whyWouldYouMakeABowOutOfThis);

			materialSlimyBone.addItem("gemSlimyBone", 1, Material.VALUE_Ingot);
			materialSlimyBone.addItem("blockSlimyBone", 1, Material.VALUE_Block);
			materialSlimyBone.setCastable(false);
			materialSlimyBone.setCraftable(true);
			materialSlimyBone.setRepresentativeItem("gemSlimyBone");
			materialSlimyBone.addTrait(TinkerTraits.splintering);
			materialSlimyBone.addTrait(TinkerTraits.cheap);
			materialSlimyBone.addTrait(TinkerTraits.cheapskate);
			materialSlimyBone.addTrait(TinkerTraits.crude);
			materialSlimyBone.addStats(new HeadMaterialStats(275, 3f, 3f, 1));
			materialSlimyBone.addStats(new HandleMaterialStats(1.1f, 25));
			materialSlimyBone.addStats(new ExtraMaterialStats(5));
			materialSlimyBone.addStats(whyWouldYouMakeABowOutOfThis);

			materialReedRope.addItem("ropeReed", 1, Material.VALUE_Ingot);
			materialReedRope.setCastable(false);
			materialReedRope.setCraftable(true);
			materialReedRope.setRepresentativeItem("ropeReed");
			materialReedRope.addTrait(TinkerTraits.ecological);
			materialReedRope.addTrait(TinkerTraits.aquadynamic);
			materialReedRope.addTrait(TinkerTraits.writable2);
			materialReedRope.addStats(new BowStringMaterialStats(2f));
			materialReedRope.addStats(new FletchingMaterialStats(0.85f, 0.35f));

			materialDragonFlyWing.addItem("itemDragonFlyWing", 1, Material.VALUE_Ingot);
			materialDragonFlyWing.setCastable(false);
			materialDragonFlyWing.setCraftable(true);
			materialDragonFlyWing.setRepresentativeItem("itemDragonFlyWing");
			materialDragonFlyWing.addTrait(TinkerTraits.lightweight);
			materialDragonFlyWing.addStats(new FletchingMaterialStats(1f, 0.75f));

			materialAnglerTooth.addItem("itemAnglerTooth", 1, Material.VALUE_Ingot);
			materialAnglerTooth.setCastable(false);
			materialAnglerTooth.setCraftable(true);
			materialAnglerTooth.setRepresentativeItem("itemAnglerTooth");
			materialAnglerTooth.addTrait(TinkerTraits.aquadynamic);
			materialAnglerTooth.addTrait(TinkerTraits.jagged);
			materialAnglerTooth.addStats(new ProjectileMaterialStats());
			materialAnglerTooth.addStats(new HeadMaterialStats(128, 2f, 5f, 1));
			materialAnglerTooth.addStats(whyWouldYouMakeABowOutOfThis);

			materialWeedwood.addItem("plankWoodWeedwood", 1, Material.VALUE_Ingot);
			materialWeedwood.addItem("logWoodWeedwood", 1, Material.VALUE_Ingot * 4);
			materialWeedwood.addItem("stickWoodWeedwood", 1, Material.VALUE_Ingot / 2);
			materialWeedwood.setCastable(false);
			materialWeedwood.setCraftable(true);
			materialWeedwood.setRepresentativeItem("plankWoodWeedwood");
			materialWeedwood.addTrait(TinkerTraits.ecological);
			materialWeedwood.addStats(new HeadMaterialStats(40, 2f, 2f, 0));
			materialWeedwood.addStats(new HandleMaterialStats(1.2f, 25));
			materialWeedwood.addStats(new ExtraMaterialStats(15));
			materialWeedwood.addStats(new BowMaterialStats(0.8f, 1.1f, 2f));
			materialWeedwood.addStats(new ArrowShaftMaterialStats(1.2f, 16));
			
			materialShockwave.addStats(new HeadMaterialStats(1256, 6f, 6.125f, 3));
			materialShockwave.addTrait(ModTraits.shockwaving);

			materialWormed.addStats(new HeadMaterialStats(1024, 1f, 4f, 1));
			materialWormed.addStats(new ProjectileMaterialStats());
			materialWormed.addTrait(ModTraits.wormed);
		}
	}

	public static void init() {
		if (CompatManager.plustic) {
			ModMaterials.registerMaterial(materialCrimson);
		}
		if (CompatManager.jokes) {
			ModMaterials.registerMaterial(materialTechnoblade);
		}
		if (CompatManager.loadMain) {
			ModMaterials.registerMaterial(materialPlasma);
			ModMaterials.registerMaterial(materialFusionite).toolforge();
			ModMaterials.registerMaterial(materialValasium).toolforge();
			ModMaterials.registerMaterial(materialSpaceTimeDisruption, "SpaceTimeDisruption");
			ModMaterials.registerMaterial(materialVoidSpar, "VoidSpar").toolforge();
			ModMaterials.registerMaterial(materialEnderal).toolforge();
			ModMaterials.registerMaterial(materialIrradium).toolforge();
			ModMaterials.registerMaterial(materialSolsteel).toolforge();
			ModMaterials.registerMaterial(materialGallium).toolforge();
			ModMaterials.registerMaterial(materialEnderexamite);
			ModMaterials.registerMaterial(materialGarstone).toolforge();
			ModMaterials.registerMaterial(materialRuneSteel).toolforge();
			ModMaterials.registerMaterial(materialGravitonium).toolforge();
			ModMaterials.registerMaterial(materialTrichromadentium).toolforge();
			ModMaterials.registerMaterial(materialTriblade);
			ModMaterials.registerMaterial(materialMirrored);
			ModMaterials.registerMaterial(materialTrailblazer);
			ModMaterials.registerMaterial(materialBloodstone).toolforge();
			ModMaterials.registerMaterial(materialEchostone).toolforge();
			ModMaterials.registerMaterial(materialIgniglomerate).toolforge();
			ModMaterials.registerMaterial(materialAtronium).toolforge();
			ModMaterials.registerMaterial(materialEbonite).toolforge();
			ModMaterials.registerMaterial(materialErythynite).toolforge();
			ModMaterials.registerMaterial(materialShadowglass);
			ModMaterials.registerMaterial(materialEtherstone).toolforge();
			ModMaterials.registerMaterial(materialEssencore);
			ModMaterials.registerMaterial(materialElectarite).toolforge();
			ModMaterials.registerMaterial(materialHallowsite).toolforge();
		}
		if (CompatManager.tic3backport) {
			ModMaterials.registerMaterial(materialNahuatl);
			ModMaterials.registerMaterial(materialSlimewood);

		}

		if (CompatManager.twilightforest) {
			ModMaterials.registerMaterial(materialIronwood).toolforge();
			ModMaterials.registerMaterial(materialPenguinite).toolforge();
			ModMaterials.registerMaterial(materialFerroherb);
		}
		if (CompatManager.aether_legacy) {
			ModMaterials.registerMaterial(materialArkenium).toolforge();
			ModMaterials.registerMaterial(materialSkyroot);
			ModMaterials.registerMaterial(materialHolystone);
			ModMaterials.registerMaterial(materialZanite).toolforge();
			ModMaterials.registerMaterial(materialGravitite).toolforge();
			ModMaterials.registerMaterial(materialValkyrieMetal, "ValkyrieMetal").toolforge();
			ModMaterials.registerMaterial(materialAmberwood);
		}
		if (CompatManager.thebetweenlands) {
			ModMaterials.registerMaterial(materialShockwave);
			ModMaterials.registerMaterial(materialSyrmorite).toolforge();
			ModMaterials.registerMaterial(materialOctine).toolforge();
			ModMaterials.registerMaterial(materialValonite).toolforge();
			ModMaterials.registerMaterial(materialReedRope, "ReedRope");
			ModMaterials.registerMaterial(materialDragonFlyWing, "DragonFlyWing");
			ModMaterials.registerMaterial(materialAnglerTooth, "AnglerTooth");
			ModMaterials.registerMaterial(materialWeedwood, "WeedWood");
			ModMaterials.registerMaterial(materialSlimyBone, "SlimyBone").toolforge();
			ModMaterials.registerMaterial(materialSwampSteel).toolforge();
			ModMaterials.registerMaterial(materialRotiron).toolforge();
			ModMaterials.registerMaterial(materialEmberlight);
			ModMaterials.registerMaterial(materialWormed);

			MaterialUtils.readdTinkerMaterial(TinkerMaterials.bone);

		}
		MoreTConLogger.log("Loaded a total of " + totalMaterials + " different TConstruct Materials.");
	}

	public static MaterialIntegration registerMaterial(Material material) {
		String mat = material.getIdentifier().replaceFirst(ModInfo.MODID + ".", "");
		return registerMaterials(material, material.getFluid(), mat.substring(0, 1).toUpperCase() + mat.substring(1));
	}

	public static MaterialIntegration registerMaterial(Material material, String suffix) {
		return registerMaterials(material, material.getFluid(), suffix, "ingot" + suffix);
	}

	public static MaterialIntegration registerMaterial(Material material, String suffix, String... requiredOreDicts) {
		return registerMaterials(material, material.getFluid(), suffix, requiredOreDicts);
	}

	public static MaterialIntegration registerMaterials(Material material, Fluid fluid, String suffix, String... requiredOreDicts) {
		MaterialIntegration integration = new MTMaterialIntegration(material, fluid, suffix, requiredOreDicts.length == 0 ? new String[1] : requiredOreDicts);
		if (RegisterHelper.registerMaterial(integration, false)) {
			totalMaterials++;
		}
		return integration;
	}
}
