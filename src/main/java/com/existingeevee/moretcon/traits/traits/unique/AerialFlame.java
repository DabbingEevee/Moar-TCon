package com.existingeevee.moretcon.traits.traits.unique;

import java.util.List;
import java.util.Random;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class AerialFlame extends AbstractProjectileTrait {

	public AerialFlame() {
		super(MiscUtils.createNonConflictiveName("AerialFlame".toLowerCase()), 0);
	}

	private static final Random rand = new Random();

	@Override
	public void onProjectileUpdate(EntityProjectileBase entity, World world, ItemStack toolStack) {
		if (entity.serializeNBT().getBoolean("inGround")) {
			return;
		}

		int lowY = entity.getPosition().getY();
		int i = 0;
		while (true) {
			BlockPos next = new BlockPos(entity.getPosition().getX(), lowY, entity.getPosition().getZ());
			IBlockState state = world.getChunkFromBlockCoords(next).getBlockState(next);
			if (state.isFullBlock() || lowY == 0 || i > 50) {
				break;
			} else {
				lowY--;
				i++;
				if (world.isRemote) {
					for (int in = 0; in < 2; in++) {
						entity.world.spawnParticle(rand.nextBoolean() || rand.nextBoolean() ? EnumParticleTypes.FLAME : EnumParticleTypes.LAVA, true, entity.posX + (rand.nextDouble() * 0.5 - 0.25), (lowY + 0.5 + (rand.nextDouble() * 1.5 - 0.75)) - 0.05, entity.posZ + (rand.nextDouble() * 0.5 - 0.25), 0, 0, 0);
					}
				}
			}

		}
		AxisAlignedBB hitbox = new AxisAlignedBB(entity.posX - 0.75, entity.posY, entity.posZ - 0.75, entity.lastTickPosX + 0.75, lowY - 1, entity.lastTickPosZ + 0.75);
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(entity.shootingEntity, hitbox);

		entities.removeIf(e -> !(e instanceof EntityLivingBase));

		DamageSource source = new EntityDamageSource("pillar_of_fire", entity.shootingEntity).setFireDamage();

		for (Entity e : entities) {
			if (e == entity)
				continue;

			float dmg = 5;
			e.setFire(10);

			if (e.isImmuneToFire()) {
				dmg = 2.5f;
			}
			if (entity.shootingEntity instanceof EntityLivingBase) {
				//Proc traits and all
				List<ITrait> traits = TinkerUtil.getTraitsOrdered(entity.tinkerProjectile.getItemStack());

				float dmgOrig = dmg;
				
				for (ITrait t : traits) {
					if (e instanceof EntityLivingBase) {
						dmg = t.damage(toolStack, (EntityLivingBase) entity.shootingEntity, (EntityLivingBase) e, dmgOrig, dmg, false);
					}
				}
				
				float hpBefore = ((EntityLivingBase) e).getHealth();
				boolean wasHit = e.attackEntityFrom(source, dmg);
				
				for (ITrait t : traits) {
					if (e instanceof EntityLivingBase) {
						t.onHit(toolStack, (EntityLivingBase) entity.shootingEntity, (EntityLivingBase) e, dmg, false);
					}
				}
				
				for (ITrait t : traits) {
					if (e instanceof EntityLivingBase) {
						t.afterHit(toolStack, (EntityLivingBase) entity.shootingEntity, (EntityLivingBase) e, hpBefore - ((EntityLivingBase) e).getHealth(), false, wasHit);
					}
				}
			}
		}
	}
}
