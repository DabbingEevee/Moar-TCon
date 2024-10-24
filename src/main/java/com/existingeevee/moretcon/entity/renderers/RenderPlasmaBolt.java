package com.existingeevee.moretcon.entity.renderers;

import java.util.Random;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.client.particle.ParticlePlasmaBolt;
import com.existingeevee.moretcon.entity.entities.EntityPlasmaBolt;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//TODO improve this to not use a hijacked particle, but im too tired to do this (3AM :3)

@SideOnly(Side.CLIENT) 
public class RenderPlasmaBolt extends Render<EntityPlasmaBolt> {
	
	public RenderPlasmaBolt(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public boolean shouldRender(EntityPlasmaBolt livingEntity, ICamera camera, double camX, double camY, double camZ) {
		return true;
	}

	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
		// nuh uh
	} 

	@Override
	public void doRender(EntityPlasmaBolt entity, double x, double y, double z, float entityYaw, float partialTicks) {
		Vec3d pos = entity.getPositionEyes(partialTicks);
		Vec3d target = entity.getTarget().add(entity.getTarget().subtract(entity.getPrevTarget()).scale(partialTicks));
		
		double progress = (entity.getTimeAlive() + partialTicks) / entity.getImpactTime();
		double randomRot = Math.PI * 2 * new Random(entity.getUniqueID().getLeastSignificantBits()).nextDouble();
		
		Vec3d newPos = pos.add(getRenderOffset(target.subtract(pos), randomRot, progress));
		
		updateHijackedParticle(entity, newPos.x, newPos.y, newPos.z);
	}
	
	public void updateHijackedParticle(EntityPlasmaBolt entity, double x, double y, double z) {
		if (entity.linkedParticle == null) {
			entity.linkedParticle = new ParticlePlasmaBolt(entity.world, x, y, z, entity);
			Minecraft.getMinecraft().effectRenderer.addEffect(entity.linkedParticle);
		}
		entity.linkedParticle.setPosition(x, y, z);
	}

	protected Vec3d getRenderOffset(Vec3d mainVec, double rotation, double progress) {
		double initialSlope = Math.PI / 4;
		double vecMagnitude = mainVec.lengthVector();

		double arc = MiscUtils.quadraticArc(initialSlope, vecMagnitude, vecMagnitude * progress);
				
		Vec3d perpVec = mainVec.scale(arc / vecMagnitude).rotatePitch(-1.57079633f);
		return mainVec.scale(progress).add(MiscUtils.rotateVec3d(perpVec, mainVec, (float) rotation));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPlasmaBolt entity) {
		return new ResourceLocation(ModInfo.MODID, "textures/other/plasma_bolt.png");
	}
}
