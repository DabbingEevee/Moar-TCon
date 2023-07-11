package com.existingeevee.moretcon.item;

import java.util.List;
import java.util.Optional;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.materials.CompositeRegistry;
import com.existingeevee.moretcon.materials.CompositeRegistry.CompositeData;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.materials.Material;

@SuppressWarnings("deprecation")
public class ItemCompositeRep extends ItemBase {

	private static ItemCompositeRep itemInstance;

	public ItemCompositeRep() {
		super("repitem");
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(null);

		itemInstance = this;
	}

	@Override
	public boolean getHasSubtypes() {
		return true;
	}

	@Override
	public boolean isDamageable() {
		return false;
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 64;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		List<CompositeData> data = CompositeRegistry.getData();

		Material mat = Material.UNKNOWN;
		if (stack.getItemDamage() < data.size()) {
			mat = data.get(stack.getItemDamage()).getResult();
		}
		return I18n.translateToLocal("item.moretcon.repitem.name").replace("-{-name-}-", mat.getLocalizedName());
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (int i = 0; i < CompositeRegistry.getData().size(); i++) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	public static ItemStack getItem(Material type) {
		Optional<Integer> optional = CompositeRegistry.getCompositeIndex(type);

		if (optional.isPresent()) {
			return new ItemStack(itemInstance, 1, optional.get());
		}

		return ItemStack.EMPTY;
	}

	public static ItemCompositeRep getItemInstance() {
		return itemInstance;
	}

	@SideOnly(Side.CLIENT)
	public static void updateCompositeRenderer() {
		//this will get registered later than other renderers. Add-ons will need to run this again after theyre done registering composites or it wont render right
		
		List<CompositeData> data = CompositeRegistry.getData();

		for (int i = 0; i < data.size(); i++) {
			ModelLoader.setCustomModelResourceLocation(itemInstance, i, new ModelResourceLocation(
					ModInfo.MODID + ":" + "repitem" + data.get(i).getResult().identifier, "inventory"));	
		}
	}

}
