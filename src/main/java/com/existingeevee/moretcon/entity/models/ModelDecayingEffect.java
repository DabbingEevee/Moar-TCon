package com.existingeevee.moretcon.entity.models;

import com.existingeevee.moretcon.entity.entities.EntityDecayingEffect;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelDecayingEffect extends ModelBase {
	private final ModelRenderer main;
	private final ModelRenderer mainr1;

	private final ModelRenderer reverse;
	private final ModelRenderer reverser1;

	public ModelDecayingEffect() {
		textureWidth = 96;
		textureHeight = 48;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 16.0F, 0.0F);

		mainr1 = new ModelRenderer(this);
		mainr1.setRotationPoint(0.0F, 0.0F, 0.0F);
		main.addChild(mainr1);
		setRotationAngle(mainr1, 0.0F, 3.1416F, 0.0F);
		mainr1.cubeList.add(new ModelBox(mainr1, -48, 0, -24.0F, -16.0F, -24.0F, 48, 0, 48, 0.0F, false));

		reverse = new ModelRenderer(this);
		reverse.setRotationPoint(0.0F, 0.0F, 0.0F);

		reverser1 = new ModelRenderer(this);
		reverser1.setRotationPoint(0.0F, 0.0F, 0.0F);
		reverse.addChild(reverser1);
		setRotationAngle(reverser1, 0.0F, 3.1416F, -3.1416F);
		reverser1.cubeList.add(new ModelBox(mainr1, -96, 0, -24.0F, 0.0F, -24.0F, 48, 0, 48, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableNormalize();
		GlStateManager.enableBlend();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 1.2 - ((EntityDecayingEffect) entity).getYTranslation(), 0);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		if (((EntityDecayingEffect) entity).isReversed()) {
			reverse.render(f5);
		} else {
			main.render(f5);
		}
		GlStateManager.popMatrix();
		GlStateManager.disableBlend();
		GlStateManager.disableNormalize();
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAngle, float entityTickTime, float rotationYaw, float rotationPitch, float unitPixel, Entity entity) {
		EntityDecayingEffect effect = (EntityDecayingEffect) entity;
		main.rotateAngleX = effect.rotationPitch * 0.017453292F;
		main.rotateAngleY = effect.rotationYaw * 0.017453292F;

		reverser1.rotateAngleX = effect.rotationPitch * 0.017453292F;
		reverser1.rotateAngleY = effect.rotationYaw * 0.017453292F;
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}