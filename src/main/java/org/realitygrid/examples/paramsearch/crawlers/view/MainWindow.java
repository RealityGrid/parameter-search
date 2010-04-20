package org.realitygrid.examples.paramsearch.crawlers.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.realitygrid.examples.paramsearch.Domain;
import org.realitygrid.examples.paramsearch.Point3D;
import org.realitygrid.examples.paramsearch.crawlers.AbstractCrawler;

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
	
	public AbstractCrawler getCrawler() {
		return parent;
	}

	private JPanel createPanels() {
		int domainSize = domain.getSize();
		
		this.topView = new ViewPanel(ViewPanel.Projection.XZ, PANEL_SIZE, domainSize);
		this.sideView = new ViewPanel(ViewPanel.Projection.ZY, PANEL_SIZE, domainSize);
		this.frontView = new ViewPanel(ViewPanel.Projection.XY, PANEL_SIZE, domainSize);
		this.infoPanel = new ControlPanel(this, domain, PANEL_SIZE);

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
	
	public void showPoint(Point3D p, boolean found) {
		if(found) {
			pbar.setValue(pbar.getValue() + 1);
			showHint();
		}
		
		frontView.showPoint(new Point(p.getX(), p.getY()), found);
		topView.showPoint(new Point(p.getX(), p.getZ()), found);
		sideView.showPoint(new Point(p.getZ(), p.getY()), found);
	}

	public void showHint() {
		Point3D p = domain.getHint();
		
		if(p != null) {
			frontView.showHint(p.getX(), p.getY(), 20, 20);
			topView.showHint(p.getX(), p.getZ(), 20, 20);
			sideView.showHint(p.getZ(), p.getY(), 20, 20);
		}
	}
}
