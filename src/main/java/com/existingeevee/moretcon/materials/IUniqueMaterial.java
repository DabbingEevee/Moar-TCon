package com.existingeevee.moretcon.materials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.utils.MirrorUtils;
import com.existingeevee.moretcon.other.utils.MirrorUtils.IField;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.modifiers.ModExtraTrait2;
import com.google.common.collect.ImmutableList;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.MaterialEvent.MaterialRegisterEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolCraftingEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolModifyEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolPartReplaceEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.modifiers.ModExtraTrait;
import slimeknights.tconstruct.tools.ranged.item.BoltCore;

@EventBusSubscriber(modid = ModInfo.MODID)
public interface IUniqueMaterial {

	public static final Set<IUniqueMaterial> uniqueMaterials = new HashSet<>();
	public static final ThreadLocal<Boolean> reentrant = ThreadLocal.withInitial(() -> false);

	default ItemStack getUniqueToolPart() {
		if ((TinkerRegistry.getMaterial(((Material) this).identifier) == null) || TinkerRegistry.getMaterial(((Material) this).getIdentifier()).getIdentifier().equals(Material.UNKNOWN.getIdentifier()) || (UniqueMaterial.getToolFromResourceLocation(getToolResLoc()) == null)) {
			return ItemStack.EMPTY;
		}

		ToolPart part = getPartType();
		if (part != null) {
			if (part instanceof BoltCore)
				return BoltCore.getItemstackWithMaterials(TinkerMaterials.wood, ((Material) this));
			return part.getItemstackWithMaterial(((Material) this));
		}
		return ItemStack.EMPTY;
	}

	default ToolPart getPartType() {
		return UniqueMaterial.getToolPartFromResourceLocation(getPartResLoc());
	}

	default String getUniqueLocName(@Nullable String defName) {

		if (defName == null) {
			defName = I18n.translateToLocal("uniquetoolpart." + ((Material) this).getIdentifier() + ".name");
		}

		String partName = UniqueMaterial.getToolPartFromResourceLocation(this.getPartResLoc()).getUnlocalizedName() + ".name";

		try {
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

			// embossment
			if ((stacktrace[3].getClassName().equals(ModExtraTrait.class.getName()) || stacktrace[3].getClassName().equals(ModExtraTrait2.class.getName())) && stacktrace[3].getMethodName().equals("getLocalizedDesc")) {
				return I18n.translateToLocal("text.misc.one_of") + defName + " " + partName;
			}
			if ((stacktrace[3].getClassName().equals(ModExtraTrait.class.getName()) || stacktrace[3].getClassName().equals(ModExtraTrait2.class.getName())) && stacktrace[3].getMethodName().equals("getLocalizedName")) {
				return defName;
			}

			// book
			if ((stacktrace[4].getClassName().equals("slimeknights.tconstruct.library.book.sectiontransformer.AbstractMaterialSectionTransformer") && stacktrace[4].getMethodName().equals("transform")) ||
					(stacktrace[4].getClassName().equals("slimeknights.tconstruct.library.book.sectiontransformer.BowMaterialSectionTransformer") && (stacktrace[4].getMethodName().equals("generateContent"))) ||
					(stacktrace[4].getClassName().equals("slimeknights.tconstruct.library.book.content.ContentSingleStatMultMaterial") && (stacktrace[4].getMethodName().equals("build"))) ||
					(stacktrace[4].getClassName().equals("slimeknights.tconstruct.library.book.content.ContentMaterial") && (stacktrace[4].getMethodName().equals("build")))) {
				return I18n.translateToLocal("material.uniquetoolpart.name") + " (" + defName + ")";
			}

			// bolts
			if (stacktrace[4].getClassName().equals(BoltCore.class.getName()) && (stacktrace[4].getMethodName().equals("getItemStackDisplayName") || stacktrace[4].getMethodName().equals("func_77653_i"))) {
				return I18n.translateToLocal("material.uniquetoolpart.name") + " (" + defName + ")";
			}
			if (stacktrace[5].getClassName().equals(BoltCore.class.getName()) && (stacktrace[5].getMethodName().equals("getItemStackDisplayName") || stacktrace[5].getMethodName().equals("func_77653_i"))) {
				return I18n.translateToLocal("material.uniquetoolpart.name") + " (" + defName + ")";
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}

		return I18n.translateToLocal("material.uniquetoolpart.name");
	}

	default String getUniqueLocItemName(@Nullable String defName, String itemName) {
		if (defName == null) {
			defName = I18n.translateToLocal("uniquetoolpart." + ((Material) this).getIdentifier() + ".name");
		}
		return I18n.translateToLocal("material.uniquetoolpart.name") + " (" + defName + ") " + itemName;
	}

	ItemStack getCrafter();

	String getCrafterString();

	ResourceLocation getToolResLoc();

	ResourceLocation getPartResLoc();

	static final IField<Set<IToolPart>> field$neededPart = MirrorUtils.reflectField(PartMaterialType.class, "neededPart");
	static final List<Supplier<Material>> standardMats = Arrays.asList(
			() -> TinkerRegistry.getMaterial(TinkerMaterials.iron.identifier), 
			() -> TinkerRegistry.getMaterial(TinkerMaterials.wood.identifier), 
			() -> TinkerRegistry.getMaterial(TinkerMaterials.feather.identifier));

	@SuppressWarnings("unlikely-arg-type")
	default ItemStack buildSampleTool() {
		ToolCore tool = UniqueMaterial.getToolFromResourceLocation(this.getToolResLoc());
		if (tool != null) {
			boolean added = false;
			ImmutableList.Builder<Material> builder = ImmutableList.builder();
			for (PartMaterialType pmt : tool.getRequiredComponents()) {
				Set<IToolPart> neededPart = field$neededPart.get(pmt);

				if (!added && pmt.isValidMaterial((Material) this) && neededPart.contains(getUniqueToolPart().getItem())) {
					builder.add((Material) this);
					added = true;
				} else {
					boolean found = false;
					for (Supplier<Material> m : standardMats) {
						if (m.get() != Material.UNKNOWN && pmt.isValidMaterial(m.get())) {
							builder.add(m.get());
							found = true;
							break;
						}
					}
					if (!found)
						for (Material m : TinkerRegistry.getAllMaterials()) {
							if (pmt.isValidMaterial(m)) {
								builder.add(m);
								found = true;
								break;
							}
						}

					if (!found) {
						builder.add(Material.UNKNOWN);
					}
				}
			}

			ItemStack builtTool = tool.buildItem(builder.build());
			return builtTool;
		}
		return ItemStack.EMPTY;
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void handleUniqueToolParts(ToolCraftingEvent event) {
		for (ItemStack part : event.getToolParts()) {
			Material mat = TinkerUtil.getMaterialFromStack(part);
			if (mat instanceof IUniqueMaterial && !event.getItemStack().getItem().getRegistryName().equals(((IUniqueMaterial) mat).getToolResLoc())) {
				event.setCanceled(I18n.translateToLocal("text.err.unique.not_correct_tool"));// "You can only use unique tool parts on the correct tool.");
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void handleUniqueToolParts(ToolPartReplaceEvent event) {
		for (ItemStack part : event.getToolParts()) {
			Material mat = TinkerUtil.getMaterialFromStack(part);
			if (mat instanceof IUniqueMaterial && !event.getItemStack().getItem().getRegistryName().equals(((IUniqueMaterial) mat).getToolResLoc())) {
				event.setCanceled(I18n.translateToLocal("text.err.unique.not_correct_tool"));
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void handleToolModifyEvent(ToolModifyEvent event) {
		List<IUniqueMaterial> pre = MiscUtils.getUniqueEmbossments(event.getToolBeforeModification());
		List<IUniqueMaterial> post = MiscUtils.getUniqueEmbossments(event.getItemStack());

		if (!pre.containsAll(post)) {
			List<IUniqueMaterial> diff = new ArrayList<>(post);
			diff.retainAll(pre);

			for (IUniqueMaterial mat : diff) {
				if (UniqueMaterial.getToolFromResourceLocation(mat.getToolResLoc()) != event.getItemStack().getItem()) {
					event.setCanceled(I18n.translateToLocal("text.err.unique.not_correct_tool"));
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(value = Side.CLIENT)
	public static void handleToolTips(ItemTooltipEvent event) {
		try {
			if (event.getItemStack().getItem() instanceof ToolPart) {
				Material mat = TinkerUtil.getMaterialFromStack(event.getItemStack());

				if (mat instanceof IUniqueMaterial) {
					ResourceLocation partResLoc = ((IUniqueMaterial) mat).getPartResLoc();
					ResourceLocation toolResLoc = ((IUniqueMaterial) mat).getToolResLoc();

					if (UniqueMaterial.getToolFromResourceLocation(toolResLoc) instanceof ToolCore) {
						ToolCore tool = UniqueMaterial.getToolFromResourceLocation(toolResLoc);
						Item part = UniqueMaterial.getToolPartFromResourceLocation(partResLoc);

						int i = 1;
						event.getToolTip().add(i++, "");
						if (part != event.getItemStack().getItem()) {
							event.getToolTip().add(i++, "" + ChatFormatting.RED + ChatFormatting.BOLD + I18n.translateToLocal("text.err.unique.unobtainable"));
							event.getToolTip().add(i++, "");
						}
						event.getToolTip().add(i++, ChatFormatting.GRAY + I18n.translateToLocal("text.err.unique.only_make").replace("__s__", tool.getLocalizedName()));
						event.getToolTip().add(i++, "");

					}
				}
			}
		} catch (NullPointerException e) {
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void materialRegister(MaterialRegisterEvent e) {
		if (e.isCanceled() || !(e.material instanceof IUniqueMaterial))
			return;

		uniqueMaterials.add((IUniqueMaterial) e.material);
	}
}
