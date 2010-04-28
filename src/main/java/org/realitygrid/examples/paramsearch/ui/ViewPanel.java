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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * The ViewPanel class is a user interface component that displays a view of
 * the domain being searched. Each instance is passed a Projection that
 * determines whether it is a XZ (top), XY (front) or ZY (side) view. 
 * @author Robert Haines
 */
final class ViewPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int PAD = 5;
	
	private int panelSize;
	private int domainSize;
	private Projection projection;
	
	// components
	private JLabel titleLabel;
	private View view;
	
	/**
	 * Create a ViewPanel with the specified projection, panel size and domain
	 * size.
	 * @param p the projection that this panel will be showing.
	 * @param ps the panel size required.
	 * @param ds the domain size being shown.
	 */
	public ViewPanel(Projection p, int ps, int ds) {
		super(true);
		
		panelSize = ps;
		domainSize = ds;
		projection = p;
		
		titleLabel = new JLabel(projection.getTitle());
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		view = new View(this, domainSize);
		
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.add(view);
		this.add(Box.createRigidArea(new Dimension(0, PAD)));
		this.add(titleLabel);
	}
	
	/**
	 * Show a point in this panel.
	 * @param p the point to show.
	 * @param found whether or not this point has been found.
	 */
	public void showPoint(Point p, boolean found) {
		this.view.addPoint(p, found);
	}
	
	/**
	 * Show a target hint in this panel.
	 * @param x the X coordinate of the hint.
	 * @param y the Y coordinate of the hint.
	 * @param w the width of the hint.
	 * @param h the height of the hint.
	 */
	public void showHint(int x, int y, int w, int h) {
		this.view.setHint(x, y, w, h);
	}

	enum Projection {
		XY("Front", "X", "Y"),
		XZ("Top", "X", "Z"),
		ZY("Side", "Z", "Y");
		
		private final String title;
		private final String x;
		private final String y;
		
		Projection(String t, String x, String y) {
			this.title = t;
			this.x = x;
			this.y = y;
		}
		
		String getTitle() {
			return title;
		}

		String getX() {
			return x;
		}
		
		String getY() {
			return y;
		}
	}

	final class View extends JPanel {

		private static final long serialVersionUID = 1L;
		private static final int ARGB_WHITE = (255 << 24) + (255 << 16) + (255 << 8) + 255;
		private static final int ARGB_RED = (255 << 24) + (255 << 16);
		private static final int ARGB_GREEN = (255 << 24) + (255 << 8);

		private ViewPanel parent;
		private int size;
		private BufferedImage image;
		private Rectangle2D.Float hint;
		private GeneralPath axes;
		
		View(ViewPanel scv, int size) {
			parent = scv;
			this.size = size;
			hint = null;
			image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
			
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					image.setRGB(i, j, ARGB_WHITE);
				}
			}
			
			axes = createAxes();
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        Dimension d = getSize();
	        
	        g2.drawImage(image, null, (d.width - 1 - size), 0);
	        
	        g2.draw(axes);
	        g2.drawString(projection.getX(), (panelSize / 2), (panelSize - 5));
	        g2.drawString(projection.getY(), 12, (panelSize / 2));

	        g2.setPaint(Color.BLUE);
	        if(hint != null)
	        	g2.draw(hint);
		}

		public void setHint(int x, int y, int w, int h) {
			x = x + (getSize().width - 1 - size);
			y = this.size - y - 21;
			this.hint = new Rectangle2D.Float(x, y, w, h);
		}

		public void addPoint(Point p, boolean found) {			
			// x is ok but y needs to be flipped
			int x = p.x;
			int y = this.size - p.y - 1;
			
			if(this.image.getRGB(x, y) != ARGB_WHITE)
				return;
				
			for(int i = (x - 1); i <= (x + 1); i++) {
				if(i < 0 || i >= this.size) continue; 
				for(int j = (y - 1); j <= (y + 1); j++) {
					if(j < 0 || j >= this.size) continue;
					this.image.setRGB(i, j, found ? ARGB_GREEN : ARGB_RED);
				}
			}
			
			this.repaint();
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(parent.panelSize, parent.panelSize);
		}
		
		private GeneralPath createAxes() {
			int[] xs = {15, 25, 5, 15, 15, 120, 110, 110, 120};
			int[] ys = {120, 110, 110, 120, 15, 15, 25, 5, 15};
						
			GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO, xs.length);
			path.moveTo(xs[0], panelSize - ys[0] + 5);
			for(int i = 1; i < xs.length; i++)
				path.lineTo(xs[i], panelSize - ys[i] + 5);
			
			return path;
		}
	}
}
