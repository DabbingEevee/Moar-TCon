package com.existingeevee.moretcon.tools.tooltypes;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.existingeevee.moretcon.compat.betweenlands.IBetweenTinkerTool;
import com.existingeevee.moretcon.inits.ModTools;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.client.crosshair.Crosshairs;
import slimeknights.tconstruct.library.client.crosshair.ICrosshair;
import slimeknights.tconstruct.library.client.crosshair.ICustomCrosshairUser;
import slimeknights.tconstruct.library.events.ProjectileEvent;
import slimeknights.tconstruct.library.events.TinkerToolEvent;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.BowStringMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
import slimeknights.tconstruct.library.tools.ranged.BowCore;
import slimeknights.tconstruct.library.tools.ranged.IAmmo;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.ranged.TinkerRangedWeapons;
import thebetweenlands.api.item.CorrosionHelper;
import thebetweenlands.api.item.IAnimatorRepairable;
import thebetweenlands.api.item.ICorrodible;

public class BetweenBow extends BowCore implements ICorrodible, IAnimatorRepairable, IBetweenTinkerTool, ICustomCrosshairUser {

	public static final float DURABILITY_MODIFIER = 1.1f;

	public BetweenBow() {
		super(PartMaterialType.bow(ModTools.betweenBowLimb), PartMaterialType.bow(ModTools.betweenBowLimb),
				PartMaterialType.bowstring(TinkerTools.bowString));
		this.setUnlocalizedName(MiscUtils.createNonConflictiveName("blbow"));
		this.addPropertyOverride(PROPERTY_PULL_PROGRESS, pullProgressPropertyGetter);
		this.addPropertyOverride(PROPERTY_IS_PULLING, isPullingPropertyGetter);
		TinkerRegistry.registerToolCrafting(this);
		CorrosionHelper.addCorrosionPropertyOverrides(this);

		addCategory(Category.WEAPON);
	}

	@Override
	public int[] getRepairParts() {
		return new int[] { 0, 1 };
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if (this.isInCreativeTab(tab)) {
			addDefaultSubItems(subItems, null, null, TinkerMaterials.string);
		}
	}

	/* Tic Tool Stuff */

	@Override
	public float baseProjectileDamage() {
		return 0f;
	}

	@Override
	public float damagePotential() {
		return 0.7f;
	}

	@Override
	public double attackSpeed() {
		return 1.5;
	}

	@Override
	protected float baseInaccuracy() {
		return 1f;
	}

	@Override
	public float projectileDamageModifier() {
		return 0.8f;
	}

	@Override
	public int getDrawTime() {
		return 12;
	}

	@Override
	protected List<Item> getAmmoItems() {
		return TinkerRangedWeapons.getDiscoveredArrows();
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		// has to be done in onUpdate because onTickUsing is too early and gets
		// overwritten. bleh.
		// shortbows are more mobile than other bows
		preventSlowDown(entityIn, 0.5f);

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		CorrosionHelper.updateCorrosion(stack, worldIn, entityIn, itemSlot, isSelected);

	}

	/* Data Stuff */

	@Override
	public ProjectileLauncherNBT buildTagData(List<Material> materials) {
		ProjectileLauncherNBT data = new ProjectileLauncherNBT();
		HeadMaterialStats head1 = materials.get(0).getStatsOrUnknown(MaterialTypes.HEAD);
		HeadMaterialStats head2 = materials.get(1).getStatsOrUnknown(MaterialTypes.HEAD);
		BowMaterialStats limb1 = materials.get(0).getStatsOrUnknown(MaterialTypes.BOW);
		BowMaterialStats limb2 = materials.get(1).getStatsOrUnknown(MaterialTypes.BOW);
		BowStringMaterialStats bowstring = materials.get(2).getStatsOrUnknown(MaterialTypes.BOWSTRING);

		data.head(head1, head2);
		data.limb(limb1, limb2);
		data.bowstring(bowstring);

		return data;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ICrosshair getCrosshair(ItemStack itemStack, EntityPlayer player) {
		return Crosshairs.SQUARE;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getCrosshairState(ItemStack itemStack, EntityPlayer player) {
		return getDrawbackProgress(itemStack, player);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		boolean shift = Util.isShiftKeyDown();
		boolean ctrl = Util.isCtrlKeyDown();
		if (!shift && !ctrl) {
			CorrosionHelper.addCorrosionTooltips(stack, tooltip, flagIn.isAdvanced());
			tooltip.add("");
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public int getMinRepairFuelCost(ItemStack stack) {
		return Math.round(stack.getMaxDamage() / 120) + 1;
	}

	@Override
	public int getFullRepairFuelCost(ItemStack stack) {
		return Math.round(stack.getMaxDamage() / 75) + 1;
	}

	@Override
	public int getFullRepairLifeCost(ItemStack arg0) {
		return Math.round(arg0.getMaxDamage() / 75) + 1;
	}

	@Override
	public int getMinRepairLifeCost(ItemStack arg0) {
		return Math.round(arg0.getMaxDamage() / 60) + 1;
	}

	@Override
	public void shootProjectile(@Nonnull ItemStack ammoIn, @Nonnull ItemStack bow, World worldIn, EntityPlayer player, int useTime) {
		float progress = getDrawbackProgress(bow, useTime);
		float power = ItemBow.getArrowVelocity((int) (progress * 20f)) * progress * baseProjectileSpeed();
		power *= ProjectileLauncherNBT.from(bow).range;
		power *= Math.pow(CorrosionHelper.getModifier(bow), 2.117904);

		if (!worldIn.isRemote) {
			TinkerToolEvent.OnBowShoot event = TinkerToolEvent.OnBowShoot.fireEvent(bow, ammoIn, player, useTime, baseInaccuracy());

			// copied because consumeAmmo can delete vanilla stacks
			ItemStack ammoStackToShoot = ammoIn.copy();

			for (int i = 0; i < event.projectileCount; i++) {
				boolean usedAmmo = false;
				if (i == 0 || event.consumeAmmoPerProjectile) {
					usedAmmo = consumeAmmo(ammoIn, player);
				}
				float inaccuracy = event.getBaseInaccuracy();
				if (i > 0) {
					inaccuracy += event.bonusInaccuracy;
				}
				inaccuracy /= Math.pow(CorrosionHelper.getModifier(bow), 2);
				EntityArrow projectile = getProjectileEntity(ammoStackToShoot, bow, worldIn, player, power, inaccuracy, progress * progress, usedAmmo);

				if (projectile != null && ProjectileEvent.OnLaunch.fireEvent(projectile, bow, player)) {
					if (progress >= 1f) {
						projectile.setIsCritical(true);
					}
					if (!player.capabilities.isCreativeMode) {
						ToolHelper.damageTool(bow, 1, player);
					}
					worldIn.spawnEntity(projectile);
				}
			}
		}

		playShootSound(power, worldIn, player);
	}

	public EntityArrow getProjectileEntity(ItemStack ammo, ItemStack bow, World world, EntityPlayer player, float power, float inaccuracy, float progress, boolean usedAmmo) {
		if (ammo.getItem() instanceof IAmmo) {
			return ((IAmmo) ammo.getItem()).getProjectile(ammo, bow, world, player, power, inaccuracy, progress, usedAmmo);
		} else if (ammo.getItem() instanceof ItemArrow) {
			EntityArrow projectile = ((ItemArrow) ammo.getItem()).createArrow(world, ammo, player);
			projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, power, inaccuracy);
			if (player.capabilities.isCreativeMode) {
				projectile.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
			} else if (!usedAmmo) {
				projectile.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
			}
			return projectile;
		}
		// shizzle-foo, this fizzles too!
		return null;
	}

}
