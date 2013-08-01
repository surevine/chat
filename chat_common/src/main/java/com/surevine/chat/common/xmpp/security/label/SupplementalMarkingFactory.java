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

import com.google.inject.Inject;
import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * This class is responsible for creating {@link SecurityLabel} objects.
 */
public class SupplementalMarkingFactory {

    /**
     * XML attribute for 'type'.
     */
    private static final String TYPE = "type";

    /**
     * The open group marking factory.
     */
    private OpenGroupMarkingFactory openGroupMarkingFactory;

    /**
     * Constructs a <code>SupplementalMarkingFactory</code> with an {@link OpenGroupMarkingFactory}.
     *
     * @param openGroupMarkingFactory
     *            The factory with which to create Open Group Marking objects.
     */
    @Inject
    public SupplementalMarkingFactory(final OpenGroupMarkingFactory openGroupMarkingFactory) {
        this.openGroupMarkingFactory = openGroupMarkingFactory;
    }

    /**
     * Creates a new {@link DisplayMarking} from the given XML element.
     *
     * @param xmlElement
     *            The XML element.
     * @return The security label.
     */
    public SupplementalMarking createSupplementalMarking(final IXmlElement xmlElement) {
        String type = xmlElement.getAttribute(TYPE);
        if (type.equals(SupplementalMarkingType.OPEN_GROUP.toString())) {
            return openGroupMarkingFactory.createOpenGroupMarking(xmlElement);
        }
        return null;
    }
}
