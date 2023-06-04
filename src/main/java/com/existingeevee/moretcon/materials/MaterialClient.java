package com.existingeevee.moretcon.materials;

import com.existingeevee.moretcon.ModInfo;
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
		ModMaterials.materialIgniglomerate
				.setRenderInfo(new NoShadingTextureColoredTexture.Texture(new ResourceLocation(ModInfo.MODID
						+ ":other/material" + ModMaterials.materialIgniglomerate.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));

		// setCustomRender(Materials.materialOctine);

		// Materials.materialSpaceTimeDisruption.setRenderInfo(new AnimatedTexture(new
		// ResourceLocation(VersionInfo.MODID + ":other/" +
		// Materials.materialSpaceTimeDisruption.getIdentifier()).toString()));
		ModMaterials.materialValkyrieMetal.setRenderInfo(new MultiColor(0xdba213, 0xeeffff, 0xeaee57));
		ModMaterials.materialOctine.setRenderInfo(new Metal(0xff8e3b, 0.7f, 2f, 0f));
		// this is cool color
		// Materials.materialOctine.setRenderInfo(new MetalTextured(new
		// ResourceLocation(VersionInfo.MODID + ":other/" +
		// Materials.materialOctine.getIdentifier()), 0xfeb04d, 0.4f, 0.4f, 0.1f));
	}

	private static void setCustomRender(Material mat) {
		mat.setRenderInfo(Misc.createMaterialRenderInfoSafe(mat));
	}

	public static void init() {

	}
}
