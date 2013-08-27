/*
 * Chat Client
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

package com.surevine.chat.view.client.xmpp.security;

import java.util.List;

import com.calclab.emite.core.client.packet.IPacket;
import com.calclab.emite.core.client.packet.NoPacket;
import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.google.inject.Inject;
import com.surevine.chat.common.xmpp.security.ISecurityLabel;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.common.xmpp.xml.IXmlElement;
import com.surevine.chat.view.client.xmpp.xml.EmiteXmlElement;

/**
 * The Security Label Service.
 */
public class SecurityLabelService implements ISecurityLabelService {

    /**
     * The emite session.
     */
    protected XmppSession session;

    /**
     * The factory to use to create security label extension objects.
     */
    protected XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory;

    /**
     * Construct a service object with the specified emite session.
     * 
     * @param session
     *            The emite session for the current user.
     * @param xmppSecurityLabelExtensionFactory
     *            The <code>XmppSecurityLabelExtensionFactory</code>.
     */
    @Inject
    public SecurityLabelService(
            final XmppSession session,
            final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory) {
        this.session = session;
        this.xmppSecurityLabelExtensionFactory = xmppSecurityLabelExtensionFactory;
    }

    /**
     * @see com.surevine.chat.view.xmpp.security.client.ISecurityLabelService#extractSecurityLabel
     *      (com.calclab.emite.core.client.packet.IPacket)
     * 
     * @param packet
     *            The <code>IPacket</code>.
     * 
     * @return The <code>IXmppSecurityLabelExtension</code>.
     */
    public IXmppSecurityLabelExtension extractSecurityLabelExtension(
            final IPacket packet) {
        final IPacket extensionPacket = packet.getFirstChild("securitylabel");

        if (extensionPacket == NoPacket.INSTANCE) {
            return null;
        }

        final IXmlElement xmlElement = new EmiteXmlElement(extensionPacket);

        final IXmppSecurityLabelExtension extension = xmppSecurityLabelExtensionFactory
                .createXmppSecurityLabelExtension(xmlElement);

        return extension;
    }

    /**
     * @see com.surevine.chat.view.xmpp.security.client.ISecurityLabelService#getSecurityLabelList()
     * 
     * @return The <code>List</code> of security labels.
     */
    public List<ISecurityLabel> getSecurityLabelList() {
        return null;
    }

}
