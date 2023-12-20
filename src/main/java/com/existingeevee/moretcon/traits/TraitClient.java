package com.existingeevee.moretcon.traits;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.utils.RegisterHelper;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.modifiers.IModifier;

public class TraitClient {

	public static void init() {
		RegisterHelper.moreTConModifiers.forEach(TraitClient::register);
	}

	private static void register(IModifier mod) {
		ModelRegisterUtil.registerModifierModel(mod, new ResourceLocation(ModInfo.MODID + ":other/" + mod.getIdentifier().replaceFirst("." + ModInfo.MODID, "")));
	}
}
