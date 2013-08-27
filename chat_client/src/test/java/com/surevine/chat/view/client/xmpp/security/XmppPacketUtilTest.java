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

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.calclab.emite.core.client.packet.IPacket;
import com.surevine.chat.common.xmpp.IXmppPacketExtension;
import com.surevine.chat.view.client.xmpp.xml.EmiteXmlElement;

/**
 * Test case for the {@link XmppPacketUtil} class.
 */
public class XmppPacketUtilTest {

    /**
     * Test method for
     * {@link com.surevine.chat.view.xmpp.security.client.XmppPacketUtil#extendPacket
     * (com.calclab.emite.core.client.packet.IPacket, com.surevine.chat.common.xmpp.IXmppPacketExtension)}.
     */
    @Test
    public void testExtendPacket() {
        // This is the outer (main) packet
        IPacket packet = Mockito.mock(IPacket.class);

        // This is the extension object itself
        IXmppPacketExtension packetExtension = Mockito.mock(IXmppPacketExtension.class);

        // Do the action
        XmppPacketUtil.extendPacket(packet, packetExtension);

        // Ensure that an appropriate ExmiteXmlElement is passed to addToXmlElement
        ArgumentCaptor<EmiteXmlElement> argument = ArgumentCaptor.forClass(EmiteXmlElement.class);
        verify(packetExtension).addToXmlElement(argument.capture());
        assertSame("Xml Element passed into addToXmlElement not correct", packet, argument
                .getValue().getPacket());
    }

}
