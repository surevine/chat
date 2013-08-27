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

package com.surevine.chat.view.client.rooms.ui.classification;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.chat.Chat;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatPresenter;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatWidget;
import com.surevine.chat.view.client.rooms.SecureRoomFactory;
import com.surevine.chat.view.client.security.ISecurityLabelManager;

public class ChangeRoomClassificationPresenter extends OpenSecureChatPresenter {

    private final SecureRoomFactory secureRoomFactory;
    
    private final XmppSession session;
    
    private final RoomManager roomManager;

    public ChangeRoomClassificationPresenter(final HablarEventBus eventBus,
            final XmppSession session, final RoomManager roomManager,
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory,            final OpenSecureChatWidget openSecureChatWidget, final Room room,
            final SecureRoomFactory secureRoomFactory) {

        super(eventBus, roomManager, securityLabelManager, xmppSecurityLabelExtensionFactory, openSecureChatWidget, room);
        this.session = session;
        this.secureRoomFactory = secureRoomFactory;
        this.roomManager = roomManager;
    }

    @Override
    protected void openChat(final XmppURI toUri, final Chat relatedChat) {
        assert relatedChat instanceof Room : relatedChat.getClass().toString();

        final Room relatedRoom = (Room) relatedChat;

        secureRoomFactory.changeRoomSecurityLabel(session.getCurrentUserURI(),
                roomManager, relatedRoom, securityLabelChooserPresenter
                        .getSecurityLabel());
        
        requestVisibility(Visibility.hidden);
    }
}
