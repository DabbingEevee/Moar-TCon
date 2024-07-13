package com.existingeevee.moretcon.materials;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.client.textures.LightShadingTextureColoredTexture;
import com.existingeevee.moretcon.client.textures.NoShadingTextureColoredTexture;
import com.existingeevee.moretcon.client.textures.OctineTexture;
import com.existingeevee.moretcon.client.textures.WhiteShadingTextureColoredTexture;
import com.existingeevee.moretcon.inits.ModMaterials;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo.MultiColor;
import slimeknights.tconstruct.library.materials.Material;

public class MaterialClient {

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
		setCustomRender(ModMaterials.materialRotiron);
		setCustomRender(ModMaterials.materialSwampSteel);
		setCustomRender(ModMaterials.materialPenguinite);
		setCustomRender(ModMaterials.materialShadowglass);
		setCustomRender(ModMaterials.materialEmberlight);
		setCustomRender(ModMaterials.materialBlightsteel);
		
		
		ModMaterials.materialTrichromadentium.setRenderInfo(new WhiteShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material" + ModMaterials.materialTrichromadentium.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));		
		ModMaterials.materialNahuatl.setRenderInfo(new MultiColor(0x100c1c, 0x271e3d, 0x49332e));
		ModMaterials.materialAmberwood.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material" + ModMaterials.materialAmberwood.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialSlimewood.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material" + ModMaterials.materialSlimewood.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialEtherstone.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material" + ModMaterials.materialEtherstone.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialSolsteel.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material" + ModMaterials.materialSolsteel.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialEbonite.setRenderInfo(new NoShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material" + ModMaterials.materialEbonite.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialIgniglomerate.setRenderInfo(new NoShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material" + ModMaterials.materialIgniglomerate.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialValkyrieMetal.setRenderInfo(new MultiColor(0xdba213, 0xeeffff, 0xeaee57));
		ModMaterials.materialOctine.setRenderInfo(new OctineTexture.RenderInfo());//new Metal(0xff8e3b, 0.7f, 2f, 0f));
		ModMaterials.materialAncientAlloy.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material" + ModMaterials.materialAncientAlloy.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
	}

	private static void setCustomRender(Material mat) {
		mat.setRenderInfo(MiscUtils.createMaterialRenderInfoSafe(mat));
	}

	public static void init() {
		//empty method to init class static block	
	} 
}
