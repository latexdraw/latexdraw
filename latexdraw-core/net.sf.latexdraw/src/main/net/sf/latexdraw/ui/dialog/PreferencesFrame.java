package net.sf.latexdraw.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.sf.latexdraw.instruments.PreferencesSetter;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;
import net.sf.latexdraw.util.LSystem;
import net.sf.latexdraw.util.VersionChecker;

import org.malai.swing.widget.MFrame;

/**
 * This frame allows the user to set his preferences concerning the program and its components.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
 * 09/14/06<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class PreferencesFrame extends MFrame {
	private static final long serialVersionUID = 1L;

	/** The instrument that modifies the preferences. */
	protected PreferencesSetter prefSetter;

	private static final String FOR_NEW_DRAWINGS = "Will be used for the next new drawings";


	/**
	 * The constructor using a frame.
	 * @param setter The instrument that modifies the preferences.
	 * @throws IllegalArgumentException If the given parameter is null.
	 */
	public PreferencesFrame(final PreferencesSetter setter) {
		super(true);

		this.prefSetter = Objects.requireNonNull(setter);
  		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

  		setIconImage(LResources.PROPERTIES_ICON.getImage());

	  	setTitle(LangTool.INSTANCE.getStringDialogFrame("PreferencesFrame.Pref")); //$NON-NLS-1$
  		setLocation(dim.width/3, dim.height/4);

  		final JTabbedPane tabbedPane = new JTabbedPane();
  		final JPanel pGeneral = new JPanel(new BorderLayout());

  		tabbedPane.add(LangTool.INSTANCE.getStringDialogFrame("PreferencesFrame.general"), createGeneralPanel()); //$NON-NLS-1$
  		tabbedPane.add("LaTeX", createLatexPanel()); //$NON-NLS-1$
  		tabbedPane.add(LangTool.INSTANCE.getStringDialogFrame("PreferencesFrame.folders"), createFoldersPanel()); //$NON-NLS-1$
  		tabbedPane.add(LangTool.INSTANCE.getStringDialogFrame("PreferencesFrame.quality"), createQualityPanel()); //$NON-NLS-1$
  		tabbedPane.add(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.90"), createDisplayPanel()); //$NON-NLS-1$
  		tabbedPane.add(LangTool.INSTANCE.getStringDialogFrame("PreferencesFrame.grid"), createGridPanel()); //$NON-NLS-1$

		pGeneral.add(tabbedPane, BorderLayout.CENTER);

		getContentPane().add(pGeneral);
  		setSize(460, 320);
		setVisible(false);
	}

JTextField latexDistribField = new JTextField();

	private JPanel createLatexPanel() {
		final JPanel pLatex = new JPanel();
		final JPanel pLatexDistrib = new JPanel();
		pLatex.setLayout(new BoxLayout(pLatex, BoxLayout.Y_AXIS));
latexDistribField.setEditable(false);
latexDistribField.setMaximumSize(new Dimension(700, 80));
		pLatexDistrib.setLayout(new BoxLayout(pLatexDistrib, BoxLayout.X_AXIS));
		final JButton bChooseLatex = new JButton(LResources.OPEN_ICON);
		bChooseLatex.addActionListener(new ActionListener() {
			@Override public void actionPerformed(final ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setMultiSelectionEnabled(false);
			    chooser.showDialog(PreferencesFrame.this, "Select");
			    if(chooser.getSelectedFile()!=null) {
			    	latexDistribField.setText(chooser.getSelectedFile().getPath()+File.separatorChar);
			    	LSystem.INSTANCE.setLatexDistribPath(chooser.getSelectedFile().getPath()+File.separatorChar);
			    }
			}
		});
		pLatexDistrib.add(latexDistribField);
		pLatexDistrib.add(bChooseLatex);
		pLatex.add(new JLabel("The path of the latex binaires:"));
		pLatex.add(pLatexDistrib);

  		pLatex.add(new JLabel("Packages included during latex compilations:"));
  		pLatex.add(prefSetter.getLatexIncludes().getScrollpane());

		return pLatex;
	}



	/**
	 * Creates the panel which allows the user to set the general preferences of latexdraw.
	 * @return The created panel.
	 */
	private JPanel createGeneralPanel() {
		final JPanel pGeneral 	= new JPanel();
		final JPanel pLang 		= new JPanel();
  		final JPanel pRecent 	= new JPanel();
  		final JPanel pTheme		= new JPanel();

  		pTheme.setLayout(new BoxLayout(pTheme, BoxLayout.X_AXIS));
		pLang.setLayout(new BoxLayout(pLang, BoxLayout.X_AXIS));
  		pGeneral.setLayout(new GridLayout(6, 1));
  		pRecent.setLayout(new FlowLayout(FlowLayout.LEFT));

  		pRecent.add(prefSetter.getNbRecentFilesField().getLabel());
  		pRecent.add(prefSetter.getNbRecentFilesField());

  		pTheme.add(prefSetter.getThemeList().getLabel());
  		pTheme.add(prefSetter.getThemeList());

  		pLang.add(new JLabel(LangTool.INSTANCE.getStringDialogFrame("PreferencesFrame.lge"))); //$NON-NLS-1$
  		pLang.add(prefSetter.getLangList());

  		pGeneral.add(pLang);
  		pGeneral.add(pTheme);
  		if(VersionChecker.WITH_UPDATE)
	  		pGeneral.add(prefSetter.getCheckNewVersion());
  		pGeneral.add(pRecent);

		return pGeneral;
	}


	@Override
	public void setVisible(final boolean visible) {
		super.setVisible(visible);

		if(visible) {
	 		final Dimension dim = LSystem.INSTANCE.getScreenDimension();
	 		final Rectangle rec = getGraphicsConfiguration().getBounds();
	 		setLocation((int)(rec.getX()+dim.width/2.0-getWidth()/2.0), (int)(rec.getY()+dim.height/2.0-getHeight()/2.0));
		}
	}


	/**
	 * Creates a JPanel containing elements allowing the set of the grid parameters.
	 * @return The created JPanel.
	 */
	private JPanel createGridPanel() {
		final JPanel pGrid = new JPanel(new GridLayout(6, 1));
		final JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(prefSetter.getPersoGridGapField().getLabel());
		panel.add(prefSetter.getPersoGridGapField());

		pGrid.add(new JLabel(FOR_NEW_DRAWINGS));
  		pGrid.add(prefSetter.getDisplayGridCB());
  		pGrid.add(prefSetter.getClassicGridRB());
  		pGrid.add(prefSetter.getPersoGridRB());
  		pGrid.add(prefSetter.getMagneticGridCB());
  		pGrid.add(panel);

		return pGrid;
	}



	/**
	 * Creates the panel which allows the user to set the preferences about displayable elements.
	 * @return The created panel.
	 */
	private JPanel createDisplayPanel() {
  		final JPanel pDisplay = new JPanel(new GridLayout(5, 1));
		final JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(prefSetter.getUnitChoice());

		pDisplay.add(new JLabel(FOR_NEW_DRAWINGS));
		pDisplay.add(prefSetter.getDisplayXScaleCB());
  		pDisplay.add(prefSetter.getDisplayYScaleCB());
  		pDisplay.add(panel);

  		return pDisplay;
	}



	/**
	 * Creates the panel which allows the user to set preferences about paths.
	 * @return The created panel.
	 */
	private JPanel createFoldersPanel() {
  		final JPanel pFolders  = new JPanel(new GridBagLayout());
  		final JButton bExport  = new JButton(LResources.OPEN_ICON);
  		final JButton bOpen    = new JButton(LResources.OPEN_ICON);
  		final GridBagConstraints constraint = new GridBagConstraints();

    	constraint.gridx = 0;
     	constraint.gridy = 0;
     	constraint.gridwidth = 6;
     	constraint.gridheight = 1;
     	constraint.weightx = 0.1;
     	constraint.weighty = 0.1;
     	constraint.fill = GridBagConstraints.HORIZONTAL;
     	constraint.anchor = GridBagConstraints.EAST;
     	pFolders.add(new JLabel(LangTool.INSTANCE.getStringDialogFrame("PreferencesFrame.defOpenSave")), constraint); //$NON-NLS-1$

     	constraint.gridy = 1;
     	constraint.weightx = 10;
     	pFolders.add(prefSetter.getPathOpenField(), constraint);

    	constraint.gridx = 6;
     	constraint.gridwidth = 1;
     	constraint.weightx = 0.1;
     	constraint.fill = GridBagConstraints.NONE;
     	pFolders.add(bOpen, constraint);

    	constraint.gridx = 0;
     	constraint.gridy = 2;
     	constraint.gridwidth = 6;
     	constraint.fill = GridBagConstraints.HORIZONTAL;
     	pFolders.add(new JLabel(LangTool.INSTANCE.getStringDialogFrame("PreferencesFrame.defFold")), constraint); //$NON-NLS-1$

     	constraint.gridy = 3;
     	constraint.weightx = 10;
     	pFolders.add(prefSetter.getPathExportField(), constraint);

    	constraint.gridx = 6;
     	constraint.gridwidth = 1;
     	constraint.weightx = 0.1;
     	constraint.fill = GridBagConstraints.NONE;
     	pFolders.add(bExport, constraint);

  		return pFolders;
	}


	/**
	 * Creates the panel which allows the user to set preferences about the rendering.
	 * @return The created panel.
	 */
	private JPanel createQualityPanel() {
  		final JPanel pQuality  = new JPanel(new GridLayout(5, 1));

  		pQuality.add(new JLabel(FOR_NEW_DRAWINGS));
  		pQuality.add(prefSetter.getAntialiasingCheckBox());
  		pQuality.add(prefSetter.getRenderingCheckBox());
  		pQuality.add(prefSetter.getColorRenderCheckBox());
  		pQuality.add(prefSetter.getAlpaInterCheckBox());

  		return pQuality;
	}
}
