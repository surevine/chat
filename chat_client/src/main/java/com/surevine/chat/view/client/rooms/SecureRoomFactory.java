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

import java.util.Collection;

import com.calclab.emite.core.client.events.StateChangedEvent;
import com.calclab.emite.core.client.events.StateChangedHandler;
import com.calclab.emite.core.client.xmpp.stanzas.HasJID;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.chat.ChatProperties;
import com.calclab.emite.im.client.chat.ChatStates;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.calclab.hablar.rooms.client.RoomName;
import com.google.gwt.core.client.GWT;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;

/**
 * A factory class to create Secure Rooms
 * 
 * @author Ash
 */
public class SecureRoomFactory {

    /**
     * The {@link RoomManager} to use to create the rooms
     */
    final RoomManager roomManager;

    /**
     * The uri for the rooms service on the server
     */
    final String roomsService;

    /**
     * Constructs a new factory class
     * 
     * @param roomManager
     *            the RoomManager instance in which to create the room.
     * @param roomsService
     *            the room service string (usually conference.[xmpp server
     *            domain]).
     */
    public SecureRoomFactory(final RoomManager roomManager,
            final String roomsService) {
        this.roomManager = roomManager;
        this.roomsService = roomsService;
    }

    /**
     * Creates a new room with a security label.
     * 
     * @param initiator
     *            the user who is initiating this room creation (usually the
     *            logged in user).
     * @param roomName
     *            the name with which to create the room.
     * @param invitees
     *            the list of users to invite to the room.
     * @param securityLabel
     *            the security label to give the room.
     * @param inviteReason
     *            the textual reason to attach to the invites.
     * @return
     */
    public Room createSecureRoom(final XmppURI initiator,
            final String roomName, final Collection<? extends HasJID> invitees,
            final IXmppSecurityLabelExtension securityLabel,
            final String inviteReason) {

        final String encodedRoomName = SecureRoomName.encode(roomName,
                initiator.getResource(), securityLabel.getLabel());
        final XmppURI roomUri = XmppURI.uri(encodedRoomName, roomsService,
                initiator.getNode());
        final ChatProperties properties = new ChatProperties(roomUri);
        properties.setData(IXmppSecurityLabelExtension.class.getName(),
                securityLabel);

        final Room room = (Room) roomManager.openChat(properties, true);

        room.addChatStateChangedHandler(true, new StateChangedHandler() {
            /**
             * Flag to ensure that we only invite occupants the first time the chat is opened
             * not every time
             */
            private boolean occupantsInvited = false;
            
            @Override
            public void onStateChanged(final StateChangedEvent event) {
                if (!occupantsInvited && event.is(ChatStates.ready)) {
                    for (final HasJID item : invitees) {
                        // We won't invite the creator as they will already be
                        // in the room.
                        if (!item.getJID().equalsNoResource(initiator)) {
                            GWT.log("INVITING: " + item.getJID());
                            room.sendInvitationTo(item.getJID(), inviteReason);
                        }
                        
                        occupantsInvited = true;
                    }
                }
            }
        });

        return room;
    }

    /**
     * Alters the security label for a group chat (by creating an identical room
     * with a different security label)
     * 
     * @param initiator
     *            the user who is initiating this change - usually the currently
     *            logged in user.
     * @param roomManager
     *            the room manager instance to use to create the room.
     * @param room
     *            the room to duplicate.
     * @param securityLabel
     *            the new security label.
     * @return the new room.
     */
    public Room changeRoomSecurityLabel(final XmppURI initiator,
            final RoomManager roomManager, final Room room,
            final IXmppSecurityLabelExtension securityLabel) {
        final XmppURI roomUri = room.getURI();
        final String originalRoomName = RoomName.decode(roomUri.getNode());

        return createSecureRoom(initiator, originalRoomName, room
                .getOccupants(), securityLabel, "Classification changed");
    }
}
