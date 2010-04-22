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

public class TestTuple {

	private static final double EPSILON = 0.000001;
	
	private Tuple<Integer> iTuple00;
	private Tuple<Integer> iTuple01;
	private Tuple<Integer> iTuple02;
	private Tuple<Double>  dTuple00;
	private Tuple<Double>  dTuple01;
	private Tuple<Double>  dTuple02;
	
	@Before
	public void setUp() throws Exception {
		iTuple00 = new Tuple<Integer>(0, 0, 0);
		iTuple01 = new Tuple<Integer>(3, 4, 0);
		iTuple02 = new Tuple<Integer>(3, 4, 12);
		dTuple00 = new Tuple<Double>(0.0, 0.0, 0.0);
		dTuple01 = new Tuple<Double>(3.0, 4.0);
		dTuple02 = new Tuple<Double>(3.0, 4.0, 12.0);
	}

	@After
	public void tearDown() throws Exception {
		iTuple00 = null;
		iTuple01 = null;
		iTuple02 = null;
		dTuple00 = null;
		dTuple01 = null;
		dTuple02 = null;
	}

	@Test
	public void testHashCode() {
		assertEquals(iTuple00.hashCode(), dTuple00.hashCode());
		assertFalse(iTuple01.hashCode() == dTuple01.hashCode());
	}

	@Test
	public void testGetLength() {
		assertEquals(iTuple00.getLength(), 3);
		assertEquals(dTuple01.getLength(), 2);
	}

	@Test
	public void testDistance() {
		assertEquals(5.0, iTuple00.distance(iTuple01), EPSILON);
		assertEquals(13.0, iTuple00.distance(iTuple02), EPSILON);
		assertEquals(5.0, dTuple00.distance(dTuple01), EPSILON);
		assertEquals(dTuple00.distance(dTuple01), dTuple01.distance(dTuple00), EPSILON);
		assertEquals(13.0, iTuple00.distance(dTuple02), EPSILON);
	}

	@Test
	public void testEqualsObject() {
		assertTrue(iTuple00.equals(dTuple00));
		assertFalse(iTuple01.equals(dTuple01));
	}

}
