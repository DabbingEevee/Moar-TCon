package com.existingeevee.moretcon.other.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.existingeevee.math.Quaternion;
import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.materials.IUniqueMaterial;
import com.existingeevee.moretcon.other.DamageScalar;
import com.existingeevee.moretcon.other.utils.MirrorUtils.IField;
import com.existingeevee.moretcon.other.utils.MirrorUtils.IMethod;
import com.existingeevee.moretcon.traits.ModTraits;
import com.existingeevee.moretcon.traits.modifiers.ModExtraTrait2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo.BlockTexture;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.ModExtraTrait;

public class MiscUtils {

	public static boolean canArrowHit(Entity e) {
		return EntitySelectors.NOT_SPECTATING.test(e) && EntitySelectors.IS_ALIVE.test(e) && e.canBeCollidedWith();
	}

	public static boolean isClass(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	protected static final IMethod<SoundEvent> getDeathSound$EntityLivingBase = MirrorUtils.reflectObfusMethod(EntityLivingBase.class, "func_184615_bR", SoundEvent.class);
	protected static final IMethod<Float> getSoundVolume$EntityLivingBase = MirrorUtils.reflectObfusMethod(EntityLivingBase.class, "func_70599_aP", float.class);
	protected static final IMethod<Float> getSoundPitch$EntityLivingBase = MirrorUtils.reflectObfusMethod(EntityLivingBase.class, "func_70647_i", float.class);
	protected static final IMethod<Boolean> checkTotemDeathProtection$EntityLivingBase = MirrorUtils.reflectObfusMethod(EntityLivingBase.class, "func_190628_d", boolean.class, DamageSource.class);

	public static void trueDamage(EntityLivingBase entity, float amount, DamageSource src, boolean bypassChecks) {
		if (entity.getHealth() <= 0 || ((entity instanceof EntityPlayer) && ((EntityPlayer) entity).capabilities.isCreativeMode)) {
			return;
		}
		if (!bypassChecks) {
			if (entity.isEntityInvulnerable(src)) {
				return;
			}
		}

		amount *= DamageScalar.getMult();

		float health = entity.getHealth();
		entity.getCombatTracker().trackDamage(src, health, amount);
		entity.setHealth(health - amount);
		if (entity.getHealth() <= 0) {
			if (!checkTotemDeathProtection$EntityLivingBase.invoke(entity, src)) {
				SoundEvent soundevent = getDeathSound$EntityLivingBase.invoke(entity);
				if (soundevent != null) {
					entity.playSound(soundevent, getSoundVolume$EntityLivingBase.invoke(entity), getSoundPitch$EntityLivingBase.invoke(entity));
				}
				entity.onDeath(src);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> T copyEntity(T entity) {
		if (!entity.world.isRemote && entity != null) {
			try {
				Class<T> c = (Class<T>) entity.getClass();
				Constructor<T> constructor = c.getDeclaredConstructor(World.class);
				T newInstance = constructor.newInstance(entity.world);
				newInstance.deserializeNBT(entity.serializeNBT());
				newInstance.setUniqueId(UUID.randomUUID()); // Prevent UUID fuckups

				return newInstance;
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Block> T getBlockTypeInBB(World world, AxisAlignedBB bb, Class<T> type) {
		int j2 = MathHelper.floor(bb.minX);
		int k2 = MathHelper.ceil(bb.maxX);
		int l2 = MathHelper.floor(bb.minY);
		int i3 = MathHelper.ceil(bb.maxY);
		int j3 = MathHelper.floor(bb.minZ);
		int k3 = MathHelper.ceil(bb.maxZ);
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

		for (int l3 = j2; l3 < k2; ++l3) {
			for (int i4 = l2; i4 < i3; ++i4) {
				for (int j4 = j3; j4 < k3; ++j4) {
					IBlockState iblockstate1 = world.getBlockState(blockpos$pooledmutableblockpos.setPos(l3, i4, j4));
					if (type.isInstance(iblockstate1.getBlock())) {
						blockpos$pooledmutableblockpos.release();
						return (T) iblockstate1.getBlock();
					}
				}
			}
		}

		blockpos$pooledmutableblockpos.release();
		return null;
	}

	protected static final IField<Boolean> isUnblockable$DamageSource = MirrorUtils.reflectObfusField(DamageSource.class, "field_76374_o");
	protected static final IField<Float> hungerDamage$DamageSource = MirrorUtils.reflectObfusField(DamageSource.class, "field_76384_q");

	public static void penetratingDamage(EntityLivingBase entity, int amount, DamageSource src, boolean bypassChecks) {
		int iframe = entity.hurtResistantTime;

		if (bypassChecks) {
			entity.hurtResistantTime = 0;
		}

		boolean isUnblockable = src.isUnblockable();
		float hungerDamage = src.getHungerDamage();
		try {
			entity.attackEntityFrom(src.setDamageBypassesArmor(), amount);
		} finally {
			isUnblockable$DamageSource.set(src, isUnblockable);
			hungerDamage$DamageSource.set(src, hungerDamage);
		}

		if (bypassChecks) {
			entity.hurtResistantTime = iframe;
		}
	}

	public static Vec3d convertToEntityPos(BlockPos blockpos) {
		double entityPosX = blockpos.getX() + 0.5;
		double entityPosY = blockpos.getY();
		double entityPosZ = blockpos.getZ() + 0.5;
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

	public static String readTextFile(File file) throws FileNotFoundException {
		String text = "";
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String data = scanner.nextLine();
			text += (data + "\n");

		}
		scanner.close();

		return text;

	}

	public static String createNonConflictiveName(String str) {
		return ModInfo.MODID + "." + str;
	}

	private static Map<String, ModExtraTrait> extratrait = new HashMap<>();
	private static Map<String, ModExtraTrait2> extratrait2 = new HashMap<>();

	public static void init() { // we have to init this
		for (Modifier m : TinkerModifiers.extraTraitMods) {
			extratrait.put(m.getIdentifier(), (ModExtraTrait) m);
		}
		for (Modifier m : ModTraits.extraTraitMods) {
			extratrait2.put(m.getIdentifier(), (ModExtraTrait2) m);
		}
	}

	protected static final IField<Material> material$ModExtraTrait = MirrorUtils.reflectObfusField(ModExtraTrait.class, "material");

	public static List<IUniqueMaterial> getUniqueEmbossments(ItemStack stack) {
		return getEmbossments(stack).stream().filter(m -> m instanceof IUniqueMaterial).map(m -> ((IUniqueMaterial) m)).collect(Collectors.toList());
	}

	public static List<Material> getEmbossments(ItemStack stack) {
		List<Material> material = new ArrayList<>();

		NBTTagList modifiers = TagUtil.getBaseModifiersTagList(stack);

		for (NBTBase tag : modifiers) {
			String identifier = ((NBTTagString) tag).getString();
			if (identifier.startsWith(ModExtraTrait2.EXTRA_TRAIT_IDENTIFIER)) {
				ModExtraTrait2 mod = extratrait2.get(identifier);
				if (mod != null) {
					material.add(mod.material);
				}
			} else if (identifier.startsWith(ModExtraTrait.EXTRA_TRAIT_IDENTIFIER)) {
				ModExtraTrait mod = extratrait.get(identifier);
				if (mod != null) {
					material.add(material$ModExtraTrait.get(mod));
				}
			}
		}

		return ImmutableList.copyOf(material);
	}

	public static List<Material> getMaterials(ItemStack stack) {
		NBTTagList list = TagUtil.getBaseMaterialsTagList(stack);

		List<Material> retList = new ArrayList<>();
		for (NBTBase base : list) {
			NBTTagString string = (NBTTagString) base;
			Material mat = TinkerRegistry.getMaterial(string.getString());
			if (mat != null) { // BlockToolForge
				retList.add(mat);
			}
		}
		return retList;
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

	public static Vec3d getCenter(AxisAlignedBB bb) {
		return new Vec3d(bb.minX + (bb.maxX - bb.minX) * 0.5D, bb.minY + (bb.maxY - bb.minY) * 0.5D, bb.minZ + (bb.maxZ - bb.minZ) * 0.5D);
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

	public static class NTickTracker {
		protected static boolean hasStarted = false;

		protected static final List<NTickTracker> LIST = Lists.newArrayList();

		public NTickTracker(Runnable runnable, int executeIn) {
			this.runnable = runnable;
			this.waitTicks = executeIn;
		}

		protected boolean start() {
			if (LIST.contains(this))
				return false;
			LIST.add(this);
			if (!hasStarted)
				MinecraftForge.EVENT_BUS.register(NTickTracker.class);
			return true;
		}

		private int ticks = 0;
		private float waitTicks;
		private Runnable runnable;

		@SubscribeEvent
		public static void tick(TickEvent.ServerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				for (int i = 0; i < LIST.size(); i++) {
					NTickTracker tracker = LIST.get(i);

					if (tracker.ticks++ > tracker.waitTicks) {
						tracker.runnable.run();
						LIST.remove(i--);
					}
				}
			}
		}

	}

	public static void executeInNTicks(Runnable runnable, int executeIn) {
		new NTickTracker(runnable, executeIn).start();
	}

	public static double randomN1T1() {
		return Math.random() * 2 - 1;
	}

	public static void resetRightClickDelay() {
		if (isClient()) {
			ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), 0, "field_71467_ac");
		}
	}

	public static RayTraceResult rayTrace(EntityLivingBase entityLiving, double maxRange, List<Entity> exclude) {
		return rayTrace(entityLiving, maxRange, exclude, true);
	}

	public static RayTraceResult rayTrace(EntityLivingBase entityLiving, double maxRange, List<Entity> exclude, boolean affectedByBlocks) {
		Vec3d start = entityLiving.getPositionEyes(0.5f);
		Vec3d lookVec = entityLiving.getLookVec();

		exclude = exclude == null ? new ArrayList<>() : new ArrayList<>(exclude);
		exclude.add(entityLiving);

		return rayTrace(start, lookVec, entityLiving.world, maxRange, exclude, affectedByBlocks, false);
	}

	public static RayTraceResult rayTrace(Vec3d start, Vec3d direction, World world, double maxRange, List<Entity> exclude, boolean affectedByBlocks, boolean ignoreNoBounding) {
		Vec3d end = start.add(direction.scale(maxRange));
		RayTraceResult firstTrace = affectedByBlocks ? world.rayTraceBlocks(start, end, false, ignoreNoBounding, true) : null;
		Vec3d path = (firstTrace != null ? firstTrace.hitVec : end).add(direction.scale(2));
		AxisAlignedBB area = new AxisAlignedBB(start.x, start.y, start.z, path.x, path.y, path.z);
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(null, area);

		Entity closestValid = null;
		double closestDistSq = Double.MAX_VALUE;

		for (Entity e : entities) {
			if (!(e instanceof EntityLivingBase) || (exclude != null && exclude.contains(e))) {
				continue;
			}

			RayTraceResult intercept = e.getEntityBoundingBox().calculateIntercept(start, end);

			if (intercept != null && intercept.hitVec.squareDistanceTo(start) <= start.squareDistanceTo(end)) {
				double distSq = intercept.hitVec.squareDistanceTo(start);
				if (closestDistSq > distSq) {
					closestValid = e;
					closestDistSq = distSq;
				}
			}
		}

		if (closestValid != null) {
			return new RayTraceResult(closestValid);
		} else if (firstTrace != null) {
			return firstTrace;
		} else {
			return new RayTraceResult(RayTraceResult.Type.MISS, end, EnumFacing.DOWN, new BlockPos(end));
		}
	}

	public static double quadraticArc(double initialSlope, double endpoint, double x) {
		double a = -initialSlope / endpoint;
		return a * x * (x - endpoint);
	}

	public static Pair<Double, Double> getPitchYaw(Vec3d vec) {
		if (vec.lengthSquared() != 1) {
			vec = vec.normalize();
		}

		double pitch = Math.asin(-vec.y);
		double yaw = Math.atan2(vec.z, vec.z);

		return Pair.of(pitch, yaw);
	}

	public static Vec3d fromPitchYaw(double pitch, double yaw) {
		double f = Math.cos(-yaw - Math.PI);
		double f1 = Math.sin(-yaw - Math.PI);
		double f2 = -Math.cos(-pitch);
		double f3 = Math.sin(-pitch);
		return new Vec3d(f1 * f2, f3, f * f2);
	}

	public static Vec3d rotateVec3d(Vec3d original, Vec3d axis, float theta) {
		Quaternion quaternion = new Quaternion(axis.normalize(), theta, false);
		return quaternion.transformVector(original);
	}

	public static AxisAlignedBB pointBound(Vec3d point) {
		return vectorBound(point, point);
	}

	public static AxisAlignedBB vectorBound(Vec3d a, Vec3d b) {
		return new AxisAlignedBB(a.x, a.y, a.z, b.x, b.y, b.z);
	}

	public static double clamp(double val, double max, double min) {
		return Math.min(Math.max(val, min), max);
	}

	public static int clamp(int val, int max, int min) {
		return Math.min(Math.max(val, min), max);
	}
	
	public static double clamp1(double d) {
		return clamp(d, 1, 0);
	}

}
