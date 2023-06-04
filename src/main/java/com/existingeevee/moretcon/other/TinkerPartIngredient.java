package com.existingeevee.moretcon.other;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.IMaterialItem;

public class TinkerPartIngredient extends Ingredient {
	
	private Material mat = null;
	
	public TinkerPartIngredient(Material mat, ItemStack...stacks) {
	    super(stacks);
		this.mat = mat;
	}
	
    public boolean apply(@Nullable ItemStack stack) {
    	if (stack.getItem() instanceof IMaterialItem) {
    		Material mat = ((IMaterialItem) stack.getItem()).getMaterial(stack);
    		if (mat.equals(this.mat)) {
    			return super.apply(stack);
    		}
    	} 
    	return false;
    }
}
