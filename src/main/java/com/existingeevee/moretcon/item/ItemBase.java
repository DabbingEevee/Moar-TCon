package com.existingeevee.moretcon.item;

import java.util.List;

import javax.annotation.Nullable;

import com.existingeevee.moretcon.ModInfo;
import com.existingeevee.moretcon.other.ICustomSlotRenderer;
import com.existingeevee.moretcon.other.Misc;
import com.existingeevee.moretcon.other.ModTabs;
import com.existingeevee.moretcon.other.utils.TextHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class ItemBase extends Item implements ICustomSlotRenderer {

	public static final ResourceLocation CIRCLE_GLOW = new ResourceLocation(ModInfo.MODID, "other/bg_glow_circle");
	public static final ResourceLocation EXTREME_GLOW = new ResourceLocation(ModInfo.MODID, "other/bg_glow_extreme");
	public static final ResourceLocation BIG_CIRCLE_GLOW = new ResourceLocation(ModInfo.MODID, "other/bg_glow_circle_big");
	public static final ResourceLocation OVAL_GLOW = new ResourceLocation(ModInfo.MODID, "other/bg_glow_oval");

	protected CreativeTabs tab;

	public ItemBase(String itemName, int hex) {
		this(itemName);
		this.hex = hex;
	}

	public ItemBase(String itemName, GlowType type, int hex) {
		this(itemName);
		this.type = type;
		this.hex = hex;
	}

	public ItemBase(String itemName) {
		super();
		setupItem(this, itemName.toLowerCase());
		setTab(ModTabs.moarTConMaterials);
	}

	public ItemBase(String itemName, CreativeTabs tab) {
		super();
		setupItem(this, itemName.toLowerCase());
		setTab(tab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRender(ItemStack stack) {
		return hex >= 0;
	}

	public void setupItem(final Item item, final String itemName) {
		item.setUnlocalizedName(Misc.createNonConflictiveName(itemName));
	}

	public CreativeTabs getTab() {
		return tab;
	}

	public ItemBase setTab(CreativeTabs materials) {
		this.tab = materials;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (I18n.canTranslate(stack.getItem().getUnlocalizedName() + ".desc")) {
			String translation = I18n.translateToLocal(stack.getItem().getUnlocalizedName() + ".desc");
			if (!translation.contains("--null"))
				TextHelper.smartSplitString(translation, 35).forEach(tooltip::add);
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	private GlowType type = GlowType.CIRCLE;
	private int hex = -1;

	@Override
	@SideOnly(Side.CLIENT)
	public void render(ItemStack stack, int x, int y, IBakedModel bakedmodel) {
		float ticks = (float) ((Minecraft.getMinecraft().world.getTotalWorldTime()
				+ Minecraft.getMinecraft().getRenderPartialTicks()) % (40 * Math.PI));

		float mult = (float) (0.5f * Math.cos(0.05f * ticks) + 0.5f);

		float b = 0.25F + 0.5f * mult;

		GlStateManager.color(((hex & 0xFF0000) >> 16) / 255f, ((hex & 0x00FF00) >> 8) / 255f,
				(hex & 0x0000FF) / 255f, b);
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableAlpha();

		TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
		TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite(CIRCLE_GLOW.toString());
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x, y, textureatlassprite, 16, 16);

		if (type != GlowType.CIRCLE) {
			//System.out.println(new ResourceLocation(ModInfo.MODID + ":other/bg_glow_" + type.toString().toLowerCase()).toString());
			textureatlassprite = texturemap.getAtlasSprite(ModInfo.MODID + ":other/bg_glow_" + type.toString().toLowerCase());
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x - 8, y - 8, textureatlassprite, 32, 32);
		}

		GlStateManager.enableAlpha();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();

		// GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public ItemBase withColor(int color) {
		this.hex = color;
		return this;
	}

	public ItemBase withType(GlowType type) {
		this.type = type;
		return this;
	}
	
	public static enum GlowType {
		CIRCLE, OVAL, EXTREME, CIRCLE_BIG;
	}
}
