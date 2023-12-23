package com.existingeevee.moretcon.traits.traits;

import java.lang.reflect.Field;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.traits.abst.NumberTrackerTrait;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class Soulforged extends NumberTrackerTrait {

	public Soulforged(int lvl) {
		super(MiscUtils.createNonConflictiveName("soulforged"), 0, 3, lvl);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
		if (this.getNumber(tool) > 0) {
			newDamage = Math.max(newDamage + 4, newDamage * 1.1f);
			if (random.nextInt(2) == 0)
				this.removeNumber(tool, 1);
		}
		return newDamage;
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
		if (this.getNumber(tool) > 0) {
			event.setNewSpeed(event.getNewSpeed() * 4);
		}
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		if (this.getNumber(tool) > 0 && random.nextInt(5) == 0) {
			this.removeNumber(tool, 1);
		}
	}
	
	@Override
	public int getNumberMax(ItemStack stack) {
		ModifierNBT tag = ModifierNBT.readTag(TinkerUtil.getModifierTag(stack, this.getModifierIdentifier()));
		return 25 * tag.level;
	}

	private static final Field recentlyHit$EntityLivingBase = ObfuscationReflectionHelper.findField(EntityLivingBase.class, "field_70718_bc");
	private static final Field attackingPlayer$EntityLivingBase = ObfuscationReflectionHelper.findField(EntityLivingBase.class, "field_70717_bb");

	@SubscribeEvent
	public void onDie(LivingDeathEvent event) {
		try {
			EntityLivingBase killerLiving = (EntityLivingBase) attackingPlayer$EntityLivingBase.get(event.getEntityLiving());
			if (killerLiving != null && recentlyHit$EntityLivingBase.getInt(event.getEntityLiving()) > 0) {
				for (EnumHand hand : EnumHand.values()) {
					ItemStack stack = killerLiving.getHeldItem(hand);
					if (ToolHelper.getTraits(stack).contains(this)) {
						this.addNumber(stack, random.nextInt(2) + 1);
						return;
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
