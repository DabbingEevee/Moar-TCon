package com.existingeevee.moretcon.inits.recipes;

import java.util.Arrays;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.inits.ModMaterials;
import com.existingeevee.moretcon.materials.UniqueMaterial;
import com.existingeevee.moretcon.other.TinkerPartIngredient;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.ConfigHandler;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.oredict.OreIngredient;
import slimeknights.tconstruct.tools.TinkerMaterials;

public class Other {

	// @ObjectHolder(value = "tconstruct:")
	// public static final Item blood = null;

	public static void init(Register<IRecipe> event) {
		if (ConfigHandler.shouldAllowMainContent) {
			if (
					ModMaterials.materialPlasma != null && 
					ModMaterials.materialPlasma.getUniqueToolPart() != null && 
					!Arrays.asList(ConfigHandler.removeUniqueToolpartRecipes).contains(ModMaterials.materialPlasma.getIdentifier()))
				event.getRegistry().register(new ShapedRecipes(ModInfo.MODID, 3, 3,
						NonNullList.from(Ingredient.EMPTY, 
								new OreIngredient("ingotIron"),
								new OreIngredient("blockGarstone"), 
								new OreIngredient("ingotIron"),
								new OreIngredient("gemGarstone"), 
								new OreIngredient("dustPrismarine"),
								new OreIngredient("gemGarstone"), 
								new OreIngredient("ingotIron"),
								Ingredient.fromStacks(new ItemStack(Blocks.BEACON)), 
								new OreIngredient("ingotIron")),
						ModMaterials.materialPlasma.getUniqueToolPart()).setRegistryName("plasma_recipe"));

			if (ModMaterials.materialTrailblazer != null && ModMaterials.materialTrailblazer.getUniqueToolPart() != null
					&& !Arrays.asList(ConfigHandler.removeUniqueToolpartRecipes).contains(ModMaterials.materialTrailblazer.getIdentifier())) {
				ItemStack fireWoodKnifeBlade = UniqueMaterial
						.getToolPartFromResourceLocation(new ResourceLocation("tconstruct", "knife_blade"))
						.getItemstackWithMaterial(TinkerMaterials.firewood);
				///Logging.log("???");
				event.getRegistry().register(new ShapedRecipes(ModInfo.MODID, 3, 3,
						NonNullList.from(
								Ingredient.EMPTY, 
								
								Ingredient.EMPTY, 
								new OreIngredient("blockSolarsteel"), 
								new OreIngredient("blockFusionite"), 
								new TinkerPartIngredient(TinkerMaterials.firewood, fireWoodKnifeBlade.copy()), 
								//Ingredient.EMPTY, 
								new TinkerPartIngredient(ModMaterials.materialPlasma, ModMaterials.materialPlasma.getUniqueToolPart()), 
								new OreIngredient("blockSolarsteel"), 
								Ingredient.fromStacks(new ItemStack(Blocks.BEACON)),
								new TinkerPartIngredient(TinkerMaterials.firewood, fireWoodKnifeBlade.copy()),
								Ingredient.EMPTY),
						ModMaterials.materialTrailblazer.getUniqueToolPart()).setRegistryName("trailblazer_recipe"));
			}
			if (ModMaterials.materialTriblade != null && ModMaterials.materialTriblade.getUniqueToolPart() != null
					&& !Arrays.asList(ConfigHandler.removeUniqueToolpartRecipes)
							.contains(ModMaterials.materialTriblade.getIdentifier()))
				event.getRegistry().register(new ShapedRecipes(ModInfo.MODID, 3, 3,
						NonNullList.from(Ingredient.EMPTY, Ingredient.fromStacks(new ItemStack(Items.SKULL, 1, 1)),
								Ingredient.EMPTY, Ingredient.fromStacks(new ItemStack(Items.SKULL, 1, 1)),
								new OreIngredient("gemVoidSpar"), new OreIngredient("boneWithered"), Ingredient.EMPTY,
								new OreIngredient("boneWithered"), new OreIngredient("gemVoidSpar"),
								Ingredient.fromStacks(new ItemStack(Items.SKULL, 1, 1))),
						ModMaterials.materialTriblade.getUniqueToolPart()).setRegistryName("triblade_recipe"));

			if (ModMaterials.materialMirrored != null && ModMaterials.materialMirrored.getUniqueToolPart() != null
					&& !Arrays.asList(ConfigHandler.removeUniqueToolpartRecipes)
							.contains(ModMaterials.materialMirrored.getIdentifier())) {
				ItemStack featherFletching = UniqueMaterial
						.getToolPartFromResourceLocation(new ResourceLocation("tconstruct", "fletching"))
						.getItemstackWithMaterial(TinkerMaterials.feather);
				event.getRegistry().register(new ShapedRecipes(ModInfo.MODID, 3, 3,
						NonNullList.from(Ingredient.EMPTY, Ingredient.EMPTY, new OreIngredient("ingotGallium"),
								Ingredient.EMPTY, new OreIngredient("ingotGallium"),
								new TinkerPartIngredient(TinkerMaterials.feather, featherFletching.copy()),
								new OreIngredient("ingotGallium"), Ingredient.EMPTY, new OreIngredient("ingotGallium"),
								Ingredient.EMPTY),
						ModMaterials.materialMirrored.getUniqueToolPart()).setRegistryName("mirrored_recipe"));
			}

			if (ModMaterials.materialTechnoblade != null && ModMaterials.materialTechnoblade.getUniqueToolPart() != null
					&& !Arrays.asList(ConfigHandler.removeUniqueToolpartRecipes)
							.contains(ModMaterials.materialTechnoblade.getIdentifier())) {
				ItemStack pigironBlade = UniqueMaterial
						.getToolPartFromResourceLocation(new ResourceLocation("tconstruct", "sword_blade"))
						.getItemstackWithMaterial(TinkerMaterials.pigiron);
				event.getRegistry().register(new ShapedRecipes(ModInfo.MODID, 3, 3,
						NonNullList.from(Ingredient.EMPTY, Ingredient.EMPTY, new OreIngredient("gemGarstone"),
								Ingredient.fromItem(Items.NETHER_STAR), new OreIngredient("ingotBrickNether"),
								new TinkerPartIngredient(TinkerMaterials.pigiron, pigironBlade.copy()),
								new OreIngredient("gemGarstone"), new OreIngredient("blockDiamond"),
								new OreIngredient("ingotBrickNether"), Ingredient.EMPTY),
						ModMaterials.materialTechnoblade.getUniqueToolPart()).setRegistryName("techno_recipe"));
			}
		}
		if (ModMaterials.materialCrimson != null && ModMaterials.materialCrimson.getUniqueToolPart() != null
				&& !Arrays.asList(ConfigHandler.removeUniqueToolpartRecipes)
						.contains(ModMaterials.materialCrimson.getIdentifier())
				&& CompatManager.plustic) {
			ItemStack bone = UniqueMaterial
					.getToolPartFromResourceLocation(new ResourceLocation("tconstruct", "tough_tool_rod"))
					.getItemstackWithMaterial(TinkerMaterials.bone);
			ItemStack bloodstone = UniqueMaterial
					.getToolPartFromResourceLocation(new ResourceLocation("tconstruct", "tough_tool_rod"))
					.getItemstackWithMaterial(ModMaterials.materialBloodstone);
			event.getRegistry().register(new ShapedRecipes(ModInfo.MODID, 3, 3,
					NonNullList.from(Ingredient.EMPTY,
							new OreIngredient("ingotGallium"),
							new OreIngredient("slimeballBlood"),
							new OreIngredient("gemGarstone"),
							new OreIngredient("slimeballBlood"),
							new TinkerPartIngredient(ModMaterials.materialBloodstone, bloodstone.copy()),
							new OreIngredient("slimeballBlood"),
							new TinkerPartIngredient(TinkerMaterials.bone, bone.copy()),
							new OreIngredient("slimeballBlood"),
							new OreIngredient("ingotGallium")),
					ModMaterials.materialCrimson.getUniqueToolPart()).setRegistryName("crimson_recipe"));
		}
	}

}
