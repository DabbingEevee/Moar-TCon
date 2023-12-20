package com.existingeevee.moretcon.materials;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.existingeevee.moretcon.other.BiValue;
import com.existingeevee.moretcon.other.Misc;
import com.existingeevee.moretcon.other.utils.MaterialUtils;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.book.content.ContentMaterial;
import slimeknights.tconstruct.library.book.content.ContentSingleStatMultMaterial;
import slimeknights.tconstruct.library.book.sectiontransformer.AbstractMaterialSectionTransformer;
import slimeknights.tconstruct.library.book.sectiontransformer.BowMaterialSectionTransformer;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolCraftingEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolModifyEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolPartReplaceEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.tools.modifiers.ModExtraTrait;

@SuppressWarnings("deprecation")
public class UniqueMaterial extends Material {

	public static final Map<String, BiValue<UniqueMaterial, String>> uniqueMaterials = new HashMap<String, BiValue<UniqueMaterial, String>>();

	private String toolResLoc;
	private String partResLoc;

	private Supplier<ItemStack> crafterSupplier = () -> new ItemStack(Blocks.CRAFTING_TABLE);
	private String craftingDescKey = "crafting_table";
	
	public UniqueMaterial(String identifier, int color, ToolPart part, ToolCore tool, Supplier<ItemStack> crafterSupplier, String craftingDescKey) {
		this(identifier, color, part, tool);
		this.crafterSupplier = crafterSupplier;
		this.craftingDescKey = craftingDescKey;
	}

	public UniqueMaterial(String identifier, int color, String part, String tool, Supplier<ItemStack> crafterSupplier, String craftingDescKey) {
		this(identifier, color, part, tool);
		this.crafterSupplier = crafterSupplier;
		this.craftingDescKey = craftingDescKey;
	}
	
	public UniqueMaterial(String identifier, int color, ToolPart part, ToolCore tool) {
		this(identifier, color);
		this.partResLoc = part.getRegistryName().toString();
		this.toolResLoc = tool.getRegistryName().toString();
		uniqueMaterials.put(this.identifier, new BiValue<UniqueMaterial, String>(this, part.getRegistryName().toString()));
	}

	public UniqueMaterial(String identifier, int color, String part, String tool) {
		this(identifier, color);
		this.partResLoc = new ResourceLocation(part).toString();
		this.toolResLoc = new ResourceLocation(tool).toString();
		uniqueMaterials.put(this.identifier, new BiValue<UniqueMaterial, String>(this, part));
	}

	private UniqueMaterial(String identifier, int color) {
		super(identifier, color, false);
		MinecraftForge.EVENT_BUS.register(this);
		this.setCastable(false);
		this.setCraftable(false);
	}

	public ItemStack getUniqueToolPart() {
		if (TinkerRegistry.getMaterial(this.identifier) == null) {
			return ItemStack.EMPTY;
		}
		if (TinkerRegistry.getMaterial(this.identifier).getIdentifier().equals(Material.UNKNOWN.getIdentifier())) {
			return ItemStack.EMPTY;
		}

		if (UniqueMaterial.getToolFromResourceLocation(new ResourceLocation(toolResLoc)) == null) {
			return ItemStack.EMPTY;
		}

		ToolPart part = getPartType();
		if (part != null) {
			return part.getItemstackWithMaterial(this);
		}
		return ItemStack.EMPTY;
	}

	public ToolPart getPartType() {
		return UniqueMaterial.getToolPartFromResourceLocation(new ResourceLocation(partResLoc));
	}
	
	@Override
	public String getLocalizedName() {
		try {
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			if (stacktrace[3].getClassName().equals(AbstractMaterialSectionTransformer.class.getName()) && stacktrace[3].getMethodName().equals("transform")) {
				return I18n.translateToLocal("material.uniquetoolpart.name") + " (" + I18n.translateToLocal("uniquetoolpart." + this.getIdentifier() + ".name") + ")";
			}
			if (stacktrace[3].getClassName().equals(ContentMaterial.class.getName()) && stacktrace[3].getMethodName().equals("build")) {
				return I18n.translateToLocal("material.uniquetoolpart.name") + " (" + I18n.translateToLocal("uniquetoolpart." + this.getIdentifier() + ".name") + ")";
			}
			if (stacktrace[2].getClassName().equals(ModExtraTrait.class.getName()) && stacktrace[2].getMethodName().equals("getLocalizedDesc")) {
				return I18n.translateToLocal("text.misc.one_of") + I18n.translateToLocal("uniquetoolpart." + this.getIdentifier() + ".name") + " " + I18n.translateToLocal(UniqueMaterial.getToolPartFromResourceLocation(new ResourceLocation(this.partResLoc)).getUnlocalizedName() + ".name");
			}
			if (stacktrace[2].getClassName().equals(ModExtraTrait.class.getName()) && stacktrace[2].getMethodName().equals("getLocalizedName")) {
				return I18n.translateToLocal("uniquetoolpart." + this.getIdentifier() + ".name");
			}
			if (stacktrace[3].getClassName().equals(ToolPart.class.getName()) && (stacktrace[3].getMethodName().equals("getItemStackDisplayName") || stacktrace[3].getMethodName().equals("func_77653_i"))) {
				return I18n.translateToLocal("material.uniquetoolpart.name") + " (" + I18n.translateToLocal("uniquetoolpart." + this.getIdentifier() + ".name") + ")";
			}
			if (stacktrace[3].getClassName().equals(BowMaterialSectionTransformer.class.getName()) && (stacktrace[3].getMethodName().equals("generateContent"))) {
				return I18n.translateToLocal("material.uniquetoolpart.name") + " (" + I18n.translateToLocal("uniquetoolpart." + this.getIdentifier() + ".name") + ")";
			}
			if (stacktrace[3].getClassName().equals(ContentSingleStatMultMaterial.class.getName()) && (stacktrace[3].getMethodName().equals("build"))) {
				return I18n.translateToLocal("material.uniquetoolpart.name") + " (" + I18n.translateToLocal("uniquetoolpart." + this.getIdentifier() + ".name") + ")";
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return I18n.translateToLocal("material.uniquetoolpart.name");
	}

	public static ToolPart getToolPart(String res) {
		return getToolPartFromResourceLocation(new ResourceLocation(res));
	}
	
	public static ToolPart getToolPartFromResourceLocation(ResourceLocation res) {
		for (IToolPart part : TinkerRegistry.getToolParts()) {
			if (part instanceof ToolPart) {
				if (((ToolPart) part).getRegistryName().equals(res)) {
					return (ToolPart) part;
				}
			}
		}
		return null;
	}

	public static ToolCore getToolFromResourceLocation(ResourceLocation res) {
		for (ToolCore tool : TinkerRegistry.getTools()) {
			if (tool.getRegistryName().equals(res)) {
				return tool;
			}
		}
		return null;
	}

	public static void onPostInit() {
		for (BiValue<UniqueMaterial, String> mat : uniqueMaterials.values()) {
			if (mat.getA() instanceof UniqueMaterial && !TinkerRegistry.getMaterial(mat.getA().getIdentifier()).getIdentifier().equals(Material.UNKNOWN.getIdentifier())) {
				MaterialUtils.forceSetRepItem(((UniqueMaterial) mat.getA()).getUniqueToolPart(), mat.getA());
			}
		}
	}

	public final boolean isCraftable() {
		return false;
	}

	public final boolean isCastable() {
		return false;
	}

	public ItemStack getCrafter() {
		return crafterSupplier.get();
	}
	
	public String getCrafterString() {
		return craftingDescKey;
	}

	public String getToolResLoc() {
		return toolResLoc;
	}

	public String getPartResLoc() {
		return partResLoc;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleUniqueToolParts(ToolCraftingEvent event) {
		for (ItemStack part : event.getToolParts()) {
			if (TinkerUtil.getMaterialFromStack(part) == this && !event.getItemStack().getItem().getRegistryName().toString().equals(getToolResLoc())) {
				event.setCanceled(I18n.translateToLocal("text.err.unique.not_correct_tool"));// "You can only use unique tool parts on the correct tool.");
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleUniqueToolParts(ToolPartReplaceEvent event) {
		for (ItemStack part : event.getToolParts()) {
			if (TinkerUtil.getMaterialFromStack(part) == this && !event.getItemStack().getItem().getRegistryName().toString().equals(getToolResLoc())) {
				event.setCanceled(I18n.translateToLocal("text.err.unique.not_correct_tool"));
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void handleToolModifyEvent(ToolModifyEvent event) {
		Material pre = Misc.getUniqueEmbossment(event.getToolBeforeModification());
		Material post = Misc.getUniqueEmbossment(event.getItemStack());
		if (pre == null && post == this) {
			if (!UniqueMaterial.getToolFromResourceLocation(new ResourceLocation(getToolResLoc())).getRegistryName().equals(event.getItemStack().getItem().getRegistryName()))
				event.setCanceled(I18n.translateToLocal("text.err.unique.not_correct_tool"));
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(value = Side.CLIENT)
	public void handleToolTips(ItemTooltipEvent event) {
		try {
			if (event.getItemStack().getItem() instanceof ToolPart) {
				if (TinkerUtil.getMaterialFromStack(event.getItemStack()) == this) {
					if (UniqueMaterial.getToolFromResourceLocation(new ResourceLocation(getToolResLoc())) != null) {
						int i = 1;
						event.getToolTip().add(i++, "");
						if (!(getPartResLoc().equals(event.getItemStack().getItem().getRegistryName().toString()))) {
							event.getToolTip().add(i++, "§4§l" + I18n.translateToLocal("text.err.unique.unobtainable"));
							event.getToolTip().add(i++, "");
						}
						event.getToolTip().add(i++, "§7" + I18n.translateToLocal("text.err.unique.only_make").replace("__s__", UniqueMaterial.getToolFromResourceLocation(new ResourceLocation(getToolResLoc())).getLocalizedName()));
						event.getToolTip().add(i++, "");

					}
				}
			}
		} catch (NullPointerException e) {
		}
	}
}
