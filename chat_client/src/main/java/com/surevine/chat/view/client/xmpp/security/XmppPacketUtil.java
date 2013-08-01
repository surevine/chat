/*
 * Chat Client
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
package com.surevine.chat.view.client.xmpp.security;

import com.calclab.emite.core.client.packet.IPacket;
import com.surevine.chat.common.xmpp.IXmppPacketExtension;
import com.surevine.chat.view.client.xmpp.xml.EmiteXmlElement;

/**
 * Utilities for manipulating XMPP packets.
 */
public final class XmppPacketUtil {

    /**
     * Private constructor.
     */
    private XmppPacketUtil() {
    }

    /**
     * Adds the XmppPacketExtension to the IPacket.
     * 
     * @param packet
     *            The <code>IPacket</code>.
     * @param packetExtension
     *            The <code>IXmppPacketExtension</code>.
     */
    public static void extendPacket(final IPacket packet,
            final IXmppPacketExtension packetExtension) {
        final EmiteXmlElement element = new EmiteXmlElement(packet);
        packetExtension.addToXmlElement(element);
    }

}
