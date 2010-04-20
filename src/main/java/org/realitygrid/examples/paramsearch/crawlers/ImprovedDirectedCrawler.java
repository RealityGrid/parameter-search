package org.realitygrid.examples.paramsearch.crawlers;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.Point3D;
import org.realitygrid.examples.paramsearch.Vector3D;

public final class ImprovedDirectedCrawler extends DirectedCrawler {
	
	public ImprovedDirectedCrawler(Domain d) {
		super("Improved Directed Crawler", d);
	}

	@Override
	public Point3D search() {
		Vector3D next;
		
		do {
			next = testPoint(start);
			if(next.equals(Vector3D.ZERO)) {
				return start;
			}
			
			start = start.moveBy(squash(next));
		}
		while(true);
	}
}
