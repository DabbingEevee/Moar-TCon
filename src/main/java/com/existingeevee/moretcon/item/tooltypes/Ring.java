package com.existingeevee.moretcon.item.tooltypes;

import java.util.List;

import com.existingeevee.moretcon.compat.ConarmSupport;
import com.existingeevee.moretcon.config.ConfigHandler;
import com.existingeevee.moretcon.inits.ModTools;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

public class Ring extends TinkerToolCore implements IBauble {

	private static final PartMaterialType HEAD = PartMaterialType.head(TinkerTools.shard);
	private static final PartMaterialType HANDLE = PartMaterialType.handle(ModTools.smallPlate);

	public Ring() {
		super(HEAD, HANDLE);
		TinkerRegistry.registerToolCrafting(this);
		this.setUnlocalizedName(MiscUtils.createNonConflictiveName("ring"));
	}

	@Override
	protected ToolNBT buildTagData(List<Material> materials) {
		ToolNBT nbt = new ToolNBT();
		nbt.head(materials.get(0).getStatsOrUnknown(MaterialTypes.HEAD));
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

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (player instanceof EntityPlayer) {
			if (player.getEntityWorld().getWorldTime() % (15 * 20) == 0 && !((EntityPlayer) player).capabilities.isCreativeMode) {
				ToolHelper.damageTool(itemstack, 1, player);
			}

			if (!shouldTick(player, itemstack) || ToolHelper.isBroken(itemstack)) {
				return;
			}
			for (ITrait t : ToolHelper.getTraits(itemstack)) {
				t.onArmorTick(itemstack, player.getEntityWorld(), (EntityPlayer) player);
				t.onUpdate(itemstack, player.getEntityWorld(), player, -1, true);
				//ConarmSupport.tickConarm((EntityPlayer) player, t);
			}
		}
	}

	private boolean shouldTick(EntityLivingBase player, ItemStack stack) {
		if (player instanceof EntityPlayer) {
			IBaublesItemHandler handler = BaublesApi.getBaublesHandler((EntityPlayer) player);
			int count = 0;
			boolean found = false;
			for (int i = 0; i < handler.getSlots(); i++) {
				if (handler.getStackInSlot(i).getItem() instanceof Ring) {
					if (handler.getStackInSlot(i) == stack)
						found = true;
					count++;
				}
			}
			if (!found) //make sure its actually in the bauble inventory
				return false;
			
			return player.getEntityWorld().getWorldTime() % (count * count) == 0;
		}
		return false;
	}

	@Override
	public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
		return true;
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

				boolean basic = !required.equals(HEAD);

				for (ITrait trait : ConarmSupport.getBasicArmorTraits(basic, material)) {
					ToolBuilder.addTrait(root, trait, material.materialTextColor);
				}
			}
		}
	}
}
