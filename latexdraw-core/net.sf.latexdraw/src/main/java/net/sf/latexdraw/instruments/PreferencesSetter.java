/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
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
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.commands.GridProperties;
import net.sf.latexdraw.commands.ModifyMagneticGrid;
import net.sf.latexdraw.commands.SetUnit;
import net.sf.latexdraw.ui.ScaleRuler;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.LPath;
import net.sf.latexdraw.util.LangTool;
import net.sf.latexdraw.util.Preference;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.util.VersionChecker;
import net.sf.latexdraw.view.GridStyle;
import net.sf.latexdraw.view.MagneticGrid;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.ButtonPressed;
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
	private final List<String> recentFileNames;
	/** Sets if the grid is magnetic. */
	@FXML protected CheckBox magneticCB;
	/** Allows the set if the program must check new version on start up. */
	@FXML protected CheckBox checkNewVersion;
	/** This textField allows to set the default directories for open/save commands. */
	@FXML private TextField pathOpenField;
	@FXML private CheckBox openGL;
	/** This textField allows to set the default directories for exporting commands. */
	@FXML private TextField pathExportField;
	/** The text field used to defines the latex packages to use. */
	@FXML private TextArea latexIncludes;
	/** Allows to set the unit of length by default. */
	@FXML private ComboBox<String> unitChoice;
	/** The list that contains the supported languages. */
	@FXML private ComboBox<Locale> langList;
	/** The field used to modifies the gap of the customised grid. */
	@FXML protected Spinner<Integer> persoGridGapField;
	/** The widget used to defines the number of recent file to keep in memory. */
	@FXML private Spinner<Integer> nbRecentFilesField;
	/** Contains the different possible kind of grids. */
	@FXML protected ComboBox<GridStyle> styleList;
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
	public PreferencesSetter() {
		super();
		recentFileNames = new ArrayList<>();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		latexIncludes.setTooltip(new Tooltip("<html>" + //NON-NLS
			LangTool.INSTANCE.getBundle().getString("PreferencesSetter.1") + //NON-NLS
			"<br>\\usepackage[frenchb]{babel}<br>\\usepackage[utf8]{inputenc}</html>"//NON-NLS
		));

		langList.setCellFactory(l -> new LocaleCell());
		langList.setButtonCell(new LocaleCell());
		langList.getItems().addAll(LangTool.INSTANCE.getSupportedLocales());

		unitChoice.getItems().addAll(Arrays.stream(Unit.values()).map(Unit::getLabel).collect(Collectors.toList()));
		styleList.getItems().addAll(GridStyle.values());
	}

	/**
	 * Adds a recent file.
	 * @param absolutePath The absolute path of the file to add.
	 */
	public void addRecentFile(final String absolutePath) {
		final int i = recentFileNames.indexOf(absolutePath);
		final int max = (int) Double.parseDouble(nbRecentFilesField.getValue().toString());

		if(i != -1) {
			recentFileNames.remove(i);
		}

		while(recentFileNames.size() >= max) {
			recentFileNames.remove(max - 1);
		}

		recentFileNames.add(0, absolutePath);
	}

	public List<String> getRecentFileNames() {
		return recentFileNames;
	}

	@Override
	protected void configureBindings() {
		comboboxBinder(ModifyMagneticGrid.class).on(styleList).first(c -> {
			c.setValue(styleList.getSelectionModel().getSelectedItem());
			c.setGrid(grid);
			c.setProperty(GridProperties.STYLE);
		}).bind();

		checkboxBinder(ModifyMagneticGrid.class).on(magneticCB).first(c -> {
			c.setValue(magneticCB.isSelected());
			c.setGrid(grid);
			c.setProperty(GridProperties.MAGNETIC);
		}).bind();

		spinnerBinder(ModifyMagneticGrid.class).on(persoGridGapField).exec().first(c -> {
			c.setGrid(grid);
			c.setProperty(GridProperties.GRID_SPACING);
		}).then(c -> c.setValue(persoGridGapField.getValue())).bind();

		comboboxBinder(SetUnit.class).on(unitChoice).first(c -> c.setUnit(Unit.getUnit(unitChoice.getSelectionModel().getSelectedItem()))).bind();

		anonCmdBinder(new ButtonPressed(), () -> {
			final File file = getFileChooser().showDialog(null);
			if(file != null) {
				pathOpenField.setText(file.getPath());
			}
		}).on(buttonOpen).bind();

		anonCmdBinder(new ButtonPressed(), () -> {
			final File file = getFileChooser().showDialog(null);
			if(file != null) {
				pathExportField.setText(file.getPath());
				exporter.setPathExport(file.getPath());
			}
		}).on(buttonExport).bind();
	}

	/**
	 * @return The file chooser used to selected folders.
	 * @since 3.0
	 */
	private DirectoryChooser getFileChooser() {
		if(fileChooser == null) {
			fileChooser = new DirectoryChooser();
			fileChooser.setTitle(LangTool.INSTANCE.getBundle().getString("PreferencesFrame.selectFolder")); //NON-NLS
		}
		return fileChooser;
	}

	private void processXMLDataPreference(final File xml) {
		final Map<String, Node> prefMap = Preference.INSTANCE.readXMLPreferencesFromFile(xml);
		final Stage stage = (Stage) pathExportField.getScene().getWindow();

		Optional.ofNullable(prefMap.get(LNamespace.XML_LATEX_INCLUDES)).ifPresent(node -> latexIncludes.setText(node.getTextContent()));
		Optional.ofNullable(prefMap.get(LNamespace.XML_OPENGL)).ifPresent(node -> openGL.setSelected(Boolean.parseBoolean(node.getTextContent())));
		Optional.ofNullable(prefMap.get(LNamespace.XML_CHECK_VERSION)).ifPresent(node -> checkNewVersion.setSelected(Boolean.parseBoolean(node.getTextContent())));

		Optional.ofNullable(prefMap.get(LNamespace.XML_CLASSIC_GRID)).ifPresent(node -> {
			if(Boolean.parseBoolean(node.getTextContent())) {
				styleList.getSelectionModel().select(GridStyle.STANDARD);
			}else {
				styleList.getSelectionModel().select(GridStyle.CUSTOMISED);
			}
		});

		Optional.ofNullable(prefMap.get(LNamespace.XML_DISPLAY_GRID)).map(node -> !Boolean.parseBoolean(node.getTextContent())).
			ifPresent(node -> styleList.getSelectionModel().select(GridStyle.NONE));

		Optional.ofNullable(prefMap.get(LNamespace.XML_GRID_GAP)).ifPresent(node -> persoGridGapField.getValueFactory().setValue(Integer.valueOf(node.getTextContent())));

		final Node langNode = prefMap.get(LNamespace.XML_LANG);
		final Locale locale = langNode == null ? Locale.US : Locale.forLanguageTag(langNode.getTextContent());
		if(langList.getItems().contains(locale)) {
			langList.getSelectionModel().select(locale);
		}else {
			langList.getSelectionModel().select(Locale.US);
		}
		Optional.ofNullable(prefMap.get(LNamespace.XML_MAGNETIC_GRID)).ifPresent(node -> magneticCB.setSelected(Boolean.parseBoolean(node.getTextContent())));
		Optional.ofNullable(prefMap.get(LNamespace.XML_PATH_EXPORT)).ifPresent(node -> pathExportField.setText(node.getTextContent()));
		Optional.ofNullable(prefMap.get(LNamespace.XML_PATH_OPEN)).ifPresent(node -> pathOpenField.setText(node.getTextContent()));
		Optional.ofNullable(prefMap.get(LNamespace.XML_UNIT)).ifPresent(node -> unitChoice.getSelectionModel().select(node.getTextContent()));
		Optional.ofNullable(prefMap.get(LNamespace.XML_RECENT_FILES)).ifPresent(node -> setRecentFiles(node));
		Optional.ofNullable(prefMap.get(LNamespace.XML_MAXIMISED)).ifPresent(node -> stage.setFullScreen(Boolean.parseBoolean(node.getTextContent())));
		Optional.ofNullable(prefMap.get(LNamespace.XML_SIZE)).ifPresent(node -> setStageSize(node, stage));
		Optional.ofNullable(prefMap.get(LNamespace.XML_POSITION)).ifPresent(node -> setStagePosition(node, stage));
	}

	private void setRecentFiles(final Node node) {
		final NodeList nl = node.getChildNodes();
		final NamedNodeMap nnm = node.getAttributes();
		recentFileNames.clear();

		if(nnm != null && nnm.getNamedItem(LNamespace.XML_NB_RECENT_FILES) != null) {
			Optional.ofNullable(nnm.getNamedItem(LNamespace.XML_NB_RECENT_FILES)).
				ifPresent(attr -> nbRecentFilesField.getValueFactory().setValue(Integer.valueOf(attr.getTextContent())));
		}

		for(int i = 0, size = nl.getLength(); i < size; i++) {
			final Node n2 = nl.item(i);

			if(n2.getNodeName().equals(LNamespace.XML_RECENT_FILE) && n2.getTextContent() != null) {
				recentFileNames.add(n2.getTextContent());
			}
		}
	}

	private void setStageSize(final Node node, final Stage stage) {
		final NodeList nl = node.getChildNodes();

		for(int i = 0, size = nl.getLength(); i < size; i++) {
			final Node n2 = nl.item(i);

			switch(n2.getNodeName()) {
				case LNamespace.XML_WIDTH:
					stage.setWidth(Double.valueOf(n2.getTextContent()));
					break;
				case LNamespace.XML_HEIGHT:
					stage.setHeight(Double.valueOf(n2.getTextContent()));
					break;
			}
		}
	}

	private void setStagePosition(final Node node, final Stage stage) {
		final NodeList nl = node.getChildNodes();

		for(int i = 0, size = nl.getLength(); i < size; i++) {
			final Node n2 = nl.item(i);

			switch(n2.getNodeName()) {
				case LNamespace.XML_POSITION_X:
					stage.setX(Math.max(0d, Double.valueOf(n2.getTextContent())));
					break;
				case LNamespace.XML_POSITION_Y:
					stage.setY(Math.max(0d, Double.valueOf(n2.getTextContent())));
					break;
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
		saver.updateRecentMenuItems(recentFileNames);

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

				document.setXmlVersion("1.0"); //NON-NLS
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
				elt.setTextContent(langList.getSelectionModel().getSelectedItem().toLanguageTag());
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

				for(final String recentFile : recentFileNames) {
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
				transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //NON-NLS
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //NON-NLS
				transformer.transform(new DOMSource(document), new StreamResult(fos));
			}
		}catch(final Exception ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}

	/**
	 * Reads the preferences of latexdraw defined in XML.
	 * @throws IllegalArgumentException If a problem occurs.
	 * @since 3.0
	 */
	public void readXMLPreferences() {
		final File xml = new File(LPath.PATH_PREFERENCES_XML_FILE);

		try {
			if(xml.canRead()) {
				processXMLDataPreference(xml);
			}
			applyValues();
		}catch(final SecurityException ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}

	/**
	 * Internal class to display locales correctly in combo boxes.
	 */
	static class LocaleCell extends ListCell<Locale> {
		LocaleCell() {
			super();
		}

		@Override
		protected void updateItem(final Locale item, final boolean empty) {
			super.updateItem(item, empty);
			if(item == null || empty) {
				setGraphic(null);
			}else {
				String country = item.getDisplayCountry();
				if(!country.isEmpty()) {
					country = " (" + country + ')';
				}
				setText(item.getDisplayLanguage() + country);
			}
		}
	}
}
