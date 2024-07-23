package com.existingeevee.moretcon.reforges;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import slimeknights.mantle.util.TagHelper;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;

public class ReforgeHelper {

	public static final String REFORGE_KEY = "moretcon.reforge";

	@Nullable
	public static AbstractReforge getReforge(ItemStack stack) {
		return getReforge(TagHelper.getTagSafe(stack));
	}

	@Nullable
	public static AbstractReforge getReforge(NBTTagCompound rootCompound) {
		String key = rootCompound.getString(REFORGE_KEY);
		ITrait trait = TinkerRegistry.getTrait(key);
		if (trait instanceof AbstractReforge) {
			return (AbstractReforge) trait;
		}

		return null;
	}

	public static void removeReforge(ItemStack stack) {
		removeReforge(TagHelper.getTagSafe(stack));
	}

	public static void removeReforge(NBTTagCompound rootCompound) {
		Set<AbstractReforge> reforges = new HashSet<>(); // Should only be one, buuuut it never hurts to be too careful

		NBTTagList traitsTagList = TagUtil.getTraitsTagList(rootCompound);
		boolean modifiedTraits = false;
		for (int i = 0; i < traitsTagList.tagCount(); i++) {
			ITrait trait = TinkerRegistry.getTrait(traitsTagList.getStringTagAt(i));
			if (trait instanceof AbstractReforge) {
				reforges.add((AbstractReforge) trait);
				traitsTagList.removeTag(i--);
				modifiedTraits = true;
			}
		}
		if (modifiedTraits) {
			TagUtil.setTraitsTagList(rootCompound, traitsTagList);
		}

		NBTTagList modifierTagList = TagUtil.getModifiersTagList(rootCompound);
		boolean modifiedModifiers = false;
		for (int i = 0; i < modifierTagList.tagCount(); i++) {
			ModifierNBT data = ModifierNBT.readTag(modifierTagList.getCompoundTagAt(i));
			IModifier modifier = TinkerRegistry.getModifier(data.identifier);
			if (modifier instanceof AbstractReforge) {
				reforges.add((AbstractReforge) modifier);
				modifierTagList.removeTag(i--);
				modifiedModifiers = true;
			}
		}
		if (modifiedModifiers) {
			TagUtil.setModifiersTagList(rootCompound, modifierTagList);
		}

		NBTTagList baseModifiersTagList = TagUtil.getBaseModifiersTagList(rootCompound);
		boolean modifiedBaseModifiers = false;
		for (int i = 0; i < modifierTagList.tagCount(); i++) {
			NBTBase tag = baseModifiersTagList.get(i);
			if (!(tag instanceof NBTTagString))
				continue;
			String identifier = ((NBTTagString) tag).getString();
			IModifier modifier = TinkerRegistry.getModifier(identifier);
			if (modifier instanceof AbstractReforge) {
				reforges.add((AbstractReforge) modifier);
				baseModifiersTagList.removeTag(i--);
				modifiedBaseModifiers = true;
			}
		}
		if (modifiedBaseModifiers) {
			TagUtil.setBaseModifiersTagList(rootCompound, baseModifiersTagList);
		}

		rootCompound.removeTag(REFORGE_KEY);

		for (AbstractReforge r : reforges) {
			r.cleanUp(rootCompound);
		}
	}
}
