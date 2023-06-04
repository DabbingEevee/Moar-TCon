package com.existingeevee.moretcon.other;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

public class BlockMaterials {
	public static final Material GAS = (new MaterialLiquid(MapColor.WATER));//.setNoPushMobility();
	public static final Material COOL_METALS = (new MaterialLiquid(MapColor.TNT) {
		@Override
	    public boolean blocksMovement()
	    {
	        return true;
	    }

		@Override
	    public boolean isSolid()
	    {
	        return true;
	    }
	});

	//public static final Material SEMISOLID = (new MaterialLiquid(MapColor.WATER));
}
