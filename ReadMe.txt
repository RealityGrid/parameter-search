This software is an example of Computational Steering via the counter example
of naive parameter sweeps.

Research Computing Services, IT Services, University of Manchester.

-----------------------------------------------------------------------------

Prerequisites:

There are no prerequisites for running this code save that of a recent JRE.
It has been tested with Java 6 but it should work with Java 5 as well.

If you have a source distribution then you can either just build all the
classes by hand or use Maven:

$ mvn package

Maven is likely to be provided by your OS but in case it is not it is
available here: http://maven.apache.org/

-----------------------------------------------------------------------------

Running the code:

If you have a binary distribution or have built the source using the above
command you can simply run the resulting jar file as follows:

$ java -jar Parameter-Search.jar <crawler_strategy>

Where crawler_strategy is one of the provided strategies for crawling through
the target domain. Please see doc/exercise.txt for an explanation of these
strategies.

-----------------------------------------------------------------------------

Adding your own crawler strategy:

You simply need to subclass the AbstractCrawler class and implement the
search method to crawl through the target domain in the manner you require.
Have a look at the other crawlers to see how they work first.

-----------------------------------------------------------------------------

Any comments, enquiries or pleas for explanation should be directed to
the comp-steering mailing list.  Details available from:

http://listserv.manchester.ac.uk/cgi-bin/wa?A0=COMP-STEERING
