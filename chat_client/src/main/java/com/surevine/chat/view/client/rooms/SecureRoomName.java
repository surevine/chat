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

import com.calclab.hablar.rooms.client.RoomName;
import com.surevine.chat.common.xmpp.security.ISecurityLabel;

/**
 * Utility class to manipulate room names in order to support multiple security
 * markings.
 */
public class SecureRoomName {

    /**
     * The prefix which will be used to separate the security label from the
     * rest of the room name
     */
    private static final String SECURITY_LABEL_PREFIX = ".securityLabel-";

    /**
     * Extracts and returns everything except the security label from the
     * encoded room name.
     * 
     * @param roomName
     *            the encoded room name.
     * @return the room name without the security label.
     */
    public static String decodeWithoutSecurityLabel(final String roomName) {
        final int prefixIndex = roomName.lastIndexOf(SECURITY_LABEL_PREFIX);

        if (prefixIndex > -1) {
            return roomName.substring(0, prefixIndex).replace('_', ' ');
        } else {
            return roomName;
        }
    }

    /**
     * Encodes a room name to include the security label so that the name is
     * unique with different security labels.
     * 
     * @param name
     *            the room base name.
     * @param id
     *            a user-unique id to add to the room name.
     * @param securityLabel
     *            the security label which will added to the room name.
     * @return the encoded room name.
     */
    public static String encode(final String name, final String id,
            final ISecurityLabel securityLabel) {
        return RoomName.encode(name, id + SECURITY_LABEL_PREFIX
                + securityLabel.getLabel().replaceAll("[^A-Za-z0-9]", "_"));
    }
}
