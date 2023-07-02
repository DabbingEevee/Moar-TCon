package com.existingeevee.moretcon.materials;

import java.util.HashMap;
import java.util.Map;

import com.existingeevee.moretcon.inits.ModMaterials;
import com.existingeevee.moretcon.other.BiValue;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.book.content.ContentMaterial;
import slimeknights.tconstruct.library.book.sectiontransformer.AbstractMaterialSectionTransformer;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.modifiers.ModExtraTrait;

@SuppressWarnings("deprecation")
public class UniqueMaterial extends Material {

	public static final Map<String, BiValue<UniqueMaterial, String>> uniqueMaterials = new HashMap<String, BiValue<UniqueMaterial, String>>();

	public String toolResLoc;
	public String partResLoc;

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

		ToolPart part = UniqueMaterial.getToolPartFromResourceLocation(new ResourceLocation(partResLoc));
		if (part != null) {
			return part.getItemstackWithMaterial(this);
		}
		return ItemStack.EMPTY;
	}

	//this needs to be reworked
	
	@Override
	public String getLocalizedName() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		try {
			if (stacktrace[3].getClassName().equals(AbstractMaterialSectionTransformer.class.getName()) && stacktrace[3].getLineNumber() == 67/* .getMethodName().equals("transform") */) {
				return I18n.translateToLocal("material.uniquetoolpart.name") + " (" + I18n.translateToLocal("uniquetoolpart." + this.getIdentifier() + ".name") + ")";
			}
			if (stacktrace[3].getClassName().equals(ContentMaterial.class.getName()) && stacktrace[3].getLineNumber() == 64/* .equals("build") */) {
				return I18n.translateToLocal("material.uniquetoolpart.name") + " (" + I18n.translateToLocal("uniquetoolpart." + this.getIdentifier() + ".name") + ")";
			}
			if (stacktrace[2].getClassName().equals(ModExtraTrait.class.getName()) && stacktrace[2].getLineNumber() == 106) {/* .getMethodName().equals("getLocalizedDesc" */
				return I18n.translateToLocal("text.misc.one_of") + I18n.translateToLocal("uniquetoolpart." + this.getIdentifier() + ".name") + " " + I18n.translateToLocal(UniqueMaterial.getToolPartFromResourceLocation(new ResourceLocation(this.partResLoc)).getUnlocalizedName() + ".name");
			}
			if (stacktrace[2].getClassName().equals(ModExtraTrait.class.getName()) && stacktrace[2].getLineNumber() == 101/* .equals("getLocalizedName") */) {
				return I18n.translateToLocal("uniquetoolpart." + this.getIdentifier() + ".name");
			}
			if (stacktrace[3].getClassName().equals(ToolPart.class.getName()) && stacktrace[3].getLineNumber() == 203/* .getMethodName().equals("getItemStackDisplayName") */) {
				return I18n.translateToLocal("material.uniquetoolpart.name") + " (" + I18n.translateToLocal("uniquetoolpart." + this.getIdentifier() + ".name") + ")";
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return I18n.translateToLocal("material.uniquetoolpart.name");
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
				ModMaterials.forceSetRepItem(((UniqueMaterial) mat.getA()).getUniqueToolPart(), mat.getA());
			}
		}
	}

	public final boolean isCraftable() {
		return false;
	}

	public final boolean isCastable() {
		return false;
	}

}
