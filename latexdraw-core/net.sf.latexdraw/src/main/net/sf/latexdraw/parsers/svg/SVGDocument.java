package net.sf.latexdraw.parsers.svg;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.latexdraw.badaboom.BadaboomCollector;

import org.w3c.dom.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * Defines an SVG document.<br>
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
 * 05/23/10<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public class SVGDocument implements Document {
	/** The root of the SVG drawing. @since 0.1 */
	protected SVGSVGElement root;

    /** Defines if the document is standalone. @since 0.1 */
    protected boolean xmlStandalone;

    /** The document URI. @since 0.1 */
    protected String documentURI;

    /** The version of XML. @since 0.1 */
    protected String xmlVersion;

    /** The encoding of the document. @since 0.1 */
    protected String xmlEncoding;


    public final static String ACTION_NOT_IMPLEMENTED = "Action not implemented.";//$NON-NLS-1$


    public static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";//$NON-NLS-1$


	/**
	 * The constructor. An URI defines the location of the SVG document to parse. If the document is valid,
	 * the document is read an place in the <code>root</code> attribute.
	 * @param uri The file to parse.
	 * @throws MalformedSVGDocument If an error occurs.
	 * @throws IOException If the document cannot be opened.
	 * @throws IllegalArgumentException If a n argument is not valid.
	 */
	public SVGDocument(final URI uri) throws MalformedSVGDocument, IOException {
		if(uri==null)
			throw new IllegalArgumentException();

		try {
			final DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
	        final DocumentBuilder builder 		= factory.newDocumentBuilder();

	        builder.setEntityResolver(new SVGEntityResolver());

			final Document doc = builder.parse(uri.getPath());
			NodeList nl;

			setDocumentURI(getDocumentURI());
			setXmlStandalone(doc.getXmlStandalone());
			setXmlVersion(doc.getXmlVersion());
			xmlEncoding = doc.getXmlEncoding();
			root 		= null;
			nl 			= doc.getChildNodes();
			Node n;

			for(int i=0, size = nl.getLength(); i<size && root==null; i++) {
				n = nl.item(i);

				if(n instanceof Element && n.getNodeName().endsWith(SVGElements.SVG_SVG))
					root = new SVGSVGElement(this, nl.item(i));
			}
		}
		catch(final SAXException | ParserConfigurationException e) {
			BadaboomCollector.INSTANCE.add(e);
			throw new MalformedSVGDocument();
		}
	}



	/**
	 * Creates an SVG document with an empty SVG element.
	 */
	public SVGDocument() {
		setDocumentURI(null);
		setXmlVersion("1.1");//$NON-NLS-1$
		setXmlStandalone(false);

		root = new SVGSVGElement(this);
	}




	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();

		builder.append("SVG Document:");//$NON-NLS-1$
		builder.append(root.toString());

		return builder.toString();
	}




	@Override
	public SVGSVGElement adoptNode(final Node source) {
		if(!(source instanceof SVGSVGElement))
			throw new DOMException(DOMException.TYPE_MISMATCH_ERR, "SVGSVGElement expected here.");//$NON-NLS-1$

		root = (SVGSVGElement)source;
		root.setOwnerDocument(this);

		return root;
	}



	@Override
	public SVGSVGElement getDocumentElement() {
		return root;
	}


	@Override
	public String getDocumentURI() {
		return documentURI;
	}


	@Override
	public String getXmlEncoding() {
		return xmlEncoding;
	}


	@Override
	public boolean getXmlStandalone() {
		return xmlStandalone;
	}


	@Override
	public String getXmlVersion() {
		return xmlVersion;
	}


	@Override
	public void setDocumentURI(final String documentURI) {
		this.documentURI = documentURI;
	}


	@Override
	public void setXmlStandalone(final boolean xmlStandalone) {
		this.xmlStandalone = xmlStandalone;
	}


	@Override
	public void setXmlVersion(final String xmlVersion) {
		this.xmlVersion = xmlVersion;
	}



	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}


	@Override
	public NodeList getChildNodes() {
		return null;
	}


	@Override
	public SVGSVGElement getFirstChild() {
		return root;
	}


	@Override
	public SVGSVGElement getLastChild() {
		return root;
	}



	@Override
	public Node getNextSibling() {
		return null;
	}


	@Override
	public String getNodeName() {
		return "#document";//$NON-NLS-1$
	}


	@Override
	public short getNodeType() {
		return Node.DOCUMENT_NODE;
	}


	@Override
	public String getNodeValue() {
		return null;
	}


	@Override
	public Document getOwnerDocument() {
		return null;
	}


	@Override
	public Node getParentNode() {
		return null;
	}



	@Override
	public Node getPreviousSibling() {
		return null;
	}



	@Override
	public boolean hasAttributes() {
		return false;
	}


	@Override
	public boolean hasChildNodes() {
		return root!=null;
	}



	@Override
	public boolean isEqualNode(final Node node) {
		boolean equal;

		if(node instanceof SVGDocument) {
			final SVGDocument doc = (SVGDocument)node;
			final boolean encod = xmlEncoding==null ? doc.xmlEncoding==null : xmlEncoding.equals(doc.xmlEncoding);
			final boolean uri   = documentURI==null ? doc.documentURI==null : documentURI.equals(doc.documentURI);

			equal = xmlStandalone==doc.xmlStandalone && encod && root.isEqualNode(doc.root) && uri;
		}
		else equal = false;

		return equal;
	}


	@Override
	public boolean isSameNode(final Node other) {
		return other!=null && other==this;
	}


	@Override
	public DocumentType getDoctype() {
		return null;
	}



	@Override
	public Element createElement(final String tagName) {
		if(tagName==null)
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Invalid tagName.");//$NON-NLS-1$

		final OtherNSElement elt = new OtherNSElement(this);
		elt.setNodeName(tagName);

		return elt;
	}


	@Override
	public Text createTextNode(final String data) {
		if(data==null)
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Invalid data.");//$NON-NLS-1$

		return new SVGText(data, this);
	}


	@Override
	public Comment createComment(final String data) {
		if(data==null)
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Invalid data.");//$NON-NLS-1$

		return new SVGComment(data, this);
	}


	@Override
	public CDATASection createCDATASection(final String data) {
		if(data==null)
			throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "Invalid data.");//$NON-NLS-1$

		return new SVGCDATASection(data, this);
	}


	@Override
	public DOMConfiguration getDomConfig()
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public DOMImplementation getImplementation()
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public String getInputEncoding()
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public boolean getStrictErrorChecking()
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public Node importNode(final Node importedNode, final boolean deep)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public void normalizeDocument()
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public Node renameNode(final Node n, final String namespaceURI, final String qualifiedName)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public void setStrictErrorChecking(final boolean strictErrorChecking)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public Node appendChild(final Node newChild)
	{ return null; }

	@Override
	public Node cloneNode(final boolean deep)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public short compareDocumentPosition(final Node other)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public String getBaseURI()
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public Object getFeature(final String feature, final String version)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public String getLocalName()
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public String getNamespaceURI()
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public String getPrefix()
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public String getTextContent()
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public Object getUserData(final String key)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public boolean isDefaultNamespace(final String namespaceURI)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public Attr createAttribute(final String name)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public Attr createAttributeNS(final String namespaceURI, final String qualifiedName)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public DocumentFragment createDocumentFragment()
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public Element createElementNS(final String namespaceURI, final String qualifiedName)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public EntityReference createEntityReference(final String name)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public ProcessingInstruction createProcessingInstruction(final String target, final String data)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public boolean isSupported(final String feature, final String version)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public String lookupNamespaceURI(final String prefix)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public String lookupPrefix(final String namespaceURI)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public void normalize()
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }

	@Override
	public Object setUserData(final String key, final Object data, final UserDataHandler handler)
	{ throw new DOMException(DOMException.INVALID_ACCESS_ERR, ACTION_NOT_IMPLEMENTED); }



	@Override
	public Node insertBefore(final Node newChild, final Node refChild)
	{ return null; }

	@Override
	public void setPrefix(final String prefix)
	{ /* Nothing to do. */ }

	@Override
	public void setTextContent(final String textContent)
	{ /* Nothing to do. */ }

	@Override
	public Node removeChild(final Node oldChild)
	{ return null; }

	@Override
	public Node replaceChild(final Node newChild, final Node oldChild)
	{ return null; }

	@Override
	public void setNodeValue(final String nodeValue)
	{ /* Nothing to do. */ }

	@Override
	public Element getElementById(final String elementId)
	{ return null; }

	@Override
	public NodeList getElementsByTagName(final String tagname)
	{ return null; }

	@Override
	public NodeList getElementsByTagNameNS(final String namespaceURI, final String localName)
	{ return null; }



	/**
	 * Serialise the given SVG document.
	 * @param path The file of the future serialised document.
	 * @return True: the document has been successfully saved.
	 * @since 2.0
	 */
	public boolean saveSVGDocument(final String path) {
		if(path==null)
			return false;

		boolean ok = true;

		try {
			try(final FileOutputStream fos  = new FileOutputStream(path)){
		        final OutputFormat of = new OutputFormat(this);
		        final XMLSerializer xmls;

		        of.setIndenting(true);
		        xmls = new XMLSerializer(fos, of);

		        try { xmls.serialize(getDocumentElement()); }
		        catch(final IOException ex) { ok = false; }
			}
		}catch(final IOException e) { ok = false; }

        return ok;
	}


	/**
	 * Used to avoid freeze when opening an SVG document.
	 */
	static class SVGEntityResolver implements EntityResolver {
		@Override
		public InputSource resolveEntity(final String publicId, final String systemId) {
			return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes(Charset.defaultCharset()))); //$NON-NLS-1$
		}
	}
}
