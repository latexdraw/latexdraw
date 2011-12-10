package net.sf.latexdraw.ui;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.malai.interaction.Eventable;
import org.malai.interaction.SwingEventManager;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;
import org.malai.widget.MToolBar;
import org.malai.widget.WidgetUtilities;

/**
 * This class defines a button which displays a frame containing
 * others buttons; a kind of menu with buttons.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 05/02/06<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class ListToggleButton extends JToggleButton implements ActionListener, ChangeListener, WindowFocusListener, Picker, Eventable {
	private static final long serialVersionUID = 1L;

	/** The event manager that listens events produced by the list of toogle buttons. May be null. */
	protected SwingEventManager eventManager;

	/** The frame which displays the toolbar */
	protected WindowWidgets buttonsFrame;

	/** The toolbar which contains the buttons */
	protected MToolBar toolbar;

	/** The width of the triangle drawn in the button */
	public static final int WIDTH_TRIANGLE = 8;

	/** The height of the triangle drawn in the button */
	public static final int HEIGHT_TRIANGLE = 6;

	/** Define the location of the panel of buttons. */
	protected int location;

	/** The component to give the focus when the toolbar is closed. */
	protected Component componentFocusOnClose;

	/** The frame is at the north of the button. */
	public static final int LOCATION_NORTH = 1;

	/** The frame is at the south of the button. */
	public static final int LOCATION_SOUTH = 2;

	private static final int WIDTH_ICON = 10;

	private static final int HEIGHT_ICON = 20;

	private static final int TRIANGLE_NB_POINT = 3;

	private static final int TRIANGLE_GAP = 4;



	/**
	 * The constructor using a text.
	 * @param frame The main frame containing the ListJToggleButton.
	 * @param txt The text of the ListJToggleButton.
	 * @param locate The position of the mini toolbar.
	 * @param componentFocusOnClose The component to give the focus when the toolbar is closed. Can be null.
	 * @since 3.0
	 */
	public ListToggleButton(final JFrame frame, final String txt, final int locate, final Component componentFocusOnClose) {
		super(txt);

		setIcon();
		intializing(frame, locate);
		this.componentFocusOnClose = componentFocusOnClose;
	}



	/**
	 * The constructor using an icon.
	 * @param icon The icon of the ListJToggleButton.
	 * @param locate The position of the mini toolbar.
	 * @param frame The main frame containing the ListJToggleButton.
	 * @param componentFocusOnClose The component to give the focus when the toolbar is closed. Can be null.
	 */
	public ListToggleButton(final JFrame frame, final Icon icon, final int locate, final Component componentFocusOnClose) {
		super(icon);

		intializing(frame, locate);
		this.componentFocusOnClose = componentFocusOnClose;
	}



	/**
	 * Initialises the ListJToggleButton.
	 * @param frame The main frame containing the ListJToggleButton.
	 * @param locate The position of the mini toolbar.
	 * @since 1.9.1
	 */
	protected void intializing(final JFrame frame, final int locate) {
		try{ setLocation(locate); }
		catch(final IllegalArgumentException e) {location = LOCATION_SOUTH;}

		toolbar = new MToolBar(true);
		toolbar.setFloatable(false);

		final JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(toolbar);
		buttonsPanel.setBorder(BorderFactory.createEtchedBorder());

		buttonsFrame = new WindowWidgets(frame, buttonsPanel, this);

		addActionListener(this);
		setMargin(new Insets(1,1,1,1));
		addComponent(new CloseButton());

		// The mini-toolbar must disappear when a click or a press occurs somewhere else.
		Toolkit.getDefaultToolkit().addAWTEventListener( new ListToggleButtonAWTEventListener(), AWTEvent.MOUSE_EVENT_MASK);
	}


	/**
	 * @return True if at least one of the widgets of the list is visible.
	 * @since 3.0
	 */
	public boolean isContentVisible() {
		boolean visible = false;
		Component comp;

		for(int i=0, size=toolbar.getComponentCount(); i<size && !visible; i++) {
			comp = toolbar.getComponent(i);

			if(!(comp instanceof JToolBar.Separator) && !(comp instanceof CloseButton) && !(comp instanceof Box.Filler))
				visible = comp.isVisible();
		}

		return visible;
	}


	/**
	 * @return Creates an imageIcon containing the triangle icon.
	 * @since 1.9.1
	 */
	protected ImageIcon createTriangleIcon() {
		final BufferedImage bufferImage = new BufferedImage(WIDTH_ICON, HEIGHT_ICON, BufferedImage.TYPE_INT_ARGB);
		paintTriangle(bufferImage.createGraphics(), WIDTH_ICON, HEIGHT_ICON);

		return new ImageIcon(bufferImage);
	}



	/**
	 * Sets the location of the mini toolbar.
	 * @param locat The position of the mini toolbar.
	 * @throws IllegalArgumentException If the location is neither <code>LOCATION_NORTH</code> nor <code>LOCATION_SOUTH</code>.
	 */
	public void setLocation(final int locat) {
		if(locat==LOCATION_NORTH || locat==LOCATION_SOUTH)
			location = locat;
		else
			throw new IllegalArgumentException();
	}



	@Override
	public void setSelected(final boolean sel) {
		updateSelected();
	}



	/**
	 * Adds a separator to the toolbar.
	 */
	public void addSeparator() {
		final Component c = toolbar.getComponentAtIndex(toolbar.getComponentCount()-1);
		toolbar.remove(c);
		toolbar.addSeparator();
		toolbar.add(c);
		buttonsFrame.pack();
	}



	/**
	 * Adds a button to the toolbar of the listJToggleButton.
	 * @param comp The new button to add.
	 */
	public void addComponent(final Component comp) {
		toolbar.add(comp, null, toolbar.getComponentCount()-1);
		buttonsFrame.pack();

		if(comp instanceof AbstractButton) {
			final AbstractButton ab = (AbstractButton) comp;
			ab.addActionListener(this);
			ab.addChangeListener(this);
		}

		attachAddedComponent(comp);
	}




	/**
	 * Paints the triangle icon in a graphic.
	 * @param g The graphic.
	 * @param width The width of the object.
	 * @param height The height of the object.
	 * @since 1.9.1
	 */
	public void paintTriangle(final Graphics g, final int width, final int height) {
		if(g==null || width<1 || height<1)
			return ;

		if(g instanceof Graphics2D) {
			final Graphics2D g2 = (Graphics2D) g;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		}

		final int xs[] = new int[TRIANGLE_NB_POINT];
		final int ys[] = new int[TRIANGLE_NB_POINT];

		if(location==LOCATION_SOUTH) {
			xs[0] = (width-WIDTH_TRIANGLE)>>1;
			xs[1] = (width+WIDTH_TRIANGLE)>>1;
			xs[2] = width>>1;

			ys[0] = height-HEIGHT_TRIANGLE-TRIANGLE_GAP;
			ys[1] = height-HEIGHT_TRIANGLE-TRIANGLE_GAP;
			ys[2] = height-TRIANGLE_GAP;
		}
		else {
			xs[0] = (width-WIDTH_TRIANGLE)>>1;
			xs[1] = (width+WIDTH_TRIANGLE)>>1;
			xs[2] = width>>1;

			ys[0] = HEIGHT_TRIANGLE+TRIANGLE_GAP;
			ys[1] = HEIGHT_TRIANGLE+TRIANGLE_GAP;
			ys[2] = TRIANGLE_GAP;
		}

		g.setColor(Color.red);
		g.fillPolygon(xs, ys, TRIANGLE_NB_POINT);
		g.setColor(Color.black);
		g.drawPolygon(xs, ys, TRIANGLE_NB_POINT);
	}



	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		if(getText().equals(""))//$NON-NLS-1$
			paintTriangle(g, getSize().width, getSize().height);
	}



	/**
	 * Defines the position of the mini-toolbar.
	 * @since 1.9.1
	 */
	public void defineToolbarLocation() {
		buttonsFrame.setLocation(getLocationOnScreen().x, getLocationOnScreen().y +
								(location==LOCATION_SOUTH ? getHeight() : -buttonsFrame.getHeight()));
	}



	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object src = e.getSource();

		if(src instanceof ListToggleButton) {
			final boolean visible = !buttonsFrame.isVisible();

			defineToolbarLocation();
			updateSelected();
			setButtonsFrameVisible(visible);

			if(visible)
				buttonsFrame.requestFocusInWindow();
			return ;
		}

		if(src instanceof JCheckBox || src instanceof JRadioButton)
			return ;

		if(src instanceof CloseButton) {
			setButtonsFrameVisible(false);
			return;
		}

		if(src instanceof JToggleButton || src instanceof JButton) {
			setButtonsFrameVisible(false);

			updateSelected();
			return ;
		}
	}


	/**
	 * Sets id the toolbar must be visible or not.
	 * @param visible True: the toolbar will be visible.
	 * @since 3.0
	 */
	public void setButtonsFrameVisible(final boolean visible) {
		buttonsFrame.setVisible(visible);

		if(!visible && componentFocusOnClose!=null)
			componentFocusOnClose.requestFocus();
	}



	@Override
	public void stateChanged(final ChangeEvent e) {
		final Object src = e.getSource();

		if(src instanceof JToggleButton && !(src instanceof JCheckBox || src instanceof JRadioButton))
			setSelected(((JToggleButton)src).isSelected());
	}



	/**
	 * @return The location of the panel of buttons.
	 */
	public int getLocationButtonPanel() {
		return location;
	}



	/**
	 * Sets the main button to selected or not following if a button is selected or not.
	 */
	public void updateSelected() {
		int i;
		final int size = toolbar.getComponentCount();
		boolean selected = false;
		Component src;

		for(i=0; i<size && !selected; i++) {
			src = toolbar.getComponent(i);

			if(src instanceof JToggleButton && !(src instanceof JCheckBox || src instanceof JRadioButton) &&
				((JToggleButton)toolbar.getComponent(i)).isSelected())
				selected = true;
		}

		super.setSelected(selected);
	}



	@Override
	public void windowGainedFocus(final WindowEvent e) {
//		toolbar.requestFocusInWindow();
	}



	@Override
	public void windowLostFocus(final WindowEvent e) {
		if(e.getOppositeWindow()==null)
			buttonsFrame.setVisible(false);
	}



	/**
	 * The icon of the button will be the red triangle.
	 * @since 1.9.1
	 */
	public void setIcon() {
		super.setIcon(createTriangleIcon());
	}



	@Override
	public void setIcon(final Icon defaultIcon) {
		if(!getText().equals(""))//$NON-NLS-1$
			setIcon();
		else
			super.setIcon(defaultIcon);
	}


	@Override
	public boolean contains(final Object obj) {
		return WidgetUtilities.INSTANCE.contains(toolbar.getComponents(), obj);
	}



	@Override
	public Pickable getPickableAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickableAt(this, toolbar.getComponents(), x, y);
	}



	@Override
	public Picker getPickerAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickerAt(this, toolbar.getComponents(), x, y);
	}



	@Override
	public Point2D getRelativePoint(final double x, final double y, final Object o){
		return WidgetUtilities.INSTANCE.getRelativePoint(toolbar.getComponents(), x, y, o);
	}


	/**
	 * Attaches the given component to the event manager of the panel if it exists.
	 * @param comp
	 * @since 3.0
	 */
	private void attachAddedComponent(final Component comp) {
		if(comp!=null && eventManager!=null)
			eventManager.attachTo(comp);
	}



	@Override
	public boolean hasEventManager() {
		return eventManager!=null;
	}


	@Override
	public SwingEventManager getEventManager() {
		return eventManager;
	}


	/**
	 * @return The toolbar associated to the toggle button.
	 * @since 3.0
	 */
	public MToolBar getToolbar() {
		return toolbar;
	}


	@Override
	public void repaint() {
		// The frame must be repack to update its layout.
		if(buttonsFrame!=null)
			buttonsFrame.pack();

		super.repaint();
	}


	class ListToggleButtonAWTEventListener implements AWTEventListener {
		protected ListToggleButtonAWTEventListener() {
			super();
		}

		@Override
		public void eventDispatched(final AWTEvent event) {
	    	// Only if the toolbar is visible and the event is a mouse event.
	    	if(buttonsFrame.isVisible() && (event.getID()==MouseEvent.MOUSE_PRESSED || event.getID()==MouseEvent.MOUSE_CLICKED) &&
    		   event.getSource() instanceof Component && event instanceof MouseEvent) {
	    		final MouseEvent me 	= (MouseEvent)event;
	    		final Component comp 	= (Component)me.getSource();
	    		final Point pt 			= new Point(me.getPoint());

	    		// Converting the mouse event point into a screen point.
	    		SwingUtilities.convertPointToScreen(pt, comp);

	    		// The toolbar is hidden if:
	    		// - the button of the toolbar does not contain the point of the event.
	    		// - the toolbar does not contain the point of the event.
	    		// - the widget concerned by the event is not a widget of the toolbar (necessary to manage widgets
	    		// which size is not contained into the toolbar such as ComboBoxes).
		    	if(!new Rectangle(buttonsFrame.getLocationOnScreen(), buttonsFrame.getSize()).contains(pt) &&
		    		!new Rectangle(ListToggleButton.this.getLocationOnScreen(), ListToggleButton.this.getSize()).contains(pt) &&
		    		SwingUtilities.getAncestorOfClass(WindowWidgets.class, comp)!=buttonsFrame)
		    		ListToggleButton.this.buttonsFrame.setVisible(false);
		    	}
		}

	}


	class WindowWidgets extends JWindow implements WindowListener {
		private static final long serialVersionUID = 1L;

		protected WindowWidgets(final JFrame owner, final JPanel buttonsPanel, final ListToggleButton list) {
			super(owner);

			setVisible(false);
			setAlwaysOnTop(true);
			add(buttonsPanel);
			pack();
			addWindowFocusListener(list);
			owner.addWindowListener(this);
		}

		@Override
		public void windowOpened(final WindowEvent e) {
			// Nothing to do.
		}

		@Override
		public void windowClosing(final WindowEvent e) {
			// Nothing to do.
		}

		@Override
		public void windowClosed(final WindowEvent e) {
			// Nothing to do.
		}

		@Override
		public void windowIconified(final WindowEvent e) {
			// Nothing to do.
		}

		@Override
		public void windowDeiconified(final WindowEvent e) {
			// Nothing to do.
		}

		@Override
		public void windowActivated(final WindowEvent e) {
			// Nothing to do.
		}

		@Override
		public void windowDeactivated(final WindowEvent e) {
			// Need to hide the window when the main frame is no more visible.
			setVisible(false);
		}
	}
}
