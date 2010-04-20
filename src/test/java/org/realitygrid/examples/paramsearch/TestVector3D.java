package org.realitygrid.examples.paramsearch;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestVector3D {

	private static final double EPSILON = 0.000001;

	private Vector3D v00;
	private Vector3D v01;
	private Vector3D v02;
	
	@Before
	public void setUp() throws Exception {
		v00 = Vector3D.ZERO;
		v01 = new Vector3D(3, 4, 0);
		v02 = new Vector3D(3.0/5.0, 4.0/5.0, 0.0);
	}

	@After
	public void tearDown() throws Exception {
		v00 = null;
		v01 = null;
		v02 = null;
	}

	@Test
	public void testGetMagnitude() {
		assertEquals(0.0, v00.getMagnitude(), EPSILON);
		assertEquals(5.0, v01.getMagnitude(), EPSILON);
	}

	@Test
	public void testNormalize() {
		assertEquals(v02, v01.normalize());
	}

}
