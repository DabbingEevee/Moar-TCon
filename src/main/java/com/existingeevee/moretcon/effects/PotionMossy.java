package com.existingeevee.moretcon.effects;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionMossy extends Potion {
	private final ResourceLocation potionIcon;

	public PotionMossy() {
		super(true, 0xfcf0fc);
		setRegistryName("mossy");
		setPotionName(MiscUtils.createNonConflictiveName("mossy"));
		potionIcon = new ResourceLocation(ModInfo.MODID + ":textures/other/mossy.png");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isBeneficial() {
		return true;
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
