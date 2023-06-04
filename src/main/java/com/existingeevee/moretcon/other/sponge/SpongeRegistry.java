package com.existingeevee.moretcon.other.sponge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.existingeevee.moretcon.inits.misc.ModSponges;
import com.existingeevee.moretcon.inits.misc.OreDictionaryManager;
import com.existingeevee.moretcon.item.ItemBase;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;

@SuppressWarnings("deprecation")
public class SpongeRegistry {
	private SpongeRegistry() {
	}

	//private static final SpongeRegistry instance = new SpongeRegistry();

	private static Map<String, SpongeRecipe> recipes = new HashMap<String, SpongeRecipe>();

	public static void postInit() {
		for (Entry<String, SpongeRecipe> s : recipes.entrySet()) {
			OreDictionaryManager.registerOre(s.getValue().resultOreDict, s.getValue().result);
			GameRegistry.addSmelting(new ItemStack(s.getValue().result, 1), s.getValue().smeltResult.copy(), 0F);
			int i = 0;
			for (SpongeStep s2 : s.getValue().steps) {
				ItemStack input = new ItemStack(ModSponges.gravitoniumSponge);
				ItemStack output = new ItemStack(s.getValue().result);
				if (i != 0) input = new ItemStack(s.getValue().result, 1, i);
			    if(i + 1 != s.getValue().steps.length) output = new ItemStack(s.getValue().result, 1, i + 1);
				TinkerRegistry.registerBasinCasting(new CastingRecipe(output, RecipeMatch.of(input, 1), new FluidStack(FluidRegistry.getFluid(s2.fluidName), s2.fluidAmount), 200, true, true));
			    i++;
			}
		}
	}



	public static GravitoniumSpongeItem getSponge(SpongeRecipe sponge) {
		return new GravitoniumSpongeItem(sponge);
	}

	public static SpongeRecipe createSpongeRecipe(String recipeName, String resultOreDict, ItemStack smeltResult,
			SpongeStep... steps) {
		return new SpongeRecipe(recipeName, resultOreDict, smeltResult, steps);
	}

	public static SpongeStep createSpongeStep(String fluid, int amount) {
		return new SpongeStep(fluid, amount);
	}

	public static SpongeStep createSpongeStep(Fluid fluid, int amount) {
		return new SpongeStep(fluid, amount);
	}

	public static class SpongeRecipe {
		public SpongeStep[] steps;
		private String resultOreDict;
		private ItemStack smeltResult;
		private String recipeName;
		private GravitoniumSpongeItem result;

		public SpongeRecipe(String recipeName, String resultOreDict, ItemStack smeltResult, SpongeStep... steps) {
			this.resultOreDict = resultOreDict;
			this.smeltResult = smeltResult;
			this.steps = steps;
			this.recipeName = recipeName;
		}

		public ItemStack getSmeltResult() {
			return smeltResult;
		}

		public void setSmeltResult(ItemStack smeltResult) {
			this.smeltResult = smeltResult;
		}

		public String getResultOreDict() {
			return resultOreDict;
		}

		public void setResultOreDict(String resultOreDict) {
			this.resultOreDict = resultOreDict;
		}

		public int getStepAmount() {
			return steps.length;
		}

		public ArrayList<SpongeStep> getSteps() {
			ArrayList<SpongeStep> steps = new ArrayList<SpongeStep>();
			steps.addAll(steps);
			return steps;
		}
	}

	public static class SpongeStep {
		private String fluidName;
		private int fluidAmount;

		public SpongeStep(String fluid, int amount) {
			fluidName = fluid;
			fluidAmount = amount;
		}

		public SpongeStep(Fluid fluid, int amount) {
			fluidName = fluid.getName();
			fluidAmount = amount;
		}
	}

	public static class GravitoniumSpongeItem extends ItemBase {

		public SpongeRecipe recipe;

		private GravitoniumSpongeItem(SpongeRecipe recipe) {
			super("item" + recipe.recipeName + "sponge");
			this.recipe = recipe;
			this.recipe.result = this;
			this.setHasSubtypes(true);
			this.setMaxDamage(0);
			recipes.put(recipe.recipeName, recipe);
		}

		public SpongeRecipe getRecipe() {
			return recipe;
		}

		@Override
		public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
			if (this.isInCreativeTab(tab)) {
				items.add(new ItemStack(this, 1, 0));
			}
		}
		//for (int i = 1; i < this.recipe.steps.length; i++) {
		//	  items.add(new ItemStack(this, 1, i));
		//}
		@Override
		public String getItemStackDisplayName(ItemStack stack) {
			if (stack.getMetadata() == 0)
				return I18n.translateToLocal("spongestatus.completed.name") + " " + I18n.translateToLocal("spongerecipe." + recipe.recipeName + ".name") +  " " + I18n.translateToLocal(ModSponges.gravitoniumSponge.getUnlocalizedName() + ".name");
			return I18n.translateToLocal("spongestatus.partial.name") + " (" + I18n.translateToLocal("sponge.step.name") + " " + stack.getMetadata() + ") " + I18n.translateToLocal("spongerecipe." + recipe.recipeName + ".name") +  " " + I18n.translateToLocal(ModSponges.gravitoniumSponge.getUnlocalizedName() + ".name");
		}

		public CreativeTabs getTab() {
			return tab;
		}
	}
}
