package com.existingeevee.moretcon.item;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.materials.CompositeRegistry;
import com.existingeevee.moretcon.materials.CompositeRegistry.CompositeData;
import com.existingeevee.moretcon.other.utils.TextHelper;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
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

	@Override
	protected boolean isInCreativeTab(CreativeTabs targetTab) {
		return targetTab == CreativeTabs.SEARCH;
	}

	public static ItemStack getItem(Material type) {
		Optional<Integer> optional = CompositeRegistry.getCompositeIndex(type);

		if (optional.isPresent()) {
			return new ItemStack(itemInstance, 1, optional.get());
		}

		return ItemStack.EMPTY;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (I18n.canTranslate(stack.getItem().getUnlocalizedName() + ".desc")) {
			String translation = I18n.translateToLocal(stack.getItem().getUnlocalizedName() + ".desc");
			tooltip.add(translation);
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	public static ItemCompositeRep getItemInstance() {
		return itemInstance;
	}
}
