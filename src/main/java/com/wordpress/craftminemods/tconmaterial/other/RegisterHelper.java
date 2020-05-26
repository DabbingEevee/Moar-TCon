package com.wordpress.craftminemods.tconmaterial.other;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.wordpress.craftminemods.tconmaterial.VersionInfo;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

public class RegisterHelper {

	public static void registerBlock(Block block) {
		RegisterHelper.registerBlock(block, ItemBlock::new);
	}

	public static void registerBlock(Block block, @Nullable Function<Block, ItemBlock> itemBlock) {
		String name = block.getUnlocalizedName().substring(5);
		ForgeRegistries.BLOCKS.register(block.setRegistryName(name));

		if (itemBlock != null) {
			ForgeRegistries.ITEMS.register(itemBlock.apply(block).setRegistryName(block.getRegistryName()));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
					new ModelResourceLocation(VersionInfo.MODID + ":" + name, "inventory"));

		}

	}

	public static void registerItem(Item item) {
		String name = item.getUnlocalizedName().substring(5);
		item.setRegistryName(name);
		ForgeRegistries.ITEMS.register(item);
		ModelLoader.setCustomModelResourceLocation(item, 0,
				new ModelResourceLocation(VersionInfo.MODID + ":" + name, "inventory"));

	}

	public static void registerBiome(Biome biome) {
		ForgeRegistries.BIOMES.register(biome);
	}

	public static void registerMaterial(Material material, Fluid fluid) {
		if (fluid != null) {
			integrate(material, fluid);
		} else {
			TinkerRegistry.addMaterial(material);
		}
	}

	public static void registerFluid(Fluid fluid) {
		String name = fluid.getUnlocalizedName().substring(5);
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		ModelBakery.registerItemVariants(Item.getItemFromBlock(fluid.getBlock()));
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(fluid.getBlock()), new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation("tconstruct:fluid_block", name.replace(".", ""));
			}
		});

		
	}

	private static MaterialIntegration add(MaterialIntegration integration) {
		return TinkerRegistry.integrate(integration);
	}

	private static MaterialIntegration integrate(Material material, Fluid fluid) {
		return add(new MaterialIntegration(material, fluid));
	}

}
