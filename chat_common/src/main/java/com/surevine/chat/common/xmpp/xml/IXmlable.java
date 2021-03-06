/*
 * Copyright (C) 2010 Surevine Limited
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
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

/**
 * This interface should be implemented by classes which can be added into an XML structure using
 * the {@link IXmlElement} interface.
 */
public interface IXmlable {

    /**
     * Injects the XML object into a parent XML element.
     *
     * @param parentElement
     *            The parent XML element.
     */
    void addToXmlElement(IXmlElement parentElement);

}
