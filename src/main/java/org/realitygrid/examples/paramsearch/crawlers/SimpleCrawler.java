package org.realitygrid.examples.paramsearch.crawlers;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.Point3D;
import org.realitygrid.examples.paramsearch.Vector3D;

public class SimpleCrawler extends AbstractCrawler {

	public SimpleCrawler(String s, Domain d) {
		super(s, d);
	}

	public SimpleCrawler(Domain d) {
		this("Simple Crawler", d);
	}

	@Override
	public Point3D search() {
		int size = getDomainSize();
		Point3D test;
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				for(int k = 0; k < size; k++) {
					test = new Point3D(i, j, k);
					if(testPoint(test).equals(Vector3D.ZERO))
						return test;
				}
			}
		}
		
		return null;
	}
}
