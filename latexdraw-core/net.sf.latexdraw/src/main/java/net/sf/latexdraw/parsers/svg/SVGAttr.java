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

import java.util.Objects;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

/**
 * Defines an SVG attribute.
 * @author Arnaud BLOUIN
 */
public class SVGAttr implements Attr, Cloneable {
	/** The name of the attribute. */
	private final String name;

	/** The owner of the attribute. */
	private final Element owner;

	/** The value of the attribute. */
	private String value;


	/**
	 * Creates an SVG attribute.
	 * @param n The name of the node.
	 * @param val The value of the node.
	 * @param parent The parent of the node.
	 * @throws NullPointerException If one of the given parameters is null.
	 */
	public SVGAttr(final String n, final String val, final Element parent) {
		super();
		name = Objects.requireNonNull(n);
		owner = Objects.requireNonNull(parent);
		value = Objects.requireNonNull(val);
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
	public Element getOwnerElement() {
		return owner;
	}


	@Override
	public String getValue() {
		return value;
	}


	@Override
	public boolean isId() {
		return name.equals(SVGAttributes.SVG_ID);
	}


	@Override
	public void setValue(final String val) {
		if(val == null) {
			throw new DOMException(DOMException.SYNTAX_ERR, "A value cannot be null");
		}
		value = val;
	}


	/** No deep allow. */
	@Override
	public Node cloneNode(final boolean deep) {
		try {
			return (SVGAttr) clone();
		}catch(final CloneNotSupportedException ex) {
			return null;
		}
	}


	@Override
	public String getNodeName() {
		return name;
	}


	@Override
	public short getNodeType() {
		return Node.ATTRIBUTE_NODE;
	}


	@Override
	public String getNodeValue() {
		return value;
	}


	@Override
	public Node getParentNode() {
		return owner;
	}


	@Override
	public boolean hasAttributes() {
		return false;
	}


	@Override
	public boolean hasChildNodes() {
		return false;
	}


	@Override
	public boolean isEqualNode(final Node node) {
		return node != null && node.getNodeName().equals(name) && node.getNodeValue().equals(value) && node.getNodeType() == getNodeType();
	}


	@Override
	public boolean isSameNode(final Node other) {
		return other != null && other == this;
	}


	@Override
	public void setNodeValue(final String nodeValue) {
		setValue(nodeValue);
	}


	@Override
	public String toString() {
		return "[" + name + ", " + value + ']'; //NON-NLS
	}


	@Override
	public String getPrefix() {
		if(getNodeName() == null) {
			return null;
		}

		final int index = getName().indexOf(':');

		if(index != -1) {
			return getName().substring(0, index);
		}

		return null;
	}


	@Override
	public boolean getSpecified() {
		return true;
	}


	@Override
	public String getNamespaceURI() {
		return lookupNamespaceURI(getPrefix());
	}


	@Override
	public String lookupNamespaceURI(final String prefix) {
		return owner.lookupNamespaceURI(prefix);
	}


	@Override
	public String getLocalName() {
		return name;
	}


	@Override
	public TypeInfo getSchemaTypeInfo() {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public short compareDocumentPosition(final Node other) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public String getBaseURI() {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public Object getFeature(final String feature, final String version) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public Node getNextSibling() {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public Document getOwnerDocument() {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public Node getPreviousSibling() {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public String getTextContent() {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public Object getUserData(final String key) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public boolean isDefaultNamespace(final String namespaceURI) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public boolean isSupported(final String feature, final String version) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public String lookupPrefix(final String namespaceURI) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public void normalize() {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public Node replaceChild(final Node newChild, final Node oldChild) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public void setPrefix(final String prefix) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public void setTextContent(final String textContent) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}

	@Override
	public Object setUserData(final String key, final Object data, final UserDataHandler handler) {
		throw new DOMException(DOMException.INVALID_ACCESS_ERR, SVGDocument.ACTION_NOT_IMPLEMENTED);
	}


	@Override
	public Node removeChild(final Node oldChild) {
		return null;
	}

	@Override
	public Node insertBefore(final Node newChild, final Node refChild) {
		return null;
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	@Override
	public NodeList getChildNodes() {
		return new SVGNodeList();
	}

	@Override
	public Node getFirstChild() {
		return null;
	}

	@Override
	public Node getLastChild() {
		return null;
	}

	@Override
	public Node appendChild(final Node newChild) {
		return null;
	}
}
