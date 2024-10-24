package com.existingeevee.moretcon.client.actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleFirework.Overlay;
import net.minecraft.client.particle.ParticleFirework.Spark;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LightningEffectAction extends ClientAction {

	private static final Constructor<Overlay> __init__$Overlay = ObfuscationReflectionHelper.findConstructor(Overlay.class, World.class, double.class, double.class, double.class);

	@Override
	@SideOnly(Side.CLIENT)
	public void runAsClient(World world, double x, double y, double z, NBTBase data) {
		boolean big = false;
		double speedModifier = 0.1;
		if (data instanceof NBTTagCompound) {
			big = ((NBTTagCompound) data).getBoolean("big");
			speedModifier = ((NBTTagCompound) data).getDouble("speed");
		}

		if (big) {
			try {
				Minecraft.getMinecraft().effectRenderer.addEffect(__init__$Overlay.newInstance(world, x, y, z));
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			}
		} else {
			for (int i = 0; i < 50 * speedModifier; i++) {
				Minecraft.getMinecraft().effectRenderer.addEffect(new Spark(world, x, y, z, MiscUtils.randomN1T1() * speedModifier, MiscUtils.randomN1T1() * speedModifier, MiscUtils.randomN1T1() * speedModifier, Minecraft.getMinecraft().effectRenderer));
			}
		}
	}
}
