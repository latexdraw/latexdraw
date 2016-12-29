package net.sf.latexdraw.ui.dialog;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
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
import org.malai.swing.widget.MDialog;
import org.malai.swing.widget.MEditorPane;
import org.malai.swing.widget.MPanel;

/**
 * This dialogue box allows users to insert code that will be converted into shapes.
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
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
public class InsertCodeDialog extends MDialog {
	private static final long serialVersionUID = 1L;

	/** This editor containing code */
	private final MEditorPane editor;

	private final MButton okButton;

	private final MButton cancelButton;

	private final JEditorPane errorMessages;


	/**
	 * Creates the code dialog box.
	 * @param parentIns The instrument managing this dialogue box.
	 */
	public InsertCodeDialog(final Frame parent, final Instrument parentIns) {
		super(parent, LangTool.INSTANCE.getString16("InsertPSTricksCodeFrame.0"), true); //$NON-NLS-1$
		setIconImage(LResources.INSERT_PST_ICON.getImage());
  		final MPanel pButton = new MPanel(false, true);
  		okButton = new MButton(LResources.LABEL_OK);
		cancelButton = new MButton(LResources.LABEL_CANCEL);
  		editor = new MEditorPane(true, true);
		parentIns.addEventable(pButton);
		parentIns.addEventable(editor);
  		// The scroller of the editor
		final JScrollPane scrollPane = new JScrollPane(editor);
		scrollPane.setMinimumSize(new Dimension(450, 250));
		scrollPane.setPreferredSize(new Dimension(450, 250));
 		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		final JLabel label = new JLabel(LangTool.INSTANCE.getString16("LaTeXDrawFrame.16"), SwingConstants.CENTER); //$NON-NLS-1$
		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		errorMessages = new JEditorPane();
		errorMessages.setEditable(false);
		final JScrollPane scrollPaneErr = new JScrollPane(errorMessages);
		scrollPaneErr.setMinimumSize(new Dimension(450, 50));
		scrollPaneErr.setPreferredSize(new Dimension(450, 50));

		editor.setText("");//$NON-NLS-1$
		pButton.add(okButton);
		pButton.add(cancelButton);
		pButton.setPreferredSize(new Dimension(280, 40));
		pButton.setMaximumSize(new Dimension(280, 40));
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(label);
		getContentPane().add(scrollPane);
		getContentPane().add(scrollPaneErr);
		getContentPane().add(pButton);

 		final Dimension dim = LSystem.INSTANCE.getScreenDimension();
 		final Rectangle rec = getGraphicsConfiguration().getBounds();
  		pack();
  		setLocation((int)(rec.getX()+dim.width/2.0-getWidth()/2.0), (int)(rec.getY()+dim.height/2.0-getHeight()/2.0));
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
	public void setVisible(final boolean visible) {
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

	public void setErrorMessage(final String message) {
		okButton.setEnabled(false);
		errorMessages.setText(message);
	}

	public void cleanErrorMessage() {
		okButton.setEnabled(true);
		errorMessages.setText("");
	}
}
