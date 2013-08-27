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

import com.google.inject.Inject;
import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * Factory class to create {@link XmppSecurityLabelExtension} objects.
 */
public class XmppSecurityLabelExtensionFactory {

    /**
     * Display marking factory.
     */
    private XEPDisplayMarkingFactory displayMarkingFactory;

    /**
     * Security label factory.
     */
    private XEPSecurityLabelFactory securityLabelFactory;

    /**
     * Constructs a new <code>XmppSecurityLabelExtensionFactory</code> with a display marking
     * factory and a security label factory.
     *
     * @param displayMarkingFactory
     *            The display marking factory.
     * @param securityLabelFactory
     *            The security label factory.
     */
    @Inject
    public XmppSecurityLabelExtensionFactory(final XEPDisplayMarkingFactory displayMarkingFactory,
            final XEPSecurityLabelFactory securityLabelFactory) {
        this.displayMarkingFactory = displayMarkingFactory;
        this.securityLabelFactory = securityLabelFactory;
    }

    /**
     * Creates a new {@link XmppSecurityLabelExtension} containing the given security label.
     *
     * @param securityLabel
     *            The security label.
     * @param renderer
     *            The display marking renderer.
     * @return The XMPP security label extension for the security label.
     */
    public IXmppSecurityLabelExtension createXMPPSecurityLabelExtension(
            final ISecurityLabel securityLabel, final IDisplayMarkingRenderer renderer) {
        IDisplayMarking displayMarking = displayMarkingFactory.createDisplayMarking(securityLabel,
                renderer);
        return new XmppSecurityLabelExtension(securityLabel, displayMarking);
    }

    /**
     * Creates a new {@link XmppSecurityLabelExtension} from the given XML element.
     *
     * @param xmlElement
     *            The XML element.
     * @return The XMPP security label extension for the XML element.
     */
    public IXmppSecurityLabelExtension createXmppSecurityLabelExtension(final IXmlElement xmlElement) {

        // Set the display marking
        IDisplayMarking displayMarking = null;
        IXmlElement displayMarkingElement = xmlElement
                .getFirstChild(IXmppSecurityLabelExtension.XEP_0258_DISPLAY_MARKING);
        if (displayMarkingElement != null) {
            displayMarking = displayMarkingFactory.createDisplayMarking(displayMarkingElement);
        }

        // Set the primary security label
        ISecurityLabel securityLabel = null;
        IXmlElement securityLabelElement = xmlElement
                .getFirstChild(IXmppSecurityLabelExtension.XEP_0258_LABEL);
        if (securityLabelElement != null) {
            securityLabel = securityLabelFactory.createSecurityLabel(securityLabelElement);
        }

        return new XmppSecurityLabelExtension(securityLabel, displayMarking);
    }

}
