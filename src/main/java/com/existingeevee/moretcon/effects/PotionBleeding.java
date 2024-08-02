package com.existingeevee.moretcon.effects;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.NetworkHandler;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionBleeding extends Potion {
	private final ResourceLocation potionIcon;

	public PotionBleeding() {
		super(true, 0xff0000);
		setRegistryName("bleeding");
		setPotionName(MiscUtils.createNonConflictiveName("bleeding"));
		potionIcon = new ResourceLocation(ModInfo.MODID + ":textures/other/bleeding.png");
	}

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
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if (entity.world.isRemote) {
			return;
		}
		amplifier++;
		int duration = entity.getActivePotionEffect(this).getDuration();
		int cnt = entity.getEntityData().getInteger(ModInfo.MODID + ".bleeding.timer");
		if (duration >= 400) {
			cnt++;
		}
		if (duration >= 300) {
			cnt++;
		}
		if (duration >= 250) {
			cnt++;
		}
		if (duration >= 200) {
			cnt++;
		}
		if (duration >= 170) {
			cnt++;
		}
		if (duration >= 150) {
			cnt++;
		}
		if (duration >= 110) {
			cnt++;
		}
		if (duration >= 90) {
			cnt++;
		}
		if (duration >= 50) {
			cnt++;
		}
		if (duration >= 10) {
			cnt++;
		}
		if (duration >= 1) {
			cnt++;
		}

		if (cnt >= 60) {
			cnt = 0;
			int resTime = entity.hurtResistantTime;
			entity.hurtResistantTime = 0;
			entity.attackEntityFrom(new DamageSource("bleeding"), amplifier);
			NetworkHandler.HANDLER.sendToDimension(new BleedingEffectMessage(entity.posX, entity.posY, entity.posZ), entity.dimension);
			entity.hurtResistantTime = resTime;
			// public void spawnParticle(EnumParticleTypes.BLOCK_DUST, entity.posX,
			// entity.posY, entity.posZ, 25, double xOffset, double yOffset, double zOffset,
			// double particleSpeed, int... particleArguments)

		}

		entity.getEntityData().setInteger("moretcon.bleeding.timer", cnt);

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

	public static class BleedingEffectMessage implements IMessage, IMessageHandler<BleedingEffectMessage, IMessage> {

		double x;
		double y;
		double z;

		public BleedingEffectMessage() {
			this(0, 0, 0);
		}

		public BleedingEffectMessage(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public IMessage onMessage(BleedingEffectMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {

				for (int i = 0; i < 25; i++) {
					Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.BLOCK_DUST, message.x, message.y + Math.random() * 2,
							message.z, (Math.random() - 0.5) * 0.2, (Math.random() - 0.5) * 0.4, (Math.random() - 0.5) * 0.2,
							Block.getStateId(Blocks.REDSTONE_BLOCK.getDefaultState()));
				}

				for (int i = 0; i < 30; i++) {
					Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.FALLING_DUST,
							message.x + (Math.random() - 0.5) / 2, message.y + Math.random() * 2, message.z + (Math.random() - 0.5) / 2,
							(Math.random() - 0.5) * 0.2, (Math.random() - 0.5) * 0.4, (Math.random() - 0.5) * 0.2,
							Block.getStateId(
									Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.RED)));
				}

				for (int i = 0; i < 10; i++) {
					Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.REDSTONE, message.x, message.y + Math.random() * 2,
							message.z, 0, 0, 0);
				}
			});
			return null;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			x = buf.readDouble();
			y = buf.readDouble();
			z = buf.readDouble();
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeDouble(x);
			buf.writeDouble(y);
			buf.writeDouble(z);
		}
	}
}
