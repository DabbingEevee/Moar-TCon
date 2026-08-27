package com.existingeevee.moretcon.mixin.late.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.materials.CompositeRegistry;
import com.existingeevee.moretcon.materials.CompositeRegistry.CompositeData;
import com.existingeevee.moretcon.materials.IUniqueMaterial;
import com.existingeevee.moretcon.other.CustomBookCraftingDisplay;
import com.existingeevee.moretcon.other.CustomBookCraftingDisplay.DisplayData;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.client.gui.book.element.BookElement;
import slimeknights.mantle.client.gui.book.element.ElementItem;
import slimeknights.tconstruct.library.book.content.ContentMaterial;
import slimeknights.tconstruct.library.book.elements.ElementTinkerItem;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.block.BlockCasting;

@Mixin(value = ContentMaterial.class, remap = false)
public abstract class MixinContentMaterial {

	@Shadow
	private transient Material material;

	@WrapOperation(method = "addStatsDisplay", at = @At(value = "INVOKE", ordinal = 0, target = "Lslimeknights/tconstruct/library/tools/IToolPart;hasUseForStat(Ljava/lang/String;)Z"))
	private boolean moretcon$INVOKE_Redirect$addStatsDisplay(IToolPart tp, String string, Operation<Boolean> original) {
		if (material instanceof IUniqueMaterial) {
			IUniqueMaterial unique = (IUniqueMaterial) material;
			return ((Item) tp).getRegistryName().equals(unique.getPartResLoc());
		}

		return original.call(tp, string);
	}

	@Inject(method = "addDisplayItems", at = @At(value = "FIELD", target = "Lslimeknights/tconstruct/tools/harvest/TinkerHarvestTools;pickaxe:Lslimeknights/tconstruct/library/tools/ToolCore;", opcode = Opcodes.GETSTATIC, shift = At.Shift.BEFORE))
	private void moretcon$FIELD_Inject$addDisplayItems(ArrayList<BookElement> list, int x, CallbackInfo ci, @Local List<ElementItem> displayTools) {
		if (CompositeRegistry.getComposite(material).isPresent()) {
			CompositeData data = CompositeRegistry.getComposite(material).get();
			ItemStack casting = new ItemStack(TinkerSmeltery.castingBlock, 1, BlockCasting.CastingType.TABLE.getMeta());
			ElementItem elementItem = new ElementTinkerItem(casting);
			String text = I18n.format("text.composite").replace("__s__", data.getFrom().getLocalizedName()).replace("__l__", data.getCatalyst().getLocalizedName(new FluidStack(data.getCatalyst(), 0)));
			elementItem.tooltip = Arrays.asList(text.split("__n__"));
			displayTools.add(elementItem);
		}

		if (CustomBookCraftingDisplay.has(material)) {
			for (DisplayData data : CustomBookCraftingDisplay.getData(material)) {
				ElementItem elementItem = new ElementTinkerItem(data.getRenderedStack());
				String text = data.getRenderedString();
				elementItem.tooltip = Arrays.asList(text.split("__n__"));
				displayTools.add(elementItem);
			}
		}

		if (material instanceof IUniqueMaterial) {
			IUniqueMaterial unique = (IUniqueMaterial) material;
			ElementItem elementItem = new ElementTinkerItem(unique.getCrafter());
			elementItem.tooltip = Arrays.asList(I18n.format("text.uniquely_crafted." + unique.getCrafterString()).split("__n__"));
			displayTools.add(elementItem);
		}
	}

	@Definition(id = "tools", local = @Local(type = ToolCore[].class))
	@Expression("@(tools)")
	@Inject(method = "addDisplayItems", at = @At("MIXINEXTRAS:EXPRESSION"))
	private void moretcon$EXPRESSION_tools_Inject$addDisplayItems(ArrayList<BookElement> list, int x, CallbackInfo ci, @Local List<ElementItem> displayTools, @Local LocalRef<ToolCore[]> tools) {
		if (material instanceof IUniqueMaterial && tools.get().length > 0) {
			IUniqueMaterial unique = (IUniqueMaterial) material;
			displayTools.add(new ElementTinkerItem(unique.buildSampleTool()));
			tools.set(new ToolCore[0]);
		}
	}
}
