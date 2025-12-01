package com.existingeevee.moretcon.proxy;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.compat.betweenlands.BLItems;
import com.existingeevee.moretcon.entity.EntityInit;
import com.existingeevee.moretcon.inits.ModTools;
import com.existingeevee.moretcon.item.ItemMDGel;
import com.existingeevee.moretcon.materials.CompositeRegistry;
import com.existingeevee.moretcon.materials.MaterialClient;
import com.existingeevee.moretcon.other.BookTransformerAppendTools;
import com.existingeevee.moretcon.other.ContentLigntningModifier;
import com.existingeevee.moretcon.other.ICustomSlotRenderer;
import com.existingeevee.moretcon.other.ICustomSlotRenderer.GlowType;
import com.existingeevee.moretcon.other.SlotRendererRegistry;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.RegisterHelper;
import com.existingeevee.moretcon.traits.ModTraits;
import com.existingeevee.moretcon.traits.TraitClient;
import com.existingeevee.moretcon.traits.book.BookTransformerAppendModifiers;
import com.existingeevee.moretcon.traits.traits.armor.ModArmorTraits;

import c4.conarm.lib.book.ArmoryBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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
import slimeknights.tconstruct.library.tinkering.MaterialItem;

public class ClientProxy extends CommonProxy {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onTexturesStitch(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(ICustomSlotRenderer.CIRCLE_GLOW);
		event.getMap().registerSprite(ICustomSlotRenderer.OVAL_GLOW);
		event.getMap().registerSprite(ICustomSlotRenderer.BIG_CIRCLE_GLOW);
		event.getMap().registerSprite(ICustomSlotRenderer.EXTREME_GLOW);
		event.getMap().registerSprite(ItemMDGel.MDGelGuiParticleRenderer.DOT);
	}

	@Override
	public void preInit() {
		super.preInit();
		MinecraftForge.EVENT_BUS.register(this);

		MaterialClient.init();
		EntityInit.initClient();

		OBJLoader.INSTANCE.addDomain(ModInfo.MODID);

		SlotRendererRegistry.register(ModTraits.luminescent::isToolWithTrait, (stack, x, y, bakedmodel) -> ICustomSlotRenderer.simpleRender(stack, x, y, bakedmodel, GlowType.CIRCLE_BIG, ModTraits.luminescent.calculateColor(stack)));
		SlotRendererRegistry.register(s -> s.getItem() instanceof MaterialItem && ((MaterialItem) s.getItem()).getMaterial(s).getAllTraits().contains(ModTraits.luminescent), (stack, x, y, bakedmodel) -> ICustomSlotRenderer.simpleRender(stack, x, y, bakedmodel, GlowType.CIRCLE_BIG, ModTraits.luminescent.calculateColor(stack)));
	}

	@Override
	public void init() {
	}

	@Override
	@SubscribeEvent
	public void itemRegistry(Register<Item> event) {
		if (CompatManager.thebetweenlands) {
			BLItems.initBL(true);
		}
		ModTools.init(true);

		CompositeRegistry.updateCompositeRenderer();

		TraitClient.init(); //we have to do it here bc tinkers registered the stuff here
	}

	@Override
	public void postInit() {
		super.postInit();

		BookLoader.registerPageType(ContentLigntningModifier.ID, ContentLigntningModifier.class);

		TinkerBook.INSTANCE.addTransformer(new BookTransformerAppendTools(
				new FileRepository(ModInfo.MODID + ":book"), RegisterHelper.moreTConTools));

		TinkerBook.INSTANCE.addTransformer(new BookTransformerAppendModifiers(
				new FileRepository(ModInfo.MODID + ":book"), RegisterHelper.moreTConModifiers));
		
		if (CompatManager.conarm)	
			ArmoryBook.INSTANCE.addTransformer(new BookTransformerAppendModifiers(
				new FileRepository(ModInfo.MODID + ":book"), true, ModArmorTraits.collector));
	}

	@Override
	public void clientRun(Runnable r) {
		r.run();
	}

	@Override
	public boolean isClient() {
		return true;
	}

	@Override
	public boolean isClientSneaking() {
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		return p == null ? false : p.isSneaking();
	}
}
