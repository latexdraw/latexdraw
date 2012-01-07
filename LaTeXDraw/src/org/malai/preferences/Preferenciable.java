package org.malai.preferences;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The interface can be applied on classes which can preferences can be saved into an XML document.<br>
 * <br>
 * This file is part of libMalai.<br>
 * Copyright (c) 2009-2012 Arnaud BLOUIN<br>
 * <br>
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.<br>
 * <br>
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * @author Arnaud BLOUIN
 * @date 01/10/11
 * @since 0.2
 * @version 0.2
 */
public interface Preferenciable {
	/**
	 * Saves the parameters of the instrument into an XML tag.
	 * @param generalPreferences True: this operation is called to save the general preferences of the interactive system.
	 * Otherwise, it is called to save a presentation in a document. This parameter is useful when different information
	 * must be saved during a presentation backup or a general preferences backup.
	 * @param nsURI The namespace that must be added to tags corresponding to the instrument's parameters.
	 * @param document The XML document.
	 * @param root The root element that will contains the instrument's parameters.
	 * @since 0.2
	 */
	void save(final boolean generalPreferences, final String nsURI, final Document document, final Element root);

	/**
	 * Loads data save in an XML document.
	 * @param generalPreferences True: this operation is called to load the general preferences of the interactive system.
	 * Otherwise, it is called to load a presentation in a document. This parameter is useful when different information
	 * must be loaded during a presentation backup or a general preferences backup.
	 * @param meta The meta data element.
	 * @param nsURI The namespace that must be added to tags corresponding to the instrument's parameters.
	 * @since 0.2
	 */
	void load(final boolean generalPreferences, final String nsURI, final Element meta);
}
