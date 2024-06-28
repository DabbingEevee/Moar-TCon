package com.existingeevee.moretcon.inits.recipes;

import org.apache.commons.lang3.tuple.Pair;

import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.inits.ModMaterials;
import com.existingeevee.moretcon.materials.UniqueMaterial;
import com.existingeevee.moretcon.other.RecipeHelper;
import com.existingeevee.moretcon.other.ingredient.TinkerPartIngredient;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.oredict.OreIngredient;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.tools.TinkerMaterials;

public class UniqueToolpartRecipes {

	public static void init(Register<IRecipe> event) {
		if (ConfigHandler.shouldAllowMainContent) {
			if (canRegisterUniqueRecipe(ModMaterials.materialPlasma)) {
				event.getRegistry().register(
						RecipeHelper.createRecipe("plasma_recipe", ModMaterials.materialPlasma.getUniqueToolPart(),
								new String[] {
										"IBI",
										"GPG",
										"IEI"
								},
								Pair.of('I', new OreIngredient("ingotIron")),
								Pair.of('B', new OreIngredient("blockGarstone")),
								Pair.of('P', new OreIngredient("dustPrismarine")),
								Pair.of('E', Ingredient.fromStacks(new ItemStack(Blocks.BEACON))),
								Pair.of('G', new OreIngredient("gemGarstone"))));
			}
			if (canRegisterUniqueRecipe(ModMaterials.materialTrailblazer)) {
				event.getRegistry().register(
						RecipeHelper.createRecipe("trailblazer_recipe", ModMaterials.materialTrailblazer.getUniqueToolPart(),
								new String[] {
										" SF",
										"WPS",
										"BW "
								},
								Pair.of('S', new OreIngredient("blockSolsteel")),
								Pair.of('F', new OreIngredient("blockFusionite")),
								Pair.of('W', new TinkerPartIngredient(TinkerMaterials.firewood, "tconstruct:knife_blade")),
								Pair.of('P', new TinkerPartIngredient(ModMaterials.materialIgniglomerate, "tconstruct:knife_blade")),
								Pair.of('B', Ingredient.fromStacks(new ItemStack(Blocks.BEACON)))));
			}
			if (canRegisterUniqueRecipe(ModMaterials.materialTriblade)) {
				event.getRegistry().register(
						RecipeHelper.createRecipe("triblade_recipe", ModMaterials.materialTriblade.getUniqueToolPart(),
								new String[] {
										"W W",
										"VB ",
										"BVW"
								},
								Pair.of('W', Ingredient.fromStacks(new ItemStack(Items.SKULL, 1, 1))),
								Pair.of('V', new OreIngredient("gemVoidSpar")),
								Pair.of('B', new OreIngredient("boneWithered"))));
			}

			if (canRegisterUniqueRecipe(ModMaterials.materialMirrored)) {
				event.getRegistry().register(
						RecipeHelper.createRecipe("mirrored_recipe", ModMaterials.materialMirrored.getUniqueToolPart(),
								new String[] {
										"RGR",
										"GFG",
										"RGR"
								},
								Pair.of('G', new OreIngredient("ingotGallium")),
								Pair.of('R', new OreIngredient("nuggetRunesteel")),
								Pair.of('F', new TinkerPartIngredient(TinkerMaterials.feather, "tconstruct:fletching"))));
			}

			if (canRegisterUniqueRecipe(ModMaterials.materialTechnoblade)) {
				event.getRegistry().register(
						RecipeHelper.createRecipe("techno_recipe", ModMaterials.materialTechnoblade.getUniqueToolPart(),
								new String[] {
										" GS",
										"NPG",
										"DN "
								},
								Pair.of('S', new OreIngredient("netherStar")),
								Pair.of('G', new OreIngredient("gemGarstone")),
								Pair.of('N', new OreIngredient("ingotBrickNether")),
								Pair.of('G', new OreIngredient("ingotGallium")),
								Pair.of('D', new OreIngredient("blockDiamond")),
								Pair.of('P', new TinkerPartIngredient(TinkerMaterials.pigiron, "tconstruct:sword_blade"))));
			}
			if (canRegisterUniqueRecipe(ModMaterials.materialCrimson)) {
				event.getRegistry().register(
						RecipeHelper.createRecipe("crimson_recipe", ModMaterials.materialCrimson.getUniqueToolPart(),
								new String[] {
										"ACG",
										"CBC",
										"OCA"
								},
								Pair.of('A', new OreIngredient("ingotGallium")),
								Pair.of('G', new OreIngredient("gemGarstone")),
								Pair.of('C', new OreIngredient("slimeballBlood")),
								Pair.of('B', new TinkerPartIngredient(ModMaterials.materialBloodstone, "tconstruct:tough_tool_rod")),
								Pair.of('O', new TinkerPartIngredient(TinkerMaterials.bone, "tconstruct:tough_tool_rod"))));
			}
			if (canRegisterUniqueRecipe(ModMaterials.materialEssencore)) {
				event.getRegistry().register(
						RecipeHelper.createRecipe("essencore_recipe", ModMaterials.materialEssencore.getUniqueToolPart(),
								new String[] {
										"GFN",
										"FRF",
										"NFG"
								},
								Pair.of('G', new OreIngredient("ingotGallium")),
								Pair.of('F', new OreIngredient("ingotFusionite")),
								Pair.of('N', new OreIngredient("nuggetFusionite")),
								Pair.of('R', new OreIngredient("ingotRunesteel"))));
			}
			
			if (canRegisterUniqueRecipe(ModMaterials.materialSpaceTimeDisruption)) {
				event.getRegistry().register(
						RecipeHelper.createRecipe("spacetimedisruption_recipe", ModMaterials.materialSpaceTimeDisruption.getUniqueToolPart(),
								new String[] {
										"PVP",
										"EFE",
										"PVP"
								},
								Pair.of('P', Ingredient.fromStacks(new ItemStack(ModItems.spaceTimeDisruptionPowder))),
								Pair.of('V', new TinkerPartIngredient(ModMaterials.materialVoidSpar, "tconstruct:bow_limb")),
								Pair.of('E', new TinkerPartIngredient(ModMaterials.materialEchostone, "tconstruct:bow_limb")),
								Pair.of('F', new TinkerPartIngredient(ModMaterials.materialFusionite, "tconstruct:bow_limb"))));
			}
			
			if (canRegisterUniqueRecipe(ModMaterials.materialQuakestruck)) {
				event.getRegistry().register(
						RecipeHelper.createRecipe("quakestruck_recipe", ModMaterials.materialQuakestruck.getUniqueToolPart(),
								new String[] {
										"OFO",
										"EIE",
										"OFO"
								},
								Pair.of('E', new OreIngredient("gemElectarite")),
								Pair.of('I', new TinkerPartIngredient(ModMaterials.materialIgniglomerate, "tconstruct:large_plate")),
								Pair.of('O', new TinkerPartIngredient(TinkerMaterials.obsidian, "tconstruct:large_plate")),
								Pair.of('F', new OreIngredient("ingotFusionite"))));
			}
			
			if (canRegisterUniqueRecipe(ModMaterials.materialCryosplinters)) {
				event.getRegistry().register(
						RecipeHelper.createRecipe("cryosplinters_recipe", ModMaterials.materialCryosplinters.getUniqueToolPart(),
								new String[] {
										"FIG",
										"IHV",
										"GVV"
								},
								Pair.of('G', new OreIngredient("gunpowder")),
								Pair.of('F', new OreIngredient("ingotFusionite")),
								Pair.of('V', new OreIngredient("ingotValasium")),
								Pair.of('H', new TinkerPartIngredient(ModMaterials.materialElectarite, "tconstruct:arrow_head")),
								Pair.of('I', Ingredient.fromStacks(new ItemStack(Blocks.PACKED_ICE)))));
			}
		}
	}

	public static boolean canRegisterUniqueRecipe(UniqueMaterial umat) {
		if (umat == null) {
			return false;
		}
		if (umat.getUniqueToolPart().isEmpty()) {
			return false;
		}
		if (!TinkerRegistry.getAllMaterials().contains(umat)) {
			return false;
		}
		for (String s : ConfigHandler.removeUniqueToolpartRecipes) {
			if (s.equals(umat.getIdentifier())) {
				return false;
			}
		}
		return true;
	}

}
