package com.existingeevee.moretcon.compat.jei;

import java.util.ArrayList;
import java.util.List;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.inits.ModFluids;
import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.other.utils.CompatManager;

import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IIngredientType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class JeiInit {

	public static final List<JeiCustomContainer> CUSTOM = new ArrayList<>();

	public static void init(IModRegistry registry) {
		CUSTOM.clear();
		
		IIngredientType<FluidStack> fluidstack = registry.getIngredientRegistry().getIngredientType(FluidStack.class);
		IIngredientType<ItemStack> itemstack = registry.getIngredientRegistry().getIngredientType(ItemStack.class);

		CUSTOM.add(new JeiHideBadToolparts());
		CUSTOM.add(new JeiAddBoltCoreBreakingContainer());

		CUSTOM.add(new JeiSupportDIRecipes());
		
		if (CompatManager.thebetweenlands) {
			CUSTOM.add(new JeiInformationContainer<>(fluidstack, new FluidStack(ModFluids.liquidMummySludge, 1000), "melt_mummy", () -> CompatManager.thebetweenlands));
		}
		if (CompatManager.loadMain) {
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreFusionite, 1), "orefusionite", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreBedrockFusionite, 1), "orefusionitebedrock", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreIrradium, 1), "oreirradium", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreVoidSpar, 1), "orevoidspar", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreNaturalVoidSpar, 1), "orevoidsparbedrock", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreEnderal, 1), "oreenderal", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreGallium, 1), "oregallium", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreEchostone, 1), "oreechostone", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreGarstone, 1), "oregarstone", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreGravitonium, 1), "oregravitonium", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreGravitoniumDense, 1), "oregravitonium", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreIgniglomerate, 1), "oreigniglomerate", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreBloodstone, 1), "orebloodstone", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreEbonite, 1), "oreebonite", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreErythynite, 1), "oreerythynite", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreEtherstone, 1), "oreetherstone", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreElectarite, 1), "oreelectarite", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreHallowsite, 1), "orehallowsite", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreZracohlium, 1), "orezracohlium", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreMonolite, 1), "oremonolite", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.orePerimidum, 1), "oreperimidum", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreGeodesium, 1), "oregeodesium", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreAnthracite, 1), "oreanthracite", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreIonstone, 1), "oreionstone", () -> CompatManager.loadMain));
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreGalliumEthereal, 1), "oregalliumethereal", () -> CompatManager.loadMain));

			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModItems.solidLightning, 1), "solid_lightning", () -> CompatManager.loadMain));
		}
		if (CompatManager.aether_legacy) {
			CUSTOM.add(new JeiInformationContainer<>(itemstack, new ItemStack(ModBlocks.oreArkenium, 1), "orearkenium", () -> CompatManager.aether_legacy));
		}
	}

}
