package com.existingeevee.moretcon.client.textures.unique;

import java.awt.image.DirectColorModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo.AbstractMaterialRenderInfo;
import slimeknights.tconstruct.library.client.RenderUtil;
import slimeknights.tconstruct.library.client.texture.AbstractColoredTexture;

//This is hell im sure theres a better way to do this
//buuuuut idk what im doing lmfao
public class ValasiumTexture extends AbstractColoredTexture { // FieryTexture
	private int state = 0;

	boolean[] blank;
	boolean[] border;
	boolean[] onTop;
	int minBrightness;
	int maxBrightness;
	int brightnessData[];

	private ResourceLocation backupTextureLocation;

	protected ValasiumTexture(ResourceLocation baseTextureLocation, String spriteName) {
		super(baseTextureLocation, spriteName);
		this.backupTextureLocation = baseTextureLocation;
	}

	public static class RenderInfo extends AbstractMaterialRenderInfo {

		@Override
		public TextureAtlasSprite getTexture(ResourceLocation baseTexture, String location) {
			ValasiumTexture texture = new ValasiumTexture(baseTexture, location);
			return texture;
		}
	}

	@Override
	public boolean load(IResourceManager manager, ResourceLocation location, Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
		this.framesTextureData = Lists.newArrayList();
		this.frameCounter = 0;
		this.tickCounter = 0;

		TextureAtlasSprite baseTexture = textureGetter.apply(backupTextureLocation);

		if (baseTexture == null || baseTexture.getFrameCount() <= 0) {
			this.width = 1;
			this.height = 1;
			// failure
			return false;
		}

		// copy data from base texture - we have the same properties/sizes as the base

		this.copyFrom(baseTexture);

		int[][] original = baseTexture.getFrameTextureData(0);

		if (original == null || original.length == 0 || original[0] == null) {
			this.width = 1;
			this.height = 1;
			return false;
		}

		ArrayList<int[][]> datas = Lists.newArrayList();

		for (int i = 0; i < 4; i++) {
			state = i;

			int[][] data = new int[original.length][];
			data[0] = Arrays.copyOf(original[0], original[0].length);

			// do the transformation on the data for mipmap level 0
			// looks like other mipmaps are generated correctly
			processData(data[0]);

			datas.add(data);
		}

		this.framesTextureData.addAll(datas);

		this.animationMetadata = new AnimationMetadataSection(
				IntStream.range(0, datas.size())
						.mapToObj(AnimationFrame::new)
						.collect(Collectors.toList()),
				width,
				height,
				2,
				false);

		return false;
	}

	@Override
	protected void preProcess(int[] data) {
		DirectColorModel color = new DirectColorModel(32, 16711680, '\uff00', 255, -16777216);

		border = new boolean[width * height];
		blank = new boolean[width * height];
		onTop = new boolean[width * height];

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
					border[coord(x, y)] = true;
				}

				int c = data[coord(x, y)];
				if (c == 0 || color.getAlpha(c) < 64) {
					blank[coord(x, y)] = true;
					if (x > 0) {
						border[coord(x - 1, y)] = true;
					}

					if (y > 0) {
						border[coord(x, y - 1)] = true;
					}

					if (x < width - 1) {
						border[coord(x + 1, y)] = true;
						onTop[coord(x + 1, y)] = true;
					}

					if (y < height - 1) {
						border[coord(x, y + 1)] = true;
						onTop[coord(x, y + 1)] = true;
					}
				}
			}
		}

		for (int x = 0; x < width; ++x) {
			if (border[coord(x, 0)]) {
				onTop[coord(x, 0)] = true;
			}
		}
		for (int y = 0; y < height; ++y) {
			if (border[coord(0, y)]) {
				onTop[coord(0, y)] = true;
			}
		}

		// setup brigthness data
		int max = 0;
		int min = 255;
		brightnessData = new int[data.length];
		for (int i = 0; i < data.length; i++) {
			int pixel = data[i];
			if (RenderUtil.alpha(pixel) == 0) {
				continue;
			}
			int brightness = getPerceptualBrightness(pixel);
			if (brightness < min) {
				min = brightness;
			}
			if (brightness > max) {
				max = brightness;
			}
			brightnessData[i] = brightness;
		}

		// calculate the actual limits where we change color
		int brightnessDiff = max - min;
		brightnessDiff /= 2;
		minBrightness = Math.max(min + 1, min + (int) (brightnessDiff * 0.4f));
		maxBrightness = Math.min(max - 1, max - (int) (brightnessDiff * 0.3f));
	}

	@Override
	protected void postProcess(int[] data) {
		blank = null;
		border = null;
		onTop = null;
		brightnessData = null;
	}

	@Override
	protected int colorPixel(int pixel, int pxCoord) {
		if (!blank[pxCoord]) {
			int a = RenderUtil.alpha(pixel);

			if (a == 0) {
				return pixel;
			}
			int brightness = brightnessData[pxCoord];
			int c = 0xa1bfff;
			if (brightness < minBrightness) {
				c = 0x85a9d4;
			} else if (isStripe(getX(pxCoord), getY(pxCoord))) {
				return calculateStripeColor(getX(pxCoord)) + 0xff000000;
			}
			// multiply in the color
			int r = RenderUtil.red(c);
			int b = RenderUtil.blue(c);
			int g = RenderUtil.green(c);

			r = mult(r, RenderUtil.red(pixel)) & 0xff;
			g = mult(g, RenderUtil.blue(pixel)) & 0xff;
			b = mult(b, RenderUtil.green(pixel)) & 0xff;

			// put it back together
			return RenderUtil.compose(r, g, b, a);

		}

		return pixel;
	}

	private boolean isStripe(double x, double y) {
		int z1 = (int) (0.5 * x + 0.25 * Math.sin(x));
		int z2 = (int) y;
		return (z1 - z2) % 6 == 0;
	}

	protected static int[] stripeColors = new int[] { 0xffffff, 0xdae7ff, 0xdae7ff, 0xdae7ff };

	private int calculateStripeColor(double x) {
		stripeColors = new int[] { 0xffffff, 0xdae7ff, 0xdae7ff, 0xdae7ff };

		float sin = (float) Math.sin(Math.PI * (x + state * 2) / 8);
		int color = Math.round(3 * sin * sin);
		return stripeColors[color];
	}
}
