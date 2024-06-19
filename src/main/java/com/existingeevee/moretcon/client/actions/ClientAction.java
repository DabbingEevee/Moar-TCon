package com.existingeevee.moretcon.client.actions;

import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;

import com.existingeevee.moretcon.MoreTCon;
import com.existingeevee.moretcon.NetworkHandler;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ClientAction {

	protected ClientAction() {
	}

	@SideOnly(Side.CLIENT)
	public abstract void runAsClient(World world, double x, double y, double z, NBTBase data);

	public void run(World world, double x, double y, double z, NBTBase data) {
		if (MoreTCon.proxy.isClient()) {
			Runnable r = () -> this.runAsClient(world, x, y, z, data);
			r.run();
		} else {
			NetworkHandler.HANDLER.sendToDimension(new SentClientActionMessage(this.getClass().getName(), x, y, z, data), world.provider.getDimension());
		}
	}

	@SideOnly(Side.CLIENT)
	public void run(double x, double y, double z, NBTBase data) {
		run(Minecraft.getMinecraft().world, x, y, z, data);
	}

	public static class SentClientActionMessage implements IMessage, IMessageHandler<SentClientActionMessage, IMessage> {

		private String classPath = "";

		private double x;
		private double y;
		private double z;

		private NBTBase tag;

		public SentClientActionMessage() {

		}

		public SentClientActionMessage(String classPath, double x, double y, double z, NBTBase tag) {
			if (classPath != null) {
				this.classPath = classPath;
				this.x = x;
				this.y = y;
				this.z = z;
				this.tag = tag;
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		public IMessage onMessage(SentClientActionMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				try {
					Class<? extends ClientAction> c = (Class<? extends ClientAction>) Class.forName(message.classPath);
					Constructor<? extends ClientAction> constructor = c.getConstructor();
					constructor.setAccessible(true);
					constructor.newInstance().run(message.x, message.y, message.z, message.tag);
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
			try {
				int len = buf.readInt();
				this.classPath = buf.readCharSequence(len, StandardCharsets.UTF_8).toString();

				len = buf.readInt();
				String tag = buf.readCharSequence(len, StandardCharsets.UTF_8).toString();
				this.tag = JsonToNBT.getTagFromJson(tag).getTag("data");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeDouble(this.x);
			buf.writeDouble(this.y);
			buf.writeDouble(this.z);

			buf.writeInt(this.classPath.length());
			buf.writeCharSequence(this.classPath, StandardCharsets.UTF_8);

			NBTTagCompound payload = new NBTTagCompound();
			if (this.tag != null)
				payload.setTag("data", this.tag);
			String payloadString = payload.toString();
			buf.writeInt(payloadString.length());
			buf.writeCharSequence(payloadString, StandardCharsets.UTF_8);
		}
	}

}
