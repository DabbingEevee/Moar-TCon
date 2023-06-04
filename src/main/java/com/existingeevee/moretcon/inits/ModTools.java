package com.existingeevee.moretcon.inits;

import com.existingeevee.moretcon.other.Misc;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.ConfigHandler;
import com.existingeevee.moretcon.other.utils.RegisterHelper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.TinkerRegistryClient;
import slimeknights.tconstruct.library.client.ToolBuildGuiInfo;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.Pattern;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.tools.TinkerTools;

public class ModTools {

	public static ToolPart betweenAxeHead;
	public static ToolPart betweenPickHead;
	public static ToolPart betweenShovelHead;
	public static ToolPart betweenSwordBlade;
	public static ToolPart betweenBowLimb;
	public static ToolPart smallPlate;
	
	public static ToolCore toolBetweenAxe;
	public static ToolCore toolBetweenSword;
	public static ToolCore toolBetweenPick;
	public static ToolCore toolBetweenShovel;
	public static ToolCore toolBetweenBow;
	public static ToolCore toolGauntlet;
	public static ToolCore toolRing;
	
	public static void init() {
		init(false);
	}

	
	public static void init(boolean isClient) {
		smallPlate = (ToolPart) new ToolPart(Material.VALUE_Ingot * 1).setUnlocalizedName(Misc.createNonConflictiveName("smallplate"));
		RegisterHelper.registerItem(smallPlate);
		
		if (CompatManager.aether_legacy && ConfigHandler.enableGauntlet) {
			toolGauntlet = tryMakeToolInstance("Gauntlet"); //new Gauntlet();
			RegisterHelper.registerItem(toolGauntlet);
		}
		
		if (CompatManager.baubles && ConfigHandler.enableRing) {
			toolRing = tryMakeToolInstance("Ring");
			RegisterHelper.registerItem(toolRing);
		}
		
		if (CompatManager.thebetweenlands && ConfigHandler.registerBetweenTinkerTools) {
			betweenAxeHead = (ToolPart) new ToolPart(Material.VALUE_Ingot * 2).setUnlocalizedName(Misc.createNonConflictiveName("blaxehead"));
			RegisterHelper.registerItem(betweenAxeHead);

			betweenSwordBlade = (ToolPart) new ToolPart(Material.VALUE_Ingot * 2).setUnlocalizedName(Misc.createNonConflictiveName("blswordblade"));
			RegisterHelper.registerItem(betweenSwordBlade);

			betweenShovelHead = (ToolPart) new ToolPart(Material.VALUE_Ingot * 2).setUnlocalizedName(Misc.createNonConflictiveName("blshovelblade"));
			RegisterHelper.registerItem(betweenShovelHead);

			betweenPickHead = (ToolPart) new ToolPart(Material.VALUE_Ingot * 2).setUnlocalizedName(Misc.createNonConflictiveName("blpickhead"));
			RegisterHelper.registerItem(betweenPickHead);

			betweenBowLimb = (ToolPart) new ToolPart(Material.VALUE_Ingot * 2).setUnlocalizedName(Misc.createNonConflictiveName("blbowlimb"));
			RegisterHelper.registerItem(betweenBowLimb);

			toolBetweenAxe = tryMakeToolInstance("BetweenAxe");// = new BetweenAxe();
			RegisterHelper.registerItem(toolBetweenAxe);

			toolBetweenSword = tryMakeToolInstance("BetweenSword");// = new BetweenSword();
			RegisterHelper.registerItem(toolBetweenSword);

			toolBetweenShovel = tryMakeToolInstance("BetweenShovel");// = new BetweenShovel();
			RegisterHelper.registerItem(toolBetweenShovel);

			toolBetweenPick = tryMakeToolInstance("BetweenPickaxe");// = new BetweenPickaxe();
			RegisterHelper.registerItem(toolBetweenPick);

			toolBetweenBow = tryMakeToolInstance("BetweenBow");// = new BetweenBow();
			RegisterHelper.registerItem(toolBetweenBow);
		}
		
		registerStencils();
		
		if (isClient) {
			registerToolGui();
		}
	}
	
	private static void registerStencils() {
		TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack(TinkerTools.pattern), smallPlate));		
	}


	@SideOnly(Side.CLIENT)
	public static void registerToolGui() {
		if (CompatManager.aether_legacy && ConfigHandler.enableGauntlet) {
			ToolBuildGuiInfo gauntletInfo = new ToolBuildGuiInfo(toolGauntlet);

			gauntletInfo.addSlotPosition(12, 62);
			gauntletInfo.addSlotPosition(30, 44);
			gauntletInfo.addSlotPosition(48, 26);
			
			TinkerRegistryClient.addToolBuilding(gauntletInfo);	
		}
		if (CompatManager.baubles && ConfigHandler.enableRing) {
			ToolBuildGuiInfo ringInfo = new ToolBuildGuiInfo(toolRing);
			ringInfo.addSlotPosition(33, 42 - 9);
			ringInfo.addSlotPosition(33, 42 + 9);
			TinkerRegistryClient.addToolBuilding(ringInfo);	
		}
		if (CompatManager.thebetweenlands && ConfigHandler.registerBetweenTinkerTools) {
			ToolBuildGuiInfo blaxeInfo = new ToolBuildGuiInfo(toolBetweenAxe);
			blaxeInfo.addSlotPosition(22, 53);
			blaxeInfo.addSlotPosition(31, 22);
			blaxeInfo.addSlotPosition(51, 34);
			TinkerRegistryClient.addToolBuilding(blaxeInfo);
	
			ToolBuildGuiInfo blswordInfo = new ToolBuildGuiInfo(toolBetweenSword);
			blswordInfo.addSlotPosition(12, 62);
			blswordInfo.addSlotPosition(48, 26);
			blswordInfo.addSlotPosition(30, 44);
			TinkerRegistryClient.addToolBuilding(blswordInfo);
	
			ToolBuildGuiInfo blshovelInfo = new ToolBuildGuiInfo(toolBetweenShovel);
			blshovelInfo.addSlotPosition(33, 42);
			blshovelInfo.addSlotPosition(51, 24);
			blshovelInfo.addSlotPosition(13, 62);
			TinkerRegistryClient.addToolBuilding(blshovelInfo);
	
			ToolBuildGuiInfo blpickInfo = new ToolBuildGuiInfo(toolBetweenPick);
			blpickInfo.addSlotPosition(15, 60);
			blpickInfo.addSlotPosition(53, 22);
			blpickInfo.addSlotPosition(33, 42);
			TinkerRegistryClient.addToolBuilding(blpickInfo);
	
			ToolBuildGuiInfo blbowInfo = new ToolBuildGuiInfo(toolBetweenBow);
			blbowInfo.addSlotPosition(36, 41 - 18); // top limb
			blbowInfo.addSlotPosition(32 - 18, 45); // left limb
			blbowInfo.addSlotPosition(38, 47); // center bowstring
			TinkerRegistryClient.addToolBuilding(blbowInfo);
		}
		/*if (CompatManager.baubles && ConfigHandler.enableRing) {
			ToolBuildGuiInfo blaxeInfo = new ToolBuildGuiInfo(toolBetweenAxe);
			blaxeInfo.addSlotPosition(22, 53);
			blaxeInfo.addSlotPosition(31, 22);
			blaxeInfo.addSlotPosition(51, 34);
			TinkerRegistryClient.addToolBuilding(blaxeInfo);
		}*/

	}
	
	private static ToolCore tryMakeToolInstance(String toolName) {
		return (ToolCore) tryMakeInstance("com.existingeevee.moretcon.tools.tooltypes." + toolName);
	}

	
	private static Object tryMakeInstance(String classPath) {
		try {
			Class<?> clazz = Class.forName(classPath);
			return clazz.newInstance();
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return null;
	}
}
