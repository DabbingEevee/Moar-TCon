package com.existingeevee.moretcon.compat.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.existingeevee.moretcon.other.BreakApartBoltCoreRecipe;
import com.google.common.collect.Lists;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.plugin.jei.JEIPlugin;
import slimeknights.tconstruct.shared.block.BlockTable;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.common.item.ItemBlockTable;
import slimeknights.tconstruct.tools.ranged.item.BoltCore;

public class JeiAddBoltCoreBreakingContainer extends JeiCustomContainer {

	public JeiAddBoltCoreBreakingContainer() {
		super(null, () -> true);
	}

	@Override
	public void onRun(IModRegistry r) {
		r.handleRecipes(BreakApartBoltCoreRecipe.class, rc -> new Wrapper(r.getJeiHelpers()), VanillaRecipeCategoryUid.CRAFTING);
	}

	public static class Wrapper implements IRecipeWrapper, IShapedCraftingRecipeWrapper, ICustomCraftingRecipeWrapper {

		private final IJeiHelpers jeiHelpers;

		public Wrapper(IJeiHelpers jeiHelpers) {
			this.jeiHelpers = jeiHelpers;
		}

		@Override
		public int getHeight() {
			return 1;
		}

		@Override
		public int getWidth() {
			return 1;
		}

		@Override
		public void getIngredients(IIngredients arg0) {
			IStackHelper stackHelper = jeiHelpers.getStackHelper();

			List<ItemStack> listInput = new ArrayList<>();
			List<ItemStack> listOutput = new ArrayList<>();
			for (Material m : TinkerRegistry.getAllMaterials()) {
				if (!m.hasStats(MaterialTypes.SHAFT))
					continue;

				listInput.add(BoltCore.getItemstackWithMaterials(m, TinkerMaterials.iron));
				listOutput.add(TinkerTools.arrowShaft.getItemstackWithMaterial(m));
			}

			arg0.setInputLists(VanillaTypes.ITEM, stackHelper.expandRecipeItemStackInputs(Lists.newArrayList(Ingredient.fromStacks(listInput.toArray(new ItemStack[0])))));
			arg0.setOutputLists(VanillaTypes.ITEM, stackHelper.expandRecipeItemStackInputs(Lists.newArrayList(Ingredient.fromStacks(listOutput.toArray(new ItemStack[0])))));
		}

		@Override
		@SuppressWarnings("deprecation")
		public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients) {
		    List<ItemStack> inputs = ingredients.getInputs(ItemStack.class).get(0);
		    List<ItemStack> outputs = ingredients.getOutputs(ItemStack.class).get(0);
			
			IFocus<?> ifocus = recipeLayout.getFocus();
			if (ifocus == null) {
				recipeLayout.getItemStacks().set(0, outputs);
				recipeLayout.getItemStacks().set(5, inputs);
				return;
			}
			Object focusObj = ifocus.getValue();

			if (focusObj instanceof ItemStack) {
				ItemStack focus = ((ItemStack) focusObj).copy();
				focus.setCount(1);
				IFocus.Mode mode = ifocus.getMode();
				
				if (mode == IFocus.Mode.INPUT) {
					recipeLayout.getItemStacks().set(5, focus);
					recipeLayout.getItemStacks().set(0, TinkerTools.arrowShaft.getItemstackWithMaterial(TinkerTools.boltCore.getMaterial(focus)));
				} else if (mode == IFocus.Mode.OUTPUT) {
					recipeLayout.getItemStacks().set(0, focus);
					recipeLayout.getItemStacks().set(5, BoltCore.getItemstackWithMaterials(TinkerTools.arrowShaft.getMaterial(focus), TinkerMaterials.iron));
				}
			}
		}
	}
}
