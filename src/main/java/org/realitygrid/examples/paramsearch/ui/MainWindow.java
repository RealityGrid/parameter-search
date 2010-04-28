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

package org.realitygrid.examples.paramsearch.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.Point3D;
import org.realitygrid.examples.paramsearch.crawlers.AbstractCrawler;

/**
 * This class provides the main window for displaying a crawler searching a
 * domain for targets. As well as organising the various panels that show the
 * search progress it also marshals communications between the crawler and
 * domain.
 * @author Robert Haines
 * @see Domain
 * @see org.realitygrid.examples.paramsearch.crawlers
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int PANEL_SIZE = 330;
	private static final int PAD = 5;

	private AbstractCrawler parent;
	private Domain domain;

	// components
	private ViewPanel topView;
	private ViewPanel sideView;
	private ViewPanel frontView;
	private ControlPanel infoPanel;
	private JProgressBar pbar;

	/**
	 * Create a MainWindow to display the specified crawler searching the
	 * specified domain.
	 * @param ac the crawler.
	 * @param d the domain.
	 * @throws HeadlessException
	 */
	public MainWindow(AbstractCrawler ac, Domain d) throws HeadlessException {
		super();

		parent = ac;
		domain = d;

		Container contentPane = this.getContentPane();
		contentPane.add(createPanels(), BorderLayout.CENTER);
		contentPane.add(createProgressBar(), BorderLayout.SOUTH);

		this.setTitle("Searching domain with '" + parent.getName() + "'");
		this.pack();
		this.setResizable(false);
		parent.setView(this);
		showHint();
	}

	/**
	 * Get the crawler that this window is displaying.
	 * @return the crawler.
	 */
	public AbstractCrawler getCrawler() {
		return parent;
	}

	private JPanel createPanels() {
		int domainSize = domain.getSize();

		this.topView = new ViewPanel(ViewPanel.Projection.XZ, PANEL_SIZE, domainSize);
		this.sideView = new ViewPanel(ViewPanel.Projection.ZY, PANEL_SIZE, domainSize);
		this.frontView = new ViewPanel(ViewPanel.Projection.XY, PANEL_SIZE, domainSize);
		this.infoPanel = new ControlPanel(this, PANEL_SIZE);

		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel topPanel = new JPanel();

		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));

		leftPanel.add(Box.createRigidArea(new Dimension(0, PAD)));
		leftPanel.add(infoPanel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, PAD)));
		leftPanel.add(sideView);
		leftPanel.add(Box.createRigidArea(new Dimension(0, PAD)));

		rightPanel.add(Box.createRigidArea(new Dimension(0, PAD)));
		rightPanel.add(topView);
		rightPanel.add(Box.createRigidArea(new Dimension(0, PAD)));
		rightPanel.add(frontView);
		rightPanel.add(Box.createRigidArea(new Dimension(0, PAD)));

		topPanel.add(Box.createRigidArea(new Dimension(PAD, 0)));
		topPanel.add(leftPanel);
		topPanel.add(Box.createRigidArea(new Dimension(PAD, 0)));
		topPanel.add(rightPanel);
		topPanel.add(Box.createRigidArea(new Dimension(PAD, 0)));

		return topPanel;
	}

	private JPanel createProgressBar() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		JLabel label = new JLabel("Progress:");
		pbar = new JProgressBar(0, domain.getNumTargets());

		panel.add(Box.createRigidArea(new Dimension(PAD, 0)));
		panel.add(label);
		panel.add(Box.createRigidArea(new Dimension(PAD, 0)));
		panel.add(pbar);
		panel.add(Box.createRigidArea(new Dimension(PAD, 0)));

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		p.add(panel);
		p.add(Box.createRigidArea(new Dimension(0, PAD)));

		return p;
	}

	/**
	 * Register a point to be shown in the displays. This method sends the
	 * point and found status through to the panels that are showing the
	 * domain. It also updates the progress bar at the bottom of the window if
	 * required.
	 * @param p the point to show.
	 * @param found whether or not this point has been found.
	 */
	public void showPoint(Point3D p, boolean found) {
		if(found) {
			pbar.setValue(pbar.getValue() + 1);
			showHint();
		}

		frontView.showPoint(p, found);
		topView.showPoint(p, found);
		sideView.showPoint(p, found);
	}

	/**
	 * Get a hint for the current target from the domain and pass it on to the
	 * panels that are showing the domain.
	 */
	public void showHint() {
		Point3D p = domain.getHint();

		if(p != null) {
			frontView.showHint(p, new Dimension(20, 20));
			topView.showHint(p, new Dimension(20, 20));
			sideView.showHint(p, new Dimension(20, 20));
		}
	}
}
