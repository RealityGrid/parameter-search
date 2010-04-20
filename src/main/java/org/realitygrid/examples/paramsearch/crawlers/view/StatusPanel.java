package org.realitygrid.examples.paramsearch.crawlers.view;

import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class StatusPanel extends JScrollPane {

	private static final long serialVersionUID = 1L;

	private JTextArea console;
	private StatusStream stream;
	
	public StatusPanel() {
		super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		console = new JTextArea();
		stream = new StatusStream(console);
		
		setViewportView(console);
		DefaultCaret caret = (DefaultCaret) console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		System.setOut(getPrintStream());
	}

	public PrintStream getPrintStream() {
		return new PrintStream(stream);
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension d =  super.getPreferredSize();
		return new Dimension(d.width, 200);
	}

	class StatusStream extends OutputStream {

		private JTextArea ta;
		
		public StatusStream(JTextArea ta) {
			super();
			this.ta = ta;
		}

		@Override
		public void write(int b) throws IOException {
			final String s = (new Character((char)b)).toString();
			ta.append(s);
		}
	}
}
