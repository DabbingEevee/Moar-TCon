package com.existingeevee.moretcon.other.utils;

import com.existingeevee.moretcon.ModInfo;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RenderHandler {
	
	@SideOnly(Side.CLIENT)
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

	@SideOnly(Side.CLIENT)
	public static void registerSponge(GravitoniumSpongeItem item) {
		ResourceLocation gsLocation = ModSponges.gravitoniumSponge.getRegistryName();
		
		for (int i = 2; i <= item.recipe.steps.length; i++) {
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(gsLocation.getResourceDomain(), "partial" + gsLocation.getResourcePath()), "inventory"));
		}
		ModelLoader.setCustomModelResourceLocation(item, 1, new ModelResourceLocation(new ResourceLocation(gsLocation.getResourceDomain(), gsLocation.getResourcePath()), "inventory"));
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(gsLocation.getResourceDomain(), "complete" + gsLocation.getResourcePath()), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item) {
		ModelResourceLocation model = new ModelResourceLocation(ModInfo.MODID + ":" + item.getUnlocalizedName().replaceFirst(("item." + ModInfo.MODID + "."), ""), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, 0, model);
	}

	@SideOnly(Side.CLIENT)
	public static void registerBlockModel(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(
				ModInfo.MODID + ":" + block.getUnlocalizedName().replaceFirst(("tile." + ModInfo.MODID + "."), ""), "inventory"));
	}

}
