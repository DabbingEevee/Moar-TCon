package com.existingeevee.moretcon.effects;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thebetweenlands.api.item.CorrosionHelper;
import thebetweenlands.api.item.ICorrodible;

public class PotionCorosiveBlast extends Potion {
		private final ResourceLocation potionIcon;

		public PotionCorosiveBlast() {
			super(false, -1);
			setRegistryName("corrosiveblast");
			setPotionName(MiscUtils.createNonConflictiveName("corrosiveblast"));
			potionIcon = new ResourceLocation("tconmaterial:textures/other/coldflames.png");
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
		public void applyAttributesModifiersToEntity(EntityLivingBase entity, AbstractAttributeMap attributeMapIn, int amplifier) {
		}

		@Override
		public void performEffect(EntityLivingBase entity, int amplifier) {
			ItemStack stack = entity.getHeldItemMainhand();

			if(!stack.isEmpty() && stack.getItem() instanceof ICorrodible && CorrosionHelper.isCorrosionEnabled()) {

				if(!stack.isEmpty() && stack.getItem() instanceof ICorrodible) {
					ICorrodible corrodible = (ICorrodible) stack.getItem();
					int corrosion = corrodible.getCorrosion(stack);
					if(!CorrosionHelper.isCorrosionEnabled()) {
						if(corrosion != 0) {
							corrodible.setCorrosion(stack, 0);
						}
					} else if (corrosion < corrodible.getMaxCorrosion(stack)) {
						int coating = corrodible.getCoating(stack);
						if(coating > 0) {
							if (coating - amplifier - 1 > 0) {
								corrodible.setCoating(stack, coating - amplifier - 1);
							} else {
								corrodible.setCoating(stack, 0);
							}
						} else {
							if (corrosion + amplifier + 1< corrodible.getMaxCorrosion(stack)) {
								corrodible.setCorrosion(stack, corrosion + amplifier + 1);
							} else {
								corrodible.setCorrosion(stack, corrodible.getMaxCorrosion(stack));
							}
						}
					}
				}

				/*ICorrodible corrodible = (ICorrodible) stack.getItem();

				int corrosion = corrodible.getCorrosion(stack);
				if (corrosion < corrodible.getMaxCorrosion(stack)) {
					if (corrosion + amplifier < corrodible.getMaxCorrosion(stack)) {
						corrodible.setCorrosion(stack, corrosion + amplifier);
					} else {
						corrodible.setCorrosion(stack, corrodible.getMaxCorrosion(stack));
					}
				}*/
			}

		}

		@Override
		public void removeAttributesModifiersFromEntity(EntityLivingBase entity, AbstractAttributeMap attributeMapIn, int amplifier) {
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
