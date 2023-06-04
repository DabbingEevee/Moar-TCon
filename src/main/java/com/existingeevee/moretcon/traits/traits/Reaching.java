package com.existingeevee.moretcon.traits.traits;

import java.util.List;
import java.util.UUID;

import com.existingeevee.moretcon.other.Misc;
import com.gildedgames.the_aether.networking.AetherNetworkingManager;
import com.gildedgames.the_aether.networking.packets.PacketExtendedAttack;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

//adapted from tinkers aether
public class Reaching extends AbstractTrait {
    public static final AttributeModifier reachModifier = new AttributeModifier(UUID.fromString("df6eabe7-ffff-0000-9099-002f90370708"), "Reaching Modifier", 2D, 0);

    public Reaching() {
        super(Misc.createNonConflictiveName("reaching"), 0);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onPlayerJoin(EntityJoinWorldEvent event) {
        if ((event.getEntity() instanceof EntityPlayer)) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeAllModifiers();
        }
    }

    @SubscribeEvent
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if ((event.getEntityLiving() instanceof EntityPlayer)) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack stack = player.getHeldItemMainhand();
            if (!player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).hasModifier(reachModifier) && isToolWithTrait(stack)) {
                player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).applyModifier(reachModifier);
            } else if (player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).hasModifier(reachModifier) && !isToolWithTrait(stack)) {
                player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeModifier(reachModifier);
            }
        }
    }

    @SubscribeEvent
    public void onMouseClick(PlayerInteractEvent.LeftClickEmpty event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.getHeldItemMainhand();
        if(isToolWithTrait(stack)) {
            Vec3d playerVision = player.getLookVec();
            AxisAlignedBB reachDistance = player.getEntityBoundingBox().grow(10.0D);
            List<Entity> locatedEntities = player.world.getEntitiesWithinAABB(Entity.class, reachDistance);
            Entity found = null;
            double foundLen = 0.0D;
            for (Object o : locatedEntities) {
                if (o == player) {
                    continue;
                }
                Entity ent = (Entity) o;
                if (!ent.canBeCollidedWith()) {
                    continue;
                }
                Vec3d vec = new Vec3d(ent.posX - player.posX, ent.getEntityBoundingBox().minY + ent.height / 2f - player.posY - player.getEyeHeight(), ent.posZ - player.posZ);

                double len = vec.lengthVector();
                if (len > 8F) {
                    continue;
                }
                vec = vec.normalize();
                double dot = playerVision.dotProduct(vec);
                if (dot < 1.0 - 0.125 / len || !player.canEntityBeSeen(ent)) {
                    continue;
                }
                if (foundLen == 0.0 || len < foundLen) {
                    found = ent;
                    foundLen = len;
                }
            }
            if (found != null && player.getRidingEntity() != found) {
            	if (!player.isCreative()) ToolHelper.damageTool(stack, 1, player);
				AetherNetworkingManager.sendToServer(new PacketExtendedAttack(found.getEntityId()));
            }
        }
    }
}
