package com.existingeevee.moretcon.entity.renderers;

import com.existingeevee.moretcon.entity.entities.EntityDecayingEffect;
import com.existingeevee.moretcon.entity.entities.EntityDecayingEffect.EnumDecayingEffectType;
import com.existingeevee.moretcon.entity.models.ModelDecayingEffect;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDecayingEffect extends RenderLiving<EntityDecayingEffect> {
	public RenderDecayingEffect(RenderManager manager) {
		super(manager, new ModelDecayingEffect(), 0.0f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDecayingEffect entity) {
		EnumDecayingEffectType type = entity.getType();
		if (type == null) {
			type = EnumDecayingEffectType.DEFAULT;
		}
		ResourceLocation resource = type.getResource();
		if (resource == null) {
			resource = EnumDecayingEffectType.DEFAULT.getResource();
		}
		return new ResourceLocation(type.getResource().getResourceDomain(),
				type.getResource().getResourcePath() + "_" + entity.getFrame() + ".png");
	}
}