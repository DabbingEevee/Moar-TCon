package com.existingeevee.moretcon.compat;

import java.util.ArrayList;
import java.util.List;

import c4.conarm.lib.materials.ArmorMaterialType;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

/** 
 * 
 * @author ExistingEevee
 *
 * @apiNote Moving all conarm api calls to a separate class for it to not fuck itself with class-not-founds
 */

public class ConarmSupport {

	public static List<ITrait> getBasicArmorTraits(boolean basic, Material material) {
		List<ITrait> traits = new ArrayList<>();
		try {
			traits.addAll(material.getAllTraitsForStats(basic ? ArmorMaterialType.CORE : ArmorMaterialType.PLATES));
		} catch (Throwable e) {}
		return traits;
	}
}
