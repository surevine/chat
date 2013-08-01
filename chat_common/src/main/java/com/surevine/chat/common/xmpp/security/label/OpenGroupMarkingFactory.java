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
 * This factory class is responsible for creating {@link SecurityLabel} objects.
 */
public class OpenGroupMarkingFactory {

    /**
     * The XML element name for 'name'.
     */
    private static final String NAME = "name";

    /**
     * Creates a new {@link DisplayMarking} from the given XML element.
     *
     * @param xmlElement
     *            The XML element.
     * @return The security label.
     */
    public OpenGroupMarking createOpenGroupMarking(final IXmlElement xmlElement) {
        IXmlElement nameElement = xmlElement.getFirstChild(NAME);
        if (nameElement == null) {
            return null;
        }
        return new OpenGroupMarking(nameElement.getText());
    }

}
