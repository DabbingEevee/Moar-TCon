package com.existingeevee.moretcon.other.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.block.ISimpleBlockItemProvider;
import com.existingeevee.moretcon.block.ore.BlockOre;
import com.existingeevee.moretcon.block.ore.BlockOreMetal;
import com.existingeevee.moretcon.item.ItemBase;
import com.existingeevee.moretcon.item.ItemCompositeRep;
import com.existingeevee.moretcon.other.BiValue;
import com.existingeevee.moretcon.other.Misc;
import com.existingeevee.moretcon.other.sponge.SpongeRegistry.GravitoniumSpongeItem;
import com.google.common.base.Function;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;

public class RegisterHelper {

	public static List<MaterialIntegration> moreTConIntegrations = new ArrayList<MaterialIntegration>();

	public static List<ToolCore> moreTConTools = new ArrayList<ToolCore>();

	public static List<Modifier> moreTConModifiers = new ArrayList<Modifier>();

	public static List<BiValue<Block, ItemStack>> oreDrops = new ArrayList<>();

	public static void registerBlock(Block block) {
		RegisterHelper.registerBlock(block, block instanceof ISimpleBlockItemProvider ? b -> ((ISimpleBlockItemProvider) b).createBlockItem() : null);
	}

	public static void registerBlock(Block block, @Nullable Function<Block, ItemBlock> itemBlock) {
		String name = block.getUnlocalizedName().replaceFirst(("tile." + ModInfo.MODID + "."), "");// 5
		ForgeRegistries.BLOCKS.register(block.setRegistryName(name));

		if (itemBlock != null) {
			ForgeRegistries.ITEMS.register(itemBlock.apply(block).setRegistryName(block.getRegistryName()));
			if (Misc.isClient()) {
				registerBlockModel(block);
				if (block instanceof BlockFluidClassic) {
					RenderHandler.registerFluidCustomMeshesAndStates(block);
				}
			}
		}

		if (block instanceof BlockOre) {
			oreDrops.add(new BiValue<Block, ItemStack>(block, ((BlockOre) block).getOreDrop()));
		} else if (block instanceof BlockOreMetal) {
			oreDrops.add(new BiValue<Block, ItemStack>(block, ((BlockOreMetal) block).getOreDrop()));
		}
	}

	public static void registerTileEntity(Class<? extends TileEntity> tileClass) {
		GameRegistry.registerTileEntity(tileClass, new ResourceLocation(ModInfo.MODID, tileClass.getSimpleName()));
	}

	public static void registerItem(Item item) {
		String name = item.getUnlocalizedName().replaceFirst(("item." + ModInfo.MODID + "."), "");
		if (item instanceof ItemBase) {
			item.setCreativeTab(((ItemBase) item).getTab());
		}
		item.setRegistryName(name);
		ForgeRegistries.ITEMS.register(item);
		if (Misc.isClient()) {
			if (item instanceof ToolCore) {
				ModelRegisterUtil.registerToolModel((ToolCore) item);
				moreTConTools.add((ToolCore) item);
			} else if (item instanceof ToolPart) {
				ModelRegisterUtil.registerPartModel((ToolPart) item);
			} else if (item instanceof GravitoniumSpongeItem) {
				RenderHandler.registerSponge((GravitoniumSpongeItem) item);
			} else if (item instanceof ItemCompositeRep) {
				//Nothing here. it will be handled later
			} else {
				registerItemModel(item);
			}
		}
	}

	public static void registerBiome(Biome biome) {
		ForgeRegistries.BIOMES.register(biome);
	}

	public static boolean registerMaterial(Material material, Fluid fluid, String suffix, boolean bypassCheck) {
		// material.addTrait(Traits.modDebug);
		List<String> list = Arrays.asList(ConfigHandler.blacklist);
		if (!list.contains(material.getIdentifier()) || bypassCheck) {
			integrate(material, fluid, suffix);
			return true;
		}
		return false;

	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(
				ModInfo.MODID + ":" + item.getUnlocalizedName().replaceFirst(("item." + ModInfo.MODID + "."), ""), "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerBlockModel(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(
				ModInfo.MODID + ":" + block.getUnlocalizedName().replaceFirst(("tile." + ModInfo.MODID + "."), ""), "inventory"));
	}

	public static void registerModifier(IModifier modifier) {
		moreTConModifiers.add((Modifier) modifier);
	}

	/*
	 * public static void registerTrait(AbstractTrait trait) {
	 *
	 * }
	 */

	/*
	 * public static void registerFluid(Fluid fluid) { String name =
	 * fluid.getUnlocalizedName().substring(5); FluidRegistry.registerFluid(fluid);
	 * FluidRegistry.addBucketForFluid(fluid); Block fluidBlock = fluid.getBlock();
	 *
	 * if (fluidBlock != null) { Item fluidItem = Item.getItemFromBlock(fluidBlock);
	 * BlockFluid.FluidStateMapper mapper = new BlockFluid.FluidStateMapper(fluid);
	 *
	 * if (fluidItem != Items.AIR) ModelLoader.setCustomMeshDefinition(fluidItem,
	 * mapper);
	 *
	 * ModelLoader.setCustomStateMapper(fluidBlock, mapper);
	 *
	 * } }
	 */

	/*
	 * private static MaterialIntegration add(MaterialIntegration integration) {
	 * return TinkerRegistry.integrate(integration); }
	 */

	private static void integrate(Material material, Fluid fluid, String suffix) {
		// new MaterialIntegration(material, fluid, suffix).preInit();
		// new MaterialIntegration(material, fluid, suffix).integrate();
		moreTConIntegrations.add(new MaterialIntegration(material, fluid, suffix));
	}

	public static void registerFluid(Fluid fluid) {
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
	}

}
