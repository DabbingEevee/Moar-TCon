package com.existingeevee.moretcon.item;

import java.util.List;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.other.ICustomSlotRenderer;
import com.existingeevee.moretcon.other.ModTabs;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.other.utils.TextHelper;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBase extends Item implements ICustomSlotRenderer {

	protected CreativeTabs tab;

	public ItemBase(String itemName, int hex) {
		this(itemName);
		this.hex = hex;
	}

	public ItemBase(String itemName, GlowType type, int hex) {
		this(itemName);
		this.type = type;
		this.hex = hex;
	}

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

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRender(ItemStack stack) {
		return hex >= 0;
	}

	public void setupItem(final Item item, final String itemName) {
		item.setUnlocalizedName(MiscUtils.createNonConflictiveName(itemName));
	}

	public CreativeTabs getTab() {
		return tab;
	}

	public ItemBase setTab(CreativeTabs materials) {
		this.tab = materials;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (I18n.canTranslate(stack.getItem().getUnlocalizedName() + ".desc")) {
			String translation = I18n.translateToLocal(stack.getItem().getUnlocalizedName() + ".desc");
			if (!translation.contains("--null"))
				TextHelper.smartSplitString(translation, 35).forEach(tooltip::add);
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	private GlowType type = GlowType.CIRCLE;
	private int hex = -1;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void render(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
		ICustomSlotRenderer.simpleRender(stack, x, y, bakedmodel, type, hex);
	}
	
	public ItemBase withColor(int color) {
		this.hex = color;
		return this;
	}

	public ItemBase withType(GlowType type) {
		this.type = type;
		return this;
	}
}
