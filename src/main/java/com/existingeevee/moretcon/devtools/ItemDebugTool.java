package com.existingeevee.moretcon.devtools;

import java.util.List;

import com.existingeevee.moretcon.item.ItemBase;
import com.existingeevee.moretcon.other.BiValue;
import com.existingeevee.moretcon.other.ModTabs;
import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemDebugTool extends ItemBase {

	public ItemDebugTool() {
		super("debugtool", 0xceedff);
		this.setMaxStackSize(1);
		this.setTab(ModTabs.moarTConMisc);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (DevEnvHandler.inDevMode() && debugFunction(worldIn, playerIn)) {
			playerIn.getCooldownTracker().setCooldown(this, 5);
			return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}

		if (worldIn.isRemote) {
			if (playerIn.isSneaking()) {
				List<Entity> entities = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, playerIn.getEntityBoundingBox().expand(5.0D, 5.0D, 5.0D).expand(-5, -5, -5));
				BiValue<Entity, Double> dval = new BiValue<>(null, Double.MAX_VALUE);
				for (Entity e : entities) {
					double distance = e.getPositionVector().distanceTo(playerIn.getPositionVector());
					if (distance < dval.getB()) {
						dval = new BiValue<>(e, distance);
					}
				}
				if (dval.getA() != null) {
					playerIn.sendMessage(new TextComponentString(dval.getA().serializeNBT().toString()));
				}
			} else {
				playerIn.sendMessage(new TextComponentString(playerIn.getHeldItem(handIn.equals(EnumHand.MAIN_HAND) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND).serializeNBT().toString()));
			}
		}

		playerIn.getCooldownTracker().setCooldown(this, 5);
		return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	protected boolean debugFunction(World worldIn, EntityPlayer playerIn) { // this is used by me to test stuff.
		if (!worldIn.isRemote) {
			try {
				//playerIn.sendStatusMessage(new TextComponentString("" + Integer.toHexString(System.identityHashCode(worldIn))), false);
				//playerIn.attackEntityFrom(DamageSource.GENERIC, 10); 
				//System.out.println();   
				
				//System.out.println(TinkerRegistry.getModifier("moretcon.extratrait2_armor"));
				
				//((ItemShakeRender) ModItems.matterDeconstructionGel);
				
				MinecraftForge.EVENT_BUS.register(ModTraits.depair);
				
				//ObfuscationReflectionHelper.setPrivateValue(BookData.class, ArmoryBook.INSTANCE, false, "initialized");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
