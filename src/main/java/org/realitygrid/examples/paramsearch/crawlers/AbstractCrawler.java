/*
 * The RealityGrid Parameter Search Example Application
 *
 * Copyright (c) 2010, University of Manchester, United Kingdom.
 * All rights reserved.
 *
 * This software is produced by Research Computing Services, University
 * of Manchester as part of the RealityGrid project and associated
 * follow on projects, funded by the EPSRC under grants GR/R67699/01,
 * GR/R67699/02, GR/T27488/01, EP/C536452/1, EP/D500028/1,
 * EP/F00561X/1.
 *
 * LICENCE TERMS
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *
 *   * Neither the name of The University of Manchester nor the names
 *     of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written
 *     permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.realitygrid.examples.paramsearch.crawlers;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.Point3D;
import org.realitygrid.examples.paramsearch.Vector3D;
import org.realitygrid.examples.paramsearch.ui.MainWindow;

/**
 * This is the base-class of the set of crawlers. A crawler implements a search
 * strategy to find targets within a domain. It "crawls" over the domain trying
 * to find targets.
 * <p/>Crawlers may be interactive (human driven) or not (entirely machine
 * driven).
 * @author Robert Haines
 */
public abstract class AbstractCrawler {

	private MainWindow view;
	private Domain domain;
	private boolean interactive;
	private String name;

	/**
	 * Create a crawler with the specified name, domain and interactive status.
	 * @param s the name (type) of this crawler.
	 * @param d the domain to search.
	 * @param b whether the crawler is to be interactive.
	 */
	public AbstractCrawler(String s, Domain d, boolean b) {
		view = null;
		name = s;
		domain = d;
		interactive = b;
	}
	
	/**
	 * Create a non-interactive crawler with the specified name and domain.
	 * @param s the name (type) of this crawler.
	 * @param d the domain to search.
	 */
	public AbstractCrawler(String s, Domain d) {
		this(s, d, false);
	}
	
	/**
	 * Get the name of the domain.
	 * @return the name of the domain.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the size (side length) of the domain.
	 * @return the size of the domain.
	 */
	public int getDomainSize() {
		return domain.getSize();
	}
	
	/**
	 * Register a viewer GUI with this crawler. If the crawler has a GUI then
	 * it can be observed searching for the targets.
	 * @param view the viewing GUI to attach to this crawler.
	 */
	public void setView(MainWindow view) {
		this.view = view;
	}

	/**
	 * Get the interactive status of this crawler.
	 * @return whether this crawler is interactive or not.
	 */
	public boolean isInteractive() {
		return interactive;
	}

	/**
	 * Run the search strategy over the provided domain. This method in turn
	 * runs the Crawler's search method until all the targets in the domain
	 * are found.
	 * @see #search()
	 */
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
		
	/**
	 * Test a point against the current target in the domain.
	 * @param p the point to test
	 * @return the zero vector if the point is found or a hint towards
	 * finding it if not.
	 */
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
	
	/**
	 * Search for a target within a domain by testing points generated by a
	 * particular strategy. It is this method that must be overridden by
	 * sub-classes to provide the search algorithm that they are implementing.
	 * <p/>Sub-classes should call testPoint from within their implementation
	 * of this method to test points against the current target. In general
	 * implementations of this method should only return when a target has
	 * been found, although "interactive" crawlers may need to return null in
	 * some instances.
	 * @return the found target point or null if no target could be found.
	 * @see #testPoint(Point3D)
	 * @see InteractiveCrawler
	 */
	public abstract Point3D search();
}
