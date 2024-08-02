package com.existingeevee.moretcon.other.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class FireproofItemUtil {

	public static final Method setFlag = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70052_a",
			void.class, int.class, boolean.class);
	public static final Method pushOutOfBlocks = ObfuscationReflectionHelper.findMethod(Entity.class, "func_145771_j",
			boolean.class, double.class, double.class, double.class);
	public static final Method searchForOtherItemsNearby = ObfuscationReflectionHelper.findMethod(EntityItem.class,
			"func_85054_d", void.class);
	public static final Method decrementTimeUntilPortal = ObfuscationReflectionHelper.findMethod(Entity.class,
			"func_184173_H", void.class);
	public static final Method updateFallState = ObfuscationReflectionHelper.findMethod(Entity.class, "func_184231_a",
			void.class, double.class, boolean.class, IBlockState.class, BlockPos.class);
	public static final Method canTriggerWalking = ObfuscationReflectionHelper.findMethod(Entity.class, "func_70041_e_",
			boolean.class);
	public static final Method getSwimSound = ObfuscationReflectionHelper.findMethod(Entity.class, "func_184184_Z",
			SoundEvent.class);
	public static final Method doBlockCollisions = ObfuscationReflectionHelper.findMethod(Entity.class, "func_145775_I",
			void.class);
	public static final Method makeFlySound = ObfuscationReflectionHelper.findMethod(Entity.class, "func_191957_ae",
			boolean.class);
	public static final Method playStepSound = ObfuscationReflectionHelper.findMethod(Entity.class, "func_180429_a",
			void.class, BlockPos.class, Block.class);
	public static final Method playFlySound = ObfuscationReflectionHelper.findMethod(Entity.class, "func_191954_d",
			float.class, float.class);

	public static final Field pickupDelay = ObfuscationReflectionHelper.findField(EntityItem.class, "field_145804_b");
	public static final Field rand = ObfuscationReflectionHelper.findField(Entity.class, "field_70146_Z");
	public static final Field rideCooldown = ObfuscationReflectionHelper.findField(Entity.class, "field_184245_j");
	public static final Field age = ObfuscationReflectionHelper.findField(EntityItem.class, "field_70292_b");
	public static final Field inPortal = ObfuscationReflectionHelper.findField(Entity.class, "field_71087_bX");
	public static final Field portalCounter = ObfuscationReflectionHelper.findField(Entity.class, "field_82153_h");
	public static final Field firstUpdate = ObfuscationReflectionHelper.findField(Entity.class, "field_70148_d");
	public static final Field pistonDeltasGameTime = ObfuscationReflectionHelper.findField(Entity.class,
			"field_191506_aJ");
	public static final Field pistonDeltas = ObfuscationReflectionHelper.findField(Entity.class, "field_191505_aI");
	public static final Field isInWeb = ObfuscationReflectionHelper.findField(Entity.class, "field_70134_J");
	public static final Field nextStepDistance = ObfuscationReflectionHelper.findField(Entity.class, "field_70150_b");
	public static final Field nextFlap = ObfuscationReflectionHelper.findField(Entity.class, "field_191959_ay");

	public static void onUpdateSafe(EntityItem entity) {
		try {
			onUpdate(entity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void onUpdate(EntityItem entity) throws IllegalAccessException, InvocationTargetException {
		if (entity.getItem().isEmpty()) {
			entity.setDead();
		} else {
			if (!entity.world.isRemote) {
				setFlag.invoke(entity, 6, entity.isGlowing());
			}
			entity.extinguish();

			onEntityUpdate(entity);

			if (pickupDelay.getInt(entity) > 0 && pickupDelay.getInt(entity) != 32767) {
				pickupDelay.setInt(entity, pickupDelay.getInt(entity) - 1);
			}

			entity.prevPosX = entity.posX;
			entity.prevPosY = entity.posY;
			entity.prevPosZ = entity.posZ;
			double d0 = entity.motionX;
			double d1 = entity.motionY;
			double d2 = entity.motionZ;

			if (entity.isInsideOfMaterial(Material.LAVA)) {
				floatInLava(entity);
			} else if (!entity.hasNoGravity()) {
				entity.motionY -= 0.03999999910593033;
			}

			if (entity.world.isRemote) {
				entity.noClip = false;
			} else {
				entity.noClip = (boolean) pushOutOfBlocks.invoke(entity, entity.posX,
						(entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D, entity.posZ);
			}

			move(entity, MoverType.SELF, entity.motionX, entity.motionY, entity.motionZ);
			// entity.move(MoverType.SELF, entity.motionX, entity.motionY, entity.motionZ);
			boolean flag = (int) entity.prevPosX != (int) entity.posX || (int) entity.prevPosY != (int) entity.posY
					|| (int) entity.prevPosZ != (int) entity.posZ;

			if (flag || entity.ticksExisted % 25 == 0) {
				if (!entity.world.isRemote) {
					searchForOtherItemsNearby.invoke(entity);
				}
			}

			float f = 0.98F;

			if (entity.onGround) {
				BlockPos underPos = new BlockPos(MathHelper.floor(entity.posX),
						MathHelper.floor(entity.getEntityBoundingBox().minY) - 1, MathHelper.floor(entity.posZ));
				net.minecraft.block.state.IBlockState underState = entity.world.getBlockState(underPos);
				f = underState.getBlock().getSlipperiness(underState, entity.world, underPos, entity) * 0.98F;
			}

			entity.motionX *= f;
			entity.motionY *= 0.98D;
			entity.motionZ *= f;

			if (entity.onGround) {
				entity.motionY *= -0.5D;
			}

			if (age.getInt(entity) != -32768) {
				age.setInt(entity, age.getInt(entity) + 1);
			}

			entity.handleWaterMovement();

			if (!entity.world.isRemote) {
				double d3 = entity.motionX - d0;
				double d4 = entity.motionY - d1;
				double d5 = entity.motionZ - d2;
				double d6 = d3 * d3 + d4 * d4 + d5 * d5;

				if (d6 > 0.01D) {
					entity.isAirBorne = true;
				}
			}

			ItemStack item = entity.getItem();

			if (!entity.world.isRemote && age.getInt(entity) >= entity.lifespan) {
				int hook = net.minecraftforge.event.ForgeEventFactory.onItemExpire(entity, item);
				if (hook < 0) {
					entity.setDead();
				} else {
					entity.lifespan += hook;
				}
			}
			if (item.isEmpty()) {
				entity.setDead();
			}
		}
	}

	public static void floatInLava(EntityItem entity) {
		entity.motionX *= 0.95;
		entity.motionY += (entity.motionY < 0.06 ? 5.0e-4 : 0.0);
		entity.motionZ *= 0.95;
	}

	public static void onEntityUpdate(Entity en) throws IllegalAccessException, InvocationTargetException {
		en.world.profiler.startSection("entityBaseTick");

		if (en.isRiding() && en.getRidingEntity().isDead) {
			en.dismountRidingEntity();
		}

		if (rideCooldown.getInt(en) > 0) {
			rideCooldown.setInt(en, rideCooldown.getInt(en) - 1);
		}

		en.prevDistanceWalkedModified = en.distanceWalkedModified;
		en.prevPosX = en.posX;
		en.prevPosY = en.posY;
		en.prevPosZ = en.posZ;
		en.prevRotationPitch = en.rotationPitch;
		en.prevRotationYaw = en.rotationYaw;

		if (!en.world.isRemote && en.world instanceof WorldServer) {
			en.world.profiler.startSection("portal");

			if (inPortal.getBoolean(en)) {
				MinecraftServer minecraftserver = en.world.getMinecraftServer();

				if (minecraftserver.getAllowNether()) {
					if (!en.isRiding()) {
						int i = en.getMaxInPortalTime();

						int pC = portalCounter.getInt(en);
						portalCounter.setInt(en, pC + 1);
						if (pC >= i) {
							portalCounter.setInt(en, i);
							en.timeUntilPortal = en.getPortalCooldown();
							int j;

							if (en.world.provider.getDimensionType().getId() == -1) {
								j = 0;
							} else {
								j = -1;
							}

							en.changeDimension(j);
						}
					}

					inPortal.setBoolean(en, false);
				}
			} else {
				if (portalCounter.getInt(en) > 0) {
					portalCounter.setInt(en, portalCounter.getInt(en) - 4);
				}

				if (portalCounter.getInt(en) < 0) {
					portalCounter.setInt(en, 0);
				}
			}

			decrementTimeUntilPortal.invoke(en);

			en.world.profiler.endSection();
		}

		en.spawnRunningParticles();
		en.handleWaterMovement();

		en.extinguish();

		if (en.posY < -64.0D) {
			en.setDead();
		}

		firstUpdate.setBoolean(en, false);

		en.world.profiler.endSection();
	}

	public static void move(Entity en, MoverType type, double x, double y, double z)
			throws IllegalAccessException, InvocationTargetException {
		if (en.noClip) {
			en.setEntityBoundingBox(en.getEntityBoundingBox().offset(x, y, z));
			en.resetPositionToBB();
		} else {
			if (type == MoverType.PISTON) {
				long i = en.world.getTotalWorldTime();

				if (i != pistonDeltasGameTime.getLong(en)) {
					Arrays.fill((double[]) pistonDeltas.get(en), 0.0D);
					pistonDeltasGameTime.setLong(en, i);
				}

				if (x != 0.0D) {
					int j = EnumFacing.Axis.X.ordinal();
					double d0 = MathHelper.clamp(x + ((double[]) pistonDeltas.get(en))[j], -0.51D, 0.51D);
					x = d0 - ((double[]) pistonDeltas.get(en))[j];
					((double[]) pistonDeltas.get(en))[j] = d0;

					if (Math.abs(x) <= 9.999999747378752E-6D) {
						return;
					}
				} else if (y != 0.0D) {
					int l4 = EnumFacing.Axis.Y.ordinal();
					double d12 = MathHelper.clamp(y + ((double[]) pistonDeltas.get(en))[l4], -0.51D, 0.51D);
					y = d12 - ((double[]) pistonDeltas.get(en))[l4];
					((double[]) pistonDeltas.get(en))[l4] = d12;

					if (Math.abs(y) <= 9.999999747378752E-6D) {
						return;
					}
				} else {
					if (z == 0.0D) {
						return;
					}

					int i5 = EnumFacing.Axis.Z.ordinal();
					double d13 = MathHelper.clamp(z + ((double[]) pistonDeltas.get(en))[i5], -0.51D, 0.51D);
					z = d13 - ((double[]) pistonDeltas.get(en))[i5];
					((double[]) pistonDeltas.get(en))[i5] = d13;

					if (Math.abs(z) <= 9.999999747378752E-6D) {
						return;
					}
				}
			}

			en.world.profiler.startSection("move");
			double d10 = en.posX;
			double d11 = en.posY;
			double d1 = en.posZ;

			if (isInWeb.getBoolean(en)) {
				isInWeb.setBoolean(en, false);
				x *= 0.25D;
				y *= 0.05D;
				z *= 0.25D;
				en.motionX = 0.0D;
				en.motionY = 0.0D;
				en.motionZ = 0.0D;
			}

			double d2 = x;
			double d3 = y;
			double d4 = z;

			if ((type == MoverType.SELF || type == MoverType.PLAYER) && en.onGround && en.isSneaking()
					&& en instanceof EntityPlayer) {
				for (; x != 0.0D && en.world
						.getCollisionBoxes(en, en.getEntityBoundingBox().offset(x, (-en.stepHeight), 0.0D))
						.isEmpty(); d2 = x) {
					if (x < 0.05D && x >= -0.05D) {
						x = 0.0D;
					} else if (x > 0.0D) {
						x -= 0.05D;
					} else {
						x += 0.05D;
					}
				}

				for (; z != 0.0D && en.world
						.getCollisionBoxes(en, en.getEntityBoundingBox().offset(0.0D, (-en.stepHeight), z))
						.isEmpty(); d4 = z) {
					if (z < 0.05D && z >= -0.05D) {
						z = 0.0D;
					} else if (z > 0.0D) {
						z -= 0.05D;
					} else {
						z += 0.05D;
					}
				}

				for (; x != 0.0D && z != 0.0D
						&& en.world
								.getCollisionBoxes(en,
										en.getEntityBoundingBox().offset(x, (-en.stepHeight), z))
								.isEmpty(); d4 = z) {
					if (x < 0.05D && x >= -0.05D) {
						x = 0.0D;
					} else if (x > 0.0D) {
						x -= 0.05D;
					} else {
						x += 0.05D;
					}

					d2 = x;

					if (z < 0.05D && z >= -0.05D) {
						z = 0.0D;
					} else if (z > 0.0D) {
						z -= 0.05D;
					} else {
						z += 0.05D;
					}
				}
			}

			List<AxisAlignedBB> list1 = en.world.getCollisionBoxes(en, en.getEntityBoundingBox().expand(x, y, z));
			AxisAlignedBB axisalignedbb = en.getEntityBoundingBox();

			if (y != 0.0D) {
				int k = 0;

				for (int l = list1.size(); k < l; ++k) {
					y = list1.get(k).calculateYOffset(en.getEntityBoundingBox(), y);
				}

				en.setEntityBoundingBox(en.getEntityBoundingBox().offset(0.0D, y, 0.0D));
			}

			if (x != 0.0D) {
				int j5 = 0;

				for (int l5 = list1.size(); j5 < l5; ++j5) {
					x = list1.get(j5).calculateXOffset(en.getEntityBoundingBox(), x);
				}

				if (x != 0.0D) {
					en.setEntityBoundingBox(en.getEntityBoundingBox().offset(x, 0.0D, 0.0D));
				}
			}

			if (z != 0.0D) {
				int k5 = 0;

				for (int i6 = list1.size(); k5 < i6; ++k5) {
					z = list1.get(k5).calculateZOffset(en.getEntityBoundingBox(), z);
				}

				if (z != 0.0D) {
					en.setEntityBoundingBox(en.getEntityBoundingBox().offset(0.0D, 0.0D, z));
				}
			}

			boolean flag = en.onGround || d3 != y && d3 < 0.0D;

			if (en.stepHeight > 0.0F && flag && (d2 != x || d4 != z)) {
				double d14 = x;
				double d6 = y;
				double d7 = z;
				AxisAlignedBB axisalignedbb1 = en.getEntityBoundingBox();
				en.setEntityBoundingBox(axisalignedbb);
				y = en.stepHeight;
				List<AxisAlignedBB> list = en.world.getCollisionBoxes(en, en.getEntityBoundingBox().expand(d2, y, d4));
				AxisAlignedBB axisalignedbb2 = en.getEntityBoundingBox();
				AxisAlignedBB axisalignedbb3 = axisalignedbb2.expand(d2, 0.0D, d4);
				double d8 = y;
				int j1 = 0;

				for (int k1 = list.size(); j1 < k1; ++j1) {
					d8 = list.get(j1).calculateYOffset(axisalignedbb3, d8);
				}

				axisalignedbb2 = axisalignedbb2.offset(0.0D, d8, 0.0D);
				double d18 = d2;
				int l1 = 0;

				for (int i2 = list.size(); l1 < i2; ++l1) {
					d18 = list.get(l1).calculateXOffset(axisalignedbb2, d18);
				}

				axisalignedbb2 = axisalignedbb2.offset(d18, 0.0D, 0.0D);
				double d19 = d4;
				int j2 = 0;

				for (int k2 = list.size(); j2 < k2; ++j2) {
					d19 = list.get(j2).calculateZOffset(axisalignedbb2, d19);
				}

				axisalignedbb2 = axisalignedbb2.offset(0.0D, 0.0D, d19);
				AxisAlignedBB axisalignedbb4 = en.getEntityBoundingBox();
				double d20 = y;
				int l2 = 0;

				for (int i3 = list.size(); l2 < i3; ++l2) {
					d20 = list.get(l2).calculateYOffset(axisalignedbb4, d20);
				}

				axisalignedbb4 = axisalignedbb4.offset(0.0D, d20, 0.0D);
				double d21 = d2;
				int j3 = 0;

				for (int k3 = list.size(); j3 < k3; ++j3) {
					d21 = list.get(j3).calculateXOffset(axisalignedbb4, d21);
				}

				axisalignedbb4 = axisalignedbb4.offset(d21, 0.0D, 0.0D);
				double d22 = d4;
				int l3 = 0;

				for (int i4 = list.size(); l3 < i4; ++l3) {
					d22 = list.get(l3).calculateZOffset(axisalignedbb4, d22);
				}

				axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d22);
				double d23 = d18 * d18 + d19 * d19;
				double d9 = d21 * d21 + d22 * d22;

				if (d23 > d9) {
					x = d18;
					z = d19;
					y = -d8;
					en.setEntityBoundingBox(axisalignedbb2);
				} else {
					x = d21;
					z = d22;
					y = -d20;
					en.setEntityBoundingBox(axisalignedbb4);
				}

				int j4 = 0;

				for (int k4 = list.size(); j4 < k4; ++j4) {
					y = list.get(j4).calculateYOffset(en.getEntityBoundingBox(), y);
				}

				en.setEntityBoundingBox(en.getEntityBoundingBox().offset(0.0D, y, 0.0D));

				if (d14 * d14 + d7 * d7 >= x * x + z * z) {
					x = d14;
					y = d6;
					z = d7;
					en.setEntityBoundingBox(axisalignedbb1);
				}
			}

			en.world.profiler.endSection();
			en.world.profiler.startSection("rest");
			en.resetPositionToBB();
			en.collidedHorizontally = d2 != x || d4 != z;
			en.collidedVertically = d3 != y;
			en.onGround = en.collidedVertically && d3 < 0.0D;
			en.collided = en.collidedHorizontally || en.collidedVertically;
			int j6 = MathHelper.floor(en.posX);
			int i1 = MathHelper.floor(en.posY - 0.2D);
			int k6 = MathHelper.floor(en.posZ);
			BlockPos blockpos = new BlockPos(j6, i1, k6);
			IBlockState iblockstate = en.world.getBlockState(blockpos);

			if (iblockstate.getMaterial() == Material.AIR) {
				BlockPos blockpos1 = blockpos.down();
				IBlockState iblockstate1 = en.world.getBlockState(blockpos1);
				Block block1 = iblockstate1.getBlock();

				if (block1 instanceof BlockFence || block1 instanceof BlockWall || block1 instanceof BlockFenceGate) {
					iblockstate = iblockstate1;
					blockpos = blockpos1;
				}
			}

			updateFallState.invoke(en, y, en.onGround, iblockstate, blockpos);

			if (d2 != x) {
				en.motionX = 0.0D;
			}

			if (d4 != z) {
				en.motionZ = 0.0D;
			}

			Block block = iblockstate.getBlock();

			if (d3 != y) {
				block.onLanded(en.world, en);
			}

			if (((boolean) canTriggerWalking.invoke(en))
					&& (!en.onGround || !en.isSneaking() || !(en instanceof EntityPlayer)) && !en.isRiding()) {
				double d15 = en.posX - d10;
				double d16 = en.posY - d11;
				double d17 = en.posZ - d1;

				if (block != Blocks.LADDER) {
					d16 = 0.0D;
				}

				if (block != null && en.onGround) {
					block.onEntityWalk(en.world, blockpos, en);
				}

				en.distanceWalkedModified = (float) (en.distanceWalkedModified
						+ MathHelper.sqrt(d15 * d15 + d17 * d17) * 0.6D);
				en.distanceWalkedOnStepModified = (float) (en.distanceWalkedOnStepModified
						+ MathHelper.sqrt(d15 * d15 + d16 * d16 + d17 * d17) * 0.6D);

				if (en.distanceWalkedOnStepModified > nextStepDistance.getInt(en)
						&& iblockstate.getMaterial() != Material.AIR) {
					nextStepDistance.setInt(en, (int) en.distanceWalkedOnStepModified + 1);

					if (en.isInWater()) {
						Entity entity = en.isBeingRidden() && en.getControllingPassenger() != null
								? en.getControllingPassenger()
								: en;
						float f = entity == en ? 0.35F : 0.4F;
						float f1 = MathHelper.sqrt(
								entity.motionX * entity.motionX * 0.2D + entity.motionY * entity.motionY
										+ entity.motionZ * entity.motionZ * 0.2D)
								* f;

						if (f1 > 1.0F) {
							f1 = 1.0F;
						}

						en.playSound((SoundEvent) getSwimSound.invoke(en), f1, 1.0F
								+ (((Random) rand.get(entity)).nextFloat() - ((Random) rand.get(entity)).nextFloat())
										* 0.4F);
					} else {
						playStepSound.invoke(en, blockpos, block);// en.playStepSound(blockpos, block);
					}
				} else if (en.distanceWalkedOnStepModified > nextFlap.getFloat(en)
						&& ((boolean) makeFlySound.invoke(en)) && iblockstate.getMaterial() == Material.AIR) {
					nextFlap.setFloat(en, (float) playFlySound.invoke(en, en.distanceWalkedOnStepModified));
				}
			}

			try {
				doBlockCollisions.invoke(en);// en.doBlockCollisions();
			} catch (Throwable throwable) {
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
				CrashReportCategory crashreportcategory = crashreport
						.makeCategory("Entity being checked for collision");
				en.addEntityCrashInfo(crashreportcategory);
				throw new ReportedException(crashreport);
			}

			en.world.profiler.endSection();
		}
	}

}
