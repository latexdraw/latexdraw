package net.sf.latexdraw.ui;

import java.awt.BorderLayout;

import javax.swing.JLayeredPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.malai.instrument.Instrument;
import org.malai.instrument.library.Scroller;
import org.malai.instrument.library.UndoRedoManager;
import org.malai.mapping.MappingRegistry;
import org.malai.presentation.Presentation;
import org.malai.ui.IProgressBar;
import org.malai.ui.UI;
import org.malai.ui.UIManager;
import org.malai.widget.MLayeredPane;
import org.malai.widget.MPanel;

import net.sf.latexdraw.badaboom.BordelCollector;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.ui.ICanvas;
import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.instruments.CodePanelActivator;
import net.sf.latexdraw.instruments.CopierCutterPaster;
import net.sf.latexdraw.instruments.EditingSelector;
import net.sf.latexdraw.instruments.ExceptionsManager;
import net.sf.latexdraw.instruments.Exporter;
import net.sf.latexdraw.instruments.FileLoaderSaver;
import net.sf.latexdraw.instruments.Hand;
import net.sf.latexdraw.instruments.Helper;
import net.sf.latexdraw.instruments.MagneticGridCustomiser;
import net.sf.latexdraw.instruments.MetaShapeCustomiser;
import net.sf.latexdraw.instruments.Pencil;
import net.sf.latexdraw.instruments.PreferencesActivator;
import net.sf.latexdraw.instruments.PreferencesSetter;
import net.sf.latexdraw.instruments.ScaleRulersCustomiser;
import net.sf.latexdraw.instruments.ShapeDeleter;
import net.sf.latexdraw.instruments.TextSetter;
import net.sf.latexdraw.instruments.Zoomer;
import net.sf.latexdraw.mapping.Drawing2CanvasMapping;
import net.sf.latexdraw.mapping.Selection2BorderMapping;
import net.sf.latexdraw.mapping.Selection2DeleterMapping;
import net.sf.latexdraw.mapping.Selection2MetaCustumiserMapping;
import net.sf.latexdraw.mapping.ShapeList2ExporterMapping;
import net.sf.latexdraw.mapping.ShapeList2ViewListMapping;
import net.sf.latexdraw.mapping.TempShape2TempViewMapping;
import net.sf.latexdraw.mapping.Unit2ScaleRuler;
import net.sf.latexdraw.mapping.Zoom2ScaleRuler;
import net.sf.latexdraw.util.LResources;

/**
 * This class contains all the elements of the graphical user interface.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 * <br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 03/11/08<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class LFrame extends UI {
	private static final long serialVersionUID = 1L;

	/** The instrument used to select, move, etc. shapes. */
	protected Hand hand;

	/** The instrument used to draw shapes. */
	protected Pencil pencil;

	/** The instrument used to add and modify texts. */
	protected TextSetter textSetter;

	/** The instrument used to select/activate the hand, the pencil, or any of the editing instruments. */
	protected EditingSelector editingSelector;

	/** This instrument manages the instruments that customise shapes and the pencil. */
	protected MetaShapeCustomiser metaShapeCustomiser;

	/** The instrument that manages undo/redo actions. */
	protected UndoRedoManager undoManager;

	/** The instrument that zoomes in/out the canvas. */
	protected Zoomer zoomer;

	/** The scroller used to scroll the canvas. */
	protected Scroller scroller;

	/** The instrument that saves and loads SVG documents. */
	protected FileLoaderSaver fileLoader;

	/** The instrument that (des-)activates the code panel. */
	protected CodePanelActivator codePanelActivator;

	/** The instrument that (des-)activates the scale rulers. */
	protected ScaleRulersCustomiser scaleRulersCustomiser;

	/** The instrument that manages help features. */
	protected Helper helper;

	/** The instrument that exports drawings as picture or code. */
	protected Exporter exporter;

	/** The instrument allows to see exceptions. */
	protected ExceptionsManager exceptionsManager;

	/** The instrument that activates the preferences setter. */
	protected PreferencesActivator prefActivator;

	/** The instrument that sets the preferences. */
	protected PreferencesSetter prefSetters;

	/** The instrument that removes shapes. */
	protected ShapeDeleter deleter;

	/** The top toolbar of the editor. */
	protected LToolbar toolbar;

	/** The bottom tool bar of the editor. */
	protected LPropertiesToolbar propertiesToolbar;

	/** The split pane that separates the drawing area from the code area. */
	protected JSplitPane splitPane;

	/** The menu bar of the interactive system. */
	protected LMenuBar menuBar;

	/** The status-bar of the frame. */
	protected JTextField statusBar;

	/** The ruler used to display X-dimensions. */
	protected XScaleRuler xScaleRuler;

	/** The ruler used to display X-dimensions. */
	protected YScaleRuler yScaleRuler;

	/** The instrument that customises the magnetic grid. */
	protected MagneticGridCustomiser gridCustomiser;

	/** The instrument that copies, cuts and pastes selected shapes. */
	protected CopierCutterPaster paster;



	/**
	 * Creates and initialises the frame.
	 * @param progressBar The progress bar used to show the progression of the construction of the frame. Can be null.
	 * @since 3.0
	 */
	public LFrame(final IProgressBar progressBar) {
		super();

		final LCanvas canvas 		= getCanvas();
		final IDrawing drawing		= getDrawing();
		final LCodePanel codePanel	= getCodePanel();
		final MLayeredPane layeredPanel	= new MLayeredPane(false, false);
		layeredPanel.add(canvas.getScrollpane(), JLayeredPane.DEFAULT_LAYER);
		layeredPanel.addComponentsToResize(canvas.getScrollpane());

		if(progressBar!=null)
			progressBar.addToProgressBar(15);

		/* Creation of the rulers. */
		yScaleRuler = new YScaleRuler(canvas);
		xScaleRuler = new XScaleRuler(canvas, yScaleRuler);

		/* Initialisation of the mapping between the model and the canvas. */
		MappingRegistry.REGISTRY.addMapping(new ShapeList2ViewListMapping(drawing.getShapes(), canvas.getViews()));
		MappingRegistry.REGISTRY.addMapping(new TempShape2TempViewMapping(drawing.getSingletonTempShape(), canvas.getSingletonTempView()));
		MappingRegistry.REGISTRY.addMapping(new Drawing2CanvasMapping(drawing, canvas));
		MappingRegistry.REGISTRY.addMapping(new Selection2BorderMapping(drawing.getSelection().getShapes(), canvas.getBorderInstrument()));
		MappingRegistry.REGISTRY.addMapping(new Zoom2ScaleRuler(canvas.getZoomSingleton(), xScaleRuler));
		MappingRegistry.REGISTRY.addMapping(new Zoom2ScaleRuler(canvas.getZoomSingleton(), yScaleRuler));
		MappingRegistry.REGISTRY.addMapping(new Unit2ScaleRuler(ScaleRuler.getUnitSingleton(), xScaleRuler));
		MappingRegistry.REGISTRY.addMapping(new Unit2ScaleRuler(ScaleRuler.getUnitSingleton(), yScaleRuler));

		if(progressBar!=null)
			progressBar.addToProgressBar(10);

		/* Creation of the drawing area composed of the canvas, the scales, etc. */
		final MPanel drawingArea = new MPanel(false, false);
		drawingArea.setLayout(new BorderLayout());
		drawingArea.add(xScaleRuler, BorderLayout.NORTH);
		drawingArea.add(yScaleRuler, BorderLayout.WEST);
		drawingArea.add(layeredPanel, BorderLayout.CENTER);

		/* Creation of the split pane. */
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, drawingArea, codePanel);
    	splitPane.setContinuousLayout(true);
     	splitPane.setOneTouchExpandable(true);
     	splitPane.setDividerSize(6);

		if(progressBar!=null)
			progressBar.addToProgressBar(10);

		// Initilisation of the status bar.
		statusBar = new JTextField("");//$NON-NLS-1$
		statusBar.setEditable(false);

		/* Creation of the instruments. */
		instantiateInstruments(canvas, layeredPanel, drawing, codePanel);

		textSetter.setPencil(pencil);
		MappingRegistry.REGISTRY.addMapping(new ShapeList2ExporterMapping(drawing.getShapes(), exporter));
		MappingRegistry.REGISTRY.addMapping(new Selection2MetaCustumiserMapping(drawing.getSelection().getShapes(), metaShapeCustomiser));
		MappingRegistry.REGISTRY.addMapping(new Selection2DeleterMapping(drawing.getSelection().getShapes(), deleter));

		if(progressBar!=null)
			progressBar.addToProgressBar(15);

		/* Creation of the menu bar. */
		menuBar = new LMenuBar(this);

		if(progressBar!=null)
			progressBar.addToProgressBar(5);

		/* Creation of the toolbar using the widgets of the instruments. */
		toolbar = new LToolbar(this);

		if(progressBar!=null)
			progressBar.addToProgressBar(10);

		/* Creation of the bottom tool bar that contains widgets to customise the pencil, to rotate shapes, etc. */
		propertiesToolbar = new LPropertiesToolbar(metaShapeCustomiser, this);

		if(progressBar!=null)
			progressBar.addToProgressBar(10);

		/* Mapping the instruments to the widgets that produce events they must listen. */
		setEventableToInstruments(canvas);

		/* Activating the instruments. */
		initialiseInstrumentsActivation();

     	if(progressBar!=null)
     		progressBar.addToProgressBar(10);

		setIconImage(LResources.LATEXDRAW_ICON.getImage());
		setTitle(LResources.LABEL_APP);

		try { prefSetters.readXMLPreferences(); }
		catch(Exception ex) { BordelCollector.INSTANCE.add(ex); }

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		UIManager.INSTANCE.registerUI(this);
	}


	private void initialiseInstrumentsActivation() {
		prefActivator.setActivated(true);
		helper.setActivated(true);
		gridCustomiser.setActivated(true);
		scroller.setActivated(true);
		exporter.setActivated(false);
		editingSelector.setActivated(true);
		undoManager.setActivated(true);
		zoomer.setActivated(true);
		codePanelActivator.setActivated(true);
		fileLoader.setActivated(true);
		scaleRulersCustomiser.setActivated(true);
		paster.setActivated(true);
	}


	private void instantiateInstruments(final LCanvas canvas, final MLayeredPane layeredPanel, final IDrawing drawing,
										final LCodePanel codePanel) {
		exceptionsManager	= new ExceptionsManager();
		helper				= new Helper();
		try { gridCustomiser= new MagneticGridCustomiser(canvas.getMagneticGrid()); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		try { scaleRulersCustomiser = new ScaleRulersCustomiser(xScaleRuler, yScaleRuler); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		try { scroller		= new Scroller(canvas); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		try { zoomer		= new Zoomer(canvas); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		try { hand 			= new Hand(canvas, canvas.getMagneticGrid(), zoomer); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		try { textSetter	= new TextSetter(layeredPanel); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		try { pencil 		= new Pencil(drawing, zoomer, canvas.getMagneticGrid(), textSetter); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		try { metaShapeCustomiser = new MetaShapeCustomiser(hand, pencil); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		deleter				= new ShapeDeleter();
		try { editingSelector = new EditingSelector(pencil, hand, metaShapeCustomiser, canvas.getBorderInstrument(), deleter); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		undoManager			= new UndoRedoManager();
		try { codePanelActivator = new CodePanelActivator(codePanel, splitPane); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		try { exporter		= new Exporter(canvas, drawing, statusBar); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		try { paster		= new CopierCutterPaster(drawing); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		prefSetters			= new PreferencesSetter(this);
		try { prefActivator	= new PreferencesActivator(prefSetters); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
		try { fileLoader	= new FileLoaderSaver(this, statusBar, prefSetters); }
		catch(IllegalArgumentException ex) {BordelCollector.INSTANCE.add(ex); }
	}


	@Override
	public void reinit() {
		super.reinit();
		setTitle(LResources.LABEL_APP);
	}


	/**
	 * Sets the eventable objects to the instruments.
	 * @since 3.0
	 */
	protected void setEventableToInstruments(final LCanvas canvas) {
		prefActivator.addEventable(menuBar.editMenu);
		exceptionsManager.addEventable(toolbar);
		scroller.addEventable(canvas);
		editingSelector.addEventable(toolbar);
		editingSelector.addEventable(toolbar.recListB.getToolbar());
		editingSelector.addEventable(toolbar.polygonListB.getToolbar());
		editingSelector.addEventable(toolbar.ellipseListB.getToolbar());
		editingSelector.addEventable(toolbar.gridListB.getToolbar());
		editingSelector.addEventable(toolbar.arcListB.getToolbar());
		editingSelector.addEventable(toolbar.bezierListB.getToolbar());
		hand.addEventable(canvas);
		pencil.addEventable(canvas);
		undoManager.addEventable(toolbar);
		zoomer.addEventable(toolbar);
		zoomer.addEventable(canvas);
		codePanelActivator.addEventable(getCodePanel());
		codePanelActivator.addEventable(menuBar.displayMenu);
		exporter.addEventable(toolbar);
		exporter.addEventable(exporter.getExportMenu());
		scaleRulersCustomiser.addEventable(menuBar.displayMenu);
		scaleRulersCustomiser.addEventable(menuBar.unitMenu);
		gridCustomiser.addEventable(toolbar.magneticGridB.getToolbar());
		helper.addEventable(menuBar.helpMenu);
		setGlobalShortcutEventable(deleter, canvas);
		setGlobalShortcutEventable(paster, canvas);
		setGlobalShortcutEventable(fileLoader, canvas);
		fileLoader.addEventable(this);
	}


	protected void setGlobalShortcutEventable(final Instrument instrument, final LCanvas canvas) {
		if(instrument!=null) {
			instrument.addEventable(toolbar);
			instrument.addEventable(propertiesToolbar);
			instrument.addEventable(canvas);
			instrument.addEventable(menuBar.displayMenu);
			instrument.addEventable(menuBar.drawingMenu);
			instrument.addEventable(menuBar.editMenu);
			instrument.addEventable(menuBar.helpMenu);
			instrument.addEventable(menuBar.unitMenu);
		}
	}


	/**
	 * @return The drawing that contains the shapes.
	 * @since 3.0
	 */
	public IDrawing getDrawing() {
		return getPresentation(IDrawing.class, LCanvas.class).getAbstractPresentation();
	}



	/**
	 * @return The canvas that contains the views of the shapes.
	 * @since 3.0
	 */
	public LCanvas getCanvas() {
		return getPresentation(IDrawing.class, LCanvas.class).getConcretePresentation();
	}


	/**
	 * @return The code panel of the system.
	 * @since 3.0
	 */
	public LCodePanel getCodePanel() {
		return getPresentation(IDrawing.class, LCodePanel.class).getConcretePresentation();
	}


	@Override
	public void initialisePresentations() {
		IDrawing drawing= DrawingTK.getFactory().createDrawing();
		LCanvas canvas	= new LCanvas();
		presentations.add(new Presentation<IDrawing, ICanvas>(drawing, canvas));
		presentations.add(new Presentation<IDrawing, LCodePanel>(drawing, new LCodePanel(drawing, canvas)));
	}



	@Override
	public Instrument[] getInstruments() {
		return new Instrument[]{codePanelActivator, editingSelector, exporter, fileLoader, hand, pencil, metaShapeCustomiser, undoManager,
								zoomer, scaleRulersCustomiser, scroller, gridCustomiser, helper, textSetter, exceptionsManager,
								deleter, prefActivator, prefSetters};
	}


	/**
	 * @return The instrument that saves and loads SVG documents.
	 * @since 3.0
	 */
	public FileLoaderSaver getFileLoader() {
		return fileLoader;
	}

	/**
	 * @return The instrument that (des-)activates the code panel.
	 * @since 3.0
	 */
	public CodePanelActivator getCodePanelActivator() {
		return codePanelActivator;
	}

	/**
	 * @return The instrument that exports drawings as picture or code.
	 * @since 3.0
	 */
	public Exporter getExporter() {
		return exporter;
	}

	/**
	 * @return The instrument that customises the magnetic grid.
	 * @since 3.0
	 */
	public MagneticGridCustomiser getGridCustomiser() {
		return gridCustomiser;
	}

	/**
	 * @return The instrument that (des-)activates the scale rulers.
	 * @since 3.0
	 */
	public ScaleRulersCustomiser getScaleRulersCustomiser() {
		return scaleRulersCustomiser;
	}
}

