package com.existingeevee.moretcon.client.actions;

import com.existingeevee.moretcon.client.particle.ParticleFieryPillar;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FieryPillarAction extends ClientAction {

	public static final FieryPillarAction INSTANCE = new FieryPillarAction();
	
	private FieryPillarAction() {
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void runAsClient(World world, double x, double y, double z, NBTBase data) {
		int minY = 0;
		if (data instanceof NBTTagInt) {
			minY = ((NBTTagInt) data).getInt();
		}
		
		Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleFieryPillar(world, x, y, z, minY));
		//Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleFieryPillar(world, x, y, z, minY));
	}
}
