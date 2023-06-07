package com.existingeevee.moretcon.block.blocktypes;

import java.util.List;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.traits.ModTraits;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class BlockEtheralBase extends BlockBase {

	public static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(-100.0D, -100.0D, -100.0D, -100.0D, -100.0D, -100.0D);

	public static final Material MAT = new MaterialTransparent(MapColor.AIR);

	public BlockEtheralBase(String itemName, int harvestLevel) {
		super(itemName, MAT, harvestLevel);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		return Float.MAX_VALUE * 0.99f;
	}

	@SubscribeEvent
	public void onBlockStrength(PlayerEvent.BreakSpeed event) {
		if (event.getState().getBlock().equals(this)) {
			if (ToolHelper.isBroken(event.getEntityPlayer().getHeldItemMainhand()))
				return;

			if (ToolHelper.getTraits(event.getEntityPlayer().getHeldItemMainhand()).stream()
					.anyMatch(t -> t.getIdentifier().equals(ModTraits.bottomsEnd.identifier))) {
				float speed = event.getNewSpeed();
				float hardness = (float) Math.max(Math.pow(10, -1000000), this.blockHardness);
				float overkill = Float.MAX_VALUE * 0.99f;
				float good = speed / hardness;
				event.setNewSpeed(overkill * good);
			} else {
				event.setNewSpeed(0);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (source instanceof WorldClient) {
			return this.getBoundingBoxClient(state, source, pos);
		}
		return FULL_BLOCK_AABB;
	}

	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getBoundingBoxClient(IBlockState state, IBlockAccess source, BlockPos pos) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		if (player != null) {
			ItemStack stack = player.getHeldItemMainhand();
			if (ToolHelper.getTraits(stack).contains(ModTraits.etheralHarvest)) {
				return FULL_BLOCK_AABB;
			}
		}
		return EMPTY_AABB;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		// trolled
	}
}
