package com.existingeevee.math;

public abstract class Equation {

	public abstract double evaluate(double x);
	
	public long evaluate(long x) {
		return (long) evaluate((double) x);
	}
}
