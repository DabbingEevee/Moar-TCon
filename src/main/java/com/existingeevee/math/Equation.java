package com.existingeevee.math;

import java.util.function.DoubleFunction;
import java.util.function.LongFunction;

public abstract class Equation implements DoubleFunction<Double>, LongFunction<Long> {

	public abstract double evaluate(double x);

	public long evaluate(long x) {
		return (long) evaluate((double) x);
	}

	@Override
	public final Long apply(long value) {
		return evaluate(value);
	}

	@Override
	public final Double apply(double value) {
		return evaluate(value);
	}
}
