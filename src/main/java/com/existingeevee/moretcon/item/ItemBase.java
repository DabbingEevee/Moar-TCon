package com.existingeevee.moretcon.item;

import java.util.List;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.Misc;
import com.existingeevee.moretcon.other.ModTabs;

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
		if (stack.getItem().getRegistryName().toString().equals(ModInfo.MODID + ":lightning")) {
			tooltip.add(I18n.translateToLocal("tooltip.moretcon.lightning")); // "Get a betweenland lightning bolt to
																				// strike your tool to apply."
		}
		if (stack.getItem().getRegistryName().getResourcePath().toLowerCase().startsWith("repitem")) {
			tooltip.add(I18n.translateToLocal("tooltip.moretcon.repitem"));// "This material is a composite. Craft the
																			// base toolparts and pour the catalyst on
																			// using a casting table.");
		}
		if (stack.getItem().getRegistryName().toString().equals(ModInfo.MODID + ":matterreconstructiongel")) {
			tooltip.add(I18n.translateToLocal("tooltip.moretcon.repair"));// "Adding this to any tool will repair 256
																			// damage on the tool.");
		}
		if (stack.getItem().getRegistryName().toString().equals(ModInfo.MODID + ":betweenifiedmodifier")) {
			tooltip.add(I18n.translateToLocal("tooltip.moretcon.betweenify"));// "Adding this to any tool will allow for
																				// its full efficiency to be used in the
																				// Betweenlands.");
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	//static final Field fire_imm = ObfuscationReflectionHelper.findField(Entity.class, "field_70178_ae");//EntityLivingBase.class.getDeclaredFields()[24];
	



}
