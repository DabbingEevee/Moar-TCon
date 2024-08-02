package com.existingeevee.math;

import net.minecraft.util.math.Vec3d;

public final class Quaternion {
	public static final Quaternion ONE = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
	private float i;
	private float j;
	private float k;
	private float r;

	public Quaternion(float pI, float pJ, float pK, float pR) {
		i = pI;
		j = pJ;
		k = pK;
		r = pR;
	}

	public Quaternion(Vec3d pRotationAxis, float pRotationAngle, boolean pDegrees) {
		if (pDegrees) {
			pRotationAngle *= ((float) Math.PI / 180F);
		}

		float f = sin(pRotationAngle / 2.0F);
		i = (float) pRotationAxis.x * f;
		j = (float) pRotationAxis.y * f;
		k = (float) pRotationAxis.z * f;
		r = cos(pRotationAngle / 2.0F);
	}

	public Quaternion(float pX, float pY, float pZ, boolean pDegrees) {
		if (pDegrees) {
			pX *= ((float) Math.PI / 180F);
			pY *= ((float) Math.PI / 180F);
			pZ *= ((float) Math.PI / 180F);
		}

		float f = sin(0.5F * pX);
		float f1 = cos(0.5F * pX);
		float f2 = sin(0.5F * pY);
		float f3 = cos(0.5F * pY);
		float f4 = sin(0.5F * pZ);
		float f5 = cos(0.5F * pZ);
		i = f * f3 * f5 + f1 * f2 * f4;
		j = f1 * f2 * f5 - f * f3 * f4;
		k = f * f2 * f5 + f1 * f3 * f4;
		r = f1 * f3 * f5 - f * f2 * f4;
	}

	public Quaternion(Quaternion pOther) {
		i = pOther.i;
		j = pOther.j;
		k = pOther.k;
		r = pOther.r;
	}

	public static Quaternion fromYXZ(float pY, float pX, float pZ) {
		Quaternion quaternion = ONE.copy();
		quaternion.mul(new Quaternion(0.0F, (float) Math.sin(pY / 2.0F), 0.0F, (float) Math.cos(pY / 2.0F)));
		quaternion.mul(new Quaternion((float) Math.sin(pX / 2.0F), 0.0F, 0.0F, (float) Math.cos(pX / 2.0F)));
		quaternion.mul(new Quaternion(0.0F, 0.0F, (float) Math.sin(pZ / 2.0F), (float) Math.cos(pZ / 2.0F)));
		return quaternion;
	}

	public static Quaternion fromXYZDegrees(Vec3d pDegreesVector) {
		return fromXYZ((float) Math.toRadians(pDegreesVector.x), (float) Math.toRadians(pDegreesVector.y), (float) Math.toRadians(pDegreesVector.z));
	}

	public static Quaternion fromXYZ(Vec3d pRadiansVector) {
		return fromXYZ((float) pRadiansVector.x, (float) pRadiansVector.y, (float) pRadiansVector.z);
	}

	public static Quaternion fromXYZ(float pX, float pY, float pZ) {
		Quaternion quaternion = ONE.copy();
		quaternion.mul(new Quaternion((float) Math.sin(pX / 2.), 0.0F, 0.0F, (float) Math.cos(pX / 2.)));
		quaternion.mul(new Quaternion(0.0F, (float) Math.sin(pY / 2.), 0.0F, (float) Math.cos(pY / 2.)));
		quaternion.mul(new Quaternion(0.0F, 0.0F, (float) Math.sin(pZ / 2.), (float) Math.cos(pZ / 2.)));
		return quaternion;
	}

	public Vec3d toXYZ() {
		float f = r() * r();
		float f1 = i() * i();
		float f2 = j() * j();
		float f3 = k() * k();
		float f4 = f + f1 + f2 + f3;
		float f5 = 2.0F * r() * i() - 2.0F * j() * k();
		float f6 = (float) Math.asin(f5 / f4);
		return Math.abs(f5) > 0.999F * f4 ? new Vec3d(2.0F * Math.atan2(i(), r()), f6, 0.0F) : new Vec3d(Math.atan2((2.0F * j() * k() + 2.0F * i() * r()), f - f1 - f2 + f3), f6, Math.atan2(2.0F * i() * j() + 2.0F * r() * k(), f + f1 - f2 - f3));
	}

	public Vec3d toXYZDegrees() {
		Vec3d vector3f = toXYZ();
		return new Vec3d(Math.toDegrees(vector3f.x), (float) Math.toDegrees(vector3f.y), (float) Math.toDegrees(vector3f.z));
	}

	public Vec3d toYXZ() {
		float f = r() * r();
		float f1 = i() * i();
		float f2 = j() * j();
		float f3 = k() * k();
		float f4 = f + f1 + f2 + f3;
		float f5 = 2.0F * r() * i() - 2.0F * j() * k();
		float f6 = (float) Math.asin(f5 / f4);
		return Math.abs(f5) > 0.999F * f4 ? new Vec3d(f6, 2.0F * Math.atan2(j(), r()), 0.0F) : new Vec3d(f6, Math.atan2((2.0F * i() * k() + 2.0F * j() * r()), f - f1 - f2 + f3), Math.atan2(2.0F * i() * j() + 2.0F * r() * k(), f - f1 + f2 - f3));
	}

	public Vec3d toYXZDegrees() {
		Vec3d vector3f = toYXZ();
		return new Vec3d(Math.toDegrees(vector3f.x), Math.toDegrees(vector3f.y), Math.toDegrees(vector3f.z));
	}

	@Override
	public boolean equals(Object pOther) {
		if (this == pOther) {
			return true;
		} else if (pOther != null && getClass() == pOther.getClass()) {
			Quaternion quaternion = (Quaternion) pOther;
			if (Float.compare(quaternion.i, i) != 0) {
				return false;
			} else if (Float.compare(quaternion.j, j) != 0) {
				return false;
			} else if (Float.compare(quaternion.k, k) != 0) {
				return false;
			} else {
				return Float.compare(quaternion.r, r) == 0;
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int r = Float.floatToIntBits(i);
		r = 31 * r + Float.floatToIntBits(j);
		r = 31 * r + Float.floatToIntBits(k);
		return 31 * r + Float.floatToIntBits(r);
	}

	@Override
	public String toString() {
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Quaternion[").append(r()).append(" + ");
		stringbuilder.append(i()).append("i + ");
		stringbuilder.append(j()).append("j + ");
		stringbuilder.append(k()).append("k]");
		return stringbuilder.toString();
	}

	public float i() {
		return i;
	}

	public float j() {
		return j;
	}

	public float k() {
		return k;
	}

	public float r() {
		return r;
	}

	public void mul(Quaternion pOther) {
		float f = i();
		float f1 = j();
		float f2 = k();
		float f3 = r();
		float f4 = pOther.i();
		float f5 = pOther.j();
		float f6 = pOther.k();
		float f7 = pOther.r();
		i = f3 * f4 + f * f7 + f1 * f6 - f2 * f5;
		j = f3 * f5 - f * f6 + f1 * f7 + f2 * f4;
		k = f3 * f6 + f * f5 - f1 * f4 + f2 * f7;
		r = f3 * f7 - f * f4 - f1 * f5 - f2 * f6;
	}

	public void mul(float pMultiplier) {
		i *= pMultiplier;
		j *= pMultiplier;
		k *= pMultiplier;
		r *= pMultiplier;
	}

	public void conj() {
		i = -i;
		j = -j;
		k = -k;
	}

	public void set(float pI, float pJ, float pK, float pR) {
		i = pI;
		j = pJ;
		k = pK;
		r = pR;
	}

	private static float cos(float pAngle) {
		return (float) Math.cos(pAngle);
	}

	private static float sin(float pAngle) {
		return (float) Math.sin(pAngle);
	}

	public void normalize() {
		float f = i() * i() + j() * j() + k() * k() + r() * r();
		if (f > 1.0E-6F) {
			float f1 = invSqrt(f);
			i *= f1;
			j *= f1;
			k *= f1;
			r *= f1;
		} else {
			i = 0.0F;
			j = 0.0F;
			k = 0.0F;
			r = 0.0F;
		}

	}

	public static float invSqrt(float x) {
		float xhalf = 0.5f * x;
		int i = Float.floatToIntBits(x);
		i = 0x5f3759df - (i >> 1);
		x = Float.intBitsToFloat(i);
		x *= (1.5f - xhalf * x * x);
		return x;
	}

	public Vec3d transformVector(Vec3d vec) {
		Quaternion quaternion = new Quaternion(this);
		quaternion.mul(new Quaternion((float) vec.x, (float) vec.y, (float) vec.z, 0.0F));
		Quaternion quaternion1 = new Quaternion(this);
		quaternion1.conj();
		quaternion.mul(quaternion1);
		return new Vec3d(quaternion.i(), quaternion.j(), quaternion.k());
	}

	public void slerp(Quaternion pOther, float pPercentage) {
		throw new UnsupportedOperationException();
	}

	public Quaternion copy() {
		return new Quaternion(this);
	}
}