package com.existingeevee.moretcon.client.actions;

import java.nio.charset.StandardCharsets;

import com.existingeevee.moretcon.MoreTCon;
import com.existingeevee.moretcon.NetworkHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ClientAction {

	@SideOnly(Side.CLIENT)
	public abstract void runAsClient(World world, double x, double y, double z);
	
	public void run(World world, double x, double y, double z) {
		if (MoreTCon.proxy.isClient()) {
			Runnable r = () -> this.runAsClient(world, x, y, z);;
			r.run();
		} else {
			NetworkHandler.HANDLER.sendToAllAround(new SentClientActionMessage(this.getClass().getName(), x, y ,z), new TargetPoint(world.provider.getDimension(), x, y, z, 200));
		}
	}
	
	public static class SentClientActionMessage implements IMessage, IMessageHandler<SentClientActionMessage, IMessage> {

		private String classPath = "";

		private double x;
		private double y;
		private double z;
		
		public SentClientActionMessage() {

		}

		public SentClientActionMessage(String classPath, double x, double y, double z) {
			if (classPath != null) {
				this.classPath = classPath;
				this.x = x;
				this.y = y;
				this.z = z;
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		public IMessage onMessage(SentClientActionMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				try {
					Class<? extends ClientAction> c = (Class<? extends ClientAction>) Class.forName(message.classPath);
					c.newInstance().run(Minecraft.getMinecraft().world, message.x, message.y, message.z);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			});
			return null;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			this.x = buf.readDouble();
			this.y = buf.readDouble();
			this.z = buf.readDouble();
			
			int len = buf.readInt();
			
			this.classPath = buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeDouble(this.x);
			buf.writeDouble(this.y);
			buf.writeDouble(this.z);
			
			buf.writeInt(this.classPath.length());

			buf.writeCharSequence(this.classPath, StandardCharsets.UTF_8);
		}
	}
	
}
