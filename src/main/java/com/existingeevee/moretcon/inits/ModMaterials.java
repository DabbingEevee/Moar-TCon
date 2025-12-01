package com.existingeevee.moretcon.inits;

import java.util.List;
import java.util.Map;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.item.tooltypes.Bomb;
import com.existingeevee.moretcon.item.tooltypes.Bomb.ExplosiveMaterialStats;
import com.existingeevee.moretcon.materials.CompositeRegistry;
import com.existingeevee.moretcon.materials.CompositeRegistry.CompositeData;
import com.existingeevee.moretcon.materials.DelagateFluidMaterial;
import com.existingeevee.moretcon.materials.MTMaterialIntegration;
import com.existingeevee.moretcon.materials.UniqueMaterial;
import com.existingeevee.moretcon.other.MoreTConLogger;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.MaterialUtils;
import com.existingeevee.moretcon.other.utils.MirrorUtils;
import com.existingeevee.moretcon.other.utils.MirrorUtils.IField;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.other.utils.RegisterHelper;
import com.existingeevee.moretcon.traits.ModTraits;
import com.existingeevee.moretcon.traits.traits.armor.ModArmorTraits;

import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.lib.materials.ArmorMaterials;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import landmaster.plustic.tools.stats.BatteryCellMaterialStats;
import landmaster.plustic.tools.stats.LaserMediumMaterialStats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ArrowShaftMaterialStats;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.BowStringMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.FletchingMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerTraits;
import thebetweenlands.common.registries.BlockRegistry;
import twilightforest.compat.TConstruct;

public class ModMaterials implements MaterialTypes {

	public static String CORE = "core";
	public static String PLATES = "plates";
	public static String TRIM = "trim";

	public static int totalMaterials;

	public static final Material materialWeedwood = new Material(MiscUtils.createNonConflictiveName("weedwood"), 0xcc9900);
	public static final Material materialIronwood = new Material(MiscUtils.createNonConflictiveName("ironwood"), 0xcc9900);
	public static final Material materialFusionite = new Material(MiscUtils.createNonConflictiveName("fusionite"), 0x3399ff);
	public static final Material materialSyrmorite = new Material(MiscUtils.createNonConflictiveName("syrmorite"), 0x234187);
	public static final Material materialValonite = new Material(MiscUtils.createNonConflictiveName("valonite"), 0xcab1ca);
	public static final Material materialOctine = new Material(MiscUtils.createNonConflictiveName("octine"), 0xff8206);
	public static final Material materialEmberlight = new Material(MiscUtils.createNonConflictiveName("emberlight"), 0xe8926d);
	public static final Material materialReedRope = new Material(MiscUtils.createNonConflictiveName("reedrope"), 0x000b4e0b);
	public static final Material materialAnglerTooth = new Material(MiscUtils.createNonConflictiveName("anglertooth"), 0x00cfcf99);
	public static final Material materialDragonFlyWing = new Material(MiscUtils.createNonConflictiveName("dragonflywing"), 0x00bed6db);
	public static final Material materialIrradium = new Material(MiscUtils.createNonConflictiveName("irradium"), 0x00ed00);
	public static final Material materialSolsteel = new Material(MiscUtils.createNonConflictiveName("solarsteel"), 0xffad33);
	public static final Material materialPenguinite = new Material(MiscUtils.createNonConflictiveName("penguinite"), 0xe6e6ffff);
	public static final Material materialVoidSpar = new Material(MiscUtils.createNonConflictiveName("voidspar"), 0xb37e15ff);
	public static final Material materialGarstone = new Material(MiscUtils.createNonConflictiveName("garstone"), 0xff0a0a);
	public static final Material materialBloodstone = new Material(MiscUtils.createNonConflictiveName("bloodstone"), 0x6b0000);
	public static final Material materialGallium = new Material(MiscUtils.createNonConflictiveName("gallium"), 0xffffff);
	public static final Material materialSlimyBone = new Material(MiscUtils.createNonConflictiveName("slimybone"), 0xede6bf);
	public static final Material materialEnderal = new Material(MiscUtils.createNonConflictiveName("enderal"), 0xb32d0080);
	public static final Material materialEnderexamite = new Material(MiscUtils.createNonConflictiveName("enderexamite"), 0x8e3acf);
	public static final Material materialFerroherb = new Material(MiscUtils.createNonConflictiveName("ferroherb"), 0x4c5e3a);
	public static final Material materialRuneSteel = new Material(MiscUtils.createNonConflictiveName("runeSteel"), 0xb6905e);
	public static final Material materialArkenium = new Material(MiscUtils.createNonConflictiveName("arkenium"), 0x595959);
	public static final Material materialSkyroot = new Material(MiscUtils.createNonConflictiveName("skyroot"), 0x6c633e);
	public static final Material materialHolystone = new Material(MiscUtils.createNonConflictiveName("holystone"), 0xa3a3a3);
	public static final Material materialZanite = new Material(MiscUtils.createNonConflictiveName("zanite"), 0x6611dd);
	public static final Material materialGravitite = new Material(MiscUtils.createNonConflictiveName("gravitite"), 0xcc55aa);
	public static final Material materialValkyrieMetal = new Material(MiscUtils.createNonConflictiveName("valkyriemetal"), 0xeaee57);
	public static final Material materialAmberwood = new Material(MiscUtils.createNonConflictiveName("amberwood"), 0xeaee57);
	public static final Material materialGravitonium = new Material(MiscUtils.createNonConflictiveName("gravitonium"), 0x00aa00);
	public static final Material materialTrichromadentium = new Material(MiscUtils.createNonConflictiveName("trichromadentium"), 0x777777);
	public static final Material materialSwampSteel = new Material(MiscUtils.createNonConflictiveName("swampsteel"), 0x003906);
	public static final Material materialRotiron = new Material(MiscUtils.createNonConflictiveName("rotiron"), 0x002400);
	public static final Material materialEchostone = new Material(MiscUtils.createNonConflictiveName("echostone"), 0x00725a);
	public static final Material materialIgniglomerate = new Material(MiscUtils.createNonConflictiveName("igniglomerate"), 0xB33B00);
	public static final Material materialAtronium = new Material(MiscUtils.createNonConflictiveName("atronium"), 0xd99857);
	public static final Material materialEbonite = new Material(MiscUtils.createNonConflictiveName("ebonite"), 0x270339);
	public static final Material materialErythynite = new Material(MiscUtils.createNonConflictiveName("erythynite"), 0x00ff00);
	public static final Material materialShadowglass = new Material(MiscUtils.createNonConflictiveName("shadowglass"), 0x2a275e);
	public static final Material materialEtherstone = new Material(MiscUtils.createNonConflictiveName("etherstone"), 0xf1ea3b);
	public static final Material materialValasium = new Material(MiscUtils.createNonConflictiveName("valasium"), 0x9ab7d6);
	public static final Material materialElectarite = new Material(MiscUtils.createNonConflictiveName("electarite"), 0x6666FF);
	public static final Material materialHallowsite = new Material(MiscUtils.createNonConflictiveName("hallowsite"), 0x34eb98);
	public static final Material materialAncientAlloy = new Material(MiscUtils.createNonConflictiveName("ancientalloy"), 0xa2c2c0);
	public static final Material materialBlightsteel = new Material(MiscUtils.createNonConflictiveName("blightsteel"), 0x243c5a);
	public static final Material materialSanguiseelium = new Material(MiscUtils.createNonConflictiveName("sanguiseelium"), 0x6b1313);
	public static final Material materialZracohlium = new Material(MiscUtils.createNonConflictiveName("zracohlium"), 0x444c2a);
	public static final Material materialIoximite = new Material(MiscUtils.createNonConflictiveName("ioximite"), 0x978cea);
	public static final Material materialMonolite = new Material(MiscUtils.createNonConflictiveName("monolite"), 0x4a74f0);
	public static final Material materialGeodesium = new Material(MiscUtils.createNonConflictiveName("geodesium"), 0xf6e6ce); // 0xbbd190
	public static final Material materialPorksteel = new Material(MiscUtils.createNonConflictiveName("porksteel"), 0xc3af7d);
	public static final Material materialPerimidum = new Material(MiscUtils.createNonConflictiveName("perimidum"), 0xdfd3ff);
	public static final Material materialBrinkstone = new Material(MiscUtils.createNonConflictiveName("brinkstone"), 0x606059);
	public static final Material materialAnthracite = new Material(MiscUtils.createNonConflictiveName("anthracite"), 0x2d3945);
	public static final Material materialIonstone = new Material(MiscUtils.createNonConflictiveName("ionstone"), 0x05a8f3);
	public static final Material materialVacuuite = new Material(MiscUtils.createNonConflictiveName("vacuuite"), 0x300a6a);

	public static final Material materialNahuatl = new Material(MiscUtils.createNonConflictiveName("nahuatl"), 0x3B2754);
	public static final Material materialSlimewood = new Material(MiscUtils.createNonConflictiveName("slimewood"), 0x96dd8f);
	public static final DelagateFluidMaterial materialSearedStone = new DelagateFluidMaterial(MiscUtils.createNonConflictiveName("searedstone"), 0x4f4a47);
	public static final Material materialSlimesteel = new Material(MiscUtils.createNonConflictiveName("slimesteel"), 0x47efea);

	// really not gonna be used lmao. only really there for explosive charge
	public static final Material materialGunpowder = new Material(MiscUtils.createNonConflictiveName("gunpowder"), 0x727272);
	public static final Material materialIcy = new Material(MiscUtils.createNonConflictiveName("icy"), 0x91bbff);
	public static final Material materialGlowstone = new Material(MiscUtils.createNonConflictiveName("glowstone"), 0xffdb78);
	public static final Material materialRedstone = new Material(MiscUtils.createNonConflictiveName("redstone"), 0x990000);

	// TODO
	// public static final Material materialQueensslime = new
	// Material(Misc.createNonConflictiveName("queensslime"), 0x565808); //Need
	// Custom
	// public static final Material materialHepatizon = new
	// Material(Misc.createNonConflictiveName("hepatizon"), 0xf1ea3b);
	// public static final Material materialScorchedStone = new
	// Material(Misc.createNonConflictiveName("scorchedstone"), 0x53453c);
	// public static final Material materialBloodbone = new
	// Material(Misc.createNonConflictiveName("bloodbone"), 0xb80000);
	// public static final Material materialNecroticBone = new
	// Material(Misc.createNonConflictiveName("necrobone"), 0x343434);
	// public static final Material materialBlazingBone = new
	// Material(Misc.createNonConflictiveName("blazingbone"), 0xefc62f);

	public static final UniqueMaterial materialPlasma = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("plasma"), 0xff0000, "tconstruct:sword_blade",
			"tconstruct:broadsword");

	public static final UniqueMaterial materialShockwave = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("shockwave"), 0xa1ddd8,
			ConfigHandler.registerBetweenTinkerTools ? "moretcon:blswordblade" : "tconstruct:sword_blade",
			ConfigHandler.registerBetweenTinkerTools ? "moretcon:blsword" : "tconstruct:broadsword",
			() -> new ItemStack(BlockRegistry.ANIMATOR), "shockwave");

	public static final UniqueMaterial materialTriblade = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("triblade"), 0x6d6d6d, "tconstruct:knife_blade",
			"tconstruct:shuriken");

	public static final UniqueMaterial materialTrailblazer = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("trailblazer"), 0xff6c00, "tconstruct:knife_blade",
			"tconstruct:shuriken");

	public static final UniqueMaterial materialMirrored = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("mirrored"), 0xfafaff, "tconstruct:fletching",
			"tconstruct:arrow");

	public static final UniqueMaterial materialTechnoblade = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("technoblade"), 0xea8f8c, "tconstruct:sword_blade",
			"tconstruct:broadsword");

	public static final UniqueMaterial materialCrimson = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("crimson"), 0xaa0000, "tconstruct:tough_tool_rod",
			"plustic:katana");

	public static final UniqueMaterial materialEssencore = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("essencore"), 0x65c9ff, "tconstruct:pick_head",
			"tconstruct:pickaxe");

	public static final UniqueMaterial materialWormed = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("wormed"), 0xd99857, "tconstruct:arrow_head",
			"tconstruct:arrow",
			() -> new ItemStack(BlockRegistry.WEEDWOOD_WORKBENCH), "crafting_table");

	public static final UniqueMaterial materialCryosplinters = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("cryosplinters"), 0x91bbff, "tconstruct:arrow_head",
			"tconstruct:arrow");

	public static final UniqueMaterial materialSpaceTimeDisruption = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("spacetimedisruption"), 0x400080, "tconstruct:bow_limb",
			"tconstruct:crossbow");

	public static final UniqueMaterial materialAutoloader = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("autoloader"), 0xa34f49, "tconstruct:tough_tool_rod",
			"tconstruct:crossbow");

	public static final UniqueMaterial materialQuakestruck = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("quakestruck"), 0x2683bd, "tconstruct:large_plate",
			"tconstruct:hammer");

	public static final UniqueMaterial materialSkybolt = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("skybolt"), 0x3BB0FF, "tconstruct:sword_blade",
			"tconstruct:broadsword");

	public static final UniqueMaterial materialVengeance = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("vengeance"), 0x3a454a, "tconstruct:sign_head",
			"tconstruct:battlesign");

	public static final UniqueMaterial materialDematerializer = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("dematerializer"), 0xbae7ff, "tconstruct:bow_limb",
			"tconstruct:longbow");

	public static final UniqueMaterial materialShotgun = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("shotgun"), 0x98f1d9, "tconstruct:tough_binding",
			"tconstruct:crossbow");

	public static final UniqueMaterial materialImpact = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("impact"), 0x007aba, "moretcon:explosive_charge",
			"moretcon:bomb");

	public static final UniqueMaterial materialInertialRedirector = new UniqueMaterial(
			MiscUtils.createNonConflictiveName("inertial_redirector"), 0xb2a1ff, "tconstruct:bolt_core",
			"tconstruct:bolt");

	private static void initMats() {
		BowMaterialStats whyWouldYouMakeABowOutOfThis = new BowMaterialStats(0.2f, 0.4f, -1f);
		HeadMaterialStats thankYouTinkersForNeedingAHeadMat = new HeadMaterialStats(700, 6f, 4f, 5);

		Material.UNKNOWN.addStats(new ExplosiveMaterialStats(0.25, 1));

		if (ConfigHandler.enableBomb) {
			TinkerRegistry.addMaterialStats(materialGunpowder, new ExplosiveMaterialStats(3, 20));

			TinkerRegistry.addMaterialStats(materialIcy, new ExplosiveMaterialStats(4, 40));
			materialIcy.addTrait(ModTraits.cryogenics);

			TinkerRegistry.addMaterialStats(materialGlowstone, new ExplosiveMaterialStats(2, 10));
			materialGlowstone.addTrait(ModTraits.flashbang);

			TinkerRegistry.addMaterialStats(materialRedstone, new ExplosiveMaterialStats(2.5, 20));
			materialRedstone.addTrait(ModTraits.shockingAura);

			TinkerRegistry.addMaterialStats(materialImpact, new ExplosiveMaterialStats(3.25, 0));
			materialImpact.addTrait(ModTraits.impactDetonated);
		}

		if (CompatManager.tic3backport) {
			materialNahuatl.setCastable(false);
			materialNahuatl.setCraftable(false);
			materialNahuatl.addTrait(ModTraits.slicing, HEAD);
			materialNahuatl.addTrait(ModTraits.darkened, HEAD);
			materialNahuatl.addTrait(TinkerTraits.ecological, HEAD);
			materialNahuatl.addTrait(TinkerTraits.dense, HEAD);
			materialNahuatl.addTrait(TinkerTraits.ecological);
			materialNahuatl.addTrait(TinkerTraits.dense);
			TinkerRegistry.addMaterialStats(materialNahuatl, new HeadMaterialStats(350, 4.5f, 5f, 2));
			TinkerRegistry.addMaterialStats(materialNahuatl, new HandleMaterialStats(0.9f, 125));
			TinkerRegistry.addMaterialStats(materialNahuatl, new ExtraMaterialStats(75));
			TinkerRegistry.addMaterialStats(materialNahuatl, new BowMaterialStats(0.7f, 0.85f, 4f));
			TinkerRegistry.addMaterialStats(materialNahuatl, new ArrowShaftMaterialStats(1.2f, 32));
			CompositeRegistry.registerComposite(new CompositeData(() -> TinkerMaterials.wood, () -> materialNahuatl, () -> TinkerFluids.obsidian, false).setMultiplier(2));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialNahuatl, new CoreMaterialStats(13.2f, 14.9f));
				TinkerRegistry.addMaterialStats(materialNahuatl, new PlatesMaterialStats(0.9f, 8.1f, 1.7f));
				TinkerRegistry.addMaterialStats(materialNahuatl, new TrimMaterialStats(3.75f));

				ArmorMaterials.addArmorTrait(materialNahuatl, ArmorTraits.ecological);
				ArmorMaterials.addArmorTrait(materialNahuatl, ArmorTraits.dense);
				materialNahuatl.addTrait(ModTraits.darkened, CORE);
				materialNahuatl.addTrait(ModArmorTraits.serrated, CORE);

			}

			materialSlimewood.setCastable(false);
			materialSlimewood.setCraftable(false);
			materialSlimewood.addTrait(ModTraits.overgrowth, HEAD);
			materialSlimewood.addTrait(ModTraits.overslime, HEAD);
			materialSlimewood.addTrait(TinkerTraits.ecological, HEAD);
			materialSlimewood.addTrait(TinkerTraits.crumbling, HEAD);
			materialSlimewood.addTrait(TinkerTraits.slimeyGreen);
			materialSlimewood.addTrait(ModTraits.overslime);
			TinkerRegistry.addMaterialStats(materialSlimewood, new HeadMaterialStats(375, 4f, 1f, 1));
			TinkerRegistry.addMaterialStats(materialSlimewood, new HandleMaterialStats(1.3f, 125));
			TinkerRegistry.addMaterialStats(materialSlimewood, new ExtraMaterialStats(75));
			TinkerRegistry.addMaterialStats(materialSlimewood, new BowMaterialStats(1f, 0.95f, 2f));
			TinkerRegistry.addMaterialStats(materialSlimewood, new ArrowShaftMaterialStats(1.2f, 28));
			CompositeRegistry.registerComposite(() -> TinkerMaterials.wood, () -> materialSlimewood, () -> TinkerFluids.blueslime);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialSlimewood, new CoreMaterialStats(13.7f, 3.4f));
				TinkerRegistry.addMaterialStats(materialSlimewood, new PlatesMaterialStats(1.3f, 8.1f, 0));
				TinkerRegistry.addMaterialStats(materialSlimewood, new TrimMaterialStats(3.75f));

				ArmorMaterials.addArmorTrait(materialSlimewood, ArmorTraits.slimeyGreen);

			}

			// Overslime for the win!
			TinkerMaterials.knightslime.addTrait(ModTraits.overslime, HEAD);
			TinkerMaterials.knightslime.addTrait(ModTraits.overcast, HEAD);
			TinkerMaterials.knightslime.addTrait(ModTraits.overslime);

			TinkerMaterials.slime.addTrait(ModTraits.overgrowth2, HEAD);
			TinkerMaterials.slime.addTrait(ModTraits.overslime, HEAD);
			TinkerMaterials.slime.addTrait(TinkerTraits.slimeyGreen, HEAD);
			TinkerMaterials.slime.addTrait(ModTraits.overgrowth);
			TinkerMaterials.slime.addTrait(ModTraits.overslime);

			TinkerMaterials.blueslime.addTrait(ModTraits.overgrowth2, HEAD);
			TinkerMaterials.blueslime.addTrait(ModTraits.overslime, HEAD);
			TinkerMaterials.blueslime.addTrait(TinkerTraits.slimeyBlue, HEAD);
			TinkerMaterials.blueslime.addTrait(ModTraits.overgrowth);
			TinkerMaterials.blueslime.addTrait(ModTraits.overslime);

			TinkerMaterials.magmaslime.addTrait(ModTraits.fireslime, HEAD);
			TinkerMaterials.magmaslime.addTrait(ModTraits.overgrowth);
			TinkerMaterials.magmaslime.addTrait(ModTraits.overslime, HEAD);
			TinkerMaterials.magmaslime.addTrait(ModTraits.overslime);

			materialSearedStone.addItem("blockSeared", 1, Material.VALUE_Ingot);
			materialSearedStone.addItem("ingotSeared", 1, Material.VALUE_Ingot / 4);
			materialSearedStone.setFluid(() -> TinkerFluids.searedStone);
			materialSearedStone.setCastable(true);
			materialSearedStone.setCraftable(true);
			materialSearedStone.setRepresentativeItem("blockSeared");
			materialSearedStone.addTrait(TinkerTraits.stonebound);
			materialSearedStone.addTrait(ModTraits.searing);
			materialSearedStone.addTrait(TinkerTraits.crude, HEAD);
			materialSearedStone.addTrait(ModTraits.searing2, HEAD);
			TinkerRegistry.addMaterialStats(materialSearedStone, new HeadMaterialStats(225, 1.5f, 6.5f, 1));
			TinkerRegistry.addMaterialStats(materialSearedStone, new HandleMaterialStats(0.85f, -25));
			TinkerRegistry.addMaterialStats(materialSearedStone, new ExtraMaterialStats(-15));
			TinkerRegistry.addMaterialStats(materialSearedStone, whyWouldYouMakeABowOutOfThis);
			TinkerRegistry.addMaterialStats(materialSearedStone, new ArrowShaftMaterialStats(0.8f, 15));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialSearedStone, new CoreMaterialStats(10.6f, 14.7f));
				TinkerRegistry.addMaterialStats(materialSearedStone, new PlatesMaterialStats(0.85f, -1.6f, 0));
				TinkerRegistry.addMaterialStats(materialSearedStone, new TrimMaterialStats(-0.75f));

				ArmorMaterials.addArmorTrait(materialSearedStone, ArmorTraits.mundane2, ArmorTraits.mundane);
				ArmorMaterials.addArmorTrait(materialSearedStone, ArmorTraits.combustible, CORE);

			}

			materialSlimesteel.addCommonItems("Slimesteel");
			materialSlimesteel.setFluid(ModFluids.liquidSlimesteel);
			materialSlimesteel.setCastable(true);
			materialSlimesteel.setCraftable(false);
			materialSlimesteel.setRepresentativeItem("ingotSlimesteel");
			materialSlimesteel.addTrait(ModTraits.slimesharp, HEAD);
			materialSlimesteel.addTrait(ModTraits.overslime, HEAD);
			materialSlimesteel.addTrait(ModTraits.overslime);
			TinkerRegistry.addMaterialStats(materialSlimesteel, new HeadMaterialStats(1040, 2.5f, 6.0f, 3));
			TinkerRegistry.addMaterialStats(materialSlimesteel, new HandleMaterialStats(0.6f, 120));
			TinkerRegistry.addMaterialStats(materialSlimesteel, new ExtraMaterialStats(45));
			TinkerRegistry.addMaterialStats(materialSlimesteel, new BowMaterialStats(0.9f, 1.0f, 3f));
			TinkerRegistry.addMaterialStats(materialSlimesteel, new ArrowShaftMaterialStats(1.2f, 15));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialSlimesteel, new CoreMaterialStats(22.8f, 16.9f));
				TinkerRegistry.addMaterialStats(materialSlimesteel, new PlatesMaterialStats(0.6f, 7.8f, 1.2f));
				TinkerRegistry.addMaterialStats(materialSlimesteel, new TrimMaterialStats(2.25f));

				materialSlimesteel.addTrait(ModArmorTraits.galvanized, CORE);
				materialSlimesteel.addTrait(ModTraits.overslime, CORE);
			}
		}

		if (CompatManager.plustic) {
			TinkerRegistry.addMaterialStats(materialCrimson, new HandleMaterialStats(4f, 1024));
			materialCrimson.addTrait(ModTraits.bloodyArc);
			materialCrimson.addTrait(ModTraits.slicing);
		}

		if (CompatManager.easterEggs) {
			// Thank you for everything, old friend
			TinkerRegistry.addMaterialStats(materialTechnoblade, new HeadMaterialStats(12288, 8f, 15f, 5));
			materialTechnoblade.addTrait(TinkerTraits.baconlicious);
			materialTechnoblade.addTrait(ModTraits.bloodGodsBlessing);
		}

		if (CompatManager.loadMain) {
			materialFusionite.addItem("oreFusionite", 1, Material.VALUE_Ore());
			materialFusionite.setFluid(ModFluids.liquidFusionite);
			materialFusionite.addCommonItems("Fusionite");
			materialFusionite.setCastable(true);
			materialFusionite.setCraftable(false);
			materialFusionite.setRepresentativeItem("ingotFusionite");
			materialFusionite.addTrait(ModTraits.frostburn, HEAD);
			materialFusionite.addTrait(ModTraits.luminescent, HEAD);
			materialFusionite.addTrait(TinkerTraits.enderference, HEAD);
			materialFusionite.addTrait(TinkerTraits.alien);
			materialFusionite.addTrait(TinkerTraits.dense);
			materialFusionite.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialFusionite, new HeadMaterialStats(500, 5.0f, 8.0f, 6));
			TinkerRegistry.addMaterialStats(materialFusionite, new HandleMaterialStats(3.0f, 20));
			TinkerRegistry.addMaterialStats(materialFusionite, new ExtraMaterialStats(20));
			TinkerRegistry.addMaterialStats(materialFusionite, new ArrowShaftMaterialStats(3.0f, 20));
			TinkerRegistry.addMaterialStats(materialFusionite, new BowMaterialStats(0.6f, 1.0f, 3f));
			if (CompatManager.plustic) {
				TinkerRegistry.addMaterialStats(materialFusionite, new LaserMediumMaterialStats(10, 20));
				TinkerRegistry.addMaterialStats(materialFusionite, new BatteryCellMaterialStats(100000));
			}
			if (ConfigHandler.enableBomb) {
				TinkerRegistry.addMaterialStats(materialFusionite, new ExplosiveMaterialStats(2f, 30));
				materialFusionite.addTrait(ModTraits.frostburn, Bomb.EXPLOSIVE_CHARGE);
				materialFusionite.addTrait(ModTraits.luminescent, Bomb.EXPLOSIVE_CHARGE);
			}
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialFusionite, new CoreMaterialStats(15.8f, 20.6f));
				TinkerRegistry.addMaterialStats(materialFusionite, new PlatesMaterialStats(3.0f, 1.3f, 0.8f));
				TinkerRegistry.addMaterialStats(materialFusionite, new TrimMaterialStats(1.0f));

				materialFusionite.addTrait(ArmorTraits.alien, TRIM);
				materialFusionite.addTrait(ArmorTraits.alien, PLATES);
				materialFusionite.addTrait(ArmorTraits.dense, TRIM);
				materialFusionite.addTrait(ArmorTraits.dense, PLATES);

				materialFusionite.addTrait(ModArmorTraits.frostburnThorns, CORE);
				materialFusionite.addTrait(ArmorTraits.enderport, CORE);

				addArmorLum(materialFusionite);
			}

			materialBrinkstone.addItem("brinkstone", 1, Material.VALUE_Ingot);
			materialBrinkstone.setCastable(false);
			materialBrinkstone.setCraftable(true);
			materialBrinkstone.setRepresentativeItem("brinkstone");
			materialBrinkstone.addTrait(TinkerTraits.stonebound);
			materialBrinkstone.addTrait(TinkerTraits.hellish);
			materialBrinkstone.addTrait(ModTraits.bottomsEnd, HEAD);
			materialBrinkstone.addTrait(TinkerTraits.hellish, HEAD);
			TinkerRegistry.addMaterialStats(materialBrinkstone, new HeadMaterialStats(600, 4f, 9f, 3));
			TinkerRegistry.addMaterialStats(materialBrinkstone, new HandleMaterialStats(1.2f, -20));
			TinkerRegistry.addMaterialStats(materialBrinkstone, new ExtraMaterialStats(50));
			TinkerRegistry.addMaterialStats(materialBrinkstone, whyWouldYouMakeABowOutOfThis);
			TinkerRegistry.addMaterialStats(materialBrinkstone, new ArrowShaftMaterialStats(1.2f, 30));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialBrinkstone, new CoreMaterialStats(17.3f, 22.4f));
				TinkerRegistry.addMaterialStats(materialBrinkstone, new PlatesMaterialStats(1.2f, -1.3f, 0.4f));
				TinkerRegistry.addMaterialStats(materialBrinkstone, new TrimMaterialStats(2.5f));

				ArmorMaterials.addArmorTrait(materialBrinkstone, ArmorTraits.steady);
			}

			// MinecraftMixin TextureManager TextureAtlasSprite

			materialValasium.addItem("oreValasium", 1, Material.VALUE_Ore());
			materialValasium.setFluid(ModFluids.liquidValasium);
			materialValasium.addCommonItems("Valasium");
			materialValasium.setCastable(true);
			materialValasium.setCraftable(false);
			materialValasium.setRepresentativeItem("ingotValasium");
			materialValasium.addTrait(ModTraits.kineticBattery, HEAD);
			materialValasium.addTrait(ModTraits.warding, HEAD);
			materialValasium.addTrait(TinkerTraits.alien, HEAD);
			materialValasium.addTrait(TinkerTraits.alien);
			materialValasium.addTrait(TinkerTraits.dense);
			if (CompatManager.plustic) {
				TinkerRegistry.addMaterialStats(materialValasium, new LaserMediumMaterialStats(15, 20));
				TinkerRegistry.addMaterialStats(materialValasium, new BatteryCellMaterialStats(950000));
			}
			TinkerRegistry.addMaterialStats(materialValasium, new HeadMaterialStats(1600, 20f, 16f, 8));
			TinkerRegistry.addMaterialStats(materialValasium, new HandleMaterialStats(4f, 60));
			TinkerRegistry.addMaterialStats(materialValasium, new ExtraMaterialStats(500));
			TinkerRegistry.addMaterialStats(materialValasium, new ArrowShaftMaterialStats(3f, 75));
			TinkerRegistry.addMaterialStats(materialValasium, new BowMaterialStats(0.7f, 1.7f, 12f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialValasium, new CoreMaterialStats(28.3f, 36.9f));
				TinkerRegistry.addMaterialStats(materialValasium, new PlatesMaterialStats(4.0f, 3.9f, 4.0f));
				TinkerRegistry.addMaterialStats(materialValasium, new TrimMaterialStats(25.0f));

				ArmorMaterials.addArmorTrait(materialValasium, ModArmorTraits.overload, ArmorTraits.dense);
				ArmorMaterials.addArmorTrait(materialValasium, ArmorTraits.alien, ArmorTraits.alien);
				materialValasium.addTrait(ArmorTraits.indomitable, CORE);
			}

			materialPorksteel.setFluid(ModFluids.liquidPorksteel);
			materialPorksteel.addCommonItems("Porksteel");
			materialPorksteel.setCastable(true);
			materialPorksteel.setCraftable(false);
			materialPorksteel.setRepresentativeItem("ingotPorksteel");
			materialPorksteel.addTrait(TinkerTraits.baconlicious, HEAD);
			materialPorksteel.addTrait(ModTraits.burning, HEAD);
			materialPorksteel.addTrait(ModTraits.saturpigting, HEAD);
			materialPorksteel.addTrait(ModTraits.saturpigting);
			TinkerRegistry.addMaterialStats(materialPorksteel, new HeadMaterialStats(430, 7.40f, 5.50f, 3));
			TinkerRegistry.addMaterialStats(materialPorksteel, new HandleMaterialStats(1.30f, 20));
			TinkerRegistry.addMaterialStats(materialPorksteel, new ExtraMaterialStats(190));
			TinkerRegistry.addMaterialStats(materialPorksteel, new BowMaterialStats(0.55f, 1.5f, 7.3f));
			TinkerRegistry.addMaterialStats(materialPorksteel, new ArrowShaftMaterialStats(1.2f, 15));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialPorksteel, new CoreMaterialStats(14.7f, 15.9f));
				TinkerRegistry.addMaterialStats(materialPorksteel, new PlatesMaterialStats(1.3f, 1.3f, 0));
				TinkerRegistry.addMaterialStats(materialPorksteel, new TrimMaterialStats(9.5f));

				materialPorksteel.addTrait(ArmorTraits.baconlicious, CORE);
				materialPorksteel.addTrait(ArmorTraits.combustible, CORE);

				ArmorMaterials.addArmorTrait(materialPorksteel, ModTraits.saturpigting);
			}

			materialIrradium.addItem("oreIrradium", 1, Material.VALUE_Ore());
			materialIrradium.setFluid(ModFluids.liquidIrradium);
			materialIrradium.addCommonItems("Irradium");
			materialIrradium.setCastable(true);
			materialIrradium.setCraftable(false);
			materialIrradium.setRepresentativeItem("ingotIrradium");
			materialIrradium.addTrait(ModTraits.radioactive, HANDLE);
			materialIrradium.addTrait(ModTraits.luminescent, HANDLE);
			materialIrradium.addTrait(ModTraits.radioactive, EXTRA);
			materialIrradium.addTrait(ModTraits.luminescent, EXTRA);
			materialIrradium.addTrait(TinkerTraits.momentum);
			materialIrradium.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialIrradium, new HeadMaterialStats(700, 6f, 4f, 5));
			if (CompatManager.plustic) {
				TinkerRegistry.addMaterialStats(materialIrradium, new LaserMediumMaterialStats(8, 25));
				TinkerRegistry.addMaterialStats(materialIrradium, new BatteryCellMaterialStats(480000));
			}
			TinkerRegistry.addMaterialStats(materialIrradium, new HandleMaterialStats(3f, 40));
			TinkerRegistry.addMaterialStats(materialIrradium, new ExtraMaterialStats(40));
			TinkerRegistry.addMaterialStats(materialIrradium, new ArrowShaftMaterialStats(3f, 20));
			TinkerRegistry.addMaterialStats(materialIrradium, new BowMaterialStats(0.7f, 1.25f, 3f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialIrradium, new CoreMaterialStats(18.7f, 12.9f));
				TinkerRegistry.addMaterialStats(materialIrradium, new PlatesMaterialStats(3.0f, 2.6f, 0));
				TinkerRegistry.addMaterialStats(materialIrradium, new TrimMaterialStats(2.0f));

				ArmorMaterials.addArmorTrait(materialIrradium, ArmorTraits.steady, ModArmorTraits.mutant);

				addArmorLum(materialIrradium);
			}

			materialSolsteel.setFluid(ModFluids.liquidSolsteel);
			materialSolsteel.addCommonItems("Solarsteel");
			materialSolsteel.setCastable(true);
			materialSolsteel.setCraftable(false);
			materialSolsteel.setRepresentativeItem("ingotSolarsteel");
			materialSolsteel.addTrait(TinkerTraits.flammable, HEAD);
			materialSolsteel.addTrait(TinkerTraits.autosmelt, HEAD);
			materialSolsteel.addTrait(ModTraits.luminescent, HEAD);
			materialSolsteel.addTrait(ModTraits.burning, HEAD);
			materialSolsteel.addTrait(ModTraits.burning);
			materialSolsteel.addTrait(TinkerTraits.superheat);
			materialSolsteel.addTrait(TinkerTraits.dense);
			materialSolsteel.addTrait(ModTraits.luminescent);
			if (CompatManager.plustic) {
				TinkerRegistry.addMaterialStats(materialSolsteel, new LaserMediumMaterialStats(15, 20));
				TinkerRegistry.addMaterialStats(materialSolsteel, new BatteryCellMaterialStats(750000));
			}
			TinkerRegistry.addMaterialStats(materialSolsteel, new HeadMaterialStats(4500, 22f, 20f, 7));
			TinkerRegistry.addMaterialStats(materialSolsteel, new HandleMaterialStats(4f, 600));
			TinkerRegistry.addMaterialStats(materialSolsteel, new ExtraMaterialStats(600));
			TinkerRegistry.addMaterialStats(materialSolsteel, new ArrowShaftMaterialStats(3f, 50));
			TinkerRegistry.addMaterialStats(materialSolsteel, new BowMaterialStats(0.8f, 5.0f, 15f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialSolsteel, new CoreMaterialStats(47.4f, 40.0f));
				TinkerRegistry.addMaterialStats(materialSolsteel, new PlatesMaterialStats(4.0f, 39f, 5.25f));
				TinkerRegistry.addMaterialStats(materialSolsteel, new TrimMaterialStats(30.0f));

				ArmorMaterials.addArmorTrait(materialSolsteel, ArmorTraits.superhot, ArmorTraits.combustible);
				ArmorMaterials.addArmorTrait(materialSolsteel, ModArmorTraits.burningThorns);

				materialSolsteel.addTrait(ArmorTraits.dense, TRIM);
				materialSolsteel.addTrait(ArmorTraits.dense, PLATES);

				addArmorLum(materialSolsteel);
			}

			materialTrichromadentium.addItem("oreTrichromadentium", 1, Material.VALUE_Ore());
			materialTrichromadentium.addCommonItems("Trichromadentium");
			materialTrichromadentium.setFluid(ModFluids.liquidTrichromadentium);
			materialTrichromadentium.setCastable(true);
			materialTrichromadentium.setCraftable(false);
			materialTrichromadentium.setRepresentativeItem("ingotTrichromadentium");
			materialTrichromadentium.addTrait(ModTraits.luminescent);
			materialTrichromadentium.addTrait(ModTraits.trichromic);
			materialTrichromadentium.addTrait(TinkerTraits.unnatural);
			materialTrichromadentium.addTrait(TinkerTraits.coldblooded);
			if (CompatManager.plustic) {
				TinkerRegistry.addMaterialStats(materialTrichromadentium, new LaserMediumMaterialStats(15, 20));
				TinkerRegistry.addMaterialStats(materialTrichromadentium, new BatteryCellMaterialStats(2000));
			}
			TinkerRegistry.addMaterialStats(materialTrichromadentium, new HeadMaterialStats(1800, 20f, 18f, 8));
			TinkerRegistry.addMaterialStats(materialTrichromadentium, new HandleMaterialStats(4f, 60));
			TinkerRegistry.addMaterialStats(materialTrichromadentium, new ExtraMaterialStats(500));
			TinkerRegistry.addMaterialStats(materialTrichromadentium, new ArrowShaftMaterialStats(3f, 75));
			TinkerRegistry.addMaterialStats(materialTrichromadentium, new BowMaterialStats(0.6f, 2.0f, 14f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialTrichromadentium, new CoreMaterialStats(30f, 37f));
				TinkerRegistry.addMaterialStats(materialTrichromadentium, new PlatesMaterialStats(4.0f, 3.9f, 4.2f));
				TinkerRegistry.addMaterialStats(materialTrichromadentium, new TrimMaterialStats(25.0f));

				ArmorMaterials.addArmorTrait(materialTrichromadentium, ArmorTraits.invigorating);
				materialTrichromadentium.addTrait(ModTraits.trichromic, CORE);
				materialTrichromadentium.addTrait(ModTraits.trichromic, TRIM);
				materialTrichromadentium.addTrait(ModTraits.trichromic, PLATES);

				addArmorLum(materialTrichromadentium);
			}
			materialAtronium.addItem("oreAtronium", 1, Material.VALUE_Ore());
			materialAtronium.addCommonItems("Atronium");
			materialAtronium.setFluid(ModFluids.liquidAtronium);
			materialAtronium.setCastable(true);
			materialAtronium.setCraftable(false);
			materialAtronium.setRepresentativeItem("ingotAtronium");
			materialAtronium.addTrait(TinkerTraits.lightweight);
			materialAtronium.addTrait(ModTraits.magicallyReactive);
			materialAtronium.addTrait(ModTraits.penetrant);
			materialAtronium.addTrait(ModTraits.etheralHarvest, HEAD);
			materialAtronium.addTrait(TinkerTraits.lightweight, HEAD);
			materialAtronium.addTrait(ModTraits.magicallyReactive, HEAD);
			materialAtronium.addTrait(ModTraits.penetrant, HEAD);
			TinkerRegistry.addMaterialStats(materialAtronium, new HeadMaterialStats(1500, 18f, 17f, 8));
			TinkerRegistry.addMaterialStats(materialAtronium, new HandleMaterialStats(3f, 180));
			TinkerRegistry.addMaterialStats(materialAtronium, new ExtraMaterialStats(750));
			TinkerRegistry.addMaterialStats(materialAtronium, new ArrowShaftMaterialStats(2f, 120));
			TinkerRegistry.addMaterialStats(materialAtronium, new BowMaterialStats(0.6f, 1.25f, 12f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialAtronium, new CoreMaterialStats(27.4f, 35.5f));
				TinkerRegistry.addMaterialStats(materialAtronium, new PlatesMaterialStats(3.0f, 11.7f, 3.7f));
				TinkerRegistry.addMaterialStats(materialAtronium, new TrimMaterialStats(37.5f));

				materialAtronium.addTrait(ArmorTraits.dense, CORE);

				ArmorMaterials.addArmorTrait(materialAtronium, ArmorTraits.lightweight);
				ArmorMaterials.addArmorTrait(materialAtronium, ModTraits.magicallyReactive);
				ArmorMaterials.addArmorTrait(materialAtronium, ModArmorTraits.etherealTangibility);
			}

			materialEbonite.addItem("oreEbonite", 1, Material.VALUE_Ore());
			materialEbonite.setFluid(ModFluids.liquidEbonite);
			materialEbonite.addCommonItems("Ebonite");
			materialEbonite.setCastable(true);
			materialEbonite.setCraftable(false);
			materialEbonite.setRepresentativeItem("ingotEbonite");
			materialEbonite.addTrait(ModTraits.darkened, HEAD);
			materialEbonite.addTrait(ModTraits.luminescent, HEAD);
			materialEbonite.addTrait(TinkerTraits.poisonous, HEAD);
			materialEbonite.addTrait(ModTraits.bottomsEnd, HEAD);
			materialEbonite.addTrait(ModTraits.luminescent);
			materialEbonite.addTrait(TinkerTraits.dense);
			materialEbonite.addTrait(TinkerTraits.established);
			materialEbonite.materialTextColor = 0x544470;
			TinkerRegistry.addMaterialStats(materialEbonite, new HeadMaterialStats(750, 6.0f, 9.0f, 7));
			TinkerRegistry.addMaterialStats(materialEbonite, new HandleMaterialStats(2.5f, 60));
			TinkerRegistry.addMaterialStats(materialEbonite, new ExtraMaterialStats(25));
			TinkerRegistry.addMaterialStats(materialEbonite, new ArrowShaftMaterialStats(1.5f, 20));
			TinkerRegistry.addMaterialStats(materialEbonite, new BowMaterialStats(0.8f, 1.25f, 4f));
			if (CompatManager.plustic) {
				TinkerRegistry.addMaterialStats(materialEbonite, new LaserMediumMaterialStats(12, 20));
				TinkerRegistry.addMaterialStats(materialEbonite, new BatteryCellMaterialStats(120000));
			}
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialEbonite, new CoreMaterialStats(19.4f, 22.4f));
				TinkerRegistry.addMaterialStats(materialEbonite, new PlatesMaterialStats(2.5f, 3.9f, 1));
				TinkerRegistry.addMaterialStats(materialEbonite, new TrimMaterialStats(1.25f));

				ArmorMaterials.addArmorTrait(materialEbonite, ArmorTraits.vengeful, ArmorTraits.dense);
				ArmorMaterials.addArmorTrait(materialEbonite, ModTraits.darkened, ArmorTraits.rough);

				addArmorLum(materialEbonite);
			}

			materialSpaceTimeDisruption.addTrait(TinkerTraits.dense);
			materialSpaceTimeDisruption.addTrait(ModTraits.luminescent);
			materialSpaceTimeDisruption.addTrait(ModTraits.blinkdrawn);
			TinkerRegistry.addMaterialStats(materialSpaceTimeDisruption, new BowMaterialStats(Float.MAX_VALUE, 3f, 3f)); // big
			TinkerRegistry.addMaterialStats(materialSpaceTimeDisruption, new HeadMaterialStats(500, 10f, 8f, 6));

			materialVoidSpar.addItem("gemVoidSpar", 1, Material.VALUE_Ingot);
			materialVoidSpar.addItem("blockVoidSpar", 1, Material.VALUE_Block);
			materialVoidSpar.setCastable(false);
			materialVoidSpar.setCraftable(true);
			materialVoidSpar.setRepresentativeItem("gemVoidSpar");
			materialVoidSpar.addTrait(ModTraits.voidic, HEAD);
			materialVoidSpar.addTrait(ModTraits.bottomsEnd, HEAD);
			materialVoidSpar.addTrait(ModTraits.luminescent, HEAD);
			materialVoidSpar.addTrait(ModTraits.luminescent);
			materialVoidSpar.addTrait(TinkerTraits.dense);
			materialVoidSpar.addTrait(TinkerTraits.ecological);
			TinkerRegistry.addMaterialStats(materialVoidSpar, new HeadMaterialStats(300, 6f, 10f, 5));
			TinkerRegistry.addMaterialStats(materialVoidSpar, new HandleMaterialStats(2f, -50));
			TinkerRegistry.addMaterialStats(materialVoidSpar, new ExtraMaterialStats(2));
			TinkerRegistry.addMaterialStats(materialVoidSpar, new ArrowShaftMaterialStats(1f, 10));
			TinkerRegistry.addMaterialStats(materialVoidSpar, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialVoidSpar, new CoreMaterialStats(12.2f, 24.1f));
				TinkerRegistry.addMaterialStats(materialVoidSpar, new PlatesMaterialStats(2.0f, -3.2f, 1.5f));
				TinkerRegistry.addMaterialStats(materialVoidSpar, new TrimMaterialStats(0.1f));

				ArmorMaterials.addArmorTrait(materialVoidSpar, ModTraits.voidic, ArmorTraits.dense);
				materialVoidSpar.addTrait(ArmorTraits.ecological, TRIM);
				materialVoidSpar.addTrait(ArmorTraits.ecological, PLATES);

				addArmorLum(materialVoidSpar);
			}

			materialGarstone.addItem("gemGarstone", 1, Material.VALUE_Ingot);
			materialGarstone.addItem("blockGarstone", 1, Material.VALUE_Block);
			materialGarstone.setCastable(false);
			materialGarstone.setCraftable(true);
			materialGarstone.setRepresentativeItem("gemGarstone");
			materialGarstone.addTrait(TinkerTraits.coldblooded, HEAD);
			materialGarstone.addTrait(ModTraits.luminescent, HEAD);
			materialGarstone.addTrait(TinkerTraits.jagged, HEAD);
			materialGarstone.addTrait(TinkerTraits.unnatural);
			materialGarstone.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialGarstone, new HeadMaterialStats(500, 6f, 10f, 5));
			TinkerRegistry.addMaterialStats(materialGarstone, new HandleMaterialStats(2f, -50));
			TinkerRegistry.addMaterialStats(materialGarstone, new ExtraMaterialStats(2));
			TinkerRegistry.addMaterialStats(materialGarstone, new ArrowShaftMaterialStats(1f, 10));
			TinkerRegistry.addMaterialStats(materialGarstone, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialGarstone, new CoreMaterialStats(15.8f, 24.1f));
				TinkerRegistry.addMaterialStats(materialGarstone, new PlatesMaterialStats(2.0f, -3.2f, 2.2f));
				TinkerRegistry.addMaterialStats(materialGarstone, new TrimMaterialStats(0.1f));

				ArmorMaterials.addArmorTrait(materialGarstone, ArmorTraits.vengeful);
				ArmorMaterials.addArmorTrait(materialGarstone, ArmorTraits.rough);

				addArmorLum(materialGarstone);
			}

			materialEchostone.addItem("gemEchostone", 1, Material.VALUE_Ingot);
			materialEchostone.addItem("blockEchostone", 1, Material.VALUE_Block);
			materialEchostone.setCastable(false);
			materialEchostone.setCraftable(true);
			materialEchostone.setRepresentativeItem("gemEchostone");
			materialEchostone.addTrait(TinkerTraits.unnatural);
			materialEchostone.addTrait(ModTraits.eularsWrath);
			materialEchostone.addTrait(ModTraits.afterimage, HEAD);
			materialEchostone.addTrait(ModTraits.luminescent, HEAD);
			materialEchostone.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialEchostone, new HeadMaterialStats(500, 6f, 10f, 5));
			TinkerRegistry.addMaterialStats(materialEchostone, new HandleMaterialStats(2f, -50));
			TinkerRegistry.addMaterialStats(materialEchostone, new ExtraMaterialStats(2));
			TinkerRegistry.addMaterialStats(materialEchostone, new ArrowShaftMaterialStats(1f, 10));
			TinkerRegistry.addMaterialStats(materialEchostone, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialEchostone, new CoreMaterialStats(15.8f, 24.1f));
				TinkerRegistry.addMaterialStats(materialEchostone, new PlatesMaterialStats(2.0f, -3.2f, 0.2f));
				TinkerRegistry.addMaterialStats(materialEchostone, new TrimMaterialStats(0.1f));

				ArmorMaterials.addArmorTrait(materialEchostone, ModArmorTraits.warpedEcho, ArmorTraits.lightweight);

				addArmorLum(materialEchostone);
			}

			materialBloodstone.addItem("gemBloodstone", 1, Material.VALUE_Ingot);
			materialBloodstone.addItem("blockBloodstone", 1, Material.VALUE_Block);
			materialBloodstone.setCastable(false);
			materialBloodstone.setCraftable(true);
			materialBloodstone.setRepresentativeItem("gemBloodstone");
			materialBloodstone.addTrait(TinkerTraits.coldblooded, HEAD);
			materialBloodstone.addTrait(ModTraits.bottomsEnd, HEAD);
			materialBloodstone.addTrait(ModTraits.slicing, HEAD);
			materialBloodstone.addTrait(ModTraits.leeching);
			TinkerRegistry.addMaterialStats(materialBloodstone, new HeadMaterialStats(750, 7f, 12f, 6));
			TinkerRegistry.addMaterialStats(materialBloodstone, new HandleMaterialStats(1.125f, -35));
			TinkerRegistry.addMaterialStats(materialBloodstone, new ExtraMaterialStats(75));
			TinkerRegistry.addMaterialStats(materialBloodstone, new ArrowShaftMaterialStats(2.35f, 80));
			TinkerRegistry.addMaterialStats(materialBloodstone, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialBloodstone, new CoreMaterialStats(19.4f, 27.5f));
				TinkerRegistry.addMaterialStats(materialBloodstone, new PlatesMaterialStats(1.125f, -2.3f, 2));
				TinkerRegistry.addMaterialStats(materialBloodstone, new TrimMaterialStats(3.75f));

				ArmorMaterials.addArmorTrait(materialBloodstone, ModArmorTraits.sapping, ArmorTraits.rough);

			}

			materialErythynite.addItem("gemErythynite", 1, Material.VALUE_Ingot);
			materialErythynite.addItem("blockErythynite", 1, Material.VALUE_Block);
			materialErythynite.setCastable(false);
			materialErythynite.setCraftable(true);
			materialErythynite.setRepresentativeItem("gemErythynite");
			materialErythynite.addTrait(TinkerTraits.unnatural, HEAD);
			materialErythynite.addTrait(ModTraits.hardcore, HEAD);
			materialErythynite.addTrait(ModTraits.luminescent, HEAD);
			materialErythynite.addTrait(TinkerTraits.hovering, HEAD);
			materialErythynite.addTrait(ModTraits.leeching);
			materialErythynite.addTrait(TinkerTraits.hovering);
			materialErythynite.addTrait(ModTraits.weightless);
			materialErythynite.addTrait(TinkerTraits.enderference);
			materialErythynite.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialErythynite, new HeadMaterialStats(600, 7.5f, 10f, 7));
			TinkerRegistry.addMaterialStats(materialErythynite, new HandleMaterialStats(2.2f, 60));
			TinkerRegistry.addMaterialStats(materialErythynite, new ExtraMaterialStats(30));
			TinkerRegistry.addMaterialStats(materialErythynite, new ArrowShaftMaterialStats(1.5f, 20));
			if (CompatManager.plustic) {
				TinkerRegistry.addMaterialStats(materialErythynite, new LaserMediumMaterialStats(12, 20));
			}
			TinkerRegistry.addMaterialStats(materialErythynite, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialErythynite, new CoreMaterialStats(17.3f, 24.1f));
				TinkerRegistry.addMaterialStats(materialErythynite, new PlatesMaterialStats(2.2f, 3.9f, 1.3f));
				TinkerRegistry.addMaterialStats(materialErythynite, new TrimMaterialStats(1.5f));

				ArmorMaterials.addArmorTrait(materialErythynite, ModArmorTraits.weightless);
				ArmorMaterials.addArmorTrait(materialErythynite, ArmorTraits.dense, ArmorTraits.lightweight);

				addArmorLum(materialErythynite);
			}

			materialGravitonium.addItem("oreGravitonium", 1, Material.VALUE_Ore());
			materialGravitonium.addItem("oreGravitoniumDense", 1, Material.VALUE_Ore() * 4);
			materialGravitonium.setFluid(ModFluids.liquidGravitonium);
			materialGravitonium.addCommonItems("Gravitonium");
			materialGravitonium.setCastable(true);
			materialGravitonium.setCraftable(false);
			materialGravitonium.setRepresentativeItem("ingotGravitonium");
			materialGravitonium.addTrait(ModTraits.hypergravity);
			materialGravitonium.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialGravitonium, new HeadMaterialStats(700, 16f, 12f, 5));
			if (CompatManager.plustic) {
				TinkerRegistry.addMaterialStats(materialGravitonium, new LaserMediumMaterialStats(12, 25));
				TinkerRegistry.addMaterialStats(materialGravitonium, new BatteryCellMaterialStats(480000));
			}
			TinkerRegistry.addMaterialStats(materialGravitonium, new HandleMaterialStats(3f, 400));
			TinkerRegistry.addMaterialStats(materialGravitonium, new ExtraMaterialStats(300));
			TinkerRegistry.addMaterialStats(materialGravitonium, new ArrowShaftMaterialStats(3f, 50));
			TinkerRegistry.addMaterialStats(materialGravitonium, new BowMaterialStats(0.5f, 1.5f, 4f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialGravitonium, new CoreMaterialStats(18.7f, 27.5f));
				TinkerRegistry.addMaterialStats(materialGravitonium, new PlatesMaterialStats(3.0f, 26f, 1.6969420f));
				TinkerRegistry.addMaterialStats(materialGravitonium, new TrimMaterialStats(15.0f));

				ArmorMaterials.addArmorTrait(materialGravitonium, ArmorTraits.magnetic2, ArmorTraits.magnetic);
				ArmorMaterials.addArmorTrait(materialGravitonium, ModArmorTraits.gravitating);

				addArmorLum(materialGravitite);
			}

			materialGallium.addCommonItems("Gallium");
			materialGallium.setFluid(ModFluids.liquidGallium);
			materialGallium.setCastable(true);
			materialGallium.setCraftable(true);
			materialGallium.setRepresentativeItem("ingotGallium");
			materialGallium.addTrait(TinkerTraits.aquadynamic);
			materialGallium.addTrait(ModTraits.liquid);
			materialGallium.addTrait(TinkerTraits.crumbling);
			materialGallium.materialTextColor = 0xadb7ba;
			TinkerRegistry.addMaterialStats(materialGallium, new HeadMaterialStats(100, 6f, 5f, 5));
			TinkerRegistry.addMaterialStats(materialGallium, new HandleMaterialStats(2f, -50));
			TinkerRegistry.addMaterialStats(materialGallium, new ExtraMaterialStats(2));
			TinkerRegistry.addMaterialStats(materialGallium, new ArrowShaftMaterialStats(1f, 10));
			TinkerRegistry.addMaterialStats(materialGallium, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialGallium, new CoreMaterialStats(7.1f, 14.9f));
				TinkerRegistry.addMaterialStats(materialGallium, new PlatesMaterialStats(2.0f, -3.2f, 0));
				TinkerRegistry.addMaterialStats(materialGallium, new TrimMaterialStats(0.1f));

				ArmorMaterials.addArmorTrait(materialGallium, ModArmorTraits.liquid);
				ArmorMaterials.addArmorTrait(materialGallium, ArmorTraits.aquaspeed);
			}

			materialRuneSteel.addCommonItems("Runesteel");
			materialRuneSteel.setFluid(ModFluids.liquidRuneSteel);
			materialRuneSteel.setCastable(true);
			materialRuneSteel.setCraftable(false);
			materialRuneSteel.addTrait(ModTraits.magical);
			materialRuneSteel.addTrait(ModTraits.warding);
			materialRuneSteel.setRepresentativeItem("ingotRunesteel");
			TinkerRegistry.addMaterialStats(materialRuneSteel, new HeadMaterialStats(900, 18f, 14f, 7));
			TinkerRegistry.addMaterialStats(materialRuneSteel, new HandleMaterialStats(4f, 60));
			TinkerRegistry.addMaterialStats(materialRuneSteel, new ExtraMaterialStats(60));
			TinkerRegistry.addMaterialStats(materialRuneSteel, new ArrowShaftMaterialStats(3f, 20));
			TinkerRegistry.addMaterialStats(materialRuneSteel, new BowMaterialStats(0.75f, 2.5f, 6f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialRuneSteel, new CoreMaterialStats(21.2f, 30.7f));
				TinkerRegistry.addMaterialStats(materialRuneSteel, new PlatesMaterialStats(4.0f, 3.9f, 2.6f));
				TinkerRegistry.addMaterialStats(materialRuneSteel, new TrimMaterialStats(3.0f));

				ArmorMaterials.addArmorTrait(materialRuneSteel, ModArmorTraits.aegisIncantamentum);
				ArmorMaterials.addArmorTrait(materialRuneSteel, ModTraits.warding);

			}

			materialEnderal.addItem("gemEnderal", 1, Material.VALUE_Ingot);
			materialEnderal.addItem("blockEnderal", 1, Material.VALUE_Block);
			materialEnderal.setCastable(false);
			materialEnderal.setCraftable(true);
			materialEnderal.setRepresentativeItem("gemEnderal");
			materialEnderal.addTrait(TinkerTraits.enderference);
			materialEnderal.addTrait(TinkerTraits.endspeed, PROJECTILE);
			materialEnderal.addTrait(TinkerTraits.enderference, HEAD);
			materialEnderal.addTrait(TinkerTraits.jagged, HEAD);
			materialEnderal.addTrait(ModTraits.pulsating, HEAD);
			materialEnderal.addTrait(ModTraits.luminescent, HEAD);
			materialEnderal.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialEnderal, new HeadMaterialStats(390, 6f, 10f, 5));
			TinkerRegistry.addMaterialStats(materialEnderal, new HandleMaterialStats(2f, -50));
			TinkerRegistry.addMaterialStats(materialEnderal, new ExtraMaterialStats(2));
			TinkerRegistry.addMaterialStats(materialEnderal, new ArrowShaftMaterialStats(1f, 10));
			TinkerRegistry.addMaterialStats(materialEnderal, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialEnderal, new CoreMaterialStats(14f, 24.1f));
				TinkerRegistry.addMaterialStats(materialEnderal, new PlatesMaterialStats(2.0f, -3.2f, 1));
				TinkerRegistry.addMaterialStats(materialEnderal, new TrimMaterialStats(0.1f));

				ArmorMaterials.addArmorTrait(materialEnderal, ModArmorTraits.pulsating, ArmorTraits.rough);
				materialEnderal.addTrait(ArmorTraits.rough, CORE);

				addArmorLum(materialEnderal);
			}

			materialEnderexamite.setCastable(false);
			materialEnderexamite.setCraftable(false);
			materialEnderexamite.addTrait(TinkerTraits.slimeyBlue, HEAD);
			materialEnderexamite.addTrait(ModTraits.pulsating, HEAD);
			materialEnderexamite.addTrait(ModTraits.warding, HEAD);
			materialEnderexamite.addTrait(TinkerTraits.crumbling);
			materialEnderexamite.addTrait(TinkerTraits.endspeed, PROJECTILE);
			materialEnderexamite.addTrait(ModTraits.resilient);
			TinkerRegistry.addMaterialStats(materialEnderexamite, new HeadMaterialStats(1300, 7f, 11f, 5));
			TinkerRegistry.addMaterialStats(materialEnderexamite, new HandleMaterialStats(2.25f, 20));
			TinkerRegistry.addMaterialStats(materialEnderexamite, new ExtraMaterialStats(12));
			TinkerRegistry.addMaterialStats(materialEnderexamite, new ArrowShaftMaterialStats(1f, 15));
			TinkerRegistry.addMaterialStats(materialEnderexamite, new BowMaterialStats(0.75f, 2.5f, 4f));
			CompositeRegistry.registerComposite(() -> materialEnderal, () -> materialEnderexamite, () -> TinkerFluids.knightslime);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialEnderexamite, new CoreMaterialStats(25.5f, 25.8f));
				TinkerRegistry.addMaterialStats(materialEnderexamite, new PlatesMaterialStats(2.25f, 1.3f, 2));
				TinkerRegistry.addMaterialStats(materialEnderexamite, new TrimMaterialStats(0.60f));

				materialEnderexamite.addTrait(ArmorTraits.slimeyBlue, CORE);
				materialEnderexamite.addTrait(ModTraits.warding, CORE);
				materialEnderexamite.addTrait(ModArmorTraits.pulsating);
				ArmorMaterials.addArmorTrait(materialEnderexamite, ModTraits.voidic, ArmorTraits.dense);
			}

			materialShadowglass.setCastable(false);
			materialShadowglass.setCraftable(false);
			materialShadowglass.addTrait(TinkerTraits.jagged, HEAD);
			materialShadowglass.addTrait(ModTraits.darkened, HEAD);
			materialShadowglass.addTrait(TinkerTraits.unnatural);
			materialShadowglass.addTrait(ModTraits.hardcore);
			TinkerRegistry.addMaterialStats(materialShadowglass, new HeadMaterialStats(1630, 17f, 17f, 6));
			TinkerRegistry.addMaterialStats(materialShadowglass, new HandleMaterialStats(2.2f, 120));
			TinkerRegistry.addMaterialStats(materialShadowglass, new ExtraMaterialStats(650));
			TinkerRegistry.addMaterialStats(materialShadowglass, new ArrowShaftMaterialStats(1.1f, 22));
			TinkerRegistry.addMaterialStats(materialShadowglass, new BowMaterialStats(0.55f, 2.5f, 6f));
			CompositeRegistry.registerComposite(() -> materialPerimidum, () -> materialShadowglass, () -> ModFluids.liquidEbonite);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialShadowglass, new CoreMaterialStats(28.5f, 35.5f));
				TinkerRegistry.addMaterialStats(materialShadowglass, new PlatesMaterialStats(2.2f, 7.8f, 3.3f));
				TinkerRegistry.addMaterialStats(materialShadowglass, new TrimMaterialStats(32.5f));

				ArmorMaterials.addArmorTrait(materialShadowglass, ArmorTraits.rough, ArmorTraits.subterranean);
				materialShadowglass.addTrait(ModTraits.darkened, CORE);
			}

			TinkerRegistry.addMaterialStats(materialPlasma, new HeadMaterialStats(4096, 6f, 12f, 5));
			materialPlasma.addTrait(ModTraits.plasmatic);
			materialPlasma.addTrait(ModTraits.luminescent);

			TinkerRegistry.addMaterialStats(materialMirrored, new FletchingMaterialStats(0.95f, 3f));
			materialMirrored.addTrait(ModTraits.mirroring);

			TinkerRegistry.addMaterialStats(materialTriblade, new HeadMaterialStats(2056, 3f, 8f, 5));
			TinkerRegistry.addMaterialStats(materialTriblade, new ExtraMaterialStats(128));
			materialTriblade.addTrait(ModTraits.tripleshot);

			TinkerRegistry.addMaterialStats(materialTrailblazer, new HeadMaterialStats(2056 * 2, 6f, 18f, 7));
			TinkerRegistry.addMaterialStats(materialTrailblazer, new ExtraMaterialStats(512 * 2));
			materialTrailblazer.addTrait(ModTraits.aerialFlames);
			materialTrailblazer.addTrait(ModTraits.luminescent);

			materialIgniglomerate.addItem("gemIgniglomerate", 1, Material.VALUE_Ingot);
			materialIgniglomerate.addItem("blockIgniglomerate", 1, Material.VALUE_Block);
			materialIgniglomerate.setCastable(false);
			materialIgniglomerate.setCraftable(true);
			materialIgniglomerate.setRepresentativeItem("gemIgniglomerate");
			materialIgniglomerate.addTrait(ModTraits.hyperheat, HEAD);
			materialIgniglomerate.addTrait(ModTraits.luminescent, HEAD);
			materialIgniglomerate.addTrait(TinkerTraits.autosmelt, HANDLE);
			materialIgniglomerate.addTrait(ModTraits.luminescent, HANDLE);
			materialIgniglomerate.addTrait(TinkerTraits.autosmelt, EXTRA);
			materialIgniglomerate.addTrait(ModTraits.luminescent, EXTRA);
			materialIgniglomerate.addTrait(ModTraits.hyperheat, SHAFT);
			materialIgniglomerate.addTrait(ModTraits.luminescent, SHAFT);
			materialIgniglomerate.addTrait(ModTraits.burning);
			materialIgniglomerate.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialIgniglomerate, new HeadMaterialStats(700, 6f, 7f, 7));
			TinkerRegistry.addMaterialStats(materialIgniglomerate, new HandleMaterialStats(1.25f, -10));
			TinkerRegistry.addMaterialStats(materialIgniglomerate, new ExtraMaterialStats(250));
			TinkerRegistry.addMaterialStats(materialIgniglomerate, new ArrowShaftMaterialStats(1f, 10));
			TinkerRegistry.addMaterialStats(materialIgniglomerate, whyWouldYouMakeABowOutOfThis);
			if (ConfigHandler.enableBomb) {
				TinkerRegistry.addMaterialStats(materialIgniglomerate, new ExplosiveMaterialStats(2.5f, 20));
				materialIgniglomerate.addTrait(ModTraits.hyperheat, Bomb.EXPLOSIVE_CHARGE);
				materialIgniglomerate.addTrait(ModTraits.luminescent, Bomb.EXPLOSIVE_CHARGE);
			}
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialIgniglomerate, new CoreMaterialStats(18.7f, 18.8f));
				TinkerRegistry.addMaterialStats(materialIgniglomerate, new PlatesMaterialStats(1.25f, -0.7f, 0));
				TinkerRegistry.addMaterialStats(materialIgniglomerate, new TrimMaterialStats(12.5f));

				ArmorMaterials.addArmorTrait(materialIgniglomerate, ModArmorTraits.unmeltable);
				ArmorMaterials.addArmorTrait(materialIgniglomerate, ModArmorTraits.burningThorns);

				materialIgniglomerate.addTrait(ArmorTraits.autoforge, CORE);

				addArmorLum(materialIgniglomerate);
			}

			materialEtherstone.addItem("gemEtherstone", 1, Material.VALUE_Ingot);
			materialEtherstone.addItem("blockEtherstone", 1, Material.VALUE_Block);
			materialEtherstone.setCastable(false);
			materialEtherstone.setCraftable(true);
			materialEtherstone.setRepresentativeItem("gemEtherstone");
			materialEtherstone.addTrait(ModTraits.etheralHarvest, HEAD);
			materialEtherstone.addTrait(ModTraits.darkened, HEAD);
			materialEtherstone.addTrait(ModTraits.overdrive);
			materialEtherstone.addTrait(ModTraits.hardcore);
			materialEtherstone.addTrait(ModTraits.voidic);
			materialEtherstone.addTrait(ModTraits.luminescent, HEAD);
			materialEtherstone.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialEtherstone, new HeadMaterialStats(1450, 17.5f, 18f, 8));
			TinkerRegistry.addMaterialStats(materialEtherstone, new HandleMaterialStats(4f, -50));
			TinkerRegistry.addMaterialStats(materialEtherstone, new ExtraMaterialStats(500));
			TinkerRegistry.addMaterialStats(materialEtherstone, new ArrowShaftMaterialStats(1f, 10));
			TinkerRegistry.addMaterialStats(materialEtherstone, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialEtherstone, new CoreMaterialStats(26.9f, 37f));
				TinkerRegistry.addMaterialStats(materialEtherstone, new PlatesMaterialStats(4.0f, -3.2f, 3.8f));
				TinkerRegistry.addMaterialStats(materialEtherstone, new TrimMaterialStats(25.0f));

				ArmorMaterials.addArmorTrait(materialEtherstone, ModArmorTraits.etherealTangibility);
				ArmorMaterials.addArmorTrait(materialEtherstone, ModTraits.darkened, ModArmorTraits.overlasting);

				materialEtherstone.addTrait(ModTraits.voidic, TRIM);
				materialEtherstone.addTrait(ModTraits.voidic, PLATES);

				addArmorLum(materialEtherstone);
			}

			TinkerRegistry.addMaterialStats(materialEssencore, new HeadMaterialStats(4096, 10f, 12f, 7));
			materialEssencore.addTrait(ModTraits.essentialObliteration);
			materialEssencore.addTrait(ModTraits.luminescent);

			materialElectarite.addItem("gemElectarite", 1, Material.VALUE_Ingot);
			materialElectarite.addItem("blockElectarite", 1, Material.VALUE_Block);
			materialElectarite.setCastable(false);
			materialElectarite.setCraftable(true);
			materialElectarite.setRepresentativeItem("gemElectarite");
			materialElectarite.addTrait(ModTraits.luminescent, HEAD);
			materialElectarite.addTrait(ModTraits.electrified, HEAD);
			materialElectarite.addTrait(ModTraits.stormcaller, HEAD);
			materialElectarite.addTrait(TinkerTraits.unnatural);
			materialElectarite.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialElectarite, new HeadMaterialStats(620, 6f, 7f, 5));
			TinkerRegistry.addMaterialStats(materialElectarite, new HandleMaterialStats(2.2f, 10));
			TinkerRegistry.addMaterialStats(materialElectarite, new ExtraMaterialStats(70));
			TinkerRegistry.addMaterialStats(materialElectarite, new ArrowShaftMaterialStats(1.2f, 10));
			TinkerRegistry.addMaterialStats(materialElectarite, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialElectarite, new CoreMaterialStats(17.6f, 18.8f));
				TinkerRegistry.addMaterialStats(materialElectarite, new PlatesMaterialStats(2.2f, 0.7f, 0.2f));
				TinkerRegistry.addMaterialStats(materialElectarite, new TrimMaterialStats(3.5f));

				ArmorMaterials.addArmorTrait(materialElectarite, ArmorTraits.dramatic);
				materialElectarite.addTrait(ModTraits.stormcaller, CORE);

				addArmorLum(materialElectarite);
			}

			materialHallowsite.addCommonItems("Hallowsite");
			materialHallowsite.setFluid(ModFluids.liquidHallowsite);
			materialHallowsite.setCastable(true);
			materialHallowsite.setCraftable(false);
			materialHallowsite.setRepresentativeItem("ingotHallowsite");
			materialHallowsite.addTrait(ModTraits.haunted, HEAD);
			materialHallowsite.addTrait(ModTraits.soulforged2, HEAD);
			materialHallowsite.addTrait(ModTraits.soulforged);
			TinkerRegistry.addMaterialStats(materialHallowsite, new HeadMaterialStats(620, 6f, 7f, 5));
			TinkerRegistry.addMaterialStats(materialHallowsite, new HandleMaterialStats(2.2f, 10));
			TinkerRegistry.addMaterialStats(materialHallowsite, new ExtraMaterialStats(70));
			TinkerRegistry.addMaterialStats(materialHallowsite, new ArrowShaftMaterialStats(1.2f, 10));
			TinkerRegistry.addMaterialStats(materialHallowsite, new BowMaterialStats(0.8f, 1.4f, 1f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialHallowsite, new CoreMaterialStats(17.6f, 18.8f));
				TinkerRegistry.addMaterialStats(materialHallowsite, new PlatesMaterialStats(2.2f, 0.7f, 0));
				TinkerRegistry.addMaterialStats(materialHallowsite, new TrimMaterialStats(3.5f));

				ArmorMaterials.addArmorTrait(materialHallowsite, ModArmorTraits.hauntingThorns);
				ArmorMaterials.addArmorTrait(materialHallowsite, ArmorTraits.skeletal);

			}

			materialBlightsteel.addCommonItems("Blightsteel");
			materialBlightsteel.setFluid(ModFluids.liquidBlightsteel);
			materialBlightsteel.setCastable(true);
			materialBlightsteel.setCraftable(false);
			materialBlightsteel.setRepresentativeItem("ingotBlightsteel");
			materialBlightsteel.addTrait(ModTraits.withered, HEAD);
			materialBlightsteel.addTrait(ModTraits.blighted, HEAD);
			materialBlightsteel.addTrait(ModTraits.withered);
			materialBlightsteel.addTrait(ModTraits.soulforged);
			TinkerRegistry.addMaterialStats(materialBlightsteel, new HeadMaterialStats(1020, 6.5f, 8f, 6));
			TinkerRegistry.addMaterialStats(materialBlightsteel, new HandleMaterialStats(2.5f, 80));
			TinkerRegistry.addMaterialStats(materialBlightsteel, new ExtraMaterialStats(100));
			TinkerRegistry.addMaterialStats(materialBlightsteel, new ArrowShaftMaterialStats(1.5f, 125));
			TinkerRegistry.addMaterialStats(materialBlightsteel, new BowMaterialStats(1.2f, 1.3f, 0.8f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialBlightsteel, new CoreMaterialStats(22.6f, 20.6f));
				TinkerRegistry.addMaterialStats(materialBlightsteel, new PlatesMaterialStats(2.5f, 5.2f, 3.2f));
				TinkerRegistry.addMaterialStats(materialBlightsteel, new TrimMaterialStats(5.0f));

				materialBlightsteel.addTrait(ModArmorTraits.blightshield, CORE);
				materialBlightsteel.addTrait(ModArmorTraits.woeful, TRIM);
				materialBlightsteel.addTrait(ModArmorTraits.woeful, PLATES);

				ArmorMaterials.addArmorTrait(materialBlightsteel, ArmorTraits.infernal);

			}

			materialQuakestruck.addTrait(ModTraits.seismishock);
			TinkerRegistry.addMaterialStats(materialQuakestruck, new HeadMaterialStats(1800, 17.5f, 10f, 8));

			materialCryosplinters.addTrait(ModTraits.hailshot);
			TinkerRegistry.addMaterialStats(materialCryosplinters, new HeadMaterialStats(2048, 6f, 16f, 5));

			materialAutoloader.addTrait(ModTraits.autoloading);
			TinkerRegistry.addMaterialStats(materialAutoloader, thankYouTinkersForNeedingAHeadMat);
			TinkerRegistry.addMaterialStats(materialAutoloader, new ExtraMaterialStats(1024));
			TinkerRegistry.addMaterialStats(materialAutoloader, new HandleMaterialStats(4f, 1024));

			materialSanguiseelium.addCommonItems("Sanguiseelium");
			materialSanguiseelium.setFluid(ModFluids.liquidSanguiseelium);
			materialSanguiseelium.setCastable(true);
			materialSanguiseelium.setCraftable(false);
			materialSanguiseelium.setRepresentativeItem("ingotSanguiseelium");
			materialSanguiseelium.addTrait(ModTraits.leeching, HEAD);
			materialSanguiseelium.addTrait(ModTraits.receptive, HEAD);
			materialSanguiseelium.addTrait(ModTraits.leeching);
			materialSanguiseelium.addTrait(ModTraits.soulforged);
			TinkerRegistry.addMaterialStats(materialSanguiseelium, new HeadMaterialStats(2004, 9f, 13f, 6));
			TinkerRegistry.addMaterialStats(materialSanguiseelium, new HandleMaterialStats(2.5f, 80));
			TinkerRegistry.addMaterialStats(materialSanguiseelium, new ExtraMaterialStats(100));
			TinkerRegistry.addMaterialStats(materialSanguiseelium, new ArrowShaftMaterialStats(1.5f, 125));
			TinkerRegistry.addMaterialStats(materialSanguiseelium, new BowMaterialStats(1.2f, 1.3f, 0.8f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialSanguiseelium, new CoreMaterialStats(31.7f, 29.1f));
				TinkerRegistry.addMaterialStats(materialSanguiseelium, new PlatesMaterialStats(2.5f, 5.2f, 3.7f));
				TinkerRegistry.addMaterialStats(materialSanguiseelium, new TrimMaterialStats(5.0f));

				ArmorMaterials.addArmorTrait(materialSanguiseelium, ArmorTraits.invigorating);
				materialSanguiseelium.addTrait(ModArmorTraits.afterheal, CORE);
			}

			TinkerRegistry.addMaterialStats(materialVengeance, new HeadMaterialStats(1024, 6f, 10f, 7));
			materialVengeance.addTrait(ModTraits.offense);

			materialZracohlium.addCommonItems("Zracohlium");
			materialZracohlium.setFluid(ModFluids.liquidZracohlium);
			materialZracohlium.setCastable(true);
			materialZracohlium.setCraftable(false);
			materialZracohlium.setRepresentativeItem("ingotZracohlium");
			materialZracohlium.addTrait(ModTraits.supercritical2, HEAD);
			materialZracohlium.addTrait(TinkerTraits.coldblooded, HEAD);
			materialZracohlium.addTrait(ModTraits.pyrophoric);
			materialZracohlium.addTrait(ModTraits.supercritical1);
			materialZracohlium.addTrait(ModTraits.radioactive);
			TinkerRegistry.addMaterialStats(materialZracohlium, new HeadMaterialStats(1200, 8f, 13.5f, 5));
			TinkerRegistry.addMaterialStats(materialZracohlium, new HandleMaterialStats(1.25f, 125));
			TinkerRegistry.addMaterialStats(materialZracohlium, new ExtraMaterialStats(90));
			TinkerRegistry.addMaterialStats(materialZracohlium, new ArrowShaftMaterialStats(1.5f, 125));
			TinkerRegistry.addMaterialStats(materialZracohlium, new BowMaterialStats(1.2f, 1.3f, 0.8f));
			if (ConfigHandler.enableBomb) {
				TinkerRegistry.addMaterialStats(materialZracohlium, new ExplosiveMaterialStats(5f, 40));
				materialZracohlium.addTrait(ModTraits.pyrophoric, Bomb.EXPLOSIVE_CHARGE);
			}
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialZracohlium, new CoreMaterialStats(24.5f, 29.9f));
				TinkerRegistry.addMaterialStats(materialZracohlium, new PlatesMaterialStats(1.25f, 8.1f, 0.6f));
				TinkerRegistry.addMaterialStats(materialZracohlium, new TrimMaterialStats(4.5f));

				materialZracohlium.addTrait(ArmorTraits.dramatic, CORE);
				ArmorMaterials.addArmorTrait(materialZracohlium, ModArmorTraits.fissile);

				materialZracohlium.addTrait(ModTraits.pyrophoric, TRIM);
				materialZracohlium.addTrait(ModTraits.pyrophoric, EXTRA);
			}

			TinkerRegistry.addMaterialStats(materialDematerializer, new BowMaterialStats(1f, 3.2f, 7f));
			TinkerRegistry.addMaterialStats(materialDematerializer, new HeadMaterialStats(2069, 10f, 8f, 6));
			materialDematerializer.addTrait(ModTraits.dematerializing);

			materialIoximite.setCastable(false);
			materialIoximite.setCraftable(false);
			materialIoximite.addTrait(ModTraits.voidic, HEAD);
			materialIoximite.addTrait(ModTraits.bottomsEnd, HEAD);
			materialIoximite.addTrait(ModTraits.plasmaMissiles, HEAD);
			materialIoximite.addTrait(TinkerTraits.alien, HEAD);
			materialIoximite.addTrait(ModTraits.plasmaMissiles);
			materialIoximite.addTrait(TinkerTraits.unnatural);
			materialIoximite.addTrait(TinkerTraits.alien);
			TinkerRegistry.addMaterialStats(materialIoximite, new HeadMaterialStats(1350, 7.5f, 11f, 6));
			TinkerRegistry.addMaterialStats(materialIoximite, new HandleMaterialStats(2.5f, 85));
			TinkerRegistry.addMaterialStats(materialIoximite, new ExtraMaterialStats(30));
			TinkerRegistry.addMaterialStats(materialIoximite, new ArrowShaftMaterialStats(2.5f, 85));
			TinkerRegistry.addMaterialStats(materialIoximite, new BowMaterialStats(2f, 1.2f, 3f));
			if (CompatManager.plustic) {
				TinkerRegistry.addMaterialStats(materialIoximite, new LaserMediumMaterialStats(10, 20));
				TinkerRegistry.addMaterialStats(materialIoximite, new BatteryCellMaterialStats(100000));
			}
			CompositeRegistry.registerComposite(() -> materialVoidSpar, () -> materialIoximite, () -> ModFluids.liquidFusionite);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialIoximite, new CoreMaterialStats(26f, 25.8f));
				TinkerRegistry.addMaterialStats(materialIoximite, new PlatesMaterialStats(2.5f, 5.5f, 1.8f));
				TinkerRegistry.addMaterialStats(materialIoximite, new TrimMaterialStats(1.5f));

				ArmorMaterials.addArmorTrait(materialIoximite, ModArmorTraits.plasmaShield);
				ArmorMaterials.addArmorTrait(materialIoximite, ArmorTraits.alien);
				materialIoximite.addTrait(ModTraits.voidic, CORE);
			}

			TinkerRegistry.addMaterialStats(materialShotgun, new ExtraMaterialStats(1024));
			TinkerRegistry.addMaterialStats(materialShotgun, thankYouTinkersForNeedingAHeadMat);
			materialShotgun.addTrait(ModTraits.polyshot);

			materialMonolite.addItem("gemMonolite", 1, Material.VALUE_Ingot);
			materialMonolite.addItem("blockMonolite", 1, Material.VALUE_Block);
			materialMonolite.setCastable(false);
			materialMonolite.setCraftable(true);
			materialMonolite.setRepresentativeItem("gemMonolite");
			materialMonolite.addTrait(ModTraits.macrocrystaline);
			materialMonolite.addTrait(ModTraits.hardcore);
			materialMonolite.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialMonolite, new HeadMaterialStats(1450, 14.5f, 14f, 6));
			TinkerRegistry.addMaterialStats(materialMonolite, new HandleMaterialStats(3f, -50));
			TinkerRegistry.addMaterialStats(materialMonolite, new ExtraMaterialStats(500));
			TinkerRegistry.addMaterialStats(materialMonolite, whyWouldYouMakeABowOutOfThis);
			TinkerRegistry.addMaterialStats(materialMonolite, new ArrowShaftMaterialStats(1.2f, 35));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialMonolite, new CoreMaterialStats(26.9f, 30.7f));
				TinkerRegistry.addMaterialStats(materialMonolite, new PlatesMaterialStats(3.0f, -3.2f, 3f));
				TinkerRegistry.addMaterialStats(materialMonolite, new TrimMaterialStats(25.0f));

				ArmorMaterials.addArmorTrait(materialMonolite, ModTraits.macrocrystaline);
				ArmorMaterials.addArmorTrait(materialMonolite, ArmorTraits.dense);

				addArmorLum(materialMonolite);
			}

			materialPerimidum.addItem("gemPerimidum", 1, Material.VALUE_Ingot);
			materialPerimidum.addItem("blockPerimidum", 1, Material.VALUE_Block);
			materialPerimidum.setCastable(false);
			materialPerimidum.setCraftable(true);
			materialPerimidum.setRepresentativeItem("gemPerimidum");
			materialPerimidum.addTrait(ModTraits.bottomsEnd, HEAD);
			materialPerimidum.addTrait(ModTraits.approximate2, HEAD);
			materialPerimidum.addTrait(ModTraits.approximate);
			materialPerimidum.addTrait(ModTraits.hardcore);
			materialPerimidum.addTrait(ModTraits.luminescent, HEAD);
			materialPerimidum.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialPerimidum, new HeadMaterialStats(1230, 15.5f, 15f, 6));
			TinkerRegistry.addMaterialStats(materialPerimidum, new ArrowShaftMaterialStats(1.2f, 24));
			TinkerRegistry.addMaterialStats(materialPerimidum, new HandleMaterialStats(2.6f, -50));
			TinkerRegistry.addMaterialStats(materialPerimidum, new ExtraMaterialStats(610));
			TinkerRegistry.addMaterialStats(materialPerimidum, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialPerimidum, new CoreMaterialStats(24.8f, 32.3f));
				TinkerRegistry.addMaterialStats(materialPerimidum, new PlatesMaterialStats(2.6f, -3.2f, 3.2f));
				TinkerRegistry.addMaterialStats(materialPerimidum, new TrimMaterialStats(30.5f));

				ArmorMaterials.addArmorTrait(materialPerimidum, ModArmorTraits.evasive2, ModArmorTraits.evasive);
				materialPerimidum.addTrait(ArmorTraits.dense, TRIM);
				materialPerimidum.addTrait(ArmorTraits.dense, PLATES);

				addArmorLum(materialPerimidum);
			}

			materialGeodesium.addCommonItems("Geodesium");
			materialGeodesium.setFluid(ModFluids.liquidGeodesium);
			materialGeodesium.setCastable(true);
			materialGeodesium.setCraftable(false);
			materialGeodesium.setRepresentativeItem("ingotGeodesium");
			materialGeodesium.addTrait(TinkerTraits.aridiculous, HEAD);
			materialGeodesium.addTrait(ModTraits.pyrophoric, HEAD);
			materialGeodesium.addTrait(ModTraits.liquid, HEAD);
			materialGeodesium.addTrait(ModTraits.burning, HEAD);
			materialGeodesium.addTrait(TinkerTraits.aridiculous);
			materialGeodesium.addTrait(ModTraits.liquid);
			materialGeodesium.addTrait(ModTraits.luminescent, HEAD);
			materialGeodesium.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialGeodesium, new HeadMaterialStats(1350, 7f, 13.5f, 7));
			TinkerRegistry.addMaterialStats(materialGeodesium, new HandleMaterialStats(1.25f, 125));
			TinkerRegistry.addMaterialStats(materialGeodesium, new ExtraMaterialStats(161));
			TinkerRegistry.addMaterialStats(materialGeodesium, new ArrowShaftMaterialStats(1.3f, 125));
			TinkerRegistry.addMaterialStats(materialGeodesium, new BowMaterialStats(1.6f, 1.3f, 0.4f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialGeodesium, new CoreMaterialStats(26f, 29.9f));
				TinkerRegistry.addMaterialStats(materialGeodesium, new PlatesMaterialStats(1.25f, 8.1f, 0));
				TinkerRegistry.addMaterialStats(materialGeodesium, new TrimMaterialStats(8.05f));

				materialGeodesium.addTrait(ModTraits.pyrophoric, CORE);
				materialGeodesium.addTrait(ModArmorTraits.burningThorns, CORE);
				ArmorMaterials.addArmorTrait(materialGeodesium, ModArmorTraits.immolating);
				ArmorMaterials.addArmorTrait(materialGeodesium, ArmorTraits.aridiculous);

				addArmorLum(materialGeodesium);
			}

			materialInertialRedirector.addTrait(ModTraits.ricoshot);
			TinkerRegistry.addMaterialStats(materialInertialRedirector, new HeadMaterialStats(2048, 6f, 16f, 5));

			materialAnthracite.addItem("gemAnthracite", 1, Material.VALUE_Ingot);
			materialAnthracite.addItem("blockAnthracite", 1, Material.VALUE_Block);
			materialAnthracite.setCastable(false);
			materialAnthracite.setCraftable(true);
			materialAnthracite.setRepresentativeItem("gemAnthracite");
			materialAnthracite.addTrait(ModTraits.bottomsEnd, HEAD);
			materialAnthracite.addTrait(ModTraits.embering, HEAD);
			materialAnthracite.addTrait(ModTraits.darkened);
			materialAnthracite.addTrait(ModTraits.embering);
			TinkerRegistry.addMaterialStats(materialAnthracite, new HeadMaterialStats(800, 12.5f, 11f, 7));
			TinkerRegistry.addMaterialStats(materialAnthracite, new HandleMaterialStats(2.0f, 67));
			TinkerRegistry.addMaterialStats(materialAnthracite, new ExtraMaterialStats(40));
			TinkerRegistry.addMaterialStats(materialAnthracite, new ArrowShaftMaterialStats(1.5f, 20));
			TinkerRegistry.addMaterialStats(materialAnthracite, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialAnthracite, new CoreMaterialStats(16f, 22f));
				TinkerRegistry.addMaterialStats(materialAnthracite, new PlatesMaterialStats(2f, 4f, 1.4f));
				TinkerRegistry.addMaterialStats(materialAnthracite, new TrimMaterialStats(1.2f));

				ArmorMaterials.addArmorTrait(materialAnthracite, ModArmorTraits.burningThorns, ArmorTraits.combustible);

				addArmorLum(materialAnthracite);
			}
			
			materialIonstone.addItem("gemIonstone", 1, Material.VALUE_Ingot);
			materialIonstone.addItem("blockIonstone", 1, Material.VALUE_Block);
			materialIonstone.setCastable(false);
			materialIonstone.setCraftable(true);
			materialIonstone.setRepresentativeItem("gemIonstone");
			materialIonstone.addTrait(ModTraits.etheralHarvest, HEAD);
			materialIonstone.addTrait(ModTraits.voltrend, HEAD);
			materialIonstone.addTrait(ModTraits.stormcaller);
			materialIonstone.addTrait(TinkerTraits.shocking);
			materialIonstone.addTrait(ModTraits.luminescent, HEAD);
			materialIonstone.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialIonstone, new HeadMaterialStats(1650, 18.5f, 18.5f, 8));
			TinkerRegistry.addMaterialStats(materialIonstone, new HandleMaterialStats(4f, -30));
			TinkerRegistry.addMaterialStats(materialIonstone, new ExtraMaterialStats(600));
			TinkerRegistry.addMaterialStats(materialIonstone, new ArrowShaftMaterialStats(1.2f, 30));
			TinkerRegistry.addMaterialStats(materialIonstone, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialIonstone, new CoreMaterialStats(27f, 38.5f));
				TinkerRegistry.addMaterialStats(materialIonstone, new PlatesMaterialStats(4.0f, -2.2f, 3.8f));
				TinkerRegistry.addMaterialStats(materialIonstone, new TrimMaterialStats(27f));

				ArmorMaterials.addArmorTrait(materialIonstone, ModArmorTraits.etherealTangibility);
				ArmorMaterials.addArmorTrait(materialIonstone, ModTraits.stormcaller, ModArmorTraits.evasive);

				addArmorLum(materialIonstone);
			}
			
			materialVacuuite.addItem("gemVacuuite", 1, Material.VALUE_Ingot);
			materialVacuuite.addItem("blockVacuuite", 1, Material.VALUE_Block);
			materialVacuuite.setCastable(false);
			materialVacuuite.setCraftable(true);
			materialVacuuite.setRepresentativeItem("gemVacuuite");
			materialVacuuite.addTrait(ModTraits.etheralHarvest, HEAD);
			materialVacuuite.addTrait(ModTraits.nulltouched2, HEAD);
			materialVacuuite.addTrait(ModTraits.voidic, HEAD);
			materialVacuuite.addTrait(ModTraits.voidic);
			materialVacuuite.addTrait(ModTraits.nulltouched);
			materialVacuuite.addTrait(TinkerTraits.unnatural);
			materialVacuuite.addTrait(ModTraits.luminescent, HEAD);
			materialVacuuite.addTrait(ModTraits.luminescent);
			TinkerRegistry.addMaterialStats(materialVacuuite, new HeadMaterialStats(1700, 19f, 19f, 8));
			TinkerRegistry.addMaterialStats(materialVacuuite, new HandleMaterialStats(3.8f, -25));
			TinkerRegistry.addMaterialStats(materialVacuuite, new ExtraMaterialStats(620));
			TinkerRegistry.addMaterialStats(materialVacuuite, new ArrowShaftMaterialStats(1.3f, 20));
			TinkerRegistry.addMaterialStats(materialVacuuite, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialVacuuite, new CoreMaterialStats(27.4f, 38.3f));
				TinkerRegistry.addMaterialStats(materialVacuuite, new PlatesMaterialStats(4.0f, -2.0f, 3.6f));
				TinkerRegistry.addMaterialStats(materialVacuuite, new TrimMaterialStats(27.6f));

				ArmorMaterials.addArmorTrait(materialVacuuite, ModArmorTraits.etherealTangibility);
				ArmorMaterials.addArmorTrait(materialVacuuite, ModTraits.voidic);
				ArmorMaterials.addArmorTrait(materialVacuuite, ArmorTraits.dense);

				addArmorLum(materialVacuuite);
			}
			
		}
		if (CompatManager.aether_legacy) { // TODO add unique toolparts for various aether artifacts
			materialZanite.addItem("gemZanite", 1, Material.VALUE_Ingot);
			materialZanite.addItem("blockZanite", 1, Material.VALUE_Block);
			materialZanite.setCastable(false);
			materialZanite.setCraftable(true);
			materialZanite.setRepresentativeItem("gemZanite");
			materialZanite.addTrait(ModTraits.aetheric);
			materialZanite.addTrait(ModTraits.hardcore);
			materialZanite.addTrait(ModTraits.aetheric, HEAD);
			materialZanite.addTrait(ModTraits.treetap, HEAD);
			materialZanite.addTrait(TinkerTraits.jagged, HEAD);
			materialZanite.addTrait(ModTraits.hardcore, HEAD);
			TinkerRegistry.addMaterialStats(materialZanite, new HeadMaterialStats(210, 2.00f, 4.00f, 2));
			TinkerRegistry.addMaterialStats(materialZanite, new HandleMaterialStats(0.9f, 65));
			TinkerRegistry.addMaterialStats(materialZanite, new ArrowShaftMaterialStats(1f, 10));
			TinkerRegistry.addMaterialStats(materialZanite, new ExtraMaterialStats(50));
			TinkerRegistry.addMaterialStats(materialZanite, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialZanite, new CoreMaterialStats(10.2f, 12.9f));
				TinkerRegistry.addMaterialStats(materialZanite, new PlatesMaterialStats(0.9f, 4.2f, 0));
				TinkerRegistry.addMaterialStats(materialZanite, new TrimMaterialStats(2.5f));
			}

			materialSkyroot.addItem("plankWoodSkyroot", 1, Material.VALUE_Ingot);
			materialSkyroot.addItem("logWoodSkyroot", 1, Material.VALUE_Ingot * 4);
			materialSkyroot.addItem("stickWoodSkyroot", 1, Material.VALUE_Ingot / 2);
			materialSkyroot.setCastable(false);
			materialSkyroot.setCraftable(true);
			materialSkyroot.setRepresentativeItem("plankWoodSkyroot");
			materialSkyroot.addTrait(ModTraits.aetheric);
			materialSkyroot.addTrait(ModTraits.rootplicating, HEAD);
			materialSkyroot.addTrait(TinkerTraits.ecological, HEAD);
			materialSkyroot.addTrait(ModTraits.aetheric, HEAD);
			TinkerRegistry.addMaterialStats(materialSkyroot, new HeadMaterialStats(40, 2f, 2f, 0));
			TinkerRegistry.addMaterialStats(materialSkyroot, new HandleMaterialStats(1.2f, 25));
			TinkerRegistry.addMaterialStats(materialSkyroot, new ExtraMaterialStats(15));
			TinkerRegistry.addMaterialStats(materialSkyroot, new BowMaterialStats(0.6f, 1.1f, 2f));
			TinkerRegistry.addMaterialStats(materialSkyroot, new ArrowShaftMaterialStats(1.2f, 16));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialSkyroot, new CoreMaterialStats(4.5f, 3f));
				TinkerRegistry.addMaterialStats(materialSkyroot, new PlatesMaterialStats(1.2f, 1.6f, 0));
				TinkerRegistry.addMaterialStats(materialSkyroot, new TrimMaterialStats(0.75f));
			}

			materialAmberwood.setCastable(false);
			materialAmberwood.setCraftable(false);
			materialAmberwood.addTrait(ModTraits.aetheric);
			materialAmberwood.addTrait(ModTraits.rootplicating, HEAD);
			materialAmberwood.addTrait(ModTraits.treetap, HEAD);
			materialAmberwood.addTrait(TinkerTraits.established, HEAD);
			materialAmberwood.addTrait(TinkerTraits.ecological);
			materialAmberwood.addTrait(TinkerTraits.writable);
			TinkerRegistry.addMaterialStats(materialAmberwood, new HeadMaterialStats(170, 4f, 4f, 2));
			TinkerRegistry.addMaterialStats(materialAmberwood, new HandleMaterialStats(1.2f, 125));
			TinkerRegistry.addMaterialStats(materialAmberwood, new ExtraMaterialStats(75));
			TinkerRegistry.addMaterialStats(materialAmberwood, new BowMaterialStats(0.7f, 1.6f, 3f));
			TinkerRegistry.addMaterialStats(materialAmberwood, new ArrowShaftMaterialStats(1.2f, 32));
			CompositeRegistry.registerComposite(() -> materialSkyroot, () -> materialAmberwood, () -> ModFluids.liquidGoldenAmber);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialAmberwood, new CoreMaterialStats(9.2f, 12.9f));
				TinkerRegistry.addMaterialStats(materialAmberwood, new PlatesMaterialStats(1.2f, 8.1f, 0));
				TinkerRegistry.addMaterialStats(materialAmberwood, new TrimMaterialStats(3.75f));
			}

			materialArkenium.addCommonItems("Arkenium");
			materialArkenium.setFluid(ModFluids.liquidArkenium);
			materialArkenium.setCastable(true);
			materialArkenium.setCraftable(false);
			materialArkenium.addTrait(TinkerTraits.lightweight);
			materialArkenium.addTrait(TinkerTraits.holy, HEAD);
			materialArkenium.addTrait(TinkerTraits.sharp, HEAD);
			materialArkenium.addTrait(ModTraits.aetheric, HEAD);
			materialArkenium.addTrait(ModTraits.aetheric);
			materialArkenium.setRepresentativeItem("ingotArkenium");
			TinkerRegistry.addMaterialStats(materialArkenium, new HeadMaterialStats(1250, 10.0f, 5.25f, 4));
			TinkerRegistry.addMaterialStats(materialArkenium, new HandleMaterialStats(1.1f, 100));
			TinkerRegistry.addMaterialStats(materialArkenium, new ExtraMaterialStats(125));
			TinkerRegistry.addMaterialStats(materialArkenium, new ArrowShaftMaterialStats(3.5f, 25));
			TinkerRegistry.addMaterialStats(materialArkenium, new BowMaterialStats(0.5f, 2.75f, 3f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialArkenium, new CoreMaterialStats(25f, 15.4f));
				TinkerRegistry.addMaterialStats(materialArkenium, new PlatesMaterialStats(1.1f, 6.5f, 2.25f));
				TinkerRegistry.addMaterialStats(materialArkenium, new TrimMaterialStats(6.25f));
			}

			materialValkyrieMetal.addCommonItems("ValkyrieMetal");
			materialValkyrieMetal.setFluid(ModFluids.liquidValkyrieMetal);
			materialValkyrieMetal.setCastable(true);
			materialValkyrieMetal.setCraftable(false);
			materialValkyrieMetal.addTrait(ModTraits.reaching2, HEAD);
			materialValkyrieMetal.addTrait(ModTraits.treetap, HEAD);
			materialValkyrieMetal.addTrait(ModTraits.reaching);
			materialValkyrieMetal.addTrait(ModTraits.aetheric);
			materialValkyrieMetal.setRepresentativeItem("ingotValkyrieMetal");
			TinkerRegistry.addMaterialStats(materialValkyrieMetal, new HeadMaterialStats(1250, 10.0f, 5.25f, 4));
			TinkerRegistry.addMaterialStats(materialValkyrieMetal, new HandleMaterialStats(1.1f, 100));
			TinkerRegistry.addMaterialStats(materialValkyrieMetal, new ExtraMaterialStats(125));
			TinkerRegistry.addMaterialStats(materialValkyrieMetal, new ArrowShaftMaterialStats(3.5f, 25));
			TinkerRegistry.addMaterialStats(materialValkyrieMetal, new BowMaterialStats(0.5f, 2.75f, 3f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialValkyrieMetal, new CoreMaterialStats(25f, 15.4f));
				TinkerRegistry.addMaterialStats(materialValkyrieMetal, new PlatesMaterialStats(1.1f, 6.5f, 2));
				TinkerRegistry.addMaterialStats(materialValkyrieMetal, new TrimMaterialStats(6.25f));
			}

			materialHolystone.addItem("holystone", 1, Material.VALUE_Ingot);
			materialHolystone.setCastable(false);
			materialHolystone.setCraftable(true);
			materialHolystone.setRepresentativeItem("holystone");
			materialHolystone.addTrait(TinkerTraits.cheapskate, HEAD);
			materialHolystone.addTrait(ModTraits.aetheric, HEAD);
			materialHolystone.addTrait(ModTraits.blessed, HEAD);
			materialHolystone.addTrait(ModTraits.aetheric);
			materialHolystone.addTrait(TinkerTraits.cheapskate);
			TinkerRegistry.addMaterialStats(materialHolystone, whyWouldYouMakeABowOutOfThis);
			TinkerRegistry.addMaterialStats(materialHolystone, new HeadMaterialStats(130, 3f, 3f, 1));
			TinkerRegistry.addMaterialStats(materialHolystone, new HandleMaterialStats(0.25f, -50));
			TinkerRegistry.addMaterialStats(materialHolystone, new ExtraMaterialStats(30));
			TinkerRegistry.addMaterialStats(materialHolystone, new ArrowShaftMaterialStats(1f, 5));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialHolystone, new CoreMaterialStats(8.1f, 5f));
				TinkerRegistry.addMaterialStats(materialHolystone, new PlatesMaterialStats(0.25f, -3.2f, 0));
				TinkerRegistry.addMaterialStats(materialHolystone, new TrimMaterialStats(1.5f));
			}

			materialGravitite.addItem("blockGravitite", 1, Material.VALUE_Block);
			materialGravitite.addItem("gemGravitite", 1, Material.VALUE_Ingot);
			materialGravitite.setCastable(false);
			materialGravitite.setCraftable(true);
			materialGravitite.setRepresentativeItem("gemGravitite");
			materialGravitite.addTrait(TinkerTraits.insatiable);
			materialGravitite.addTrait(ModTraits.aetheric, HEAD);
			materialGravitite.addTrait(ModTraits.aetheric);
			materialGravitite.addTrait(ModTraits.antigravity, HEAD);
			materialGravitite.addTrait(ModTraits.treetap, HEAD);
			TinkerRegistry.addMaterialStats(materialGravitite, new HeadMaterialStats(950, 7.50f, 5.00f, 3));
			TinkerRegistry.addMaterialStats(materialGravitite, new HandleMaterialStats(0.9f, 90));
			TinkerRegistry.addMaterialStats(materialGravitite, new ExtraMaterialStats(90));
			TinkerRegistry.addMaterialStats(materialGravitite, whyWouldYouMakeABowOutOfThis);
			TinkerRegistry.addMaterialStats(materialGravitite, new ArrowShaftMaterialStats(1.2f, 15));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialGravitite, new CoreMaterialStats(21.8f, 14.9f));
				TinkerRegistry.addMaterialStats(materialGravitite, new PlatesMaterialStats(0.9f, 5.9f, 2));
				TinkerRegistry.addMaterialStats(materialGravitite, new TrimMaterialStats(4.5f));

				ArmorMaterials.addArmorTrait(materialGravitite, ArmorTraits.magnetic2, ArmorTraits.magnetic);
			}

			TinkerRegistry.addMaterialStats(materialSkybolt, new HeadMaterialStats(1070, 7.50f, 5.00f, 3));
			materialSkybolt.addTrait(ModTraits.boltforged);
		}
		if (CompatManager.twilightforest) { // TODO add carminite & more alloys
			materialIronwood.addCommonItems("Ironwood");
			materialIronwood.setCastable(true);
			materialIronwood.setFluid(ModFluids.liquidIronwood);
			materialIronwood.setCraftable(false);
			materialIronwood.setRepresentativeItem("ingotIronwood");
			materialIronwood.addTrait(TConstruct.twilit);
			materialIronwood.addTrait(ModTraits.warding);
			materialIronwood.addTrait(TinkerTraits.ecological);
			TinkerRegistry.addMaterialStats(materialIronwood, new HeadMaterialStats(100, 10f, 5f, 4));
			TinkerRegistry.addMaterialStats(materialIronwood, new HandleMaterialStats(1f, 25));
			TinkerRegistry.addMaterialStats(materialIronwood, new ExtraMaterialStats(25));
			TinkerRegistry.addMaterialStats(materialIronwood, new ArrowShaftMaterialStats(1f, 1));
			TinkerRegistry.addMaterialStats(materialIronwood, new BowMaterialStats(0.75f, 2.75f, 3f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialIronwood, new CoreMaterialStats(7.1f, 14.9f));
				TinkerRegistry.addMaterialStats(materialIronwood, new PlatesMaterialStats(1.0f, 1.6f, 0));
				TinkerRegistry.addMaterialStats(materialIronwood, new TrimMaterialStats(1.25f)); // TinkerMaterials
			}

			materialFerroherb.setCastable(false);
			materialFerroherb.setCraftable(false);
			materialFerroherb.addTrait(TConstruct.synergy);
			materialFerroherb.addTrait(TConstruct.twilit);
			materialFerroherb.addTrait(TinkerTraits.ecological);
			materialFerroherb.addTrait(TConstruct.twilit, HEAD);
			materialFerroherb.addTrait(TinkerTraits.ecological, HEAD);
			materialFerroherb.addTrait(TinkerTraits.sharp, HEAD);
			materialFerroherb.addTrait(TinkerTraits.splintering, HEAD);
			TinkerRegistry.addMaterialStats(materialFerroherb, new HeadMaterialStats(300, 10f, 7f, 4));
			TinkerRegistry.addMaterialStats(materialFerroherb, new HandleMaterialStats(1.125f, 100));
			TinkerRegistry.addMaterialStats(materialFerroherb, new ExtraMaterialStats(125));
			TinkerRegistry.addMaterialStats(materialFerroherb, new ArrowShaftMaterialStats(1f, 25));
			TinkerRegistry.addMaterialStats(materialFerroherb, new BowMaterialStats(0.7f, 2.75f, 4f));
			CompositeRegistry.registerComposite(() -> TConstruct.steeleaf, () -> materialFerroherb, () -> ModFluids.liquidIronwood);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialFerroherb, new CoreMaterialStats(10f, 18.8f));
				TinkerRegistry.addMaterialStats(materialFerroherb, new PlatesMaterialStats(1.125f, 6.5f, 0.8f));
				TinkerRegistry.addMaterialStats(materialFerroherb, new TrimMaterialStats(6.25f));
			}

			materialPenguinite.addCommonItems("Penguinite");
			materialPenguinite.setCastable(true);
			materialPenguinite.setFluid(ModFluids.liquidPenguinite);
			materialPenguinite.setCraftable(false);
			materialPenguinite.setRepresentativeItem("ingotPenguinite");
			materialPenguinite.addTrait(TConstruct.twilit);
			materialPenguinite.addTrait(TinkerTraits.coldblooded);
			materialPenguinite.addTrait(TinkerTraits.freezing);
			TinkerRegistry.addMaterialStats(materialPenguinite, new HeadMaterialStats(170, 10f, 7f, 4));
			TinkerRegistry.addMaterialStats(materialPenguinite, new HandleMaterialStats(1f, 112));
			TinkerRegistry.addMaterialStats(materialPenguinite, new ExtraMaterialStats(85));
			TinkerRegistry.addMaterialStats(materialPenguinite, new ArrowShaftMaterialStats(1f, 1));
			TinkerRegistry.addMaterialStats(materialPenguinite, new BowMaterialStats(0.7f, 1f, 2f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialPenguinite, new CoreMaterialStats(7.1f, 18.8f));
				TinkerRegistry.addMaterialStats(materialPenguinite, new PlatesMaterialStats(1.0f, 1.6f, 0));
				TinkerRegistry.addMaterialStats(materialPenguinite, new TrimMaterialStats(1.25f));
			}
		}

		if (CompatManager.thebetweenlands) {
			materialSyrmorite.addItem("oreSyrmorite", 1, Material.VALUE_Ore());
			materialSyrmorite.setFluid(ModFluids.liquidSyrmorite);
			materialSyrmorite.addCommonItems("Syrmorite");
			materialSyrmorite.setCastable(true);
			materialSyrmorite.setCraftable(false);
			materialSyrmorite.setRepresentativeItem("ingotSyrmorite");
			materialSyrmorite.addTrait(TinkerTraits.coldblooded);
			materialSyrmorite.addTrait(TinkerTraits.magnetic);
			TinkerRegistry.addMaterialStats(materialSyrmorite, new HeadMaterialStats(800, 5f, 4f, 2));
			TinkerRegistry.addMaterialStats(materialSyrmorite, new HandleMaterialStats(0.8f, 60));
			TinkerRegistry.addMaterialStats(materialSyrmorite, new ExtraMaterialStats(-25));
			TinkerRegistry.addMaterialStats(materialSyrmorite, new BowMaterialStats(0.7f, 1f, 3f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialSyrmorite, new CoreMaterialStats(20f, 12.9f));
				TinkerRegistry.addMaterialStats(materialSyrmorite, new PlatesMaterialStats(0.8f, 3.9f, 0));
				TinkerRegistry.addMaterialStats(materialSyrmorite, new TrimMaterialStats(-1.25f));
			}

			materialRotiron.setFluid(ModFluids.liquidRotiron);
			materialRotiron.addCommonItems("Rotiron");
			materialRotiron.setCastable(true);
			materialRotiron.setCraftable(false);
			materialRotiron.setRepresentativeItem("ingotRotiron");
			materialRotiron.addTrait(ModTraits.oxide, HEAD);
			materialRotiron.addTrait(ModTraits.rotten, HEAD);
			materialRotiron.addTrait(TinkerTraits.dense);
			materialRotiron.addTrait(TinkerTraits.magnetic);
			TinkerRegistry.addMaterialStats(materialRotiron, new HeadMaterialStats(850, 5.25f, 5f, 2));
			TinkerRegistry.addMaterialStats(materialRotiron, new HandleMaterialStats(1.125f, 45));
			TinkerRegistry.addMaterialStats(materialRotiron, new ExtraMaterialStats(-20));
			TinkerRegistry.addMaterialStats(materialRotiron, new BowMaterialStats(0.7f, 1f, 4f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialRotiron, new CoreMaterialStats(20.6f, 14.9f));
				TinkerRegistry.addMaterialStats(materialRotiron, new PlatesMaterialStats(1.125f, 2.9f, 0));
				TinkerRegistry.addMaterialStats(materialRotiron, new TrimMaterialStats(-1.0f));
			}

			materialSwampSteel.setFluid(ModFluids.liquidSwampSteel);
			materialSwampSteel.addCommonItems("Swampsteel");
			materialSwampSteel.setCastable(true);
			materialSwampSteel.setCraftable(false);
			materialSwampSteel.setRepresentativeItem("ingotSwampsteel");
			materialSwampSteel.addTrait(ModTraits.corroding, HEAD);
			materialSwampSteel.addTrait(TinkerTraits.dense, HEAD);
			materialSwampSteel.addTrait(TinkerTraits.established);
			materialSwampSteel.addTrait(TinkerTraits.heavy);
			TinkerRegistry.addMaterialStats(materialSwampSteel, new HeadMaterialStats(1200, 5.25f, 4.5f, 3));
			TinkerRegistry.addMaterialStats(materialSwampSteel, new HandleMaterialStats(0.9f, 80));
			TinkerRegistry.addMaterialStats(materialSwampSteel, new ExtraMaterialStats(-12));
			TinkerRegistry.addMaterialStats(materialSwampSteel, new BowMaterialStats(0.7f, 1f, 4f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialSwampSteel, new CoreMaterialStats(24.5f, 13.9f));
				TinkerRegistry.addMaterialStats(materialSwampSteel, new PlatesMaterialStats(0.9f, 5.2f, 0.8f));
				TinkerRegistry.addMaterialStats(materialSwampSteel, new TrimMaterialStats(-0.6f));
			}

			materialOctine.addItem("oreOctine", 1, Material.VALUE_Ore());
			materialOctine.setFluid(ModFluids.liquidOctine);
			materialOctine.addCommonItems("Octine");
			materialOctine.setCastable(true);
			materialOctine.setCraftable(false);
			materialOctine.setRepresentativeItem("ingotOctine");
			materialOctine.addTrait(TinkerTraits.superheat);
			materialOctine.addTrait(ModTraits.burning);
			TinkerRegistry.addMaterialStats(materialOctine, new HeadMaterialStats(900, 5f, 4f, 2));
			TinkerRegistry.addMaterialStats(materialOctine, new HandleMaterialStats(1.25f, -50));
			TinkerRegistry.addMaterialStats(materialOctine, new ExtraMaterialStats(-25));
			TinkerRegistry.addMaterialStats(materialOctine, new BowMaterialStats(0.7f, 1f, 3f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialOctine, new CoreMaterialStats(21.2f, 12.9f));
				TinkerRegistry.addMaterialStats(materialOctine, new PlatesMaterialStats(1.25f, -3.2f, 0));
				TinkerRegistry.addMaterialStats(materialOctine, new TrimMaterialStats(-1.25f));
			}

			materialValonite.addItem("gemValonite", 1, Material.VALUE_Ingot);
			materialValonite.addItem("blockValonite", 1, Material.VALUE_Block);
			materialValonite.addItem("nuggetValonite", 1, Material.VALUE_Nugget);
			materialValonite.setCastable(false);
			materialValonite.setCraftable(true);
			materialValonite.setRepresentativeItem("gemValonite");
			materialValonite.addTrait(TinkerTraits.dense);
			materialValonite.addTrait(ModTraits.warding);
			materialValonite.addTrait(TinkerTraits.sharp);
			TinkerRegistry.addMaterialStats(materialValonite, new HeadMaterialStats(975, 6f, 6.125f, 3));
			TinkerRegistry.addMaterialStats(materialValonite, new HandleMaterialStats(1f, -75));
			TinkerRegistry.addMaterialStats(materialValonite, new ExtraMaterialStats(-25));
			TinkerRegistry.addMaterialStats(materialValonite, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialValonite, new CoreMaterialStats(22.1f, 17.1f));
				TinkerRegistry.addMaterialStats(materialValonite, new PlatesMaterialStats(1.0f, -4.9f, 2));
				TinkerRegistry.addMaterialStats(materialValonite, new TrimMaterialStats(-1.25f));
			}

			materialEmberlight.setCastable(false);
			materialEmberlight.setCraftable(false);
			materialEmberlight.addTrait(TinkerTraits.dense);
			materialEmberlight.addTrait(TinkerTraits.sharp);
			materialEmberlight.addTrait(ModTraits.hardcore);
			materialEmberlight.addTrait(ModTraits.burning);
			materialEmberlight.addTrait(ModTraits.resilient);
			TinkerRegistry.addMaterialStats(materialEmberlight, new HeadMaterialStats(1200, 7.0f, 6.5f, 4));
			TinkerRegistry.addMaterialStats(materialEmberlight, new HandleMaterialStats(2.0f, 20));
			TinkerRegistry.addMaterialStats(materialEmberlight, new ExtraMaterialStats(12));
			CompositeRegistry.registerComposite(() -> materialValonite, () -> materialEmberlight, () -> ModFluids.liquidEmber);
			TinkerRegistry.addMaterialStats(materialEmberlight, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialEmberlight, new CoreMaterialStats(24.5f, 17.9f));
				TinkerRegistry.addMaterialStats(materialEmberlight, new PlatesMaterialStats(2.0f, 1.3f, 2.2f));
				TinkerRegistry.addMaterialStats(materialEmberlight, new TrimMaterialStats(0.6f));
			}

			materialSlimyBone.addItem("gemSlimyBone", 1, Material.VALUE_Ingot);
			materialSlimyBone.addItem("blockSlimyBone", 1, Material.VALUE_Block);
			materialSlimyBone.setCastable(false);
			materialSlimyBone.setCraftable(true);
			materialSlimyBone.setRepresentativeItem("gemSlimyBone");
			materialSlimyBone.addTrait(TinkerTraits.splintering);
			materialSlimyBone.addTrait(TinkerTraits.cheap);
			materialSlimyBone.addTrait(TinkerTraits.cheapskate);
			materialSlimyBone.addTrait(TinkerTraits.crude);
			TinkerRegistry.addMaterialStats(materialSlimyBone, new HeadMaterialStats(275, 3f, 3f, 1));
			TinkerRegistry.addMaterialStats(materialSlimyBone, new HandleMaterialStats(1.1f, 25));
			TinkerRegistry.addMaterialStats(materialSlimyBone, new ExtraMaterialStats(5));
			TinkerRegistry.addMaterialStats(materialSlimyBone, whyWouldYouMakeABowOutOfThis);
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialSlimyBone, new CoreMaterialStats(11.7f, 5f));
				TinkerRegistry.addMaterialStats(materialSlimyBone, new PlatesMaterialStats(1.1f, 1.6f, 0));
				TinkerRegistry.addMaterialStats(materialSlimyBone, new TrimMaterialStats(0.25f));
			}

			materialReedRope.addItem("ropeReed", 1, Material.VALUE_Ingot);
			materialReedRope.setCastable(false);
			materialReedRope.setCraftable(true);
			materialReedRope.setRepresentativeItem("ropeReed");
			materialReedRope.addTrait(TinkerTraits.ecological);
			materialReedRope.addTrait(TinkerTraits.aquadynamic);
			materialReedRope.addTrait(TinkerTraits.writable2);
			TinkerRegistry.addMaterialStats(materialReedRope, new BowStringMaterialStats(2f));
			TinkerRegistry.addMaterialStats(materialReedRope, new FletchingMaterialStats(0.85f, 0.35f));

			materialDragonFlyWing.addItem("itemDragonFlyWing", 1, Material.VALUE_Ingot);
			materialDragonFlyWing.setCastable(false);
			materialDragonFlyWing.setCraftable(true);
			materialDragonFlyWing.setRepresentativeItem("itemDragonFlyWing");
			materialDragonFlyWing.addTrait(TinkerTraits.lightweight);
			TinkerRegistry.addMaterialStats(materialDragonFlyWing, new FletchingMaterialStats(1f, 0.75f));

			materialAnglerTooth.addItem("itemAnglerTooth", 1, Material.VALUE_Ingot);
			materialAnglerTooth.setCastable(false);
			materialAnglerTooth.setCraftable(true);
			materialAnglerTooth.setRepresentativeItem("itemAnglerTooth");
			materialAnglerTooth.addTrait(TinkerTraits.aquadynamic);
			materialAnglerTooth.addTrait(TinkerTraits.jagged);
			TinkerRegistry.addMaterialStats(materialAnglerTooth, new HeadMaterialStats(128, 2f, 5f, 1));
			TinkerRegistry.addMaterialStats(materialAnglerTooth, whyWouldYouMakeABowOutOfThis);

			materialWeedwood.addItem("plankWoodWeedwood", 1, Material.VALUE_Ingot);
			materialWeedwood.addItem("logWoodWeedwood", 1, Material.VALUE_Ingot * 4);
			materialWeedwood.addItem("stickWoodWeedwood", 1, Material.VALUE_Ingot / 2);
			materialWeedwood.setCastable(false);
			materialWeedwood.setCraftable(true);
			materialWeedwood.setRepresentativeItem("plankWoodWeedwood");
			materialWeedwood.addTrait(TinkerTraits.ecological);
			TinkerRegistry.addMaterialStats(materialWeedwood, new HeadMaterialStats(40, 2f, 2f, 0));
			TinkerRegistry.addMaterialStats(materialWeedwood, new HandleMaterialStats(1.2f, 25));
			TinkerRegistry.addMaterialStats(materialWeedwood, new ExtraMaterialStats(15));
			TinkerRegistry.addMaterialStats(materialWeedwood, new BowMaterialStats(0.8f, 1.1f, 2f));
			TinkerRegistry.addMaterialStats(materialWeedwood, new ArrowShaftMaterialStats(1.2f, 16));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialWeedwood, new CoreMaterialStats(4.5f, 4f));
				TinkerRegistry.addMaterialStats(materialWeedwood, new PlatesMaterialStats(1.2f, 1.6f, 0));
				TinkerRegistry.addMaterialStats(materialWeedwood, new TrimMaterialStats(0.75f));
			}

			TinkerRegistry.addMaterialStats(materialShockwave, new HeadMaterialStats(1256, 6f, 6.125f, 3));
			materialShockwave.addTrait(ModTraits.shockwaving);

			TinkerRegistry.addMaterialStats(materialWormed, new HeadMaterialStats(1024, 1f, 5f, 1));
			materialWormed.addTrait(ModTraits.wormed);

			materialAncientAlloy.setFluid(ModFluids.liquidAncientAlloy);
			materialAncientAlloy.addCommonItems("AncientAlloy");
			materialAncientAlloy.setCastable(true);
			materialAncientAlloy.setCraftable(false);
			materialAncientAlloy.setRepresentativeItem("ingotAncientAlloy");
			materialAncientAlloy.addTrait(TinkerTraits.dense, HEAD);
			materialAncientAlloy.addTrait(ModTraits.inertia, HEAD);
			materialAncientAlloy.addTrait(TinkerTraits.heavy);
			TinkerRegistry.addMaterialStats(materialAncientAlloy, new HeadMaterialStats(1000, 5.5f, 5.5f, 3));
			TinkerRegistry.addMaterialStats(materialAncientAlloy, new HandleMaterialStats(1f, 125));
			TinkerRegistry.addMaterialStats(materialAncientAlloy, new ExtraMaterialStats(40));
			TinkerRegistry.addMaterialStats(materialAncientAlloy, new BowMaterialStats(0.6f, 1.25f, 4f));
			if (CompatManager.conarm) {
				TinkerRegistry.addMaterialStats(materialAncientAlloy, new CoreMaterialStats(22.4f, 15.9f));
				TinkerRegistry.addMaterialStats(materialAncientAlloy, new PlatesMaterialStats(1.0f, 8.1f, 2.5f));
				TinkerRegistry.addMaterialStats(materialAncientAlloy, new TrimMaterialStats(2.0f));
			}
		}
	}

	public static void init() {
		initMats();

		if (ConfigHandler.enableBomb) {
			ModMaterials.registerMaterial(materialGunpowder);
			ModMaterials.registerMaterial(materialIcy);
			ModMaterials.registerMaterial(materialGlowstone);
			ModMaterials.registerMaterial(materialRedstone);
			ModMaterials.registerMaterial(materialImpact);
		}

		if (CompatManager.plustic) {
			ModMaterials.registerMaterial(materialCrimson);
		}

		if (CompatManager.easterEggs) {
			ModMaterials.registerMaterial(materialTechnoblade);
		}

		if (CompatManager.loadMain) {
			ModMaterials.registerMaterial(materialZracohlium).toolforge();
			ModMaterials.registerMaterial(materialPlasma, null);
			ModMaterials.registerMaterial(materialFusionite).toolforge();
			ModMaterials.registerMaterial(materialValasium).toolforge();
			ModMaterials.registerMaterial(materialSpaceTimeDisruption, null);
			ModMaterials.registerMaterial(materialVoidSpar, "VoidSpar").toolforge();
			ModMaterials.registerMaterial(materialEnderal).toolforge();
			ModMaterials.registerMaterial(materialIrradium).toolforge();
			ModMaterials.registerMaterial(materialSolsteel).toolforge();
			ModMaterials.registerMaterial(materialGallium).toolforge();
			ModMaterials.registerMaterial(materialEnderexamite, null);
			ModMaterials.registerMaterial(materialGarstone).toolforge();
			ModMaterials.registerMaterial(materialRuneSteel).toolforge();
			ModMaterials.registerMaterial(materialGravitonium).toolforge();
			ModMaterials.registerMaterial(materialTrichromadentium).toolforge();
			ModMaterials.registerMaterial(materialTriblade, null);
			ModMaterials.registerMaterial(materialMirrored, null);
			ModMaterials.registerMaterial(materialTrailblazer, null);
			ModMaterials.registerMaterial(materialBloodstone).toolforge();
			ModMaterials.registerMaterial(materialEchostone).toolforge();
			ModMaterials.registerMaterial(materialIgniglomerate).toolforge();
			ModMaterials.registerMaterial(materialAtronium).toolforge();
			ModMaterials.registerMaterial(materialEbonite).toolforge();
			ModMaterials.registerMaterial(materialErythynite).toolforge();
			ModMaterials.registerMaterial(materialShadowglass, null);
			ModMaterials.registerMaterial(materialEtherstone).toolforge();
			ModMaterials.registerMaterial(materialMonolite).toolforge();
			ModMaterials.registerMaterial(materialEssencore, null);
			ModMaterials.registerMaterial(materialElectarite).toolforge();
			ModMaterials.registerMaterial(materialHallowsite).toolforge();
			ModMaterials.registerMaterial(materialQuakestruck, null);
			ModMaterials.registerMaterial(materialCryosplinters, null);
			ModMaterials.registerMaterial(materialBlightsteel).toolforge();
			ModMaterials.registerMaterial(materialSanguiseelium).toolforge();
			ModMaterials.registerMaterial(materialAutoloader, null);
			ModMaterials.registerMaterial(materialVengeance, null);
			ModMaterials.registerMaterial(materialDematerializer, null);
			ModMaterials.registerMaterial(materialIoximite, null);
			ModMaterials.registerMaterial(materialShotgun, null);
			ModMaterials.registerMaterial(materialPorksteel).toolforge();
			ModMaterials.registerMaterial(materialPerimidum).toolforge();
			ModMaterials.registerMaterial(materialGeodesium).toolforge();
			ModMaterials.registerMaterial(materialInertialRedirector, null);
			ModMaterials.registerMaterial(materialBrinkstone, null);
			ModMaterials.registerMaterial(materialAnthracite).toolforge();
			ModMaterials.registerMaterial(materialIonstone).toolforge();
			ModMaterials.registerMaterial(materialVacuuite).toolforge();
		}

		if (CompatManager.tic3backport) {
			ModMaterials.registerMaterial(materialNahuatl, null);
			ModMaterials.registerMaterial(materialSlimewood, null);
			ModMaterials.registerMaterial(materialSearedStone, null);
			ModMaterials.registerMaterial(materialSlimesteel).toolforge();
		}

		if (CompatManager.twilightforest) {
			ModMaterials.registerMaterial(materialIronwood).toolforge();
			ModMaterials.registerMaterial(materialPenguinite).toolforge();
			ModMaterials.registerMaterial(materialFerroherb, null);
		}
		if (CompatManager.aether_legacy) {
			ModMaterials.registerMaterial(materialArkenium).toolforge();
			ModMaterials.registerMaterial(materialSkyroot, null);
			ModMaterials.registerMaterial(materialHolystone, null);
			ModMaterials.registerMaterial(materialZanite).toolforge();
			ModMaterials.registerMaterial(materialGravitite).toolforge();
			ModMaterials.registerMaterial(materialValkyrieMetal, "ValkyrieMetal").toolforge();
			ModMaterials.registerMaterial(materialAmberwood, null);
			ModMaterials.registerMaterial(materialSkybolt, null);
		}
		if (CompatManager.thebetweenlands) {
			ModMaterials.registerMaterial(materialShockwave, null);
			ModMaterials.registerMaterial(materialSyrmorite).toolforge();
			ModMaterials.registerMaterial(materialOctine).toolforge();
			ModMaterials.registerMaterial(materialValonite).toolforge();
			ModMaterials.registerMaterial(materialReedRope, null);
			ModMaterials.registerMaterial(materialDragonFlyWing, null);
			ModMaterials.registerMaterial(materialAnglerTooth, null);
			ModMaterials.registerMaterial(materialWeedwood, null);
			ModMaterials.registerMaterial(materialSlimyBone, "SlimyBone").toolforge();
			ModMaterials.registerMaterial(materialSwampSteel).toolforge();
			ModMaterials.registerMaterial(materialRotiron).toolforge();
			ModMaterials.registerMaterial(materialEmberlight, null);
			ModMaterials.registerMaterial(materialWormed, null);
			ModMaterials.registerMaterial(materialAncientAlloy).toolforge();

			MaterialUtils.readdTinkerMaterial(TinkerMaterials.bone);

		}
		MoreTConLogger.log("Loaded a total of " + totalMaterials + " different TConstruct Materials.");
	}

	private static final IField<Map<String, List<ITrait>>> traits$Material = MirrorUtils.reflectField(Material.class, "traits");

	public static void addArmorLum(Material mat) {
		Map<String, List<ITrait>> traits = traits$Material.get(mat);

		if (traits.get(CORE) != null) {
			mat.addTrait(ModTraits.luminescent, CORE);
		}
		if (traits.get(TRIM) != null) {
			mat.addTrait(ModTraits.luminescent, TRIM);
		}
		if (traits.get(PLATES) != null) {
			mat.addTrait(ModTraits.luminescent, PLATES);
		}
	}

	public static MaterialIntegration registerMaterial(Material material) {
		String mat = material.getIdentifier().replaceFirst(ModInfo.MODID + ".", "");
		return registerMaterials(material, material.getFluid(), mat.substring(0, 1).toUpperCase() + mat.substring(1));
	}

	public static MaterialIntegration registerMaterial(Material material, String suffix) {
		return registerMaterials(material, material.getFluid(), suffix, suffix == null ? null : ("ingot" + suffix));
	}

	public static MaterialIntegration registerMaterial(Material material, String suffix, String... requiredOreDicts) {
		return registerMaterials(material, material.getFluid(), suffix, requiredOreDicts);
	}

	public static MaterialIntegration registerMaterials(Material material, Fluid fluid, String suffix, String... requiredOreDicts) {
		MaterialIntegration integration = new MTMaterialIntegration(material, fluid, suffix, requiredOreDicts.length == 0 ? new String[1] : requiredOreDicts);
		if (RegisterHelper.registerMaterial(integration, false)) {
			totalMaterials++;
		}
		return integration;
	}
}
