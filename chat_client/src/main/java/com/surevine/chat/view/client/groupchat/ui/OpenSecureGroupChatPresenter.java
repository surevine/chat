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

package com.surevine.chat.view.client.groupchat.ui;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.rooms.client.RoomMessages;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.rooms.SecureRoomFactory;
import com.surevine.chat.view.client.rooms.ui.open.EditSecureRoomDisplay;
import com.surevine.chat.view.client.rooms.ui.open.OpenSecureRoomPresenter;
import com.surevine.chat.view.client.security.ISecurityLabelManager;

/**
 * Presenter for the widget which opens a new group chat
 */
public class OpenSecureGroupChatPresenter extends OpenSecureRoomPresenter {

    /**
     * The hablar page type for this page
     */
    private static final String TYPE = "OpenGroupChat";
    
    /**
     * The group name to initialise the widget with
     */
    private String groupName;

    private XmppRoster roster;
    
    /**
     * Create a new presenter.
     * 
     * @param eventBus the event bus to use.
     * @param display the display to attach to this presenter.
     * @param secureRoomFactory the factory used to create Room instances.
     */
    public OpenSecureGroupChatPresenter(final HablarEventBus eventBus, final XmppRoster roster, final XmppSession session,
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory securityLabelExtensionFactory,
            final EditSecureRoomDisplay display,
            final SecureRoomFactory secureRoomFactory) {
        super(TYPE, eventBus, session, securityLabelManager, securityLabelExtensionFactory, display, secureRoomFactory);
        
        this.roster = roster;
    }

    /**
     * Sets the group name.
     * 
     * @param groupName
     */
    public void setGroupName(final String groupName) {
        this.groupName = groupName;
        display.getRoomName().setValue(groupName);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    protected void onPageOpen() {
        display.getRoomName().setValue(groupName);
        display.setPageTitle(RoomMessages.msg.openNewGroupChat());
        display.setAcceptText(RoomMessages.msg.openNewGroupChatAction());

        setItems(roster.getItemsByGroup(groupName), true);
        roomNameValidator.validate();
    }

}
