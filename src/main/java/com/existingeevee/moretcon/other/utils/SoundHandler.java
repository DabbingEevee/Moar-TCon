package com.existingeevee.moretcon.other.utils;

import com.existingeevee.moretcon.ModInfo;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundHandler {

	public static SoundEvent BLOODY_SLASH;
	public static SoundEvent SWOOSH_EXPLOSION;
	public static SoundEvent ICY_EXPLOSION;

	public static void registerInit() {
		BLOODY_SLASH = registerSound("traits.bloody_arc.slash");
		SWOOSH_EXPLOSION = registerSound("traits.kinetic_battery.swoosh_explosion");
		ICY_EXPLOSION = registerSound("traits.hailshot.icy_explosion");
	}

	private static SoundEvent registerSound(String name) {
		ResourceLocation location = new ResourceLocation(ModInfo.MODID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}
