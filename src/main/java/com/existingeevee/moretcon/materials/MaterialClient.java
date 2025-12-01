package com.existingeevee.moretcon.materials;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.client.textures.GlintTexture;
import com.existingeevee.moretcon.client.textures.LightShadingTextureColoredTexture;
import com.existingeevee.moretcon.client.textures.NoShadingTextureColoredTexture;
import com.existingeevee.moretcon.client.textures.WhiteShadingTextureColoredTexture;
import com.existingeevee.moretcon.client.textures.unique.GeodesiumTexture;
import com.existingeevee.moretcon.client.textures.unique.PerimidumTexture;
import com.existingeevee.moretcon.client.textures.unique.SanguiseeliumTexture;
import com.existingeevee.moretcon.client.textures.unique.ValasiumTexture;
import com.existingeevee.moretcon.inits.ModMaterials;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.Material;

public class MaterialClient {
	
	private static void setCustomRender(Material mat) {
		mat.setRenderInfo(MiscUtils.createMaterialRenderInfoSafe(mat));
	}

	public static void init() {
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
				
		ModMaterials.materialTrichromadentium.setRenderInfo(new WhiteShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialTrichromadentium.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialNahuatl.setRenderInfo(new MaterialRenderInfo.MultiColor(0x100c1c, 0x271e3d, 0x49332e));
		ModMaterials.materialAmberwood.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialAmberwood.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialSlimewood.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialSlimewood.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialEtherstone.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialEtherstone.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialRuneSteel.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialRuneSteel.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialSolsteel.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialSolsteel.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialBrinkstone.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialBrinkstone.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialAnthracite.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialAnthracite.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialIonstone.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialIonstone.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialVacuuite.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialVacuuite.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialEbonite.setRenderInfo(new NoShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialEbonite.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialIgniglomerate.setRenderInfo(new NoShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialIgniglomerate.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialValkyrieMetal.setRenderInfo(new MaterialRenderInfo.MultiColor(0xdba213, 0xeeffff, 0xeaee57));
		ModMaterials.materialValonite.setRenderInfo(new GlintTexture.RenderInfo(0x906390, 0x563856, 0xf2ecf2, 0xd8b8d8, 0xc390c3, 0xc390c3));
		ModMaterials.materialOctine.setRenderInfo(new GlintTexture.RenderInfo(0xff8906, 0xd3550c, 0xf7f7f7, 0xffc81f, 0xf8a100, 0xff8906));
		ModMaterials.materialAncientAlloy.setRenderInfo(new LightShadingTextureColoredTexture.RenderInfo(new ResourceLocation(ModInfo.MODID + ":other/material/material" + ModMaterials.materialAncientAlloy.getIdentifier().replaceFirst(ModInfo.MODID + ".", ""))));
		ModMaterials.materialAtronium.setRenderInfo(new GlintTexture.RenderInfo(0x4e4031, 0x2f261c, 0xf0ceac, 0xf6be86, 0xd99857, 0xd99857));
		ModMaterials.materialValasium.setRenderInfo(new GlintTexture.RenderInfo(0x646681, 0x646681, 0xb3c3cb, 0x959cb3, 0x959cb3, 0x959cb3));
		ModMaterials.materialSanguiseelium.setRenderInfo(new SanguiseeliumTexture.RenderInfo());
		ModMaterials.materialSearedStone.setRenderInfo(new MaterialRenderInfo.Default(0x4f4f4f));
		ModMaterials.materialIoximite.setRenderInfo(new GlintTexture.RenderInfo(0x725ee3, 0x674fe1, 0xd7d3fc, 0xb1aaf1, 0x978cea, 0x8374e6));
		ModMaterials.materialBloodstone.setRenderInfo(new GlintTexture.RenderInfo(0x1c0000, 0x1c0000, 0xffadad, 0x8e0000, 0x6b0000, 0x4f0101));
		ModMaterials.materialValasium.setRenderInfo(new ValasiumTexture.RenderInfo());
		ModMaterials.materialPerimidum.setRenderInfo(new PerimidumTexture.RenderInfo());
		ModMaterials.materialGeodesium.setRenderInfo(new GeodesiumTexture.RenderInfo());
		
		
	
		
	}
}
