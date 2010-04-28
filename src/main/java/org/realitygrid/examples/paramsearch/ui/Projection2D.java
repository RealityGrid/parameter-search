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

package org.realitygrid.examples.paramsearch.ui;

import java.awt.Point;

import org.realitygrid.examples.paramsearch.Point3D;

/**
 * This enumerated type represents the three standard projections from 3D space
 * to 2D space.
 * @author Robert Haines
 */
public enum Projection2D {
	/**
	 * The "front" projection.
	 */
	XY("Front", "X", "Y"),
	/**
	 * The "top" projection.
	 */
	XZ("Top", "X", "Z"),
	/**
	 * The "side" projection.
	 */
	ZY("Side", "Z", "Y");

	private final String title;
	private final String x;
	private final String y;

	Projection2D(String t, String x, String y) {
		this.title = t;
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the title of this projection.
	 * @return the title of this projection.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Get the X axis label of this projection.
	 * @return the X axis label of this projection.
	 */
	public String getX() {
		return x;
	}

	/**
	 * Get the Y axis label of this projection.
	 * @return the Y axis label of this projection.
	 */
	public String getY() {
		return y;
	}

	/**
	 * Project a 3D point into this 2D projection.
	 * @param p the 3D point to project.
	 * @return the resultant 2D point.
	 */
	public Point projectPoint(Point3D p) {
		int x = (this.x.equals("X")) ? p.getX() : p.getZ();
		int y = (this.y.equals("Y")) ? p.getY() : p.getZ();
		return new Point(x, y);
	}
}
