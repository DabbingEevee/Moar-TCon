package com.existingeevee.moretcon.compat.crafttweaker.contenttweaker.sponge;

import java.util.List;

import com.existingeevee.moretcon.other.sponge.SpongeRegistry.ItemGravitoniumSponge;
import com.existingeevee.moretcon.other.sponge.SpongeRegistry.SpongeRecipe;
import com.existingeevee.moretcon.other.utils.ModelRegistryHelper;
import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.client.models.IHasModel;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("rawtypes")
public class ItemCoTGravitoniumSponge extends ItemGravitoniumSponge implements IHasModel {

	IBaseMod mod;

	public ItemCoTGravitoniumSponge(SpongeRecipe recipe) {
		super(recipe);
		this.setUnlocalizedName("item" + recipe.getRecipeName() + "sponge");
	}

	@Override
	public Item getItem() {
		return this;
	}

	@Override
	public IBaseMod getMod() {
		return mod;
	}

	@Override
	public void setMod(IBaseMod mod) {
		this.mod = mod;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<ModelResourceLocation> getModelResourceLocations(List<ModelResourceLocation> models) {
		// yes ik that CoT has its own model system
		// im choosing to use my own because CoT's doesnt really handle damages well
		// mrow
		ModelRegistryHelper.registerSponge(this);
		return models;
	}
}
