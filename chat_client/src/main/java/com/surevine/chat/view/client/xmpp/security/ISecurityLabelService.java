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
package com.surevine.chat.view.client.xmpp.security;

import java.util.List;

import com.calclab.emite.core.client.packet.IPacket;
import com.surevine.chat.common.xmpp.security.ISecurityLabel;
import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;

/**
 * A service for manipulating security labels inside xmpp packets.
 */
public interface ISecurityLabelService {

    /**
     * Checks if a packet contains a security label and returns it if it does.
     * 
     * @param packet
     *            the packet which may (or may not) contain the security label
     * @return the security label extension, or null if the packet doesn't
     *         contain one.
     */
    IXmppSecurityLabelExtension extractSecurityLabelExtension(IPacket packet);

    /**
     * Retrieves a list of available security labels for the current user.
     * 
     * @return the security label list.
     */
    List<ISecurityLabel> getSecurityLabelList();

}
