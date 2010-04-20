package org.realitygrid.examples.paramsearch.crawlers.view;

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
import org.realitygrid.examples.paramsearch.crawlers.AbstractCrawler;
import org.realitygrid.examples.paramsearch.crawlers.InteractiveCrawler;

public final class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int PAD = 5;

	private MainWindow parent;
	private Domain domain;
	private AbstractCrawler crawler;
	private int panelSize;
	
	// components
	private JLabel titleLabel;
	private JPanel control;
	private StatusPanel status;
	private JSpinner xInput;
	private JSpinner yInput;
	private JSpinner zInput;
	private JButton testButton;

	public ControlPanel(MainWindow scw, Domain d, int ps) {
		super(true);
		
		parent = scw;
		domain = d;
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
		status = new StatusPanel();
		
		control = new JPanel();
		control.setLayout(new BoxLayout(control, BoxLayout.PAGE_AXIS));
		control.add(Box.createRigidArea(new Dimension(0, PAD)));
		if(crawler.isInteractive()) {
			int dSize = domain.getSize();
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
					
					((InteractiveCrawler) crawler).queue(new Point3D(x, y, z));
				}
			});
			
			control.add(test);
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
