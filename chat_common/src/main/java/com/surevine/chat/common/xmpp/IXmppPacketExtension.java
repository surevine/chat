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
package com.surevine.chat.common.xmpp;

import com.surevine.chat.common.xmpp.xml.IXmlable;

/**
 * This interface is for all classes which extend the XMPP packet structure.
 */
public interface IXmppPacketExtension extends IXmlable {

    /**
     * Gets the element name for the xml extension.
     *
     * @return The XML element name.
     */
    String getXmlNodeName();

    /**
     * Gets the xml namespace for the packet extension.
     *
     * @return The XML namespace.
     */
    String getXmlNamespace();

}
