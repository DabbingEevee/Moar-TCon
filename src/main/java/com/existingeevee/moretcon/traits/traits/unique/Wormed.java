package com.existingeevee.moretcon.traits.traits.unique;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.other.Misc;
import com.existingeevee.moretcon.traits.traits.abst.IAdditionalTraitMethods;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import thebetweenlands.common.entity.mobs.EntityTinySludgeWormHelper;

public class Wormed extends AbstractProjectileTrait implements IAdditionalTraitMethods {

	public Wormed() {
		super(Misc.createNonConflictiveName("wormed"), 0);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {		
		if (!world.isRemote && world.getWorldTime() % 20 == 0 && entity instanceof EntityLivingBase) {
			
			EntityLivingBase living = (EntityLivingBase) entity;
			if (living.getActiveItemStack() == tool) 
				return;

			int current = getNumberRemaining(tool);
			int cap = getNumberMax(tool);
		
			if (current < cap && random.nextFloat() < 0.025) {
				addNumberRemaining(tool, 1);
			}
		}
	}
	
	@Override
	public void onPickup(EntityProjectileBase projectileBase, ItemStack ammo, EntityLivingBase entity) {
		if (getNumberRemaining(ammo) < getNumberMax(ammo) && projectileBase.getTags().contains(this.getIdentifier() + ".active")) {
			addNumberRemaining(ammo, 1);
		}
	}
	
	@Override
	public void onLaunch(EntityProjectileBase projectileBase, World world, @Nullable EntityLivingBase shooter) {
		if (getNumberRemaining(projectileBase.tinkerProjectile.getItemStack()) > 0 && (shooter == null ? true : shooter.isSneaking())) {
			projectileBase.getTags().add(this.getIdentifier() + ".active");
		}
	}
	
	@Override
	public void onAmmoConsumed(ItemStack ammo, @Nullable EntityLivingBase entity) {
		if (getNumberRemaining(ammo) > 0 && (entity == null ? true : entity.isSneaking())) {
			subtractNumberRemaining(ammo, 1);
		}
	}

	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed) {
		if (!world.isRemote && target instanceof EntityLivingBase && projectile.getTags().contains(this.getIdentifier() + ".active")) {
			EntityTinySludgeWormHelper worm = new EntityTinySludgeWormHelper(world);
			worm.setLocationAndAngles(projectile.posX, projectile.posY, projectile.posZ, projectile.rotationYaw, projectile.rotationPitch);
			worm.setAttackTarget((EntityLivingBase) target);
			if (attacker instanceof EntityPlayer) {
				worm.setOwnerId(attacker.getUniqueID());
			}
			world.spawnEntity(worm);
			projectile.setDead();
		}
	}

	public int getNumberMax(ItemStack stack) {
		return 10;
	}

	public int getNumberRemaining(ItemStack stack) {
		NBTTagCompound comp = stack.getOrCreateSubCompound(this.identifier);
		return comp.hasKey("remaining", NBT.TAG_INT) ? comp.getInteger("remaining") : 10;
	}

	public int setNumberRemaining(ItemStack stack, int amount) {
		NBTTagCompound comp = stack.getOrCreateSubCompound(this.identifier);
		comp.setInteger("remaining", amount);
		return amount;
	}

	public int addNumberRemaining(ItemStack stack, int amount) {
		return setNumberRemaining(stack, getNumberRemaining(stack) + amount);
	}

	public int subtractNumberRemaining(ItemStack stack, int amount) {
		return addNumberRemaining(stack, -amount);
	}

	@Override
	public String getTooltip(NBTTagCompound modifierTag, boolean detailed) {
		StringBuilder sb = new StringBuilder();

		ModifierNBT data = ModifierNBT.readTag(modifierTag);

		sb.append(getLocalizedName());
		if (data.level > 1) {
			sb.append(" ");
			sb.append(TinkerUtil.getRomanNumeral(data.level));
		}
		if (!detailed)
			sb.append(": -{-toreplace.moretcon.number." + this.getIdentifier() + "-}-");

		return sb.toString();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onItemTooltipEvent(ItemTooltipEvent event) {
		ItemStack tool = event.getItemStack();
		if (!this.isToolWithTrait(tool))
			return;
		for (int i = 0; i < event.getToolTip().size(); i++) {
			String str = event.getToolTip().get(i);
			String[] splitString = str.split(": ");
			if (splitString.length >= 2 && splitString[1].equals("-{-toreplace.moretcon.number." + this.getIdentifier() + "-}-")) {
				splitString[1] = getNumberRemaining(tool) + "/" + getNumberMax(tool);
				event.getToolTip().set(i, String.join(": ", splitString));
				return;
			}
		}
	}
}