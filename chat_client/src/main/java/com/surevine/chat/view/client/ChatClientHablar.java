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

package com.surevine.chat.view.client;

import com.calclab.emite.core.client.events.MessageEvent;
import com.calclab.emite.core.client.events.MessageHandler;
import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.im.client.chat.Chat;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.chat.events.ChatChangedEvent;
import com.calclab.emite.im.client.chat.events.ChatChangedHandler;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.calclab.hablar.client.HablarConfig;
import com.calclab.hablar.clipboard.client.HablarClipboard;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.HablarCore;
import com.calclab.hablar.dock.client.HablarDock;
import com.calclab.hablar.editbuddy.client.HablarEditBuddy;
import com.calclab.hablar.group.client.HablarGroup;
import com.calclab.hablar.icons.client.AvatarProviderRegistry;
import com.calclab.hablar.roster.client.HablarRoster;
import com.calclab.hablar.roster.client.page.RosterPage;
import com.calclab.hablar.search.client.HablarSearch;
import com.calclab.hablar.signals.client.HablarSignals;
import com.calclab.hablar.signals.client.sound.HablarSoundSignals;
import com.calclab.hablar.user.client.HablarUser;
import com.calclab.hablar.usergroups.client.HablarUserGroups;
import com.calclab.hablar.vcard.client.HablarVCard;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.chat.HablarSecureChat;
import com.surevine.chat.view.client.groupchat.HablarSecureGroupChat;
import com.surevine.chat.view.client.rooms.HablarSecureRooms;
import com.surevine.chat.view.client.security.ISecurityLabelManager;
import com.surevine.chat.view.client.xmpp.security.ISecurityLabelService;
import com.surevine.chat.view.client.xmpp.security.XmppPacketUtil;

/**
 * Static class to configure the hablar instance to our liking.
 */
public class ChatClientHablar {
    /**
     * Installs the functionality for our security-labelled version of hablar.
     * 
     * @param hablar
     *            the instance into which to install the functionality.
     * @param config
     *            the configuration
     */
    public static void install(final Hablar hablar, final HablarConfig config,
            final ChatClientGinjector ginjector) {
        final XmppSession session = ginjector.getXmppSession();
        final XmppRoster roster = ginjector.getXmppRoster();
        final ChatManager chatManager = ginjector.getChatManager();
        final RoomManager roomManager = ginjector.getRoomManager();
        final ISecurityLabelManager securityLabelManager = ginjector
                .getSecurityLabelManager();
        final XmppSecurityLabelExtensionFactory securityLabelExtensionFactory = ginjector
                .getXmppSecurityLabelExtensionFactory();
        final ISecurityLabelService securityLabelService = ginjector.getSecurityLabelService();
        final AvatarProviderRegistry registry = ginjector.getAvatarProviderRegistry();

        new HablarCore(hablar);
        new HablarSecureChat(hablar, config.chatConfig, roster, chatManager,
                ginjector.getStateManager(), securityLabelManager,
                securityLabelExtensionFactory, securityLabelService, ginjector.getAvatarProviderRegistry());
        new HablarSecureRooms(hablar, config.roomsConfig, session, roster,
                roomManager, ginjector.getMUCChatStateManager(),
                securityLabelManager, securityLabelExtensionFactory, securityLabelService, registry);
        new HablarSecureGroupChat(hablar, config.roomsConfig, session, roster,
                chatManager, roomManager, securityLabelManager,
                securityLabelExtensionFactory,
                ginjector.getAvatarProviderRegistry());

        new HablarDock(hablar, config.dockConfig);

        new HablarUser(hablar, session, ginjector.getPresenceManager(),
                ginjector.getPrivateStorageManager());

        RosterPage rosterPage = null;
        HablarRoster hablarRoster = null;
        if (config.hasRoster) {
            hablarRoster = new HablarRoster(hablar, config.rosterConfig,
                    session, roster, chatManager,
                    ginjector.getSubscriptionHandler());
            rosterPage = hablarRoster.getRosterPage();
        }

        if (config.hasVCard) {
            new HablarVCard(hablar, config.vcardConfig, session, roster,
                    ginjector.getVCardManager());
        }

        if (config.hasRoster) {
            // new HablarOpenChat(hablar, session, roster, chatManager);
            new HablarEditBuddy(hablar, roster);
            new HablarUserGroups(rosterPage, hablar, roster);
            new HablarGroup(hablar, session, roster, ginjector.getAvatarProviderRegistry());
            hablarRoster.addLowPriorityActions();
        }

        if (config.hasSearch) {
            new HablarSearch(hablar, config.searchConfig, session, roster,
                    chatManager, ginjector.getSearchManager(), ginjector.getAvatarProviderRegistry());
        }

        if (config.hasSignals) {
            new HablarSignals(hablar, session,
                    ginjector.getPrivateStorageManager());
        }

        if (config.hasSound) {
            new HablarSoundSignals(hablar);
        }

        if (config.hasCopyToClipboard) {
            new HablarClipboard(hablar);
        }

        final ChatChangedHandler chatChangedHandler = new ChatChangedHandler() {

            @Override
            public void onChatChanged(final ChatChangedEvent event) {
                if (event.isAdded()) {
                    final Chat chat = event.getChat();

                    final Object securityLabelObject = chat.getProperties()
                            .getData("security_label");

                    if ((securityLabelObject != null)
                            && (securityLabelObject instanceof IXmppSecurityLabelExtension)) {
                        final IXmppSecurityLabelExtension xmppSecurityLabel = (IXmppSecurityLabelExtension) securityLabelObject;

                        chat.addBeforeSendMessageHandler(new MessageHandler() {

                            @Override
                            public void onMessage(final MessageEvent event) {
                                XmppPacketUtil.extendPacket(event.getMessage(),
                                        xmppSecurityLabel);
                            }
                        });
                    }
                }
            }
        };

        chatManager.addChatChangedHandler(chatChangedHandler);
        roomManager.addChatChangedHandler(chatChangedHandler);
    }
}
