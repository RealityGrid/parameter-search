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
