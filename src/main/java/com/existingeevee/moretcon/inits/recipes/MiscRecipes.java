package com.existingeevee.moretcon.inits.recipes;

import com.existingeevee.moretcon.other.BreakApartBoltCoreRecipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent.Register;

public class MiscRecipes {

	public static void init(Register<IRecipe> event) {
		event.getRegistry().register(new BreakApartBoltCoreRecipe().setRegistryName("break_bolt_core"));
	}
}
