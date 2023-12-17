package com.existingeevee.moretcon.traits.modifiers.internal;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.existingeevee.moretcon.traits.modifiers.ModExtraTrait2;
import com.google.common.collect.ImmutableList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifierDisplay;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.tools.harvest.TinkerHarvestTools;

/** A custom clientside modifier to handle the loading and displaying of the different fortify modifiers */
public class ModExtraTraitDisplay2 extends Modifier implements IModifierDisplay {

	public ModExtraTraitDisplay2() {
		super(ModExtraTrait2.EXTRA_TRAIT_IDENTIFIER);
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		// dummy
	}

	@Override
	public int getColor() {
		return 0xdddddd;
	}

	@Override
	public List<List<ItemStack>> getItems() {
		return TinkerHarvestTools.pickaxe.getRequiredComponents().stream()
				.map(PartMaterialType::getPossibleParts)
				.flatMap(Collection::stream)
				.map(this::getItems)
				.collect(Collectors.toList());
	}

	private List<ItemStack> getItems(IToolPart part) {
		List<Material> possibleMaterials = TinkerRegistry.getAllMaterials().stream()
				.filter(part::canUseMaterial)
				.collect(Collectors.toList());
		Material material = possibleMaterials.get(new Random().nextInt(possibleMaterials.size()));

		return ImmutableList.<ItemStack>builder()
				.add(part.getItemstackWithMaterial(material))
				.addAll(ModExtraTrait2.EMBOSSMENT_ITEMS)
				.build();
	}
}
