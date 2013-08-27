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

package com.surevine.chat.common.xmpp.security;

import java.util.Collections;
import java.util.List;

import com.surevine.chat.common.xmpp.security.label.SecurityLabel;
import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * This factory class is responsible for creating {@link SecurityLabel} objects.
 */
public class XEPSecurityLabelFactory {

    /**
     * The list of security label readers which will be used to attempt to read the security label
     * from XML.
     */
    private List<ISecurityLabelXmlReader> securityLabelReaders;

    /**
     * Constructor which takes a list of security label readers.
     *
     * @param securityLabelReaders
     *            The list of security label readers.
     */
    public XEPSecurityLabelFactory(final List<ISecurityLabelXmlReader> securityLabelReaders) {
        if (securityLabelReaders != null && !securityLabelReaders.isEmpty()) {
            this.securityLabelReaders = securityLabelReaders;
        } else {
            this.securityLabelReaders = Collections.emptyList();
        }
    }

    /**
     * Creates a new {@link ISecurityLabel} from the given XML element.
     *
     * @param xmlElement
     *            The XML element.
     * @return The security label if the reader is compatible, otherwise <code>null</code>.
     */
    public ISecurityLabel createSecurityLabel(final IXmlElement xmlElement) {
        for (ISecurityLabelXmlReader reader : securityLabelReaders) {
            if (reader.canReadSecurityLabel(xmlElement)) {
                return reader.createSecurityLabel(xmlElement);
            }
        }
        return null;
    }
}
