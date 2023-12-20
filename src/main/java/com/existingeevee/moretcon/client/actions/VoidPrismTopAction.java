package com.existingeevee.moretcon.client.actions;

import com.existingeevee.moretcon.particle.ParticleBeamDust;
import com.existingeevee.moretcon.particle.ParticleSpawner;
import com.existingeevee.moretcon.particle.ParticleSpawner.ParticleProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VoidPrismTopAction extends ClientAction {

	@SideOnly(Side.CLIENT)
	private static final ParticleProvider outerSupplier = (world, x, y, z) -> {
		ParticleBeamDust p = new ParticleBeamDust(world, x, y, z, 5, 1200, 1.000f, 0.507f, 0.000f, 0.25f);
		p.addMotion(0, -.45, 0);
		return p;
	};

	@SideOnly(Side.CLIENT)
	private static final ParticleProvider innerSupplier = (world, x, y, z) -> {
		ParticleBeamDust p = new ParticleBeamDust(world, x, y, z, 2f, 1200, 1.000f, 0.0f, 0.000f, 0.85f);
		p.addMotion(0, -.45, 0);
		return p;
	};
	
	@Override
	@SideOnly(Side.CLIENT)
	public void runAsClient(World world, double x, double y, double z, NBTBase data) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleSpawner(world, x, y, z, 10, outerSupplier));
		Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleSpawner(world, x, y, z, 10, innerSupplier));		
	}

}
