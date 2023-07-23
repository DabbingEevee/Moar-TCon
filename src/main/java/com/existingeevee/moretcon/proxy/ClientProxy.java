package com.existingeevee.moretcon.proxy;

import com.existingeevee.moretcon.compat.betweenlands.BLItems;
import com.existingeevee.moretcon.entity.EntityInit;
import com.existingeevee.moretcon.inits.ModTools;
import com.existingeevee.moretcon.materials.CompositeRegistry;
import com.existingeevee.moretcon.materials.MaterialClient;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.RegisterHelper;
import com.existingeevee.moretcon.tools.BookTransformerAppendTools;
import com.existingeevee.moretcon.traits.TraitClient;
import com.existingeevee.moretcon.traits.book.BookTransformerAppendModifiers;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.tconstruct.library.book.TinkerBook;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		super.preInit();
		MaterialClient.init();
		EntityInit.initClient();
	}

	@Override
	public void init() {
		TraitClient.init();
	}

	@Override
	@SubscribeEvent
	public void itemRegistry(Register<Item> event) {
		if (CompatManager.thebetweenlands) {
			BLItems.initBL(true);
		}
		ModTools.init(true);

		CompositeRegistry.updateCompositeRenderer();

	}

	@Override
	public void postInit() {
		super.postInit();
		TinkerBook.INSTANCE.addTransformer(new BookTransformerAppendTools(
				new FileRepository("tconstruct:book"), RegisterHelper.moreTConTools));

		TinkerBook.INSTANCE.addTransformer(new BookTransformerAppendModifiers(
				new FileRepository("tconstruct:book"), RegisterHelper.moreTConModifiers));
	}
	
	@Override
	public void clientRun(Runnable r) {
		r.run();
	}
	
	@Override 
	public boolean isClient() {
		return true;
	}
}
