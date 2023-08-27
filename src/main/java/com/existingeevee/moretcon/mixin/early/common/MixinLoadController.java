package com.existingeevee.moretcon.mixin.early.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.transformer.Proxy;

import com.existingeevee.moretcon.ModInfo;

import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.ModClassLoader;
import net.minecraftforge.fml.common.ModContainer;

@Mixin(value = LoadController.class, remap = false)
public class MixinLoadController {

	@Shadow
	private Loader loader;

	@Inject(method = "distributeStateMessage(Lnet/minecraftforge/fml/common/LoaderState;[Ljava/lang/Object;)V", at = @At("HEAD"))
	private void moretcon$HEAD_Inject$distributeStateMessage(LoaderState state, Object[] eventData, CallbackInfo ci) throws Throwable {
		if (state == LoaderState.CONSTRUCTING) {
			ModClassLoader modClassLoader = (ModClassLoader) eventData[0];

			MixinBootstrap.init();
			Mixins.addConfiguration("mixins." + ModInfo.MODID + ".late.json");

			for (ModContainer container : this.loader.getActiveModList()) {
				modClassLoader.addFile(container.getSource());
			}

			Field field$transformer = Proxy.class.getDeclaredField("transformer");
			field$transformer.setAccessible(true);
			Object transformer = field$transformer.get(null);
			try {
				Method selectMethod = transformer.getClass().getDeclaredMethod("select", MixinEnvironment.class);
				selectMethod.setAccessible(true);
				selectMethod.invoke(transformer, MixinEnvironment.getCurrentEnvironment());
			} catch (NoSuchMethodException t) {
				Object processor = null;
				try {
					Method getProcessorMethod = transformer.getClass().getDeclaredMethod("getProcessor");
					getProcessorMethod.setAccessible(true);
					processor = getProcessorMethod.invoke(transformer);
				} catch (NoSuchMethodException t2) {
					Field processorField = transformer.getClass().getDeclaredField("processor");
					processorField.setAccessible(true);
					processor = processorField.get(transformer);
				}

				Method selectMethod = processor.getClass().getDeclaredMethod("select", MixinEnvironment.class);
				selectMethod.setAccessible(true);
				selectMethod.invoke(processor, MixinEnvironment.getCurrentEnvironment());
			}
		}
	}
}