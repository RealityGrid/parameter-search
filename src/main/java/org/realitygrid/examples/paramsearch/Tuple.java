/*
 * The RealityGrid Parameter Search Example Application
 *
 * Copyright (c) 2010, University of Manchester, United Kingdom.
 * All rights reserved.
 *
 * This software is produced by Research Computing Services, University
 * of Manchester as part of the RealityGrid project and associated
 * follow on projects, funded by the EPSRC under grants GR/R67699/01,
 * GR/R67699/02, GR/T27488/01, EP/C536452/1, EP/D500028/1,
 * EP/F00561X/1.
 *
 * LICENCE TERMS
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *
 *   * Neither the name of The University of Manchester nor the names
 *     of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written
 *     permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.realitygrid.examples.paramsearch;

import java.util.ArrayList;

/**
 * This class represents a tuple of arbitrary length. It is the base class of
 * the component-based classes in this package, {@link Point3D} and
 * {@link Vector3D}.
 * <p/>The component list is stored internally as an {@link ArrayList}.
 * @author Robert Haines
 *
 * @param <T> the type (extends {@link Number}) of the elements in this Tuple.
 */
public class Tuple<T extends Number> {
	/**
	 * The list of components that make up this tuple.
	 */
	ArrayList<T> components;

	/**
	 * Create a tuple using the supplied ArrayList as its component list.
	 * @param components the ArrayList of components to use.
	 */
	public Tuple(ArrayList<T> components) {
		this.components = components;
	}

	/**
	 * Create a tuple of length two with the specified components.
	 * @param x the first component.
	 * @param y the second component.
	 */
	public Tuple(T x, T y) {
		this.components = new ArrayList<T>(2);
		this.components.add(x);
		this.components.add(y);
	}

	/**
	 * Create a tuple of length three with the specified components.
	 * @param x the first component.
	 * @param y the second component.
	 * @param z the third component.
	 */
	public Tuple(T x, T y, T z) {
		this.components = new ArrayList<T>(3);
		this.components.add(x);
		this.components.add(y);
		this.components.add(z);
	}

	/**
	 * Create a tuple of length four with the specified components.
	 * @param x the first component.
	 * @param y the second component.
	 * @param z the third component.
	 * @param w the fourth component.
	 */
	public Tuple(T x, T y, T z, T w) {
		this.components = new ArrayList<T>(4);
		this.components.add(x);
		this.components.add(y);
		this.components.add(z);
		this.components.add(w);
	}

	/**
	 * Create a tuple from the supplied array. The items in the array are
	 * copied, in order, into the internal components ArrayList.
	 * @param array the array holding the required components.
	 */
	public Tuple(T[] array) {
		this.components = new ArrayList<T>(array.length);
		for (T a : array) {
			this.components.add(a);
		}
	}

	/**
	 * Get the length of this tuple.
	 * @return the length of this tuple.
	 */
	public int getLength() {
		return this.components.size();
	}

	/**
	 * Get the distance between this tuple and another. The other tuple can be
	 * of a different type and of a different length. All components are cast
	 * to doubles internally so that they are compatible for the distance
	 * calculation. If tuples of different lengths are specified then they are
	 * mapped into the larger tuple's dimensional space. I.e missing
	 * components are treated as being zero.
	 * @param <U> the type of the other tuple.
	 * @param t the other tuple.
	 * @return the distance between the two tuples.
	 */
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
