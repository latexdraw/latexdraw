package net.sf.latexdraw.instruments;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.latexdraw.actions.WritePreferences;
import net.sf.latexdraw.bordel.BordelCollector;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.ui.LCanvas;
import net.sf.latexdraw.glib.ui.LMagneticGrid.GridStyle;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.ui.LFrame;
import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.ui.ScaleRuler.Unit;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.VersionChecker;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import fr.eseo.malai.instrument.Instrument;
import fr.eseo.malai.instrument.Link;
import fr.eseo.malai.interaction.library.WindowClosed;
import fr.eseo.malai.widget.MCheckBox;
import fr.eseo.malai.widget.MComboBox;
import fr.eseo.malai.widget.MRadioButton;
import fr.eseo.malai.widget.MSpinner;
import fr.eseo.malai.widget.MTextArea;
import fr.eseo.malai.widget.MTextField;

/**
 * This instrument modifies the preferences.<br>
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
 * 01/18/11<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class PreferencesSetter extends Instrument {
	/** The file chooser of paths selection. */
	protected JFileChooser fileChooser;

	/** This check-box allows to set if antialiasing must be used. */
	protected MCheckBox antialiasingCheckBox;

	/** This check-box allows to set if rendering quality must be used. */
	protected MCheckBox renderingCheckBox;

	/** This check-box allows to set if colour rendering quality must be used. */
	protected MCheckBox colorRenderCheckBox;

	/** This check-box allows to set if the user wants to display the grid. */
	protected MCheckBox displayGridCB;

	/** The widget that defines if the grid is magnetic. */
	protected MCheckBox magneticGridCB;

	/** This check-box allows to set if the user wants to display the X-scale. */
	protected MCheckBox displayXScaleCB;

	/** This check-box allows to set if the user wants to display the Y-scale. */
	protected MCheckBox displayYScaleCB;

	/** Allows to update or not in real time the PSTricks code. */
	protected MCheckBox codeAutoUpdateCB;

	/** Allows the set if the program must check new version on start up. */
	protected MCheckBox checkNewVersion;

	/** This check-box allows to set if the user wants to display the code panel. */
	protected MCheckBox displayCodePanelCB;

	/** This check-box allows to set if the user wants to the borders of the drawing. */
	protected MCheckBox displayBordersCB;

	/** This check-box allows to set if alpha-interpolation must be used. */
	protected MCheckBox alpaInterCheckBox;

	/** This textField allows to set the default directories for open/save actions. */
	protected MTextField pathOpenField;

	/** This textField allows to set the default directories for exporting actions. */
	protected MTextField pathExportField;

	/** The field used to modifies the path of the selected latex editor. */
	protected MTextField pathTexEditorField;

	/** The field used to modifies the path of latex binaries. */
	protected MTextField pathLatexDistribField;

	/** The text field used to defines the latex packages to use. */
	protected MTextArea latexIncludes;

	/** Allows to set the unit of length by default. */
	protected MComboBox unitChoice;

	/** The list that contains the supported theme. */
	protected MComboBox themeList;

	/** The list that contains the supported languages. */
	protected MComboBox langList;

	/** The widget used to display the standard grid. */
	protected MRadioButton classicGridRB;

	/** The widget used to display a customised grid. */
	protected MRadioButton persoGridRB;

	/** The field used to modifies the gap of the customised grid. */
	protected MSpinner persoGridGapField;

	/** The widget used to defines the number of recent file to keep in memory. */
	protected MSpinner nbRecentFilesField;

	/** The recent files. */
	protected List<String> recentFilesName;

	/** Defines if the main frame is maximised or not. */
	protected boolean isFrameMaximized;

	/** Defines the size of the main frame. */
	protected Dimension frameSize;

	/** Defines the position of the main frame. */
	protected IPoint framePosition;

	/** Defines the position of the divider. */
	protected double dividerPosition;

	/** The main frame. */
	protected LFrame frame;


	/**
	 * Creates the instrument.
	 * @since 3.0
	 */
	public PreferencesSetter(final LFrame frame) {
		super();

		if(frame==null)
			throw new IllegalArgumentException();

		this.frame		 	= frame;
		framePosition	 	= DrawingTK.getFactory().createPoint();
		frameSize 			= new Dimension();
		frameSize.height 	= 3*Toolkit.getDefaultToolkit().getScreenSize().height/2;
		frameSize.width 	= 3*Toolkit.getDefaultToolkit().getScreenSize().width/2;
		recentFilesName  	= new ArrayList<String>();
		isFrameMaximized 	= false;
		dividerPosition  	= 1.;
		initialiseWidgets();
		initialiseLinks();
	}


	/**
	 * Initialises the widgets of the instrument.
	 * @since 3.0
	 */
	protected void initialiseWidgets() {
		final int height = 40;
  		pathLatexDistribField = new MTextField();
  		pathLatexDistribField.setMaximumSize(new Dimension(700, height));

  		latexIncludes = new MTextArea();
  		latexIncludes.setToolTipText("<html>Include in this list the latex packages you regularly use in your drawing, e.g. :<br>\\usepackage[frenchb]{babel}<br>\\usepackage[utf8]{inputenc}</html>");
  		pathTexEditorField = new MTextField();
  		pathTexEditorField.setMaximumSize(new Dimension(700, height));

  		checkNewVersion = new MCheckBox(LangTool.LANG.getStringDialogFrame("PreferencesFrame.newVers"));//$NON-NLS-1$
		if(VersionChecker.WITH_UPDATE)
			checkNewVersion.setSelected(true);

  		langList = new MComboBox();
  		langList.addItem(LangTool.Lang.DE.getName());
  		langList.addItem(LangTool.Lang.EN_BR.getName());
  		langList.addItem(LangTool.Lang.EN_US.getName());
  		langList.addItem(LangTool.Lang.ES.getName());
  		langList.addItem(LangTool.Lang.FR.getName());
  		langList.addItem(LangTool.Lang.IT.getName());
  		langList.addItem(LangTool.Lang.JA.getName());
  		langList.addItem(LangTool.Lang.HU.getName());
  		langList.addItem(LangTool.Lang.PL.getName());
  		langList.addItem(LangTool.Lang.PT_BR.getName());
  		langList.addItem(LangTool.Lang.VI.getName());
  		langList.addItem(LangTool.Lang.TR.getName());
  		langList.setMaximumSize(new Dimension(250, height));

  		SpinnerModel model = new SpinnerNumberModel(5, 0, 20, 1);
  		nbRecentFilesField = new MSpinner(model, new JLabel(LangTool.LANG.getString19("PreferencesFrame.0")));//$NON-NLS-1$
  		nbRecentFilesField.setEditor(new JSpinner.NumberEditor(nbRecentFilesField, "0"));//$NON-NLS-1$
  		nbRecentFilesField.setMaximumSize(new Dimension(60, height));

  		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
  		String[] nameThemes = new String[info.length];

  		for(int i=0; i<info.length;i++)
  			nameThemes[i] = info[i].getName();

  		themeList = new MComboBox(nameThemes);
  		themeList.setMaximumSize(new Dimension(160, height));

  		codeAutoUpdateCB 	= new MCheckBox(LangTool.LANG.getStringDialogFrame("PreferencesFrame.codeAuto"));//$NON-NLS-1$
  		codeAutoUpdateCB.setSelected(true);
  		classicGridRB  		= new MRadioButton(LangTool.LANG.getString18("PreferencesFrame.4")); //$NON-NLS-1$
  		classicGridRB.setSelected(false);
  		persoGridRB    		= new MRadioButton(LangTool.LANG.getString18("PreferencesFrame.5")); //$NON-NLS-1$
  		persoGridRB.setSelected(true);
  		displayGridCB      	= new MCheckBox(LangTool.LANG.getStringDialogFrame("PreferencesFrame.grid"));//$NON-NLS-1$
  		displayGridCB.setSelected(true);
  		magneticGridCB	   	= new MCheckBox(LangTool.LANG.getString18("PreferencesFrame.6")); //$NON-NLS-1$
  		magneticGridCB.setSelected(true);
     	model 			   	= new SpinnerNumberModel(20, 2, 100000, 1);
     	persoGridGapField  	= new MSpinner(model, new JLabel(LangTool.LANG.getString18("PreferencesFrame.7")));//$NON-NLS-1$
     	persoGridGapField.setEditor(new JSpinner.NumberEditor(persoGridGapField, "0"));//$NON-NLS-1$
     	persoGridGapField.setMaximumSize(new Dimension(60, height));

   		displayCodePanelCB 	= new MCheckBox(LangTool.LANG.getStringDialogFrame("PreferencesFrame.codePanel"));//$NON-NLS-1$
   		displayCodePanelCB.setSelected(true);
  		displayXScaleCB    	= new MCheckBox(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.38"));//$NON-NLS-1$
  		displayXScaleCB.setSelected(true);
  		displayYScaleCB    	= new MCheckBox(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.39"));//$NON-NLS-1$
  		displayYScaleCB.setSelected(true);
  		displayBordersCB   	= new MCheckBox(LangTool.LANG.getStringDialogFrame("PreferencesFrame.bordersDraw"));//$NON-NLS-1$
  		displayBordersCB.setSelected(false);
  		unitChoice 		   	= new MComboBox();
  		unitChoice.addItem(Unit.CM.getLabel());
  		unitChoice.addItem(Unit.INCH.getLabel());
  		unitChoice.setMaximumSize(new Dimension(160, height));
  		unitChoice.setSelectedItem(Unit.CM.getLabel());

  		pathExportField  	= new MTextField();
  		pathOpenField    	= new MTextField();

  		antialiasingCheckBox = new MCheckBox(LangTool.LANG.getStringDialogFrame("PreferencesFrame.antiAl"));//$NON-NLS-1$
  		renderingCheckBox    = new MCheckBox(LangTool.LANG.getStringDialogFrame("PreferencesFrame.rendQ"));//$NON-NLS-1$
  		colorRenderCheckBox  = new MCheckBox(LangTool.LANG.getStringDialogFrame("PreferencesFrame.colRendQ"));//$NON-NLS-1$
  		alpaInterCheckBox    = new MCheckBox(LangTool.LANG.getStringDialogFrame("PreferencesFrame.AlphaQ"));//$NON-NLS-1$
  		antialiasingCheckBox.setSelected(true);
  		renderingCheckBox.setSelected(true);
  		colorRenderCheckBox.setSelected(true);
  		alpaInterCheckBox.setSelected(true);
	}



	/**
	 * Adds a recent file.
	 * @param absolutePath The absolute path of the file to add.
	 */
	public void addRecentFile(final String absolutePath) {
		int i = recentFilesName.indexOf(absolutePath);
		int max = Float.valueOf(nbRecentFilesField.getValue().toString()).intValue();

		if(i!=-1)
			recentFilesName.remove(i);

		while(recentFilesName.size()>=max)
			recentFilesName.remove(max-1);

		recentFilesName.add(0, absolutePath);
		writeXMLPreferences();
	}


	@Override
	protected void initialiseLinks() {
		try{
			links.add(new CloseFrame2SavePreferences(this));
		}catch(InstantiationException e){
			BordelCollector.INSTANCE.add(e);
		}catch(IllegalAccessException e){
			BordelCollector.INSTANCE.add(e);
		}
	}


	/**
	 * @return The file chooser used to selected folders.
	 * @since 3.0
	 */
	public JFileChooser getFileChooser() {
		if(fileChooser==null) {
			fileChooser = new JFileChooser();
			fileChooser.setApproveButtonText(LangTool.LANG.getStringLaTeXDrawFrame("LaTeXDrawFrame.171"));	//$NON-NLS-1$
			fileChooser.setDialogTitle(LangTool.LANG.getStringDialogFrame("PreferencesFrame.selectFolder"));	//$NON-NLS-1$
			fileChooser.setMultiSelectionEnabled(false);
		}
		return fileChooser;
	}


	/**
	 * @return The check-box that allows to set if antialiasing must be used.
	 */
	public MCheckBox getAntialiasingCheckBox() {
		return antialiasingCheckBox;
	}

	/**
	 * @return The check-box that allows to set if rendering quality must be used.
	 */
	public MCheckBox getRenderingCheckBox() {
		return renderingCheckBox;
	}

	/**
	 * @return The check-box that allows to set if colour rendering quality must be used.
	 */
	public MCheckBox getColorRenderCheckBox() {
		return colorRenderCheckBox;
	}

	/**
	 * @return The check-box that allows to set if the user wants to display the grid.
	 */
	public MCheckBox getDisplayGridCB() {
		return displayGridCB;
	}

	/**
	 * @return The widget that defines if the grid is magnetic.
	 */
	public MCheckBox getMagneticGridCB() {
		return magneticGridCB;
	}

	/**
	 * @return This check-box allows to set if the user wants to display the X-scale.
	 */
	public MCheckBox getDisplayXScaleCB() {
		return displayXScaleCB;
	}

	/**
	 * @return This check-box allows to set if the user wants to display the Y-scale.
	 */
	public MCheckBox getDisplayYScaleCB() {
		return displayYScaleCB;
	}

	/**
	 * @return The widget used to update or not in real time the PSTricks code.
	 */
	public MCheckBox getCodeAutoUpdateCB() {
		return codeAutoUpdateCB;
	}

	/**
	 * @return The widget used to set if the program must check new version on start up.
	 */
	public MCheckBox getCheckNewVersion() {
		return checkNewVersion;
	}

	/**
	 * @return This check-box allows to set if the user wants to display the code panel.
	 */
	public MCheckBox getDisplayCodePanelCB() {
		return displayCodePanelCB;
	}

	/**
	 * @return This check-box allows to set if the user wants to the borders of the drawing.
	 */
	public MCheckBox getDisplayBordersCB() {
		return displayBordersCB;
	}

	/**
	 * @return This check-box allows to set if alpha-interpolation must be used.
	 */
	public MCheckBox getAlpaInterCheckBox() {
		return alpaInterCheckBox;
	}

	/**
	 * @return This textField allows to set the default directories for open/save actions.
	 */
	public MTextField getPathOpenField() {
		return pathOpenField;
	}

	/**
	 * @return This textField allows to set the default directories for exporting actions.
	 */
	public MTextField getPathExportField() {
		return pathExportField;
	}

	/**
	 * @return The field used to modifies the path of the selected latex editor.
	 */
	public MTextField getPathTexEditorField() {
		return pathTexEditorField;
	}

	/**
	 * @return The field used to modifies the path of latex binaries.
	 */
	public MTextField getPathLatexDistribField() {
		return pathLatexDistribField;
	}

	/**
	 * @return The text field used to defines the latex packages to use.
	 */
	public MTextArea getLatexIncludes() {
		return latexIncludes;
	}

	/**
	 * @return Allows to set the unit of length by default.
	 */
	public MComboBox getUnitChoice() {
		return unitChoice;
	}

	/**
	 * @return The list that contains the supported theme.
	 */
	public MComboBox getThemeList() {
		return themeList;
	}

	/**
	 * @return The list that contains the supported languages.
	 */
	public MComboBox getLangList() {
		return langList;
	}

	/**
	 * @return The widget used to display the standard grid.
	 */
	public MRadioButton getClassicGridRB() {
		return classicGridRB;
	}

	/**
	 * @return The widget used to display a customised grid.
	 */
	public MRadioButton getPersoGridRB() {
		return persoGridRB;
	}

	/**
	 * @return The field used to modifies the gap of the customised grid.
	 */
	public MSpinner getPersoGridGapField() {
		return persoGridGapField;
	}

	/**
	 * @return The widget used to defines the number of recent file to keep in memory.
	 */
	public MSpinner getNbRecentFilesField() {
		return nbRecentFilesField;
	}


	private void processXMLDataPreference(final Map<String, Node> prefMap) {
		Node n2;
		Node node;

		node = prefMap.get(LNamespace.XML_LATEX_INCLUDES);
		if(node!=null) latexIncludes.setText(node.getTextContent());

		node = prefMap.get(LNamespace.XML_PATH_LATEX_DISTRIB);
		if(node!=null) pathLatexDistribField.setText(node.getTextContent());

		node = prefMap.get(LNamespace.XML_ALPHA_INTER);
		if(node!=null) alpaInterCheckBox.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());

		node = prefMap.get(LNamespace.XML_ANTI_ALIAS);
		if(node!=null) antialiasingCheckBox.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());

		node = prefMap.get(LNamespace.XML_AUTO_UPDATE);
		if(node!=null) codeAutoUpdateCB.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());

		node = prefMap.get(LNamespace.XML_CHECK_VERSION);
		if(node!=null) checkNewVersion.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());

		node = prefMap.get(LNamespace.XML_CLASSIC_GRID);
		if(node!=null) {
			classicGridRB.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());
			persoGridRB.setSelected(!Boolean.valueOf(node.getTextContent()).booleanValue());
		}

		node = prefMap.get(LNamespace.XML_COLOR_RENDERING);
		if(node!=null) colorRenderCheckBox.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());

		node = prefMap.get(LNamespace.XML_DRAW_BORDERS);
		if(node!=null) displayBordersCB.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());

		node = prefMap.get(LNamespace.XML_DISPLAY_CODE_PANEL);
		if(node!=null) displayCodePanelCB.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());

		node = prefMap.get(LNamespace.XML_DISPLAY_GRID);
		if(node!=null) displayGridCB.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());

		node = prefMap.get(LNamespace.XML_DISPLAY_X);
		if(node!=null) displayXScaleCB.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());

		node = prefMap.get(LNamespace.XML_DISPLAY_Y);
		if(node!=null) displayYScaleCB.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());

		node = prefMap.get(LNamespace.XML_GRID_GAP);
		if(node!=null) persoGridGapField.setValueSafely(Integer.valueOf(node.getTextContent()).intValue());

		node = prefMap.get(LNamespace.XML_LAF);
		if(node!=null) {
			String nodeText = node.getTextContent();
			int count = themeList.getItemCount(), j=0;
			boolean again = true;

			while(j<count && again)
				if(themeList.getItemAt(j).toString().equals(nodeText))
					again = false;
				else
					j++;

			if(again)
				 themeList.setSelectedItem(UIManager.getCrossPlatformLookAndFeelClassName());
			else themeList.setSelectedIndex(j);
		}

		node = prefMap.get(LNamespace.XML_LANG);
		if(node!=null) langList.setSelectedItemSafely(node.getTextContent());

		node = prefMap.get(LNamespace.XML_MAGNETIC_GRID);
		if(node!=null) magneticGridCB.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());

		node = prefMap.get(LNamespace.XML_PATH_EXPORT);
		if(node!=null) pathExportField.setText(node.getTextContent());

		node = prefMap.get(LNamespace.XML_PATH_OPEN);
		if(node!=null) pathOpenField.setText(node.getTextContent());

		node = prefMap.get(LNamespace.XML_PATH_TEX_EDITOR);
		if(node!=null) pathTexEditorField.setText(node.getTextContent());

		node = prefMap.get(LNamespace.XML_RENDERING);
		if(node!=null) renderingCheckBox.setSelected(Boolean.valueOf(node.getTextContent()).booleanValue());

		node = prefMap.get(LNamespace.XML_UNIT);
		if(node!=null) unitChoice.setSelectedItemSafely(node.getTextContent());

		node = prefMap.get(LNamespace.XML_RECENT_FILES);
		if(node!=null) {
			NodeList nl2 = node.getChildNodes();
			NamedNodeMap nnm = node.getAttributes();
			recentFilesName.clear();

			if(nnm!=null && nnm.getNamedItem(LNamespace.XML_NB_RECENT_FILES)!=null) {
				Node attr = nnm.getNamedItem(LNamespace.XML_NB_RECENT_FILES);

				if(attr!=null)
					nbRecentFilesField.setValue(Integer.valueOf(attr.getTextContent()).intValue());
			}

			for(int j=0, size2=nl2.getLength(); j<size2; j++) {
				n2 = nl2.item(j);

				if(n2.getNodeName().equals(LNamespace.XML_RECENT_FILE) && n2.getTextContent()!=null)
					recentFilesName.add(n2.getTextContent());
			}
		}

		node = prefMap.get(LNamespace.XML_MAXIMISED);
		if(node!=null) isFrameMaximized = Boolean.valueOf(node.getTextContent()).booleanValue();

		node = prefMap.get(LNamespace.XML_SIZE);
		if(node!=null) {
			NodeList nl2 = node.getChildNodes();
			frameSize = new Dimension();

			for(int j=0, size2=nl2.getLength(); j<size2; j++) {
				n2 = nl2.item(j);

				if(n2.getNodeName().equals(LNamespace.XML_WIDTH))
					frameSize.width = Integer.valueOf(n2.getTextContent()).intValue();
				else
					if(n2.getNodeName().equals(LNamespace.XML_HEIGHT))
						frameSize.height = Integer.valueOf(n2.getTextContent()).intValue();
			}
		}

		node = prefMap.get(LNamespace.XML_POSITION);
		if(node!=null) {
			NodeList nl2 = node.getChildNodes();

			for(int j=0, size2=nl2.getLength(); j<size2; j++) {
				n2 = nl2.item(j);

				if(n2.getNodeName().equals(LNamespace.XML_POSITION_X))
					framePosition.setX(Math.max(0, Integer.valueOf(n2.getTextContent()).intValue()));
				else
					if(n2.getNodeName().equals(LNamespace.XML_POSITION_Y))
						framePosition.setY(Math.max(0, Integer.valueOf(n2.getTextContent()).intValue()));
			}
		}

		node = prefMap.get(LNamespace.XML_DIVIDER_POSITION);
		if(node!=null) dividerPosition = Double.valueOf(node.getTextContent()).doubleValue();

//		else if(name.equals(LNamespace.XML_DELIMITOR_OPACITY))//FIXME
//  		AbstractHandler.setOpacity(Double.valueOf(content).intValue());
	}


	/**
	 * Applies the values of the preferences setter to the concerned elements.
	 * @since 3.0
	 */
	private void applyValues() {
		final Exporter exporter 				= frame.getExporter();
		final MagneticGridCustomiser gridCust 	= frame.getGridCustomiser();
		final CodePanelActivator codePanelActiv = frame.getCodePanelActivator();
		final ScaleRulersCustomiser scaleCust 	= frame.getScaleRulersCustomiser();
		final FileLoaderSaver saver 			= frame.getFileLoader();
		final LCanvas canvas					= frame.getCanvas();
		final GridStyle gridStyle;

		if(displayGridCB.isSelected())
			 gridStyle = classicGridRB.isSelected() ? GridStyle.STANDARD : GridStyle.CUSTOMISED;
		else gridStyle = GridStyle.NONE;

		gridCust.grid.setStyle(gridStyle);
		gridCust.grid.setMagnetic(magneticGridCB.isSelected());
		gridCust.grid.setGridSpacing(Integer.valueOf(persoGridGapField.getValue().toString()));
		canvas.setAlphaInterpolation(alpaInterCheckBox.isSelected() ? RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY : RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		canvas.setAntiAliasing(antialiasingCheckBox.isSelected() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		canvas.setColorRendering(colorRenderCheckBox.isSelected() ? RenderingHints.VALUE_COLOR_RENDER_QUALITY : RenderingHints.VALUE_COLOR_RENDER_SPEED);
		canvas.setRendering(renderingCheckBox.isSelected() ? RenderingHints.VALUE_RENDER_QUALITY : RenderingHints.VALUE_RENDER_SPEED);
		exporter.setDefaultPackages(latexIncludes.getText());
		exporter.setLatexPathDistrib(pathLatexDistribField.getText());
		exporter.setPathExport(pathExportField.getText());
		gridCust.gridSpacing.setValueSafely(persoGridGapField.getValue());
		gridCust.magneticCB.setSelected(magneticGridCB.isSelected());
		gridCust.styleList.setSelectedItemSafely(gridStyle.getLabel());
		codePanelActiv.codePanel.setVisible(displayCodePanelCB.isSelected());
		codePanelActiv.closeMenuItem.setSelected(displayCodePanelCB.isSelected());
		scaleCust.xRuler.setVisible(displayXScaleCB.isSelected());
		scaleCust.yRuler.setVisible(displayYScaleCB.isSelected());
		scaleCust.xRulerItem.setSelected(displayXScaleCB.isSelected());
		scaleCust.yRulerItem.setSelected(displayYScaleCB.isSelected());
		scaleCust.unitCmItem.setSelected(unitChoice.getSelectedItem().toString().equals(Unit.CM.getLabel()));
		scaleCust.unitInchItem.setSelected(unitChoice.getSelectedItem().toString().equals(Unit.INCH.getLabel()));
		saver.setPathSave(pathOpenField.getText());
		ScaleRuler.setUnit(Unit.getUnit(unitChoice.getSelectedItem().toString()));
		frame.setLocation((int)framePosition.getX(), (int)framePosition.getY());

		if(frameSize.width>0 && frameSize.height>0)
			frame.setSize(frameSize.width, frameSize.height);

		if(isFrameMaximized || frameSize.width==0 || frameSize.height==0)
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		// TODO checkversion, autoupdate, drawBorders, PathtexEditor, recentFiles, XML_DIVIDER_POSITION
	}



	/**
	 * Writes the preferences of latexdraw in an XML document.
	 * @since 3.0
	 */
	public void writeXMLPreferences() {
		try {
			FileOutputStream fos = new FileOutputStream(LPath.PATH_PREFERENCES_XML_FILE);
			Document document 	 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	        Element root, elt, elt2;
	        OutputFormat of;
	        XMLSerializer xmls;

	        document.setXmlVersion("1.0");//$NON-NLS-1$
	        document.setXmlStandalone(true);
	        root = document.createElement(LNamespace.XML_ROOT_PREFERENCES);

	        Attr attr = document.createAttribute(LNamespace.XML_VERSION);
	        attr.setTextContent(VersionChecker.VERSION);
	        root.setAttributeNode(attr);

	        elt = document.createElement(LNamespace.XML_RENDERING);
	        elt.setTextContent(String.valueOf(renderingCheckBox.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_COLOR_RENDERING);
	        elt.setTextContent(String.valueOf(colorRenderCheckBox.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_ALPHA_INTER);
	        elt.setTextContent(String.valueOf(alpaInterCheckBox.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_ANTI_ALIAS);
	        elt.setTextContent(String.valueOf(antialiasingCheckBox.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_PATH_EXPORT);
	        elt.setTextContent(pathExportField.getText());
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_PATH_OPEN);
	        elt.setTextContent(pathOpenField.getText());
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_DISPLAY_CODE_PANEL);
	        elt.setTextContent(String.valueOf(displayCodePanelCB.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_DISPLAY_GRID);
	        elt.setTextContent(String.valueOf(displayGridCB.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_DISPLAY_X);
	        elt.setTextContent(String.valueOf(displayXScaleCB.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_DISPLAY_Y);
	        elt.setTextContent(String.valueOf(displayYScaleCB.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_DRAW_BORDERS);
	        elt.setTextContent(String.valueOf(displayBordersCB.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_UNIT);
	        elt.setTextContent(unitChoice.getSelectedItem().toString());
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_AUTO_UPDATE);
	        elt.setTextContent(String.valueOf(codeAutoUpdateCB.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_CHECK_VERSION);
	        elt.setTextContent(String.valueOf(checkNewVersion.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_LANG);
	        elt.setTextContent(langList.getSelectedItem().toString());
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_MAGNETIC_GRID);
	        elt.setTextContent(String.valueOf(magneticGridCB.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_CLASSIC_GRID);
	        elt.setTextContent(String.valueOf(classicGridRB.isSelected()));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_GRID_GAP);
	        elt.setTextContent(persoGridGapField.getValue().toString());
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_PATH_TEX_EDITOR);
	        elt.setTextContent(pathTexEditorField.getText());
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_PATH_LATEX_DISTRIB);
	        elt.setTextContent(pathLatexDistribField.getText());
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_LATEX_INCLUDES);
	        elt.setTextContent(latexIncludes.getText());
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_RECENT_FILES);
	        elt.setAttribute(LNamespace.XML_NB_RECENT_FILES, nbRecentFilesField.getValue().toString());
	        root.appendChild(elt);

	        for(String recentFile : recentFilesName) {
	            elt2 = document.createElement(LNamespace.XML_RECENT_FILE);
	            elt2.setTextContent(recentFile);
	            elt.appendChild(elt2);
	        }

	        elt = document.createElement(LNamespace.XML_LAF);
	        elt.setTextContent(themeList.getSelectedItem().toString());
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_MAXIMISED);
	        elt.setTextContent(String.valueOf(frame.getExtendedState()==Frame.MAXIMIZED_BOTH));
	        root.appendChild(elt);

	        elt = document.createElement(LNamespace.XML_SIZE);
	        root.appendChild(elt);

	        elt2 = document.createElement(LNamespace.XML_WIDTH);
	        elt2.setTextContent(String.valueOf(frame.getWidth()));
	        elt.appendChild(elt2);

	        elt2 = document.createElement(LNamespace.XML_HEIGHT);
	        elt2.setTextContent(String.valueOf(frame.getHeight()));
	        elt.appendChild(elt2);

	        elt = document.createElement(LNamespace.XML_POSITION);
	        root.appendChild(elt);

	        elt2 = document.createElement(LNamespace.XML_POSITION_X);
	        elt2.setTextContent(String.valueOf(frame.getLocation().x));
	        elt.appendChild(elt2);

	        elt2 = document.createElement(LNamespace.XML_POSITION_Y);
	        elt2.setTextContent(String.valueOf(frame.getLocation().y));
	        elt.appendChild(elt2);

	        //TODO
//	        elt = document.createElement(LNamespace.XML_DIVIDER_POSITION);
	//        double divLoc = frame.getCodePanelActivator().codePanel.isVisible() ? mainFrame.getDividerCurrentLocation() : mainFrame.getFormerDividerLocation();
	//        elt.setTextContent(String.valueOf(divLoc<mainFrame.getWidth() && divLoc>=0 ? divLoc/mainFrame.getWidth() : 1.));
	//        root.appendChild(elt);

	//        elt = document.createElement(LNamespace.XML_DELIMITOR_OPACITY);//TODO
	//        elt.setTextContent(String.valueOf(Delimitor.getOpacity()));
	//        root.appendChild(elt);

	        of = new OutputFormat(document);
	        of.setIndenting(true);
	        xmls = new XMLSerializer(fos, of);
	        xmls.serialize(root);
			fos.close();
		}catch(Exception e) { BordelCollector.INSTANCE.add(e); }
	}


	/**
	 * Reads the preferences of latexdraw defined in XML.
	 * @throws IllegalArgumentException If a problem occurs.
	 * @since 3.0
	 */
	public void readXMLPreferences() throws SAXException, IOException, ParserConfigurationException {
        final File xml = new File(LPath.PATH_PREFERENCES_XML_FILE);

        if(xml.canRead())
        	readXMLPreferencesFromFile(xml);

        applyValues();
	}


	private void readXMLPreferencesFromFile(final File xmlFile) throws SAXException, IOException, ParserConfigurationException {
		Node node = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile).getFirstChild();

		if(node==null || !node.getNodeName().equals(LNamespace.XML_ROOT_PREFERENCES))
			throw new IllegalArgumentException();

		final NodeList nl = node.getChildNodes();
		String name;

		Map<String, Node> prefMaps = new HashMap<String, Node>();

		for(int i=0, size=nl.getLength(); i<size; i++) {
			node = nl.item(i);
			name = node.getNodeName();

			if(name!=null && name.length()>0)
				prefMaps.put(name, node);
		}

		processXMLDataPreference(prefMaps);
		prefMaps.clear();
	}
}


/**
 * This link maps a pressure on the close button of the preferences frame to an action saving the preferences.
 */
class CloseFrame2SavePreferences extends Link<WritePreferences, WindowClosed, PreferencesSetter> {
	/**
	 * Creates the link.
	 */
	public CloseFrame2SavePreferences(final PreferencesSetter ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, WritePreferences.class, WindowClosed.class);
	}

	@Override
	public void initAction() {
		action.setSetter(getInstrument());
	}

	@Override
	public boolean isConditionRespected() {
		return true;
	}
}

