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

package com.surevine.chat.view.client.chat;

import com.calclab.emite.im.client.chat.Chat;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.chat.ChatSelectionStrategy;
import com.calclab.emite.im.client.chat.events.ChatChangedEvent;
import com.calclab.emite.im.client.chat.events.ChatChangedHandler;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.xep.chatstate.client.StateManager;
import com.calclab.hablar.chat.client.ChatConfig;
import com.calclab.hablar.chat.client.HablarChatManager;
import com.calclab.hablar.chat.client.state.HablarChatStateManager;
import com.calclab.hablar.chat.client.ui.ChatDisplay;
import com.calclab.hablar.chat.client.ui.ChatWidget;
import com.calclab.hablar.chat.client.ui.PairChatPage;
import com.calclab.hablar.chat.client.ui.PairChatPresenter;
import com.calclab.hablar.chat.client.ui.chatmessageformat.ChatMessageFormatter;
import com.calclab.hablar.chat.client.ui.chatmessageformat.StandardChatMessageFormatReplacements;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.container.PageAddedEvent;
import com.calclab.hablar.core.client.container.PageAddedHandler;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.core.client.ui.emoticons.EmoticonsChatMessageFormatReplacements;
import com.calclab.hablar.icons.client.AvatarProviderRegistry;
import com.google.inject.Inject;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.chat.ui.SecureChatDisplay;
import com.surevine.chat.view.client.chat.ui.SecureChatPresenter;
import com.surevine.chat.view.client.chat.ui.SecureChatWidget;
import com.surevine.chat.view.client.security.ISecurityLabelManager;
import com.surevine.chat.view.client.security.ui.SecurityLabelPresenter;
import com.surevine.chat.view.client.xmpp.security.ISecurityLabelService;

public class HablarSecureChat {

    /**
     * Installs the functionality for the one-to-one chat.
     * 
     * @param hablar
     *            the instance into which to install the functionality.
     * @param config
     *            the configuration
     */
    @Inject
    public HablarSecureChat(final Hablar hablar, final ChatConfig config, final XmppRoster roster, final ChatManager chatManager, final StateManager stateManager, final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory, final ISecurityLabelService securityLabelService, final AvatarProviderRegistry registry) {

        // Set up the message replacements
        ChatMessageFormatter.addReplacements(new StandardChatMessageFormatReplacements());
        
        if(config.enableEmoticons) {
                ChatMessageFormatter.addReplacements(new EmoticonsChatMessageFormatReplacements());
        }
        
        /*
         * Override the default selection strategy
         */
        final ChatSelectionStrategy strategy = new SecureChatSelectionStrategy(
                securityLabelService,
                SecureChatSelectionStrategy.MESSAGE_TYPE_CHAT);

        chatManager.setChatSelectionStrategy(strategy);

        /*
         * Create the chat presenter factory which will generate us secure chat
         * presenters
         */
        final HablarChatManager.PairChatPresenterFactory chatPresenterFactory = new HablarChatManager.PairChatPresenterFactory() {
            @Override
            public PairChatPresenter create(final HablarEventBus eventBus,
                    final Chat chat, final ChatDisplay display) {

                assert display instanceof SecureChatDisplay : display
                        .getClass().toString();

                final SecureChatDisplay secureDisplay = (SecureChatDisplay) display;

                final Object securityLabelObject = chat.getProperties()
                        .getData(IXmppSecurityLabelExtension.class.getName());

                IXmppSecurityLabelExtension securityLabel = null;

                SecureChatPresenter presenter;

                if (securityLabel instanceof IXmppSecurityLabelExtension) {
                    securityLabel = (IXmppSecurityLabelExtension) securityLabelObject;
                }

                presenter = new SecureChatPresenter(roster, hablar, chatManager, securityLabelManager, xmppSecurityLabelExtensionFactory, chat, secureDisplay, registry);
                new SecurityLabelPresenter(secureDisplay.getSecurityLabel(),
                        SecureChatUtility.extractSecurityLabelFromChat(chat));

                return presenter;
            }
        };

        /*
         * Create the chat page factory which will generate us secure chat
         * widgets
         */
        final HablarChatManager.ChatPageFactory chatPageFactory = new HablarChatManager.ChatPageFactory() {

            @Override
            public ChatDisplay create(final boolean sendButtonVisible) {
                final ChatWidget chatWidget = new ChatWidget(sendButtonVisible);
                
                return new SecureChatWidget(chatWidget);
            }
        };
        
        /*
         * Instantiate a chat manager which will manage the various chat
         * sessions and the interfacing between the emite Chats and the hablar
         * ui.
         */
        new HablarChatManager(roster, chatManager, hablar, config, chatPageFactory,
                chatPresenterFactory, registry);

        hablar.addPageAddedHandler(new PageAddedHandler() {
            @Override
            public void onPageAdded(final PageAddedEvent event) {
                if (event.isType(PairChatPresenter.TYPE)) {
                    final PairChatPage page = (PairChatPage) event.getPage();
                    new HablarChatStateManager(stateManager, page);
                }
            }
        }, true);

        // Listen for chat creation events and add the security label events to
        // any newly created chats
        chatManager.addChatChangedHandler(new ChatChangedHandler() {

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

}
