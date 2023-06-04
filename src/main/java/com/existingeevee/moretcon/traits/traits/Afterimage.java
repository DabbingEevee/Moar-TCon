package com.existingeevee.moretcon.traits.traits;

import java.lang.reflect.Field;
import java.util.Random;

import com.existingeevee.moretcon.NetworkHandler;
import com.existingeevee.moretcon.other.Misc;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import slimeknights.tconstruct.library.tools.ranged.IProjectile;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Afterimage extends AbstractTrait {

	static final Random rand = new Random();

	static final Field ticksSinceLastSwing = ObfuscationReflectionHelper.findField(EntityLivingBase.class, "field_184617_aD");//EntityLivingBase.class.getDeclaredFields()[24];

	static {
		ticksSinceLastSwing.setAccessible(true);
	}

	public Afterimage() {
		super(Misc.createNonConflictiveName("afterimage"), 0);
	}

	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage,
			float knockback, float newKnockback, boolean isCritical) {
		if (tool.getItem() instanceof IProjectile) 
			return newKnockback;
		
		if (target.getEntityData().hasKey("afterimaged", NBT.TAG_LONG)
				&& target.getEntityData().getLong("afterimaged") >= player.world.getTotalWorldTime()) {
			return newKnockback + 2.5f;
		}
		return newKnockback;
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage,
			boolean isCritical) {
		if (tool.getItem() instanceof IProjectile) 
			return newDamage;
		
		if (target.getEntityData().hasKey("afterimaged", NBT.TAG_LONG)
				&& target.getEntityData().getLong("afterimaged") >= player.world.getTotalWorldTime()) {
			if (Minecraft.getMinecraft().player != null) {
				Minecraft.getMinecraft().player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1, 1);
			}
			AfterimageMessage msg = new AfterimageMessage();
			msg.t = 1;
			NetworkHandler.HANDLER.sendTo(msg, ((EntityPlayerMP) player));

			return (newDamage + 2) * 1.5f;
		}
		return newDamage;
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt,
			boolean wasCritical, boolean wasHit) {
		if (tool.getItem() instanceof IProjectile) 
			return;
		
		if (target.getEntityData().hasKey("afterimaged", NBT.TAG_LONG)
				&& target.getEntityData().getLong("afterimaged") >= player.world.getTotalWorldTime()) {
			target.getEntityData().removeTag("afterimaged");
		}
		
		if (rand.nextInt(5) == 0 && !player.world.isRemote) {
			target.hurtResistantTime = 0;
			try {
				ticksSinceLastSwing.set(player, Integer.MAX_VALUE);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			target.getEntityData().setLong("afterimaged", player.world.getTotalWorldTime() + 40);
			player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
			NetworkHandler.HANDLER.sendTo(new AfterimageMessage(), ((EntityPlayerMP) player));
		}
	}

	public static class AfterimageMessage implements IMessage, IMessageHandler<AfterimageMessage, IMessage> {

		byte t = 0;

		@Override
		public IMessage onMessage(AfterimageMessage message, MessageContext ctx) {
			if (t == 0) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					Minecraft.getMinecraft().player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
					try {
						ticksSinceLastSwing.set(Minecraft.getMinecraft().player, Integer.MAX_VALUE / 2);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				});
			}
			if (t == 1) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					Minecraft.getMinecraft().player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1, 1);
				});
			}
			return null;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			t = buf.readByte();
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeByte(t);
		}
	}

}
