package com.existingeevee.moretcon.proxy;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.compat.betweenlands.BLItems;
import com.existingeevee.moretcon.entity.EntityInit;
import com.existingeevee.moretcon.inits.ModTools;
import com.existingeevee.moretcon.item.ItemBase;
import com.existingeevee.moretcon.materials.CompositeRegistry;
import com.existingeevee.moretcon.materials.MaterialClient;
import com.existingeevee.moretcon.other.ContentLigntningModifier;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.RegisterHelper;
import com.existingeevee.moretcon.tools.BookTransformerAppendTools;
import com.existingeevee.moretcon.traits.TraitClient;
import com.existingeevee.moretcon.traits.book.BookTransformerAppendModifiers;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.tconstruct.library.book.TinkerBook;

public class ClientProxy extends CommonProxy {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onTexturesStitch(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(ItemBase.CIRCLE_GLOW);
		event.getMap().registerSprite(ItemBase.OVAL_GLOW);
		event.getMap().registerSprite(ItemBase.BIG_CIRCLE_GLOW);
		event.getMap().registerSprite(ItemBase.EXTREME_GLOW);
	}
	
	@Override
	public void preInit() {
		super.preInit();
		MinecraftForge.EVENT_BUS.register(this);
		
		MaterialClient.init();
		EntityInit.initClient();
		
		OBJLoader.INSTANCE.addDomain(ModInfo.MODID);
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
		
		BookLoader.registerPageType(ContentLigntningModifier.ID, ContentLigntningModifier.class);
		
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
