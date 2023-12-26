package com.existingeevee.moretcon.mixinext;

import java.util.List;
import java.util.Set;

import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import net.minecraftforge.fml.common.Loader;

public final class MoreTConMixinPlugin implements IMixinConfigPlugin {

	private static final String START = "com.existingeevee.moretcon.mixin.softdep.";

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if (mixinClassName.startsWith(START)) {
			String modid = mixinClassName.replaceFirst(START, "").split("\\.")[0];
			System.out.println(modid + " " + Loader.isModLoaded(modid));
			return Loader.isModLoaded(modid);
		} 
		return true;
	}

	// Boilerplate

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public void onLoad(String mixinPackage) {

	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}
}