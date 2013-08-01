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
package com.surevine.chat.common.xmpp.security.label;

import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * This class is responsible for creating {@link Prefix} objects.
 */
public class PrefixFactory {

    /**
     * The XML element name for 'name'.
     */
    private static final String NAME = "name";

    /**
     * Creates a new {@link Prefix} from the given XML element. The XML element should not be
     * <code>null</code>.
     *
     * @param xmlElement
     *            The XML element.
     * @return Returns the <code>Prefix</code> if it exists, otherwise <code>null</code>.
     */
    public Prefix createPrefix(final IXmlElement xmlElement) {
        IXmlElement nameElement = xmlElement.getFirstChild(NAME);
        if (nameElement == null) {
            return null;
        }
        return new Prefix(nameElement.getText());
    }

}
