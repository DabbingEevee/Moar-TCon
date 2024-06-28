package com.existingeevee.moretcon.mixin.late.client;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.existingeevee.moretcon.materials.CompositeRegistry;
import com.existingeevee.moretcon.materials.CompositeRegistry.CompositeData;
import com.existingeevee.moretcon.materials.UniqueMaterial;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import slimeknights.mantle.client.gui.book.element.BookElement;
import slimeknights.mantle.client.gui.book.element.ElementItem;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.book.content.ContentMaterial;
import slimeknights.tconstruct.library.book.elements.ElementTinkerItem;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.block.BlockCasting;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.common.block.BlockToolTable;
import slimeknights.tconstruct.tools.harvest.TinkerHarvestTools;
import slimeknights.tconstruct.tools.melee.TinkerMeleeWeapons;
import slimeknights.tconstruct.tools.ranged.TinkerRangedWeapons;

@Mixin(ContentMaterial.class)
public abstract class MixinContentMaterial {

	@Shadow(remap = false)
	private transient Material material;
	@Unique
	private static final Field field$neededPart = ObfuscationReflectionHelper.findField(PartMaterialType.class, "neededPart");

	@Redirect(method = "addStatsDisplay", at = @At(value = "INVOKE", ordinal = 0, target = "Lslimeknights/tconstruct/library/tools/IToolPart;hasUseForStat(Ljava/lang/String;)Z"), remap = false)
	private boolean moretcon$INVOKE_Redirect$addStatsDisplay(IToolPart tp, String string) {
		if (material instanceof UniqueMaterial) {
			UniqueMaterial unique = (UniqueMaterial) material;
			if (((Item) tp).getRegistryName().toString().equals(unique.getPartResLoc())) {
				return true;
			}
			return false;
		}

		return tp.hasUseForStat(string);
	}
	
	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	@Inject(method = "addDisplayItems", at = @At("HEAD"), remap = false, cancellable = true)
	private void moretcon$HEAD_Inject$addDisplayItems(ArrayList<BookElement> list, int x, CallbackInfo ci) throws IllegalAccessException {
		ContentMaterial $this = (ContentMaterial) (Object) this;

		int y = 10;
		List<ElementItem> displayTools = Lists.newArrayList();

		if (!material.getRepresentativeItem().isEmpty()) {
			displayTools.add(new ElementTinkerItem(material.getRepresentativeItem())); //ToolModel
		}

		if (material instanceof UniqueMaterial) {
			UniqueMaterial unique = (UniqueMaterial) material;
			ElementItem elementItem = new ElementTinkerItem(unique.getCrafter());
			elementItem.tooltip = Arrays.asList(I18n.format("text.uniquely_crafted." + unique.getCrafterString()).split("__n__"));
			displayTools.add(elementItem);
		}
		if (CompositeRegistry.getComposite(material).isPresent()) {
			CompositeData data = CompositeRegistry.getComposite(material).get();
			ItemStack casting = new ItemStack(TinkerSmeltery.castingBlock, 1, BlockCasting.CastingType.TABLE.getMeta());
			ElementItem elementItem = new ElementTinkerItem(casting);
			String text = I18n.format("text.composite")
					.replace("__s__", data.getFrom().getLocalizedName())
					.replace("__l__", data.getCatalyst().getLocalizedName(new FluidStack(data.getCatalyst(), 0)));
			elementItem.tooltip = Arrays.asList(text.split("__n__"));
			displayTools.add(elementItem);
		}
		if (material.isCraftable()) {
			ItemStack partbuilder = new ItemStack(TinkerTools.toolTables, 1, BlockToolTable.TableTypes.PartBuilder.meta);
			ElementItem elementItem = new ElementTinkerItem(partbuilder);
			elementItem.tooltip = ImmutableList.of($this.parent.translate("material.craft_partbuilder"));
			displayTools.add(elementItem);
		}
		if (material.isCastable()) {
			ItemStack basin = new ItemStack(TinkerSmeltery.castingBlock, 1, BlockCasting.CastingType.BASIN.getMeta());
			ElementItem elementItem = new ElementTinkerItem(basin);
			String text = $this.parent.translate("material.craft_casting");
			elementItem.tooltip = ImmutableList.of(String.format(text, material.getFluid().getLocalizedName(new FluidStack(material.getFluid(), 0))));
			displayTools.add(elementItem);
		}

		if (material instanceof UniqueMaterial) { 
			UniqueMaterial unique = (UniqueMaterial) material;
			ToolCore tool = UniqueMaterial.getToolFromResourceLocation(new ResourceLocation(unique.getToolResLoc()));
			if (tool != null) {
				boolean added = false;
				ImmutableList.Builder<Material> builder = ImmutableList.builder();
				for (int i = 0; i < tool.getRequiredComponents().size(); i++) {
					PartMaterialType pmt = tool.getRequiredComponents().get(i);
					Set<IToolPart> neededPart = (Set<IToolPart>) field$neededPart.get(pmt);
					if (!added && neededPart.contains(unique.getUniqueToolPart().getItem())) {
						builder.add(material);
						added = true;
					} else {
						builder.add(TinkerRegistry.getMaterial(TinkerMaterials.iron.identifier)); //we'd rather get unknown than a unregistered material
					}
				}
				ItemStack builtTool = tool.buildItem(builder.build());
				displayTools.add(new ElementTinkerItem(builtTool));
			}
		} else {
			ToolCore[] tools = new ToolCore[] { TinkerHarvestTools.pickaxe, TinkerHarvestTools.mattock, TinkerMeleeWeapons.broadSword,
					TinkerHarvestTools.hammer, TinkerMeleeWeapons.cleaver, TinkerRangedWeapons.shuriken,
					TinkerMeleeWeapons.fryPan, TinkerHarvestTools.lumberAxe, TinkerMeleeWeapons.battleSign };

			for (ToolCore tool : tools) {
				if (tool == null) {
					continue;
				}
				ImmutableList.Builder<Material> builder = ImmutableList.builder();
				for (int i = 0; i < tool.getRequiredComponents().size(); i++) {
					builder.add(material);
				}
				ItemStack builtTool = tool.buildItem(builder.build());
				if (tool.hasValidMaterials(builtTool)) {
					displayTools.add(new ElementTinkerItem(builtTool));
				}

				if (displayTools.size() == 9) {
					break;
				}
			}
		}

		// built tools
		if (!displayTools.isEmpty()) {
			for (ElementItem element : displayTools) {
				element.x = x;
				element.y = y;
				element.scale = 1f;
				y += ElementItem.ITEM_SIZE_HARDCODED;
				list.add(element);
			}
		}
		ci.cancel();
	}
}
