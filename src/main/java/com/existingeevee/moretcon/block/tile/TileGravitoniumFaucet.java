package com.existingeevee.moretcon.block.tile;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import slimeknights.tconstruct.smeltery.tileentity.TileFaucet;

public class TileGravitoniumFaucet extends TileFaucet {
	@Override
	protected void pour() {
		if (drained == null) {
			return;
		}

		IFluidHandler toFill = getFluidHandler(pos.down(), EnumFacing.UP);
		if (toFill != null) {
			FluidStack fillStack = drained.copy();
			fillStack.amount = Math.min(drained.amount, LIQUID_TRANSFER * 6);

			// can we fill?
			int filled = toFill.fill(fillStack, false);
			if (filled > 0) {
				// transfer it
				this.drained.amount -= filled;
				fillStack.amount = filled;
				toFill.fill(fillStack, true);
			}
		} else {
			// filling TE got lost. reset. all liquid buffered is lost.
			reset();
		}
	}
}