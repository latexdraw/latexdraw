/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import com.google.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.sf.latexdraw.actions.GridProperties;
import net.sf.latexdraw.actions.ModifyMagneticGrid;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.util.Preference;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.util.VersionChecker;
import net.sf.latexdraw.view.GridStyle;
import net.sf.latexdraw.view.MagneticGrid;
import org.malai.javafx.instrument.JFxAnonInteractor;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.BoxChecked;
import org.malai.javafx.interaction.library.ComboBoxSelected;
import org.malai.javafx.interaction.library.SpinnerValueChanged;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This instrument modifies the preferences.
 * @author Arnaud BLOUIN
 */
public class PreferencesSetter extends JfxInstrument implements Initializable {
	/** The recent files. */
	private final List<String> recentFilesName;
	/** Sets if the grid is magnetic. */
	@FXML private CheckBox magneticCB;
	/** Allows the set if the program must check new version on start up. */
	@FXML private CheckBox checkNewVersion;
	/** This textField allows to set the default directories for open/save actions. */
	@FXML private TextField pathOpenField;
	@FXML private CheckBox openGL;
	/** This textField allows to set the default directories for exporting actions. */
	@FXML private TextField pathExportField;
	/** The text field used to defines the latex packages to use. */
	@FXML private TextArea latexIncludes;
	/** Allows to set the unit of length by default. */
	@FXML private ComboBox<String> unitChoice;
	/** The list that contains the supported languages. */
	@FXML private ComboBox<String> langList;
	/** The field used to modifies the gap of the customised grid. */
	@FXML private Spinner<Integer> persoGridGapField;
	/** The widget used to defines the number of recent file to keep in memory. */
	@FXML private Spinner<Integer> nbRecentFilesField;
	/** Contains the different possible kind of grids. */
	@FXML private ComboBox<GridStyle> styleList;
	@FXML private Button buttonOpen;
	@FXML private Button buttonExport;
	@Inject private Exporter exporter;
	@Inject private FileLoaderSaver saver;
	@Inject private MagneticGrid grid;
	/** The file chooser of paths selection. */
	private DirectoryChooser fileChooser;

	/**
	 * Creates the instrument.
	 */
	PreferencesSetter() {
		super();
		recentFilesName = new ArrayList<>();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		latexIncludes.setTooltip(new Tooltip("<html>" + //$NON-NLS-1$
			LangTool.INSTANCE.getBundle().getString("PreferencesSetter.1") + //$NON-NLS-1$
			"<br>\\usepackage[frenchb]{babel}<br>\\usepackage[utf8]{inputenc}</html>"//$NON-NLS-1$
		));

		langList.getItems().addAll(LangTool.INSTANCE.getSupportedLocales().stream().map(Locale::getDisplayLanguage).collect(Collectors.toList()));
		unitChoice.getItems().addAll(Arrays.stream(Unit.values()).map(Unit::getLabel).collect(Collectors.toList()));
		styleList.getItems().addAll(GridStyle.values());

		ShapePropertyCustomiser.scrollOnSpinner(nbRecentFilesField);
		ShapePropertyCustomiser.scrollOnSpinner(persoGridGapField);

		buttonOpen.setOnAction(evt -> {
			File file = getFileChooser().showDialog(null);
			if(file != null) {
				pathOpenField.setText(file.getPath());
			}
		});

		buttonExport.setOnAction(evt -> {
			File file = getFileChooser().showDialog(null);
			if(file != null) {
				pathExportField.setText(file.getPath());
				exporter.setPathExport(file.getPath());
			}
		});
	}

	/**
	 * Adds a recent file.
	 * @param absolutePath The absolute path of the file to add.
	 */
	public void addRecentFile(final String absolutePath) {
		final int i = recentFilesName.indexOf(absolutePath);
		final int max = (int) Double.parseDouble(nbRecentFilesField.getValue().toString());

		if(i != -1) {
			recentFilesName.remove(i);
		}

		while(recentFilesName.size() >= max) {
			recentFilesName.remove(max - 1);
		}

		recentFilesName.add(0, absolutePath);
	}

	@Override
	protected void initialiseInteractors() throws IllegalAccessException, InstantiationException {
		addInteractor(new JFxAnonInteractor<>(this, false, ModifyMagneticGrid.class, ComboBoxSelected.class, action -> {
			action.setValue(styleList.getSelectionModel().getSelectedItem());
			action.setGrid(grid);
			action.setProperty(GridProperties.STYLE);
		}, styleList));
		addInteractor(new JFxAnonInteractor<>(this, false, ModifyMagneticGrid.class, BoxChecked.class, action -> {
			action.setValue(magneticCB.isSelected());
			action.setGrid(grid);
			action.setProperty(GridProperties.MAGNETIC);
		}, magneticCB));
		addInteractor(new JFxAnonInteractor<>(this, false, ModifyMagneticGrid.class, SpinnerValueChanged.class, action -> {
			action.setValue(persoGridGapField.getValue());
			action.setGrid(grid);
			action.setProperty(GridProperties.GRID_SPACING);
		}, persoGridGapField));
	}

	/**
	 * @return The file chooser used to selected folders.
	 * @since 3.0
	 */
	private DirectoryChooser getFileChooser() {
		if(fileChooser == null) {
			fileChooser = new DirectoryChooser();
			fileChooser.setTitle(LangTool.INSTANCE.getBundle().getString("PreferencesFrame.selectFolder")); //$NON-NLS-1$
		}
		return fileChooser;
	}

	private void processXMLDataPreference(final File xml) {
		final Map<String, Node> prefMap = Preference.readXMLPreferencesFromFile(xml);
		final Stage frame = (Stage) pathExportField.getScene().getWindow();
		Node n2;
		Node node;

		node = prefMap.get(LNamespace.XML_LATEX_INCLUDES);
		if(node != null) latexIncludes.setText(node.getTextContent());

		node = prefMap.get(LNamespace.XML_OPENGL);
		if(node != null) openGL.setSelected(Boolean.parseBoolean(node.getTextContent()));

		node = prefMap.get(LNamespace.XML_CHECK_VERSION);
		if(node != null) checkNewVersion.setSelected(Boolean.parseBoolean(node.getTextContent()));

		node = prefMap.get(LNamespace.XML_CLASSIC_GRID);
		if(node != null) if(Boolean.parseBoolean(node.getTextContent())) styleList.getSelectionModel().select(GridStyle.STANDARD);
		else styleList.getSelectionModel().select(GridStyle.CUSTOMISED);

		node = prefMap.get(LNamespace.XML_DISPLAY_GRID);
		if(node != null) if(!Boolean.parseBoolean(node.getTextContent())) styleList.getSelectionModel().select(GridStyle.NONE);

		node = prefMap.get(LNamespace.XML_GRID_GAP);
		if(node != null) persoGridGapField.getValueFactory().setValue(Integer.valueOf(node.getTextContent()));

		node = prefMap.get(LNamespace.XML_LANG);
		if(node != null) langList.getSelectionModel().select(node.getTextContent());

		node = prefMap.get(LNamespace.XML_MAGNETIC_GRID);
		if(node != null) magneticCB.setSelected(Boolean.parseBoolean(node.getTextContent()));

		node = prefMap.get(LNamespace.XML_PATH_EXPORT);
		if(node != null) pathExportField.setText(node.getTextContent());

		node = prefMap.get(LNamespace.XML_PATH_OPEN);
		if(node != null) pathOpenField.setText(node.getTextContent());

		node = prefMap.get(LNamespace.XML_UNIT);
		if(node != null) unitChoice.getSelectionModel().select(node.getTextContent());

		node = prefMap.get(LNamespace.XML_RECENT_FILES);
		if(node != null) {
			final NodeList nl2 = node.getChildNodes();
			final NamedNodeMap nnm = node.getAttributes();
			recentFilesName.clear();

			if(nnm != null && nnm.getNamedItem(LNamespace.XML_NB_RECENT_FILES) != null) {
				final Node attr = nnm.getNamedItem(LNamespace.XML_NB_RECENT_FILES);

				if(attr != null) {
					nbRecentFilesField.getValueFactory().setValue(Integer.valueOf(attr.getTextContent()));
				}
			}

			for(int j = 0, size2 = nl2.getLength(); j < size2; j++) {
				n2 = nl2.item(j);

				if(n2.getNodeName().equals(LNamespace.XML_RECENT_FILE) && n2.getTextContent() != null) {
					recentFilesName.add(n2.getTextContent());
				}
			}
		}

		node = prefMap.get(LNamespace.XML_MAXIMISED);
		if(node != null) {
			frame.setFullScreen(Boolean.parseBoolean(node.getTextContent()));
		}

		node = prefMap.get(LNamespace.XML_SIZE);
		if(node != null) {
			final NodeList nl2 = node.getChildNodes();

			for(int j = 0, size2 = nl2.getLength(); j < size2; j++) {
				n2 = nl2.item(j);

				if(n2.getNodeName().equals(LNamespace.XML_WIDTH)) {
					frame.setWidth(Double.valueOf(n2.getTextContent()));
				}else {
					if(n2.getNodeName().equals(LNamespace.XML_HEIGHT)) {
						frame.setHeight(Double.valueOf(n2.getTextContent()));
					}
				}
			}
		}

		node = prefMap.get(LNamespace.XML_POSITION);
		if(node != null) {
			final NodeList nl2 = node.getChildNodes();

			for(int j = 0, size2 = nl2.getLength(); j < size2; j++) {
				n2 = nl2.item(j);

				if(n2.getNodeName().equals(LNamespace.XML_POSITION_X)) {
					frame.setX(Math.max(0, Double.valueOf(n2.getTextContent())));
				}else {
					if(n2.getNodeName().equals(LNamespace.XML_POSITION_Y)) {
						frame.setY(Math.max(0, Double.valueOf(n2.getTextContent())));
					}
				}
			}
		}
	}

	/**
	 * @return True if a new version must be checked.
	 * @since 3.0
	 */
	public boolean isVersionCheckEnable() {
		return checkNewVersion != null && checkNewVersion.isSelected();
	}

	/**
	 * Applies the values of the preferences setter to the concerned elements.
	 * @since 3.0
	 */
	private void applyValues() {
		grid.setGridStyle(styleList.getSelectionModel().getSelectedItem());
		grid.setMagnetic(magneticCB.isSelected());
		grid.setGridSpacing(Integer.parseInt(persoGridGapField.getValue().toString()));
		grid.setGridSpacing(persoGridGapField.getValue());

		exporter.setDefaultPackages(latexIncludes.getText());
		exporter.setPathExport(pathExportField.getText());

		saver.setPathSave(pathOpenField.getText());
		saver.updateRecentMenuItems(recentFilesName);

		ScaleRuler.setUnit(Unit.getUnit(unitChoice.getSelectionModel().getSelectedItem()));
	}

	/**
	 * Writes the preferences of latexdraw in an XML document.
	 * @since 3.0
	 */
	public void writeXMLPreferences() {
		try {
			try(final FileOutputStream fos = new FileOutputStream(LPath.PATH_PREFERENCES_XML_FILE)) {
				final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
				final Stage frame = (Stage) pathExportField.getScene().getWindow();
				final Rectangle2D rec = Screen.getPrimary().getBounds();
				final Element root = document.createElement(LNamespace.XML_ROOT_PREFERENCES);
				Element elt;
				Element elt2;

				document.setXmlVersion("1.0");//$NON-NLS-1$
				document.setXmlStandalone(true);
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
				elt.setTextContent(String.valueOf(styleList.getSelectionModel().getSelectedItem() != GridStyle.NONE));
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_UNIT);
				elt.setTextContent(unitChoice.getSelectionModel().getSelectedItem());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_CHECK_VERSION);
				elt.setTextContent(String.valueOf(checkNewVersion.isSelected()));
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_LANG);
				elt.setTextContent(langList.getSelectionModel().getSelectedItem());
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_MAGNETIC_GRID);
				elt.setTextContent(String.valueOf(magneticCB.isSelected()));
				root.appendChild(elt);

				elt = document.createElement(LNamespace.XML_CLASSIC_GRID);
				elt.setTextContent(Boolean.valueOf(styleList.getSelectionModel().getSelectedItem() == GridStyle.STANDARD).toString());
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
				elt2.setTextContent(String.valueOf((int) frame.getWidth()));
				elt.appendChild(elt2);

				elt2 = document.createElement(LNamespace.XML_HEIGHT);
				elt2.setTextContent(String.valueOf((int) frame.getHeight()));
				elt.appendChild(elt2);

				elt = document.createElement(LNamespace.XML_POSITION);
				root.appendChild(elt);

				elt2 = document.createElement(LNamespace.XML_POSITION_X);
				elt2.setTextContent(String.valueOf((int) (frame.getX() - rec.getMinX())));
				elt.appendChild(elt2);

				elt2 = document.createElement(LNamespace.XML_POSITION_Y);
				elt2.setTextContent(String.valueOf((int) (frame.getY() - rec.getMinY())));
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
	 * @throws IllegalArgumentException If a problem occurs.
	 * @since 3.0
	 */
	public void readXMLPreferences() {
		final File xml = new File(LPath.PATH_PREFERENCES_XML_FILE);

		if(xml.canRead()) {
			processXMLDataPreference(xml);
		}

		applyValues();
	}
}
