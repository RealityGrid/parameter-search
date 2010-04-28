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
 * This class represents a point in integer 3D space.
 * @author Robert Haines
 */
public final class Point3D extends Tuple3<Integer> implements Comparable<Point3D> {

	/**
	 * The point at the origin {@code (0, 0, 0)}.
	 */
	public static final Point3D ORIGIN = new Point3D(0);

	/**
	 * Create a new point at the coordinates specified.
	 * @param x the X coordinate.
	 * @param y the Y coordinate.
	 * @param z the Z coordinate.
	 */
	public Point3D(Integer x, Integer y, Integer z) {
		super(x, y, z);
	}

	/**
	 * Create a new point at the coordinates specified by those in the {@link Tuple3}.
	 * @param t the {@link Tuple3} holding the coordinates to use.
	 */
	public Point3D(Tuple3<Integer> t) {
		super(t);
	}

	/**
	 * Create a new point with all three coordinates the same.
	 * @param c the single X, Y and Z coordinate.
	 */
	public Point3D(Integer c) {
		super(c);
	}

	/**
	 * Find the distance from this point to another specified point.
	 * @param p the point whose distance from this one is required.
	 * @return the distance between the two points.
	 */
	public double distance(Point3D p) {
		return super.distance(p);
	}

	/**
	 * Get the vector from this point to another specified point.
	 * @param p the point to find the vector to.
	 * @return the vector from this point to the other.
	 * @see #getNormalizedVectorTo(Point3D)
	 */
	public Vector3D getVectorTo(Point3D p) {
		return new Vector3D(p.getX() - this.getX(), p.getY() - this.getY(),
				p.getZ() - this.getZ());
	}

	/**
	 * Get the normalized vector from this point to another specified point.
	 * @param p the point to find the normalized vector to.
	 * @return the normalized vector from this point to the other.
	 * @see #getVectorTo(Point3D)
	 */
	public Vector3D getNormalizedVectorTo(Point3D p) {
		Vector3D result = new Vector3D(p.getX() - this.getX(), p.getY()
				- this.getY(), p.getZ() - this.getZ());
		return result.normalize();
	}

	@Override
	public int compareTo(Point3D o) {
		if(getX() != o.getX()) {
			return getX() - o.getX();
		}
		else if(getY() != o.getY()) {
			return getY() - o.getY();
		}
		else if(getZ() != o.getZ()) {
			return getZ() - o.getZ();
		}

		return 0;
	}

	/**
	 * Find the point arrived at by moving from this one by the specified
	 * vector.
	 * @param v the vector to move by.
	 * @return the new point after moving by the specified vector.
	 */
	public Point3D moveBy(Vector3D v) {
		return new Point3D(getX() + v.getX().intValue(),
				getY() + v.getY().intValue(),
				getZ() + v.getZ().intValue());
	}

	/**
	 * Test to see if the specified point is within a certain distance of
	 * another one. <strong>This method tests distance along each axis, not straight
	 * line distance!</strong>
	 * @param d the distance within which the two points must be.
	 * @param p the other point.
	 * @return whether the two points are within the specified distance.
	 */
	public boolean isWithin(int d, Point3D p) {
		if(Math.abs(this.getX() - p.getX()) > d)
			return false;

		if(Math.abs(this.getY() - p.getY()) > d)
			return false;

		if(Math.abs(this.getZ() - p.getZ()) > d)
			return false;

		return true;
	}
}
