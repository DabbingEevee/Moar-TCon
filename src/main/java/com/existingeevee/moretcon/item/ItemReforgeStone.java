package com.existingeevee.moretcon.item;

import java.util.List;
import java.util.function.Supplier;

import com.existingeevee.moretcon.other.utils.TextHelper;
import com.existingeevee.moretcon.reforges.AbstractReforge;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.common.ClientProxy;
import slimeknights.tconstruct.library.client.CustomFontColor;

public class ItemReforgeStone extends ItemBase {

	final Supplier<AbstractReforge> reforge;

	public ItemReforgeStone(String itemName, Supplier<AbstractReforge> reforge, GlowType type, int hex) {
		super(itemName, type, hex); 
		this.reforge = reforge;
		this.maxStackSize = 1;
		this.setTab(null);
	}

	public ItemReforgeStone(String itemName, Supplier<AbstractReforge> reforge, int hex) {
		super(itemName, hex);
		this.reforge = reforge;
		this.maxStackSize = 1;
		this.setTab(null);
	}

	public ItemReforgeStone(String itemName, Supplier<AbstractReforge> reforge) {
		super(itemName);
		this.reforge = reforge;
		this.maxStackSize = 1;
		this.setTab(null);
	}

	public AbstractReforge getReforge() {
		return reforge.get();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public FontRenderer getFontRenderer(ItemStack stack) {
		return ClientProxy.fontRenderer;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.translateToLocal("item.reforgestone.name") + " (" + CustomFontColor.encodeColor(getReforge().getColor()) + getReforge().getLocalizedPrefix() + ChatFormatting.RESET + ")";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		String encodedColor = CustomFontColor.encodeColor(getReforge().getColor());

		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add("");
		tooltip.add(I18n.translateToLocal("item.reforgestone.desc"));
		TextHelper.smartSplitString(I18n.translateToLocal(getReforge().getLocalizedDescWithoutFlavor()), 45).forEach(s -> tooltip.add("  " + ChatFormatting.ITALIC + encodedColor + s));
	}
}
