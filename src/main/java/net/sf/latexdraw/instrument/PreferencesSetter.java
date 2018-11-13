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
package net.sf.latexdraw.instrument;

import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.DirectoryChooser;
import net.sf.latexdraw.service.PreferencesService;
import net.sf.latexdraw.util.Unit;
import net.sf.latexdraw.view.GridStyle;
import org.jetbrains.annotations.NotNull;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.library.ButtonPressed;

/**
 * This instrument modifies the preferences.
 * @author Arnaud BLOUIN
 */
public class PreferencesSetter extends JfxInstrument implements Initializable {
	/** Sets if the grid is magnetic. */
	@FXML protected CheckBox magneticCB;
	/** Allows the set if the program must check new version on start up. */
	@FXML protected CheckBox checkNewVersion;
	@FXML protected CheckBox openGL;
	/** This textField allows to set the default directories for open/save commands. */
	@FXML protected TextField pathOpenField;
	/** This textField allows to set the default directories for exporting commands. */
	@FXML protected TextField pathExportField;
	/** The text field used to defines the latex packages to use. */
	@FXML protected TextArea latexIncludes;
	/** Allows to set the unit of length by default. */
	@FXML protected ComboBox<Unit> unitChoice;
	/** The list that contains the supported languages. */
	@FXML protected ComboBox<Locale> langList;
	/** The field used to modifies the gap of the customised grid. */
	@FXML protected Spinner<Integer> magneticGridGap;
	/** The widget used to defines the number of recent file to keep in memory. */
	@FXML protected Spinner<Integer> nbRecentFilesField;
	/** Contains the different possible kind of grids. */
	@FXML protected ComboBox<GridStyle> styleList;
	@FXML protected Button buttonOpen;
	@FXML protected Button buttonExport;
	private final @NotNull PreferencesService prefsService;
	/** The file chooser of paths selection. */
	private DirectoryChooser fileChooser;
	/** to avoid GC'd */
	private ObjectProperty<Integer> gridGapProp;
	private ObjectProperty<Integer> recentFilesNb;

	/**
	 * Creates the instrument.
	 */
	public PreferencesSetter(final PreferencesService prefsService) {
		super();
		this.prefsService = Objects.requireNonNull(prefsService);
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		latexIncludes.setTooltip(new Tooltip("<html>" + //NON-NLS
			prefsService.getBundle().getString("PreferencesSetter.1") + //NON-NLS
			"<br>\\usepackage[frenchb]{babel}<br>\\usepackage[utf8]{inputenc}</html>"//NON-NLS
		));

		langList.setCellFactory(l -> new LocaleCell());
		langList.setButtonCell(new LocaleCell());
		langList.getItems().addAll(prefsService.getSupportedLocales());

		unitChoice.getItems().addAll(Unit.values());
		styleList.getItems().addAll(GridStyle.values());

		gridGapProp = prefsService.gridGapProperty().asObject();
		recentFilesNb = prefsService.nbRecentFilesProperty().asObject();

		nbRecentFilesField.getValueFactory().valueProperty().bindBidirectional(recentFilesNb);
		magneticGridGap.getValueFactory().valueProperty().bindBidirectional(gridGapProp);

		unitChoice.valueProperty().bindBidirectional(prefsService.unitProperty());

		styleList.valueProperty().bindBidirectional(prefsService.gridStyleProperty());

		langList.valueProperty().bindBidirectional(prefsService.langProperty());

		magneticCB.selectedProperty().bindBidirectional(prefsService.magneticGridProperty());

		openGL.selectedProperty().bindBidirectional(prefsService.openGLProperty());

		checkNewVersion.selectedProperty().bindBidirectional(prefsService.checkVersionProperty());

		latexIncludes.textProperty().bindBidirectional(prefsService.includesProperty());

		pathOpenField.textProperty().bindBidirectional(prefsService.pathOpenProperty());

		pathExportField.textProperty().bindBidirectional(prefsService.pathExportProperty());
	}

	@Override
	protected void configureBindings() {
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
			}
		}).on(buttonExport).bind();
	}

	/**
	 * @return The file chooser used to selected folders.
	 */
	private DirectoryChooser getFileChooser() {
		if(fileChooser == null) {
			fileChooser = new DirectoryChooser();
			fileChooser.setTitle(prefsService.getBundle().getString("PreferencesFrame.selectFolder")); //NON-NLS
		}
		return fileChooser;
	}


	/**
	 * @return True if a new version must be checked.
	 */
	public boolean isVersionCheckEnable() {
		return checkNewVersion != null && checkNewVersion.isSelected();
	}

	/**
	 * Internal class to display locales correctly in combo boxes.
	 */
	private static class LocaleCell extends ListCell<Locale> {
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
