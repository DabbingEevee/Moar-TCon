package com.existingeevee.moretcon.other;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.materials.UniqueMaterial;
import com.existingeevee.moretcon.other.utils.ClientUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo.BlockTexture;
import slimeknights.tconstruct.library.materials.Material;

public class Misc {
	private static final Gson GSON = new GsonBuilder().create();

	public static boolean isClass(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public static void trueDamage(EntityLivingBase entity, float amount, DamageSource src, boolean bypassChecks) {
		if (entity.getHealth() <= 0)
			return;
		if (!bypassChecks) {
			if (entity.getIsInvulnerable())
				return;
			if (entity.isEntityInvulnerable(src))
				return;
			if (entity.hurtResistantTime > 0)	
				return;
		}
		float health = entity.getHealth();
		entity.getCombatTracker().trackDamage(src, health, amount);
		entity.setHealth(health - amount);
		if (entity.getHealth() <= 0) {
			if (!checkTotemDeathProtection(entity, src)) {
				entity.onDeath(src);
			}
		}
	}

	public static boolean checkTotemDeathProtection(EntityLivingBase e, DamageSource p_190628_1_) {
		if (p_190628_1_.canHarmInCreative()) {
			return false;
		} else {
			ItemStack itemstack = null;

			for (EnumHand enumhand : EnumHand.values()) {
				ItemStack itemstack1 = e.getHeldItem(enumhand);

				if (itemstack1.getItem() == Items.TOTEM_OF_UNDYING) {
					itemstack = itemstack1.copy();
					itemstack1.shrink(1);
					break;
				}
			}

			if (itemstack != null) {
				if (e instanceof EntityPlayerMP) {
					EntityPlayerMP entityplayermp = (EntityPlayerMP) e;
					entityplayermp.addStat(StatList.getObjectUseStats(Items.TOTEM_OF_UNDYING));
					CriteriaTriggers.USED_TOTEM.trigger(entityplayermp, itemstack);
				}

				e.setHealth(1.0F);
				e.clearActivePotions();
				e.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
				e.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
				e.world.setEntityState(e, (byte) 35);
			}

			return itemstack != null;
		}
	}

	public static void penetratingDamage(EntityLivingBase entity, int amount, DamageSource src, boolean bypassChecks) {
		int iframe = entity.hurtResistantTime;

		if (bypassChecks) {
			entity.hurtResistantTime = 0;
		}

		DamageSource clone = GSON.fromJson(GSON.toJson(src), src.getClass()).setDamageBypassesArmor();
		entity.attackEntityFrom(clone, amount);

		if (bypassChecks) {
			entity.hurtResistantTime = iframe;
		}
	}

	public static Vec3d convertToEntityPos(BlockPos blockpos) {
		double entityPosX = (((double) ((int) (blockpos.getX()))) + 0.5);
		double entityPosY = ((double) ((int) (blockpos.getY())));
		double entityPosZ = (((double) ((int) (blockpos.getZ()))) + 0.5);
		return new Vec3d(entityPosX, entityPosY, entityPosZ);
	}

	public static boolean isClient() {
		boolean isclient = true;
		try {
			emptyClientFunc();
		} catch (NoSuchMethodError e) {
			isclient = false;
		}
		return isclient;
	}

	@SideOnly(Side.CLIENT)
	private static void emptyClientFunc() {
	}

	public static BlockTexture createMaterialRenderInfoSafe(Material mat) {
		try {
			return ClientUtils.createMaterialRenderInfo(mat);
		} catch (NoClassDefFoundError e) {
			return null;
		}
	}

	public static String fileread(String str) {
		String textfile = "";
		try {
			File myObj = new File(new File(ClassLoader.getSystemClassLoader().getResource(".").getPath())
					.getAbsolutePath().replace("%20", " ") + "/" + str);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				textfile += (data + "\n");

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			return null;
		}
		return textfile;

	}

	public static String createNonConflictiveName(String str) {
		return ModInfo.MODID + "." + str;
	}

	public static Material getUniqueEmbossment(ItemStack stack) {
		NBTTagList tagList = stack.serializeNBT().getCompoundTag("tag").getCompoundTag("TinkerData")
				.getTagList("Modifiers", NBT.TAG_STRING);
		for (NBTBase tag : tagList) {
			if (!((NBTTagString) tag).getString().toString().startsWith("extratrait"))
				continue;
			String tagString = ((NBTTagString) tag).getString().toString().replaceFirst("extratrait", "");
			for (Material mat : TinkerRegistry.getAllMaterials()) {
				if ((TinkerRegistry.getTrait(tagString.replaceFirst(mat.getIdentifier(), "")) != null)
						&& mat instanceof UniqueMaterial) {
					return mat;
				}
			}
		}
		return null;
	}

	public static Material getEmbossment(ItemStack stack) {
		NBTTagList tagList = stack.serializeNBT().getCompoundTag("tag").getCompoundTag("TinkerData")
				.getTagList("Modifiers", NBT.TAG_STRING);
		for (NBTBase tag : tagList) {
			if (!((NBTTagString) tag).getString().toString().startsWith("extratrait"))
				continue;
			String tagString = ((NBTTagString) tag).getString().toString().replaceFirst("extratrait", "");
			for (Material mat : TinkerRegistry.getAllMaterials()) {
				if ((TinkerRegistry.getTrait(tagString.replaceFirst(mat.getIdentifier(), "")) != null)) {
					return mat;
				}
			}
		}
		return null;
	}

	public static List<Material> getMaterials(ItemStack stack) {
		if (stack.getTagCompound() == null)
			return new ArrayList<Material>();

		try {
			NBTTagList list = stack.getTagCompound().getCompoundTag("TinkerData").getTagList("Materials",
					NBT.TAG_STRING);
			List<Material> retList = new ArrayList<Material>();
			for (NBTBase base : list) {
				NBTTagString string = (NBTTagString) base;
				Material mat = TinkerRegistry.getMaterial(string.getString());
				if (mat != null) {
					retList.add(mat);
				}
			}
			return retList;
		} catch (ClassCastException e) {
		}
		return new ArrayList<Material>();
	}

	public static void registerBlockNuggetIngotRecipeOre(String suffix, Register<IRecipe> event) {
		registerBlockNuggetIngotRecipeOre("block" + suffix, "ingot" + suffix, "nugget" + suffix, event);
	}

	public static void registerBlockNuggetIngotRecipeOre(String block, String ingot, String nugget, Register<IRecipe> event) {
		register9x9Recipes(ingot, block, event);
		register9x9Recipes(nugget, ingot, event);
	}

	public static void registerBlockNuggetIngotRecipe(ItemStack block, ItemStack ingot, ItemStack nugget, Register<IRecipe> event) {
		register9x9Recipes(ingot, block, event);
		register9x9Recipes(nugget, ingot, event);
	}

	public static void register9x9Recipes(ItemStack small, ItemStack large, Register<IRecipe> event) {
		event.getRegistry().register(new ShapedRecipes(ModInfo.MODID, 3, 3,
				NonNullList.from(Ingredient.fromStacks(ItemStack.EMPTY), Ingredient.fromStacks(small.copy()),
						Ingredient.fromStacks(small.copy()), Ingredient.fromStacks(small.copy()),
						Ingredient.fromStacks(small.copy()), Ingredient.fromStacks(small.copy()),
						Ingredient.fromStacks(small.copy()), Ingredient.fromStacks(small.copy()),
						Ingredient.fromStacks(small.copy()), Ingredient.fromStacks(small.copy())),
				large.copy()).setRegistryName(
						small.getUnlocalizedName().toLowerCase() + "_to_" + large.getUnlocalizedName().toLowerCase()));

		ItemStack small9 = small.copy();
		small9.setCount(9);

		event.getRegistry().register(new ShapelessRecipes(ModInfo.MODID, small9,
				NonNullList.from(Ingredient.fromStacks(ItemStack.EMPTY), Ingredient.fromStacks(large.copy())))
				.setRegistryName(
						large.getUnlocalizedName().toLowerCase() + "_to_" + small.getUnlocalizedName().toLowerCase()));
	}

	public static void register9x9Recipes(String odSmall, String odLarge, Register<IRecipe> event) {
		if (OreDictionary.getOres(odSmall).size() == 0 || OreDictionary.getOres(odLarge).size() == 0) {
			return;
		}

		ItemStack small = OreDictionary.getOres(odSmall).get(0);
		ItemStack large = OreDictionary.getOres(odLarge).get(0);

		event.getRegistry().register(new ShapedRecipes(ModInfo.MODID, 3, 3,
				NonNullList.from(Ingredient.fromStacks(ItemStack.EMPTY), new OreIngredient(odSmall),
						new OreIngredient(odSmall), new OreIngredient(odSmall), new OreIngredient(odSmall),
						new OreIngredient(odSmall), new OreIngredient(odSmall), new OreIngredient(odSmall),
						new OreIngredient(odSmall), new OreIngredient(odSmall)),
				large.copy()).setRegistryName("od_" + odSmall + "_to_" + odLarge));

		ItemStack small9 = small.copy();
		small9.setCount(9);

		event.getRegistry().register(new ShapelessRecipes(ModInfo.MODID, small9,
				NonNullList.from(Ingredient.fromStacks(ItemStack.EMPTY), new OreIngredient(odLarge)))
				.setRegistryName("od_" + odLarge + "_to_" + odSmall));
	}

	public static void executeInNTicks(Executor executor, int executeIn) {
		new Object() {
			private int ticks = 0;
			private float waitTicks;

			public void start(int waitTicks) {
				this.waitTicks = waitTicks;
				MinecraftForge.EVENT_BUS.register(this);
			}

			@SubscribeEvent
			public void tick(TickEvent.ServerTickEvent event) {
				if (event.phase == TickEvent.Phase.END) {
					this.ticks += 1;
					if (this.ticks >= this.waitTicks) {
						run();
						MinecraftForge.EVENT_BUS.unregister(this);
					}
				}
			}

			@SubscribeEvent
			public void onWorldStarted(WorldEvent.Load e) {
				MinecraftForge.EVENT_BUS.unregister(this);
			}

			private void run() {
				executor.execute();
			}
		}.start(executeIn);
	}

	@FunctionalInterface
	public static interface Executor {
		void execute();
	}

	/*
	 * public static boolean isOldModid() { boolean isOld = false; try { File myObj
	 * = new File(new
	 * File(ClassLoader.getSystemClassLoader().getResource(".").getPath())
	 * .getAbsolutePath().replace("%20", " ") + "/config/" + VersionInfo.MODID + "/"
	 * + VersionInfo.NEW_MODID + ".cfg"); Scanner myReader = new Scanner(myObj);
	 * while (myReader.hasNextLine()) { String data = myReader.nextLine(); if
	 * (data.trim().startsWith("B:uselegacymodid")) { isOld =
	 * data.trim().split("=")[1].equals("true"); }
	 * 
	 * } myReader.close(); } catch (FileNotFoundException e) {} return isOld;
	 * 
	 * }
	 */
}
