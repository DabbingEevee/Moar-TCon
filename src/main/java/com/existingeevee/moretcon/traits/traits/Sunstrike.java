package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.pwt.PersistantTickerInstance;
import com.existingeevee.moretcon.other.pwt.PersistantTickingHandler;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.tickers.SunMarkStrikeTicker;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class Sunstrike extends AbstractTrait {

	protected static final SunMarkStrikeTicker TICKER = new SunMarkStrikeTicker();

	public Sunstrike() {
		super(MiscUtils.createNonConflictiveName("sunstrike"), 0);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if (!wasHit) {
			return;
		}

		if (!player.world.isRemote && random.nextInt(1) == 0) {
			PersistantTickerInstance inst = new PersistantTickerInstance(TICKER, target.posX, target.posZ, player, tool.serializeNBT(), new NBTTagCompound());
			PersistantTickingHandler.addPersistantTicker(target.world, inst);
		}
	}
}
