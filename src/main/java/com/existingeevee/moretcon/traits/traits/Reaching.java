package com.existingeevee.moretcon.traits.traits;

import java.util.List;
import java.util.UUID;

import com.existingeevee.moretcon.NetworkHandler;
import com.existingeevee.moretcon.other.ExtendedAttackMessage;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Reaching extends AbstractTraitLeveled {
	public static final UUID REACH_MODIFIER = UUID.fromString("df6eabe7-ffff-0000-9099-002f90370708");

	public Reaching(int level) {
		super(MiscUtils.createNonConflictiveName("reaching"), 0, 3, level);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
		if (slot != EntityEquipmentSlot.MAINHAND) {
			return;
		}

		NBTTagList tagList = TagUtil.getModifiersTagList(stack);
		int index = TinkerUtil.getIndexInCompoundList(tagList, getModifierIdentifier());

		if (index > -1) {
			ModifierNBT modifier = ModifierNBT.readTag(tagList.getCompoundTagAt(index));
			attributeMap.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(REACH_MODIFIER, "reach modifier", modifier.level * 2 - 2, 0));
		}
	}

	public boolean shouldHaveReach(ItemStack stack) {
		return this.isToolWithTrait(stack) && !ToolHelper.isBroken(stack);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onMouseClick(PlayerInteractEvent.LeftClickEmpty event) {
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = player.getHeldItemMainhand();
		NBTTagList tagList = TagUtil.getModifiersTagList(stack);
		int index = TinkerUtil.getIndexInCompoundList(tagList, getModifierIdentifier());

		if (index > -1 && shouldHaveReach(stack)) {
			ModifierNBT modifier = ModifierNBT.readTag(tagList.getCompoundTagAt(index));
			List<Entity> exclude = player.getRidingEntity() == null ? null : Lists.newArrayList(player.getRidingEntity());
			RayTraceResult result = MiscUtils.rayTrace(player, 4 + 2 * modifier.level, exclude);
			if (result != null && result.entityHit != null) {
				if (!player.isCreative()) {
					ToolHelper.damageTool(stack, 1, player);
				}
				NetworkHandler.HANDLER.sendToServer(new ExtendedAttackMessage(result.entityHit));
			}
		}
	}
}
