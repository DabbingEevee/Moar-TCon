package com.existingeevee.moretcon.block.blocktypes;

import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.item.ItemIonstoneBlock;
import com.existingeevee.moretcon.item.ItemVacuuiteBlock;
import com.existingeevee.moretcon.other.WorldGravityUtils;
import com.existingeevee.moretcon.other.utils.CompatManager;
import com.existingeevee.moretcon.traits.ModTraits;
import com.existingeevee.moretcon.traits.traits.armor.ModArmorTraits;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.utils.ToolHelper;

public class BlockEtherealBase extends BlockBase {

	public static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(-100.0D, -100.0D, -100.0D, -100.0D, -100.0D, -100.0D);

	protected boolean canBeReplaced = true;

	public BlockEtherealBase(String itemName, Material material, int harvestLevel) {
		super(itemName, material, harvestLevel);
		MinecraftForge.EVENT_BUS.register(this);
		this.translucent = true;
		this.setResistance(Float.POSITIVE_INFINITY);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity) {		
		if (CompatManager.conarm && entity instanceof EntityLivingBase) {

			AxisAlignedBB blockBB = new AxisAlignedBB(pos, pos.add(1, 1, 1));// ;.contains(entity.getPositionVector())
			AxisAlignedBB foot = entity.getEntityBoundingBox().setMaxY(entity.getEntityBoundingBox().minY + 0.0001);
			if (!foot.intersects(blockBB)) {
				return;
			}

			EntityLivingBase living = (EntityLivingBase) entity;

			boolean canWalkOn = false; // ScaffoldBlock

			for (ItemStack stack : living.getArmorInventoryList()) {
				if (ModArmorTraits.etherealTangibility.isToolWithTrait(stack) && !ToolHelper.isBroken(stack)) {
					canWalkOn = true;
					break;
				}
			}

			double gravity = WorldGravityUtils.getWorldGravitiationalAcceleration(worldIn, entity.getPositionVector());

			if (canWalkOn && !(living.isElytraFlying() || (living instanceof EntityPlayer && ((EntityPlayer) living).capabilities.isFlying))) {
				entity.fallDistance = 0;
				entity.onGround = true;
				if (entity.motionY < 0.01) {
					entity.motionY = entity.isSneaking() ? gravity / 8 : -gravity;
				}
			}
		}

	}

	@Override
	public boolean isTopSolid(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean causesSuffocation(IBlockState state) {
		return false;
	}

	protected static final ThreadLocal<Boolean> USED_ETHERAL_BLOCK = ThreadLocal.withInitial(() -> false);

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		if (USED_ETHERAL_BLOCK.get()) // we want it so that ethereal blocks can be placed on ethereal blocks
			return false;
		return canBeReplaced;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canSilkHarvest() {
		return true;
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		return -1;// Float.MAX_VALUE * 0.99f;
	}

	public float getTrueHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		return super.getBlockHardness(blockState, worldIn, pos);
	}

	@Override
	public ItemBlock createBlockItem() {
		if (this == ModBlocks.oreVacuuite) {
			return new ItemVacuuiteBlock(this) {

				@Override
				public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
					USED_ETHERAL_BLOCK.set(true);
					EnumActionResult ret = super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
					USED_ETHERAL_BLOCK.remove();
					return ret;
				}
			};
		}
		
		if (this == ModBlocks.oreIonstone) {
			return new ItemIonstoneBlock(this) {

				@Override
				public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
					USED_ETHERAL_BLOCK.set(true);
					EnumActionResult ret = super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
					USED_ETHERAL_BLOCK.remove();
					return ret;
				}
			};
		}

		return new ItemBlock(this) {

			@Override
			public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
				USED_ETHERAL_BLOCK.set(true);
				EnumActionResult ret = super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
				USED_ETHERAL_BLOCK.remove();
				return ret;
			}
		};

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
			if (ModTraits.etheralHarvest.isToolWithTrait(stack) || Block.getBlockFromItem(stack.getItem()) instanceof BlockEtherealBase) {
				return FULL_BLOCK_AABB;
			}
		}
		return EMPTY_AABB;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
