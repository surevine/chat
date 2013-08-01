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
package com.surevine.chat.view.client.groupchat;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.calclab.hablar.chat.client.ui.PairChatPage;
import com.calclab.hablar.chat.client.ui.PairChatPresenter;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.Idify;
import com.calclab.hablar.core.client.container.PageAddedEvent;
import com.calclab.hablar.core.client.container.PageAddedHandler;
import com.calclab.hablar.core.client.container.overlay.OverlayContainer;
import com.calclab.hablar.core.client.page.PagePresenter.Visibility;
import com.calclab.hablar.core.client.ui.menu.SimpleAction;
import com.calclab.hablar.groupchat.client.ConvertToGroupChatPresenter;
import com.calclab.hablar.groupchat.client.GroupChatMessages;
import com.calclab.hablar.groupchat.client.HablarGroupChat;
import com.calclab.hablar.icons.client.AvatarProviderRegistry;
import com.calclab.hablar.icons.client.IconsBundle;
import com.calclab.hablar.rooms.client.HablarRoomsConfig;
import com.calclab.hablar.rooms.client.open.EditRoomWidget;
import com.calclab.hablar.roster.client.groups.RosterGroupPresenter;
import com.calclab.hablar.roster.client.page.RosterPage;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.groupchat.ui.OpenSecureGroupChatPresenter;
import com.surevine.chat.view.client.rooms.SecureRoomFactory;
import com.surevine.chat.view.client.rooms.ui.open.EditSecureRoomWidget;
import com.surevine.chat.view.client.security.ISecurityLabelManager;

/**
 * Class to configure the group chat functionality within hablar.
 */
public class HablarSecureGroupChat {

    private final ISecurityLabelManager securityLabelManager;
    private final XmppSecurityLabelExtensionFactory securityLabelExtensionFactory;

    private final Hablar hablar;
    private final XmppSession session;
    private final RoomManager roomManager;
    private final XmppRoster roster;
    private final HablarRoomsConfig config;
    private final AvatarProviderRegistry registry;

    public HablarSecureGroupChat(Hablar hablar, HablarRoomsConfig config,
            XmppSession session, XmppRoster roster, ChatManager chatManager,
            RoomManager roomManager,
            final ISecurityLabelManager securityLabelManager,
            XmppSecurityLabelExtensionFactory securityLabelExtensionFactory,
            final AvatarProviderRegistry registry) {
        this.hablar = hablar;
        this.config = config;
        this.session = session;
        this.roster = roster;
        this.roomManager = roomManager;
        this.registry = registry;

        this.securityLabelManager = securityLabelManager;
        this.securityLabelExtensionFactory = securityLabelExtensionFactory;
        install();
    }

    /**
     * Add the group chat functionlity into hablar.
     * 
     * @param hablar
     *            the {@link Hablar} instance to configure.
     * @param config
     *            the configuration to use.
     */
    private void install() {
        final SecureRoomFactory secureRoomFactory = new SecureRoomFactory(
                roomManager, config.roomsService);

        final EditSecureRoomWidget secureRoomOpenGroup = new EditSecureRoomWidget(registry);
        final EditRoomWidget roomConvertTo = new EditRoomWidget(registry);

        final OpenSecureGroupChatPresenter openGroupPage = new OpenSecureGroupChatPresenter(
                hablar.getEventBus(), roster, session, securityLabelManager,
                securityLabelExtensionFactory, secureRoomOpenGroup,
                secureRoomFactory);
        hablar.addPage(openGroupPage, OverlayContainer.ROL);
        final ConvertToGroupChatPresenter convertToGroupPage = new ConvertToGroupChatPresenter(
                session, roster, roomManager, roomManager, config.roomsService,
                hablar.getEventBus(), roomConvertTo);
        hablar.addPage(convertToGroupPage, OverlayContainer.ROL);

        hablar.addPageAddedHandler(new PageAddedHandler() {
            @Override
            public void onPageAdded(final PageAddedEvent event) {
                if (event.isType(PairChatPresenter.TYPE)) {
                    final PairChatPage chatPage = (PairChatPage) event
                            .getPage();
                    final XmppURI uri = chatPage.getChat().getURI();
                    chatPage.addAction(createConvertToGroupChatAction(uri,
                            convertToGroupPage));
                } else if (event.isType(RosterPage.TYPE)) {
                    final RosterPage roster = (RosterPage) event.getPage();
                    roster.getGroupMenu().addAction(
                            newOpenGroupChatAction(openGroupPage));
                }
            }
        }, true);
    }

    private SimpleAction<PairChatPage> createConvertToGroupChatAction(
            final XmppURI uri,
            final ConvertToGroupChatPresenter convertToGroupPage) {
        final String actionId = HablarGroupChat.ACTION_ID_CONVERT
                + Idify.id(uri);
        return new SimpleAction<PairChatPage>(
                GroupChatMessages.msg.convertToGroupAction(), actionId,
                IconsBundle.bundle.buddyAddIcon()) {
            @Override
            public void execute(final PairChatPage chatPage) {
                convertToGroupPage.setChat(chatPage.getChat());
                convertToGroupPage.requestVisibility(Visibility.focused);
            }
        };
    }

    private SimpleAction<RosterGroupPresenter> newOpenGroupChatAction(
            final OpenSecureGroupChatPresenter openGroupPage) {
        return new SimpleAction<RosterGroupPresenter>(
                GroupChatMessages.msg.openGroupChatAction(),
                HablarGroupChat.ACTION_ID_OPEN,
                IconsBundle.bundle.groupChatIcon()) {
            @Override
            public void execute(final RosterGroupPresenter target) {
                openGroupPage.setGroupName(target.getGroupName());
                openGroupPage.requestVisibility(Visibility.focused);
            }
        };
    }
}