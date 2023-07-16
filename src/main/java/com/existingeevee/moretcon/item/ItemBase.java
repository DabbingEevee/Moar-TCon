package com.existingeevee.moretcon.item;

import java.util.List;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.other.Misc;
import com.existingeevee.moretcon.other.ModTabs;
import com.existingeevee.moretcon.other.utils.TextHelper;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemBase extends Item {

	protected CreativeTabs tab;

	public ItemBase(String itemName) {
		super();
		setupItem(this, itemName.toLowerCase());
		setTab(ModTabs.moarTConMaterials);
	}

	public ItemBase(String itemName, CreativeTabs tab) {
		super();
		setupItem(this, itemName.toLowerCase());
		setTab(tab);
	}

	public void setupItem(final Item item, final String itemName) {
		item.setUnlocalizedName(Misc.createNonConflictiveName(itemName));
	}

	public CreativeTabs getTab() {
		return tab;
	}

	public ItemBase setTab(CreativeTabs materials) {
		this.tab = materials;
		return this;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (I18n.canTranslate(stack.getItem().getUnlocalizedName() + ".desc")) {
			String translation = I18n.translateToLocal(stack.getItem().getUnlocalizedName() + ".desc");
			if (!translation.contains("--null"))
				TextHelper.smartSplitString(translation, 35).forEach(tooltip::add);
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
