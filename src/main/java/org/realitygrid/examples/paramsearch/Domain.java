package org.realitygrid.examples.paramsearch;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.codec.digest.DigestUtils;

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
		byte[] hash = DigestUtils.sha(name + numTargets + size + TARGET_SEED);
		generateTargets(hash);
		Collections.sort(targets);
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
