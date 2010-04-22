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
