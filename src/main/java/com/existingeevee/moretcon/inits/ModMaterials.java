package com.existingeevee.moretcon.inits;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.materials.CompositeRegistry;
import com.existingeevee.moretcon.materials.UniqueMaterial;
import com.existingeevee.moretcon.other.MoreTConLogger;
import com.existingeevee.moretcon.other.Misc;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.ConfigHandler;
import com.existingeevee.moretcon.other.utils.RegisterHelper;
import com.existingeevee.moretcon.traits.ModTraits;

import landmaster.plustic.tools.stats.BatteryCellMaterialStats;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.TinkerRegistry;
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
import twilightforest.compat.TConstruct;

public class ModMaterials implements MaterialTypes {

	public static int totalMaterials;

	public static Material materialWeedwood = new Material(Misc.createNonConflictiveName("weedwood".toLowerCase()),
			0xcc9900);

	public static Material materialIronwood = new Material(Misc.createNonConflictiveName("ironwood".toLowerCase()),
			0xcc9900);

	public static Material materialFusionite = new Material(Misc.createNonConflictiveName("fusionite".toLowerCase()),
			0x3399ff);

	public static Material materialSyrmorite = new Material(Misc.createNonConflictiveName("syrmorite".toLowerCase()),
			0x234187);

	public static Material materialValonite = new Material(Misc.createNonConflictiveName("Valonite".toLowerCase()),
			0xcab1ca);

	public static Material materialOctine = new Material(Misc.createNonConflictiveName("Octine".toLowerCase()),
			0xff8206);

	public static Material materialReedRope = new Material(Misc.createNonConflictiveName("ReedRope".toLowerCase()),
			0x000b4e0b);

	public static Material materialAnglerTooth = new Material(
			Misc.createNonConflictiveName("AnglerTooth".toLowerCase()), 0x00cfcf99);

	public static Material materialDragonFlyWing = new Material(
			Misc.createNonConflictiveName("DragonFlyWing".toLowerCase()), 0x00bed6db);
	// pp
	public static Material materialSpaceTimeDisruption = new Material(
			Misc.createNonConflictiveName("SpaceTimeDisruption".toLowerCase()), 0x400080);

	public static Material materialIrradium = new Material(Misc.createNonConflictiveName("irradium".toLowerCase()),
			0x00ed00);

	public static Material materialSolarSteel = new Material(Misc.createNonConflictiveName("SolarSteel".toLowerCase()),
			0xffad33);

	public static Material materialPenguinite = new Material(Misc.createNonConflictiveName("Penguinite".toLowerCase()),
			0xe6e6ffff);

	public static Material materialVoidSpar = new Material(Misc.createNonConflictiveName("VoidSpar".toLowerCase()),
			0xb37e15ff);

	public static Material materialGarstone = new Material(Misc.createNonConflictiveName("Garstone".toLowerCase()),
			0xff0a0a);
	public static Material materialBloodstone = new Material(Misc.createNonConflictiveName("bloodstone".toLowerCase()),
			0x660a0a);
	public static Material materialGallium = new Material(Misc.createNonConflictiveName("Gallium".toLowerCase()),
			0xffffff);

	public static Material materialSlimyBone = new Material(Misc.createNonConflictiveName("SlimyBone".toLowerCase()),
			0xede6bf);

	public static Material materialEnderal = new Material(Misc.createNonConflictiveName("Enderal".toLowerCase()),
			0xb32d0080);

	public static Material materialEnderexamite = new Material(
			Misc.createNonConflictiveName("Enderexamite".toLowerCase()), 0x8e3acf);

	public static Material materialFerroherb = new Material(Misc.createNonConflictiveName("Ferroherb".toLowerCase()),
			0x4c5e3a);

	public static Material materialRuneSteel = new Material(Misc.createNonConflictiveName("RuneSteel".toLowerCase()),
			0xb6905e);

	public static Material materialArkenium = new Material(Misc.createNonConflictiveName("Arkenium".toLowerCase()),
			0x595959);

	public static Material materialSkyroot = new Material(Misc.createNonConflictiveName("Skyroot".toLowerCase()),
			0x6c633e);

	public static Material materialHolystone = new Material(Misc.createNonConflictiveName("Holystone".toLowerCase()),
			0xa3a3a3);

	public static Material materialZanite = new Material(Misc.createNonConflictiveName("Zanite".toLowerCase()),
			0x6611dd);

	public static Material materialGravitite = new Material(Misc.createNonConflictiveName("Gravitite".toLowerCase()),
			0xcc55aa);

	public static Material materialValkyrieMetal = new Material(
			Misc.createNonConflictiveName("ValkyrieMetal".toLowerCase()), 0xeaee57);

	public static Material materialGravitonium = new Material(
			Misc.createNonConflictiveName("Gravitonium".toLowerCase()), 0x00aa00);

	public static Material materialTrichromadentium = new Material(
			Misc.createNonConflictiveName("trichromadentium".toLowerCase()), 0x777777);

	public static Material materialSwampSteel = new Material(Misc.createNonConflictiveName("swampsteel".toLowerCase()),
			0x003906);

	public static Material materialRotiron = new Material(Misc.createNonConflictiveName("rotiron".toLowerCase()),
			0x002400);

	public static Material materialEchostone = new Material(Misc.createNonConflictiveName("echostone".toLowerCase()),
			0x00725a);
	
	public static Material materialIgniglomerate = new Material(Misc.createNonConflictiveName("igniglomerate".toLowerCase()),
			0xB33B00);	

	public static Material materialAtronium = new Material(Misc.createNonConflictiveName("atronium".toLowerCase()),
			0xd99857);	
	
	public static Material materialEbonite = new Material(Misc.createNonConflictiveName("ebonite".toLowerCase()),
			0x270339);	
	
	public static UniqueMaterial materialPlasma = new UniqueMaterial(
			Misc.createNonConflictiveName("plasma".toLowerCase()), 0xff0000, "tconstruct:sword_blade",
			"tconstruct:broadsword");

	public static UniqueMaterial materialShockwave = new UniqueMaterial(
			Misc.createNonConflictiveName("shockwave".toLowerCase()), 0xa1ddd8,
			ConfigHandler.registerBetweenTinkerTools ? "moretcon:blswordblade" : "tconstruct:sword_blade",
			ConfigHandler.registerBetweenTinkerTools ? "moretcon:blsword" : "tconstruct:broadsword");

	public static UniqueMaterial materialTriblade = new UniqueMaterial(
			Misc.createNonConflictiveName("triblade".toLowerCase()), 0x6d6d6d, "tconstruct:knife_blade",
			"tconstruct:shuriken");

	public static UniqueMaterial materialTrailblazer = new UniqueMaterial(
			Misc.createNonConflictiveName("trailblazer".toLowerCase()), 0xff6c00, "tconstruct:knife_blade",
			"tconstruct:shuriken");

	public static UniqueMaterial materialMirrored = new UniqueMaterial(
			Misc.createNonConflictiveName("Mirrored".toLowerCase()), 0xfafaff, "tconstruct:fletching",
			"tconstruct:arrow");

	public static UniqueMaterial materialTechnoblade = new UniqueMaterial(
			Misc.createNonConflictiveName("technoblade".toLowerCase()), 0xea8f8c, "tconstruct:sword_blade",
			"tconstruct:broadsword");

	public static UniqueMaterial materialCrimson = new UniqueMaterial(
			Misc.createNonConflictiveName("Crimson".toLowerCase()), 0xaa0000, "tconstruct:tough_tool_rod",
			"plustic:katana");
	static {

		if (CompatManager.plustic) {
			// materialCrimson.addStats(new HeadMaterialStats(4096, 6f, 12f, 5));
			// materialCrimson.addStats(new ExtraMaterialStats(1000));
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
			materialFusionite.addTrait(ModTraits.coldFire);
			materialFusionite.addTrait(TinkerTraits.enderference);
			materialFusionite.addTrait(TinkerTraits.alien);
			materialFusionite.addTrait(TinkerTraits.dense);//TinkerMaterials
			materialFusionite.addStats(new HeadMaterialStats(500, 5.0f, 8.0f, 6));
			materialFusionite.addStats(new HandleMaterialStats(3.0f, 20));
			materialFusionite.addStats(new ExtraMaterialStats(20));
			materialFusionite.addStats(new ArrowShaftMaterialStats(3.0f, 20));
			materialFusionite.addStats(new ProjectileMaterialStats());
			if (CompatManager.plustic) {
				materialFusionite.addStats(new LaserMediumMaterialStats(10, 20));
				materialFusionite.addStats(new BatteryCellMaterialStats(100000));
			}

			materialIrradium.addItem("oreIrradium", 1, Material.VALUE_Ore());
			materialIrradium.setFluid(ModFluids.liquidIrradium);
			materialIrradium.addCommonItems("Irradium");
			materialIrradium.setCastable(true);
			materialIrradium.setCraftable(false);
			materialIrradium.setRepresentativeItem("ingotIrradium");
			materialIrradium.addTrait(ModTraits.radioactive);
			materialIrradium.addTrait(TinkerTraits.momentum);
			materialIrradium.addStats(new HeadMaterialStats(700, 6f, 4f, 5));
			if (CompatManager.plustic) {
				materialIrradium.addStats(new LaserMediumMaterialStats(8, 25));
				materialIrradium.addStats(new BatteryCellMaterialStats(480000));
			}
			materialIrradium.addStats(new HandleMaterialStats(3f, 40));
			materialIrradium.addStats(new ExtraMaterialStats(40));
			// materialirradium.addStats(new BowMaterialStats(9f, 3f, 8f));
			materialIrradium.addStats(new ArrowShaftMaterialStats(3f, 20));
			materialIrradium.addStats(new ProjectileMaterialStats());

			materialSolarSteel.setFluid(ModFluids.liquidSolarSteel);
			materialSolarSteel.addCommonItems("SolarSteel");
			materialSolarSteel.setCastable(true);
			materialSolarSteel.setCraftable(false);
			materialSolarSteel.setRepresentativeItem("ingotSolarSteel");
			materialSolarSteel.addTrait(TinkerTraits.flammable);
			materialSolarSteel.addTrait(TinkerTraits.superheat);
			materialSolarSteel.addTrait(TinkerTraits.dense);
			materialSolarSteel.addTrait(TinkerTraits.autosmelt);
			if (CompatManager.plustic) {
				materialSolarSteel.addStats(new LaserMediumMaterialStats(15, 20));
				materialSolarSteel.addStats(new BatteryCellMaterialStats(2000));
			}
			materialSolarSteel.addStats(new HeadMaterialStats(4500, 18f, 14f, 7));
			materialSolarSteel.addStats(new HandleMaterialStats(4f, 60));
			materialSolarSteel.addStats(new ExtraMaterialStats(60));
			materialSolarSteel.addStats(new ArrowShaftMaterialStats(3f, 20));

			materialTrichromadentium.addItem("oreTrichromadentium", 1, Material.VALUE_Ore());
			materialTrichromadentium.addCommonItems("Trichromadentium");
			materialTrichromadentium.setFluid(ModFluids.liquidTrichromadentium);
			materialTrichromadentium.setCastable(true);
			materialTrichromadentium.setCraftable(false);
			materialTrichromadentium.setRepresentativeItem("ingotTrichromadentium");
			materialTrichromadentium.addTrait(ModTraits.trichromic);
			if (CompatManager.plustic) {
				materialTrichromadentium.addStats(new LaserMediumMaterialStats(15, 20));
				materialTrichromadentium.addStats(new BatteryCellMaterialStats(2000));
			}
			materialTrichromadentium.addStats(new HeadMaterialStats(1800, 20f, 16f, 8));
			materialTrichromadentium.addStats(new HandleMaterialStats(4f, 60));
			materialTrichromadentium.addStats(new ExtraMaterialStats(500));
			materialTrichromadentium.addStats(new ArrowShaftMaterialStats(3f, 75));

			materialAtronium.addItem("oreAtronium", 1, Material.VALUE_Ore());
			materialAtronium.addCommonItems("Atronium");
			materialAtronium.setFluid(ModFluids.liquidAtronium);
			materialAtronium.setCastable(true);
			materialAtronium.setCraftable(false);
			materialAtronium.setRepresentativeItem("ingotAtronium");
			materialAtronium.addTrait(TinkerTraits.lightweight);
			materialAtronium.addTrait(ModTraits.magicallyReactive);
			materialAtronium.addTrait(ModTraits.penetrant);
			materialAtronium.addStats(new HeadMaterialStats(1500, 18f, 17f, 8));
			materialAtronium.addStats(new HandleMaterialStats(3f, 180));
			materialAtronium.addStats(new ExtraMaterialStats(750));
			materialAtronium.addStats(new ArrowShaftMaterialStats(2f, 120));
			
			materialEbonite.addItem("oreEbonite", 1, Material.VALUE_Ore());
			materialEbonite.setFluid(ModFluids.liquidEbonite);
			materialEbonite.addCommonItems("Ebonite");
			materialEbonite.setCastable(true);
			materialEbonite.setCraftable(false);
			materialEbonite.setRepresentativeItem("ingotEbonite");
			materialEbonite.addTrait(TinkerTraits.dense);
			materialEbonite.addTrait(ModTraits.darkened);
			materialEbonite.addTrait(TinkerTraits.established);			
			materialEbonite.addTrait(TinkerTraits.poisonous);			
			materialEbonite.addStats(new HeadMaterialStats(750, 6.0f, 9.0f, 7));
			materialEbonite.addStats(new HandleMaterialStats(2.5f, 60));
			materialEbonite.addStats(new ExtraMaterialStats(25));
			materialEbonite.addStats(new ArrowShaftMaterialStats(1.5f, 20));
			materialEbonite.addStats(new ProjectileMaterialStats());
			if (CompatManager.plustic) {
			    materialEbonite.addStats(new LaserMediumMaterialStats(12, 20));
			    materialEbonite.addStats(new BatteryCellMaterialStats(120000));
			}
			
			materialSpaceTimeDisruption.addItem(ModItems.spaceTimeDisruptionPowder, 1, Material.VALUE_Fragment);
			materialSpaceTimeDisruption.setCastable(false);
			materialSpaceTimeDisruption.setCraftable(true);
			materialSpaceTimeDisruption.setRepresentativeItem(ModItems.spaceTimeDisruptionPowder);
			materialSpaceTimeDisruption.addTrait(TinkerTraits.lightweight);
			materialSpaceTimeDisruption.addTrait(TinkerTraits.dense);
			if (CompatManager.plustic) {
				materialSpaceTimeDisruption.addStats(new LaserMediumMaterialStats(13, 25));
				materialSpaceTimeDisruption.addStats(new BatteryCellMaterialStats(520000));
			}
			materialSpaceTimeDisruption.addStats(new HeadMaterialStats(5, 10f, 8f, 6));
			materialSpaceTimeDisruption.addStats(new HandleMaterialStats(3f, 2));
			materialSpaceTimeDisruption.addStats(new ExtraMaterialStats(4));
			materialSpaceTimeDisruption.addStats(new BowMaterialStats(100f, 3f, 8f));
			materialSpaceTimeDisruption.addStats(new BowStringMaterialStats(3f));
			materialSpaceTimeDisruption.addStats(new ArrowShaftMaterialStats(3f, 64));
			materialSpaceTimeDisruption.addStats(new ProjectileMaterialStats());

			materialVoidSpar.addItem(ModItems.gemVoidSpar, 1, Material.VALUE_Ingot);
			materialVoidSpar.addItem(Item.getItemFromBlock(ModBlocks.blockVoidSpar), 1, Material.VALUE_Block);
			materialVoidSpar.setCastable(false);
			materialVoidSpar.setCraftable(true);
			materialVoidSpar.setRepresentativeItem(ModItems.gemVoidSpar);
			materialVoidSpar.addTrait(ModTraits.voidic);
			materialVoidSpar.addTrait(ModTraits.bottomsEnd);
			materialVoidSpar.addTrait(TinkerTraits.dense);
			materialVoidSpar.addTrait(TinkerTraits.ecological);
			materialVoidSpar.addStats(new HeadMaterialStats(300, 6f, 10f, 5));
			materialVoidSpar.addStats(new HandleMaterialStats(2f, -50));
			materialVoidSpar.addStats(new ExtraMaterialStats(2));
			materialVoidSpar.addStats(new ArrowShaftMaterialStats(1f, 10));
			materialVoidSpar.addStats(new ProjectileMaterialStats());

			materialGarstone.addItem(ModItems.gemGarstone, 1, Material.VALUE_Ingot);
			materialGarstone.addItem(Item.getItemFromBlock(ModBlocks.blockGarstone), 1, Material.VALUE_Block);
			materialGarstone.setCastable(false);
			materialGarstone.setCraftable(true);
			materialGarstone.setRepresentativeItem(ModItems.gemGarstone);
			materialGarstone.addTrait(TinkerTraits.coldblooded);
			materialGarstone.addTrait(TinkerTraits.jagged);
			materialGarstone.addTrait(TinkerTraits.unnatural);
			materialGarstone.addStats(new HeadMaterialStats(500, 6f, 10f, 5));
			materialGarstone.addStats(new HandleMaterialStats(2f, -50));
			materialGarstone.addStats(new ExtraMaterialStats(2));
			materialGarstone.addStats(new ArrowShaftMaterialStats(1f, 10));
			materialGarstone.addStats(new ProjectileMaterialStats());

			materialEchostone.addItem(ModItems.gemEchostone, 1, Material.VALUE_Ingot);
			materialEchostone.addItem(Item.getItemFromBlock(ModBlocks.blockEchostone), 1, Material.VALUE_Block);
			materialEchostone.setCastable(false);
			materialEchostone.setCraftable(true);
			materialEchostone.setRepresentativeItem(ModItems.gemEchostone);
			materialEchostone.addTrait(TinkerTraits.unnatural);
			materialEchostone.addTrait(ModTraits.afterimage, HEAD);
			materialEchostone.addStats(new HeadMaterialStats(500, 6f, 10f, 5));
			materialEchostone.addStats(new HandleMaterialStats(2f, -50));
			materialEchostone.addStats(new ExtraMaterialStats(2));
			materialEchostone.addStats(new ArrowShaftMaterialStats(1f, 10));
			materialEchostone.addStats(new ProjectileMaterialStats());
			
			materialBloodstone.addItem(ModItems.gemBloodstone, 1, Material.VALUE_Ingot); 
			materialBloodstone.addItem(Item.getItemFromBlock(ModBlocks.blockBloodstone), 1, Material.VALUE_Block);
			materialBloodstone.setCastable(false);
			materialBloodstone.setCraftable(true);
			materialBloodstone.setRepresentativeItem(ModItems.gemBloodstone);
			materialBloodstone.addTrait(TinkerTraits.coldblooded);
			materialBloodstone.addTrait(ModTraits.bottomsEnd);
			materialBloodstone.addTrait(ModTraits.slicing);
			materialBloodstone.addTrait(ModTraits.leeching);
			materialBloodstone.addStats(new HeadMaterialStats(750, 7f, 12f, 6));
			materialBloodstone.addStats(new HandleMaterialStats(1.125f, -35));
			materialBloodstone.addStats(new ExtraMaterialStats(75));
			materialBloodstone.addStats(new ArrowShaftMaterialStats(2.35f, 80));
			materialBloodstone.addStats(new ProjectileMaterialStats());
			
			materialGravitonium.addItem("oreGravitonium", 1, Material.VALUE_Ore());
			materialGravitonium.addItem("oreGravitoniumDense", 1, Material.VALUE_Ore() * 4);
			materialGravitonium.setFluid(ModFluids.liquidGravitonium);
			materialGravitonium.addCommonItems("Gravitonium");
			materialGravitonium.setCastable(true);
			materialGravitonium.setCraftable(false);
			materialGravitonium.setRepresentativeItem("ingotGravitonium");
			materialGravitonium.addTrait(ModTraits.hypergravity);
			materialGravitonium.addStats(new HeadMaterialStats(700, 6f, 4f, 5));
			if (CompatManager.plustic) {
				materialGravitonium.addStats(new LaserMediumMaterialStats(8, 25));
				materialGravitonium.addStats(new BatteryCellMaterialStats(480000));
			}
			materialGravitonium.addStats(new HandleMaterialStats(3f, 40));
			materialGravitonium.addStats(new ExtraMaterialStats(40));
			materialGravitonium.addStats(new ArrowShaftMaterialStats(3f, 20));
			materialGravitonium.addStats(new ProjectileMaterialStats());

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

			materialRuneSteel.addCommonItems("RuneSteel");
			materialRuneSteel.setFluid(ModFluids.liquidRuneSteel);
			materialRuneSteel.setCastable(true);
			materialRuneSteel.setCraftable(false);
			materialRuneSteel.addTrait(ModTraits.magical);
			materialRuneSteel.setRepresentativeItem("ingotRuneSteel");
			materialRuneSteel.addStats(new HeadMaterialStats(900, 18f, 14f, 7));
			materialRuneSteel.addStats(new HandleMaterialStats(4f, 60));
			materialRuneSteel.addStats(new ExtraMaterialStats(60));
			materialRuneSteel.addStats(new ArrowShaftMaterialStats(3f, 20));
			materialRuneSteel.addStats(new ProjectileMaterialStats());

			materialEnderal.addItem(ModItems.gemEnderal, 1, Material.VALUE_Ingot);
			materialEnderal.addItem(Item.getItemFromBlock(ModBlocks.blockEnderal), 1, Material.VALUE_Block);
			materialEnderal.setCastable(false);
			materialEnderal.setCraftable(true);
			materialEnderal.setRepresentativeItem(ModItems.gemEnderal);
			materialEnderal.addTrait(TinkerTraits.enderference);
			materialEnderal.addTrait(TinkerTraits.endspeed);
			materialEnderal.addTrait(TinkerTraits.jagged);
			materialEnderal.addTrait(ModTraits.pulsation);
			materialEnderal.addStats(new HeadMaterialStats(390, 6f, 10f, 5));
			materialEnderal.addStats(new HandleMaterialStats(2f, -50));
			materialEnderal.addStats(new ExtraMaterialStats(2));
			materialEnderal.addStats(new ArrowShaftMaterialStats(1f, 10));
			materialEnderal.addStats(new ProjectileMaterialStats());

			materialEnderexamite.setCastable(false);
			materialEnderexamite.setCraftable(false);
			ModMaterials.forceSetRepItem(new ItemStack(ModItems.repItemEnderexamite), materialEnderexamite);
			materialEnderexamite.addTrait(TinkerTraits.slimeyBlue);
			materialEnderexamite.addTrait(TinkerTraits.crumbling);
			materialEnderexamite.addTrait(TinkerTraits.endspeed);
			materialEnderexamite.addTrait(ModTraits.pulsation);
			materialEnderexamite.addTrait(ModTraits.resilient);
			materialEnderexamite.addStats(new HeadMaterialStats(1300, 7f, 11f, 5));
			materialEnderexamite.addStats(new HandleMaterialStats(2.25f, 20));
			materialEnderexamite.addStats(new ExtraMaterialStats(12));
			materialEnderexamite.addStats(new ArrowShaftMaterialStats(1f, 15));
			materialEnderexamite.addStats(new ProjectileMaterialStats());
			CompositeRegistry.registerComposite(materialEnderal, materialEnderexamite, TinkerFluids.knightslime);

			materialPlasma.addStats(new HeadMaterialStats(4096, 6f, 12f, 5));
			materialPlasma.addTrait(ModTraits.plasmatic);

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
			
			materialIgniglomerate.addItem(ModItems.gemIgniglomerate, 1, Material.VALUE_Ingot);
			materialIgniglomerate.addItem(Item.getItemFromBlock(ModBlocks.blockIgniglomerate), 1, Material.VALUE_Block);
			materialIgniglomerate.setCastable(false);
			materialIgniglomerate.setCraftable(true);
			materialIgniglomerate.setRepresentativeItem(ModItems.gemIgniglomerate);
			materialIgniglomerate.addTrait(ModTraits.hyperheat);
			materialIgniglomerate.addStats(new HeadMaterialStats(700, 6f, 7f, 7));
			materialIgniglomerate.addStats(new HandleMaterialStats(1.25f, -10));
			materialIgniglomerate.addStats(new ExtraMaterialStats(250));
			materialIgniglomerate.addStats(new ArrowShaftMaterialStats(1f, 10));
			materialIgniglomerate.addStats(new ProjectileMaterialStats());

		}
		if (CompatManager.aether_legacy) { // TODO
			materialZanite.addItem("gemZanite", 1, Material.VALUE_Ingot);
			materialZanite.addItem("blockZanite", 1, Material.VALUE_Block);
			materialZanite.setCastable(false);
			materialZanite.setCraftable(true);
			materialZanite.setRepresentativeItem("gemZanite");
			materialZanite.addTrait(ModTraits.aetheric);
			materialZanite.addTrait(ModTraits.treetap);
			materialZanite.addTrait(TinkerTraits.jagged);
			materialZanite.addTrait(ModTraits.hardcore);
			materialZanite.addStats(new HeadMaterialStats(210, 2.00f, 4.00f, 2));
			materialZanite.addStats(new HandleMaterialStats(0.9f, 65));
			materialZanite.addStats(new ExtraMaterialStats(50));

			materialSkyroot.addItem("plankWoodSkyroot", 1, Material.VALUE_Ingot);
			materialSkyroot.addItem("logWoodSkyroot", 1, Material.VALUE_Ingot * 4);
			materialSkyroot.addItem("stickWoodSkyroot", 1, Material.VALUE_Ingot / 2);
			materialSkyroot.setCastable(false);
			materialSkyroot.setCraftable(true);
			materialSkyroot.setRepresentativeItem("logWoodSkyroot");
			materialSkyroot.addTrait(ModTraits.aetheric);
			materialSkyroot.addTrait(ModTraits.rootplicating);
			materialSkyroot.addTrait(TinkerTraits.ecological);
			materialSkyroot.addStats(new HeadMaterialStats(40, 2f, 2f, 0));
			materialSkyroot.addStats(new HandleMaterialStats(1.2f, 25));
			materialSkyroot.addStats(new ExtraMaterialStats(15));
			materialSkyroot.addStats(new BowMaterialStats(0.6f, 1.1f, 2f));
			materialSkyroot.addStats(new ArrowShaftMaterialStats(1.2f, 16));

			materialArkenium.addCommonItems("Arkenium");
			materialArkenium.setFluid(ModFluids.liquidArkenium);
			materialArkenium.setCastable(true);
			materialArkenium.setCraftable(false);
			materialArkenium.addTrait(TinkerTraits.lightweight);
			materialArkenium.addTrait(TinkerTraits.holy);
			materialArkenium.addTrait(TinkerTraits.sharp);
			materialArkenium.addTrait(ModTraits.aetheric);
			materialArkenium.setRepresentativeItem("ingotArkenium");
			materialArkenium.addStats(new HeadMaterialStats(1250, 10.0f, 5.25f, 4));
			materialArkenium.addStats(new HandleMaterialStats(1.1f, 100));
			materialArkenium.addStats(new ExtraMaterialStats(125));
			materialArkenium.addStats(new ArrowShaftMaterialStats(3.5f, 25));
			materialArkenium.addStats(new ProjectileMaterialStats());

			materialValkyrieMetal.addCommonItems("ValkyrieMetal");
			materialValkyrieMetal.setFluid(ModFluids.liquidValkyrieMetal);
			materialValkyrieMetal.setCastable(true);
			materialValkyrieMetal.setCraftable(false);
			materialValkyrieMetal.addTrait(ModTraits.reaching);
			materialValkyrieMetal.addTrait(ModTraits.treetap);
			materialValkyrieMetal.addTrait(ModTraits.aetheric);
			materialValkyrieMetal.setRepresentativeItem("ingotValkyrieMetal");
			materialValkyrieMetal.addStats(new HeadMaterialStats(1250, 10.0f, 5.25f, 4));
			materialValkyrieMetal.addStats(new HandleMaterialStats(1.1f, 100));
			materialValkyrieMetal.addStats(new ExtraMaterialStats(125));
			materialValkyrieMetal.addStats(new ArrowShaftMaterialStats(3.5f, 25));
			materialValkyrieMetal.addStats(new ProjectileMaterialStats());

			materialHolystone.addItem("holystone", 1, Material.VALUE_Ingot);
			materialHolystone.setCastable(false);
			materialHolystone.setCraftable(true);
			materialHolystone.setRepresentativeItem("holystone");
			materialHolystone.addTrait(TinkerTraits.cheapskate);
			materialHolystone.addTrait(ModTraits.aetheric);
			materialHolystone.addTrait(ModTraits.blessed);
			materialHolystone.addStats(new HeadMaterialStats(130, 3f, 3f, 1));
			materialHolystone.addStats(new HandleMaterialStats(0.25f, -50));
			materialHolystone.addStats(new ExtraMaterialStats(30));

			materialGravitite.addItem("blockGravitite", 1, Material.VALUE_Block);
			materialGravitite.addItem("gemGravitite", 1, Material.VALUE_Ingot);
			materialGravitite.setCastable(false);
			materialGravitite.setCraftable(true);
			materialGravitite.setRepresentativeItem("gemGravitite");
			materialGravitite.addTrait(TinkerTraits.insatiable);
			materialGravitite.addTrait(ModTraits.antigravity);
			materialGravitite.addTrait(ModTraits.treetap);
			materialGravitite.addStats(new HeadMaterialStats(950, 7.50f, 5.00f, 3));
			materialGravitite.addStats(new HandleMaterialStats(0.9f, 90));
			materialGravitite.addStats(new ExtraMaterialStats(90));

		}
		if (CompatManager.twilightforest) {
			materialIronwood.addCommonItems("Ironwood");
			materialIronwood.setCastable(true);
			materialIronwood.setFluid(ModFluids.liquidIronwood);
			materialIronwood.setCraftable(false);
			materialIronwood.setRepresentativeItem("ingotIronwood");
			materialIronwood.addTrait(TConstruct.twilit);
			materialIronwood.addTrait(TinkerTraits.ecological);
			materialIronwood.addStats(new HeadMaterialStats(100, 10f, 5f, 4));
			materialIronwood.addStats(new HandleMaterialStats(1f, 25));
			materialIronwood.addStats(new ExtraMaterialStats(25));
			materialIronwood.addStats(new ArrowShaftMaterialStats(1f, 1));
			materialIronwood.addStats(new ProjectileMaterialStats());

			ModMaterials.forceSetRepItem(new ItemStack(ModItems.repItemFerroherb), materialFerroherb);
			materialFerroherb.setCastable(false);
			materialFerroherb.setCraftable(false);
			CompositeRegistry.registerComposite(TConstruct.steeleaf, materialFerroherb, ModFluids.liquidIronwood);
			materialFerroherb.addTrait(TConstruct.synergy);
			materialFerroherb.addTrait(TConstruct.twilit);
			materialFerroherb.addTrait(TinkerTraits.ecological);
			materialFerroherb.addTrait(TinkerTraits.sharp);
			materialFerroherb.addTrait(TinkerTraits.splintering);
			materialFerroherb.addStats(new HeadMaterialStats(200, 10f, 7f, 4));
			materialFerroherb.addStats(new HandleMaterialStats(1.125f, 100));
			materialFerroherb.addStats(new ExtraMaterialStats(125));
			materialFerroherb.addStats(new ArrowShaftMaterialStats(1f, 25));
			materialFerroherb.addStats(new ProjectileMaterialStats());

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

			// materialFusionDragonSteel.addItem("oreFusionite", 1, Material.VALUE_Ore());
			// materialFusionDragonSteel.setFluid(ModFluids.liquidFusionite);
			// materialFusionDragonSteel.addCommonItems("Fusionite");
			// materialFusionDragonSteel.setCastable(true);
			// materialFusionDragonSteel.setCraftable(false);
			// materialFusionDragonSteel.setRepresentativeItem("ingotFusionite");
			/*
			 * materialFusionDragonSteel.addTrait(Traits.coldFire);
			 * materialFusionDragonSteel.addTrait(TinkerTraits.enderference);
			 * materialFusionDragonSteel.addTrait(TinkerTraits.alien);
			 * materialFusionDragonSteel.addTrait(TinkerTraits.dense);
			 * materialFusionDragonSteel.addStats(new HeadMaterialStats(50, 5f, 8f, 6));
			 * materialFusionDragonSteel.addStats(new HandleMaterialStats(3f, 20));
			 * materialFusionDragonSteel.addStats(new ExtraMaterialStats(20));
			 * materialFusionDragonSteel.addStats(new BowMaterialStats(1f, 1f, 0f)); //
			 * materialFusionite.addStats(new BowStringMaterialStats(1f));
			 * materialFusionDragonSteel.addStats(new ArrowShaftMaterialStats(3f, 20)); //
			 * materialFusionite.addStats(new FletchingMaterialStats(1f, 1f));
			 * materialFusionDragonSteel.addStats(new ProjectileMaterialStats());
			 */
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

			materialRotiron.setFluid(ModFluids.liquidRotiron);
			materialRotiron.addCommonItems("Rotiron");
			materialRotiron.setCastable(true);
			materialRotiron.setCraftable(false);
			materialRotiron.setRepresentativeItem("ingotRotiron");
			materialRotiron.addTrait(TinkerTraits.dense);
			materialRotiron.addTrait(TinkerTraits.magnetic);
			materialRotiron.addTrait(ModTraits.rotten);
			materialRotiron.addStats(new HeadMaterialStats(850, 5.25f, 5f, 2));
			materialRotiron.addStats(new HandleMaterialStats(1.125f, -35));
			materialRotiron.addStats(new ExtraMaterialStats(-20));
			materialRotiron.addStats(new ProjectileMaterialStats());

			materialSwampSteel.setFluid(ModFluids.liquidSwampSteel);
			materialSwampSteel.addCommonItems("SwampSteel");
			materialSwampSteel.setCastable(true);
			materialSwampSteel.setCraftable(false);
			materialSwampSteel.setRepresentativeItem("ingotSwampSteel");
			materialSwampSteel.addTrait(TinkerTraits.dense);
			materialSwampSteel.addTrait(ModTraits.corroding);
			materialSwampSteel.addStats(new HeadMaterialStats(1200, 5.25f, 4.5f, 3));
			materialSwampSteel.addStats(new HandleMaterialStats(1.5f, -25));
			materialSwampSteel.addStats(new ExtraMaterialStats(-12));
			materialSwampSteel.addStats(new ProjectileMaterialStats());

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
			// materialOctine.addStats(new BowMaterialStats(8f, 0.35f, 1f));
			materialOctine.addStats(new ProjectileMaterialStats());

			materialValonite.addItem("gemValonite", 1, Material.VALUE_Ingot);
			materialValonite.addItem("blockValonite", 1, Material.VALUE_Block);
			materialValonite.addItem("nuggetValonite", 1, Material.VALUE_Nugget);
			materialValonite.setCastable(false);
			materialValonite.setCraftable(true);
			materialValonite.setRepresentativeItem("gemValonite");
			materialValonite.addTrait(TinkerTraits.dense);
			materialValonite.addTrait(TinkerTraits.sharp);
			materialValonite.addStats(new HeadMaterialStats(975, 6f, 6.125f, 3));
			materialValonite.addStats(new HandleMaterialStats(1f, -75));
			materialValonite.addStats(new ExtraMaterialStats(-25));
			materialValonite.addStats(new ProjectileMaterialStats());

			materialSlimyBone.addItem("gemSlimyBone", 1, Material.VALUE_Ingot);
			// materialSlimyBone.addItem("itemSlimyBone", 1, Material.VALUE_Ingot);
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

			materialWeedwood.addItem("plankWoodWeedwood", 1, Material.VALUE_Ingot);
			materialWeedwood.addItem("logWoodWeedwood", 1, Material.VALUE_Ingot * 4);
			materialWeedwood.addItem("stickWoodWeedwood", 1, Material.VALUE_Ingot / 2);
			materialWeedwood.setCastable(false);
			materialWeedwood.setCraftable(true);
			materialWeedwood.setRepresentativeItem("logWoodWeedwood");
			materialWeedwood.addTrait(TinkerTraits.ecological);
			materialWeedwood.addStats(new HeadMaterialStats(40, 2f, 2f, 0));
			materialWeedwood.addStats(new HandleMaterialStats(1.2f, 25));
			materialWeedwood.addStats(new ExtraMaterialStats(15));
			materialWeedwood.addStats(new BowMaterialStats(0.8f, 1.1f, 2f));
			materialWeedwood.addStats(new ArrowShaftMaterialStats(1.2f, 16));

			materialShockwave.addStats(new HeadMaterialStats(1256, 6f, 6.125f, 3));
			materialShockwave.addTrait(ModTraits.shockwaving);
		}
	}

	public static void registerMaterial(Material material) {
		ModMaterials.addMaterial(material, material.getFluid(),
				material.getIdentifier().replaceFirst(ModInfo.MODID + ".", "").substring(0, 1).toUpperCase()
						+ material.getIdentifier().replaceFirst(ModInfo.MODID + ".", "").substring(1));
	}

	public static void registerMaterial(Material material, String suffix) {
		ModMaterials.addMaterial(material, material.getFluid(), suffix);
	}

	public static void registerMaterials(Material material, String suffix, boolean bypassCheck) {
		ModMaterials.addMaterial(material, material.getFluid(), suffix, bypassCheck);
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
			ModMaterials.registerMaterial(materialFusionite);
			ModMaterials.registerMaterial(materialSpaceTimeDisruption, "SpaceTimeDisruption");
			ModMaterials.registerMaterial(materialVoidSpar, "VoidSpar");
			ModMaterials.registerMaterial(materialEnderal);
			ModMaterials.registerMaterial(materialIrradium);
			ModMaterials.registerMaterial(materialSolarSteel);
			ModMaterials.registerMaterial(materialGallium);
			ModMaterials.registerMaterial(materialEnderexamite);
			ModMaterials.registerMaterial(materialGarstone);
			ModMaterials.registerMaterial(materialRuneSteel);
			ModMaterials.registerMaterial(materialGravitonium);
			ModMaterials.registerMaterial(materialTrichromadentium);
			ModMaterials.registerMaterial(materialTriblade);
			ModMaterials.registerMaterial(materialMirrored);
			ModMaterials.registerMaterial(materialTrailblazer);
			ModMaterials.registerMaterial(materialBloodstone);
			ModMaterials.registerMaterial(materialEchostone);
			ModMaterials.registerMaterial(materialIgniglomerate);
			ModMaterials.registerMaterial(materialAtronium);
			ModMaterials.registerMaterial(materialEbonite);
			
		}
		if (CompatManager.twilightforest) {
			ModMaterials.registerMaterial(materialIronwood);
			ModMaterials.registerMaterial(materialPenguinite);
			ModMaterials.registerMaterial(materialFerroherb);
		}
		if (CompatManager.aether_legacy) {
			ModMaterials.registerMaterial(materialArkenium);
			ModMaterials.registerMaterial(materialSkyroot);
			ModMaterials.registerMaterial(materialHolystone);
			ModMaterials.registerMaterial(materialZanite);
			ModMaterials.registerMaterial(materialGravitite);
			ModMaterials.registerMaterial(materialValkyrieMetal, "ValkyrieMetal");
		}
		if (CompatManager.thebetweenlands) {
			ModMaterials.registerMaterial(materialShockwave);
			ModMaterials.registerMaterial(materialSyrmorite);
			ModMaterials.registerMaterial(materialOctine);
			ModMaterials.registerMaterial(materialValonite);
			ModMaterials.registerMaterial(materialReedRope, "ReedRope");
			ModMaterials.registerMaterial(materialDragonFlyWing, "DragonFlyWing");
			ModMaterials.registerMaterial(materialAnglerTooth, "AnglerTooth");
			ModMaterials.registerMaterial(materialWeedwood, "WeedWood");
			ModMaterials.registerMaterial(materialSlimyBone, "SlimyBone");
			ModMaterials.registerMaterial(materialSwampSteel);
			ModMaterials.registerMaterial(materialRotiron);
			ModMaterials.readdTinkerMaterial(TinkerMaterials.bone.identifier);

		}
		if (CompatManager.jokes) {

		}
		MoreTConLogger.log("Loaded a total of " + totalMaterials + " different TConstruct Materials.");
	}

	public static boolean removeTinkerMaterial(Material material) {
		return removeTinkerMaterial(material.getIdentifier());
	}

	@SuppressWarnings("unchecked")
	public static boolean removeTinkerMaterial(String identifier) {
		boolean success = false;
		Field mat = null;
		try {
			mat = TinkerRegistry.class.getDeclaredField("materials");
			mat.setAccessible(true);
			Map<String, Material> fieldValue = (Map<String, Material>) mat.get(TinkerRegistry.class);
			success = fieldValue.entrySet().removeIf(m -> m.getValue().identifier.equals(identifier));
			if (!success) {
				MoreTConLogger.log("Unable to remove material \"" + identifier + "\" as it was never registered", Level.ERROR);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
			MoreTConLogger.log("Unable to remove material \"" + identifier + "\" as an error was encountered", Level.ERROR);
			e1.printStackTrace();
		}
		return success;
	}

	public static Map<String, Material> readd = new LinkedHashMap<>();

	public static void completeReadds() {
		try {
			Field mat = TinkerRegistry.class.getDeclaredField("materials");
			mat.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<String, Material> fieldValue = (Map<String, Material>) mat.get(TinkerRegistry.class);
			readd.forEach((s, m) -> fieldValue.remove(s));
			fieldValue.putAll(readd);
		} catch (NoSuchFieldException | IllegalAccessException e) {

		}

	}

	@SuppressWarnings("unchecked")
	public static boolean readdTinkerMaterial(String identifier) {
		boolean success = false;
		Field mat = null;
		try {
			mat = TinkerRegistry.class.getDeclaredField("materials");
			mat.setAccessible(true);
			Map<String, Material> fieldValue = (Map<String, Material>) mat.get(TinkerRegistry.class);
			Entry<String, Material> entry = null;
			for (Entry<String, Material> e : fieldValue.entrySet()) {
				if (e.getValue().identifier.equals(identifier)) {
					success = true;
					entry = e;
					break;
				}
			}
			readd.put(entry.getKey(), entry.getValue());

			if (!success) {
				MoreTConLogger.log("Unable to readd material \"" + identifier + "\" as it was never registered", Level.ERROR);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
			MoreTConLogger.log("Unable to readd material \"" + identifier + "\" as an error was encountered", Level.ERROR);
			e1.printStackTrace();
		}
		return success;
	}

	public static Material forceSetRepItem(ItemStack repItem, Material material) {
		Field f = null;
		try {
			for (Field pF : Material.class.getDeclaredFields()) {
				if (pF.getName().equals("representativeItem")) {
					f = pF;
					break;
				}
			}
			f.setAccessible(true);
			f.set(material, repItem);
		} catch (NullPointerException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		// Logging.log("forceSetRepItem(" + repItem.getItem().getRegistryName() + ", " +
		// material.identifier + ")");
		return material;
	}

	private static void addMaterial(Material material, Fluid fluid, String suffix, boolean bypassCheck) {
		if (RegisterHelper.registerMaterial(material, fluid, suffix, bypassCheck)) {
			if (!bypassCheck) {
				totalMaterials++;
			}
		}
	}

	private static void addMaterial(Material material, Fluid fluid, String suffix) {
		addMaterial(material, fluid, suffix, false);
	}

	/*
	 * public static void addItems() { if (CompatManager.loadMain) {
	 * 
	 * materialFusionite.addItem("oreFusionite", 1, Material.VALUE_Ore());
	 * materialFusionite.setFluid(ModFluids.liquidFusionite);
	 * materialFusionite.addCommonItems("Fusionite");
	 * 
	 * materialirradium.addItem("oreirradium", 1, Material.VALUE_Ore());
	 * materialirradium.setFluid(ModFluids.liquidirradium);
	 * materialirradium.addCommonItems("irradium");
	 * 
	 * materialSolarSteel.addCommonItems("SolarSteel");
	 * 
	 * materialSpaceTimeDisruption.addItem(ModItems.spaceTimeDisruptionPowder, 1,
	 * Material.VALUE_Fragment);
	 * 
	 * materialVoidSpar.addItem(ModItems.gemVoidSpar, 1, Material.VALUE_Ingot);
	 * materialVoidSpar.addItem(ModBlocks.blockVoidSpar, Material.VALUE_Block);
	 * 
	 * materialGallium.addCommonItems("Gallium");
	 * 
	 * materialEnderal.addItem(ModItems.gemEnderal, 1, Material.VALUE_Ingot);
	 * materialEnderal.addItem(ModBlocks.blockEnderal, Material.VALUE_Block);
	 * 
	 * 
	 * materialIronwood.addCommonItems("Ironwood");
	 * 
	 * materialPenguinite.addCommonItems("Penguinite");
	 * 
	 * 
	 * materialSyrmorite.addItem("oreSyrmorite", 1, Material.VALUE_Ore());
	 * materialSyrmorite.addCommonItems("Syrmorite");
	 * 
	 * materialOctine.addItem("oreOctine", 1, Material.VALUE_Ore());
	 * materialOctine.addCommonItems("Octine");
	 * 
	 * materialValonite.addItem("gemValonite", 1, Material.VALUE_Ingot);
	 * materialValonite.addItem("blockValonite", 1, Material.VALUE_Block);
	 * materialValonite.addItem("nuggetValonite", 1, Material.VALUE_Nugget);
	 * 
	 * materialSlimyBone.addItem("gemSlimyBone", 1, Material.VALUE_Ingot);
	 * materialSlimyBone.addItem("blockSlimyBone", 1, Material.VALUE_Block);
	 * 
	 * materialReedRope.addItem("ropeReed", 1, Material.VALUE_Ingot);
	 * 
	 * materialDragonFlyWing.addItem("itemDragonFlyWing", 1, Material.VALUE_Ingot);
	 * 
	 * materialAnglerTooth.addItem("itemAnglerTooth", 1, Material.VALUE_Ingot);
	 * 
	 * materialWeedwood.addItem("plankWoodWeedwood", 1, Material.VALUE_Ingot);
	 * materialWeedwood.addItem("logWoodWeedwood", 1, Material.VALUE_Ingot * 4);
	 * materialWeedwood.addItem("stickWoodWeedwood", 1, Material.VALUE_Ingot / 2);
	 * 
	 * }
	 */

}
