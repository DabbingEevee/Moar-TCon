package com.existingeevee.moretcon.traits.traits;

import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class BottomsEnd extends AbstractTrait {
	public BottomsEnd() {
		super(MiscUtils.createNonConflictiveName("bottomsend".toLowerCase()), 0);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
    @SubscribeEvent
    public void bedrockHarvestEvent(BlockEvent.HarvestDropsEvent event) {
    	if (event.getHarvester() != null && this.isToolWithTrait(event.getHarvester().getHeldItemMainhand())) {
			IBlockState state = event.getState();
			if (state.getBlock() == Blocks.BEDROCK && event.getDrops().isEmpty()) {
				event.getDrops().add(new ItemStack(Blocks.BEDROCK));
			}
    	}
	}
	
    @Override
    public boolean isToolWithTrait(ItemStack is) {
    	return super.isToolWithTrait(is);
    }
        
    public Class<ToolCore> getToolCoreClass() {
    	return ToolCore.class;
    }
    
    public boolean canToolHarvest(ItemStack is, IBlockState state) {
    	if (is.getItem() instanceof ToolCore) {
    		return ((ToolCore) is.getItem()).isEffective(state);
    	}
    	return false;
    }
}
