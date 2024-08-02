package com.existingeevee.moretcon.other.fires;

import java.util.HashMap;
import java.util.Map;

import com.existingeevee.moretcon.ModInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomFireEffect {

	public static void init() {
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onTexturesStitch(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(one);
		event.getMap().registerSprite(two);
	}

	public static final Map<String, CustomFireEffect> registeredEffects = new HashMap<>();

	public static final CustomFireEffect COLD_FIRE = new CustomFireEffect("cold_fire",
			new ResourceLocation(ModInfo.MODID, "other/fire/cold_0"),
			new ResourceLocation(ModInfo.MODID, "other/fire/cold_1"), (e, t) -> {
				e.motionX *= 0.25D;
				e.motionY *= e.isSneaking() ? 0.75D : 0.5D;
				e.motionZ *= 0.25D;

				if (!e.world.isRemote && t % 10 == 0) {
					e.attackEntityFrom(new DamageSource("coldfire").setFireDamage(), 4);
				}
				return true;
			});

	public static final CustomFireEffect SPIRIT_FIRE = new CustomFireEffect("spirit_fire",
			new ResourceLocation(ModInfo.MODID, "other/fire/spirit_0"),
			new ResourceLocation(ModInfo.MODID, "other/fire/spirit_1"), (e, t) -> {
				if (e.isImmuneToFire())
					return false;
				if (e.isInWater()) {
					e.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1, 1);
					return false;
				}

				if (!e.world.isRemote && t % 20 == 0) {
					int hurt = e.hurtResistantTime;
					e.hurtResistantTime = 0;
					e.attackEntityFrom(new DamageSource("haunted").setFireDamage(), e.isImmuneToFire() ? 1 : 4);
					e.hurtResistantTime = hurt;
				}
				return true;
			});

	final String id;

	final ResourceLocation one;
	final ResourceLocation two;

	final FireEffect effect;

	boolean fullbright = true;

	public CustomFireEffect(String id, ResourceLocation one, ResourceLocation two) {
		this(id, one, two, (e, t) -> true);
	}

	public CustomFireEffect(String id, ResourceLocation one, ResourceLocation two, FireEffect effect) {
		this.one = one;
		this.two = two;
		this.id = id;
		this.effect = effect;
		MinecraftForge.EVENT_BUS.register(this);

		registeredEffects.put(id, this);
	}

	public boolean effect(EntityLivingBase entity, int timeLeft) {
		return effect.run(entity, timeLeft);
	}

	public boolean isFullbright() {
		return fullbright;
	}

	public CustomFireEffect setFullbright(boolean fullbright) {
		this.fullbright = fullbright;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CustomFireEffect) {
			return ((CustomFireEffect) obj).id.equals(this.id);
		}
		return false;
	}

	public void render(RenderLivingEvent.Pre<?> event) {
		GlStateManager.disableLighting();
		TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
		TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite(one.toString());
		TextureAtlasSprite textureatlassprite1 = texturemap.getAtlasSprite(two.toString());
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) event.getX(), (float) event.getY(), (float) event.getZ());
		float f = event.getEntity().width * 1.4F;
		GlStateManager.scale(f, f, f);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		float f1 = 0.5F;
		float f3 = event.getEntity().height / f;
		float f4 = (float) (event.getEntity().posY - event.getEntity().getEntityBoundingBox().minY);
		GlStateManager.rotate(-event.getRenderer().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.0F, 0.0F, -0.3F + (float) ((int) f3) * 0.02F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		float f5 = 0.0F;
		int i = 0;
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);

		while (f3 > 0.0F) {
			TextureAtlasSprite textureatlassprite2 = i % 2 == 0 ? textureatlassprite : textureatlassprite1;
			event.getRenderer().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			float f6 = textureatlassprite2.getMinU();
			float f7 = textureatlassprite2.getMinV();
			float f8 = textureatlassprite2.getMaxU();
			float f9 = textureatlassprite2.getMaxV();

			if (i / 2 % 2 == 0) {
				float f10 = f8;
				f8 = f6;
				f6 = f10;
			}

			bufferbuilder.pos((double) (f1 - 0.0F), (double) (0.0F - f4), (double) f5).tex((double) f8, (double) f9)
					.endVertex();
			bufferbuilder.pos((double) (-f1 - 0.0F), (double) (0.0F - f4), (double) f5).tex((double) f6, (double) f9)
					.endVertex();
			bufferbuilder.pos((double) (-f1 - 0.0F), (double) (1.4F - f4), (double) f5).tex((double) f6, (double) f7)
					.endVertex();
			bufferbuilder.pos((double) (f1 - 0.0F), (double) (1.4F - f4), (double) f5).tex((double) f8, (double) f7)
					.endVertex();
			f3 -= 0.45F;
			f4 -= 0.45F;
			f1 *= 0.9F;
			f5 += 0.03F;
			++i;
		}
		if (fullbright)
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

		tessellator.draw();
		GlStateManager.popMatrix();
		GlStateManager.enableLighting();
	}

	@FunctionalInterface
	public static interface FireEffect {
		boolean run(EntityLivingBase entity, int fireTime);
	}
}
