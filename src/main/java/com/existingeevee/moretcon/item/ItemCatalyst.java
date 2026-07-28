package com.existingeevee.moretcon.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;

import com.existingeevee.moretcon.other.MoreTConLogger;

import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.TinkerAPIException;
import slimeknights.tconstruct.library.events.TinkerRegisterEvent;
import slimeknights.tconstruct.library.smeltery.AlloyRecipe;

public class ItemCatalyst extends ItemBase {

	final List<AlloyRecipe> catalyzedRecipes = new ArrayList<>();

	public ItemCatalyst(String itemName) {
		super(itemName);
		commonSetup();
	}

	public ItemCatalyst(String itemName, int hex) {
		super(itemName, hex);
		commonSetup();
	}

	public ItemCatalyst(String itemName, GlowType type, int hex) {
		super(itemName, type, hex);
		commonSetup();
	}

	private void commonSetup() {
		this.setMaxStackSize(1);
	}

	public Collection<AlloyRecipe> getCatalyzedAlloys() {
		return catalyzedRecipes;
	}

	// TODO crafttweaker

	public void registerAlloy(FluidStack result, FluidStack... inputs) {
		if (result.amount < 1) {
			error("Alloy Recipe: Resulting alloy %s has to have an amount (%d)", result.getLocalizedName(), result.amount);
		}
		if (inputs.length < 2) {
			error("Alloy Recipe: Alloy for %s must consist of at least 2 liquids", result.getLocalizedName());
		}

		registerAlloy(new AlloyRecipe(result, inputs));
	}

	public void registerAlloy(AlloyRecipe recipe) {
		if (new CatalyzedAlloyRegisterEvent(recipe, this).fire()) {
			catalyzedRecipes.add(recipe);
			// waow
		} else {
			try {
				String input = recipe.getFluids().stream().map(FluidStack::getUnlocalizedName).collect(Collectors.joining(", "));
				String output = recipe.getResult().getUnlocalizedName();
				MoreTConLogger.log("Registration of alloy recipe for " + output + " from [" + input + "] has been cancelled by event", Level.DEBUG);
			} catch (Exception e) {
				MoreTConLogger.log("Error when logging alloy event", Level.ERROR);
				e.printStackTrace();
			}
		}
	}

	public void removeAlloy(FluidStack output, FluidStack[] input) {
		for (AlloyRecipe r : new ArrayList<>(this.catalyzedRecipes)) {
            if(r.getResult().isFluidEqual(output)) {
                if(input != null) {
                    FluidStack[] in = input;
                    List<FluidStack> rin = r.getFluids();
                    if(rin.size() == in.length) {
                        boolean valid = true;
                        for(int i = 0; i < in.length; i++) {
                            FluidStack stack = in[i];
                            FluidStack lStack = rin.get(i);
                            if(!lStack.isFluidEqual(stack)) {
                                valid = false;
                                
                            }
                        }
                        if(valid) {
                        	this.catalyzedRecipes.remove(r);
                        }
                    }
                } else {
                	this.catalyzedRecipes.remove(r);
                }     
            } //
		}
	}
	
	private static void error(String message, Object... params) {
		throw new TinkerAPIException(String.format(message, params));
	}

	public static class CatalyzedAlloyRegisterEvent extends TinkerRegisterEvent<AlloyRecipe> {
		public final ItemCatalyst catalyst;

		public CatalyzedAlloyRegisterEvent(AlloyRecipe recipe, ItemCatalyst catalyst) {
			super(recipe);
			this.catalyst = catalyst;
		}
	}
}
