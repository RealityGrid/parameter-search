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

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPoint3D {

	private static final double EPSILON = 0.000001;

	private Point3D p00;
	private Point3D p01;
	private Point3D p02;
	private Vector3D v00;
	private Vector3D v01;
	private Vector3D v02;

	@Before
	public void setUp() throws Exception {
		p00 = Point3D.ORIGIN;
		p01 = new Point3D(3, 4, 0);
		p02 = new Point3D(3, 4, 12);
		v00 = Vector3D.ZERO;
		v01 = new Vector3D(3, 4, 0);
		v02 = new Vector3D(3, 4, 12).normalize();
	}

	@After
	public void tearDown() throws Exception {
		p00 = null;
		p01 = null;
		p02 = null;
		v00 = null;
		v01 = null;
		v02 = null;
	}

	@Test
	public void testDistancePoint3D() {
		assertEquals(5.0, p00.distance(p01), EPSILON);
		assertEquals(13.0, p00.distance(p02), EPSILON);
	}

	@Test
	public void testGetVectorTo() {
		assertEquals(v00, p00.getVectorTo(p00));
		assertEquals(v01, p00.getVectorTo(p01));
	}

	@Test
	public void testGetNormalizedVectorTo() {
		assertEquals(v02, p00.getNormalizedVectorTo(p02));
	}
	
	@Test
	public void testMoveBy() {
		assertEquals(p00, p00.moveBy(v00));
		assertEquals(p01, p00.moveBy(v01));
	}
}
