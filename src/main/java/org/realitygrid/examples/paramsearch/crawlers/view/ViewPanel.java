package org.realitygrid.examples.paramsearch.crawlers.view;

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


final class ViewPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int PAD = 5;
	
	private int panelSize;
	private int domainSize;
	private Projection projection;
	
	// components
	private JLabel titleLabel;
	private View view;
	
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
	
	public void showPoint(Point p, boolean found) {
		this.view.addPoint(p, found);
	}
	
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
