package net.sf.latexdraw.instruments;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import net.sf.latexdraw.util.LResources;
import net.sf.latexdraw.util.LangTool;

/**
 * The controller of the shortcuts dialogue box.<br>
 * <br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
 * 2014-11-15<br>
 * @author Arnaud BLOUIN
 * @version 4.0
 */
public class ShortcutsController implements Initializable {
	@FXML private TableView<ObservableList<String>> table;
	
	/**
	 * Creates the controller.
	 */
	public ShortcutsController() {
		super();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		final String ctrl = KeyEvent.getKeyModifiersText(InputEvent.CTRL_MASK);
		final String shift = KeyEvent.getKeyModifiersText(InputEvent.SHIFT_MASK);
		final String leftClick = LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.8"); //$NON-NLS-1$
		final String catEdit	= LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.89"); //$NON-NLS-1$
		final String catNav		= LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.4"); //$NON-NLS-1$
		final String catTran	= LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.5"); //$NON-NLS-1$
		final String catDraw	= LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.6"); //$NON-NLS-1$
		final String catFile	= LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.88"); //$NON-NLS-1$
		
	    for (int i=0, size=table.getColumns().size(); i<size ; i++) {
	        final int colIndex = i ;
	        table.getColumns().get(i).setCellValueFactory(cellData -> {return new ReadOnlyStringWrapper(cellData.getValue().get(colIndex));});
	    }
	   
	    table.getColumns().stream().forEach(col -> col.prefWidthProperty().bind(table.widthProperty().divide(3)));
	    
	    table.getItems().addAll(
	    		FXCollections.observableArrayList(ctrl+"+C", LResources.LABEL_COPY, catEdit),
					FXCollections.observableArrayList(ctrl+"+V", LResources.LABEL_PASTE, catEdit),//$NON-NLS-1$
					FXCollections.observableArrayList(ctrl+"+X", LResources.LABEL_CUT, catEdit),//$NON-NLS-1$
					FXCollections.observableArrayList(ctrl+"+Z", LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.23"), catEdit),//$NON-NLS-1$ //$NON-NLS-2$
					FXCollections.observableArrayList(ctrl+"+Y", LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.22"), catEdit),//$NON-NLS-1$ //$NON-NLS-2$
					FXCollections.observableArrayList(ctrl+"+N", LResources.LABEL_NEW, catFile),//$NON-NLS-1$
					FXCollections.observableArrayList(ctrl+"+O", FileLoaderSaver.LABEL_OPEN, catFile),//$NON-NLS-1$
					FXCollections.observableArrayList(ctrl+"+S", FileLoaderSaver.LABEL_SAVE, catFile),//$NON-NLS-1$
					FXCollections.observableArrayList(ctrl+"+W", LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.18"), catFile),//$NON-NLS-1$ //$NON-NLS-2$
					FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_ADD), LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.57"), catNav), //$NON-NLS-1$
					FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_SUBTRACT), LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.58"), catNav), //$NON-NLS-1$
					FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_DELETE), LangTool.INSTANCE.getBundle().getString("LaTeXDrawFrame.17"), catDraw), //$NON-NLS-1$
					FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_RIGHT), LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.9"), catNav),
					FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_LEFT), LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.10"), catNav),
					FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_UP), LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.11"), catNav),
					FXCollections.observableArrayList(KeyEvent.getKeyText(KeyEvent.VK_DOWN), LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.12"), catNav),
					FXCollections.observableArrayList(ctrl+"+U", LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.23"), catTran), //$NON-NLS-1$ //$NON-NLS-2$
					FXCollections.observableArrayList(ctrl+"+A", LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.25"), catDraw), //$NON-NLS-1$ //$NON-NLS-2$
					FXCollections.observableArrayList(ctrl+'+'+leftClick, LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.26"), catDraw), //$NON-NLS-1$
					FXCollections.observableArrayList(shift+'+'+leftClick, LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.27"), catDraw), //$NON-NLS-1$
					FXCollections.observableArrayList(ctrl+'+'+LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.29"), LangTool.INSTANCE.getBundle().getString("ShortcutsFrame.30"), catDraw) //$NON-NLS-1$ //$NON-NLS-2$
		);
	}
}
