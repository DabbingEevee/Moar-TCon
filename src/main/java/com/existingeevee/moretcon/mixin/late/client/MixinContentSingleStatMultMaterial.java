package com.existingeevee.moretcon.mixin.late.client;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.materials.UniqueMaterial;
import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import slimeknights.mantle.client.book.data.element.TextData;
import slimeknights.mantle.client.gui.book.GuiBook;
import slimeknights.mantle.client.gui.book.element.BookElement;
import slimeknights.mantle.client.gui.book.element.ElementItem;
import slimeknights.mantle.client.gui.book.element.ElementText;
import slimeknights.tconstruct.library.book.content.ContentMaterial;
import slimeknights.tconstruct.library.book.content.ContentSingleStatMultMaterial;
import slimeknights.tconstruct.library.book.elements.ElementTinkerItem;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.library.traits.ITrait;

@Mixin(ContentSingleStatMultMaterial.class)
public class MixinContentSingleStatMultMaterial {

	@Inject(method = "addStatsDisplay", at = @At("HEAD"), remap = false, cancellable = true)
	private void moretcon$HEAD_Inject$addStatsDisplay(int x, int y, int w, ArrayList<BookElement> list, Material material, LinkedHashSet<ITrait> allTraits, String stattype, CallbackInfo ci) {
		if (material instanceof UniqueMaterial) {
			UniqueMaterial umat = (UniqueMaterial) material;
			IMaterialStats stats = material.getStats(stattype);
			if (stats == null) {
				return;
			}

			int x1 = 10;
			int x2 = 30;
			int x3 = 120;

			List<ITrait> traits = material.getAllTraitsForStats(stats.getIdentifier());

			// create a list of all valid toolparts with the stats
			List<ItemStack> parts = Lists.newLinkedList();

			ToolPart uniquepart = umat.getPartType();
			if (uniquepart.hasUseForStat(stats.getIdentifier())) {
				parts.add(umat.getUniqueToolPart());
			}

			// said parts next to the name
			if (parts.size() > 0) {
				ElementItem display = new ElementTinkerItem(x1, y + 1, 1f, parts);
				list.add(display);
			}

			List<TextData> lineData = ContentMaterial.getStatLines(stats);
			List<TextData> traitLineData = ContentMaterial.getTraitLines(traits, material);

			list.add(new ElementText(x2, y, w, GuiBook.PAGE_HEIGHT, lineData));
			if (!traitLineData.isEmpty()) {
				list.add(new ElementText(x3, y, w, GuiBook.PAGE_HEIGHT, traitLineData));
			}
			ci.cancel();
		}
	}
}
