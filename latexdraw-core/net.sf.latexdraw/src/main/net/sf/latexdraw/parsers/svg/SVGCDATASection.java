package net.sf.latexdraw.parsers.svg;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;

/**
 * Defines a CDATA node.<br>
 *<br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
 * 10/31/07<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class SVGCDATASection extends SVGText implements CDATASection {
	/**
	 * Creates an CData tag.
	 * @param text The text of the tag.
	 * @param owner The parent of the tag.
	 * @since 0.1
	 */
	public SVGCDATASection(final String text, final SVGDocument owner) {
		super(text, owner);
		setNodeName("#cdata-section");//$NON-NLS-1$
	}


    @Override
	public short getNodeType() {
        return Node.CDATA_SECTION_NODE;
    }
}
