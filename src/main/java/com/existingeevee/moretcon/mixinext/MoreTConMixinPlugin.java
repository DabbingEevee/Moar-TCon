package com.existingeevee.moretcon.mixinext;

import java.util.List;
import java.util.Set;

import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import net.minecraftforge.fml.common.Loader;

public final class MoreTConMixinPlugin implements IMixinConfigPlugin {

	private static final String START = "com.existingeevee.moretcon.mixin.softdep.";

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if (mixinClassName.startsWith(START)) {
			String[] modids = mixinClassName.replaceFirst(START, "").split("\\.")[0].split("$");
			for (String modid : modids) {
				if (!Loader.isModLoaded(modid))
					return false;
			}
			return true;
		} 
		return true;
	}

	// me when the boiler is plate

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
	public void preApply(String targetClassName, org.objectweb.asm.tree.ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		
	}

	@Override
	public void postApply(String targetClassName, org.objectweb.asm.tree.ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		
	}

}