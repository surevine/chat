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
package com.surevine.chat.view.client.rooms.ui;

import com.calclab.hablar.chat.client.ui.ChatWidget;
import com.calclab.hablar.rooms.client.occupant.OccupantsDisplay;
import com.calclab.hablar.rooms.client.occupant.OccupantsWidget;
import com.surevine.chat.view.client.chat.ui.SecureChatWidget;

/**
 * The view of the secure room, built on the secure chat widget.
 */
public class SecureRoomWidget extends SecureChatWidget implements
        ISecureRoomDisplay {

    /**
     * Constructor.
     * 
     * @param chatWidget
     *            The <code>ChatWidget</code>.
     */
    public SecureRoomWidget(final ChatWidget chatWidget) {
        super(chatWidget);
    }

    /**
     * {@inheritDoc}.
     */
    public OccupantsDisplay createOccupantsDisplay(final String roomId) {
        final OccupantsWidget occupantsWidget = new OccupantsWidget(roomId);
        chatWidget.addToActions(occupantsWidget);
        return occupantsWidget;
    }
}
