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
package com.surevine.chat.common.xmpp.security;

import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * This interface defines the contract for classes that can convert an xml element of an
 * {@link ISecurityLabel} into an object.
 */
public interface ISecurityLabelXmlReader {

    /**
     * Creates a {@link ISecurityLabel} object from an {@link IXmlElement}.
     *
     * @param xmlElement
     *            The XML element to convert.
     * @return The new <code>ISecurityLabel</code>.
     */
    ISecurityLabel createSecurityLabel(IXmlElement xmlElement);

    /**
     * Determines whether this reader is capable of reading the security label within an
     * {@linkIXmlElement}.
     *
     * @param xmlElement
     *            The XML element to check.
     * @return <code>true</code> if this reader can read the given security label, otherwise
     *         <code>false</code>.
     */
    boolean canReadSecurityLabel(IXmlElement xmlElement);

}
