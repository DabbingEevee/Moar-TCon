package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.client.actions.LightningEffectAction;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Electrified extends AbstractTrait {
	public Electrified() {
		super(MiscUtils.createNonConflictiveName("electrified"), 0);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		NBTTagCompound tag = target.getEntityData().getCompoundTag(this.getModifierIdentifier());
		int amount = tag.getInteger("amount");

		if (tag.getLong("last") + 10 * 20 < target.world.getTotalWorldTime()) {
			amount = 0;
		}

		if (!target.world.isRemote) {
			Vec3d center = MiscUtils.getCenter(target.getEntityBoundingBox());
			
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setBoolean("big", amount >= 10);
			tagCompound.setDouble("speed", amount * 0.05);
			new LightningEffectAction().run(target.world, center.x, center.y, center.z, tagCompound);
		}
		if (amount >= 10) {
			target.world.playSound(null, target.posX, target.posY, target.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.PLAYERS, 1, 1.5f);
			tag.setInteger("amount", 0);
			target.getEntityData().setTag(this.getModifierIdentifier(), tag);
			return (newDamage + amount / 2f) * 2.25f;
		} else {
			target.world.playSound(null, target.posX, target.posY, target.posZ, SoundEvents.BLOCK_ENDERCHEST_OPEN, SoundCategory.PLAYERS, 1, amount * 1.f / 10);
			return newDamage + amount / 2f;
		}
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (wasHit) {

			if (player instanceof EntityPlayer && ((EntityPlayer) player).getCooledAttackStrength(0f) < 0.75) {
				return; //if not close to fully charged then no proc
			}

			NBTTagCompound tag = target.getEntityData().getCompoundTag(this.getIdentifier());
			int amount = tag.getInteger("amount");
			if (tag.getLong("last") + 10 * 20 < target.world.getTotalWorldTime()) {
				amount = 0;
			}

			amount += wasCritical ? 2 : 1;

			tag.setInteger("amount", amount);
			tag.setLong("last", target.world.getTotalWorldTime());
			target.getEntityData().setTag(this.getIdentifier(), tag);
		}
	}
}
