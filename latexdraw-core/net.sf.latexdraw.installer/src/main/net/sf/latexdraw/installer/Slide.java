package net.sf.latexdraw.installer;

import net.sf.latexdraw.util.InstallerLog;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

abstract class Slide extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/** The dimension of a slide. */
	protected static final Dimension PANEL_SIZE = new Dimension(650, 350);

	protected Slide next;
	
	protected Slide prev;
	
	private boolean init;
	
	protected Installer installer;
	
	
	protected Slide(final Slide prev, final Slide next, final Installer installer) {
		super();
		
		this.installer 	= installer;
		this.prev 		= prev;
		this.next 		= next;
		init 			= false;
	}
	
	protected Slide next() {
		if(next!=null) {
			uninstall();
			next.install();
		}
		
		return next;
	}
	
	protected Slide prev() {
		if(prev!=null) {
			uninstall();
			prev.install();
		}
		
		return prev;
	}
	
	
	protected void install() {
		InstallerLog.getLogger().log(Level.INFO, "Slide: " + getClass().getSimpleName());

		if(!init)
			init();
		
		installer.getContentPane().add(this, BorderLayout.CENTER);
		installer.previousB.setEnabled(prev!=null);
		installer.nextB.setVisible(next!=null);
		installer.endB.setVisible(next==null);
		installer.pack();
	}
	

	protected void uninstall() {
		installer.getContentPane().remove(this);
		installer.pack();
	}
	
	protected void init() {
		init = true;
	}
	
	protected void setPanelDimension() {
		setPreferredSize(PANEL_SIZE);
		setMinimumSize(PANEL_SIZE);
		setMaximumSize(PANEL_SIZE);
	}
}
