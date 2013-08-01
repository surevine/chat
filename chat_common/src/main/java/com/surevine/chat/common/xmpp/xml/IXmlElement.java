/*
 * Chat Common
 * Copyright (C) 2010 Surevine Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see {http://www.gnu.org/licenses/}.
 */
package com.surevine.chat.common.xmpp.xml;

import java.util.List;
import java.util.Map;

/**
 * This interface defines the contract for XML generation.
 */
public interface IXmlElement {

    /**
     * Adds a child element to the end of the child list.
     *
     * @param elementName
     *            The name of the element to add.
     *
     * @return The new child element.
     */
    IXmlElement addChild(String elementName);

    /**
     * Adds a child element to the end of the child list.
     *
     * @param elementName
     *            The name of the element to add.
     * @param namespace
     *            The XML namespace.
     *
     * @return The new child element.
     */
    IXmlElement addChild(String elementName, String namespace);

    /**
     * Retrieves an element attribute value by name.
     *
     * @param name
     *            The attribute name.
     *
     * @return The attribute value, or <code>null</code> if the attribute doesn't exist.
     */
    String getAttribute(String name);

    /**
     * Retrieve a map of all the element attributes.
     *
     * @return The attribute map.
     */
    Map<String, String> getAttributes();

    /**
     * Retrieve the list of children.
     *
     * @return The list of children.
     */
    List<? extends IXmlElement> getChildren();

    /**
     * Retrieve a list of children which have a certain element name.
     *
     * @param elementName
     *            The name of the element.
     * @return The list of children.
     */
    List<? extends IXmlElement> getChildren(String elementName);

    /**
     * Retrieves the first child with a certain element name.
     *
     * @param elementName
     *            The name of the element.
     * @return The first child with that element name, or <code>null</code> if not found.
     */
    IXmlElement getFirstChild(String elementName);

    /**
     * Retrieve the element text contents.
     *
     * @return The element text.
     */
    String getText();

    /**
     * Sets an element attribute.
     *
     * @param name
     *            The attribute name.
     * @param value
     *            The attribute value.
     *
     * @return The XML element (for the fluent interface).
     */
    IXmlElement setAttribute(String name, String value);

    /**
     * Sets the text for this element.
     *
     * @param text
     *            The text to add to this element.
     *
     * @return The XML element (for the fluent interface).
     */
    IXmlElement setText(String text);

    /**
     * Gets the element name.
     *
     * @return The element name.
     */
    String getName();

}
