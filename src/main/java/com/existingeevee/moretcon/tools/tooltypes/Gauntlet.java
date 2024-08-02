package com.existingeevee.moretcon.tools.tooltypes;

import java.util.List;

import com.existingeevee.moretcon.compat.ConarmSupport;
import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.inits.ModTools;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.accessories.AccessoryType;
import com.gildedgames.the_aether.api.accessories.AetherAccessory;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.api.player.util.IAccessoryInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.TinkerToolCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTools;

public class Gauntlet extends TinkerToolCore {

	public Gauntlet() {
		super(
				PartMaterialType.head(TinkerTools.largePlate),
				PartMaterialType.handle(ModTools.smallPlate),
				PartMaterialType.extra(ModTools.smallPlate));
		this.setUnlocalizedName(MiscUtils.createNonConflictiveName("gauntlet"));
		TinkerRegistry.registerToolCrafting(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void registerAetherAccessories(RegistryEvent.Register<AetherAccessory> e) {
		e.getRegistry().register(new AetherAccessory(this, AccessoryType.GLOVE));
	}

	@Override
	protected ToolNBT buildTagData(List<Material> materials) {
		ToolNBT nbt = new ToolNBT();
		nbt.head(materials.get(0).getStatsOrUnknown(MaterialTypes.HEAD));
		nbt.extra(materials.get(2).getStatsOrUnknown(MaterialTypes.EXTRA));
		nbt.handle(materials.get(1).getStatsOrUnknown(MaterialTypes.HANDLE));
		nbt.attack = 0;
		nbt.harvestLevel = 0;
		nbt.durability *= 1.5f;
		return nbt;
	}

	@Override
	public float damagePotential() {
		return 0;
	}

	@Override
	public double attackSpeed() {
		return 3;
	}

	static float lastDMG = -1;

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLivingAttackEvent(LivingKnockBackEvent e) {
		int count = 0;
		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			if (ste.getClassName().equals(this.getClass().getName())) {
				count++;
			}
		}

		if (count > 1) {
			return;
		}

		if (e.getAttacker() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e.getAttacker();

			IPlayerAether aetherPlayer = player.getCapability(AetherAPI.AETHER_PLAYER, null);

			IAccessoryInventory accessories = aetherPlayer.getAccessoryInventory();
			for (int i = 0; i < accessories.getSizeInventory(); i++) {
				ItemStack itemstack = accessories.getStackInSlot(i);
				if (itemstack.getItem() instanceof Gauntlet && !ToolHelper.isBroken(itemstack)) {
					float knockback = e.getOriginalStrength();
					for (ITrait t : ToolHelper.getTraits(itemstack)) {
						knockback = t.knockBack(itemstack, player, e.getEntityLiving(), lastDMG, e.getOriginalStrength(), knockback, itemRand.nextBoolean());
					}
				}
			}

		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onLivingAttackEvent(LivingHurtEvent e) {
		int count = 0;
		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			if (ste.getClassName().equals(this.getClass().getName())) {
				count++;
			}
		}

		if (count > 1) {
			return;
		}

		DamageSource source = e.getSource();
		if (source.getImmediateSource() instanceof EntityPlayer && source.getDamageType().equals("player")) {
			EntityPlayer player = (EntityPlayer) source.getImmediateSource();

			IPlayerAether aetherPlayer = player.getCapability(AetherAPI.AETHER_PLAYER, null);

			IAccessoryInventory accessories = aetherPlayer.getAccessoryInventory();
			lastDMG = e.getAmount();
			for (int i = 0; i < accessories.getSizeInventory(); i++) {
				ItemStack itemstack = accessories.getStackInSlot(i);
				if (itemstack.getItem() instanceof Gauntlet && !ToolHelper.isBroken(itemstack)) {
					boolean crit = Math.random() < 0.125;
					for (ITrait t : ToolHelper.getTraits(itemstack)) {
						lastDMG = t.damage(itemstack, player, e.getEntityLiving(), e.getAmount(), lastDMG, crit);
					}
					for (ITrait t : ToolHelper.getTraits(itemstack)) {
						t.onHit(itemstack, player, e.getEntityLiving(), lastDMG, crit);
						t.afterHit(itemstack, player, e.getEntityLiving(), lastDMG, crit, true);
					}
					if (Math.random() < 0.75 && !player.capabilities.isCreativeMode) {
						ToolHelper.damageTool(itemstack, 1, player);
					}
				}
			}
			e.setAmount(lastDMG);
		}
	}

	@Override
	public void addMaterialTraits(NBTTagCompound root, List<Material> materials) {
		int size = requiredComponents.length;

		if (materials.size() < size) {
			size = materials.size();
		}

		for (int i = 0; i < size; i++) {
			PartMaterialType required = requiredComponents[i];
			Material material = materials.get(i);
			for (ITrait trait : required.getApplicableTraitsForMaterial(material)) {
				ToolBuilder.addTrait(root, trait, material.materialTextColor);
			}
			if (ConfigHandler.shouldAllowConstructsArmory) {

				for (ITrait trait : ConarmSupport.getBasicArmorTraits(true, material)) {
					ToolBuilder.addTrait(root, trait, material.materialTextColor);
				}
			}
		}
	}
	/*
	 * @Override public void onWornTick(ItemStack itemstack, EntityLivingBase
	 * player) { if (player instanceof EntityPlayer) {
	 *
	 * List<Material> mats = Misc.getMaterials(itemstack);
	 *
	 * for (ITrait t : ToolHelper.getTraits(itemstack)) { t.onArmorTick(itemstack,
	 * player.getEntityWorld(), (EntityPlayer) player); t.onUpdate(itemstack,
	 * player.getEntityWorld(), player, -1,
	 * mats.get(0).getAllTraits().stream().anyMatch(i ->
	 * i.getIdentifier().equals(t.getIdentifier()))); } } }
	 */

}
