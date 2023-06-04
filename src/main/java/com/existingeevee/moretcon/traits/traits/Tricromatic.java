package com.existingeevee.moretcon.traits.traits;

import java.util.Random;

import com.existingeevee.moretcon.other.Misc;
import com.existingeevee.moretcon.other.utils.ConfigHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;

public class Tricromatic extends ModifierTrait {

	public Tricromatic() {
		super(Misc.createNonConflictiveName("trichromatic"), 0);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof EntityPlayer && isSelected) {
			int color = calculateChunkColor(entity);
			if (color == 0) {
				red((EntityPlayer) entity);
			} else if (color == 1) {
				green((EntityPlayer) entity);
			} else if (color == 2) {
				blue((EntityPlayer) entity);
			}
		}
	}

	private void red(EntityPlayer entity) {
		if (entity.isSneaking()) entity.sendStatusMessage(new TextComponentString("§cRed"), true);
		if (entity.world.isRemote) entity.world.spawnParticle(EnumParticleTypes.REDSTONE, true, entity.getPositionVector().x, entity.getPositionVector().y + 0.05, entity.getPositionVector().z, 0, 0, 0);

		Potion effect = Potion.REGISTRY.getObject(ConfigHandler.trichromicRed);

		if (effect != null && !entity.isPotionActive(effect)) {
			entity.addPotionEffect(new PotionEffect(effect, 2, ConfigHandler.trichromicRedLvl - 1, true, true));
		}
	}

	private void green(EntityPlayer entity) {
		if (entity.isSneaking()) entity.sendStatusMessage(new TextComponentString("§aGreen"), true);
		if (entity.world.isRemote) entity.world.spawnParticle(EnumParticleTypes.REDSTONE, true, entity.getPositionVector().x, entity.getPositionVector().y + 0.05, entity.getPositionVector().z, -1, 1, 0);

		Potion effect = Potion.REGISTRY.getObject(ConfigHandler.trichromicGreen);

		if (entity.world.getTotalWorldTime() % 40 == 0 && effect == null) {
			entity.heal(1);
		}

		if (effect != null && !entity.isPotionActive(effect)) {
			entity.addPotionEffect(new PotionEffect(effect, 2, ConfigHandler.trichromicGreenLvl - 1, true, true));
		}
	}

	private void blue(EntityPlayer entity) {
		if (entity.isSneaking()) entity.sendStatusMessage(new TextComponentString("§9Blue"), true);
		if (entity.world.isRemote) entity.world.spawnParticle(EnumParticleTypes.REDSTONE, true, entity.getPositionVector().x, entity.getPositionVector().y + 0.05, entity.getPositionVector().z, -1, 0, 1);

		Potion effect = Potion.REGISTRY.getObject(ConfigHandler.trichromicBlue);

		if (entity.world.getTotalWorldTime() % 80 == 0 && effect == null && entity.getAbsorptionAmount() < 10) {
			entity.setAbsorptionAmount(Math.min(entity.getAbsorptionAmount() + 1, 10));
		}
		if (effect != null && !entity.isPotionActive(effect)) {
			entity.addPotionEffect(new PotionEffect(effect, 2, ConfigHandler.trichromicBlueLvl - 1, true, true));
		}
	}

	private int calculateChunkColor(Entity entity) {
		long seed = new Random(Integer.toString(((int) entity.posX) / 16).hashCode() + Integer.toString(((int) entity.posZ) / 16).hashCode()).nextInt(10000);
		float rngNum = new Random(seed).nextFloat();
		if (rngNum < (1f/3f)) return 0;
		if (rngNum < (2f/3f)) return 1;
		return 2;
	}

/*	private static void aoeParticle(Entity entity, int count, String particleCommand) {//particle reddust {~pos~} -1 -1 1 1
		Random rand = new Random();
		ArrayList<String> commands = new ArrayList<String>();
		//AxisAlignedBB box = entity.getEntityBoundingBox().expand(0.5D, 0.5D, 0.5D).expand(-0.5D, -0.5D, -0.5D);
		for (int i = 0; i < count; i++) {
			commands.add(particleCommand.replace("{~pos~}", "~" + ((rand.nextDouble() * 3D) - 1.5D) + " ~" + ((rand.nextDouble() * 3D) - 1.5D) + " ~" + ((rand.nextDouble() * 3D) - 1.5D)));
		}
		execCommandNoLog(entity, commands.toArray(new String[0]));
	}

	private static void execCommandNoLog(Entity entity, String...commands) {
		if (entity.world == null) return;

		ICommandSender sender = new ICommandSender() {

			@Override
			public String getName() {
				return "";
			}

			@Override
			public boolean canUseCommand(int permLevel, String commandName) {
				return true;
			}

			@Override
			public World getEntityWorld() {
				return entity.world;
			}

			@Override
			public MinecraftServer getServer() {
				return entity.world.getMinecraftServer();
			}

			@Override
			public BlockPos getPosition() {
		        return entity.getPosition();
		    }

			@Override
			public Vec3d getPositionVector() {
		        return entity.getPositionVector();
		    }
		};
		boolean logAdminCommands = entity.world.getGameRules().getBoolean("logAdminCommands");
		entity.world.getGameRules().setOrCreateGameRule("logAdminCommands", "false");
		for (String command : commands) {
			entity.world.getMinecraftServer().getCommandManager().executeCommand(sender, command);
		}
		entity.world.getGameRules().setOrCreateGameRule("logAdminCommands", logAdminCommands == false ? "false" : "true");
	}*/
}
