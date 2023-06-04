package com.existingeevee.moretcon.traits;

import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.RegisterHelper;
import com.existingeevee.moretcon.traits.modifiers.Betweenified;
import com.existingeevee.moretcon.traits.modifiers.BlueGem;
import com.existingeevee.moretcon.traits.modifiers.Crushing;
import com.existingeevee.moretcon.traits.modifiers.Debug;
import com.existingeevee.moretcon.traits.modifiers.GreenGem;
import com.existingeevee.moretcon.traits.modifiers.Impactor;
import com.existingeevee.moretcon.traits.modifiers.MatterReconstructionGel;
import com.existingeevee.moretcon.traits.modifiers.RedGem;
import com.existingeevee.moretcon.traits.modifiers.Shocking;
import com.existingeevee.moretcon.traits.modifiers.Tarred;
import com.existingeevee.moretcon.traits.modifiers.Valonite;
import com.existingeevee.moretcon.traits.traits.Aetheric;
import com.existingeevee.moretcon.traits.traits.Afterimage;
import com.existingeevee.moretcon.traits.traits.AntiGravity;
import com.existingeevee.moretcon.traits.traits.BottomsEnd;
import com.existingeevee.moretcon.traits.traits.Burning;
import com.existingeevee.moretcon.traits.traits.ColdFire;
import com.existingeevee.moretcon.traits.traits.Corroding;
import com.existingeevee.moretcon.traits.traits.Darkened;
import com.existingeevee.moretcon.traits.traits.Hardcore;
import com.existingeevee.moretcon.traits.traits.HelpfulRadiation;
import com.existingeevee.moretcon.traits.traits.HyperGravity;
import com.existingeevee.moretcon.traits.traits.Hyperheat;
import com.existingeevee.moretcon.traits.traits.Leeching;
import com.existingeevee.moretcon.traits.traits.Liquid;
import com.existingeevee.moretcon.traits.traits.Magical;
import com.existingeevee.moretcon.traits.traits.MagicallyReactive;
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
import com.existingeevee.moretcon.traits.unique.AerialFlame;
import com.existingeevee.moretcon.traits.unique.BloodGodsBlessing;
import com.existingeevee.moretcon.traits.unique.BloodyArc;
import com.existingeevee.moretcon.traits.unique.Mirroring;
import com.existingeevee.moretcon.traits.unique.Plasmatic;
import com.existingeevee.moretcon.traits.unique.Shockwaving;
import com.existingeevee.moretcon.traits.unique.TripleShot;

import slimeknights.tconstruct.library.modifiers.IModifier;

public class ModTraits {
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
	
	public static MatterReconstructionGel repair;

	public static AntiGravity antigravity;

	//public static Annihilating annihilating = new Annihilating();
	public static Impactor modImpactor;
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
	
	static {
		if (CompatManager.thebetweenlands) {
			shockwaving = new Shockwaving();
			rotten = new Rotten();
			corroding = new Corroding();
			betweenified = new Betweenified();
		}
		if (CompatManager.aether_legacy) {
			antigravity = new AntiGravity();
			reaching = new Reaching();
		}
	}
//	public static Gleaming gleaming_1 = new Gleaming(1);
//  public static Gleaming gleaming_2 = new Gleaming(2);
//	public static Gleaming gleaming_3 = new Gleaming(3);
//	public static Gleaming gleaming_4 = new Gleaming(4);
//	public static Gleaming gleaming_5 = new Gleaming(5);
//	public static Gleaming gleaming_6 = new Gleaming(6);


	private static void registerModifier(IModifier... mod) {
		for (IModifier i : mod) {
			RegisterHelper.registerModifier(i);
		}
	}

	public static void init() {
		if (CompatManager.loadMain) {
			modImpactor = new Impactor();
			modDebug = new Debug();
			repair = new MatterReconstructionGel();
			crushing = new Crushing();
			registerModifier(
					modImpactor,
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
					modShocking,
					modValonite,
					modTarred
					);

		}
	}
}
