package com.existingeevee.moretcon.effects;

import java.lang.reflect.Method;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.fires.CustomFireEffect;
import com.existingeevee.moretcon.other.fires.CustomFireHelper;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionColdFlames extends Potion {
	private final ResourceLocation potionIcon;

	static final Method setFlag = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70052_a", void.class,
			int.class, boolean.class);// EntityLivingBase.class.getDeclaredFields()[24];

	// func_70052_a
	public PotionColdFlames() {
		super(true, -1);
		setRegistryName("coldflames");
		setPotionName(MiscUtils.createNonConflictiveName("coldflames"));
		potionIcon = new ResourceLocation(ModInfo.MODID + ":textures/other/coldflames.png");
		//MinecraftForge.EVENT_BUS.register(this);
	}

	//@SubscribeEvent

	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isBeneficial() {
		return false;
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public boolean shouldRenderInvText(PotionEffect effect) {
		return true;
	}

	@Override
	public boolean shouldRenderHUD(PotionEffect effect) {
		return true;
	}

	@Override
	public void applyAttributesModifiersToEntity(EntityLivingBase entity, AbstractAttributeMap attributeMapIn,
			int amplifier) {
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		CustomFireHelper.setAblaze(entity, CustomFireEffect.COLD_FLAMES, 10);
		
		//entity.setInWeb();
		//entity.setFire(1);
		// RenderLivingEvent
		// net.minecraft.client.renderer.entity.Render.renderEntityOnFire()
	}

	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entity, AbstractAttributeMap attributeMapIn,
			int amplifier) {
		super.removeAttributesModifiersFromEntity(entity, attributeMapIn, amplifier);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		if (mc.currentScreen != null) {
			mc.getTextureManager().bindTexture(potionIcon);
			Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		mc.getTextureManager().bindTexture(potionIcon);
		Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
}
