package com.existingeevee.moretcon.other;

public class BiValue<A,B> {

	private A a;

	private B b;

	public BiValue(A a, B b){
		this.a = a;
		this.b = b;
	}

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return "BiVal(" + a + "," + b + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BiValue)) return super.equals(obj);
		BiValue<?, ?> dValObj = (BiValue<?, ?>) obj;
		return (dValObj.getA().equals(this.getA()) && dValObj.getB().equals(this.getB()));
	}
}
