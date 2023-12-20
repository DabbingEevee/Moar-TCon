package com.existingeevee.moretcon.client.actions;

import com.existingeevee.moretcon.particle.ParticleSpawner;
import com.existingeevee.moretcon.particle.ParticleSpawner.ParticleProvider;
import com.existingeevee.moretcon.particle.ParticleVoidDust;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VoidCoreAction extends ClientAction {

	@SideOnly(Side.CLIENT)
	private static final ParticleProvider outerSupplier = (world, x, y, z) -> new ParticleVoidDust(world, x + rOff(), y + rOff() / 10, z + rOff(), 20, 40, 0.05f, 0.05f, 0.05f, 0.25f, false);
	private static final ParticleProvider innerSupplier = (world, x, y, z) -> new ParticleVoidDust(world, x + rOff() * 2.5, y + rOff() * 2.5, z + rOff() * 2.5, 8f, 40, 0.0001f, 0.0001f, 0.0001f, 0.85f, true);

	@Override
	@SideOnly(Side.CLIENT)
	public void runAsClient(World world, double x, double y, double z, NBTBase data) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleSpawner(world, x, y, z, 10, innerSupplier));
		Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleSpawner(world, x, y, z, 10, outerSupplier));
	}

	private static double rOff() {
		return (Math.random() - 0.5) * 0.2;
	}
	
}
