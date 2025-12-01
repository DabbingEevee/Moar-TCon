package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.other.utils.ReequipHack;
import com.existingeevee.moretcon.traits.ModTraits;
import com.existingeevee.moretcon.traits.traits.abst.NumberTrackerTrait;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class Embering extends NumberTrackerTrait {

	public Embering() {
		super(MiscUtils.createNonConflictiveName("embering"), 0x00ed00);
		ReequipHack.registerIgnoredKey(this.getModifierIdentifier());
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
		if (this.getNumber(tool) > 0) {
			float damageToDeal = Math.max(1f, this.getNumber(tool) / 20f);
			attackEntitySecondary(DamageSource.ON_FIRE, damageToDeal * 0.2f, target, false, true);
			attackEntitySecondary(new EntityDamageSource("onFire", player).setFireDamage(), damageToDeal * 0.8f, target, false, true);
			if (player.world.isRemote) {
				for (int i = 1; i <= 2 * Math.ceil(this.getNumber(tool) / 20f); i++) {
					player.world.spawnParticle(EnumParticleTypes.LAVA, true, target.posX, target.posY + 0.6, target.posZ, MiscUtils.randomN1T1() * 0.05 + 0.05, MiscUtils.randomN1T1() * 0.05 + 0.05, MiscUtils.randomN1T1() * 0.05 + 0.05);
				}
			}
		}
	}

	@Override
	public void onBlock(ItemStack tool, EntityPlayer player, LivingHurtEvent event) {
		Entity entity = event.getSource().getImmediateSource();

		if (this.getNumber(tool) > 0 && entity instanceof EntityLivingBase) {
			attackEntitySecondary(DamageSource.IN_FIRE, Math.max(1f, this.getNumber(tool) / 20f), entity, false, false);
		}
	}

	@Override
	public String getStringToRender(ItemStack tool) {
		return getNumber(tool) + "%";
	}

	@Override
	public int getDefaultNumber(ItemStack stack) {
		return 0;
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (entity.world.isRemote)
			return;

		if (world.getTotalWorldTime() % 10 == 0 && (entity.isBurning() || ModTraits.pyrophoric.isBurning(tool))) {
			this.addNumber(tool, 1);
			return;
		}

		if (world.getTotalWorldTime() % 100 == 0 && TinkerUtil.hasTrait(TagUtil.getTagSafe(tool), ModTraits.burning.getIdentifier())) {
			this.addNumber(tool, 1);
			return;
		}

		if (isSelected && random.nextInt(100) == 0) {
			this.removeNumber(tool, 1);
		}
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (target.isBurning()) {
			this.addNumber(tool, 2);
		}
	}

	@Override
	public int getPriority() {
		return 10; // we need this to run last ish.
	}

	@Override
	public int getNumberMax(ItemStack stack) {
		return 100;
	}
}