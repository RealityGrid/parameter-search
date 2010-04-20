package org.realitygrid.examples.paramsearch;

public final class Vector3D extends Tuple3<Double> {
	
	public static final Vector3D ZERO = new Vector3D(0.0);
	
	private double magnitude;

	public Vector3D(Double x, Double y, Double z) {
		super(x, y, z);
		calcMagnitude();
	}

	public Vector3D(Tuple3<Double> t) {
		super(t);
		calcMagnitude();
	}
	
	public Vector3D(Double c) {
		super(c);
		calcMagnitude();
	}
	
	Vector3D(Integer x, Integer y, Integer z) {
		super(x.doubleValue(), y.doubleValue(), z.doubleValue());
		calcMagnitude();
	}

	private void calcMagnitude() {
		magnitude = Math.sqrt((getX() * getX()) + (getY() * getY()) + (getZ() * getZ()));
	}

	public double getMagnitude() {
		return magnitude;
	}
	
	public Vector3D normalize() {
		return new Vector3D(getX() / magnitude, getY() / magnitude, getZ() / magnitude);
	}
}
