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

package com.surevine.chat.view.client.rooms.ui.open;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.rooms.SecureRoomFactory;
import com.surevine.chat.view.client.security.ISecurityLabelManager;

/**
 * The presenter for the page which opens a new room with a security label
 */
public abstract class OpenSecureRoomPresenter extends EditSecureRoomPresenter {

    /**
     * The factory class which will be used to create room instances
     */
    private final SecureRoomFactory roomFactory;

    private XmppSession session;

    /**
     * Creates a new presenter.
     * 
     * @param type
     *            the hablar page type for this page.
     * @param eventBus
     *            the hablar event bus.
     * @param display
     *            the view for this presenter.
     * @param roomFactory
     *            The factory class which will be used to create room instances.
     */
    public OpenSecureRoomPresenter(final String type,
            final HablarEventBus eventBus, final XmppSession session,
            final ISecurityLabelManager securityLabelManager,
            final XmppSecurityLabelExtensionFactory securityLabelExtensionFactory,
            final EditSecureRoomDisplay display,
            final SecureRoomFactory roomFactory) {
        super(type, eventBus, securityLabelManager, securityLabelExtensionFactory, display);
        this.roomFactory = roomFactory;
        this.session = session;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    protected void onAccept() {
        final XmppURI user = session.getCurrentUserURI();
        final String inviteReason = display.getMessage().getText();

        roomFactory.createSecureRoom(user, display.getRoomName().getValue(),
                getItems(), securityLabelChooserPresenter.getSecurityLabel(),
                inviteReason);
    }

}
