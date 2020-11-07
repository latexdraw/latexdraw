/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.service;

import io.github.interacto.undo.UndoCollector;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.util.BadaboomCollector;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.util.Page;
import net.sf.latexdraw.util.SystemUtils;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.util.VersionChecker;
import net.sf.latexdraw.view.GridStyle;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class PreferencesService {
	/** The recent files. */
	private final @NotNull ListProperty<String> recentFileNames;
	/** The current handled file. */
	private @NotNull Optional<File> currentFile;
	/** The current working folder. */
	private @NotNull Optional<File> currentFolder;
	private final @NotNull IntegerProperty nbRecentFiles;
	private final @NotNull IntegerProperty gridGap;
	private final @NotNull BooleanProperty checkVersion;
	private final @NotNull BooleanProperty magneticGrid;
	private final @NotNull StringProperty pathExport;
	private final @NotNull StringProperty pathOpen;
	private final @NotNull StringProperty includes;
	private final @NotNull ObjectProperty<Locale> lang;
	private final @NotNull ResourceBundle bundle;
	private final @NotNull ObjectProperty<GridStyle> gridStyle;
	private final @NotNull ObjectProperty<Unit> unit;
	private final @NotNull ObjectProperty<Page> page;
	private final @NotNull String prefsPath;

	PreferencesService(final @NotNull String prefsPath) {
		super();
		this.prefsPath = prefsPath;
		lang = new SimpleObjectProperty<>(readLang());
		bundle = loadResourceBundle(lang.get()).orElseThrow(
			() -> new IllegalArgumentException("Cannot read any resource bundle in this lang: " + lang.get()));
		nbRecentFiles = new SimpleIntegerProperty(5);
		gridGap = new SimpleIntegerProperty(10);
		checkVersion = new SimpleBooleanProperty(true);
		gridStyle = new SimpleObjectProperty<>(GridStyle.NONE);
		unit = new SimpleObjectProperty<>(Unit.CM);
		magneticGrid = new SimpleBooleanProperty(false);
		pathExport = new SimpleStringProperty("");
		pathOpen = new SimpleStringProperty("");
		includes = new SimpleStringProperty("");
		currentFile = Optional.empty();
		currentFolder = Optional.empty();
		recentFileNames = new SimpleListProperty<>(FXCollections.observableArrayList());
		page = new SimpleObjectProperty<>(Page.USLETTER);

		nbRecentFiles.addListener((observable, oldValue, newValue) -> {
			while(newValue.intValue() > recentFileNames.size() && !recentFileNames.isEmpty()) {
				recentFileNames.remove(recentFileNames.size() - 1);
			}
		});

		UndoCollector.getInstance().setBundle(bundle);
	}

	public PreferencesService() {
		this(SystemUtils.getInstance().getPathLocalUser() + File.separator + ".preferences.xml"); //NON-NLS
	}

	public @NotNull StringProperty includesProperty() {
		return includes;
	}

	public @NotNull IntegerProperty nbRecentFilesProperty() {
		return nbRecentFiles;
	}

	public @NotNull Locale getLang() {
		return lang.get();
	}

	public @NotNull Page getPage() {
		return page.get();
	}

	public void setPage(final @NotNull Page newPage) {
		page.set(newPage);
	}

	public @NotNull ObjectProperty<Page> pageProperty() {
		return page;
	}

	public @NotNull IntegerProperty gridGapProperty() {
		return gridGap;
	}

	public boolean isMagneticGrid() {
		return magneticGrid.get();
	}

	public @NotNull GridStyle getGridStyle() {
		return gridStyle.get();
	}

	public @NotNull ObjectProperty<GridStyle> gridStyleProperty() {
		return gridStyle;
	}

	public @NotNull Unit getUnit() {
		return unit.get();
	}

	public @NotNull ObjectProperty<Unit> unitProperty() {
		return unit;
	}

	public @NotNull ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * Gets the language of the program.
	 * @return The read language, the default language, or nothing.
	 */
	private @NotNull Locale readLang() {
		Optional<Locale> res = Optional.empty();

		try {
			final Node node =
				DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Files.newInputStream(Paths.get(getPreferencesPath()))).getFirstChild();

			if(node != null && LNamespace.XML_ROOT_PREFERENCES.equals(node.getNodeName())) {
				final NodeList nl = node.getChildNodes();
				res = IntStream.range(0, nl.getLength()).
					filter(i -> LNamespace.XML_LANG.equals(nl.item(i).getNodeName())).
					mapToObj(i -> Locale.forLanguageTag(nl.item(i).getTextContent())).findFirst();
			}
		}catch(final InvalidPathException | SAXException | ParserConfigurationException | IOException ignored) {
			// No preferences file
		}

		// Getting the current locale
		final Locale locale = res.orElseGet(() -> Locale.getDefault());

		// If this locale is supported (same language, same country), it is used
		if(getSupportedLocales().stream()
			.anyMatch(loc -> loc.getLanguage().equals(locale.getLanguage()) && loc.getCountry().equals(locale.getCountry()))) {
			return locale;
		}

		return getSupportedLocales()
			.stream()
			// Finding a locale that has the same language
			.filter(loc -> loc.getLanguage().equals(locale.getLanguage()))
			.findFirst()
			// Or use en-US by default
			.orElse(Locale.forLanguageTag("en-US")); //NON-NLS
	}


	public @NotNull List<Locale> getSupportedLocales() {
		return Stream.of("ar", "ca", "cs", "de", "en-GB", "en-US", "es", "fr", "gl", "hu", "it", "iw", "ja", //NON-NLS
			"nl", "oc", "pl", "pt", "pt-BR", "ru", "si", "sr", "sv", "ta", "tr", "uk", "vi", "zh-CN", "zh-TW") //NON-NLS
			.map(id -> Locale.forLanguageTag(id)).collect(Collectors.toList());
	}


	private @NotNull Optional<ResourceBundle> loadResourceBundle(final @NotNull Locale locale) {
		try {
			return Optional.ofNullable(ResourceBundle.getBundle("lang.bundle", locale)); //NON-NLS
		}catch(final MissingResourceException ex) {
			BadaboomCollector.INSTANCE.add(ex);
			return Optional.empty();
		}
	}

	public @NotNull BooleanProperty checkVersionProperty() {
		return checkVersion;
	}

	public @NotNull String getPathExport() {
		return pathExport.get();
	}

	public @NotNull String getPathOpen() {
		return pathOpen.get();
	}

	public @NotNull BooleanProperty magneticGridProperty() {
		return magneticGrid;
	}

	public @NotNull ObjectProperty<Locale> langProperty() {
		return lang;
	}

	public int getNbRecentFiles() {
		return nbRecentFiles.get();
	}

	public void setNbRecentFiles(final int nbFiles) {
		this.nbRecentFiles.set(nbFiles);
		while(recentFileNames.size() > nbRecentFiles.get()) {
			recentFileNames.remove(recentFileNames.size() - 1);
		}
	}

	public @NotNull StringProperty pathExportProperty() {
		return pathExport;
	}

	public @NotNull StringProperty pathOpenProperty() {
		return pathOpen;
	}

	public @NotNull Optional<File> getCurrentFolder() {
		return currentFolder;
	}

	public void setCurrentFolder(final File folder) {
		currentFolder = Optional.ofNullable(folder);
	}

	public @NotNull Optional<File> getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(final File file) {
		currentFile = Optional.ofNullable(file);
	}

	public @NotNull ListProperty<String> getRecentFiles() {
		return recentFileNames;
	}

	private void setRecentFiles(final @NotNull Node node) {
		final NodeList nl = node.getChildNodes();
		final NamedNodeMap nnm = node.getAttributes();

		if(nnm != null && nnm.getNamedItem(LNamespace.XML_NB_RECENT_FILES) != null) {
			MathUtils.INST.parseInt(nnm.getNamedItem(LNamespace.XML_NB_RECENT_FILES).getTextContent()).ifPresent(val -> nbRecentFiles.setValue(val));
		}

		recentFileNames.setAll(IntStream
			.range(0, Math.min(nbRecentFiles.get(), nl.getLength()))
			.mapToObj(i -> nl.item(i))
			.filter(n -> LNamespace.XML_RECENT_FILE.equals(n.getNodeName()) && n.getTextContent() != null)
			.map(n -> n.getTextContent())
			.collect(Collectors.toList()));
	}

	public void addRecentFile(final @NotNull String absolutePath) {
		final int i = recentFileNames.indexOf(absolutePath);

		if(i != -1) {
			recentFileNames.remove(i);
		}

		if(recentFileNames.size() == nbRecentFiles.get()) {
			recentFileNames.remove(nbRecentFiles.get() - 1);
		}

		recentFileNames.add(0, absolutePath);
	}


	@NotNull String getPreferencesPath() {
		return prefsPath;
	}


	public void writePreferences() {
		final Optional<Document> opt = SystemUtils.getInstance().createXMLDocumentBuilder().map(b -> b.newDocument());

		if(opt.isEmpty()) {
			return;
		}

		final Document document = opt.get();
		final Element root = document.createElement(LNamespace.XML_ROOT_PREFERENCES);

		document.setXmlVersion("1.0"); //NON-NLS
		document.setXmlStandalone(true);
		document.appendChild(root);

		final Attr attr = document.createAttribute(LNamespace.XML_VERSION);
		attr.setTextContent(VersionChecker.VERSION);
		root.setAttributeNode(attr);

		SystemUtils.getInstance().createElement(document, LNamespace.XML_PATH_EXPORT, pathExport.get(), root);
		SystemUtils.getInstance().createElement(document, LNamespace.XML_PATH_OPEN, pathOpen.get(), root);
		SystemUtils.getInstance().createElement(document, LNamespace.XML_UNIT, unit.get().name(), root);
		SystemUtils.getInstance().createElement(document, LNamespace.XML_PAGE, page.get().name(), root);
		SystemUtils.getInstance().createElement(document, LNamespace.XML_CHECK_VERSION, String.valueOf(checkVersion.get()), root);
		SystemUtils.getInstance().createElement(document, LNamespace.XML_LANG, lang.get().toLanguageTag(), root);
		SystemUtils.getInstance().createElement(document, LNamespace.XML_MAGNETIC_GRID, String.valueOf(magneticGrid.get()), root);
		SystemUtils.getInstance().createElement(document, LNamespace.XML_GRID_STYLE, gridStyle.get().name(), root);
		SystemUtils.getInstance().createElement(document, LNamespace.XML_GRID_GAP, String.valueOf(gridGap.get()), root);
		SystemUtils.getInstance().createElement(document, LNamespace.XML_LATEX_INCLUDES, includes.get(), root);
		final Element recent = document.createElement(LNamespace.XML_RECENT_FILES);
		root.appendChild(recent);
		recent.setAttribute(LNamespace.XML_NB_RECENT_FILES, String.valueOf(nbRecentFiles.get()));
		recentFileNames.forEach(n -> SystemUtils.getInstance().createElement(document, LNamespace.XML_RECENT_FILE, n, recent));

		try {
			try(final OutputStream fos = Files.newOutputStream(Path.of(getPreferencesPath()))) {
				final Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //NON-NLS
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //NON-NLS
				transformer.transform(new DOMSource(document), new StreamResult(fos));
			}
		}catch(final TransformerException | IllegalArgumentException | DOMException | IOException | FactoryConfigurationError ex) {
			BadaboomCollector.INSTANCE.add(ex);
		}
	}


	public void readPreferences() {
		processXMLDataPreference(readPreferencesFromFile(new File(getPreferencesPath())));
	}


	private Map<String, Node> readPreferencesFromFile(final @NotNull File xmlFile) {
		return SystemUtils.getInstance().createXMLDocumentBuilder().map(builder -> {
			try {
				final NodeList nl = builder.parse(xmlFile).getFirstChild().getChildNodes();
				// Transforming the list of nodes in a map.
				return Collections.unmodifiableMap(IntStream
					.range(0, nl.getLength())
					.mapToObj(index -> nl.item(index))
					.filter(node -> node.getNodeName() != null && !node.getNodeName().isEmpty() && !(node instanceof Text))
					.collect(Collectors.toMap(n -> n.getNodeName(), n -> n)));
			}catch(final IOException | SAXException ignored) {
				return Collections.<String, Node>emptyMap();
			}
		}).orElse(Collections.emptyMap());
	}


	private void processXMLDataPreference(final @NotNull Map<String, Node> prefMap) {
		final Optional<Element> opt = SystemUtils.getInstance().createXMLDocumentBuilder().map(b -> b.newDocument().createElement("STUB")); //NON-NLS

		if(opt.isEmpty()) {
			return;
		}

		final Element noElt = opt.get();

		final String incl = prefMap.getOrDefault(LNamespace.XML_LATEX_INCLUDES, noElt).getTextContent();
		if(incl != null) {
			includes.setValue(incl);
		}

		final String check = prefMap.getOrDefault(LNamespace.XML_CHECK_VERSION, noElt).getTextContent();
		if(check != null) {
			checkVersion.setValue(Boolean.valueOf(check));
		}

		GridStyle.getStylefromName(prefMap.getOrDefault(LNamespace.XML_GRID_STYLE, noElt).getTextContent()).ifPresent(style -> gridStyle.set(style));

		MathUtils.INST.parseInt(prefMap.getOrDefault(LNamespace.XML_GRID_GAP, noElt).getTextContent()).ifPresent(gap -> gridGap.set(gap));

		final String magnet = prefMap.getOrDefault(LNamespace.XML_MAGNETIC_GRID, noElt).getTextContent();
		if(magnet != null) {
			magneticGrid.setValue(Boolean.valueOf(magnet));
		}

		final String export = prefMap.getOrDefault(LNamespace.XML_PATH_EXPORT, noElt).getTextContent();
		if(export != null) {
			pathExport.set(export);
		}

		final String open = prefMap.getOrDefault(LNamespace.XML_PATH_OPEN, noElt).getTextContent();
		if(open != null) {
			pathOpen.set(open);
		}

		Unit.getUnit(prefMap.getOrDefault(LNamespace.XML_UNIT, noElt).getTextContent()).ifPresent(u -> unit.set(u));

		Page.getPage(prefMap.getOrDefault(LNamespace.XML_PAGE, noElt).getTextContent()).ifPresent(p -> page.set(p));

		setRecentFiles(prefMap.getOrDefault(LNamespace.XML_RECENT_FILES, noElt));
	}
}
