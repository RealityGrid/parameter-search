package org.realitygrid.examples.paramsearch.crawlers;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.Point3D;
import org.realitygrid.examples.paramsearch.Vector3D;

public class InteractiveCrawler extends AbstractCrawler {

	private static final int SLEEP_TIME = 1000;

	private ConcurrentLinkedQueue<Point3D> pointQueue;

	public InteractiveCrawler(String s, Domain d) {
		super(s, d, true);
		
		pointQueue = new ConcurrentLinkedQueue<Point3D>();
	}

	public void queue(Point3D p) {
		pointQueue.add(p);
	}
	
	@Override
	public Point3D search() {
		Point3D p = pointQueue.poll();
		
		if(p == null) {
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				// don't care!
			}
			
			return null;
		}
		
		System.out.print("Testing " + p + "...");
		Vector3D next = testPoint(p);
		boolean result = next.equals(Vector3D.ZERO);
		System.out.println(result ? "  Yes!" : "  Try again.");

		return result ? p : null;
	}
}
