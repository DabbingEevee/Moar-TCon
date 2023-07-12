package com.existingeevee.moretcon.materials;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.existingeevee.moretcon.inits.ModMaterials;
import com.existingeevee.moretcon.item.ItemCompositeRep;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.tools.ranged.item.BoltCore;

public class CompositeRegistry {

	private static List<CompositeData> data = new ArrayList<>();

	public static List<CompositeData> getData() {
		return new ArrayList<>(data);
	}

	public static void registerComposite(Material from, Material to, Fluid catalyst) {
		registerComposite(from, to, catalyst, true);
	}

	public static void registerComposite(Material from, Material to, Fluid catalyst, boolean onlyOne) {
		data.add(new CompositeData(from, to, catalyst, onlyOne));
	}

	public static Optional<CompositeData> getComposite(Material mat) {
		return data.stream().filter(d -> d.getResult().equals(mat)).findFirst();
	}
	
	public static Optional<Integer> getCompositeIndex(Material mat) {		
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getResult().equals(mat)) {
				return Optional.of(i);
			}
		}
		
		return Optional.empty();
	}

	//DO NOT CALL
	public static void onPostInit() {
		for (CompositeData d : data) {
			for (IToolPart t : TinkerRegistry.getToolParts()) {
				if (!t.canUseMaterial(d.getFrom()) || !t.canUseMaterial(d.getResult())) {
					continue;
				}
				
				if (t instanceof BoltCore) {
					List<Material> fluidWithHead = TinkerRegistry.getAllMaterials().stream()
							.filter(mat -> mat.hasStats(MaterialTypes.HEAD))
							.filter(mat -> mat.hasFluid())
							.collect(Collectors.toList());
										
					for (Material m : fluidWithHead) {
						RecipeMatch rm = RecipeMatch.ofNBT(BoltCore.getItemstackWithMaterials(d.getFrom(), m));
						ItemStack output = BoltCore.getItemstackWithMaterials(d.getResult(), m);
			
						TinkerRegistry.registerTableCasting(new CastingRecipe(output, rm, d.getCatalyst(), d.onlyOne ? Material.VALUE_Ingot : t.getCost(), true, false));
					}
					continue;
				}
				
				ItemStack output = t.getItemstackWithMaterial(d.getResult());
				
				if (output == null || output.isEmpty()) {
					continue;
				}
				
				RecipeMatch rm = RecipeMatch.ofNBT(t.getItemstackWithMaterial(d.getFrom()));
				TinkerRegistry.registerTableCasting(new CastingRecipe(output, rm, d.getCatalyst(), d.onlyOne ? Material.VALUE_Ingot : t.getCost(), true, false));
			}
			ModMaterials.forceSetRepItem(ItemCompositeRep.getItem(d.getResult()), d.getResult());
		}
	}
	
	public static class CompositeData {

		private final boolean onlyOne;
		private final Material from;
		private final Material result;
		private final Fluid catalyst;

		public CompositeData(Material from, Material result, Fluid catalyst) {
			this(from, result, catalyst, true);
		}

		public CompositeData(Material from, Material result, Fluid catalyst, boolean onlyOne) {
			this.onlyOne = onlyOne;
			this.from = from;
			this.result = result;
			this.catalyst = catalyst;
		}

		public boolean requiresOnlyOne() {
			return onlyOne;
		}

		public Material getFrom() {
			return from;
		}

		public Material getResult() {
			return result;
		}

		public Fluid getCatalyst() {
			return catalyst;
		}

	}
}
