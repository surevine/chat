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

import com.surevine.chat.common.xmpp.xml.IXmlable;

/**
 * This interface defines the Security Label portion of the XEP-0258 security labels specification.
 * Classes representing Security Labels as defined by the XEP-0258 specification should implemement
 * this interface.
 */
public interface ISecurityLabel extends IXmlable {

    /**
     * The XML element namespace.
     */
    String SECURITY_LABEL_NAMESPACE = "http://www.surevine.com/chat/common/xmpp/security/label/securitylabel.xsd";
    
    /**
     * The XML element name.
     */
    String SECURITY_LABEL = "securityLabel";
    
    /**
     * The XML element name.
     */
    String PREFIX = "prefix";

    /**
     * XML protective marking attribute name.
     */
    String PROTECTIVE_MARKING = "protectiveMarking";

    /**
     * XML caveat attribute name.
     */
    String CAVEAT = "caveat";

    /**
     * XML supplemental marking attribute name.
     */
    String SUPPLEMENTAL_MARKING = "supplementalMarking";

    /**
     * Gets a textual representation of this security label.
     *
     * @return The textual representation.
     */
    String getLabel();

    /**
     * Gets the string representation of a protective marking.
     *
     * @return The protective marking.
     */
//    String getProtectiveMarking();

}
