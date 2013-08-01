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
package com.surevine.chat.view.client.xmpp.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.calclab.emite.core.client.packet.IPacket;
import com.calclab.emite.core.client.packet.MatcherFactory;
import com.calclab.emite.core.client.packet.NoPacket;
import com.calclab.emite.core.client.packet.PacketMatcher;
import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * Provides a wrapper around the Emite IPacket to allow it to be used as an
 * IXmlElement.
 */
public class EmiteXmlElement implements IXmlElement {

    /**
     * The packet.
     */
    private final IPacket packet;

    /**
     * Constructor.
     * 
     * @param packet
     *            The <code>IPacket</code>.
     */
    public EmiteXmlElement(final IPacket packet) {
        this.packet = packet;
    }

    /**
     * {@inheritDoc}.
     */
    public EmiteXmlElement addChild(final String elementName) {
        return this.addChild(elementName, null);
    }

    /**
     * {@inheritDoc}.
     */
    public EmiteXmlElement addChild(final String elementName,
            final String namespace) {
        return new EmiteXmlElement(packet.addChild(elementName, namespace));
    }

    /**
     * {@inheritDoc}.
     */
    public String getAttribute(final String name) {
        return packet.getAttribute(name);
    }

    /**
     * {@inheritDoc}.
     */
    public Map<String, String> getAttributes() {
        return packet.getAttributes();
    }

    /**
     * {@inheritDoc}.
     */
    public List<? extends EmiteXmlElement> getChildren() {
        final List<EmiteXmlElement> result = new ArrayList<EmiteXmlElement>();

        final List<? extends IPacket> children = packet.getChildren();

        for (final IPacket child : children) {
            result.add(new EmiteXmlElement(child));
        }

        return result;
    }

    /**
     * {@inheritDoc}.
     */
    public String getText() {
        return packet.getText();
    }

    /**
     * {@inheritDoc}.
     */
    public EmiteXmlElement setAttribute(final String name, final String value) {
        packet.setAttribute(name, value);

        return this;
    }

    /**
     * {@inheritDoc}.
     */
    public EmiteXmlElement setText(final String text) {
        packet.setText(text);

        return this;
    }

    /**
     * {@inheritDoc}.
     */
    public List<? extends EmiteXmlElement> getChildren(final String elementName) {
        final PacketMatcher matcher = MatcherFactory.byName(elementName);

        final List<EmiteXmlElement> result = new ArrayList<EmiteXmlElement>();

        final List<? extends IPacket> children = packet.getChildren(matcher);

        for (final IPacket child : children) {
            result.add(new EmiteXmlElement(child));
        }

        return result;
    }

    /**
     * {@inheritDoc}.
     */
    public EmiteXmlElement getFirstChild(final String elementName) {
        final IPacket child = packet.getFirstChild(elementName);

        if (child instanceof NoPacket) {
            return null;
        }

        return new EmiteXmlElement(child);
    }

    /**
     * {@inheritDoc}.
     */
    public String getName() {
        return packet.getName();
    }

    /**
     * Get the packet.
     * 
     * @return The <code>IPacket</code> packet.
     */
    public IPacket getPacket() {
        return packet;
    }
}
