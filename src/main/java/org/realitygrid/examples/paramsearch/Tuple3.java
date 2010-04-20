package org.realitygrid.examples.paramsearch;

public class Tuple3<T extends Number> extends Tuple<T> {

	public Tuple3(Tuple3<T> t) {
		super(t.components);
	}

	public Tuple3(T x, T y, T z) {
		super(x, y, z);
	}

	public Tuple3(T c) {
		super(c, c, c);
	}

	public T getX() {
		return this.components.get(0);
	}

	public T getY() {
		return this.components.get(1);
	}

	public T getZ() {
		return this.components.get(2);
	}

	public <U extends Number> double distance(Tuple3<U> t) {
		return super.distance(t);
	}
	
	@Override
	public String toString() {
		return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
	}
}
