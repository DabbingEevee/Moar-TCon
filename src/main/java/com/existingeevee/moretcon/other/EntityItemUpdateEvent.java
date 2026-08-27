package com.existingeevee.moretcon.other;

import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.EntityEvent;

public class EntityItemUpdateEvent extends EntityEvent {

	public EntityItemUpdateEvent(EntityItem entity) {
		super(entity);
	}

	@Override
	public boolean isCancelable() {
		return true;
	}
	
	public EntityItem getEntityItem() {
		return (EntityItem) this.getEntity();
	}

}
