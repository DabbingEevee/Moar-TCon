package com.existingeevee.moretcon.inits;

import com.existingeevee.moretcon.block.tile.TileGravitoniumFaucet;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.RegisterHelper;

public class ModTileEntities {

	public static void init() {
		if (CompatManager.loadMain) {
			RegisterHelper.registerTileEntity(TileGravitoniumFaucet.class);
		}
	}

}
