package org.realitygrid.examples.paramsearch.crawlers;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.Point3D;
import org.realitygrid.examples.paramsearch.Vector3D;

public class DirectedCrawler extends AbstractCrawler {

	protected Point3D start;

	public DirectedCrawler(String s, Domain d) {
		super(s, d);
		start = new Point3D(0);	
	}
	public DirectedCrawler(Domain d) {
		this("Directed Crawler", d);
	}

	@Override
	public Point3D search() {
		Vector3D next;
		
		do {
			next = testPoint(start);
			if(next.equals(Vector3D.ZERO)) {
				return start;
			}
			
			next = squash(next);
			
			if(next.getX() != 0.0)
				start = new Point3D(start.getX() + next.getX().intValue(), start.getY(), start.getZ());
			else if(next.getY() != 0.0)
				start = new Point3D(start.getX(), start.getY() + next.getY().intValue(), start.getZ());
			else
				start = new Point3D(start.getX(), start.getY(), start.getZ() + next.getZ().intValue());
		}
		while(true);
	}
	
	protected Vector3D squash(Vector3D v) {
		double x = (v.getX() == 0.0) ? 0.0 : v.getX()/Math.abs(v.getX());
		double y = (v.getY() == 0.0) ? 0.0 : v.getY()/Math.abs(v.getY());
		double z = (v.getZ() == 0.0) ? 0.0 : v.getZ()/Math.abs(v.getZ());
		
		return new Vector3D(x, y, z);
	}
}
