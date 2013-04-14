package net.sf.latexdraw.ui;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.glib.views.pst.PSTCodeGenerator;
import net.sf.latexdraw.instruments.*;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.mapping.Drawing2CanvasMapping;
import net.sf.latexdraw.mapping.Selection2BorderMapping;
import net.sf.latexdraw.mapping.Selection2DeleterMapping;
import net.sf.latexdraw.mapping.Selection2MetaCustumiserMapping;
import net.sf.latexdraw.mapping.Selection2TemplateManager;
import net.sf.latexdraw.mapping.ShapeList2ExporterMapping;
import net.sf.latexdraw.mapping.ShapeList2ViewListMapping;
import net.sf.latexdraw.mapping.Unit2ScaleRuler;
import net.sf.latexdraw.mapping.Zoom2ScaleRuler;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LResources;
import net.sf.latexdraw.util.VersionChecker;

import org.malai.instrument.Instrument;
import org.malai.mapping.MappingRegistry;
import org.malai.presentation.Presentation;
import org.malai.swing.instrument.library.Scroller;
import org.malai.swing.instrument.library.UndoRedoManager;
import org.malai.swing.instrument.library.WidgetZoomer;
import org.malai.swing.ui.UI;
import org.malai.swing.ui.UIManager;
import org.malai.swing.widget.MLayeredPane;
import org.malai.swing.widget.MProgressBar;
import org.malai.swing.widget.MTabbedPane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class contains all the elements of the graphical user interface.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
	protected WidgetZoomer zoomer;

	/** The scroller used to scroll the canvas. */
	protected Scroller scroller;

	/** The instrument that saves and loads SVG documents. */
	protected FileLoaderSaver fileLoader;

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

	/** The status-bar of the frame. */
	protected JLabel statusBar;

	/** The ruler used to display X-dimensions. */
	protected XScaleRuler xScaleRuler;

	/** The ruler used to display X-dimensions. */
	protected YScaleRuler yScaleRuler;

	/** The instrument that customises the magnetic grid. */
	protected MagneticGridCustomiser gridCustomiser;

	/** The instrument that customises the properties of the drawing. */
	protected DrawingPropertiesCustomiser drawingPropCustomiser;

	/** The instrument that copies, cuts and pastes selected shapes. */
	protected CopierCutterPaster paster;

	/** The instrument that selects the current tab. */
	protected TabSelector tabSelector;

	/** The instrument that manages the templates. */
	protected TemplateManager templateManager;

	/** The instrument that converts PST code into shapes. */
	protected CodeInserter codeInserter;

	/** The layered panel used to display widgets upon shapes (e.g. text setters). */
	protected MLayeredPane layeredPanel;

	/** The panel that contains the tabs of the app. */
	protected MTabbedPane tabbedPanel;



	/**
	 * Creates and initialises the frame.
	 * @param progressBar The progress bar used to show the progression of the construction of the frame. Can be null.
	 * @since 3.0
	 */
	public LFrame(final MProgressBar progressBar) {
		super();
		composer = new UIBuilder(this);
		buildFrame(progressBar);
	}


	/**
	 * The builder of the frame.
	 * @since 3.0
	 */
	private void buildFrame(final MProgressBar progressBar) {
		final LCanvas canvas 	= getCanvas();
		final IDrawing drawing	= getDrawing();

		tabbedPanel = new MTabbedPane(true);

		layeredPanel = new MLayeredPane(false, false);
		layeredPanel.add(canvas.getScrollpane(), JLayeredPane.DEFAULT_LAYER);
		layeredPanel.addComponentsToResize(canvas.getScrollpane());

		if(progressBar!=null)
			progressBar.addToProgressBar(5);

		/* Creation of the rulers. */
		yScaleRuler = new YScaleRuler(canvas);
		xScaleRuler = new XScaleRuler(canvas, yScaleRuler);

		if(progressBar!=null)
			progressBar.addToProgressBar(5);

		/* Initialisation of the mapping between the model and the canvas. */
		MappingRegistry.REGISTRY.addMapping(new ShapeList2ViewListMapping(drawing.getShapes(), canvas.getViews(), canvas.getBorderInstrument()));
		MappingRegistry.REGISTRY.addMapping(new Drawing2CanvasMapping(drawing, canvas));
		MappingRegistry.REGISTRY.addMapping(new Selection2BorderMapping(drawing.getSelection().getShapes(), canvas.getBorderInstrument()));
		MappingRegistry.REGISTRY.addMapping(new Zoom2ScaleRuler(canvas.getZoomUnary(), xScaleRuler));
		MappingRegistry.REGISTRY.addMapping(new Zoom2ScaleRuler(canvas.getZoomUnary(), yScaleRuler));
		MappingRegistry.REGISTRY.addMapping(new Unit2ScaleRuler(ScaleRuler.getUnitSingleton(), xScaleRuler));
		MappingRegistry.REGISTRY.addMapping(new Unit2ScaleRuler(ScaleRuler.getUnitSingleton(), yScaleRuler));

		if(progressBar!=null)
			progressBar.addToProgressBar(5);

		// Initialisation of the status bar.
		statusBar = new JLabel("");//$NON-NLS-1$
//		statusBar.setEditable(false);

		/* Creation of the instruments. */
		instantiateInstruments(canvas, drawing);

		if(progressBar!=null)
			progressBar.addToProgressBar(15);

		textSetter.setPencil(pencil);
		MappingRegistry.REGISTRY.addMapping(new ShapeList2ExporterMapping(drawing.getShapes(), exporter));
		MappingRegistry.REGISTRY.addMapping(new Selection2MetaCustumiserMapping(drawing.getSelection().getShapes(), metaShapeCustomiser));
		MappingRegistry.REGISTRY.addMapping(new Selection2DeleterMapping(drawing.getSelection().getShapes(), deleter));
		MappingRegistry.REGISTRY.addMapping(new Selection2TemplateManager(drawing.getSelection().getShapes(), templateManager));

		if(progressBar!=null)
			progressBar.addToProgressBar(5);

		try{setIconImage(LResources.LATEXDRAW_ICON.getImage());}catch(Exception ex){BadaboomCollector.INSTANCE.add(ex);}
		setTitle(LResources.LABEL_APP);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		UIManager.INSTANCE.registerUI(this);

     	if(progressBar!=null)
     		progressBar.addToProgressBar(5);
	}


	@Override
	public UIBuilder getComposer() {
		return (UIBuilder)composer;
	}


	private void instantiateInstruments(final LCanvas canvas, final IDrawing drawing) {
		final PSTCodeGenerator gen = getCodePanel().getPstGenerator();

		exceptionsManager	= new ExceptionsManager();
		helper				= new Helper(composer);
		try { gridCustomiser= new MagneticGridCustomiser(composer, canvas.getMagneticGrid()); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { drawingPropCustomiser= new DrawingPropertiesCustomiser(composer, gen); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { scaleRulersCustomiser = new ScaleRulersCustomiser(xScaleRuler, yScaleRuler); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { scroller		= new Scroller(canvas); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { zoomer		= new WidgetZoomer(canvas, true, true, LResources.ZOOM_DEFAULT_ICON, LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.113"),
				new Dimension(55, 28), LangTool.INSTANCE.getString19("ShortcutsFrame.30"), true); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { textSetter	= new TextSetter(layeredPanel); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { deleter		= new ShapeDeleter(composer); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { hand 			= new Hand(canvas, canvas.getMagneticGrid(), zoomer, textSetter); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { pencil 		= new Pencil(canvas, zoomer, canvas.getMagneticGrid(), textSetter); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { exporter		= new Exporter(composer, canvas, drawing, statusBar, gen); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try {
			metaShapeCustomiser = new MetaShapeCustomiser(composer, hand, pencil, canvas.getBorderInstrument());
			canvas.getBorderInstrument().setMetaCustomiser(metaShapeCustomiser);
			hand.setMetaCustomiser(metaShapeCustomiser);
		} catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { codeInserter = new CodeInserter(canvas, getStatusBar()); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { editingSelector = new EditingSelector(composer, pencil, hand, metaShapeCustomiser, canvas.getBorderInstrument(), deleter, codeInserter); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		undoManager			= new UndoRedoManager(composer);
		try { paster		= new CopierCutterPaster(composer, drawing); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		prefSetters			= new PreferencesSetter(this);
		try { prefActivator	= new PreferencesActivator(composer, prefSetters); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { fileLoader	= new FileLoaderSaver(this, statusBar, prefSetters); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { tabSelector	= new TabSelector(this); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
		try { templateManager = new TemplateManager(composer, this); }
		catch(final IllegalArgumentException ex) {BadaboomCollector.INSTANCE.add(ex); }
	}


	@Override
	public void reinit() {
		super.reinit();
		setTitle(LResources.LABEL_APP);
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
		final IDrawing drawing	= DrawingTK.getFactory().createDrawing();
		final LCanvas canvas	= new LCanvas(drawing);
		presentations.add(new Presentation<>(drawing, canvas));
		presentations.add(new Presentation<>(drawing, new LCodePanel(drawing, canvas)));
	}



	@Override
	public void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root) {
		super.save(generalPreferences, nsURI, document, root);

    	Element elt = document.createElement(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_VERSION);
    	Element elt2;

    	// Saving the version.
    	elt.appendChild(document.createTextNode(VersionChecker.VERSION));
    	root.appendChild(elt);

    	// Saving the dimensions of the frame.
        elt = document.createElement(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_SIZE);
        root.appendChild(elt);

        elt2 = document.createElement(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_WIDTH);
        elt2.setTextContent(String.valueOf(getWidth()));
        elt.appendChild(elt2);

        elt2 = document.createElement(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_HEIGHT);
        elt2.setTextContent(String.valueOf(getHeight()));
        elt.appendChild(elt2);

		// Saving the position.
		final Point location = getLocation();
        elt = document.createElement(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_POSITION);
        root.appendChild(elt);

        elt2 = document.createElement(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_POSITION_X);
        elt2.setTextContent(String.valueOf(location.getX()));
        elt.appendChild(elt2);

        elt2 = document.createElement(LNamespace.LATEXDRAW_NAMESPACE+':'+LNamespace.XML_POSITION_Y);
        elt2.setTextContent(String.valueOf(location.getY()));
        elt.appendChild(elt2);
	}



	@Override
	public void load(final boolean generalPreferences, final String nsURI, final Element meta) {
		super.load(generalPreferences, nsURI, meta);

		Node node;
		final NodeList metaNodes = meta.getChildNodes();
		String name;

		for(int i=0, size = metaNodes.getLength(); i<size; i++) {
			node = metaNodes.item(i);
			name = node.getNodeName();

			if(name.endsWith(LNamespace.XML_SIZE)) {
				final NodeList nlSize = node.getChildNodes();
				Node nodeSize;

				for(int j=0, size2 = nlSize.getLength(); j<size2; j++) {
					nodeSize = nlSize.item(j);
					name = nodeSize.getNodeName();

					if(name.endsWith(LNamespace.XML_WIDTH))
						setSize(Double.valueOf(nodeSize.getTextContent()).intValue(), getHeight());
					else
						if(name.endsWith(LNamespace.XML_HEIGHT))
							setSize(getWidth(), Double.valueOf(nodeSize.getTextContent()).intValue());
				}
			}else
			// Loading the position.
			if(name.endsWith(LNamespace.XML_POSITION)) {
				final NodeList nlPos = node.getChildNodes();
				Node nodePosition;

				for(int j=0, sizeNodePos = nlPos.getLength(); j<sizeNodePos; j++) {
					nodePosition = nlPos.item(j);
					name = nodePosition.getNodeName();

					if(name.endsWith(LNamespace.XML_POSITION_X))
						setLocation(Math.max(0, Double.valueOf(nodePosition.getTextContent()).intValue()), (int)getLocation().getY());
					else
						if(name.endsWith(LNamespace.XML_POSITION_Y))
							setLocation((int)getLocation().getX(), Math.max(0, Double.valueOf(nodePosition.getTextContent()).intValue()));
				}
			}
		}
	}



	@Override
	public Instrument[] getInstruments() {
		return new Instrument[]{editingSelector, exporter, fileLoader, hand, pencil, metaShapeCustomiser, undoManager,
								zoomer, scaleRulersCustomiser, scroller, gridCustomiser, helper, textSetter, exceptionsManager,
								deleter, prefActivator, prefSetters, paster, getCanvas().getBorderInstrument(), tabSelector,
								drawingPropCustomiser, templateManager};
	}


	/**
	 * @return The instrument that saves and loads SVG documents.
	 * @since 3.0
	 */
	public FileLoaderSaver getFileLoader() {
		return fileLoader;
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

	/**
	 * @return The instrument that manages the preferences.
	 * @since 3.0
	 */
	public PreferencesSetter getPrefSetters() {
		return prefSetters;
	}


	/**
	 * @return The instrument that customises the drawing's properties.
	 * @since 3.0
	 */
	public final DrawingPropertiesCustomiser getDrawingPropCustomiser() {
		return drawingPropCustomiser;
	}


	/**
	 * @return the metaShapeCustomiser.
	 * @since 3.0
	 */
	public MetaShapeCustomiser getMetaShapeCustomiser() {
		return metaShapeCustomiser;
	}


	/**
	 * @return the hand.
	 * @since 3.0
	 */
	public Hand getHand() {
		return hand;
	}


	/**
	 * @return the pencil.
	 * @since 3.0
	 */
	public Pencil getPencil() {
		return pencil;
	}


	/**
	 * @return the textSetter.
	 * @since 3.0
	 */
	public TextSetter getTextSetter() {
		return textSetter;
	}


	/**
	 * @return the editingSelector.
	 * @since 3.0
	 */
	public EditingSelector getEditingSelector() {
		return editingSelector;
	}


	/**
	 * @return the undoManager.
	 * @since 3.0
	 */
	public UndoRedoManager getUndoManager() {
		return undoManager;
	}


	/**
	 * @return the zoomer.
	 * @since 3.0
	 */
	public WidgetZoomer getZoomer() {
		return zoomer;
	}


	/**
	 * @return the deleter.
	 * @since 3.0
	 */
	public ShapeDeleter getDeleter() {
		return deleter;
	}


	/**
	 * @return the paster.
	 * @since 3.0
	 */
	public CopierCutterPaster getPaster() {
		return paster;
	}


	/**
	 * @return the tabbedPanel.
	 * @since 3.0
	 */
	public MTabbedPane getTabbedPanel() {
		return tabbedPanel;
	}


	/**
	 * @return The status bar used to display some feedback information.
	 * @since 3.0
	 */
	public JLabel getStatusBar() {
		return statusBar;
	}
}

