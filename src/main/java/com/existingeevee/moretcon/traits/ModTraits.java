package com.existingeevee.moretcon.traits;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.existingeevee.moretcon.MoreTCon;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.RegisterHelper;
import com.existingeevee.moretcon.traits.modifiers.Betweenified;
import com.existingeevee.moretcon.traits.modifiers.BlueGem;
import com.existingeevee.moretcon.traits.modifiers.Crushing;
import com.existingeevee.moretcon.traits.modifiers.Debug;
import com.existingeevee.moretcon.traits.modifiers.GreenGem;
import com.existingeevee.moretcon.traits.modifiers.MatterReconstructionGel;
import com.existingeevee.moretcon.traits.modifiers.ModExtraTrait2;
import com.existingeevee.moretcon.traits.modifiers.RedGem;
import com.existingeevee.moretcon.traits.modifiers.Shocking;
import com.existingeevee.moretcon.traits.modifiers.Tarred;
import com.existingeevee.moretcon.traits.modifiers.Valonite;
import com.existingeevee.moretcon.traits.modifiers.internal.ModExtraTraitDisplay2;
import com.existingeevee.moretcon.traits.traits.Aetheric;
import com.existingeevee.moretcon.traits.traits.Afterimage;
import com.existingeevee.moretcon.traits.traits.AntiGravity;
import com.existingeevee.moretcon.traits.traits.AttributeTrait;
import com.existingeevee.moretcon.traits.traits.BottomsEnd;
import com.existingeevee.moretcon.traits.traits.Burning;
import com.existingeevee.moretcon.traits.traits.ColdFire;
import com.existingeevee.moretcon.traits.traits.Corroding;
import com.existingeevee.moretcon.traits.traits.Darkened;
import com.existingeevee.moretcon.traits.traits.DummyTrait;
import com.existingeevee.moretcon.traits.traits.Hardcore;
import com.existingeevee.moretcon.traits.traits.HelpfulRadiation;
import com.existingeevee.moretcon.traits.traits.HyperGravity;
import com.existingeevee.moretcon.traits.traits.Hyperheat;
import com.existingeevee.moretcon.traits.traits.KineticBattery;
import com.existingeevee.moretcon.traits.traits.Leeching;
import com.existingeevee.moretcon.traits.traits.Liquid;
import com.existingeevee.moretcon.traits.traits.Luminescent;
import com.existingeevee.moretcon.traits.traits.Magical;
import com.existingeevee.moretcon.traits.traits.MagicallyReactive;
import com.existingeevee.moretcon.traits.traits.Overdrive;
import com.existingeevee.moretcon.traits.traits.Overgrowth;
import com.existingeevee.moretcon.traits.traits.Overslime;
import com.existingeevee.moretcon.traits.traits.Oxide;
import com.existingeevee.moretcon.traits.traits.Penetrant;
import com.existingeevee.moretcon.traits.traits.Pulsating;
import com.existingeevee.moretcon.traits.traits.Reaching;
import com.existingeevee.moretcon.traits.traits.Resilient;
import com.existingeevee.moretcon.traits.traits.Rootplicating;
import com.existingeevee.moretcon.traits.traits.Rotten;
import com.existingeevee.moretcon.traits.traits.SkysBlessing;
import com.existingeevee.moretcon.traits.traits.Slicing;
import com.existingeevee.moretcon.traits.traits.Treetap;
import com.existingeevee.moretcon.traits.traits.Tricromatic;
import com.existingeevee.moretcon.traits.traits.Voidic;
import com.existingeevee.moretcon.traits.traits.unique.AerialFlame;
import com.existingeevee.moretcon.traits.traits.unique.BloodGodsBlessing;
import com.existingeevee.moretcon.traits.traits.unique.BloodyArc;
import com.existingeevee.moretcon.traits.traits.unique.EssentialObliteration;
import com.existingeevee.moretcon.traits.traits.unique.Mirroring;
import com.existingeevee.moretcon.traits.traits.unique.Plasmatic;
import com.existingeevee.moretcon.traits.traits.unique.Shockwaving;
import com.existingeevee.moretcon.traits.traits.unique.TripleShot;
import com.existingeevee.moretcon.traits.traits.unique.Wormed;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.ITrait;

public class ModTraits {
	public static KineticBattery kineticBattery = new KineticBattery();
	public static Hyperheat hyperheat = new Hyperheat();
	public static ColdFire coldFire = new ColdFire();
	public static Tricromatic trichromic = new Tricromatic();
	public static BloodGodsBlessing bloodGodsBlessing = new BloodGodsBlessing();
	public static HelpfulRadiation radioactive = new HelpfulRadiation();
	public static Voidic voidic = new Voidic();
	public static Pulsating pulsation = new Pulsating();
	public static Liquid liquid = new Liquid();
	public static Burning burning = new Burning();
	public static Resilient resilient = new Resilient();
	public static Magical magical = new Magical();
	public static Reaching reaching;
	public static Aetheric aetheric = new Aetheric();
	public static Rootplicating rootplicating = new Rootplicating();
	public static Plasmatic plasmatic = new Plasmatic();
	public static SkysBlessing blessed = new SkysBlessing();
	public static Treetap treetap = new Treetap();
	public static Hardcore hardcore = new Hardcore();
	public static HyperGravity hypergravity = new HyperGravity();
	public static TripleShot tripleshot = new TripleShot();
	public static Mirroring mirroring = new Mirroring();
	public static AerialFlame aerialFlames = new AerialFlame();
	public static BloodyArc bloodyArc = new BloodyArc();
	public static BottomsEnd bottomsEnd = new BottomsEnd();
	public static Slicing slicing = new Slicing();
	public static Leeching leeching = new Leeching();
	public static Afterimage afterimage = new Afterimage();
	public static MagicallyReactive magicallyReactive = new MagicallyReactive();
	public static Penetrant penetrant = new Penetrant();
	public static Darkened darkened = new Darkened();
	public static DummyTrait etheralHarvest = new DummyTrait("etheralharvest", 0);
	public static Luminescent luminescent = new Luminescent("luminescent", 0);
	public static AttributeTrait weightless = new AttributeTrait("weightless", 0, new AttributeModifier(UUID.fromString("aed073df-79af-4de9-b62c-44b5fcc4df1d"), "weightless", 1.00, 2), SharedMonsterAttributes.ATTACK_SPEED);
	public static AttributeTrait shielding = new AttributeTrait("shielding", 0, new AttributeModifier(UUID.fromString("aed073df-79af-4de9-b62c-44b5fcc44fef"), "shielding", 4, 0), SharedMonsterAttributes.ARMOR).setWorksInOffhand(true);
	public static Overdrive overdrive = new Overdrive();
	public static Overslime overslime = new Overslime();
	public static DummyTrait overcast = new DummyTrait("overcast", 0);
	public static Overgrowth overgrowth = new Overgrowth(1);
	public static Overgrowth overgrowth2 = new Overgrowth(2);

	public static EssentialObliteration essentialObliteration = new EssentialObliteration();

	public static MatterReconstructionGel repair;

	public static AntiGravity antigravity;

	public static RedGem modRedGem;
	public static GreenGem modGreenGem;
	public static BlueGem modBlueGem;
	public static Shocking modShocking;

	public static Corroding corroding;
	public static Crushing crushing;
	public static Betweenified betweenified;
	public static Valonite modValonite;
	public static Tarred modTarred;
	public static Debug modDebug;
	public static Shockwaving shockwaving;
	public static Rotten rotten;
	public static Oxide oxide;
	public static Wormed wormed;

	static {
		if (CompatManager.thebetweenlands) {
			shockwaving = new Shockwaving();
			rotten = new Rotten();
			corroding = new Corroding();
			betweenified = new Betweenified();
			oxide = new Oxide();
			wormed = new Wormed();
		}
		if (CompatManager.aether_legacy) {
			antigravity = new AntiGravity();
			reaching = new Reaching();
		}
	}

	private static void registerModifier(IModifier... mod) {
		for (IModifier i : mod) {
			RegisterHelper.registerModifier(i);
		}
	}

	public static void init() {
		if (MoreTCon.proxy.isClient()) {
			registerModifier(new ModExtraTraitDisplay2());
		}
		
		if (CompatManager.loadMain) {
			modDebug = new Debug();
			repair = new MatterReconstructionGel();
			crushing = new Crushing();
			registerModifier(
					crushing
					);
		}
		if (CompatManager.thebetweenlands) {
			modRedGem = new RedGem();
			modGreenGem = new GreenGem();
			modBlueGem = new BlueGem();
			modShocking = new Shocking();
			modValonite = new Valonite();
			modTarred = new Tarred();
			registerModifier(
					modRedGem,
					modGreenGem,
					modBlueGem,
					modValonite,
					modTarred,
					modShocking);

		}
	}

	public static void postInit() {
		registerExtraTraitModifiers();
	}

	
	private static Map<String, ModExtraTrait2> extraTraitLookup = new HashMap<>();
	public static List<Modifier> extraTraitMods;

	private static void registerExtraTraitModifiers() {
		TinkerRegistry.getAllMaterials().forEach(ModTraits::registerExtraTraitModifiers);
		extraTraitMods = Lists.newArrayList(extraTraitLookup.values());
	}

	private static void registerExtraTraitModifiers(Material material) {
		TinkerRegistry.getTools().forEach(tool -> registerExtraTraitModifiers(material, tool));
	}

	private static void registerExtraTraitModifiers(Material material, ToolCore tool) {
		tool.getRequiredComponents().forEach(pmt -> registerExtraTraitModifiers(material, tool, pmt));
	}

	private static void registerExtraTraitModifiers(Material material, ToolCore tool, PartMaterialType partMaterialType) {
		partMaterialType.getPossibleParts().forEach(part -> registerExtraTraitModifiers(material, tool, partMaterialType, part));
	}

	@SuppressWarnings("unchecked")
	private static <T extends Item & IToolPart> void registerExtraTraitModifiers(Material material, ToolCore tool, PartMaterialType partMaterialType, IToolPart toolPart) {
		if (toolPart instanceof Item) {
			Collection<ITrait> traits = partMaterialType.getApplicableTraitsForMaterial(material);
			if (!traits.isEmpty()) {
				// we turn it into a set to remove duplicates, reducing the total amount of modifiers created by roughly 25%!
				final Collection<ITrait> traits2 = ImmutableSet.copyOf(traits);
				String identifier = ModExtraTrait2.generateIdentifier(material, traits2);
				ModExtraTrait2 mod = extraTraitLookup.computeIfAbsent(identifier, id -> new ModExtraTrait2(material, traits2, identifier));
				mod.addCombination(tool, (T) toolPart);
			}
		}
	}
}
