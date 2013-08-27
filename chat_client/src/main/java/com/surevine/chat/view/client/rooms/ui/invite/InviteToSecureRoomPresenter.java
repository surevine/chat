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

package com.surevine.chat.view.client.rooms.ui.invite;

import java.util.HashSet;
import java.util.Set;

import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.xep.muc.client.Occupant;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.hablar.client.HablarMessages;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.rooms.client.RoomMessages;
import com.calclab.hablar.rooms.client.RoomName;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.rooms.ui.open.EditSecureRoomDisplay;
import com.surevine.chat.view.client.rooms.ui.open.EditSecureRoomPresenter;
import com.surevine.chat.view.client.security.ISecurityLabelManager;

/**
 * Presenter for the widget used to invite extra participants to a group
 * chat/room.
 */
public class InviteToSecureRoomPresenter extends EditSecureRoomPresenter {

    /**
     * The hablar page type.
     */
    public static final String TYPE = "InviteToRoom";

    /**
     * The room to which to invite people.
     */
    private Room room;

    /**
     * The Roster
     */
    final private XmppRoster roster;

    /**
     * Create the presenter attaching to a given display.
     * 
     * @param eventBus
     *            the hablar event bus to use.
     * @param display
     *            the display to which to attach this presenter.
     */
    public InviteToSecureRoomPresenter(
            final HablarEventBus eventBus,
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory securityLabelExtensionFactory,
            final XmppRoster roster, final EditSecureRoomDisplay display) {
        super(TYPE, eventBus, securityLabelManager, securityLabelExtensionFactory, display);

        this.roster = roster;
    }

    /**
     * Sets the room into which people will be invited.
     * 
     * @param room
     *            the room.
     */
    public void setRoom(final Room room) {
        this.room = room;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    protected void onAccept() {
        final String reasonText = display.getMessage().getText();
        for (final RosterItem selectItem : getItems()) {
            room.sendInvitationTo(selectItem.getJID(), reasonText);
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    protected void onBeforeFocus() {
        onPageOpen();
        // hide the security label chooser for this invite to group page.
        display.setSecurityLabelChooserVisible(false);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    protected void onPageOpen() {
        display.setPageTitle(RoomMessages.msg.invitePeopleToGroupChat());
        display.setAcceptText(HablarMessages.msg.acceptAction());

        final String roomName = RoomName.decode(room.getURI().getNode());
        display.getRoomName().setValue(roomName);
        display.setRoomNameEnabled(false);
        final Set<String> occupantUris = new HashSet<String>();
        for (final Occupant occupant : room.getOccupants()) {
            occupantUris.add(occupant.getOccupantUri().getResource());
        }
        for (final RosterItem item : roster.getItems()) {
            if (!occupantUris.contains(item.getJID().getNode())) {
                createItem(item, false);
            }
        }
    }
}
