package com.existingeevee.moretcon.traits.traits.armor;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.other.utils.ReequipHack;
import com.existingeevee.moretcon.other.utils.SoundHandler;
import com.existingeevee.moretcon.traits.traits.abst.NumberTrackerTrait;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.IArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Overload extends NumberTrackerTrait implements IArmorTrait {

	public Overload() {
		super(MiscUtils.createNonConflictiveName("overload_armor"), 0);
		ReequipHack.registerIgnoredKey(this.getModifierIdentifier());
	}

	@Override
	public int getNumberMax(ItemStack stack) {
		return 25;
	}

	public static final ThreadLocal<Boolean> REENTRANT = ThreadLocal.withInitial(() -> false);

	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		if (REENTRANT.get()) {
			return;
		}

		REENTRANT.set(true);
		try {
			double totalMax = 0, total = 0;

			for (ItemStack stack : event.getEntityLiving().getArmorInventoryList()) {
				if (this.isToolWithTrait(stack) && !ToolHelper.isBroken(stack)) {
					totalMax += this.getNumberMax(stack);
					total += this.addNumber(stack, Math.round(event.getAmount()));
				}
			}

			if (totalMax > 0 && totalMax - total < 1e-6) {
				Team team = event.getEntityLiving().getTeam();

				EntityLivingBase player = event.getEntityLiving();

				player.world.playSound(null, player.getPosition(), SoundHandler.SWOOSH_EXPLOSION, SoundCategory.PLAYERS, 1, 1);

				if (player.world instanceof WorldServer) {
					SPacketParticles spacketparticles = new SPacketParticles(EnumParticleTypes.EXPLOSION_LARGE, true, (float) player.posX, (float) player.posY + 0.5f, (float) player.posZ, 0, 0, 0, 0, 1);
					for (EntityPlayerMP p : player.world.getPlayers(EntityPlayerMP.class, p -> true)) {
						if (p.getPositionVector().squareDistanceTo(player.getPositionVector()) < 100 * 100) {
							p.connection.sendPacket(spacketparticles);
						}
					}
				}

				for (Entity e : event.getEntityLiving().world.getEntitiesInAABBexcluding(event.getEntityLiving(), MiscUtils.vectorBound(event.getEntityLiving().getPositionVector(), event.getEntityLiving().getPositionVector()).grow(totalMax / 10.), e -> e instanceof EntityLivingBase)) {
					if (team == null || team.getAllowFriendlyFire() || e.getTeam() != team) {
						e.attackEntityFrom(event.getEntityLiving() instanceof EntityPlayer ? DamageSource.causePlayerDamage((EntityPlayer) event.getEntityLiving()) : DamageSource.causeMobDamage(event.getEntityLiving()), (float) (totalMax / 4 * Math.exp(1 / (totalMax / 10. * totalMax / 10.) * -player.getPositionVector().squareDistanceTo(e.getPositionVector()))));
					}
				}

				for (ItemStack stack : event.getEntityLiving().getArmorInventoryList()) {
					if (this.isToolWithTrait(stack)) {
						this.setNumber(stack, 0);
					}
				}
			}
		} finally {
			REENTRANT.remove();
		}
	}

	@Override
	public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot) {
		return mods;
	}

	@Override
	public void onItemPickup(ItemStack armor, EntityItem item, EntityItemPickupEvent evt) {

	}

	@Override
	public float onHeal(ItemStack armor, EntityPlayer player, float amount, float newAmount, LivingHealEvent evt) {
		return newAmount;
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt) {

		return newDamage;
	}

	@Override
	public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt) {
		return newDamage;
	}

	@Override
	public void onKnockback(ItemStack armor, EntityPlayer player, LivingKnockBackEvent evt) {

	}

	@Override
	public void onFalling(ItemStack armor, EntityPlayer player, LivingFallEvent evt) {

	}

	@Override
	public void onJumping(ItemStack armor, EntityPlayer player, LivingJumpEvent evt) {

	}

	@Override
	public void onAbilityTick(int level, World world, EntityPlayer player) {

	}

	@Override
	public void onArmorEquipped(ItemStack armor, EntityPlayer player, int slot) {

	}

	@Override
	public void onArmorRemoved(ItemStack armor, EntityPlayer player, int slot) {

	}

	@Override
	public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
		return newDamage;
	}

	@Override
	public int onArmorHeal(ItemStack armor, DamageSource source, int amount, int newAmount, EntityPlayer player, int slot) {
		return newAmount;
	}

	@Override
	public boolean disableRendering(ItemStack armor, EntityLivingBase entityLivingBase) {
		return false;
	}

	@Override
	public int getPriority() {
		return -2;
	}
}
