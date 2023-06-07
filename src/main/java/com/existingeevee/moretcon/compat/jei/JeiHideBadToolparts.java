package com.existingeevee.moretcon.compat.jei;

import java.util.Map.Entry;

import com.existingeevee.moretcon.materials.UniqueMaterial;
import com.existingeevee.moretcon.other.BiValue;

import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants.NBT;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;

public class JeiHideBadToolparts extends JeiCustomContainer {

	public JeiHideBadToolparts() {
		super(null, () -> true);
	}

	@Override
	public void onRun(IModRegistry r) {
		IIngredientBlacklist blacklist = r.getJeiHelpers().getIngredientBlacklist();
		for (Entry<String, BiValue<UniqueMaterial, String>> mat : UniqueMaterial.uniqueMaterials.entrySet()) {
			for (IToolPart part : TinkerRegistry.getToolParts()) {
				blacklist.addIngredientToBlacklist(part.getItemstackWithMaterial(mat.getValue().getA()));
			}
			blacklist.removeIngredientFromBlacklist(UniqueMaterial
					.getToolPartFromResourceLocation(new ResourceLocation(mat.getValue().getA().partResLoc))
					.getItemstackWithMaterial(mat.getValue().getA()));

			for (ToolCore tool : TinkerRegistry.getTools()) {
				NonNullList<ItemStack> stacks = NonNullList.create();
				tool.getSubItems(TinkerRegistry.tabTools, stacks);
				for (ItemStack s : stacks) {
					NBTTagList list = s.getTagCompound().getCompoundTag("TinkerData").getTagList("Materials",
							NBT.TAG_STRING);
					boolean found = false;
					for (NBTBase base : list) {
						String id = ((NBTTagString) base).getString();
						if (mat.getValue().getA().getIdentifier().equals(id)) {
							found = true;
							blacklist.addIngredientToBlacklist(s);
							break;
						}
					}
					if (found) {
						break;
					}
				}
			}
		}
	}
}
