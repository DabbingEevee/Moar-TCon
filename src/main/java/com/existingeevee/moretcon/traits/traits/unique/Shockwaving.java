package com.existingeevee.moretcon.traits.traits.unique;

import java.util.ArrayList;
import java.util.List;

import com.existingeevee.moretcon.other.OverrideItemUseEvent;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.traits.traits.abst.NumberTrackerTrait;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.utils.ToolHelper;
import thebetweenlands.common.entity.EntityShockwaveBlock;
import thebetweenlands.common.registries.SoundRegistry;

public class Shockwaving extends NumberTrackerTrait {

	public Shockwaving() {
		super(MiscUtils.createNonConflictiveName("shockwaving"), 0x0066ff);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onRightClick(OverrideItemUseEvent event) {
		ItemStack stack = event.getItemStack();

		if (!isToolWithTrait(stack) || ToolHelper.isBroken(stack))
			return;

		if (stack.getItemDamage() == stack.getMaxDamage()) {
			stack.getTagCompound().setInteger("cooldown", 0);
			event.setResult(Result.DENY);
			return;
		}

		if (stack.getTagCompound().getInteger("uses") < 3) {
			if (!event.getWorld().isRemote) {
				stack.damageItem(2, event.getEntityPlayer());
				event.getWorld().playSound(null, event.getEntityPlayer().posX, event.getEntityPlayer().posY,
						event.getEntityPlayer().posZ, SoundRegistry.SHOCKWAVE_SWORD, SoundCategory.BLOCKS, 1.25F,
						1.0F + event.getWorld().rand.nextFloat() * 0.1F);
				double direction = Math.toRadians(event.getEntityPlayer().rotationYaw);
				Vec3d diag = new Vec3d(Math.sin(direction + Math.PI / 2.0D), 0, Math.cos(direction + Math.PI / 2.0D))
						.normalize();
				List<BlockPos> spawnedPos = new ArrayList<BlockPos>();
				for (int distance = -1; distance <= 16; distance++) {
					for (int distance2 = -distance; distance2 <= distance; distance2++) {
						for (int yo = -1; yo <= 1; yo++) {
							int originX = MathHelper.floor(event.getPos().getX() + 0.5D - Math.sin(direction) * distance
									- diag.x * distance2 * 0.25D);
							int originY = event.getPos().getY() + yo;
							int originZ = MathHelper.floor(event.getPos().getZ() + 0.5D + Math.cos(direction) * distance
									+ diag.z * distance2 * 0.25D);
							BlockPos origin = new BlockPos(originX, originY, originZ);

							if (spawnedPos.contains(origin))
								continue;

							spawnedPos.add(origin);

							IBlockState block = event.getWorld().getBlockState(new BlockPos(originX, originY, originZ));

							if (block.isNormalCube() && !block.getBlock().hasTileEntity(block)
									&& block.getBlockHardness(event.getWorld(), origin) <= 5.0F
									&& block.getBlockHardness(event.getWorld(), origin) >= 0.0F
									&& !event.getWorld().getBlockState(origin.up()).isOpaqueCube()) {
								stack.getTagCompound().setInteger("blockID",
										Block.getIdFromBlock(event.getWorld().getBlockState(origin).getBlock()));
								stack.getTagCompound().setInteger("blockMeta", event.getWorld().getBlockState(origin)
										.getBlock().getMetaFromState(event.getWorld().getBlockState(origin)));

								EntityShockwaveBlock shockwaveBlock = new EntityShockwaveBlock(event.getWorld());
								shockwaveBlock.setOrigin(origin,
										MathHelper.floor(Math.sqrt(distance * distance + distance2 * distance2)),
										event.getPos().getX() + 0.5D, event.getPos().getZ() + 0.5D,
										event.getEntityPlayer());
								shockwaveBlock.setLocationAndAngles(originX + 0.5D, originY, originZ + 0.5D, 0.0F,
										0.0F);
								shockwaveBlock.setBlock(
										Block.getBlockById(stack.getTagCompound().getInteger("blockID")),
										stack.getTagCompound().getInteger("blockMeta"));
								event.getWorld().spawnEntity(shockwaveBlock);
								break;
							}
						}
					}
				}
				stack.getTagCompound().setInteger("uses", stack.getTagCompound().getInteger("uses") + 1);
				if (stack.getTagCompound().getInteger("uses") >= 3) {
					stack.getTagCompound().setInteger("uses", 3);
					stack.getTagCompound().setInteger("cooldown", 0);
				}
			}
			event.getEntityPlayer().swingArm(event.getHand());
			event.setResult(Result.ALLOW);
			return;
		}
		event.setResult(Result.DENY);
		return;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if (!stack.getTagCompound().hasKey("cooldown"))
			stack.getTagCompound().setInteger("cooldown", 0);
		if (!stack.getTagCompound().hasKey("uses"))
			stack.getTagCompound().setInteger("uses", 0);

		if (stack.getTagCompound().getInteger("uses") == 3) {
			if (stack.getTagCompound().getInteger("cooldown") < 60)
				stack.getTagCompound().setInteger("cooldown", stack.getTagCompound().getInteger("cooldown") + 1);
			if (stack.getTagCompound().getInteger("cooldown") >= 60) {
				stack.getTagCompound().setInteger("cooldown", 60);
				stack.getTagCompound().setInteger("uses", 0);
			}
		}
	}

	@Override
	public int getNumberMax(ItemStack stack) {
		return 3;
	}

	@Override
	public int getNumber(ItemStack stack) {
		return 3 - stack.getTagCompound().getInteger("uses");
	}

	@Override
	public int setNumber(ItemStack stack, int amount) {
		stack.getTagCompound().setInteger("uses", 3 - amount);
		return getNumber(stack);
	}
}