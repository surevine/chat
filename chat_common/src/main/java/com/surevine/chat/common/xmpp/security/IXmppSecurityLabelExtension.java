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

import java.util.List;

import com.surevine.chat.common.xmpp.IXmppPacketExtension;

/**
 * Interface for classes which extend the XMPP specification to provide XEP-0258 security labels.
 */
public interface IXmppSecurityLabelExtension extends IXmppPacketExtension {

    /**
     * The XEP-0258 security label element name.
     */
    String XEP_0258_XML_ELEMENT = "securitylabel";

    /**
     * The XEP-0258 XML namespace.
     */
    String XEP_0258_XML_NAMESPACE = "urn:xmpp:sec-label:0";

    /**
     * The XEP-0258 display marking.
     */
    String XEP_0258_DISPLAY_MARKING = "displaymarking";

    /**
     * The XEP-0258 label.
     */
    String XEP_0258_LABEL = "label";

    /**
     * The XEP-0258 equivalent label.
     */
    String XEP_0258_EQUIVALENT_LABEL = "equivalentlabel";

    /**
     * Gets the basic display marking, which is used if none of the security labels are recognised. Must not return null.
     *
     * @return The display marking.
     */
    IDisplayMarking getDisplayMarking();

    /**
     * Gets the primary security label. Must not return null.
     *
     * @return The primary security label.
     */
    ISecurityLabel getLabel();

    /**
     * Gets a list of the security labels which are equivalent to the primary security label. Must not return null.
     *
     * @return The equivalent labels
     */
    List<? extends ISecurityLabel> getEquivalentLabels();

    /**
     * Determies whether this extension contains the security label (either as the primary or an
     * equivalent label.
     *
     * @param securityLabel
     *            The security label.
     * @return <code>true</code> if this extension contains the security label, otherwise
     *         <code>false</code>.
     */
    boolean contains(ISecurityLabel securityLabel);

}
