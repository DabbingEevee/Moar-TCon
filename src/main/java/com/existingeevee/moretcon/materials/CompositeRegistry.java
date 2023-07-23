package com.existingeevee.moretcon.materials;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.item.ItemCompositeRep;
import com.existingeevee.moretcon.other.utils.MaterialUtils;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

	public static void registerComposite(CompositeData compData) {
		data.add(compData);
	}

	public static void registerComposite(Supplier<Material> from, Supplier<Material> to, Supplier<Fluid> catalyst) {
		data.add(new CompositeData(from, to, catalyst, false));
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

	@SideOnly(Side.CLIENT)
	public static void updateCompositeRenderer() {
		//this will get registered later than other renderers. Add-ons will need to run this again after theyre done registering composites or it wont render right

		List<CompositeData> data = CompositeRegistry.getData();

		for (int i = 0; i < data.size(); i++) {
			ModelLoader.setCustomModelResourceLocation(ItemCompositeRep.getItemInstance(), i, new ModelResourceLocation(
					ModInfo.MODID + ":" + "repitem" + data.get(i).getResult().identifier, "inventory"));
		}
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
			MaterialUtils.forceSetRepItem(ItemCompositeRep.getItem(d.getResult()), d.getResult());
			d.getResult().setCastable(false);
			d.getResult().setCraftable(false);
		}
	}
	
	public static class CompositeData {

		private final boolean onlyOne;
		private final Supplier<Material> from;
		private final Supplier<Material> result;
		private final Supplier<Fluid> catalyst;

		private double multiplier = Material.VALUE_Ingot;
		private boolean genIcon = true;
		
		public CompositeData(Supplier<Material> from, Supplier<Material> result, Supplier<Fluid> catalyst) {
			this(from, result, catalyst, true);
		}

		public CompositeData(Supplier<Material> from, Supplier<Material> result, Supplier<Fluid> catalyst, boolean onlyOne) {
			this.onlyOne = onlyOne;
			this.from = from;
			this.result = result;
			this.catalyst = catalyst;
		}

		public boolean requiresOnlyOne() {
			return onlyOne;
		}

		public Material getFrom() {
			return from.get();
		}

		public Material getResult() {
			return result.get();
		}

		public Fluid getCatalyst() {
			return catalyst.get();
		}

		public double getMultiplier() {
			return multiplier;
		}
		
		public CompositeData setMultiplier(double multiplier) {
			this.multiplier = multiplier;
			return this;
		}
		
		public CompositeData setGenIcon(boolean genIcon) {
			this.genIcon = genIcon;
			return this;
		}

		public boolean shouldGenIcon() {
			return genIcon;
		}
	}
}
