package com.existingeevee.moretcon.item;

import com.existingeevee.moretcon.other.ICustomSlotRenderer;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShakeRender extends ItemBase implements ICustomSlotRenderer {

	protected float shakeIntensity = 0.4f;
	protected float pulseMin = 0.9f;
	protected float pulseStr = 0.2f;
	protected float spinSpeed = 0; 
	
	public ItemShakeRender(String itemName, GlowType type, int hex) {
		super(itemName, type, hex);
	}

	public ItemShakeRender(String itemName, int hex) {
		super(itemName, hex);
	}

	public ItemShakeRender(String itemName) {
		super(itemName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRender(ItemStack stack) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void render(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
		if (super.shouldRender(stack))
			super.render(stack, x, y, bakedmodel);		
				
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 8, y + 8, 0);
		double d = (stack.hashCode() + Minecraft.getSystemTime()) / 1000d;
		double m = Math.sin(d) * Math.sin(d) * pulseStr;
		GlStateManager.scale(pulseMin + m, pulseMin + m, 1);
		if (spinSpeed != 0)
			GlStateManager.rotate((float) d * spinSpeed, 0, 0, 1);
		GlStateManager.translate(MiscUtils.randomN1T1() * shakeIntensity, MiscUtils.randomN1T1() * shakeIntensity, 0);
		GlStateManager.translate(-(x + 8), -(y + 8), 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void postRender(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
		GlStateManager.popMatrix();
	}

	public ItemShakeRender withShakeIntensity(float shakeIntensity) {
		this.shakeIntensity = shakeIntensity;
		return this;
	}

	public ItemShakeRender withPulseMin(float pulseMin) {
		this.pulseMin = pulseMin;
		return this;
	}

	public ItemShakeRender withPulseStr(float pulseStr) {
		this.pulseStr = pulseStr;
		return this;
	}

	public ItemShakeRender withSpinSpeed(float spinSpeed) {
		this.spinSpeed = spinSpeed;
		return this;
	}
}
