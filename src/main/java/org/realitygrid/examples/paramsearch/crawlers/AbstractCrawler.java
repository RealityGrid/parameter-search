package org.realitygrid.examples.paramsearch.crawlers;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.Point3D;
import org.realitygrid.examples.paramsearch.Vector3D;
import org.realitygrid.examples.paramsearch.crawlers.view.MainWindow;

public abstract class AbstractCrawler {

	private MainWindow view;
	private Domain domain;
	private boolean interactive;
	private String name;

	public AbstractCrawler(String s, Domain d, boolean b) {
		view = null;
		name = s;
		domain = d;
		interactive = b;
	}
	
	public AbstractCrawler(String s, Domain d) {
		this(s, d, false);
	}
	
	public String getName() {
		return name;
	}

	public int getDomainSize() {
		return domain.getSize();
	}
	
	public void setView(MainWindow view) {
		this.view = view;
	}

	public boolean isInteractive() {
		return interactive;
	}

	public void run() {
		int found = 0;
		
		do {
			Point3D result = search();
			if(result != null) {
				System.out.println("Found target at " + result);
				found++;
			}
		}
		while(found < domain.getNumTargets());
	}
		
	protected Vector3D testPoint(Point3D p) {
		Point3D test = domain.isWithinError(p);
		if(test != null) {
			updateView(test, true);
			return Vector3D.ZERO;
		}
		else {
			updateView(p, false);
			return domain.search(p);
		}
	}
	
	private void updateView(Point3D p, boolean found) {
		if(this.view != null) {
			view.showPoint(p, found);
		}
	}
	
	public abstract Point3D search();
}
