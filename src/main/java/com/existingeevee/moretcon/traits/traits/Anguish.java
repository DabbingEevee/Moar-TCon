package com.existingeevee.moretcon.traits.traits;

import java.text.DecimalFormat;
import java.util.List;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Anguish extends AbstractTrait {

	public Anguish() {
		super(MiscUtils.createNonConflictiveName("anguish"), 0);
	}

	private static final DecimalFormat FORMATTER = new DecimalFormat("0.##");
	
	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		float x = ToolHelper.getActualDamage(tool, player);
		float quarticComponent = Math.min(80, 7e-7f * x * x * x * x);
		float naturalLogComponent = (float) Math.max(0, Math.log(x));
		return newDamage + quarticComponent + naturalLogComponent;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
		String loc = String.format(LOC_Extra, getModifierIdentifier());

		float x;
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null) {
			x = ToolHelper.getActualAttack(tool) + 1;

			if (tool.getItem() instanceof ToolCore) {
				x = ToolHelper.calcCutoffDamage(x, ((ToolCore) tool.getItem()).damageCutoff());
			}
		} else {
			x = ToolHelper.getActualDamage(tool, player);
		}
		
		float quarticComponent = Math.min(80, 0.0000007f * x * x * x * x);
		float naturalLogComponent = (float) Math.max(0, Math.log(x));

		return ImmutableList.of(Util.translateFormatted(loc, FORMATTER.format(quarticComponent + naturalLogComponent)));
	}
}