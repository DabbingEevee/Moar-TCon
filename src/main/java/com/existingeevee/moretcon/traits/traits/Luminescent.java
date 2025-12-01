package com.existingeevee.moretcon.traits.traits;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.inits.ModMaterials;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.MaterialEvent.TraitRegisterEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.MaterialItem;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Luminescent extends AbstractTrait {

	public static final Map<Material, Integer> MATERIAL_COLOR_OVERRIDE = new HashMap<>();

	static {
		MATERIAL_COLOR_OVERRIDE.put(ModMaterials.materialFusionite, 0x0000ff);
		MATERIAL_COLOR_OVERRIDE.put(ModMaterials.materialTrichromadentium, 0xaaaaaa);
		MATERIAL_COLOR_OVERRIDE.put(ModMaterials.materialGravitonium, 0x00a000);
		MATERIAL_COLOR_OVERRIDE.put(ModMaterials.materialGeodesium, 0xffffff);
		MATERIAL_COLOR_OVERRIDE.put(ModMaterials.materialIonstone, 0xb4e5ff);
	}

	public Luminescent(String identifier, int color) {
		super(MiscUtils.createNonConflictiveName(identifier), color);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onTraitRegisterEvent(TraitRegisterEvent<Luminescent> event) {
		if (event.trait == this && ConfigHandler.disableLuminescent) {
			event.setCanceled(true);
		}
	}
	
	@Override
	public boolean isToolWithTrait(ItemStack itemStack) {
		return super.isToolWithTrait(itemStack);
	}

	private final Map<NBTTagCompound, Integer> map = new HashMap<>();

	public int calculateColor(ItemStack stack) {
		NBTTagCompound nbt = stack.serializeNBT();
		if (stack.getItem() instanceof MaterialItem) {
			Material m = ((MaterialItem) stack.getItem()).getMaterial(stack);
			int color = MATERIAL_COLOR_OVERRIDE.containsKey(m) ? MATERIAL_COLOR_OVERRIDE.get(m) : m.materialTextColor;
			map.put(nbt, color);
		}
		if (map.containsKey(nbt)) {
			return map.get(nbt);
		} else {
			int sumRed = 0;
			int sumGreen = 0;
			int sumBlue = 0;
			int sumTotal = 0;
			Set<Material> materials = new HashSet<>();

			materials.addAll(MiscUtils.getMaterials(stack));
			materials.addAll(MiscUtils.getEmbossments(stack));

			materials.removeIf(m -> !m.getAllTraits().contains(this));

			for (Material m : materials) {
				int col = MATERIAL_COLOR_OVERRIDE.containsKey(m) ? MATERIAL_COLOR_OVERRIDE.get(m) : m.materialTextColor;
				sumRed += (col & 0xFF0000) >> 16;
				sumGreen += (col & 0xFF00) >> 8;
				sumBlue += (col & 0xFF);
				sumTotal += 1;
			}

			if (sumTotal == 0) {
				map.put(nbt, Integer.MIN_VALUE);
			} else {
				Color color = new Color(sumRed / 255f / sumTotal, sumGreen / 255f / sumTotal, sumBlue / 255f / sumTotal);
				map.put(nbt, color.getRGB());
			}
			return map.get(nbt);
		}
	}
}
