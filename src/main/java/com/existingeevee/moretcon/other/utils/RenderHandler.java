package com.existingeevee.moretcon.other.utils;

import com.existingeevee.moretcon.inits.misc.ModSponges;
import com.existingeevee.moretcon.other.sponge.SpongeRegistry.GravitoniumSpongeItem;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class RenderHandler {
	public static void registerFluidCustomMeshesAndStates(Block blockIn) {
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(blockIn), new ItemMeshDefinition() {

			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation(blockIn.getRegistryName(), "fluid");
			}

		});

		ModelLoader.setCustomStateMapper(blockIn, new StateMapperBase() {

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(blockIn.getRegistryName(), "fluid");
			}

		});
	}

	public static void registerSponge(GravitoniumSpongeItem item) {
		for (int i=1; i < item.recipe.steps.length; i++) {
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(ModSponges.gravitoniumSponge.getRegistryName().getResourceDomain(), "partial" + ModSponges.gravitoniumSponge.getRegistryName().getResourcePath()), "inventory"));
		}
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(ModSponges.gravitoniumSponge.getRegistryName().getResourceDomain(), "complete" + ModSponges.gravitoniumSponge.getRegistryName().getResourcePath()), "inventory"));
	}
}
