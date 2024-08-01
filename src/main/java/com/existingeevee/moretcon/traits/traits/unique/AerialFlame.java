package com.existingeevee.moretcon.traits.traits.unique;

import java.util.List;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.client.actions.FieryPillarAction;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
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

	@Override
	public void onLaunch(EntityProjectileBase projectileBase, World world, @Nullable EntityLivingBase shooter) {
		NBTTagCompound tag = projectileBase.getEntityData().getCompoundTag(this.getModifierIdentifier());
		tag.setDouble("LaunchX", projectileBase.posX);
		tag.setDouble("LaunchY", projectileBase.posY);
		tag.setDouble("LaunchZ", projectileBase.posZ);
		projectileBase.getEntityData().setTag(this.getModifierIdentifier(), tag);
	}

	@Override
	public void onMovement(EntityProjectileBase entity, World world, double slowdown) {
		NBTTagCompound tag = entity.getEntityData().getCompoundTag(this.getModifierIdentifier());
		
		double x = tag.getDouble("LaunchX"), y = tag.getDouble("LaunchY"), z = tag.getDouble("LaunchZ");
		double distSq = entity.getDistanceSq(x, y, z);
		
		if (entity.inGround || distSq > 96 * 96 || distSq < 2 * 2) //no in ground, not too far, and def not too close
			return;

		ItemStack toolStack = entity.tinkerProjectile.getItemStack();

		int lowY = entity.getPosition().getY();
		int i = 0;
		while (true) {
			BlockPos next = new BlockPos(entity.getPosition().getX(), lowY, entity.getPosition().getZ());
			IBlockState state = world.getChunkFromBlockCoords(next).getBlockState(next);
			if (state.isFullBlock() || i > 50) {
				break;
			} else {
				lowY--;
				i++;
			}
		}

		if (!world.isRemote) {
			FieryPillarAction.INSTANCE.run(world, entity.posX, entity.posY, entity.posZ, new NBTTagInt(lowY));
		}

		AxisAlignedBB hitbox = new AxisAlignedBB(entity.posX - 0.75, entity.posY, entity.posZ - 0.75, entity.lastTickPosX + 0.75, lowY - 1, entity.lastTickPosZ + 0.75);
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(entity.shootingEntity, hitbox);

		entities.removeIf(e -> !(e instanceof EntityLivingBase));

		DamageSource source = new EntityDamageSource("pillar_of_fire", entity.shootingEntity).setFireDamage();

		for (Entity e : entities) {
			if (e == entity)
				continue;

			if (entity.shootingEntity instanceof EntityLivingBase && e.hurtResistantTime < ((EntityLivingBase) e).maxHurtResistantTime / 2) {
				float dmg = 5;
				e.setFire(10);

				if (e.isImmuneToFire()) {
					dmg = 2.5f;
				}
				
				// Proc traits and all
				List<ITrait> traits = TinkerUtil.getTraitsOrdered(entity.tinkerProjectile.getItemStack());

				float dmgOrig = dmg;

				for (ITrait t : traits) {
					if (e instanceof EntityLivingBase) {
						dmg = t.damage(toolStack, (EntityLivingBase) entity.shootingEntity, (EntityLivingBase) e, dmgOrig, dmg, false);
					}
				}

				for (ITrait t : traits) {
					if (e instanceof EntityLivingBase) {
						t.onHit(toolStack, (EntityLivingBase) entity.shootingEntity, (EntityLivingBase) e, dmg, false);
					}
				}

				e.hurtResistantTime = 0;

				float hpBefore = ((EntityLivingBase) e).getHealth();
				boolean wasHit = e.attackEntityFrom(source, dmg);

				for (ITrait t : traits) {
					if (e instanceof EntityLivingBase) {
						t.afterHit(toolStack, (EntityLivingBase) entity.shootingEntity, (EntityLivingBase) e, hpBefore - ((EntityLivingBase) e).getHealth(), false, wasHit);
					}
				}
			}
		}
	}
}
