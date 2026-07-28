package com.existingeevee.moretcon.mixin.softdep.contenttweaker;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.existingeevee.moretcon.compat.crafttweaker.contenttweaker.material.CoTTConUniqueMaterialBuilder;
import com.existingeevee.moretcon.compat.crafttweaker.contenttweaker.material.CoTUniqueTConMaterial;
import com.existingeevee.moretcon.item.tooltypes.Bomb.ExplosiveMaterialStats;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.teamacronymcoders.contenttweaker.modules.tinkers.materials.CoTTConMaterial;
import com.teamacronymcoders.contenttweaker.modules.tinkers.materials.CoTTConMaterialBuilder;
import com.teamacronymcoders.contenttweaker.modules.tinkers.materials.TConMaterialRepresentation;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.materials.Material;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.util.Pair;

@Mixin(value = CoTTConMaterialBuilder.class, remap = false)
public class MixinCoTTConMaterialBuilder {

	@Shadow
	List<Pair<String, String>> materialTraits;

	@Shadow
	String identifier;

	@Shadow
	int color;

	@Unique
	private ExplosiveMaterialStats explosiveMaterialStats = null;
	
	@Unique
	@ZenMethod("addExplosiveMaterialStats")
	public void moretcon$Unique$addExplosiveMaterialStats(double radius, int fuseTime) {
		this.explosiveMaterialStats = new ExplosiveMaterialStats(radius, fuseTime);
	}

	@Inject(method = "register", at = @At(value = "RETURN"), remap = false)
	public void moretcon$TAIL_Inject$register(CallbackInfoReturnable<TConMaterialRepresentation> ci) {
		if (explosiveMaterialStats != null) {
			TConMaterialRepresentation ret = ci.getReturnValue();
			Material mat = ret.getMaterial();
			mat.addStats(explosiveMaterialStats);
		}
	}

	@Inject(method = "register", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
	private void moretcon$INVOKE_add_Inject$register(CallbackInfoReturnable<TConMaterialRepresentation> ci, @Local LocalRef<CoTTConMaterial> ref) {
		if ((Object) this instanceof CoTTConUniqueMaterialBuilder) {
			CoTTConUniqueMaterialBuilder $this = (CoTTConUniqueMaterialBuilder) (Object) this;
			ref.set(new CoTUniqueTConMaterial(identifier, color, materialTraits, new ResourceLocation($this.getToolpart()), new ResourceLocation($this.getTool()), new ResourceLocation($this.getStation()), $this.getStationDesc()));
		}
	}
}
