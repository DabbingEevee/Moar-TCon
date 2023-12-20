package com.existingeevee.moretcon.traits;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.utils.RegisterHelper;
import com.existingeevee.moretcon.traits.modifiers.internal.ModExtraTraitDisplay2;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.modifiers.IModifier;

public class TraitClient {

	public static void init() {
		RegisterHelper.moreTConModifiers.forEach(TraitClient::register);
	}

	private static void register(IModifier mod) {
		if (mod instanceof ModExtraTraitDisplay2) {
			return; //nope
		}
		ResourceLocation rl = new ResourceLocation(ModInfo.MODID, "models/item/modifiers/" + mod.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""));
		System.out.println(rl);
		ModelRegisterUtil.registerModifierModel(mod, rl);
	}
}
