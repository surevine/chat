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
