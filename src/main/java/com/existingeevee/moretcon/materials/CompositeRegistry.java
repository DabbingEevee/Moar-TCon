package com.existingeevee.moretcon.materials;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.existingeevee.moretcon.other.BiValue;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.tools.IToolPart;

public class CompositeRegistry {

	private static Map<BiValue<String,String>,BiValue<Boolean, Material>> compositeMap = new HashMap<>();
	
	public static void registerComposite(Material from, Material to, Fluid catalyst) {
		registerComposite(from, to, catalyst, true);
	}
	public static void registerComposite(Material from, Material to, Fluid catalyst, boolean onlyOne) {
		BiValue<String, String> compIdentifier = new BiValue<String,String>(from.identifier, catalyst.getName());
		if (compositeMap.get(compIdentifier) != null) return;
		compositeMap.put(compIdentifier, new BiValue<Boolean,Material>(onlyOne, to));
	}

	public static boolean isComposite(Material mat) {
		return compositeMap.entrySet().stream().anyMatch(i -> i.getValue().getB().equals(mat));
	}
	
	public static Pair<Fluid,Material> compositeDesc(Material mat) {
		if (!isComposite(mat)) return null;
		Optional<Entry<BiValue<String, String>, BiValue<Boolean, Material>>> op = compositeMap.entrySet().stream().filter(i -> i.getValue().getB().equals(mat)).findFirst();
		if (op.isPresent())
			return Pair.of(FluidRegistry.getFluid(op.get().getKey().getB()), TinkerRegistry.getMaterial(op.get().getKey().getA()));
					/*I18n.format("text.can_be_composited.name") + 
					I18n.format(FluidRegistry.getFluid(op.get().getKey().getB()).getUnlocalizedName()) + 
					I18n.format("text.as_well_as.name") + 
					TinkerRegistry.getMaterial(op.get().getKey().getA()).getLocalizedName();*/
		return null;
	}
	
	//DO NOT CALL
	public static void onPostInit() { 
		for (Entry<BiValue<String, String>, BiValue<Boolean, Material>> e : compositeMap.entrySet()) {
			for (IToolPart t : TinkerRegistry.getToolParts()) {
				if (t.canUseMaterial(e.getValue().getB()) && t.canUseMaterial(TinkerRegistry.getMaterial(e.getKey().getA()))) {
				    RecipeMatch rm = null;
				    if(t.getItemstackWithMaterial(TinkerRegistry.getMaterial(e.getKey().getA())) != ItemStack.EMPTY) {
				      rm = RecipeMatch.ofNBT(t.getItemstackWithMaterial(TinkerRegistry.getMaterial(e.getKey().getA())));
				    }
				    TinkerRegistry.registerTableCasting(new CastingRecipe(t.getItemstackWithMaterial(e.getValue().getB()), rm, FluidRegistry.getFluid(e.getKey().getB()), e.getValue().getA() ? Material.VALUE_Ingot : t.getCost(), true, false));
				}
			}
		}
	}
}
