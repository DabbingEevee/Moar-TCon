package com.existingeevee.moretcon.other.utils;

import com.existingeevee.moretcon.ModInfo;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.client.MaterialRenderInfo.BlockTexture;
import slimeknights.tconstruct.library.materials.Material;

public class ClientUtils {
	public static BlockTexture createMaterialRenderInfo(Material material) {
		return new MaterialRenderInfo.BlockTexture(new ResourceLocation(ModInfo.MODID + ":other/material" + material.getIdentifier().replaceFirst(ModInfo.MODID + ".", "")));
	}
}
