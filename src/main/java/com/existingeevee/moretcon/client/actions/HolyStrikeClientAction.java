package com.existingeevee.moretcon.client.actions;

import com.existingeevee.moretcon.client.particle.HolyStrikeParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HolyStrikeClientAction extends ClientAction {

	public static final HolyStrikeClientAction INSTANCE = new HolyStrikeClientAction();
 
	@Override
	@SideOnly(Side.CLIENT)
	public void runAsClient(World world, double x, double y, double z, NBTBase data) {
		double radius = 5;
		if (data instanceof NBTTagDouble) {
			radius = ((NBTTagDouble) data).getInt();
		}

		Minecraft.getMinecraft().effectRenderer.addEffect(new HolyStrikeParticle(world, x, z, radius));
	}
}
