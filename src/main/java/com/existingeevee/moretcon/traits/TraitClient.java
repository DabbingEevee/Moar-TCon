package com.existingeevee.moretcon.traits;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.MoreTConLogger;
import com.existingeevee.moretcon.other.utils.CompatManager;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class TraitClient {

	public static void init() {
		if (CompatManager.thebetweenlands) {
			register(ModTraits.modRedGem);
			register(ModTraits.modGreenGem);
			register(ModTraits.modBlueGem);
			register(ModTraits.modShocking);
			register(ModTraits.modValonite);
			register(ModTraits.modTarred);
			register(ModTraits.modBetweenified);
			
			register(TinkerModifiers.modFiery);
			register(TinkerModifiers.modLuck);
			register(TinkerModifiers.modEmerald);
			register(TinkerModifiers.modHaste);
			register(TinkerModifiers.modReinforced);
			register(TinkerModifiers.modDiamond);
			register(TinkerModifiers.modSharpness);
			register(TinkerModifiers.modNecrotic);
			register(TinkerModifiers.modBeheading);
			register(TinkerModifiers.modMendingMoss);
			register(TinkerModifiers.modGlowing);
			register(TinkerModifiers.modSilktouch);
			register(TinkerModifiers.modBaneOfArthopods);
			register(TinkerModifiers.modSmite);
			register(TinkerModifiers.modShulking);
			register(TinkerModifiers.modKnockback);
			register(TinkerModifiers.modWebbed);
			register(TinkerModifiers.modSoulbound);
			register(TinkerModifiers.modBlasting);
		}
		if (CompatManager.loadMain) {
			register(ModTraits.modCrushing);
		}
	}

	private static void register(IModifier mod) {
		if (mod == null) {
			MoreTConLogger.log("Null modifier detected. Oops");
			return;
		}
		ResourceLocation rl = new ResourceLocation(ModInfo.MODID, "models/item/modifiers/" + mod.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""));
		ModelRegisterUtil.registerModifierModel(mod, rl);
	}
}
