package com.existingeevee.moretcon.block.blocktypes;

import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.block.ISimpleBlockItemProvider;
import com.existingeevee.moretcon.block.ore.IBedrockMineable;
import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.inits.ModItems;
import com.existingeevee.moretcon.item.ItemIonstoneBlock;
import com.existingeevee.moretcon.item.ItemVacuuiteBlock;
import com.existingeevee.moretcon.other.ClusterTickingHandler;
import com.existingeevee.moretcon.other.ClusterTickingHandler.IClusterType;
import com.existingeevee.moretcon.other.ClusterTickingHandler.IClusterable;
import com.existingeevee.moretcon.other.ClusterTickingHandler.ISimpleClusterable;
import com.existingeevee.moretcon.other.utils.FireproofItemUtil;
import com.existingeevee.moretcon.other.utils.MiscUtils;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBase extends Block implements ISimpleBlockItemProvider, IBedrockMineable, IClusterable {

	protected boolean canBeBeacon = false;
	protected boolean canBurn = true;
	protected boolean canSustainFire = false;

	protected double mass = 1;
	protected Supplier<IClusterType> cluster = () -> null;

	protected Supplier<Block> fireTransformer;

	public BlockBase(String itemName, Material material, int harvestLevel) {
		super(material);
		setUnlocalizedName(MiscUtils.createNonConflictiveName(itemName.toLowerCase()));
		if (harvestLevel > 0) {
			setHarvestLevel("pickaxe", harvestLevel);
		}
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
		return side == EnumFacing.UP && canSustainFire || super.isFireSource(world, pos, side);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
		return canBeBeacon;
	}

	public BlockBase canBeBeacon(boolean bool) {
		this.canBeBeacon = bool;
		return this;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (this.getType() != null) {
			worldIn.scheduleUpdate(pos, this, 1);
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (this.getType() != null) {
			ClusterTickingHandler.addTick(worldIn, pos);
			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
		}
	}

	@Override
	public int quantityDropped(Random random) {
		if (this == ModBlocks.blockSiltClay) { // BlockGrass
			return 4;
		}
		return 1;
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {
		if (this.equals(ModBlocks.blockSiltClay)) {
			return 4;
		}
		return 1;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if (state.getBlock() == ModBlocks.blockSiltClay) {
			return ModItems.itemSiltClay == null ? Item.getItemFromBlock(this) : ModItems.itemSiltClay;
		}
		return super.getItemDropped(state, rand, fortune);
	}

	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
		if ((state.getBlock() == ModBlocks.blockCragravel) || (state.getBlock() == ModBlocks.blockSiltClay)) {
			return SoundType.GROUND;
		}
		return super.getSoundType(state, world, pos, entity);
	}

	public boolean canBurn() {
		return this.canBurn;
	}

	public BlockBase setCanBurn(boolean bool) {
		this.canBurn = bool;
		return this;
	}

	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}

	@Override
	public ItemBlock createBlockItem() {
		if (this == ModBlocks.blockIonstone) {
			return new ItemIonstoneBlock(this);
		}
		
		if (this == ModBlocks.blockVacuuite) {
			return new ItemVacuuiteBlock(this);
		}
		
		if (this == ModBlocks.blockErythynite || this == ModBlocks.oreErythynite) {
			return new ItemBlock(this) {
				@Override
				public boolean onEntityItemUpdate(EntityItem entityItem) {
					entityItem.motionY += 0.039f;
					return false;
				}
			};
		}
		if (this.canBurn) {
			return new ItemBlock(this);
		}
		return new ItemBlock(this) {
			@Override
			public boolean onEntityItemUpdate(EntityItem entityItem) {
				FireproofItemUtil.onUpdateSafe(entityItem);
				return true;
			}
		};
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (fireTransformer != null && fromPos.equals(pos.up()) && world.getBlockState(pos.up()).getBlock() == Blocks.FIRE) {
			world.setBlockState(pos.up(), fireTransformer.get().getDefaultState());
		}
	}

	public BlockBase setHarvestLevelC(String string, int i) {
		super.setHarvestLevel(string, i);
		return this;
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type) {
		return this != ModBlocks.blockBrinkstone && this != ModBlocks.blockDarkBrinkstone && this != ModBlocks.orePerimidum && super.canCreatureSpawn(state, world, pos, type);
	}

	@Override
	public boolean isBedrockLike(IBlockState blockState, World worldIn, BlockPos pos) {
		return this == ModBlocks.blockBrinkstone || this == ModBlocks.blockDarkBrinkstone || this == ModBlocks.orePerimidum;
	}

	@Override
	public boolean isSoftBedrock(IBlockState blockState, World worldIn, BlockPos pos) {
		return this == ModBlocks.blockBrinkstone || this == ModBlocks.blockDarkBrinkstone || this == ModBlocks.orePerimidum;
	}

	public Supplier<Block> getFireTransformer() {
		return fireTransformer;
	}

	public BlockBase setFireTransformer(Supplier<Block> fireTransformer) {
		this.fireTransformer = fireTransformer;
		return this;
	}

	public BlockBase setCanSustainFire(boolean canSustainFire) {
		this.canSustainFire = canSustainFire;
		return this;
	}

	@Override
	public IClusterType getType() {
		if (this instanceof ISimpleClusterable)
			return (IClusterType) this;
		return cluster.get();
	}

	@Override
	public double getBlockMass(IBlockState state, World world, BlockPos pos) {
		return mass;
	}

	public BlockBase setClusterDate(Supplier<IClusterType> cluster, double mass) {
		this.cluster = cluster;
		this.mass = mass;

		return this;
	}
}