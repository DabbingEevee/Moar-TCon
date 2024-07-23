package com.existingeevee.moretcon.inits;

import com.existingeevee.moretcon.reforges.reforges.HeavyReforge;
import com.existingeevee.moretcon.reforges.reforges.SharpenedReforge;

public class ModReforges {

	public static HeavyReforge reforgeHeavy;
	public static SharpenedReforge reforgeSharpened;
	
	public static void init() {
		reforgeHeavy = new HeavyReforge();
		reforgeSharpened = new SharpenedReforge();
	}
}
