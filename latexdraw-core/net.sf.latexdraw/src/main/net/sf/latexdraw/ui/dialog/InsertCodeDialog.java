package net.sf.latexdraw.ui.dialog;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;
import net.sf.latexdraw.util.LSystem;

import org.malai.instrument.Instrument;
import org.malai.swing.widget.MButton;
import org.malai.swing.widget.MFrame;
import org.malai.swing.widget.MPanel;

/**
 * This dialogue box allows users to insert code that will be converted into shapes.
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 *<br>
 * 2012-10-23<br>
 * @author Arnaud BLOUIN<br>
 * @version 3.0<br>
 */
public class InsertCodeDialog extends MFrame {
	private static final long serialVersionUID = 1L;

	/** This editor containing code */
	protected JEditorPane editor;

	protected MButton okButton;

	protected MButton cancelButton;


	/**
	 * Creates the code dialog box.
	 * @param parentIns The instrument managing this dialogue box.
	 */
	public InsertCodeDialog(Instrument parentIns) {
		super(LangTool.INSTANCE.getString16("InsertPSTricksCodeFrame.0"), true); //$NON-NLS-1$
		setIconImage(LResources.INSERT_PST_ICON.getImage());
  		final MPanel pButton = new MPanel(false, true);
  		okButton = new MButton(LResources.LABEL_OK);
		cancelButton = new MButton(LResources.LABEL_CANCEL);
		parentIns.addEventable(pButton);
  		editor = new JEditorPane();

  		// The scroller of the editor
		JScrollPane scrollPane = new JScrollPane(editor);
		scrollPane.setMinimumSize(new Dimension(450, 250));
		scrollPane.setPreferredSize(new Dimension(450, 250));
 		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JLabel label = new JLabel(LangTool.INSTANCE.getString16("LaTeXDrawFrame.16"), SwingConstants.CENTER); //$NON-NLS-1$
		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		editor.setText("");//$NON-NLS-1$
		pButton.add(okButton);
		pButton.add(cancelButton);
		pButton.setPreferredSize(new Dimension(280, 40));
		pButton.setMaximumSize(new Dimension(280, 40));
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(label);
		getContentPane().add(scrollPane);
		getContentPane().add(pButton);

 		Dimension dim = LSystem.INSTANCE.getScreenDimension();
 		Rectangle rec = getGraphicsConfiguration().getBounds();
  		pack();
  		setLocation((int)(rec.getX()+dim.width/2-getWidth()/2), (int)(rec.getY()+dim.height/2-getHeight()/2));
		setVisible(false);
	}


	/**
	 * @return The text of the editor.
	 * @since 3.0
	 */
	public String getText() {
		return editor.getText();
	}


	@Override
	public void setVisible(boolean visible) {
		if(visible)
			editor.setText("");//$NON-NLS-1$
		super.setVisible(visible);
	}


	/**
	 * @return the okButton.
	 * @since 3.0
	 */
	public MButton getOkButton() {
		return okButton;
	}

	/**
	 * @return the cancelButton.
	 * @since 3.0
	 */
	public MButton getCancelButton() {
		return cancelButton;
	}
}
