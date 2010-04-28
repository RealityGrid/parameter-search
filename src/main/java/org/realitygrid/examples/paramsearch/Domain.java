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

package org.realitygrid.examples.paramsearch;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Domain class represents a volume of 3D space that has a number of
 * targets hidden in it. These targets must be found. The only way of
 * finding a target is to ask the domain whether a point in space is within
 * a certain distance of it or not. If so the target is marked as found and
 * the next target, if there is one, is activated.
 * <p/>The following information holds true for any crawler operating on a
 * domain but certain crawlers know more of it than others:
 * <ul>
 *   <li>Only one target is active at any time and must be found in
 *   order.</li>
 *   <li>The targets are ordered from lowest to highest firstly by their x
 *   coordinate, then their y coordinate and finally their z coordinate.</li>
 *   <li>The current target to be sought is always surrounded by a blue
 *   halo.</li>
 *   <li>Testing anywhere within this blue halo is enough to trigger the
 *   target and find it.</li>
 *   <li>Testing a point in the domain takes approximately one second.</li>
 * </ul>
 * @author Robert Haines
 * @see org.realitygrid.examples.paramsearch.crawlers
 */
public class Domain {
	private final static int SLEEP_TIME = 1000;
	private final static int ERROR = 10;
	private final static int TARGET_SEED = 17;

	private int size;
	private ArrayList<Target> targets;
	private int numTargets;
	private String name;

	/**
	 * Create a named domain of the specified size and with the required number
	 * of targets.
	 * @param name the name of the domain.
	 * @param size the size (side length) of the domain.
	 * @param numTargets the number of targets to embed in the domain.
	 */
	public Domain(String name, int size, int numTargets) {
		this.size = size;
		this.name = name;
		this.numTargets = numTargets;
		targets = new ArrayList<Target>(numTargets);

		// generate target locations
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("sha1");
			md.reset();
			md.update((name + numTargets + size + TARGET_SEED).getBytes());
			byte[] hash = md.digest();

			generateTargets(hash);
			Collections.sort(targets);
		} catch (NoSuchAlgorithmException e) {
			// I know sha1 is correct so...
		}
	}

	/**
	 * Get the name of the domain.
	 * @return the name of the domain.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the number of targets in this domain.
	 * @return the number of targets.
	 */
	public int getNumTargets() {
		return numTargets;
	}

	/**
	 * Get the size of the domain. Domains are cubic so a single number is
	 * used to describe all three side lengths.
	 * @return the size (side length) of the domain.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Reset the "found" status of each of the targets in the domain. This is
	 * generally only used by something that is benchmarking a domain.
	 * @see org.realitygrid.examples.paramsearch.runner.Runner
	 */
	public void reset() {
		for(Target t : targets)
			t.reset();
	}

	/**
	 * Get the vector from the test point to the target.
	 * This will be the zero vector if the test point is the same as the
	 * target.
	 * @param location the point to test against the target.
	 * @return the vector from the test point to the target.
	 * @see Point3D
	 * @see Vector3D
	 */
	public Vector3D search(Point3D location) {
		if(location == null)
			return null;

		for(Target t : targets) {
			if(t.isFound())
				continue;

			if(t.isFound(location))
				return Vector3D.ZERO;

			return location.getVectorTo(t.getLocation());
		}

		return Vector3D.ZERO;
	}

	/**
	 * Test a point to see if it is within a certain distance of a target.
	 * @param location
	 * @return the actual target point if test point is within the error
	 * bounds, null otherwise.
	 * @see Point3D
	 */
	public Point3D isWithinError(Point3D location) {
		// sleep to emulate work
		try {
			Thread.sleep(SLEEP_TIME);
		} catch (InterruptedException e) {
		}

		if(location == null)
			return null;

		for(Target t : targets) {
			if(t.isFound())
				continue;

			return t.search(location);
		}

		return null;
	}

	/**
	 * Get a hint as to where the target might be.
	 * @return a point offset by an error margin in all three axes.
	 */
	public Point3D getHint() {
		for(Target t : targets) {
			if(t.isFound())
				continue;

			return t.getLocation().moveBy(new Vector3D(-ERROR, -ERROR, -ERROR));
		}

		return null;
	}

	private void generateTargets(byte[] hash) {
		int h = 0;
		int multiplier = size / 256;

		for (int i = 0; i < numTargets; i++) {
			int[] dims = new int[3];
			for (int j = 0; j < 3; j++) {
				dims[j] = (size / 2) + (hash[h++] * multiplier);
				if (h == hash.length)
					h = 0;
			}
			targets.add(new Target(dims[0], dims[1], dims[2]));
		}
	}

	@Override
	public String toString() {
		int found = 0;
		String result = "Domain created for " + name + ", size = " + size
		+ ".\nTargets found: ";
		for (Target t : targets)
			if (t.isFound()) {
				result += t + "\n               ";
				found += 1;
			}

		return result + found + "\\" + numTargets;
	}

	private static class Target implements Comparable<Target> {
		private Point3D location;
		private boolean found;

		Target(int x, int y, int z) {
			location = new Point3D(x, y, z);
			found = false;
		}

		void reset() {
			found = false;
		}

		boolean isFound() {
			return found;
		}

		boolean isFound(Point3D location) {
			found = location.equals(this.location);
			return found;
		}

		Point3D search(Point3D p) {
			if(location.isWithin(ERROR, p)) {
				found = true;
				return location;
			}

			return null;
		}

		Point3D getLocation() {
			return location;
		}

		@Override
		public String toString() {
			return "Target [found=" + found + ", location=" + location + "]";
		}

		@Override
		public int compareTo(Target o) {
			return location.compareTo(o.location);
		}
	}
}
