package com.existingeevee.moretcon.materials;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.client.LightShadingTextureColoredTexture;
import com.existingeevee.moretcon.client.NoShadingTextureColoredTexture;
import com.existingeevee.moretcon.inits.ModMaterials;
import com.existingeevee.moretcon.other.Misc;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo.Metal;
import slimeknights.tconstruct.library.client.MaterialRenderInfo.MultiColor;
import slimeknights.tconstruct.library.materials.Material;

public class MaterialClient {

	// s@SideOnly(Side.CLIENT)
	static {
		setCustomRender(ModMaterials.materialFusionite);
		setCustomRender(ModMaterials.materialIrradium);
		setCustomRender(ModMaterials.materialEnderexamite);
		setCustomRender(ModMaterials.materialFerroherb);
		setCustomRender(ModMaterials.materialEnderal);
		setCustomRender(ModMaterials.materialSpaceTimeDisruption);
		setCustomRender(ModMaterials.materialIronwood);
		setCustomRender(ModMaterials.materialSlimyBone);
		setCustomRender(ModMaterials.materialReedRope);
		setCustomRender(ModMaterials.materialAnglerTooth);
		setCustomRender(ModMaterials.materialWeedwood);
		setCustomRender(ModMaterials.materialDragonFlyWing);
		setCustomRender(ModMaterials.materialRuneSteel);
		setCustomRender(ModMaterials.materialArkenium);
		setCustomRender(ModMaterials.materialSkyroot);
		setCustomRender(ModMaterials.materialZanite);
		setCustomRender(ModMaterials.materialGravitite);
		setCustomRender(ModMaterials.materialGravitonium);
		setCustomRender(ModMaterials.materialTrichromadentium);
		setCustomRender(ModMaterials.materialRotiron);
		setCustomRender(ModMaterials.materialSwampSteel);
		setCustomRender(ModMaterials.materialPenguinite);
		setCustomRender(ModMaterials.materialShadowglass);
		ModMaterials.materialEtherstone
		.setRenderInfo(new LightShadingTextureColoredTexture.Texture(new ResourceLocation(ModInfo.MODID
				+ ":other/material" + ModMaterials.materialEtherstone.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));

		ModMaterials.materialEbonite.setRenderInfo(new NoShadingTextureColoredTexture.Texture(new ResourceLocation(ModInfo.MODID
				+ ":other/material" + ModMaterials.materialEbonite.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialIgniglomerate
				.setRenderInfo(new NoShadingTextureColoredTexture.Texture(new ResourceLocation(ModInfo.MODID
						+ ":other/material" + ModMaterials.materialIgniglomerate.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialValkyrieMetal.setRenderInfo(new MultiColor(0xdba213, 0xeeffff, 0xeaee57));
		ModMaterials.materialOctine.setRenderInfo(new Metal(0xff8e3b, 0.7f, 2f, 0f));
	}

	private static void setCustomRender(Material mat) {
		mat.setRenderInfo(Misc.createMaterialRenderInfoSafe(mat));
	}

	public static void init() {

	}
}
