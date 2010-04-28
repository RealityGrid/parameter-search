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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.crawlers.AbstractCrawler;
import org.realitygrid.examples.paramsearch.crawlers.InteractiveCrawler;

/**
 * This class is a user interface element that displays information about the
 * search being performed on a domain.<p/>If the search is autonomous then
 * progress is reported but if it is an interactive search then a mechanism for
 * the user to choose test points is provided.<p/>When this class is
 * instantiated it redirects {@code System.out} to its status window so any
 * output from the search process is displayed in the GUI.
 * @author Robert Haines
 * @see MainWindow
 * @see Domain
 * @see AbstractCrawler
 * @see InteractiveCrawler
 * @see System#setOut(java.io.PrintStream)
 */
public final class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int PAD = 5;

	private MainWindow parent;
	private AbstractCrawler crawler;
	private int panelSize;

	// components
	private JLabel titleLabel;
	private JPanel control;
	private StatusPanel status;

	/**
	 * Create a control panel.
	 * @param scw the parent of this panel.
	 * @param ps the panel size required.
	 */
	public ControlPanel(MainWindow scw, int ps) {
		super(true);

		parent = scw;
		panelSize = ps;
		crawler = parent.getCrawler();

		if(crawler.isInteractive())
			titleLabel = new JLabel("Control");
		else
			titleLabel = new JLabel("Status");

		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(createPanel());
		this.add(Box.createRigidArea(new Dimension(0, PAD)));
		this.add(titleLabel);
	}

	private JPanel createPanel() {
		// create status panel and redirect System.out to it
		status = new StatusPanel();
		System.setOut(status.getPrintStream());

		if(crawler.isInteractive()) {
			control = ((InteractiveCrawler) crawler).getPanel();
		}
		else {
			control = new JPanel();
			control.setLayout(new BoxLayout(control, BoxLayout.PAGE_AXIS));
			control.add(Box.createRigidArea(new Dimension(0, PAD)));
		}

		control.add(status);
		control.add(Box.createRigidArea(new Dimension(0, PAD)));

		JPanel container = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(panelSize, panelSize);
			}
		};

		container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
		container.add(Box.createRigidArea(new Dimension(PAD, 0)));
		container.add(control);
		container.add(Box.createRigidArea(new Dimension(PAD, 0)));

		return container;
	}
}
