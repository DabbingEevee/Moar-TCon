package com.wordpress.craftminemods.tconmaterial;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.apache.logging.log4j.Logger;

import com.wordpress.craftminemods.tconmaterial.block.ModBlocks;
import com.wordpress.craftminemods.tconmaterial.fluid.ModFluidBlocks;
import com.wordpress.craftminemods.tconmaterial.fluid.ModFluids;
import com.wordpress.craftminemods.tconmaterial.item.ModItems;
import com.wordpress.craftminemods.tconmaterial.other.OreDictionaryManager;
import com.wordpress.craftminemods.tconmaterial.other.SmeltingManager;
import com.wordpress.craftminemods.tconmaterial.other.Tabs;
import com.wordpress.craftminemods.tconmaterial.potion_effect.ModPotions;
import com.wordpress.craftminemods.tconmaterial.tconstuff.Materials;
import com.wordpress.craftminemods.tconmaterial.tconstuff.Traits;
import com.wordpress.craftminemods.tconmaterial.world.OreGen;
@Mod(modid = VersionInfo.MODID, name = VersionInfo.NAME, version = VersionInfo.VERSION, dependencies = VersionInfo.DEPENDANCY)
public class MoreMaterials
{
	static {
		FluidRegistry.enableUniversalBucket();
	}
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModFluids.init();
		ModFluidBlocks.init();
		Materials.init();
		ModPotions.init();
		//Traits.init();
		Tabs.init();
		ModItems.init();
		ModBlocks.init();
		GameRegistry.registerWorldGenerator(new OreGen(), 0);
		OreDictionaryManager.init();
		SmeltingManager.init();
		//instance().proxy.registerPreRendering();
		//instance().proxy.preInit(event);
	}
}
//TODO 
//ally of both trait