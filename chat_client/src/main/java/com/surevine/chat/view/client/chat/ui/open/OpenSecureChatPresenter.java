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

package com.surevine.chat.view.client.chat.ui.open;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.chat.Chat;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.chat.ChatProperties;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.core.client.page.PagePresenter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.security.ISecurityLabelManager;
import com.surevine.chat.view.client.security.labelchooser.SecurityLabelChooserPresenter;

/**
 * The Presenter for the page used to open a new secure chat and to change the
 * classification of an existing chat
 */
public class OpenSecureChatPresenter extends
        PagePresenter<OpenSecureChatDisplay> {

    /**
     * The page type.
     */
    public static final String TYPE = "OpenSecureChat";

    /**
     * The presenter for the security label chooser
     */
    protected SecurityLabelChooserPresenter securityLabelChooserPresenter;

    /**
     * The related chat object. This will be populated if we're using this to
     * change an existing chat's security label (rather than starting a brand
     * new one)
     */
    private Chat relatedChat;

    /**
     * The Chat Manager
     */
    private ChatManager chatManager;

    /**
     * Construct a new {@link OpenSecureChatPresenter}.
     * 
     * @param eventBus
     *            the hablar event bus.
     * @param display
     *            the display which this presenter should use.
     * @param toUri
     *            the uri to which to open this chat.
     * @param room
     *            <code>true</code> if this chat represents a room/group chat,
     *            <code>false</code> if it's a one-to-one chat
     */
    public OpenSecureChatPresenter(
            final HablarEventBus eventBus,
            final ChatManager chatManager,
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory,
            final OpenSecureChatDisplay display, final XmppURI toUri) {
        this(eventBus, chatManager, securityLabelManager,
                xmppSecurityLabelExtensionFactory, display, toUri, null);
    }

    /**
     * Construct a presenter using an existing chat. This is used to change the
     * classification on an existing chat.
     */
    public OpenSecureChatPresenter(
            final HablarEventBus eventBus,
            final ChatManager chatManager,
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory,
            final OpenSecureChatDisplay display, final Chat relatedChat) {
        this(eventBus, chatManager, securityLabelManager,
                xmppSecurityLabelExtensionFactory, display, relatedChat
                        .getProperties().getUri(),
                (IXmppSecurityLabelExtension) relatedChat.getProperties()
                        .getData(IXmppSecurityLabelExtension.class.getName()));

        this.relatedChat = relatedChat;
        this.chatManager = chatManager;
    }

    /**
     * Construct a new {@link OpenSecureChatPresenter}.
     * 
     * @param eventBus
     *            the hablar event bus.
     * @param display
     *            the display which this presenter should use.
     * @param toUri
     *            the uri to which to open this chat.
     * @param room
     *            <code>true</code> if this chat represents a room/group chat,
     *            <code>false</code> if it's a one-to-one chat.
     * @param xmppSecurityLabel
     *            the security label to set the selection screen to by default.
     */
    public OpenSecureChatPresenter(
            final HablarEventBus eventBus,
            final ChatManager chatManager,
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory,
            final OpenSecureChatDisplay display, final XmppURI toUri,
            final IXmppSecurityLabelExtension xmppSecurityLabel) {
        super(TYPE, eventBus, display);

        relatedChat = null;
        this.chatManager = chatManager;

        securityLabelChooserPresenter = new SecurityLabelChooserPresenter(
                securityLabelManager, xmppSecurityLabelExtensionFactory,
                display.getSecurityLabelChooser());

        if (xmppSecurityLabel != null) {
            securityLabelChooserPresenter.setSecurityLabel(xmppSecurityLabel);
        }

        display.getTitleLabel().setText("Choose a Security Label");

        display.getCancel().addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                requestVisibility(Visibility.hidden);
            }
        });

        display.getAccept().addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                OpenSecureChatPresenter.this.openChat(toUri, relatedChat);
            }
        });

    }

    /**
     * Override this to do something different when the accept button is
     * clicked.
     * 
     * @param toUri
     *            the uri to which to open the chat.
     * @param relatedChat
     *            the related chat. Used for changing the classification of a
     *            chat.
     */
    protected void openChat(final XmppURI toUri, final Chat relatedChat) {
        ChatProperties properties;

        if (relatedChat != null) {
            properties = new ChatProperties(toUri, relatedChat.getProperties()
                    .getInitiatorUri(), relatedChat.getProperties().getState(),
                    relatedChat.getProperties());
        } else {
            properties = new ChatProperties(toUri);
        }

        properties.setData(IXmppSecurityLabelExtension.class.getName(),
                securityLabelChooserPresenter.getSecurityLabel());

        chatManager.openChat(properties, true);

        requestVisibility(Visibility.hidden);
    }
}
