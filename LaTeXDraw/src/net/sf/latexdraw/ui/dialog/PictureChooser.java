package net.sf.latexdraw.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import net.sf.latexdraw.filters.*;
import net.sf.latexdraw.lang.LangTool;
import net.sf.latexdraw.util.LResources;

/**
 * This class defines a file chooser for pictures; some elements come from
 * the JavaSwing.jar demo code source Copyright (c) 2004 Sun Microsystems.<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
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
 * 04/11/06<br>
 * @author Arnaud BLOUIN
 * @version 2.0.0<br>
 */
public class PictureChooser extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;

	/** The file chooser. */
	JFileChooser fileChooser;

	/** The command name of the button cancel. */
	public static final String CMD_CANCEL = "PICTURE_CHOSSER_CANCEL";//$NON-NLS-1$

	/** The command name of the button ok. */
	public static final String CMD_OK = "PICTURE_CHOOSER_OK";//$NON-NLS-1$




	/**
	 * The constructor.
	 * @param parent The parent of the frame.
	 * @param msg The message displayed by the picture chooser.
	 * @param preview True: a thumbnail of the picture will be displayed.
	 * @param title The title of the chooser.
	 */
	public PictureChooser(final JFrame parent, final String msg, final boolean preview, final String title) {
		super(parent, title, true);

		fileChooser = new JFileChooser();
 		// add preview accessory
		if(preview)
			fileChooser.setAccessory(new FilePreviewer(fileChooser));
 		// remove the approve/cancel buttons
		fileChooser.setControlButtonsAreShown(false);
 		fileChooser.setApproveButtonText(LangTool.INSTANCE.getStringLaTeXDrawFrame("LaTeXDrawFrame.199")); //$NON-NLS-1$
 		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
 		fileChooser.addChoosableFileFilter(new BMPFilter());
 		fileChooser.addChoosableFileFilter(new PNGFilter());
 		fileChooser.addChoosableFileFilter(new GIFFilter());
 		fileChooser.addChoosableFileFilter(new JPGFilter());
 		fileChooser.setAcceptAllFileFilterUsed(true);

 		// make custom controls
 		JPanel custom = new JPanel();
 		custom.setLayout(new BoxLayout(custom, BoxLayout.Y_AXIS));
 		JLabel description = new JLabel(msg);
 		description.setAlignmentX(Component.CENTER_ALIGNMENT);
 		custom.add(description);
 		custom.add(fileChooser);
 		description.setLabelFor(fileChooser);

 		JPanel buttonsPanel = new JPanel();
 		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));

 		JButton cancel = new JButton(LResources.LABEL_CANCEL);
 		cancel.setActionCommand(CMD_CANCEL);
 		cancel.addActionListener(this);

 		JButton ok = new JButton(LResources.LABEL_OK);
 		ok.setActionCommand(CMD_OK);
 		ok.addActionListener(this);

 		buttonsPanel.add(ok);
 		buttonsPanel.add(cancel);

 		custom.add(buttonsPanel);

 		// show the filechooser
 		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
 		getContentPane().add(custom, BorderLayout.CENTER);

 		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
  		pack();
  		setLocation(dim.width/2-getWidth()/2, dim.height/2-getHeight()/2);
	}




	/**
	 * Allows to get the file chooser.
	 * @return The file chooser.
	 */
	public JFileChooser getFileChooser() {
		return fileChooser;
	}



	/**
	 * Allows to display the frame.
	 * @return The selected file.
	 */
	public File displayFrame()
	{
		super.setVisible(true);
		return fileChooser.getSelectedFile();
	}




	@Override
	public void actionPerformed(final ActionEvent e)
	{
		Object o = e.getSource();

		if(o instanceof JButton)
		{
			String cmd = ((JButton)o).getActionCommand();

			if(cmd==null) return;

			if(cmd.equals(CMD_CANCEL))
			{
				fileChooser.setSelectedFile(null);
				dispose();
				return ;
			}

			if(cmd.equals(CMD_OK))
			{
				dispose();
				return ;
			}
		}

	}
}



/**
 *	1.14 04/07/26
 * Copyright (c) 2004 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */
class FilePreviewer extends JComponent implements PropertyChangeListener
{
	private static final long serialVersionUID = 1L;

	ImageIcon thumbnail = null;


	/**
	 * Creates the file previewer.
	 * @param fc The associated file chooser.
	 * @since 3.0
	 */
    public FilePreviewer(final JFileChooser fc) {
    	super();

		setPreferredSize(new Dimension(100, 50));
		fc.addPropertyChangeListener(this);
		setBorder(new BevelBorder(BevelBorder.LOWERED));
    }


    /**
     * Loads the picture corresponding to the given file.
     * @param f The file to load.
     * @since 3.0
     */
    public void loadImage(final File f)
    {
        if(f == null)
            thumbnail = null;
        else
        {
		    ImageIcon tmpIcon = new ImageIcon(f.getPath());

		    if(tmpIcon.getIconWidth() > 90)
		    	 thumbnail = new ImageIcon(tmpIcon.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT));
		    else
		    	thumbnail = tmpIcon;
        }
    }

    @Override
	public void propertyChange(final PropertyChangeEvent e) {
		String prop = e.getPropertyName();
		if(prop == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY && isShowing()) {
	    	loadImage((File) e.getNewValue());
            repaint();
		}
    }


    @Override
	public void paint(final Graphics g)
    {
		super.paint(g);
		if(thumbnail != null)
		{
		    int x = getWidth()/2 - thumbnail.getIconWidth()/2;
		    int y = getHeight()/2 - thumbnail.getIconHeight()/2;

		    if(y < 0) y = 0;
		    if(x < 5) x = 5;

		    thumbnail.paintIcon(this, g, x, y);
		}
	}
}
