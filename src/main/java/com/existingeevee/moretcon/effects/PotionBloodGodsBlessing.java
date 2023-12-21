package com.existingeevee.moretcon.effects;

import java.util.UUID;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.PotionAttackDamage;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionBloodGodsBlessing extends PotionAttackDamage {
	private final ResourceLocation potionIcon;

	public PotionBloodGodsBlessing() {
		super(false, 0xea8f8c, 2);
		this.setBeneficial();
		this.setRegistryName("bloodgodsblessing");
		this.setPotionName(MiscUtils.createNonConflictiveName("bloodgodsblessing"));
		this.registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, UUID.nameUUIDFromBytes("bloodgodsblessing".getBytes()).toString(), 2.0D, 0);
		potionIcon = new ResourceLocation(ModInfo.MODID + ":textures/other/bloodgodsblessing.png");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isBeneficial() {
		return true;
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
}
