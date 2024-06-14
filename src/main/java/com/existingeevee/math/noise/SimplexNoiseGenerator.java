package com.existingeevee.math.noise;

import java.util.Random;

public class SimplexNoiseGenerator {
		
	private double scale;
	private double roughness;
	private int octaves;

	public SimplexNoiseGenerator(int octaves, double roughness, double scale) {
		this.octaves = octaves; // Number of Layers combined together to get a natural looking surface
		this.roughness = roughness; // Increasing the of the range between -1 and 1, causing higher values eg more
									// rough terrain
		this.scale = scale; // Overall scaling of the terrain
	}

	//WHAT THE FUCK DOWS THIS MEEEAANNNNN>>>>??>>>?>>?>
	public double generateOctavedSimplexNoise(int x, int y, long seed) {
		double totalNoise = 0;
		double layerFrequency = scale;
		double layerWeight = 1;

		for (int octave = 0; octave < octaves; octave++) {
			// Calculate single layer/octave of simplex noise, then add it to total noise
			totalNoise += SimplexNoise.noise(x * layerFrequency, y * layerFrequency, seed) * layerWeight;
			// Increase variables with each incrementing octave
			layerFrequency *= 2;
			layerWeight *= roughness;

		}
		return totalNoise;
	}

	public static class SimplexNoise { // Simplex noise in 2D, 3D and 4D
		private static Grad grad3[] = { new Grad(1, 1, 0), new Grad(-1, 1, 0), new Grad(1, -1, 0), new Grad(-1, -1, 0),
				new Grad(1, 0, 1), new Grad(-1, 0, 1), new Grad(1, 0, -1), new Grad(-1, 0, -1),
				new Grad(0, 1, 1), new Grad(0, -1, 1), new Grad(0, 1, -1), new Grad(0, -1, -1) };


		private static short p[] = { 151, 160, 137, 91, 90, 15,
				131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23,
				190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33,
				88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166,
				77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244,
				102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196,
				135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123,
				5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
				223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
				129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228,
				251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107,
				49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254,
				138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180 };
		// To remove the need for index wrapping, double the permutation table length
		private static short perm[] = new short[512];
		private static short permMod12[] = new short[512];
		static {
			for (int i = 0; i < 512; i++) {
				perm[i] = p[i & 255];
				permMod12[i] = (short) (perm[i] % 12);
			}
		}

		// Skewing and unskewing factors for 2, and 3 dimensions
		private static final double F2 = 0.5 * (Math.sqrt(3.0) - 1.0);
		private static final double G2 = (3.0 - Math.sqrt(3.0)) / 6.0;
		private static final double F3 = 1.0 / 3.0;
		private static final double G3 = 1.0 / 6.0;

		// This method is a *lot* faster than using (int)Math.floor(x)
		private static long fastfloor(double x) {
			long xi = (long) x;
			return x < xi ? xi - 1 : xi;
		}

		private static double dot(Grad g, double x, double y) {
			return g.x * x + g.y * y;
		}

		private static double dot(Grad g, double x, double y, double z) {
			return g.x * x + g.y * y + g.z * z;
		}

		// 2D simplex noise
		public static double noise(double xin, double yin, long seed) {
			//Janky addition. please forgive me - Eevee
			Random rand = new Random(seed);
			xin += rand.nextDouble() * 1000000000d;
			yin += rand.nextDouble() * 1000000000d;
			
			double n0, n1, n2; // Noise contributions from the three corners
			// Skew the input space to determine which simplex cell we're in
			double s = (xin + yin) * F2; // Hairy factor for 2D
			long i = fastfloor(xin + s);
			long j = fastfloor(yin + s); //ItemBow
			double t = (i + j) * G2;
			double X0 = i - t; // Unskew the cell origin back to (x,y) space
			double Y0 = j - t;
			double x0 = xin - X0; // The x,y distances from the cell origin
			double y0 = yin - Y0;
			// For the 2D case, the simplex shape is an equilateral triangle.
			// Determine which simplex we are in.
			int i1, j1; // Offsets for second (middle) corner of simplex in (i,j) coords
			if (x0 > y0) {
				i1 = 1;
				j1 = 0;
			} // lower triangle, XY order: (0,0)->(1,0)->(1,1)
			else {
				i1 = 0;
				j1 = 1;
			} // upper triangle, YX order: (0,0)->(0,1)->(1,1)
			// A step of (1,0) in (i,j) means a step of (1-c,-c) in (x,y), and
			// a step of (0,1) in (i,j) means a step of (-c,1-c) in (x,y), where
			// c = (3-sqrt(3))/6
			double x1 = x0 - i1 + G2; // Offsets for middle corner in (x,y) unskewed coords
			double y1 = y0 - j1 + G2;
			double x2 = x0 - 1.0 + 2.0 * G2; // Offsets for last corner in (x,y) unskewed coords
			double y2 = y0 - 1.0 + 2.0 * G2;
			// Work out the hashed gradient indices of the three simplex corners
			int ii = (int) (i & 255);
			int jj = (int) (j & 255);
			int gi0 = permMod12[ii + perm[jj]];
			int gi1 = permMod12[ii + i1 + perm[jj + j1]];
			int gi2 = permMod12[ii + 1 + perm[jj + 1]];
			// Calculate the contribution from the three corners
			double t0 = 0.5 - x0 * x0 - y0 * y0;
			if (t0 < 0)
				n0 = 0.0;
			else {
				t0 *= t0;
				n0 = t0 * t0 * dot(grad3[gi0], x0, y0); // (x,y) of grad3 used for 2D gradient
			}
			double t1 = 0.5 - x1 * x1 - y1 * y1;
			if (t1 < 0)
				n1 = 0.0;
			else {
				t1 *= t1;
				n1 = t1 * t1 * dot(grad3[gi1], x1, y1);
			}
			double t2 = 0.5 - x2 * x2 - y2 * y2;
			if (t2 < 0)
				n2 = 0.0;
			else {
				t2 *= t2;
				n2 = t2 * t2 * dot(grad3[gi2], x2, y2);
			}
			// Add contributions from each corner to get the final noise value.
			// The result is scaled to return values in the interval [-1,1].
			return 70.0 * (n0 + n1 + n2);
		}

		// 3D simplex noise
		public static double noise(double xin, double yin, double zin, long seed) {
			//Janky addition. please forgive me - Eevee
			Random rand = new Random(seed);
			xin += rand.nextDouble() * 1000000000d;
			yin += rand.nextDouble() * 1000000000d;
			zin += rand.nextDouble() * 1000000000d;
			
			double n0, n1, n2, n3; // Noise contributions from the four corners
			// Skew the input space to determine which simplex cell we're in
			double s = (xin + yin + zin) * F3; // Very nice and simple skew factor for 3D
			long i = fastfloor(xin + s);
			long j = fastfloor(yin + s);
			long k = fastfloor(zin + s);
			double t = (i + j + k) * G3;
			double X0 = i - t; // Unskew the cell origin back to (x,y,z) space
			double Y0 = j - t;
			double Z0 = k - t;
			double x0 = xin - X0; // The x,y,z distances from the cell origin
			double y0 = yin - Y0;
			double z0 = zin - Z0;
			// For the 3D case, the simplex shape is a slightly irregular tetrahedron.
			// Determine which simplex we are in.
			int i1, j1, k1; // Offsets for second corner of simplex in (i,j,k) coords
			int i2, j2, k2; // Offsets for third corner of simplex in (i,j,k) coords
			if (x0 >= y0) {
				if (y0 >= z0) {
					i1 = 1;
					j1 = 0;
					k1 = 0;
					i2 = 1;
					j2 = 1;
					k2 = 0;
				} // X Y Z order
				else if (x0 >= z0) {
					i1 = 1;
					j1 = 0;
					k1 = 0;
					i2 = 1;
					j2 = 0;
					k2 = 1;
				} // X Z Y order
				else {
					i1 = 0;
					j1 = 0;
					k1 = 1;
					i2 = 1;
					j2 = 0;
					k2 = 1;
				} // Z X Y order
			} else { // x0<y0
				if (y0 < z0) {
					i1 = 0;
					j1 = 0;
					k1 = 1;
					i2 = 0;
					j2 = 1;
					k2 = 1;
				} // Z Y X order
				else if (x0 < z0) {
					i1 = 0;
					j1 = 1;
					k1 = 0;
					i2 = 0;
					j2 = 1;
					k2 = 1;
				} // Y Z X order
				else {
					i1 = 0;
					j1 = 1;
					k1 = 0;
					i2 = 1;
					j2 = 1;
					k2 = 0;
				} // Y X Z order
			}
			// A step of (1,0,0) in (i,j,k) means a step of (1-c,-c,-c) in (x,y,z),
			// a step of (0,1,0) in (i,j,k) means a step of (-c,1-c,-c) in (x,y,z), and
			// a step of (0,0,1) in (i,j,k) means a step of (-c,-c,1-c) in (x,y,z), where
			// c = 1/6.
			double x1 = x0 - i1 + G3; // Offsets for second corner in (x,y,z) coords
			double y1 = y0 - j1 + G3;
			double z1 = z0 - k1 + G3;
			double x2 = x0 - i2 + 2.0 * G3; // Offsets for third corner in (x,y,z) coords
			double y2 = y0 - j2 + 2.0 * G3;
			double z2 = z0 - k2 + 2.0 * G3;
			double x3 = x0 - 1.0 + 3.0 * G3; // Offsets for last corner in (x,y,z) coords
			double y3 = y0 - 1.0 + 3.0 * G3;
			double z3 = z0 - 1.0 + 3.0 * G3;
			// Work out the hashed gradient indices of the four simplex corners
			int ii = (int) (i & 255);
			int jj = (int) (j & 255);
			int kk = (int) (k & 255);
			int gi0 = permMod12[ii + perm[jj + perm[kk]]];
			int gi1 = permMod12[ii + i1 + perm[jj + j1 + perm[kk + k1]]];
			int gi2 = permMod12[ii + i2 + perm[jj + j2 + perm[kk + k2]]];
			int gi3 = permMod12[ii + 1 + perm[jj + 1 + perm[kk + 1]]];
			// Calculate the contribution from the four corners
			double t0 = 0.6 - x0 * x0 - y0 * y0 - z0 * z0;
			if (t0 < 0)
				n0 = 0.0;
			else {
				t0 *= t0;
				n0 = t0 * t0 * dot(grad3[gi0], x0, y0, z0);
			}
			double t1 = 0.6 - x1 * x1 - y1 * y1 - z1 * z1;
			if (t1 < 0)
				n1 = 0.0;
			else {
				t1 *= t1;
				n1 = t1 * t1 * dot(grad3[gi1], x1, y1, z1);
			}
			double t2 = 0.6 - x2 * x2 - y2 * y2 - z2 * z2;
			if (t2 < 0)
				n2 = 0.0;
			else {
				t2 *= t2;
				n2 = t2 * t2 * dot(grad3[gi2], x2, y2, z2);
			}
			double t3 = 0.6 - x3 * x3 - y3 * y3 - z3 * z3;
			if (t3 < 0)
				n3 = 0.0;
			else {
				t3 *= t3;
				n3 = t3 * t3 * dot(grad3[gi3], x3, y3, z3);
			}
			// Add contributions from each corner to get the final noise value.
			// The result is scaled to stay just inside [-1,1]
			return 32.0 * (n0 + n1 + n2 + n3);
		}
	}
	private static class Grad {
		double x, y, z;

		Grad(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
