package com.existingeevee.moretcon.traits.traits.armor;

import java.util.ArrayList;
import java.util.List;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.traits.abst.ISimpleArmorTrait;

import c4.conarm.common.armor.utils.ArmorHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Evasive extends AbstractTraitLeveled implements ISimpleArmorTrait {

	public Evasive(int level) {
		super(MiscUtils.createNonConflictiveName("evasive_armor"), 0xffffff, 3, level);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingAtkEvent(LivingAttackEvent event) {
		if (event.getEntity().world.isRemote || event.getAmount() <= 0 || event.isCanceled())
			return; 

		if (event.getSource().isFireDamage() && (event.getEntityLiving().isPotionActive(MobEffects.FIRE_RESISTANCE) || event.getEntityLiving().isImmuneToFire())) 
			return;
		
		int total = 0;

		List<ItemStack> stacks = new ArrayList<>();

		for (ItemStack s : event.getEntityLiving().getArmorInventoryList()) {
			if (event.getEntityLiving() instanceof EntityPlayer) {
				CooldownTracker cooldowns = ((EntityPlayer) event.getEntityLiving()).getCooldownTracker();
				
				if (cooldowns.hasCooldown(s.getItem()))
					continue;
				
				if (((EntityPlayer) event.getEntityLiving()).capabilities.isCreativeMode) {
					return;
				}
			}

			if (this.isToolWithTrait(s) && !ToolHelper.isBroken(s)) {
				ModifierNBT tag = ModifierNBT.readTag(TinkerUtil.getModifierTag(s, this.getModifierIdentifier()));
				total += tag.level;
				stacks.add(s);
			}
		}

		if (random.nextInt(100) < total) {
			event.setCanceled(true);
			event.getEntityLiving().world.playSound(null, event.getEntityLiving().getPosition(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 2, 0);
			if (event.getEntityLiving().world instanceof WorldServer) {
				SPacketParticles spacketparticles = new SPacketParticles(EnumParticleTypes.EXPLOSION_LARGE, true, (float) event.getEntityLiving().posX, (float) event.getEntityLiving().posY + 0.5f, (float) event.getEntityLiving().posZ, 0, 0, 0, 0, 1);
				for (EntityPlayerMP p : event.getEntityLiving().world.getPlayers(EntityPlayerMP.class, p -> true)) {
					if (p.getPositionVector().squareDistanceTo(event.getEntityLiving().getPositionVector()) < 100 * 100) {
						p.connection.sendPacket(spacketparticles);
					}
				}
			}
			if (event.getEntityLiving() instanceof EntityPlayer) {
				CooldownTracker cooldowns = ((EntityPlayer) event.getEntityLiving()).getCooldownTracker();
				for (ItemStack stack : stacks) {
					ArmorHelper.damageArmor(stack, event.getSource(), 1, (EntityPlayer) event.getEntityLiving());
					cooldowns.setCooldown(stack.getItem(), 50);
				}
			}
			return;
		}
	}
}
