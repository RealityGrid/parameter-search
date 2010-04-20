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
