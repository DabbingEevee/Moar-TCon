package com.existingeevee.moretcon.client.actions;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.client.RenderUtil;

public class ColoredDustAction extends ClientAction {

	public static final ColoredDustAction INSTANCE = new ColoredDustAction();
	
	private ColoredDustAction() {
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void runAsClient(World world, double x, double y, double z, NBTBase data) {
		int color = 0xffffff;
		if (data instanceof NBTTagInt) {
			color = ((NBTTagInt) data).getInt();
		}
		
		float r = RenderUtil.red(color) / 255f;
		float g = RenderUtil.green(color) / 255f;
		float b = RenderUtil.blue(color) / 255f;
		
		world.spawnParticle(EnumParticleTypes.REDSTONE, true, x, y, z, r == 0 ? 0.0001f : r, g, b);	
	}
}
