package com.existingeevee.moretcon.other;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class LeftClickEvent extends PlayerEvent {

	private float strength = 1;

	private RayTraceResult.Type clickType = RayTraceResult.Type.MISS;

	public LeftClickEvent(EntityPlayer player, float strength, RayTraceResult.Type clickType) {
		super(player);
		this.strength = strength;
		this.clickType = clickType;
	}

	public boolean isFullCharge() {
		return strength >= 0.95;
	}

	public float getStrength() {
		return strength;
	}

	public RayTraceResult.Type getClickType() {
		return clickType;
	}

	public static class NotifyEmptyLeftClickMessage implements IMessage {

		public NotifyEmptyLeftClickMessage() {
			this(1, RayTraceResult.Type.MISS);
		}

		public NotifyEmptyLeftClickMessage(float strength, RayTraceResult.Type typeOfHit) {
			this.strength = strength;
			this.typeOfHit = typeOfHit;
		}

		public float strength;
		public RayTraceResult.Type typeOfHit = RayTraceResult.Type.MISS;

		@Override
		public void fromBytes(ByteBuf buf) {
			this.strength = buf.readFloat();
			int next = buf.readInt();
			if (next < RayTraceResult.Type.values().length)
				this.typeOfHit = RayTraceResult.Type.values()[next];
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeFloat(strength);
			buf.writeInt(typeOfHit.ordinal());
		}

		public static class NotifyEmptyLeftClickMessageHandler implements IMessageHandler<NotifyEmptyLeftClickMessage, IMessage> {

			@Override
			public IMessage onMessage(NotifyEmptyLeftClickMessage message, MessageContext ctx) {
				World world = ctx.getServerHandler().player.world;
				world.getMinecraftServer().addScheduledTask(() -> {
					MinecraftForge.EVENT_BUS.post(new LeftClickEvent(ctx.getServerHandler().player, message.strength, message.typeOfHit));
				});
				return null;
			}
		}
	}
}
