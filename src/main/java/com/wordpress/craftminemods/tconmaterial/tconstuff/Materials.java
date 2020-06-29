package com.wordpress.craftminemods.tconmaterial.tconstuff;

import com.wordpress.craftminemods.tconmaterial.fluid.ModFluids;
import com.wordpress.craftminemods.tconmaterial.item.ModItems;
import com.wordpress.craftminemods.tconmaterial.other.Logging;
import com.wordpress.craftminemods.tconmaterial.other.RegisterHelper;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import slimeknights.tconstruct.library.materials.ArrowShaftMaterialStats;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.BowStringMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.FletchingMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.ProjectileMaterialStats;
import slimeknights.tconstruct.tools.TinkerTraits;

public class Materials {
	public static int totalMaterials;

	public static Material materialFusionite = new Material("materialFusionite".toLowerCase(), 0x3399ff);

	public static Material materialSpaceTimeDisruption = new Material("materialSpaceTimeDisruption".toLowerCase(),
			0x400080);

	static {
		materialFusionite.addItem("oreFusionite", 1, Material.VALUE_Ore());
		materialFusionite.setFluid(ModFluids.liquidFusionite);
		materialFusionite.addCommonItems("Fusionite");
		materialFusionite.setCastable(true);
		materialFusionite.setCraftable(false);
		materialFusionite.setRepresentativeItem("ingotFusionite");
		materialFusionite.addTrait(Traits.coldFire);
		materialFusionite.addTrait(TinkerTraits.alien);
		materialFusionite.addTrait(TinkerTraits.dense);
		materialFusionite.addStats(new HeadMaterialStats(50, 5f, 8f, 6));
		materialFusionite.addStats(new HandleMaterialStats(3f, 20));
		materialFusionite.addStats(new ExtraMaterialStats(20));
		materialFusionite.addStats(new BowMaterialStats(1f, 1f, 0f));
		// materialFusionite.addStats(new BowStringMaterialStats(1f));
		materialFusionite.addStats(new ArrowShaftMaterialStats(3f, 20));
		// materialFusionite.addStats(new FletchingMaterialStats(1f, 1f));
		materialFusionite.addStats(new ProjectileMaterialStats());

		materialSpaceTimeDisruption.addItem(ModItems.spaceTimeDisruptionPowder, 1, Material.VALUE_Fragment);
		materialSpaceTimeDisruption.setCastable(false);
		materialSpaceTimeDisruption.setCraftable(true);
		materialSpaceTimeDisruption.setRepresentativeItem(ModItems.spaceTimeDisruptionPowder);
		materialSpaceTimeDisruption.addTrait(TinkerTraits.lightweight);
		materialSpaceTimeDisruption.addTrait(TinkerTraits.dense);
		materialSpaceTimeDisruption.addStats(new HeadMaterialStats(5, 10f, 8f, 6));
		materialSpaceTimeDisruption.addStats(new HandleMaterialStats(3f, 2));
		materialSpaceTimeDisruption.addStats(new ExtraMaterialStats(4));
		materialSpaceTimeDisruption.addStats(new BowMaterialStats(10000f, 3f, 8f));
		materialSpaceTimeDisruption.addStats(new BowStringMaterialStats(3f));
		materialSpaceTimeDisruption.addStats(new ArrowShaftMaterialStats(3f, 64));
		materialSpaceTimeDisruption.addStats(new ProjectileMaterialStats());
		materialSpaceTimeDisruption.addStats(new FletchingMaterialStats(100, 25));
	}

	/*
	 * private static void registerMaterials(Material... material) { for (Material m
	 * : material) { Materials.addMaterial(m, m.getFluid(),
	 * m.getIdentifier().replace("material", "")); } }
	 * 
	 * 
	 * public static void init() { Materials.registerMaterials( materialFusionite,
	 * materialSpaceTimeDisruption );
	 * 
	 * 
	 * 
	 * Logging.Log("Loaded a total of " + totalMaterials +
	 * " different TConstruct Materials."); }
	 * 
	 * private static void addMaterial(Material material, Fluid fluid, String
	 * suffix) { RegisterHelper.registerMaterial(material, fluid, suffix);
	 * totalMaterials++; }
	 */
	private static void registerMaterials(Material Material, String suffix) {

		Materials.addMaterial(Material, Material.getFluid(), suffix);

	}

	public static void init() {
		Materials.registerMaterials(materialFusionite, "Fusionite");
		Materials.registerMaterials(materialSpaceTimeDisruption, "SpaceTimeDisruption");

		Logging.Log("Loaded a total of " + totalMaterials + " different TConstruct Materials.");
	}

	private static void addMaterial(Material material, Fluid fluid, String suffix) {
		RegisterHelper.registerMaterial(material, fluid, suffix);
		totalMaterials++;
	}

}
