package com.existingeevee.moretcon.mixin.softdep.thebetweenlands;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ToolCore;
import thebetweenlands.common.entity.mobs.EntityFortressBossProjectile;

@Mixin(EntityFortressBossProjectile.class)
public abstract class MixinEntityFortressBossProjectile extends Entity {

	public MixinEntityFortressBossProjectile(World worldIn) {
		super(worldIn);
	}
	
	//this is a item to allow for non swords to carry the projectile
	//we swap out the item with a valid dummy one if conditions are correct hehe
	@Unique
	private final ItemStack dummyStack = new ItemStack(Items.WOODEN_SWORD); 
	
	@ModifyVariable(method = { "onUpdate", "func_70071_h_" }, at = @At("STORE"), ordinal = 0, remap = false)
	public ItemStack onUpdate(ItemStack heldItem) {
		if (heldItem != null && heldItem.getItem() instanceof ToolCore && ((ToolCore) heldItem.getItem()).hasCategory(Category.WEAPON))
			return dummyStack;
		return heldItem;
	}
	
	@ModifyVariable(method = { "attackEntityFrom", "func_70097_a" }, at = @At("STORE"), ordinal = 0, remap = false)
	public ItemStack attackEntityFrom(ItemStack heldItem) {
		if (heldItem != null && heldItem.getItem() instanceof ToolCore && ((ToolCore) heldItem.getItem()).hasCategory(Category.WEAPON))
			return dummyStack;
		return heldItem;
	}
}
