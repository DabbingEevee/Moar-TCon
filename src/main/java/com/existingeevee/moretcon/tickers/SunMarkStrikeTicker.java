package com.existingeevee.moretcon.tickers;

import java.util.ArrayList;
import java.util.UUID;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.client.actions.HolyStrikeClientAction;
import com.existingeevee.moretcon.other.pwt.PersistantTicker;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class SunMarkStrikeTicker extends PersistantTicker {

	public SunMarkStrikeTicker() {
		super(ModInfo.MODID, "blastphemy_strike");
	}

	@Override
	public boolean isValid(NBTTagCompound tag, World world) {
		return tag.getInteger("TimeRemaining") > 0;
	}

	@Override
	public void tick(NBTTagCompound tag, World world) {

		int time = tag.getInteger("TimeRemaining");

		if (!world.isRemote && time - 1 == 0) { // &&
			double x = tag.getDouble("X");
			double z = tag.getDouble("Z");

			ItemStack stack = new ItemStack(tag.getCompoundTag("Stack"));
			EntityPlayer attacker = null;
			try {
				attacker = world.getPlayerEntityByUUID(UUID.fromString(tag.getString("AttackingPlayer")));
			} catch (IllegalArgumentException e) {
				// nuh uh
			}

			float power = ToolHelper.calcCutoffDamage(ToolHelper.getActualAttack(stack), stack.getItem() instanceof ToolCore ? ((ToolCore) stack.getItem()).damageCutoff() : 12);

			double radius = 1.5;

			HolyStrikeClientAction.INSTANCE.run(world, x, 69, z, new NBTTagDouble(radius));

			for (Entity e : new ArrayList<>(world.loadedEntityList)) {
				if (!(MiscUtils.canArrowHit(e)))
					continue;
				Vec3d entityPos = e.getPositionVector();

				double delX = entityPos.x - x;
				double delZ = entityPos.z - z;

				double xSqPlusYSq = delX * delX + delZ * delZ;
				double adjustedRadius = radius + 0.5 * (e.getEntityBoundingBox().maxX - e.getEntityBoundingBox().minX);

				if (xSqPlusYSq < adjustedRadius * adjustedRadius) {
					float damage = power;

					if (attacker instanceof EntityPlayer) {
						EntityPlayer player = (EntityPlayer) attacker;
						if (e instanceof EntityPlayer) {
							if (!player.canAttackPlayer((EntityPlayer) e)) {
								continue; //nop
							}
						}
					}

					DamageSource source = new EntityDamageSource("sunstrike", attacker);

					System.out.println(stack);
					
					e.attackEntityFrom(source, damage);

					e.setFire(20);

					e.motionX += Math.min(1 / delX, 0.75) * 0.125;
					e.motionZ += Math.min(1 / delZ, 0.75) * 0.125;
					e.motionY += 0.15f;
					e.velocityChanged = true;
				}
			}

			int y = 255;
			for (; y >= 0 && world.isAirBlock(new BlockPos(x, y, z)); y--);

			if (!world.isAirBlock(new BlockPos(x, y, z))) {
				// world.newExplosion(attacker, x, y, z, 25, true, true);

				world.playSound(null, x, y, z, SoundEvents.ENTITY_ENDERDRAGON_SHOOT, SoundCategory.PLAYERS, 10F, 0.8f * (float) Math.random());
			}
		}

		tag.setInteger("TimeRemaining", time - 1);
	}

	@Override
	public void init(NBTTagCompound tag, Object... objects) {
		if (objects.length >= 5) {
			if (objects[0] instanceof Number)
				tag.setDouble("X", ((Number) objects[0]).doubleValue());
			if (objects[1] instanceof Number)
				tag.setDouble("Z", ((Number) objects[1]).doubleValue());
			if (objects[2] instanceof Entity)
				tag.setString("AttackingPlayer", ((Entity) objects[2]).getUniqueID().toString());
			if (objects[3] instanceof NBTTagCompound)
				tag.setTag("Stack", (NBTTagCompound) objects[3]);
			if (objects[4] instanceof NBTTagCompound)
				tag.setTag("HitscanData", (NBTTagCompound) objects[4]);
		}

		if (tag.getCompoundTag("HitacanData").getBoolean("Fast")) {
			tag.setInteger("TimeRemaining", 10);
		} else {
			tag.setInteger("TimeRemaining", 30);
		}
		
		System.out.println(tag);
	}

}
