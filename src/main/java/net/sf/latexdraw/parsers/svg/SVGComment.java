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
package net.sf.latexdraw.parsers.svg;

import org.w3c.dom.Comment;
import org.w3c.dom.Node;

/**
 * Defines a comment node.
 * @author Arnaud BLOUIN
 */
public class SVGComment extends SVGText implements Comment {
	/**
	 * Creates a comment element.
	 * @param comment The text of the comment.
	 * @param owner The parent of the element.
	 */
	public SVGComment(final String comment, final SVGDocument owner) {
		super(comment, owner);
	}


	@Override
	public short getNodeType() {
		return Node.COMMENT_NODE;
	}


	@Override
	public String getNodeName() {
		return "#comment"; //NON-NLS
	}
}
