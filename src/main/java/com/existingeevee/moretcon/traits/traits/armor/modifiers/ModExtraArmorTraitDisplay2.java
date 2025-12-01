package com.existingeevee.moretcon.traits.traits.armor.modifiers;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.existingeevee.moretcon.traits.modifiers.ModExtraTrait2;
import com.google.common.collect.ImmutableList;

import c4.conarm.common.ConstructsRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifierDisplay;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;

public class ModExtraArmorTraitDisplay2 extends Modifier implements IModifierDisplay {

	public ModExtraArmorTraitDisplay2() {
		super(ModExtraTrait2.EXTRA_TRAIT_IDENTIFIER + "_armor");
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		// we don touch dis :3
	}

	@Override
	public int getColor() {
		return 0xdddddd;
	}

	@Override
	public List<List<ItemStack>> getItems() {
		return ConstructsRegistry.chestplate.getRequiredComponents().stream()
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