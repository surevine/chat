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
package com.surevine.chat.view.client.chat.ui;

import com.calclab.emite.im.client.chat.Chat;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.hablar.chat.client.ui.PairChatPresenter;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.Idify;
import com.calclab.hablar.core.client.container.overlay.OverlayContainer;
import com.calclab.hablar.icons.client.AvatarProviderRegistry;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.surevine.chat.common.xmpp.security.ISecurityLabel;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.chat.SecureChatUtility;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatPresenter;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatWidget;
import com.surevine.chat.view.client.security.ISecurityLabelManager;

/**
 * The presenter for a security labelled chat session.
 */
public class SecureChatPresenter extends PairChatPresenter {

    /**
     * The <code>IXmppSecurityLabelExtension</code> security label.
     */
    private final IXmppSecurityLabelExtension securityLabel;

    /**
     * Create a new secure chat widget presenter. Note that this constructor
     * does not add the widget to the {@link Hablar} object - it is up to the
     * calling code to do that.
     * 
     * @param hablar
     *            The <code>Hablar</code> in which this secure chat will be
     *            placed. Provides the event bus and the container for the
     *            "change classification" functionality.
     * @param chat
     *            The <code>Chat</code> object which will provide the model for
     *            this widget.
     * @param display
     *            The <code>SecureChatDisplay</code> object which this presenter
     *            should use for display.
     */
    public SecureChatPresenter(
            final XmppRoster roster,
            final Hablar hablar,
            final ChatManager chatManager,
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory,
            final Chat chat, final SecureChatDisplay display, final AvatarProviderRegistry registry) {

        super(roster, hablar.getEventBus(), chat, display, registry);

        securityLabel = SecureChatUtility.extractSecurityLabelFromChat(chat);

        if (securityLabel == null) {
            display.setLabelVisible(false);
        } else {
            final ISecurityLabel label = securityLabel.getLabel();

            // securityLabel.getLabel() will not return null so is unchecked

            final String id = Idify.uriId(chat.getURI().toString()) + "-"
                    + Idify.id(label.getLabel());
            display.setId(id);

            display.setLabelVisible(true);
        }
        display.setChangeClassificationVisible(true);

        // Set the action for the change classification button
        display.changeClassificationButton().addClickHandler(
                new ClickHandler() {

                    public void onClick(final ClickEvent event) {
                        final OpenSecureChatPresenter openChat = new OpenSecureChatPresenter(
                                hablar.getEventBus(), chatManager,
                                securityLabelManager,
                                xmppSecurityLabelExtensionFactory,
                                new OpenSecureChatWidget(), chat.getURI(),
                                securityLabel);
                        hablar.addPage(openChat, OverlayContainer.ROL);

                        openChat.requestVisibility(Visibility.focused);
                    }
                });
    }

    /**
     * Get the <code>IXmppSecurityLabelExtension</code> security label.
     * 
     * @return The <code>IXmppSecurityLabelExtension</code> security label.
     */
    public IXmppSecurityLabelExtension getSecurityLabel() {
        return securityLabel;
    }

}
