package com.existingeevee.math.number;

public class ComplexDouble extends Number implements Comparable<Number> {
	
	private static final long serialVersionUID = 432809482084L;
	
	private double real = 0;
	private double imaginary = 0;
	
	public ComplexDouble(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	@Override
	public int intValue() {
		return (int) real;
	}

	@Override
	public long longValue() {
		return (long) real;
	}

	@Override
	public float floatValue() {
		return (float) real;
	}

	@Override
	public double doubleValue() {
		return real;
	} 

	public int imaginaryIntValue() {
		return (int) imaginary;
	}

	public long imaginaryLongValue() {
		return (long) imaginary;
	}

	public float imaginaryFloatValue() {
		return (float) imaginary;
	}

	public double imaginaryDoubleValue() {
		return imaginary;
	}
	
	@Override
	public int compareTo(Number o) {
		return Double.compare(real, o.doubleValue());
	}
	
	public int compareToImaginary(ComplexDouble o) {
		return Double.compare(imaginary, o.imaginary);
	}
	
	public int compareToMagnitude(ComplexDouble o) {
		return Double.compare(imaginary * imaginary + real * real, o.imaginary * o.imaginary + o.real * o.real);
	}
}
