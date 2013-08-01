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
package com.surevine.chat.view.client.chat;

import com.calclab.emite.core.client.events.MessageEvent;
import com.calclab.emite.core.client.events.MessageHandler;
import com.calclab.emite.im.client.chat.Chat;
import com.calclab.emite.im.client.chat.ChatProperties;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.emite.xep.muc.client.events.BeforeRoomInvitationSendEvent;
import com.calclab.emite.xep.muc.client.events.BeforeRoomInvitationSendHandler;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.view.client.xmpp.security.XmppPacketUtil;

/**
 * Utility class anti-pattern of stuff which needs a better place to live.
 */
public final class SecureChatUtility {

    /**
     * Prevent instantiation
     */
    private SecureChatUtility() {
    }

    /**
     * The key used to store the message send handler within the chat properties
     */
    public static final String CHAT_PROPERTY_MESSAGE_SEND_HANDLER = "ChatClient.SecurityLabelBeforeSendMessageHandler";

    /**
     * Extracts a security label from a {@link Chat} instance.
     * 
     * @param chat
     *            the Chat object.
     * @return the security label, or null if one wasn't found.
     */
    public static IXmppSecurityLabelExtension extractSecurityLabelFromChat(
            final Chat chat) {
        final ChatProperties properties = chat.getProperties();

        final Object securityLabel = properties
                .getData(IXmppSecurityLabelExtension.class.getName());

        if (securityLabel instanceof IXmppSecurityLabelExtension) {
            return (IXmppSecurityLabelExtension) securityLabel;
        }

        return null;
    }

    /**
     * Method to add the handlers to a chat instance to add the security label
     * to outgoing messages.
     * 
     * @param chat
     *            the chat to which to add the handler(s)
     */
    public static void addOutgoingSecurityLabelHandlerToChat(final Chat chat) {
        final MessageHandler messageHandler = new MessageHandler() {

            @Override
            public void onMessage(final MessageEvent event) {
                final IXmppSecurityLabelExtension securityLabel = extractSecurityLabelFromChat(chat);

                if (securityLabel != null) {
                    XmppPacketUtil.extendPacket(event.getMessage(),
                            securityLabel);
                }
            }
        };

        chat.addBeforeSendMessageHandler(messageHandler);
        
        chat.getProperties().setData(CHAT_PROPERTY_MESSAGE_SEND_HANDLER, messageHandler);

        /*
         * If the chat is a Room chat then we need to add a handler to add the
         * security label to the outgoing invitations
         */
        if (chat instanceof Room) {
            final BeforeRoomInvitationSendHandler invitationSendHandler = new BeforeRoomInvitationSendHandler() {

                @Override
                public void onBeforeInvitationSend(
                        final BeforeRoomInvitationSendEvent event) {
                    final IXmppSecurityLabelExtension securityLabel = extractSecurityLabelFromChat(chat);

                    if (securityLabel != null) {
                        XmppPacketUtil.extendPacket(event.getMessage(),
                                securityLabel);
                    }
                }
            };

            final Room room = (Room) chat;

            room.addBeforeRoomInvitationSendHandler(invitationSendHandler);
        }
    }
}
