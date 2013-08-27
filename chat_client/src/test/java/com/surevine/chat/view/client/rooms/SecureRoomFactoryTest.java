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

package com.surevine.chat.view.client.rooms;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.calclab.emite.core.client.events.StateChangedHandler;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.chat.ChatProperties;
import com.calclab.emite.im.client.chat.ChatStates;
import com.calclab.emite.im.client.chat.events.ChatStateChangedEvent;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtension;
import com.surevine.chat.common.xmpp.security.label.ProtectiveMarking;
import com.surevine.chat.common.xmpp.security.label.SecurityLabel;
import com.surevine.chat.common.xmpp.security.label.SupplementalMarking;
import com.surevine.chat.common.xmpp.security.marking.DisplayMarking;
import com.surevine.chat.common.xmpp.security.marking.DisplayMarkingColour;

public class SecureRoomFactoryTest {

    private static final String ROOMS_SERVICE = "conference.example.tld";

    private static final XmppURI JID_1 = XmppURI.jid("user1@example.tld");
    private static final XmppURI JID_2 = XmppURI.jid("user2@example.tld");

    private static final SecurityLabel BASIC_SECURITY_LABEL = new SecurityLabel(
            new ProtectiveMarking("PM"), new ArrayList<SupplementalMarking>());

    @Mock
    private RoomManager roomManager;

    /**
     * Class under test
     */
    private SecureRoomFactory secureRoomFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        secureRoomFactory = new SecureRoomFactory(roomManager, ROOMS_SERVICE);
    }

    @After
    public void tearDown() throws Exception {
        secureRoomFactory = null;
    }

    @Test
    public void testCreateSecureRoomDoesntReinviteOccupants() {
        ArrayList<XmppURI> invitees = new ArrayList<XmppURI>();
        invitees.add(JID_2);

        IXmppSecurityLabelExtension securityLabelExtension = new XmppSecurityLabelExtension(
                BASIC_SECURITY_LABEL, new DisplayMarking(
                        BASIC_SECURITY_LABEL.toString(),
                        new DisplayMarkingColour()));

        Room room = mock(Room.class);
        when(roomManager.openChat(any(ChatProperties.class), anyBoolean()))
                .thenReturn(room);

        secureRoomFactory.createSecureRoom(JID_1, "test", invitees,
                securityLabelExtension, "test");

        ArgumentCaptor<StateChangedHandler> captor = ArgumentCaptor
                .forClass(StateChangedHandler.class);
        verify(room).addChatStateChangedHandler(eq(true), captor.capture());

        StateChangedHandler handler = captor.getValue();

        // The room has been disconnected and reconnected
        handler.onStateChanged(new ChatStateChangedEvent(ChatStates.locked));
        handler.onStateChanged(new ChatStateChangedEvent(ChatStates.ready));
        handler.onStateChanged(new ChatStateChangedEvent(ChatStates.locked));
        handler.onStateChanged(new ChatStateChangedEvent(ChatStates.ready));

        verify(room, times(1)).sendInvitationTo(JID_2, "test");
    }
}
