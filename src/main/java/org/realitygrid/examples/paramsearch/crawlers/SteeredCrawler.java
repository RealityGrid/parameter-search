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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.Point3D;

/**
 * @author Robert Haines
 * @see InteractiveCrawler
 * @see Domain
 */
public final class SteeredCrawler extends InteractiveCrawler {

	private static final int PAD = 5;

	// panel components
	private JPanel panel;
	private JSpinner xInput;
	private JSpinner yInput;
	private JSpinner zInput;
	private JButton testButton;

	/**
	 * Create a SteeredCrawler with the specified domain.
	 * @param d the domain to be searched.
	 */
	public SteeredCrawler(Domain d) {
		super("Steered Crawler", d);
		panel = null;
	}

	@Override
	public JPanel getPanel() {
		if(panel != null)
			return panel;

		int dSize = getDomainSize();
		int modelStart = dSize / 2;
		int modelEnd = dSize - 1;
		int modelStep = 10;

		JPanel point = new JPanel();
		point.setLayout(new BoxLayout(point, BoxLayout.LINE_AXIS));
		point.add(Box.createRigidArea(new Dimension(PAD, 0)));

		point.add(new JLabel("("));
		xInput = (JSpinner) point.add(
				new JSpinner(new SpinnerNumberModel(modelStart, 0, modelEnd, modelStep)));
		point.add(new JLabel(","));
		yInput = (JSpinner) point.add(
				new JSpinner(new SpinnerNumberModel(modelStart, 0, modelEnd, modelStep)));
		point.add(new JLabel(","));
		zInput = (JSpinner) point.add(
				new JSpinner(new SpinnerNumberModel(modelStart, 0, modelEnd, modelStep)));
		point.add(new JLabel(")"));

		point.add(Box.createRigidArea(new Dimension(PAD, 0)));

		JPanel test = new JPanel();
		test.setLayout(new BoxLayout(test, BoxLayout.PAGE_AXIS));
		test.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Choose point"));
		test.add(point);
		test.add(Box.createRigidArea(new Dimension(0, PAD)));
		testButton = (JButton) test.add(new JButton(" Test! "));
		testButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		testButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int x = (Integer) xInput.getValue();
				int y = (Integer) yInput.getValue();
				int z = (Integer) zInput.getValue();

				queue(new Point3D(x, y, z));
			}
		});

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(Box.createRigidArea(new Dimension(0, PAD)));
		panel.add(test);
		panel.add(Box.createRigidArea(new Dimension(0, PAD)));

		return panel;
	}
}
