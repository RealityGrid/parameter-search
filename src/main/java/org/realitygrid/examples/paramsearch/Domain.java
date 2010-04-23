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

public class Domain {
	private final static int SLEEP_TIME = 1000;
	private final static int ERROR = 10;
	private final static int TARGET_SEED = 17;

	private int size;
	private ArrayList<Target> targets;
	private int numTargets;
	private String name;

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

	public String getName() {
		return name;
	}

	public int getNumTargets() {
		return numTargets;
	}

	public int getSize() {
		return size;
	}
	
	public void reset() {
		for(Target t : targets)
			t.reset();
	}
		
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
