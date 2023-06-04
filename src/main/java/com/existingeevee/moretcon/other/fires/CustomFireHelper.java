package com.existingeevee.moretcon.other.fires;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.NetworkHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * 	if (e instanceof EntityPig) {
		setAblaze(entity, new CustomFireInfo(CustomFireEffect.FUSIONITE_FLAME, 2));
	}
 */

public class CustomFireHelper {

	private static Map<Integer, CustomFireInfo> customBurning = new HashMap<Integer, CustomFireInfo>();
	
	private static boolean dirty = false;

	public static void setAblaze(EntityLivingBase entity, CustomFireInfo info) {
		if (entity.getHealth() <= 0 || entity.isDead) {
			return;
		}
		customBurning.put(entity.getEntityId(), info);
		entity.extinguish();
		if (!entity.world.isRemote) {
			entity.getEntityData().setTag(ModInfo.MODID + ".fire", info.asTag());
			dirty = true;
		}
	}

	public static void setAblaze(EntityLivingBase entity, CustomFireEffect effect, int time) {
		setAblaze(entity, new CustomFireInfo(effect, time, false));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onRenderLivingEvent(RenderLivingEvent.Pre<?> event) {
		if (event.getEntity().world.isRemote) {
			if (customBurning.get(event.getEntity().getEntityId()) != null) {
				if (event.getEntity().getHealth() <= 0 || event.getEntity().isDead) {
					return;
				}
				CustomFireInfo info = customBurning.get(event.getEntity().getEntityId());
				if (info == null || info.isInvalid())
					return;
				info.effect.render(event);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onRenderTickEvent(RenderGameOverlayEvent e) {
		if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
			if (e.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
				if (customBurning.get(Minecraft.getMinecraft().player.getEntityId()) != null) {
					CustomFireInfo info = customBurning.get(Minecraft.getMinecraft().player.getEntityId());
					if (info.isInvalid())
						return;
					int posX = (e.getResolution().getScaledWidth()) / 2;
					int posY = (e.getResolution().getScaledHeight()) / 2;
					GlStateManager.disableDepth();
					GlStateManager.depthMask(false);
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
							GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
							GlStateManager.DestFactor.ZERO);
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					GlStateManager.disableAlpha();
					TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
					TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite(info.effect.one.toString());
					Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(-posX / 2, posY, textureatlassprite, (int) (posX * 1.25), posY);
					Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect((int) (posX / 2), (int) (posY * 0.75), textureatlassprite, (int) (posX * 1.25), (int) (posY * 1.5));
					Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect((int) (posX * 1.5), posY, textureatlassprite, (int) (posX * 1.25), posY);

//					textureatlassprite = texturemap.getAtlasSprite(info.effect.two.toString());
//					Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//					Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(0, (int) (posY * 0.75), textureatlassprite, posX * 2, posY);

					GlStateManager.depthMask(true);
					GlStateManager.enableDepth();
					GlStateManager.enableAlpha();
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					
				}
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onClientTickEvent(TickEvent.ClientTickEvent event) {
		if (Minecraft.getMinecraft().world != null && event.phase == Phase.END)
			for (Entity e : new ArrayList<>(Minecraft.getMinecraft().world.loadedEntityList)) {
				if (e instanceof EntityLivingBase) {
					EntityLivingBase entity = (EntityLivingBase) e;
					if (customBurning.get(entity.getEntityId()) != null) {
						if (entity.getHealth() <= 0 || entity.isDead) {
							continue;
						}
						CustomFireInfo info = customBurning.get(entity.getEntityId());
						if (info == null || info.effect == null)
							continue;
						if (!info.effect.effect(entity)) {
							info.forcedInvalid = true;
						}
					}
				}
			}

	}

	@SubscribeEvent
	public static void onRespawn(PlayerRespawnEvent event) {
		if (!event.isEndConquered() && customBurning.get(event.player.getEntityId()) != null) {
			event.player.getEntityData().removeTag(ModInfo.MODID + ".fire");
			customBurning.remove(event.player.getEntityId());
			dirty = true;
		}
	}

	@SubscribeEvent
	public static void onEntityOof(LivingDeathEvent event) {
		if (customBurning.get(event.getEntity().getEntityId()) != null) {
			event.getEntity().getEntityData().removeTag(ModInfo.MODID + ".fire");
			customBurning.remove(event.getEntity().getEntityId());
			dirty = true;
		}
	}

	@SubscribeEvent
	public static void onEntityTick(TickEvent.WorldTickEvent event) {
		for (Entity e : new ArrayList<>(event.world.loadedEntityList)) {
			if (e instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase) e;
				if (customBurning.get(entity.getEntityId()) != null) {
					if (entity.getHealth() <= 0 || entity.isDead) {
						continue;
					}
					CustomFireInfo info = customBurning.get(entity.getEntityId());
					if (info.effect == null)
						continue;
					if (!info.effect.effect(entity)) {
						info.forcedInvalid = true;
					}
				}
			}
		}

		if (event.world.isRemote)
			return;

		// customBurning = new HashMap<Integer, CustomFireInfo>();

		for (Entity e : new ArrayList<>(event.world.loadedEntityList)) {
			if (e instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase) e;
				if (e.getEntityData().hasKey(ModInfo.MODID + ".fire", NBT.TAG_COMPOUND)) {
					try {
						CustomFireInfo info = new CustomFireInfo(
								e.getEntityData().getCompoundTag(ModInfo.MODID + ".fire")).decrementTime();
						if (entity.isBurning() || info.isInvalid() || (customBurning.get(entity.getEntityId()) != null && customBurning.get(entity.getEntityId()).isInvalid()) || entity.getHealth() <= 0 || entity.isDead) {
							customBurning.remove(entity.getEntityId());
							entity.getEntityData().removeTag(ModInfo.MODID + ".fire");
							dirty = true;
							continue;
						}
						setAblaze(entity, info);
						dirty = true;
					} catch (NullPointerException npe) {

					}
				}
			}
		}
		if (dirty) {
			dirty = false;
			SyncCustomFiresMessage msg = new SyncCustomFiresMessage();
			msg.customBurningData = customBurning;
			NetworkHandler.HANDLER.sendToAll(msg);
			// Logging.log("pointa");
		}
	}

	public static CustomFireInfo getBurningInfo(EntityLivingBase entity) {
		if (entity.getHealth() <= 0 || entity.isDead) {
			return null;
		}
		if (customBurning.get(entity.getEntityId()) != null) {
			return customBurning.get(entity.getEntityId());
		}
		if (entity.getEntityData().hasKey(ModInfo.MODID + ".fire", NBT.TAG_COMPOUND) && !entity.world.isRemote) {
			NBTTagCompound data = entity.getEntityData().getCompoundTag(ModInfo.MODID + ".fire");
			String fireEffect = data.getString("type");
			int time = data.getInteger("time");
			if (time > 0 && CustomFireEffect.registeredEffects.get(fireEffect) != null) {
				setAblaze(entity, CustomFireEffect.registeredEffects.get(fireEffect), time);
				return customBurning.get(entity.getEntityId());
			}
		}
		return null;
	}

	public static class SyncCustomFiresMessage implements IMessage, IMessageHandler<SyncCustomFiresMessage, IMessage> {
		Map<Integer, CustomFireInfo> customBurningData = new HashMap<Integer, CustomFireInfo>();

		@Override
		public IMessage onMessage(SyncCustomFiresMessage message, MessageContext ctx) {
			customBurning = message.customBurningData;
			// Logging.log("c -> " + customBurning);
			return null; 
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			Map<Integer, CustomFireInfo> newCustomBurningData = new HashMap<Integer, CustomFireInfo>();
			try {
				int len = buf.readInt();
				for (int i = 0; i < len; i++) {
					int eid = buf.readInt();
					int time = buf.readInt();
					int strLen = buf.readByte();
					String effect = new StringBuilder(strLen)
							.append(buf.readCharSequence(strLen, StandardCharsets.UTF_8)).toString();
					if (CustomFireEffect.registeredEffects.get(effect) != null) {
						newCustomBurningData.put(eid,
								new CustomFireInfo(CustomFireEffect.registeredEffects.get(effect), time, false));
					}
				}
			} catch (Throwable t) {
				return;
			}
			customBurningData = newCustomBurningData;
		}

		@Override
		public void toBytes(ByteBuf buf) {
			int len = customBurningData.entrySet().stream().filter(p -> !p.getValue().isInvalid()).mapToInt(i -> 1)
					.sum();
			buf.writeInt(len);
			for (Entry<Integer, CustomFireInfo> e : customBurningData.entrySet()) {
				if (e.getValue().isInvalid())
					continue;
				buf.writeInt(e.getKey());
				buf.writeInt(e.getValue().time);
				buf.writeByte(e.getValue().getEffect().id.length());
				buf.writeCharSequence(e.getValue().getEffect().id, StandardCharsets.UTF_8);
			}
		}

	}
}
