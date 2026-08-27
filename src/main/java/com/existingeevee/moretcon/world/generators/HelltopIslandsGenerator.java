package com.existingeevee.moretcon.world.generators;

import java.awt.Color;

import com.existingeevee.math.noise.SimplexNoiseGenerator;
import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.NetworkHandler;
import com.existingeevee.moretcon.inits.ModBlocks;
import com.existingeevee.moretcon.inits.ModMaterials;
import com.existingeevee.moretcon.other.EntityItemUpdateEvent;
import com.existingeevee.moretcon.other.utils.MiscUtils;
import com.existingeevee.moretcon.world.WorldGenModifier;
import com.existingeevee.moretcon.world.WorldgenContext;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.ToolPart;
import slimeknights.tconstruct.library.utils.Tags;

public class HelltopIslandsGenerator extends WorldGenModifier {

	private static boolean registeredEventHandler = false;

	public HelltopIslandsGenerator() {
		if (!registeredEventHandler) {
			MinecraftForge.EVENT_BUS.register(HelltopIslandsGenerator.class);
		}
	}

	public static final SimplexNoiseGenerator HELLTOP_ISLANDS_GENERATOR = new SimplexNoiseGenerator(7, 0.6f, 0.00275f, -0.8);

	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent event) {
		if (event.phase != Phase.END || event.world.getWorldTime() % 20 != 0 || event.world.isRemote) {
			return;
		}

		for (EntityPlayer player : event.world.playerEntities) {

			if (!(player instanceof EntityPlayerMP))
				return;

			double x = player.posX, z = player.posZ;

			NetworkHandler.HANDLE.sendTo(new HelltopStatusMessage(isInHelltop(x, player.posY + player.eyeHeight, z, player.world)), (EntityPlayerMP) player);
		}
	}

	@SubscribeEvent
	public static void onEntityItemUpdate(EntityItemUpdateEvent event) {
		EntityItem entity = event.getEntityItem();
		ItemStack stack = entity.getItem();

		if (stack.getItem() instanceof ToolPart) {
			ToolPart part = (ToolPart) stack.getItem();

			if (part.getMaterial(stack) != ModMaterials.materialBrinkstone)
				return;
			
			if (!part.canUseMaterial(ModMaterials.materialMossyBrinkstone) || TinkerRegistry.getMaterial(ModMaterials.materialMossyBrinkstone.identifier) == Material.UNKNOWN)
				return;
			
			if (!entity.onGround || entity.world.getBlockState(entity.getPosition().down()).getBlock() != ModBlocks.blockMossyBrinkstone)
				return;
			
			int time = entity.getEntityData().getInteger(ModInfo.MODID + ".mossification");
			if (time >= 5 * 60 * 20) { // 5 mins 
			    NBTTagCompound tag = stack.getTagCompound();
			    tag.setString(Tags.PART_MATERIAL, ModMaterials.materialMossyBrinkstone.identifier);
			    stack.setTagCompound(tag);
			    entity.motionY += 0.25f; 
			    entity.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, 1.6, 1.6, 1.6);
			    entity.world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.BLOCKS, 1, 2f);
			} else {
				if (entity.lifespan < 10 * 60 * 20) 
					entity.lifespan = 10 * 60 * 20;
				entity.getEntityData().setInteger(ModInfo.MODID + ".mossification", time + 1);
			    entity.world.spawnParticle(EnumParticleTypes.END_ROD, entity.posX + MiscUtils.randomN1T1() * 0.1, entity.posY + entity.getEyeHeight() + MiscUtils.randomN1T1() * 0.1, entity.posZ + MiscUtils.randomN1T1() * 0.1, MiscUtils.randomN1T1() * 0.1, MiscUtils.randomN1T1() * 0.1 - 0.07, MiscUtils.randomN1T1() * 0.1);
			}
		}
	}

	public static boolean isInHelltop(double x, double y, double z, World world) {
		double noise = HELLTOP_ISLANDS_GENERATOR.generateOctavedSimplexNoise(x, z, world.getSeed()) * 1d;

		double expFactor = -1.0e-6; // ~-(2/1000)^2
		double mulFactor = -50;

		noise += mulFactor * Math.pow(Math.E, expFactor * (x * x + z * z));

		return world.provider.getDimensionType().getId() == DimensionType.NETHER.getId() && y >= 128 && y < 170 && noise > -0.4;
	}

	public static final Color FOG_COLOR = new Color(0xe8e0ff);
	private static boolean inHelltopIslandRegion = false;
	private static World curWorld = null;
	private static int curFogP = 0;
	private static int prevFogP = 0;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onClientTick(ClientTickEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		if (event.phase != Phase.END || mc.world == null || mc.player == null || mc.isGamePaused() && mc.isSingleplayer())
			return;

		prevFogP = curFogP;
		curFogP = Math.max(0, Math.min(60, curFogP + (inHelltopIslandRegion ? 1 : -1)));

		if (curWorld != mc.world) {
			curWorld = mc.world;

			inHelltopIslandRegion = false;
			prevFogP = 0;
			curFogP = 0;
		}

		if (!inHelltopIslandRegion)
			return;

		for (int i = 0; i < 10; i++) {
			double rX = MiscUtils.randomN1T1() * 15;
			double rY = MiscUtils.randomN1T1() * 15;
			double rZ = MiscUtils.randomN1T1() * 15;

			double x = mc.player.posX + rX, y = Math.max(128, mc.player.posY + rY), z = mc.player.posZ + rZ;

			mc.world.spawnParticle(EnumParticleTypes.END_ROD, x, y, z, 0, 0, 0);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onFogColors(FogColors event) {
		float renderFogP = curFogP + (curFogP - prevFogP) * Minecraft.getMinecraft().getRenderPartialTicks();

		renderFogP /= 60f;

		if (renderFogP < 1e-6)
			return;

		float r = FOG_COLOR.getRed() * renderFogP / 255f + event.getRed() * (1 - renderFogP);
		float g = FOG_COLOR.getGreen() * renderFogP / 255f + event.getGreen() * (1 - renderFogP);
		float b = FOG_COLOR.getBlue() * renderFogP / 255f + event.getBlue() * (1 - renderFogP);

		event.setRed(r * 1.2f);
		event.setGreen(g * 1.2f);
		event.setBlue(b * 1.2f);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onFogDensity(FogDensity event) {
		float renderFogP = curFogP + (curFogP - prevFogP) * Minecraft.getMinecraft().getRenderPartialTicks();

		renderFogP /= 60f;

		if (renderFogP < 1e-6)
			return;

		GlStateManager.setFog(GlStateManager.FogMode.EXP2);

		event.setDensity(Math.max(0.025f, 0.15f * renderFogP));
		event.setCanceled(true);
	}

	public static final char[][][] CRYSTAL_LAYOUT_BASE = { // MC CV
			{
					{ '-', 'S', '-' },
					{ 'S', 'S', 'S' },
					{ '-', 'S', '-' }
			},
			{
					{ 'S', 'S', 'S' },
					{ 'S', 'M', 'S' },
					{ 'S', 'S', 'S' }
			},

			{
					{ 'S', 'S', 'S' },
					{ 'S', 'M', 'S' },
					{ 'S', 'S', 'S' }
			},

			{
					{ 'S', 'S', 'S' },
					{ 'S', 'V', 'S' },
					{ 'S', 'S', 'S' }
			},

			{
					{ 'T', 'C', 'C' },
					{ 'C', 'V', 'C' },
					{ 'C', 'C', 'C' }
			},

			{
					{ '-', 'C', 'T' },
					{ 'C', 'V', 'C' },
					{ 'C', 'C', 'T' }
			},

			{
					{ '-', 'C', '-' },
					{ 'C', 'V', 'T' },
					{ 'T', 'C', '-' }
			},

			{
					{ '-', 'C', '-' },
					{ 'T', 'C', '-' },
					{ '-', 'T', '-' }
			},

			{
					{ '-', 'T', '-' },
					{ '-', 'C', '-' },
					{ '-', '-', '-' }
			},

			{
					{ '-', '-', '-' },
					{ '-', 'C', '-' },
					{ '-', '-', '-' }
			},

			{
					{ '-', '-', '-' },
					{ '-', 'C', '-' },
					{ '-', '-', '-' }
			},

			{
					{ '-', '-', '-' },
					{ '-', 'T', '-' },
					{ '-', '-', '-' }
			},
	};

	public static final char[][][][] CRYSTAL_LAYOUTS = { CRYSTAL_LAYOUT_BASE, {}, {}, {} };

	static {
		for (int r = 1; r < 4; r++) {
			char[][][] newLayout = new char[CRYSTAL_LAYOUT_BASE.length][0][0];
			for (int y = 0; y < CRYSTAL_LAYOUT_BASE.length; y++) {

				char[][] layer = CRYSTAL_LAYOUT_BASE[y];

				switch (r) {
					case 3:
						layer = rotateCW(layer);
					case 2:
						layer = rotateCW(layer);
					case 1:
						layer = rotateCW(layer);
				}

				newLayout[y] = layer;
			}

			CRYSTAL_LAYOUTS[r] = newLayout;
		}
	}

	public static char getCrystalBlock(int x, int y, int z, int r) {
		if (x < -1 || x > 1 || y < 0 || y >= CRYSTAL_LAYOUT_BASE.length || z < -1 || z > 1)
			return '-';

		while (r < 0)
			r += 4;
		r %= 4;

		char[][] layer = CRYSTAL_LAYOUTS[r][y];

		return getCrystalBlock(x, layer, z);
	}

	public static char getCrystalBlock(int x, char[][] layer, int z) {
		if (x < -1 || x > 1 || z < -1 || z > 1)
			return '-';

		return layer[x + 1][z + 1];
	}

	static char[][] rotateCW(char[][] mat) {
		final int M = mat.length;
		final int N = mat[0].length;
		char[][] ret = new char[N][M];
		for (int r = 0; r < M; r++) {
			for (char c = 0; c < N; c++) {
				ret[c][M - 1 - r] = mat[r][c];
			}
		}
		return ret;
	}

	@SuppressWarnings("unused")
	@Override
	public void generate(IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, WorldgenContext ctx) {
		World world = ctx.world;
		int chunkX = ctx.chunkX;
		int chunkZ = ctx.chunkZ;

		if (world.provider.getDimensionType().getId() != DimensionType.NETHER.getId()) {
			return;
		}

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				int x = i + chunkX * 16 + 8;
				int z = j + chunkZ * 16 + 8;

				double noise = HELLTOP_ISLANDS_GENERATOR.generateOctavedSimplexNoise(x, z, world.getSeed()) * 1d;

				double expFactor = -1.0e-6; // -(2/1000)^2
				double mulFactor = -50;

				noise += mulFactor * Math.pow(Math.E, expFactor * (x * x + z * z));

				if (noise < -0.5)
					continue;

				int height = noiseToHeight(noise);
				for (int y = 128; y < 128 + height; y++) {
					BlockPos pos = new BlockPos(x, y, z);
					if (!canReplace(world, pos))
						continue;
					if (noise < -0.2) {
						world.setBlockState(pos, ModBlocks.blockBrokenSand.getDefaultState(), 2);
					} else {
						if (y == 128 + height - 1) {
							world.setBlockState(pos, ModBlocks.blockMossyBrinkstone.getDefaultState(), 2);

							// plants
							if (ctx.rand.nextInt(6) == 0 && canReplace(world, pos.up())) {
								world.setBlockState(pos.up(), ctx.rand.nextInt(4) == 0 ? ModBlocks.blockPerimishroom.getDefaultState() : ModBlocks.blockPerimigrowth.getDefaultState(), 2);
							}

							if (ctx.rand.nextInt(10000) == 0 && y > 131) { // crystal
								int cy = 0;
								int r = ctx.rand.nextInt(4);
								for (char[][] layer : CRYSTAL_LAYOUT_BASE) {
									for (int cx = -1; cx < 2; cx++) {
										for (int cz = -1; cz < 2; cz++) {
											switch (getCrystalBlock(cx, cy, cz, r)) {
												case 'M':
												case 'S':
													world.setBlockState(new BlockPos(x + cx, cy + y - 3, z + cz), ModBlocks.blockDarkBrinkstone.getDefaultState(), 2);
													break;
												case 'T':
												case 'V':
												case 'C':
													world.setBlockState(new BlockPos(x + cx, cy + y - 3, z + cz), ModBlocks.orePerimidum.getDefaultState(), 2);
													break;
											}
										}
									}
									cy++;
								}
							}

							if (ctx.rand.nextInt(100) == 0 && y > 131) { // crystal
								int cy = 0;
								int r = ctx.rand.nextInt(4);

								Block block = ModBlocks.blockDarkBrinkstone;

								if (ctx.rand.nextInt(3) == 0) {
									block = ctx.rand.nextBoolean() ? Blocks.BEDROCK : ModBlocks.blockBrinkstone;
								}

								boolean spawnMalithyst = ctx.rand.nextInt(20) == 0;
								boolean hollow = ctx.rand.nextInt(3) == 0;
								for (char[][] layer : CRYSTAL_LAYOUT_BASE) {

									for (int cx = -1; cx < 2; cx++) {
										for (int cz = -1; cz < 2; cz++) {
											switch (getCrystalBlock(cx, cy, cz, r)) {
												case 'T':
													if (block == ModBlocks.blockBrinkstone) {
														world.setBlockState(new BlockPos(x + cx, cy + y - 3, z + cz), ModBlocks.blockMossyBrinkstone.getDefaultState(), 2);
														break;
													}
												case 'S':
												case 'C':
													world.setBlockState(new BlockPos(x + cx, cy + y - 3, z + cz), block.getDefaultState(), 2);
													break;
												case 'M':
												case 'V':
													if (spawnMalithyst && block == ModBlocks.blockDarkBrinkstone) {
														world.setBlockState(new BlockPos(x + cx, cy + y - 3, z + cz), ModBlocks.oreMalithyst.getDefaultState(), 2);
														break;
													} else if (!hollow) {
														world.setBlockState(new BlockPos(x + cx, cy + y - 3, z + cz), block.getDefaultState(), 2);
														break;
													}
											}
										}
									}
									cy++;
								}
							}
						} else {
							world.setBlockState(pos, ModBlocks.blockBrinkstone.getDefaultState(), 2);
						}
					}
				}
			}
		}
	}

	public static class HelltopStatusMessage implements IMessage, IMessageHandler<HelltopStatusMessage, IMessage> {
		boolean inHelltop;

		public HelltopStatusMessage() {
			this(false);
		}

		public HelltopStatusMessage(boolean inHelltop) {
			this.inHelltop = inHelltop;
		}

		@Override
		public IMessage onMessage(HelltopStatusMessage message, MessageContext ctx) {
			inHelltopIslandRegion = message.inHelltop;
			return null;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			inHelltop = buf.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeBoolean(inHelltop);
		}
	}

	private static int noiseToHeight(double noise) { // TODO mess with this
		return (int) Math.ceil((noise + 0.5) * 15 + 0.01);
	}

	private static boolean canReplace(World world, BlockPos pos) {
		IBlockState iblockstate = world.getBlockState(pos);
		Block block = iblockstate.getBlock();

		return block.isAir(iblockstate, world, pos) || block instanceof BlockBush; // i hate the stupid mushrooms grahhhh
	}
}
