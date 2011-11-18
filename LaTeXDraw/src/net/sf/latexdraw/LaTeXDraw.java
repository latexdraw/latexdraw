package net.sf.latexdraw;

import java.io.File;

import org.malai.action.ActionsRegistry;
import org.malai.mapping.MappingRegistry;
import org.malai.ui.UIComposer;
import org.malai.undo.UndoCollector;

import net.sf.latexdraw.actions.LoadDrawing;
import net.sf.latexdraw.generators.svg.SVGDocumentGenerator;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.views.Java2D.LViewsFactory;
import net.sf.latexdraw.glib.views.Java2D.interfaces.View2DTK;
import net.sf.latexdraw.ui.LFrame;
import net.sf.latexdraw.ui.LUIComposer;
import net.sf.latexdraw.ui.SplashScreen;
import net.sf.latexdraw.util.LCommandLine;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.Theme;

/**
 * The main class of the project.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 01/20/06<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public final class LaTeXDraw {
	private LaTeXDraw() {
		super();
	}

	static{
		// Setting the size of the the saved actions.
		final int size = 30;
		UndoCollector.INSTANCE.setSizeMax(size);
		ActionsRegistry.INSTANCE.setSizeMax(size);

		// Creating the required directories.
		LPath.INSTANCE.checkDirectories();

		// Settings the theme.
		Theme.INSTANCE.setTheme();

		// Settings the factories.
		DrawingTK.setFactory(new LShapeFactory());
		View2DTK.setFactory(new LViewsFactory());
	}



	/**
	 * The main function.
	 * @param args The parameters given during the call of the program.
	 */
	public static void main(final String[] args) {
    	LCommandLine cmdLine = new LCommandLine();

    	switch(cmdLine.parse(args)) {
    		case APPLICATION:
    		case APPLICATION_FILENAME:// Create the frame of the application.
    			launchLatexdraw(cmdLine);
//	    	    	if(!System.getProperty("os.name").equals("Linux"))//$NON-NLS-1$//$NON-NLS-2$
//	    	    		frame.setVisible(true);
    			break;

    		case CONVERTION:
//	    			if(!BatchConvertFrame.convert(cmdLine.getFileConvertionSrc(), cmdLine.getFileConvertionTarget()))
//	    					System.out.println("The conversion failed.");
    			break;

    		case STOP:
    			break;
    	}
    }



    private static void launchLatexdraw(final LCommandLine cmdLine) {
    	// Creation of the splash screen.
		final SplashScreen splashScreen = new SplashScreen(Theme.INSTANCE.getLookAndFeel());
		splashScreen.setVisible(true);
		// Creation of the main frame.
    	LFrame frame = new LFrame(splashScreen);
    	// Creation of the UI composer.
    	UIComposer composer = new LUIComposer(frame, splashScreen);
    	// Composing the user interface.
    	composer.compose();
    	// Removing the splash screen.
    	splashScreen.setVisible(false);
    	// Showing the user interface.
    	MappingRegistry.REGISTRY.initMappings();
    	frame.setVisible(true);
    	frame.setModified(false);
    	frame.getCanvas().requestFocusInWindow();
    	// Flushes the resources.
    	splashScreen.flush();

    	if(cmdLine.getFilename()!=null) {
	    	LoadDrawing action = new LoadDrawing();
	    	action.setFile(new File(cmdLine.getFilename()));
	    	action.setUi(frame);
	    	action.setOpenSaveManager(SVGDocumentGenerator.SVG_GENERATOR);
	    	action.setFileChooser(frame.getFileLoader().getDialog(false));
	    	action.doIt();
    	}
    }
 }
