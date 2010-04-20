package org.realitygrid.examples.paramsearch.runner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.crawlers.AbstractCrawler;
import org.realitygrid.examples.paramsearch.crawlers.view.MainWindow;

public final class Runner {

	private static final int DOMAIN_SIZE = 300;
	private static final int NUM_TARGETS = 5;

	private AbstractCrawler crawler;
	private MainWindow window;
	private Domain domain;
	private String crawlerName;
	
	/**
	 * @param args
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

	public Runner(String classname, boolean benchmark) {
		crawlerName = classname;
		
		if(benchmark)
			domain = new Domain("Benchmark", DOMAIN_SIZE, NUM_TARGETS);
		else {
			String username = System.getProperty("user.name");
			domain = new Domain(crawlerName + username, DOMAIN_SIZE, NUM_TARGETS);
		}
	}
	
	public Runner(String classname) {
		this(classname, false);
	}
	
	public String getCrawlerName() {
		return crawlerName;
	}

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
