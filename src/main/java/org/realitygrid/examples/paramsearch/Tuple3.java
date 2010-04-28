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

/**
 * This class specialises {@link Tuple} to have three components. It is a
 * convenient intermediate class between {@link Tuple} and {@link Point3D}
 * and {@link Vector3D}.
 * @author Robert Haines
 */
public class Tuple3<T extends Number> extends Tuple<T> {

	/**
	 * Create a Tuple3 from another.
	 * @param t the tuple to copy.
	 */
	public Tuple3(Tuple3<T> t) {
		super(t.components);
	}

	/**
	 * Create a Tuple3 with the specified components.
	 * @param x the first component.
	 * @param y the second component.
	 * @param z the third component.
	 */
	public Tuple3(T x, T y, T z) {
		super(x, y, z);
	}

	/**
	 * Create a Tuple3 with all components equal.
	 * @param c the single value to use for all three components.
	 */
	public Tuple3(T c) {
		super(c, c, c);
	}

	/**
	 * Get the first component.
	 * @return the first component.
	 */
	public T getX() {
		return this.components.get(0);
	}

	/**
	 * Get the second component.
	 * @return the second component.
	 */
	public T getY() {
		return this.components.get(1);
	}

	/**
	 * Get the third component.
	 * @return the third component.
	 */
	public T getZ() {
		return this.components.get(2);
	}

	/**
	 * Get the distance between this tuple and another.
	 * @param t the other tuple.
	 * @return the distance between the two tuples.
	 */
	public <U extends Number> double distance(Tuple3<U> t) {
		return super.distance(t);
	}

	@Override
	public String toString() {
		return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
	}
}
