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

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.im.client.chat.ChatSelectionStrategy;
import com.calclab.emite.im.client.chat.events.ChatChangedEvent;
import com.calclab.emite.im.client.chat.events.ChatChangedHandler;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.calclab.emite.xep.mucchatstate.client.MUCChatStateManager;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.container.PageAddedEvent;
import com.calclab.hablar.core.client.container.PageAddedHandler;
import com.calclab.hablar.core.client.container.overlay.OverlayContainer;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.core.client.page.PagePresenter.Visibility;
import com.calclab.hablar.core.client.ui.menu.Action;
import com.calclab.hablar.core.client.ui.menu.SimpleAction;
import com.calclab.hablar.icons.client.AvatarProviderRegistry;
import com.calclab.hablar.icons.client.IconsBundle;
import com.calclab.hablar.rooms.client.HablarRoomManager;
import com.calclab.hablar.rooms.client.HablarRoomsConfig;
import com.calclab.hablar.rooms.client.RoomMessages;
import com.calclab.hablar.rooms.client.invite.InviteToRoomPresenter;
import com.calclab.hablar.rooms.client.open.EditRoomWidget;
import com.calclab.hablar.rooms.client.room.RoomDisplay;
import com.calclab.hablar.rooms.client.room.RoomPage;
import com.calclab.hablar.rooms.client.room.RoomPresenter;
import com.calclab.hablar.rooms.client.room.RoomWidget;
import com.calclab.hablar.rooms.client.state.HablarRoomStateManager;
import com.calclab.hablar.roster.client.page.RosterPage;
import com.google.inject.Inject;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.chat.SecureChatSelectionStrategy;
import com.surevine.chat.view.client.chat.SecureChatUtility;
import com.surevine.chat.view.client.rooms.ui.ISecureRoomDisplay;
import com.surevine.chat.view.client.rooms.ui.SecureRoomPresenter;
import com.surevine.chat.view.client.rooms.ui.SecureRoomWidget;
import com.surevine.chat.view.client.rooms.ui.open.EditSecureRoomWidget;
import com.surevine.chat.view.client.rooms.ui.open.OpenNewSecureRoomPresenter;
import com.surevine.chat.view.client.security.ISecurityLabelManager;
import com.surevine.chat.view.client.security.ui.SecurityLabelPresenter;
import com.surevine.chat.view.client.xmpp.security.ISecurityLabelService;

/**
 * Configures hablar for our group chat/rooms with security labels requirements.
 */
public class HablarSecureRooms {

    private static final String ACTION_ID_INVITE = "HablarRooms-inviteAction";
    private static final String ACTION_ID_OPENROOM = "HablarRooms-openRoom";

    @Inject
    public HablarSecureRooms(
            final Hablar hablar,
            final HablarRoomsConfig config,
            final XmppSession session,
            final XmppRoster roster,
            final RoomManager roomManager,
            final MUCChatStateManager mucChatStateManager,
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory securityLabelExtensionFactory,
            final ISecurityLabelService securityLabelService,
            final AvatarProviderRegistry registry) {
        final SecureRoomFactory secureRoomFactory = new SecureRoomFactory(
                roomManager, config.roomsService);

        final ChatSelectionStrategy strategy = new SecureChatSelectionStrategy(
                securityLabelService,
                SecureChatSelectionStrategy.MESSAGE_TYPE_GROUPCHAT);
        
        roomManager.setChatSelectionStrategy(strategy);
        
        // The factory which will create secure room presenter instances
        final HablarRoomManager.RoomPresenterFactory roomPresenterFactory = new HablarRoomManager.RoomPresenterFactory() {

            @Override
            public RoomPresenter create(final HablarEventBus eventBus,
                    final Room room, final RoomDisplay display) {
                assert display instanceof ISecureRoomDisplay : display
                        .getClass().toString();

                final ISecureRoomDisplay secureDisplay = (ISecureRoomDisplay) display;

                final Object securityLabelObject = room.getProperties()
                        .getData(IXmppSecurityLabelExtension.class.toString());

                IXmppSecurityLabelExtension securityLabel = null;

                SecureRoomPresenter presenter;

                if (securityLabel instanceof IXmppSecurityLabelExtension) {
                    securityLabel = (IXmppSecurityLabelExtension) securityLabelObject;
                }

                presenter = new SecureRoomPresenter(session, roster, hablar,
                        roomManager, securityLabelManager,
                        securityLabelExtensionFactory, room, secureDisplay,
                        secureRoomFactory, registry);
                new SecurityLabelPresenter(secureDisplay.getSecurityLabel(),
                        SecureChatUtility.extractSecurityLabelFromChat(room));

                return presenter;
            }
        };

        // The factory which will create secure room views
        final HablarRoomManager.RoomPageFactory roomPageFactory = new HablarRoomManager.RoomPageFactory() {

            @Override
            public RoomDisplay create(final boolean sendButtonVisible) {
                final RoomWidget roomWidget = new RoomWidget(sendButtonVisible);
                return new SecureRoomWidget(roomWidget);
            }
        };

        new HablarRoomManager(roomManager, hablar, config, roomPageFactory,
                roomPresenterFactory, registry, roster);

        final EditSecureRoomWidget secureRoomOpenNew = new EditSecureRoomWidget(registry);

        final InviteToRoomPresenter invitePage = new InviteToRoomPresenter(
                roster, hablar.getEventBus(), new EditRoomWidget(registry));
        hablar.addPage(invitePage, OverlayContainer.ROL);

        final OpenNewSecureRoomPresenter openNewRoomPage = new OpenNewSecureRoomPresenter(
                hablar.getEventBus(), roster, session, securityLabelManager,
                securityLabelExtensionFactory, secureRoomOpenNew,
                secureRoomFactory);
        hablar.addPage(openNewRoomPage, OverlayContainer.ROL);

        hablar.addPageAddedHandler(new PageAddedHandler() {

            public void onPageAdded(final PageAddedEvent event) {

                if (event.isType(RoomPresenter.TYPE)) {
                    final RoomPresenter roomPage = (RoomPresenter) event
                            .getPage();
                    roomPage.addAction(newInviteAction(invitePage));
                    new HablarRoomStateManager(mucChatStateManager, roomPage);
                } else if (event.isType(RosterPage.TYPE)) {
                    final RosterPage rosterPage = (RosterPage) event.getPage();
                    rosterPage.addAction(newOpenRoomAction(openNewRoomPage));
                }

            }
        }, true);

        // Listen for chat creation events and add the security label events to
        // any newly created chats
        roomManager.addChatChangedHandler(new ChatChangedHandler() {

            @Override
            public void onChatChanged(final ChatChangedEvent event) {
                if (event.isCreated()) {
                    SecureChatUtility
                            .addOutgoingSecurityLabelHandlerToChat(event
                                    .getChat());
                }
            }
        });
    }

    /**
     * Creates the action for the "Invite more people to this room" button on
     * the chat.
     * 
     * @param page
     *            the page to display.
     * @return the {@link Action}
     */
    private Action<RoomPage> newInviteAction(final InviteToRoomPresenter invitePage) {
        return new SimpleAction<RoomPage>(RoomMessages.msg.inviteToThisGroupChat(), ACTION_ID_INVITE, IconsBundle.bundle.buddyAddIcon()) {
                @Override
                public void execute(final RoomPage target) {
                        invitePage.setRoom(target.getRoom());
                        invitePage.requestVisibility(Visibility.focused);
                }
        };
}

    /**
     * Creates the action for the "Open room" button on the roster.
     * 
     * @param page
     *            the page to display.
     * @return the {@link Action}
     */
    private SimpleAction<RosterPage> newOpenRoomAction(final OpenNewSecureRoomPresenter page) {
        final String name = RoomMessages.msg.openNewGroupChatTooltip();
        final SimpleAction<RosterPage> action = new SimpleAction<RosterPage>(name, ACTION_ID_OPENROOM, IconsBundle.bundle.groupChatAddIcon()) {
                @Override
                public void execute(final RosterPage target) {
                        page.requestVisibility(Visibility.focused);
                }
        };
        return action;
    }
}
