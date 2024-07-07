package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.OverrideItemUseEvent;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.traits.abst.IAdditionalTraitMethods;
import com.gildedgames.the_aether.entities.block.EntityFloatingBlock;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;


public class AntiGravity extends AbstractTrait implements IAdditionalTraitMethods {

	public AntiGravity() {
		super(MiscUtils.createNonConflictiveName("antigravity"), 0);
        MinecraftForge.EVENT_BUS.register(this);
	}

    @SubscribeEvent(priority = EventPriority.LOWEST)
	public void onRightClick(OverrideItemUseEvent event) {
    	if (CompatManager.aether_legacy) {
	        World world = event.getWorld();
	        BlockPos pos = event.getPos();
	        ItemStack heldItem = event.getItemStack();
	        if (event.getWorld().isRemote
	                //|| !event.getEntityPlayer().isSneaking()
	                || heldItem == ItemStack.EMPTY
	                || !isToolWithTrait(heldItem)
	                || ToolHelper.getCurrentDurability(event.getItemStack()) < 4)
	            return;
	        if ((heldItem.getDestroySpeed(world.getBlockState(pos)) > 1.0f || ForgeHooks.isToolEffective(world, pos, heldItem)) && world.isAirBlock(pos.up())) {
	            if (world.getTileEntity(pos) != null || world.getBlockState(pos).getBlockHardness(world, pos) == -1.0F) {
	                return;
	            }

	            if (!world.isRemote) {
	                EntityFloatingBlock ent = new EntityFloatingBlock(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, world.getBlockState(pos));
	                world.spawnEntity(ent);
	            }

	            if (!event.getEntityPlayer().isCreative()) {
	                ToolHelper.damageTool(event.getItemStack(), 4, event.getEntityLiving());
	            }
	            event.setResult(Result.DENY);
	            event.getEntityPlayer().swingArm(event.getHand());
	        }
    	}
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        target.addVelocity(0.0D, 1.0D, 0.0D);
        if (target instanceof EntityPlayerMP) {
            ((EntityPlayerMP)target).connection.sendPacket(new SPacketEntityVelocity(target));
        }
    }
}