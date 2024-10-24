package com.existingeevee.moretcon.traits.traits.unique;

import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.traits.abst.StackDelagateTrait;
import com.gildedgames.the_aether.items.ItemsAether;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Boltforged extends StackDelagateTrait {

	public Boltforged() {
		super(MiscUtils.createNonConflictiveName("boltforged"), 0, () -> new ItemStack(ItemsAether.lightning_sword), false);
		this.setOnHit();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onEntityStruckByLightning(EntityStruckByLightningEvent event) {
		if (event.getEntity() instanceof EntityLivingBase) {
			EntityLivingBase player = (EntityLivingBase) event.getEntity();
			if (this.isToolWithTrait(player.getHeldItemMainhand())) {
				event.setCanceled(true);
			}
		}
	}
}
