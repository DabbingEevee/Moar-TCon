package com.wordpress.craftminemods.tconmaterial.tconstuff;

import com.wordpress.craftminemods.tconmaterial.block.fluid.Fluids;
import com.wordpress.craftminemods.tconmaterial.other.Logging;
import com.wordpress.craftminemods.tconmaterial.other.RegisterHelper;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ArrowShaftMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.ProjectileMaterialStats;
import slimeknights.tconstruct.tools.TinkerTraits;

public class Materials {
	public static int totalMaterials;

	// Fusionite
	public static Material materialFusionite = new Material("materialFusionite".toLowerCase(), 0x0066ff);
	static {
		materialFusionite.addItem("oreFusionite", 1, Material.VALUE_Ore());
		materialFusionite.addCommonItems("Fusionite");
		materialFusionite.setFluid(Fluids.moltenFusionite);
		materialFusionite.setCastable(true);
		materialFusionite.setRepresentativeItem("ingotFusionite");
		materialFusionite.addTrait(TinkerTraits.alien);
		materialFusionite.addTrait(TinkerTraits.dense);
		materialFusionite.addTrait(TinkerTraits.flammable);
		materialFusionite.addStats(new HeadMaterialStats(50, 5f, 8f, 6));
		materialFusionite.addStats(new HandleMaterialStats(3f, 20));
		materialFusionite.addStats(new ExtraMaterialStats(20));
		// materialFusionite.addStats(new BowMaterialStats(1f, 1f, 0f));
		// materialFusionite.addStats(new BowStringMaterialStats(1f));
		materialFusionite.addStats(new ArrowShaftMaterialStats(3f, 20));
		// materialFusionite.addStats(new FletchingMaterialStats(1f, 1f));
		materialFusionite.addStats(new ProjectileMaterialStats());
	}

	private static void registerMaterials(Material... Material) {
		for (Material m : Material) {
			Materials.addMaterial(m, m.getFluid());
		}
	}

	public static void init() {
		Materials.registerMaterials(
		/** ------------------------------------- **/
				materialFusionite
		/** ------------------------------------- **/

		);
		Logging.Log("Loaded a total of " + totalMaterials + " different TConstruct Materials.");
	}

	private static void addMaterial(Material material, Fluid fluid) {
		RegisterHelper.registerMaterial(material, fluid);
		totalMaterials++;
	}


}
