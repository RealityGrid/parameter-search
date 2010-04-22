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

public final class Point3D extends Tuple3<Integer> implements Comparable<Point3D> {

	public static final Point3D ORIGIN = new Point3D(0);

	public Point3D(Integer x, Integer y, Integer z) {
		super(x, y, z);
	}

	public Point3D(Tuple3<Integer> t) {
		super(t);
	}

	public Point3D(Integer c) {
		super(c);
	}

	public double distance(Point3D p) {
		return super.distance(p);
	}

	public Vector3D getVectorTo(Point3D p) {
		return new Vector3D(p.getX() - this.getX(), p.getY() - this.getY(),
				p.getZ() - this.getZ());
	}

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

	public Point3D moveBy(Vector3D v) {
		return new Point3D(getX() + v.getX().intValue(),
				getY() + v.getY().intValue(),
				getZ() + v.getZ().intValue());
	}
	
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
