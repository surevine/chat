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

import com.calclab.emite.core.client.packet.NoPacket;
import com.calclab.emite.core.client.xmpp.stanzas.BasicStanza;
import com.calclab.emite.im.client.chat.ChatProperties;
import com.calclab.emite.im.client.chat.ChatSelectionStrategy;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.view.client.xmpp.security.ISecurityLabelService;

public class SecureChatSelectionStrategy implements ChatSelectionStrategy {

    public static final String MESSAGE_TYPE_CHAT = "chat";
    public static final String MESSAGE_TYPE_GROUPCHAT = "groupchat";

    /**
     * The security label service to use to extract the labels
     */
    private final ISecurityLabelService securityLabelService;

    /**
     * Whether to filter out
     */
    private final String messageType;

    public SecureChatSelectionStrategy(
            final ISecurityLabelService securityLabelService,
            final String messageType) {
        this.securityLabelService = securityLabelService;
        this.messageType = messageType;
    }

    @Override
    public ChatProperties extractProperties(final BasicStanza stanza) {
        final ChatProperties properties = new ChatProperties(stanza.getFrom());

        final boolean messageHasBody = stanza.getFirstChild("body") != NoPacket.INSTANCE;

        final String stanzaType = stanza.getAttribute("type");

        // Check the message is a supported type
        final boolean isSupportedMessage = (stanzaType != null)
                && stanzaType.equals(messageType);

        properties.setShouldCreateNewChat(messageHasBody && isSupportedMessage);

        final IXmppSecurityLabelExtension securityLabel = securityLabelService
                .extractSecurityLabelExtension(stanza);

        if (securityLabel != null) {
            properties.setData(IXmppSecurityLabelExtension.class.getName(),
                    securityLabel);
        }

        return properties;
    }

    @Override
    public boolean isAssignable(final ChatProperties chatProperties,
            final ChatProperties messageProperties) {

        // If the uri is wrong, fail straight away
        if (!chatProperties.getUri().equalsNoResource(
                messageProperties.getUri())) {
            return false;
        }

        final Object messageSecurityLabel = messageProperties
                .getData(IXmppSecurityLabelExtension.class.getName());

        assert (messageSecurityLabel == null)
                || (messageSecurityLabel instanceof IXmppSecurityLabelExtension);

        // If the message security label is null then it gets delivered to all the open chats
        if(messageSecurityLabel == null) {
            return true;
        }
        
        final Object chatSecurityLabel = chatProperties
                .getData(IXmppSecurityLabelExtension.class.getName());

        assert (chatSecurityLabel == null)
                || (chatSecurityLabel instanceof IXmppSecurityLabelExtension);

        // If they're both null then return true, otherwise if chatSecurityLabel
        // is null then return false.
        if ((chatSecurityLabel == null)) {
            return (messageSecurityLabel == null);
        }

        // Otheriwse return true if both the security labels are equal.
        return chatSecurityLabel.equals(messageSecurityLabel);
    }

}
