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

package com.surevine.chat.view.client.rooms.ui;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.Idify;
import com.calclab.hablar.core.client.container.overlay.OverlayContainer;
import com.calclab.hablar.icons.client.AvatarProviderRegistry;
import com.calclab.hablar.rooms.client.room.RoomPresenter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.surevine.chat.common.xmpp.security.ISecurityLabel;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.chat.SecureChatUtility;
import com.surevine.chat.view.client.chat.ui.open.OpenSecureChatWidget;
import com.surevine.chat.view.client.rooms.SecureRoomFactory;
import com.surevine.chat.view.client.rooms.ui.classification.ChangeRoomClassificationPresenter;
import com.surevine.chat.view.client.security.ISecurityLabelManager;

/**
 * 
 */
public class SecureRoomPresenter extends RoomPresenter {

    /**
     * The <code>IXmppSecurityLabelExtension</code> security label.
     */
    private final IXmppSecurityLabelExtension securityLabel;

    /**
     * Creates a new secure room presenter
     * 
     * @param eventBus
     * @param room
     * @param display
     * @param securityLabel
     */
    public SecureRoomPresenter(final XmppSession session, final XmppRoster roster, final Hablar hablar, final RoomManager roomManager,final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory, final Room room,
            final ISecureRoomDisplay display,
            final SecureRoomFactory secureRoomFactory, final AvatarProviderRegistry registry) {
        super(session, roster, hablar.getEventBus(), room, display, registry);

        securityLabel = SecureChatUtility.extractSecurityLabelFromChat(room);

        if (securityLabel == null) {
            display.setLabelVisible(false);
        } else {
            final ISecurityLabel label = securityLabel.getLabel();

            if (label != null) {
                final String id = Idify.uriId(room.getURI().toString()) + "-"
                        + Idify.id(label.getLabel());
                display.setId(id);
            }

            display.setLabelVisible(true);
        }

        display.setChangeClassificationVisible(true);

        // Set the action for the change classification button
        display.changeClassificationButton().addClickHandler(
                new ClickHandler() {

                    public void onClick(final ClickEvent event) {
                        final ChangeRoomClassificationPresenter changeClassification = new ChangeRoomClassificationPresenter(
                                hablar.getEventBus(),
                                session, roomManager, securityLabelManager, xmppSecurityLabelExtensionFactory, new OpenSecureChatWidget(), room, secureRoomFactory);
                        hablar.addPage(changeClassification,
                                OverlayContainer.ROL);

                        changeClassification
                                .requestVisibility(Visibility.focused);
                    }
                });
    }

}
