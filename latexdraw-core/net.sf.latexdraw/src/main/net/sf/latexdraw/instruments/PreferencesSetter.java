package net.sf.latexdraw.instruments;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.view.GridStyle;
import net.sf.latexdraw.ui.XScaleRuler;
import net.sf.latexdraw.ui.YScaleRuler;
import net.sf.latexdraw.util.*;
import org.malai.javafx.instrument.JfxInstrument;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This instrument modifies the preferences.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 01/18/11<br>
 * 
 * @author Arnaud BLOUIN
 * @version 4.0
 */
public class PreferencesSetter extends JfxInstrument implements Initializable {
	/** Sets if the grid is magnetic. */
	@FXML protected CheckBox magneticCB;

	/** Allows the set if the program must check new version on start up. */
	@FXML protected CheckBox checkNewVersion;

	/**
	 * This textField allows to set the default directories for open/save
	 * actions.
	 */
	@FXML protected TextField pathOpenField;

	@FXML protected CheckBox openGL;

	/**
	 * This textField allows to set the default directories for exporting
	 * actions.
	 */
	@FXML protected TextField pathExportField;

	/** The text field used to defines the latex packages to use. */
	@FXML protected TextArea latexIncludes;

	/** Allows to set the unit of length by default. */
	@FXML protected ComboBox<String> unitChoice;

	/** The list that contains the supported languages. */
	@FXML protected ComboBox<String> langList;

	/** The field used to modifies the gap of the customised grid. */
	@FXML protected Spinner<Integer> persoGridGapField;

	/** The widget used to defines the number of recent file to keep in memory. */
	@FXML protected Spinner<Integer> nbRecentFilesField;

	/** Contains the different possible kind of grids. */
	@FXML protected ComboBox<GridStyle> styleList;

	@FXML protected Button buttonOpen;
	@FXML protected Button buttonExport;

	/** The x ruler of the system. */
	@Inject protected XScaleRuler xRuler;

	/** The Y ruler of the system. */
	@Inject protected YScaleRuler yRuler;

	/** The recent files. */
	protected List<String> recentFilesName;

	/** The file chooser of paths selection. */
	protected FileChooser fileChooser;

	/**
	 * Creates the instrument.
	 */
	public PreferencesSetter() {
		super();
		recentFilesName = new ArrayList<>();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		latexIncludes.setTooltip(new Tooltip("<html>"+ //$NON-NLS-1$
				LangTool.INSTANCE.getBundle().getString("PreferencesSetter.1")+ //$NON-NLS-1$
				"<br>\\usepackage[frenchb]{babel}<br>\\usepackage[utf8]{inputenc}</html>"//$NON-NLS-1$
		));

		langList.getItems().addAll(LangTool.INSTANCE.getSupportedLocales().stream().map(Locale::getDisplayLanguage).collect(Collectors.toList()));
		unitChoice.getItems().addAll(Arrays.stream(Unit.values()).map(Unit::getLabel).collect(Collectors.toList()));
		styleList.getItems().addAll(GridStyle.values());
	}

	/**
	 * Adds a recent file.
	 * 
	 * @param absolutePath
	 *            The absolute path of the file to add.
	 */
	public void addRecentFile(final String absolutePath) {
		final int i = recentFilesName.indexOf(absolutePath);
		final int max = (int)Double.parseDouble(nbRecentFilesField.getValue().toString());

		if(i!=-1)
			recentFilesName.remove(i);

		while(recentFilesName.size()>=max)
			recentFilesName.remove(max-1);

		recentFilesName.add(0, absolutePath);
	}

	// protected void update() {
	// final GridStyle style = grid.getStyle();
	//
	// gridSpacing.setDisable(style!=GridStyle.CUSTOMISED);
	// magneticCB.setDisable(style==GridStyle.NONE);
	// styleList.getSelectionModel().select(style);
	//
	// if(style!=GridStyle.NONE) {
	// if(style==GridStyle.CUSTOMISED)
	// gridSpacing.getValueFactory().setValue(grid.getGridSpacing());
	// magneticCB.setSelected(grid.isMagnetic());
	// }
	// unitCmItem.setSelected(ScaleRuler.getUnit()==Unit.CM);
	// unitInchItem.setSelected(!unitCmItem.isSelected());
	// }

	@Override
	protected void initialiseInteractors() {
		// addInteractor(new CloseFrame2SavePreferences(this));
		// addInteractor(new List2ChangeStyle(this));
		// addInteractor(new Spinner2GridSpacing(this));
		// addInteractor(new CheckBox2MagneticGrid(this));
	}

	/**
	 * @return The file chooser used to selected folders.
	 * @since 3.0
	 */
	public FileChooser getFileChooser() {
		if(fileChooser==null) {
			fileChooser = new FileChooser();
			fileChooser.setTitle(LangTool.INSTANCE.getBundle().getString("PreferencesFrame.selectFolder")); //$NON-NLS-1$
		}
		return fileChooser;
	}

	private void processXMLDataPreference(File xml) {
		Map<String, Node> prefMap = Preference.readXMLPreferencesFromFile(xml);
		Node n2;
		Node node;
		final Stage frame = (Stage)pathExportField.getScene().getWindow();

		node = prefMap.get(LNamespace.XML_LATEX_INCLUDES);
		if(node!=null)
			latexIncludes.setText(node.getTextContent());

		node = prefMap.get(LNamespace.XML_OPENGL);
		if(node!=null) openGL.setSelected(Boolean.parseBoolean(node.getTextContent()));

		node = prefMap.get(LNamespace.XML_CHECK_VERSION);
		if(node!=null)
			checkNewVersion.setSelected(Boolean.parseBoolean(node.getTextContent()));

		node = prefMap.get(LNamespace.XML_CLASSIC_GRID);
		if(node!=null)
			if(Boolean.parseBoolean(node.getTextContent()))
				styleList.getSelectionModel().select(GridStyle.STANDARD);
			else
				styleList.getSelectionModel().select(GridStyle.CUSTOMISED);

		node = prefMap.get(LNamespace.XML_DISPLAY_GRID);
		if(node!=null)
			if(!Boolean.parseBoolean(node.getTextContent()))
				styleList.getSelectionModel().select(GridStyle.NONE);

		node = prefMap.get(LNamespace.XML_GRID_GAP);
		if(node!=null)
			persoGridGapField.getValueFactory().setValue(Integer.valueOf(node.getTextContent()));

		node = prefMap.get(LNamespace.XML_LANG);
		if(node!=null)
			langList.getSelectionModel().select(node.getTextContent());

		node = prefMap.get(LNamespace.XML_MAGNETIC_GRID);
		if(node!=null)
			magneticCB.setSelected(Boolean.parseBoolean(node.getTextContent()));

		node = prefMap.get(LNamespace.XML_PATH_EXPORT);
		if(node!=null)
			pathExportField.setText(node.getTextContent());

		node = prefMap.get(LNamespace.XML_PATH_OPEN);
		if(node!=null)
			pathOpenField.setText(node.getTextContent());

		node = prefMap.get(LNamespace.XML_UNIT);
		if(node!=null)
			unitChoice.getSelectionModel().select(node.getTextContent());

		node = prefMap.get(LNamespace.XML_RECENT_FILES);
		if(node!=null) {
			final NodeList nl2 = node.getChildNodes();
			final NamedNodeMap nnm = node.getAttributes();
			recentFilesName.clear();

			if(nnm!=null&&nnm.getNamedItem(LNamespace.XML_NB_RECENT_FILES)!=null) {
				final Node attr = nnm.getNamedItem(LNamespace.XML_NB_RECENT_FILES);

				if(attr!=null)
					nbRecentFilesField.getValueFactory().setValue(Integer.valueOf(attr.getTextContent()));
			}

			for(int j = 0, size2 = nl2.getLength(); j<size2; j++) {
				n2 = nl2.item(j);

				if(n2.getNodeName().equals(LNamespace.XML_RECENT_FILE)&&n2.getTextContent()!=null)
					recentFilesName.add(n2.getTextContent());
			}
		}

		node = prefMap.get(LNamespace.XML_MAXIMISED);
		if(node!=null)
			frame.setFullScreen(Boolean.parseBoolean(node.getTextContent()));

		node = prefMap.get(LNamespace.XML_SIZE);
		if(node!=null) {
			final NodeList nl2 = node.getChildNodes();

			for(int j = 0, size2 = nl2.getLength(); j<size2; j++) {
				n2 = nl2.item(j);

				if(n2.getNodeName().equals(LNamespace.XML_WIDTH))
					frame.setWidth(Integer.parseInt(n2.getTextContent()));
				else if(n2.getNodeName().equals(LNamespace.XML_HEIGHT))
					frame.setHeight(Integer.parseInt(n2.getTextContent()));
			}
		}

		node = prefMap.get(LNamespace.XML_POSITION);
		if(node!=null) {
			final NodeList nl2 = node.getChildNodes();

			for(int j = 0, size2 = nl2.getLength(); j<size2; j++) {
				n2 = nl2.item(j);

				if(n2.getNodeName().equals(LNamespace.XML_POSITION_X))
					frame.setX(Math.max(0, Integer.parseInt(n2.getTextContent())));
				else if(n2.getNodeName().equals(LNamespace.XML_POSITION_Y))
					frame.setY(Math.max(0, Integer.parseInt(n2.getTextContent())));
			}
		}
	}

	/**
	 * @return True if a new version must be checked.
	 * @since 3.0
	 */
	public boolean isVersionCheckEnable() {
		return checkNewVersion!=null&&checkNewVersion.isSelected();
	}

	/**
	 * Applies the values of the preferences setter to the concerned elements.
	 * 
	 * @since 3.0
	 */
	private void applyValues() {
		// final Exporter exporter = frame.getExporter();
		// final MagneticGridCustomiser gridCust = frame.getGridCustomiser();
		// final ScaleRulersCustomiser scaleCust =
		// frame.getScaleRulersCustomiser();
		// final FileLoaderSaver saver = frame.getFileLoader();
		// final Dimension dim = LSystem.INSTANCE.getScreenDimension();
		// final Rectangle rec = frame.getGraphicsConfiguration().getBounds();
		// final GridStyle gridStyle;
		//
		// if(displayGridCB.isSelected())
		// gridStyle = classicGridRB.isSelected() ? GridStyle.STANDARD :
		// GridStyle.CUSTOMISED;
		// else gridStyle = GridStyle.NONE;
		//
		// gridCust.grid.setStyle(gridStyle);
		// gridCust.grid.setMagnetic(magneticGridCB.isSelected());
		// gridCust.grid.setGridSpacing(Integer.parseInt(persoGridGapField.getValue().toString()));
		// exporter.setDefaultPackages(latexIncludes.getText());
		// exporter.setPathExport(pathExportField.getText());
		// try{
		// gridCust.gridSpacing.getValueFactory().setValue(Integer.valueOf(persoGridGapField.getValue().toString()));
		// }catch(NumberFormatException ex) {
		// BadaboomCollector.INSTANCE.add(ex); }
		// gridCust.magneticCB.setSelected(magneticGridCB.isSelected());
		// gridCust.styleList.getSelectionModel().select(gridStyle);
		// scaleCust.unitCmItem.setSelected(unitChoice.getSelectedItem().toString().equals(Unit.CM.getLabel()));
		// scaleCust.unitInchItem.setSelected(unitChoice.getSelectedItem().toString().equals(Unit.INCH.getLabel()));
		// saver.setPathSave(pathOpenField.getText());
		// saver.updateRecentMenuItems(recentFilesName);
		// ScaleRuler.setUnit(Unit.getUnit(unitChoice.getSelectedItem().toString()));
		// frame.setLocation((int)(rec.getX()+(framePosition.getX()>dim.getWidth()?0:framePosition.getX())),
		// (int)(rec.getY()+(framePosition.getY()>dim.getHeight()?0:framePosition.getY())));
		//
		// if(frameSize.width>0 && frameSize.height>0)
		// frame.setSize((int)Math.min(frameSize.width, dim.getWidth()),
		// (int)Math.min(frameSize.height, dim.getHeight()));
		//
		// if(isFrameMaximized || frameSize.width==0 || frameSize.height==0)
		// frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	/**
	 * Writes the preferences of latexdraw in an XML document.
	 * 
	 * @since 3.0
	 */
	public void writeXMLPreferences() {
		try {
			try(final FileOutputStream fos = new FileOutputStream(LPath.PATH_PREFERENCES_XML_FILE)) {
				final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
				Stage frame = (Stage)pathExportField.getScene().getWindow();
				Rectangle2D rec = Screen.getPrimary().getBounds();
				final Element root;
				Element elt;
				Element elt2;

				document.setXmlVersion("1.0");//$NON-NLS-1$
				document.setXmlStandalone(true);
				root = document.createElement(LNamespace.XML_ROOT_PREFERENCES);
				document.appendChild(root);

				final Attr attr = document.createAttribute(LNamespace.XML_VERSION);
				attr.setTextContent(VersionChecker.VERSION);
				root.setAttributeNode(attr);

				elt = document.createElement(LNamespace.XML_OPENGL);
				elt.setTextContent(String.valueOf(openGL.isSelected()));
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_PATH_EXPORT);
				elt.setTextContent(pathExportField.getText());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_PATH_OPEN);
				elt.setTextContent(pathOpenField.getText());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_DISPLAY_GRID);
				elt.setTextContent(String.valueOf(styleList.getSelectionModel().getSelectedItem()!=GridStyle.NONE));
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_UNIT);
				elt.setTextContent(unitChoice.getSelectionModel().getSelectedItem().toString());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_CHECK_VERSION);
				elt.setTextContent(String.valueOf(checkNewVersion.isSelected()));
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_LANG);
				elt.setTextContent(langList.getSelectionModel().getSelectedItem().toString());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_MAGNETIC_GRID);
				elt.setTextContent(String.valueOf(magneticCB.isSelected()));
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_CLASSIC_GRID);
				if(styleList.getSelectionModel().getSelectedItem()==GridStyle.STANDARD)
					elt.setTextContent(Boolean.TRUE.toString());
				else
					elt.setTextContent(Boolean.FALSE.toString());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_GRID_GAP);
				elt.setTextContent(persoGridGapField.getValue().toString());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_LATEX_INCLUDES);
				elt.setTextContent(latexIncludes.getText());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_RECENT_FILES);
				elt.setAttribute(LNamespace.XML_NB_RECENT_FILES, nbRecentFilesField.getValue().toString());
				root.appendChild(elt);

				for(final String recentFile : recentFilesName) {
					elt2 = document.createElement(LNamespace.XML_RECENT_FILE);
					elt2.setTextContent(recentFile);
					elt.appendChild(elt2);
				}

				elt = document.createElement(LNamespace.XML_MAXIMISED);
				elt.setTextContent(String.valueOf(frame.isMaximized()));
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
				elt2.setTextContent(String.valueOf((int)(frame.getX()-rec.getMinX())));
				elt.appendChild(elt2);

				elt2 = document.createElement(LNamespace.XML_POSITION_Y);
				elt2.setTextContent(String.valueOf((int)(frame.getY()-rec.getMinY())));
				elt.appendChild(elt2);

				final Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //$NON-NLS-1$ //$NON-NLS-2$
				transformer.transform(new DOMSource(document), new StreamResult(fos));
			}
		}catch(final Exception e) {
			BadaboomCollector.INSTANCE.add(e);
		}
	}

	/**
	 * Reads the preferences of latexdraw defined in XML.
	 * 
	 * @throws IllegalArgumentException
	 *             If a problem occurs.
	 * @since 3.0
	 */
	public void readXMLPreferences() {
		final File xml = new File(LPath.PATH_PREFERENCES_XML_FILE);

		if(xml.canRead())
			processXMLDataPreference(xml);

		applyValues();
	}
}


// /**
// * This link maps a pressure on the close button of the preferences frame to
// an action saving the preferences.
// */
// class CloseFrame2SavePreferences extends InteractorImpl<WritePreferences,
// WindowClosed, PreferencesSetter> {
// /**
// * Creates the link.
// */
// protected CloseFrame2SavePreferences(final PreferencesSetter ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, WritePreferences.class, WindowClosed.class);
// }
//
// @Override
// public void initAction() {
// action.setSetter(getInstrument());
// }
//
// @Override
// public boolean isConditionRespected() {
// return true;
// }
// }

// /**
// * Links a check-box widget to an action that sets if the grid is magnetic.
// */
// class CheckBox2MagneticGrid extends InteractorImpl<ModifyMagneticGrid,
// CheckBoxModified, MagneticGridCustomiser> {
// /**
// * Initialises the link.
// * @since 3.0
// */
// protected CheckBox2MagneticGrid(final MagneticGridCustomiser ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, ModifyMagneticGrid.class, CheckBoxModified.class);
// }
//
// @Override
// public void initAction() {
// action.setProperty(GridProperties.MAGNETIC);
// action.setGrid(instrument.grid);
// action.setValue(interaction.getCheckBox().isSelected());
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getCheckBox()==instrument.magneticCB;
// }
// }
//
//
//
// /**
// * Links a spinner widget to an action that modifies the spacing of the
// customised magnetic grid.
// */
// class Spinner2GridSpacing extends InteractorImpl<ModifyMagneticGrid,
// SpinnerModified, MagneticGridCustomiser> {
// /**
// * Initialises the link.
// * @since 3.0
// */
// protected Spinner2GridSpacing(final MagneticGridCustomiser ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, ModifyMagneticGrid.class, SpinnerModified.class);
// }
//
// @Override
// public void initAction() {
// action.setProperty(GridProperties.GRID_SPACING);
// action.setGrid(instrument.grid);
// }
//
//
// @Override
// public void updateAction() {
// action.setValue(Integer.valueOf(interaction.getSpinner().getValue().toString()));
// }
//
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getSpinner()==instrument.gridSpacing;
// }
// }
//
//
//
// /**
// * Links a list widget to an action that modifies the style of the magnetic
// grid.
// */
// class List2ChangeStyle extends InteractorImpl<ModifyMagneticGrid,
// ListSelectionModified, MagneticGridCustomiser> {
// /**
// * Initialises the link.
// * @since 3.0
// */
// protected List2ChangeStyle(final MagneticGridCustomiser ins) throws
// InstantiationException, IllegalAccessException {
// super(ins, false, ModifyMagneticGrid.class, ListSelectionModified.class);
// }
//
// @Override
// public void initAction() {
// action.setValue(GridStyle.getStyleFromLabel(interaction.getList().getSelectedObjects()[0].toString()));
// action.setGrid(instrument.grid);
// action.setProperty(GridProperties.STYLE);
// }
//
// @Override
// public boolean isConditionRespected() {
// return interaction.getList()==instrument.styleList;
// }
// }

