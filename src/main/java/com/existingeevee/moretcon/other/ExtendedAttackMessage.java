package com.existingeevee.moretcon.other;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExtendedAttackMessage implements IMessage, IMessageHandler<ExtendedAttackMessage, IMessage> {

	static final String ticksSinceLastSwing = "field_184617_aD";

	int entID = -1;

	public ExtendedAttackMessage() {

	}

	@SideOnly(Side.CLIENT)
	public ExtendedAttackMessage(Entity target) {
		entID = target.getEntityId();
	}

	@Override
	public IMessage onMessage(ExtendedAttackMessage message, MessageContext ctx) {
		World world = ctx.getServerHandler().player.world;
		EntityPlayer sender = ctx.getServerHandler().player;
		Entity target = world.getEntityByID(message.entID);

		if (sender.getDistanceSq(target) > 25 * 25 || message.entID < 0 || target == null)
			return null;
		
		world.getMinecraftServer().addScheduledTask(() -> sender.attackTargetEntityWithCurrentItem(target));
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entID);
	}
}
