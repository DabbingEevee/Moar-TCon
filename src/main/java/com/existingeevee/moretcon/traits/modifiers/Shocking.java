package com.existingeevee.moretcon.traits.modifiers;

import com.existingeevee.moretcon.other.Misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ProjectileModifierTrait;
import thebetweenlands.client.render.particle.BLParticles;
import thebetweenlands.client.render.particle.BatchedParticleRenderer;
import thebetweenlands.client.render.particle.DefaultParticleBatches;
import thebetweenlands.client.render.particle.ParticleFactory.ParticleArgs;
import thebetweenlands.common.entity.EntityShock;

public class Shocking extends ProjectileModifierTrait {

	public Shocking() {
		super(Misc.createNonConflictiveName("modshocking"), 0x0099e6);
		this.addAspects(ModifierAspect.projectileOnly);//, new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this), ModifierAspect.freeModifier);
	}

	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed) {
		if(!world.isRemote) {
			if (target instanceof EntityLivingBase) {//
				float damage = MathHelper.ceil((double) MathHelper.sqrt(projectile.motionX * projectile.motionX + projectile.motionY * projectile.motionY + projectile.motionZ * projectile.motionZ) * projectile.getDamage());
				damage += (projectile.getIsCritical() ? random.nextInt((int)damage / 2 + 2) : 0);
				world.spawnEntity(new EntityShock(world, projectile, (EntityLivingBase) target, damage, projectile.isWet() || projectile.isInWater() || world.isRainingAt(projectile.getPosition().up())));
			}
		}
		super.afterHit(projectile, world, ammoStack, attacker, target, impactSpeed);
	}

	@Override
	public void onProjectileUpdate(EntityProjectileBase projectile, World world, ItemStack toolStack) {
		super.onProjectileUpdate(projectile, world, toolStack);
		if(world.isRemote) {
			spawnLightningArcs(projectile);
		}

	}

	@SideOnly(Side.CLIENT)
	private void spawnLightningArcs(EntityProjectileBase entity) {
		Entity view = Minecraft.getMinecraft().getRenderViewEntity();
		if(view != null && view.getDistance(entity) < 16 && entity.world.rand.nextInt(!entity.onGround ? 5 : 50) == 0) {
			float ox = entity.world.rand.nextFloat() - 0.5f + (!entity.onGround ? (float)entity.motionX : 0);
			float oy = entity.world.rand.nextFloat() - 0.5f + (!entity.onGround ? (float)entity.motionY : 0);
			float oz = entity.world.rand.nextFloat() - 0.5f + (!entity.onGround ? (float)entity.motionZ : 0);

			Particle particle;
			if (entity.isAirBorne) {
				particle = BLParticles.LIGHTNING_ARC.create(entity.world, entity.posX, entity.posY, entity.posZ,
						ParticleArgs.get()
						.withMotion(entity.motionX, entity.motionY, entity.motionZ)
						.withColor(0.3f, 0.5f, 1.0f, 0.9f)
						.withData(new Vec3d(entity.posX + ox, entity.posY + oy, entity.posZ + oz)));
			} else {
				particle = BLParticles.LIGHTNING_ARC.create(entity.world, entity.posX, entity.posY, entity.posZ,
						ParticleArgs.get()
						.withMotion(0, 0, 0)
						.withColor(0.3f, 0.5f, 1.0f, 0.9f)
						.withData(new Vec3d(entity.posX + ox, entity.posY + oy, entity.posZ + oz)));
			}
			BatchedParticleRenderer.INSTANCE.addParticle(DefaultParticleBatches.BEAM, particle);
		}
	}
}

//end bricks
