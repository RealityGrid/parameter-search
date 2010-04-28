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

import java.util.concurrent.ConcurrentLinkedQueue;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.Point3D;
import org.realitygrid.examples.paramsearch.Vector3D;

/**
 * The InteractiveCrawler is the base class of any crawler that is to be
 * controlled by the user rather than automatic. A queue is made available for
 * external code to pass points in to be tested however they have been
 * selected.
 * @author RobertHaines
 * @see Domain
 */
public class InteractiveCrawler extends AbstractCrawler {

	private static final int SLEEP_TIME = 1000;

	private ConcurrentLinkedQueue<Point3D> pointQueue;

	/**
	 * Create an InteractiveCrawler with the specified name and domain.
	 * @param s the name (type) of the crawler.
	 * @param d the domain to be searched.
	 */
	public InteractiveCrawler(String s, Domain d) {
		super(s, d, true);
		
		pointQueue = new ConcurrentLinkedQueue<Point3D>();
	}

	/**
	 * Add a point to be tested to the queue. The queue is polled at intervals
	 * of one second for new points.
	 * @param p the point to be added to the queue.
	 */
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
