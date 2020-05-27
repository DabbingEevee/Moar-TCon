package com.wordpress.craftminemods.tconmaterial.block.fluid;

import com.wordpress.craftminemods.tconmaterial.VersionInfo;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluid extends BlockFluidClassic{

	public BlockFluid(Fluid fluid, Material material) {
		super(fluid, material);
	}
	public FluidStateMapper getFluidStateMap() {
		return new FluidStateMapper(getFluid());
	}

	public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {
		private final ModelResourceLocation resource;

		public FluidStateMapper(Fluid fluid) {
			this.resource = new ModelResourceLocation(new ResourceLocation(VersionInfo.MODID,"blockstaaates/fluid_block"), fluid.getName());
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return resource;
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
			return resource;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
