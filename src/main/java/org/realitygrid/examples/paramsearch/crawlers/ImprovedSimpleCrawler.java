package org.realitygrid.examples.paramsearch.crawlers;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.Point3D;
import org.realitygrid.examples.paramsearch.Vector3D;

public class ImprovedSimpleCrawler extends SimpleCrawler {

	private int xLocation;
	private int yLocation;
	private int zLocation;
	
	public ImprovedSimpleCrawler(Domain d) {
		super("Improved Simple Crawler", d);
		
		xLocation = 0;
		yLocation = 0;
		zLocation = 0;
	}

	@Override
	public Point3D search() {
		int size = getDomainSize();
		Point3D test;
		boolean start = true;

		for(int i = 0; i < size; i++) {
			if(start) i = xLocation;
			for(int j = 0; j < size; j++) {
				if(start) j = yLocation;
				for(int k = 0; k < size; k++) {
					if(start) {
						k = zLocation;
						start = false;
					}
					test = new Point3D(i, j, k);
					if(testPoint(test).equals(Vector3D.ZERO)) {
						xLocation = i;
						yLocation = j;
						zLocation = k + 1;
						return test;
					}
				}
			}
		}
		
		xLocation = 0;
		yLocation = 0;
		zLocation = 0;
		return null;
	}

}
