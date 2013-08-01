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
package com.surevine.chat.view.client.rooms.ui.open;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.rooms.client.RoomMessages;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.rooms.SecureRoomFactory;
import com.surevine.chat.view.client.security.ISecurityLabelManager;

/**
 * Presenter for the widget used to open a new group chat/room with a security
 * label.
 */
public class OpenNewSecureRoomPresenter extends OpenSecureRoomPresenter {

    /**
     * The hablar page type
     */
    private static final String TYPE = "OpenNewRoom";

    /**
     * A counter used to append a suffix to the default room name. This is
     * incremented each time the a room is created.
     */
    private int roomNumber = 1;

    /**
     * The XmppRoster
     */
    private XmppRoster roster;

    /**
     * Create the presenter attached to the given display.
     * 
     * @param eventBus
     *            the event bus to use.
     * @param display
     *            the display to attach this presenter to.
     * @param roomFactory
     *            the factory to sue to create {@link Room} instances.
     */
    public OpenNewSecureRoomPresenter(final HablarEventBus eventBus,
            final XmppRoster roster, final XmppSession session,
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory securityLabelExtensionFactory,
            final EditSecureRoomDisplay display,
            final SecureRoomFactory roomFactory) {
        super(TYPE, eventBus, session, securityLabelManager, securityLabelExtensionFactory, display, roomFactory);
        this.roster = roster;
        display.setPageTitle(RoomMessages.msg.openNewGroupChat());
        display.setAcceptText(RoomMessages.msg.openNewGroupChatAction());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    protected void onAccept() {
        super.onAccept();
        roomNumber++;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    protected void onPageOpen() {
        final String roomName = RoomMessages.msg.groupChatId(roomNumber);
        display.getRoomName().setValue(roomName);
        setItems(roster.getItems(), false);
        roomNameValidator.validate();
    }

}