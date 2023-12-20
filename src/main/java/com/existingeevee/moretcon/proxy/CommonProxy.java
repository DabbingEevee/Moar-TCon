package com.existingeevee.moretcon.proxy;

import com.existingeevee.moretcon.compat.betweenlands.BLItems;
import com.existingeevee.moretcon.entity.EntityInit;
import com.existingeevee.moretcon.inits.ModTools;
import com.existingeevee.moretcon.other.utils.CompatManager;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonProxy {

	public void preInit() {
		MinecraftForge.EVENT_BUS.register(this);
		EntityInit.init();
	}

	public void init() {

	}

	public void postInit() {

	}

	@SubscribeEvent
	public void itemRegistry(Register<Item> event) {
		if (CompatManager.thebetweenlands) {
			BLItems.initBL();
		}
		ModTools.init();
	}
	public void registerToolGui() {

	}

	public void clientRun(Runnable r) {
		
	}
	
	public boolean isClient() {
		return false;
	}

}
