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
