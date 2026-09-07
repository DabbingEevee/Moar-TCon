package com.existingeevee.moretcon.other.slotrender;

import java.util.HashMap;
import java.util.Map;

import com.existingeevee.moretcon.item.ItemShakeRender;
import com.existingeevee.moretcon.other.ICustomSlotRenderer;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.MaterialItem;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class ShakeTicRender {

	public static final Map<Material, MaterialShakeData> MATERIAL_SHAKE = new HashMap<>();

	public static final ICustomSlotRenderer RENDERER_PART = new ICustomSlotRenderer() {

		@Override
		@SideOnly(Side.CLIENT)
		public void render(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
			GlStateManager.pushMatrix();

			MaterialShakeData msd = calculateShakePart(stack);
			if (msd == null)
				return;
			
			GlStateManager.translate(x + 8, y + 8, 0);
			double d = (stack.hashCode() + Minecraft.getSystemTime()) / 1000d;
			double m = Math.sin(d) * Math.sin(d) * msd.pulseStr;
			GlStateManager.scale(msd.pulseMin + m, msd.pulseMin + m, 1);
			if (msd.spinSpeed != 0)
				GlStateManager.rotate((float) d * msd.spinSpeed, 0, 0, 1);
			GlStateManager.translate(MiscUtils.randomN1T1() * msd.shakeIntensity, MiscUtils.randomN1T1() * msd.shakeIntensity, 0);
			GlStateManager.translate(-(x + 8), -(y + 8), 0);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void postRender(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
			GlStateManager.popMatrix();
		}
	};

	public static boolean isPart(ItemStack stack) {
		return stack.getItem() instanceof MaterialItem && MATERIAL_SHAKE.containsKey(((MaterialItem) stack.getItem()).getMaterial(stack));
	}

	public static boolean isTool(ItemStack stack) {
		if (MiscUtils.getEmbossments(stack).stream().anyMatch(MATERIAL_SHAKE.keySet()::contains)) {
			return true;
		}
		if (TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(stack)).stream().anyMatch(MATERIAL_SHAKE.keySet()::contains)) {
			return true;
		}
		return false;
	}

	public static class MaterialShakeData {

		public MaterialShakeData(ItemShakeRender item) {
			this.shakeIntensity = item.getShakeIntensity();
			this.pulseMin = item.getPulseMin();
			this.pulseStr = item.getPulseStr();
			this.spinSpeed = item.getSpinSpeed();
		}
		
		public MaterialShakeData(float shakeIntensity, float pulseMin, float pulseStr, float spinSpeed) {
			this.shakeIntensity = shakeIntensity;
			this.pulseMin = pulseMin;
			this.pulseStr = pulseStr;
			this.spinSpeed = spinSpeed;
		}

		protected float shakeIntensity = 0.4f;
		protected float pulseMin = 0.9f;
		protected float pulseStr = 0.2f;
		protected float spinSpeed = 0;

		public float getShakeIntensity() {
			return shakeIntensity;
		}

		public float getPulseMin() {
			return pulseMin;
		}

		public float getPulseStr() {
			return pulseStr;
		}

		public float getSpinSpeed() {
			return spinSpeed;
		}

		public MaterialShakeData setShakeIntensity(float i) {
			this.shakeIntensity = i;
			return this;
		}
		
		public MaterialShakeData setPulseMin(float i) {
			this.pulseMin = i;
			return this;
		}
		
		public MaterialShakeData setPulseSpeed(float i) {
			this.pulseStr = i;
			return this;
		}
		
		public MaterialShakeData setSpinSpeed(float i) {
			this.spinSpeed = i;
			return this;
		}
	}
	
	public static MaterialShakeData calculateShakePart(ItemStack stack) {
		if (stack.getItem() instanceof MaterialItem) {
			Material m = ((MaterialItem) stack.getItem()).getMaterial(stack);
			return MATERIAL_SHAKE.get(m);
		}
		return null;
	}
}
