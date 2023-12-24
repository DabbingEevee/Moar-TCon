package com.existingeevee.moretcon.traits.traits.unique;

import com.existingeevee.moretcon.effects.ModPotions;
import com.existingeevee.moretcon.entity.entities.EntityDecayingEffect;
import com.existingeevee.moretcon.entity.entities.EntityDecayingEffect.EnumDecayingEffectType;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.other.utils.SoundHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class BloodyArc extends AbstractTrait {
	
	public BloodyArc() {
		super(MiscUtils.createNonConflictiveName("bloody_arc"), -1);
		MinecraftForge.EVENT_BUS.register(this);
	}

    @SubscribeEvent
    public void onMouseClick(PlayerInteractEvent.RightClickItem e) {
        ItemStack stack = e.getEntityPlayer().getHeldItemMainhand();
        if (!e.getEntityPlayer().isSneaking()) return;
        if (e.getEntityPlayer().getCooldownTracker().hasCooldown(stack.getItem())) {
        	return;
        }
        if(isToolWithTrait(stack)) {
        	e.getEntityPlayer().getCooldownTracker().setCooldown(stack.getItem(), 15);
            if (!e.getEntityPlayer().world.isRemote) spawn(e.getEntityPlayer(), ToolHelper.getActualDamage(stack, e.getEntityPlayer()));
    		e.getEntityPlayer().playSound(SoundHandler.BLOODY_SLASH, 1, 1);
    		e.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
        }
	}
    
    @SubscribeEvent
    public void onMouseClick(PlayerInteractEvent.RightClickBlock e) {
        ItemStack stack = e.getEntityPlayer().getHeldItemMainhand();
        if (!e.getEntityPlayer().isSneaking()) return;
        if (e.getEntityPlayer().getCooldownTracker().hasCooldown(stack.getItem())) {
        	return;
        }
        if(isToolWithTrait(stack)) {
        	e.getEntityPlayer().getCooldownTracker().setCooldown(stack.getItem(), 15);
            if (!e.getEntityPlayer().world.isRemote) spawn(e.getEntityPlayer(), ToolHelper.getActualDamage(stack, e.getEntityPlayer()));
    		e.getEntityPlayer().playSound(SoundHandler.BLOODY_SLASH, 1, 1);
    		e.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
        }
	}
    
    @SubscribeEvent
    public void onMouseClick(EntityInteract e) {
        ItemStack stack = e.getEntityPlayer().getHeldItemMainhand();
        if (!e.getEntityPlayer().isSneaking()) return;
        if (e.getEntityPlayer().getCooldownTracker().hasCooldown(stack.getItem())) {
        	return;
        }
        if (ToolHelper.isBroken(stack)) return;
        
        if(isToolWithTrait(stack)) {
        	e.getEntityPlayer().getCooldownTracker().setCooldown(stack.getItem(), 15);
            if (!e.getEntityPlayer().world.isRemote) spawn(e.getEntityPlayer(), ToolHelper.getActualDamage(stack, e.getEntityPlayer()));
    		e.getEntityPlayer().playSound(SoundHandler.BLOODY_SLASH, 1, 1);
    		e.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
        }
	}
    
	public static void spawn(EntityPlayer playerIn, double damage) {
		EntityDecayingEffect e = new EntityDecayingEffect(playerIn.getEntityWorld(), EnumDecayingEffectType.BLOODY_ARC, damage * 0.75,
				1.5, playerIn.getUniqueID(), playerIn.rotationPitch, playerIn.rotationYaw, true);
		e.setPosition(playerIn.posX, playerIn.posY, playerIn.posZ);
		playerIn.getEntityWorld().spawnEntity(e);
		e.getAffectedEntities().stream().filter(entity -> entity instanceof EntityLivingBase)
				.map(entity -> (EntityLivingBase) entity)
				.forEach(entity -> entity.addPotionEffect(new PotionEffect(ModPotions.bleeding, 100, 0, false, false)));
		
	}
}
