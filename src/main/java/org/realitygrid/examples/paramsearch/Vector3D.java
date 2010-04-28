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
 * This class represents a vector in 3D space.
 * @author Robert Haines
 */
public final class Vector3D extends Tuple3<Double> {

	/**
	 * The zero vector, {@code (0, 0, 0)}.
	 */
	public static final Vector3D ZERO = new Vector3D(0.0);

	private double magnitude;

	/**
	 * Create a vector with the specified X, Y and Z components.
	 * @param x the X component.
	 * @param y the Y component.
	 * @param z the Z component.
	 */
	public Vector3D(Double x, Double y, Double z) {
		super(x, y, z);
		calcMagnitude();
	}

	/**
	 * Create a vector with its three components specified by the {@link Tuple3}.
	 * @param t the {@link Tuple3} holding the components to use.
	 */
	public Vector3D(Tuple3<Double> t) {
		super(t);
		calcMagnitude();
	}

	/**
	 * Create a vector with all components equal.
	 * @param c the single X, Y and Z component.
	 */
	public Vector3D(Double c) {
		super(c);
		calcMagnitude();
	}

	/**
	 * Create a vector with {@link Integer} components.
	 * @param x the X component.
	 * @param y the Y component.
	 * @param z the Z component.
	 */
	Vector3D(Integer x, Integer y, Integer z) {
		super(x.doubleValue(), y.doubleValue(), z.doubleValue());
		calcMagnitude();
	}

	private void calcMagnitude() {
		magnitude = Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ()));
	}

	/**
	 * Get the magnitude of this vector.
	 * @return the magnitude of this vector.
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Get the normalized form of this vector. A normalized vector is one which
	 * preserves the direction but has a magnitude of one. It is calculated by
	 * dividing each component of the original vector by its magnitude.
	 * @return a new vector which is the normalized form of this one.
	 */
	public Vector3D normalize() {
		return new Vector3D(getX() / magnitude, getY() / magnitude, getZ() / magnitude);
	}
}
