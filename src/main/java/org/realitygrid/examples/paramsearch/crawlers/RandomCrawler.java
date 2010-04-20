package org.realitygrid.examples.paramsearch.crawlers;

import java.util.Random;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.Point3D;
import org.realitygrid.examples.paramsearch.Vector3D;

public class RandomCrawler extends AbstractCrawler {

	private Random random;
	private final int size;
	
	public RandomCrawler(Domain d) {
		super("Random Crawler", d);
		random = new Random();
		size = d.getSize();
	}

	@Override
	public Point3D search() {
		int x, y, z;
		Point3D test;
		Vector3D next;
		
		do {
			x = random.nextInt(size);
			y = random.nextInt(size);
			z = random.nextInt(size);
			test = new Point3D(x, y, z);
			next = testPoint(test);
		}
		while(!next.equals(Vector3D.ZERO));
		
		return test;
	}
}
