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

package org.realitygrid.examples.paramsearch.runner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.crawlers.AbstractCrawler;
import org.realitygrid.examples.paramsearch.ui.MainWindow;

/**
 * This class is used to run a search strategy (Crawler) over a target domain.
 * @author Robert Haines
 * @see org.realitygrid.examples.paramsearch.Domain
 * @see org.realitygrid.examples.paramsearch.crawlers
 */
public final class Runner {

	private static final int DOMAIN_SIZE = 300;
	private static final int NUM_TARGETS = 5;

	private AbstractCrawler crawler;
	private MainWindow window;
	private Domain domain;
	private String crawlerName;
	
	/**
	 * This is the standard entry point for this example. It creates a Runner
	 * which in turn instantiates a crawler and runs it. Its usage is described
	 * as follows:
	 * <p/>{@code Usage: java Runner [OPTIONS] <Crawler>}
	 * <p/>{@code Where OPTIONS can be one of:}<br/>
	 * {@code   -b		benchmark the crawler with a standard target set.}<br/>
     * {@code   -c		console only, no user interface (implies -b).}
	 * @param args The command line arguments with which to configure the run.
	 */
	public static void main(String[] args) {
		Runner runner;
		boolean benchmark = false;
		boolean noView = false;
		String classname ="";
		double duration = 0.0;
		
		if(args.length  < 1) {
			usage();
			System.exit(1);
		}
		
		try {
			if(args.length > 1) {
				if(args[0].equals("-b")) {
					benchmark = true;
					noView = true;
				}
				else if(args[0].equals("-c"))
					noView = true;
				else {
					usage();
					System.exit(1);
				}
				
				classname = args[1];
			}
			else
				classname = args[0];
			
			runner = new Runner(classname, benchmark);
			runner.loadClass();
			if(!noView)
				runner.createAndShowGUI();
		
			System.out.println((benchmark ? "Benchmarking" : "Searching") + " class " + classname);
			System.out.println("Searching for " + NUM_TARGETS + " targets...");
			if(benchmark) {
				double runDuration;
				for(int i = 1; i <= 5; i++) {
					runDuration = runner.run();
					duration += runDuration;
					System.out.println("Run " + i + " completed in " + runDuration + " seconds");
				}
				System.out.println("Average run " + (duration/5.0) + " seconds");
			}
			else {
				duration = runner.run();
				System.out.println("Completed in " + duration + " seconds");
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Cannot find class '" + classname + "' in package. Exiting.");
			System.exit(1);
		} catch (BadCrawlerException e) {
			System.err.println("Specified Crawler '" + classname + "' does not have base-type AbstractClass. Exiting.");
			System.exit(1);
		} catch (Exception e) {
			System.err.println("An unexpected exception (" + e.getMessage() + ") occured, sorry. Exiting.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates a runner.
	 * 
	 * @param classname The name of the crawler to be used with this runner. In
	 * practice this can be anything.
	 * @param benchmark Whether this runner is to be used to benchmark the
	 * specified crawler.
	 */
	public Runner(String classname, boolean benchmark) {
		crawlerName = classname;
		
		if(benchmark)
			domain = new Domain("Benchmark", DOMAIN_SIZE, NUM_TARGETS);
		else {
			String username = System.getProperty("user.name");
			domain = new Domain(crawlerName + username, DOMAIN_SIZE, NUM_TARGETS);
		}
	}
	
	/**
	 * Creates a non-benchmarking runner.
	 * 
	 * @param classname The name of the crawler to be used with this runner. In
	 * practice this can be anything.
	 */
	public Runner(String classname) {
		this(classname, false);
	}
	
	/**
	 * Get the name of the crawler being used by this runner.
	 * 
	 * @return The name of the crawler being used.
	 */
	public String getCrawlerName() {
		return crawlerName;
	}

	/**
	 * Runs the crawler. This simply calls the crawler's run method but
	 * wraps it with timing code.
	 * 
	 * @return the time taken, in seconds, by the crawler to complete.
	 */
	public double run() {
		domain.reset();
		
		long startTime = System.currentTimeMillis();
		crawler.run();
		long endTime = System.currentTimeMillis();
		
		return (endTime - startTime) / 1000.0;
	}
	
	private void loadClass() throws ClassNotFoundException, BadCrawlerException, Exception {
		Class<?> cls = Class.forName("org.realitygrid.examples.paramsearch.crawlers." + crawlerName);
		checkSuperclass(cls);

		Constructor<?> cons = cls.getConstructor(new Class[]{domain.getClass()});
		crawler = (AbstractCrawler) cons.newInstance(new Object[]{domain});
	}

	private void checkSuperclass(Class<?> cls) throws BadCrawlerException {
		Class<?> superclass = cls.getSuperclass();
		
		while(!superclass.getSimpleName().equals("Object")) {		
			if(superclass.getSimpleName().equals("AbstractCrawler"))
				return;
			
			superclass = superclass.getSuperclass();
		}
		
		throw new BadCrawlerException();
	}
	
	private void createAndShowGUI() throws InterruptedException, InvocationTargetException {
		javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				window = new MainWindow(crawler, domain);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setVisible(true);
			}
		});
	}
	
	private static void usage() {
		System.err.println("Usage: java Runner [OPTIONS] <Crawler>");
		System.err.println("\nWhere OPTIONS can be one of:");
		System.err.println("  -b\t\tbenchmark the crawler with a standard target set.");
		System.err.println("  -c\t\tconsole only, no user interface (implies -b).");
	}
}
