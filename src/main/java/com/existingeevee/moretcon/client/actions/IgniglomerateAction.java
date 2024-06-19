package com.existingeevee.moretcon.client.actions;

import com.existingeevee.moretcon.client.particle.ParticleIgniglomerateFlame;
import com.existingeevee.moretcon.client.particle.ParticleSpawner;
import com.existingeevee.moretcon.client.particle.ParticleSpawner.ParticleProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IgniglomerateAction extends ClientAction {

	public static final IgniglomerateAction INSTANCE = new IgniglomerateAction();
	
	private IgniglomerateAction() {
		
	}
	
	@SideOnly(Side.CLIENT)
	private static final ParticleProvider innerSupplier = (world, x, y, z) -> {
		ParticleIgniglomerateFlame p = new ParticleIgniglomerateFlame(world, x, y, z, 1200);
		p.addMotion((world.rand.nextDouble() - 0.5) / 8, .45, (world.rand.nextDouble() - 0.5) / 8);
		return p;
	};
	
	@Override
	@SideOnly(Side.CLIENT)
	public void runAsClient(World world, double x, double y, double z, NBTBase data) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleSpawner(world, x, y, z, 10, innerSupplier));	
	}

}
