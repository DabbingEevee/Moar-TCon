package com.existingeevee.moretcon.client.textures;

import java.awt.image.DirectColorModel;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.client.MaterialRenderInfo.AbstractMaterialRenderInfo;
import slimeknights.tconstruct.library.client.RenderUtil;
import slimeknights.tconstruct.library.client.texture.AbstractColoredTexture;

//This is hell im sure theres a better way to do this
//buuuuut idk what im doing lmfao
public class SanguiseeliumTexture extends AbstractColoredTexture {
	boolean[] blank;
	boolean[] border;
	boolean[] onTop;
	int minBrightness;
	int maxBrightness;
	int brightnessData[];

	protected SanguiseeliumTexture(ResourceLocation baseTextureLocation, String spriteName) {
		super(baseTextureLocation, spriteName);
	}

	public static class RenderInfo extends AbstractMaterialRenderInfo {

		@Override
		public TextureAtlasSprite getTexture(ResourceLocation baseTexture, String location) {
			SanguiseeliumTexture texture = new SanguiseeliumTexture(baseTexture, location);
			return texture;
		}
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
	protected int colorPixel(int pixel, int pxCoord) {
		if (!blank[pxCoord]) {
			int a = RenderUtil.alpha(pixel);
			if (border[pxCoord]) {
				return RenderUtil.compose(25, 4, 4, a);
			} else {

				if (a == 0) {
					return pixel;
				}
				int brightness = brightnessData[pxCoord];
				int c = isStripe(getX(pxCoord), getY(pxCoord)) ? 0xff8787 : 0x6b1313;
				if (brightness < minBrightness) {
					c = 0x340909;
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
		}

		return pixel;
	}

	private boolean isStripe(double x, double y) {
		int z1 = (int) (0.5 * x + 0.25 * Math.sin(x));
		int z2 = (int) y;
		return (z1 - z2) % 4 == 0;
	}
}
