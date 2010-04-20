package org.realitygrid.examples.paramsearch;

import java.util.ArrayList;

public class Tuple<T extends Number> {
	ArrayList<T> components;

	public Tuple(ArrayList<T> components) {
		this.components = components;
	}

	public Tuple(T x, T y) {
		this.components = new ArrayList<T>(2);
		this.components.add(x);
		this.components.add(y);
	}

	public Tuple(T x, T y, T z) {
		this.components = new ArrayList<T>(3);
		this.components.add(x);
		this.components.add(y);
		this.components.add(z);
	}

	public Tuple(T x, T y, T z, T w) {
		this.components = new ArrayList<T>(4);
		this.components.add(x);
		this.components.add(y);
		this.components.add(z);
		this.components.add(w);
	}

	public Tuple(T[] array) {
		this.components = new ArrayList<T>(array.length);
		for (T a : array) {
			this.components.add(a);
		}
	}

	public int getLength() {
		return this.components.size();
	}

	public <U extends Number> double distance(Tuple<U> t) {
		double a = 0.0;
		int num = Math.max(this.components.size(), t.getLength());
		ArrayList<Double> d = new ArrayList<Double>(num);
		for (int i = 0; i < num; i++) {
			if (this.components.size() <= i)
				d.add(t.components.get(i).doubleValue());
			else if (t.getLength() <= i)
				d.add(this.components.get(i).doubleValue());
			else
				d.add(t.components.get(i).doubleValue()
						- this.components.get(i).doubleValue());
		}

		for (double s : d) {
			a += (s * s);
		}

		return Math.sqrt(a);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for (T c : components) {
			result += prime * (c.intValue() + 1);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Tuple)) {
			return false;
		}
		Tuple other = (Tuple) obj;
		if (components == null) {
			if (other.components != null) {
				return false;
			}
		} else if (components.size() != other.components.size()) {
			return false;
		}

		for (int i = 0; i < components.size(); i++)
			if (components.get(i).doubleValue() != ((Number) other.components
					.get(i)).doubleValue())
				return false;

		return true;
	}

	@Override
	public String toString() {
		return "Tuple [components=" + components + "]";
	}
}
