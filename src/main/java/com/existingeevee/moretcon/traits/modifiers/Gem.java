package com.existingeevee.moretcon.traits.modifiers;

import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;
import thebetweenlands.common.capability.circlegem.CircleGemType;

public class Gem extends ToolModifier {

	private final CircleGemType type;
	
	private static boolean registered = false;
	
	public Gem(CircleGemType type, Item item, int color) {
		super(MiscUtils.createNonConflictiveName("mod" + type.name().toLowerCase() + "gem"), color);
		this.addItem(item);
		if (ConfigHandler.middleGemsRequireModifierSlots) {
			this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this), ModifierAspect.freeModifier);
		} else {
			this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this));
		}
		this.type = type;
		if (!registered) {
			MinecraftForge.EVENT_BUS.register(Gem.class);
			registered = true;	
		}
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		ToolNBT data = TagUtil.getToolStats(rootCompound);
		rootCompound.setInteger("Gem", type.id);
		TagUtil.setToolTag(rootCompound, data.get());
	}

	@Override
	public boolean canApplyCustom(ItemStack stack) throws TinkerGuiException {
		if (stack.serializeNBT().getCompoundTag("tag").hasKey("Gem", 99)) {
			throw new TinkerGuiException(I18n.translateToLocal("text.err.middle_gem_only_once"));
		}
		return super.canApplyCustom(stack);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(value = Side.CLIENT)
	public static void handleToolTips(ItemTooltipEvent event) {
		if (event.getItemStack().getItem() instanceof ToolCore) {
            for (int i = 1; i < event.getToolTip().size() + 1; i++) {
                String last = StringUtils.stripControlCodes(event.getToolTip().get(event.getToolTip().size() - i));
                boolean found = false;
                for (String str : new String[] {
                        StringUtils.stripControlCodes(I18n.translateToLocal("tooltip.bl.circlegem.green")),
                        StringUtils.stripControlCodes(I18n.translateToLocal("tooltip.bl.circlegem.crimson")),
                        StringUtils.stripControlCodes(I18n.translateToLocal("tooltip.bl.circlegem.aqua")) }) {
                    if (last.equals(str)) {
                        event.getToolTip().remove(event.getToolTip().size() - i);
                        found = true;
                        break;
                    }
                }
                if (found)
                    break;
            }
        }
	}
}