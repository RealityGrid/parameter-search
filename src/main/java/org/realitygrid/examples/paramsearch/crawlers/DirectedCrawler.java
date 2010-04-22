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
