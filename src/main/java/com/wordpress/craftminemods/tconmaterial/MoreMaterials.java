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
import com.wordpress.craftminemods.tconmaterial.block.fluid.Fluids;
import com.wordpress.craftminemods.tconmaterial.item.ModItems;
import com.wordpress.craftminemods.tconmaterial.other.OreDictionaryManager;
import com.wordpress.craftminemods.tconmaterial.other.SmeltingManager;
import com.wordpress.craftminemods.tconmaterial.other.Tabs;
import com.wordpress.craftminemods.tconmaterial.tconstuff.Materials;
import com.wordpress.craftminemods.tconmaterial.world.OreGen;
@Mod(modid = VersionInfo.MODID, name = VersionInfo.NAME, version = VersionInfo.VERSION, dependencies = VersionInfo.DEPENDANCY)
public class MoreMaterials
{
	static {
		FluidRegistry.enableUniversalBucket();
	}
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Materials.init();
		Tabs.init();
		ModItems.init();
		ModBlocks.init();
		Fluids.init();
		GameRegistry.registerWorldGenerator(new OreGen(), 0);
		OreDictionaryManager.init();
		SmeltingManager.init();
		//instance().proxy.registerPreRendering();
		//instance().proxy.preInit(event);
	}
}
