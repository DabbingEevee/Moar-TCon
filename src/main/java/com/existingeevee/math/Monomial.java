package com.existingeevee.math;

public class Monomial extends Equation {

	private double multiplier = 0;
	private double exponential = 0;
	
	public Monomial(double multiplier, double exponential) {
		this.multiplier = multiplier;
		this.exponential = exponential;
	}

	@Override
	public double evaluate(double x) {
		return Math.pow(x, this.exponential) * multiplier;
	}

	
	@Override
	public long evaluate(long x) {
		return (long) (Math.pow(x, this.exponential) * multiplier);
	}
	
	public static Monomial valueOf(double val) {
		return new Monomial(val, 0);
	}
	
}
