package com.existingeevee.moretcon.item;

import com.existingeevee.moretcon.inits.ModPotions;
import com.existingeevee.moretcon.other.ModTabs;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.tools.ToolCore;

public class ItemPerimimoss extends ItemFood {

	public ItemPerimimoss() {
		super(1, 0.7f, false);
		this.setAlwaysEdible();
		this.setUnlocalizedName(MiscUtils.createNonConflictiveName("perimimoss"));
		this.setCreativeTab(ModTabs.moarTConWorld);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if (!worldIn.isRemote) {
			player.addPotionEffect(new PotionEffect(ModPotions.mossy, 20 * 90, 0));
		}
	}	

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 12;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (hasMosstacular(playerIn))
			return super.onItemRightClick(worldIn, playerIn, handIn);
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		if (Minecraft.getMinecraft().player == null || !hasMosstacular(Minecraft.getMinecraft().player))
			return super.hasEffect(stack);
		
		for (int i = 0; i < Minecraft.getMinecraft().player.inventory.getSizeInventory(); i++) {
			ItemStack inv = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
			if (inv == stack)
				return true;
		}

		return false;
	}

	public static boolean hasMosstacular(EntityPlayer playerIn) {
		if (playerIn == null)
			return false;

		for (int i = 0; i < InventoryPlayer.getHotbarSize(); i++) {
			ItemStack stack = playerIn.inventory.mainInventory.get(i);
			if (stack.getItem() instanceof ToolCore && ModTraits.mosstacular.isToolWithTrait(stack))
				return true;
		}

		ItemStack stack = playerIn.getHeldItemOffhand();
		return stack.getItem() instanceof ToolCore && ModTraits.mosstacular.isToolWithTrait(stack);
	}
}
