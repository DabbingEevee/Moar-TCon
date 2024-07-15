package com.existingeevee.moretcon.other;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.ranged.item.BoltCore;

public class BreakApartBoltCoreRecipe extends Impl<IRecipe> implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		boolean found = false;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.isEmpty()) {
				continue;
			}
			if (stack.getItem() instanceof BoltCore && !found) {
				found = true;
			} else {
				return false;
			}
		}

		return found;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack found = ItemStack.EMPTY;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.isEmpty()) {
				continue;
			}
			if (stack.getItem() instanceof BoltCore && found.isEmpty()) {
				found = stack;
			} else {
				return ItemStack.EMPTY;
			}
		}

		if (found.isEmpty()) {
			return found;
		}

		Material mat = ((BoltCore) found.getItem()).getMaterial(found);

		return TinkerTools.arrowShaft.getItemstackWithMaterial(mat);
	}

	@Override
	public boolean canFit(int width, int height) {
		return width * height >= 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return TinkerTools.arrowShaft.getItemstackWithMaterial(TinkerMaterials.iron);
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		for (int i = 0; i < ret.size(); i++) {
			if (inv.getStackInSlot(i).getItem() instanceof BoltCore) {
				Material mat = BoltCore.getHeadMaterial(inv.getStackInSlot(i));
				ItemStack shard = mat.getShard();
				shard.setCount(288 / Material.VALUE_Shard);
				ret.set(i, shard);
			} else {
				ret.set(i, ForgeHooks.getContainerItem(inv.getStackInSlot(i)));
			}
		}
		return ret;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}
}
