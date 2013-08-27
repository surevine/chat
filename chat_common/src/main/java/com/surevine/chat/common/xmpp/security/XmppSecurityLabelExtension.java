/*
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

package com.surevine.chat.common.xmpp.security;

import java.util.Collections;
import java.util.List;

import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * An implementation of the XEP-0258 extension to add security labels to XMPP stanzas.
 */
public class XmppSecurityLabelExtension implements IXmppSecurityLabelExtension {

    /**
     * This is the display marking (used if none of the security labels are recognised).
     */
    private IDisplayMarking displayMarking;

    /**
     * This is the primary security label.
     */
    private ISecurityLabel label;

    /**
     * This is a list of security labels which are equivalent to the primary security label.
     */
    private List<? extends ISecurityLabel> equivalentLabels;

    /**
     * Construct an <code>XmppSecurityLabelExtension</code> with a security label and display
     * marking.
     *
     * @param securityLabel
     *            The primary security label.
     * @param displayMarking
     *            The fallback display marking.
     */
    public XmppSecurityLabelExtension(final ISecurityLabel securityLabel,
            final IDisplayMarking displayMarking) {
    	assert securityLabel != null;
    	assert displayMarking != null;
    	
        this.label = securityLabel;
        this.displayMarking = displayMarking;
        this.equivalentLabels = Collections.emptyList();
    }

    /**
     * Construct an <code>XmppSecurityLabelExtension</code> with a security label, display marking
     * and equivalent labels.
     *
     * @param securityLabel
     *            The primary security label.
     * @param displayMarking
     *            The fallback display marking.
     * @param equivalentLabels
     *            The equivalent security labels.
     */
    public XmppSecurityLabelExtension(final ISecurityLabel securityLabel,
            final IDisplayMarking displayMarking, final List<? extends ISecurityLabel> equivalentLabels) {
        this(securityLabel, displayMarking);
        if (equivalentLabels != null && !equivalentLabels.isEmpty()) {
            this.equivalentLabels = equivalentLabels;
        }
    }

    /**
     * {@inheritDoc}.
     */
    public IDisplayMarking getDisplayMarking() {
        return displayMarking;
    }

    /**
     * {@inheritDoc}.
     */
    public List<? extends ISecurityLabel> getEquivalentLabels() {
        return equivalentLabels;
    }

    /**
     * {@inheritDoc}.
     */
    public ISecurityLabel getLabel() {
        return label;
    }

    /**
     * {@inheritDoc}.
     */
    public String getXmlNamespace() {
        return IXmppSecurityLabelExtension.XEP_0258_XML_NAMESPACE;
    }

    /**
     * {@inheritDoc}.
     */
    public String getXmlNodeName() {
        return IXmppSecurityLabelExtension.XEP_0258_XML_ELEMENT;
    }

    /**
     * {@inheritDoc}.
     */
    public void addToXmlElement(final IXmlElement parentElement) {
        IXmlElement element = parentElement.addChild(
                IXmppSecurityLabelExtension.XEP_0258_XML_ELEMENT,
                IXmppSecurityLabelExtension.XEP_0258_XML_NAMESPACE);
        displayMarking.addToXmlElement(element);

        IXmlElement labelElement = element.addChild(IXmppSecurityLabelExtension.XEP_0258_LABEL);
        label.addToXmlElement(labelElement);

        for (ISecurityLabel equivalentLabel : equivalentLabels) {
            IXmlElement equivalentLabelElement = element
                    .addChild(IXmppSecurityLabelExtension.XEP_0258_EQUIVALENT_LABEL);
            equivalentLabel.addToXmlElement(equivalentLabelElement);
        }
    }

    /**
     * {@inheritDoc}.
     */
    public boolean contains(final ISecurityLabel securityLabel) {
        if (label.equals(securityLabel)) {
            return true;
        }
        return equivalentLabels.contains(securityLabel);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = hash * prime + (displayMarking == null ? 0 : displayMarking.hashCode());
        hash = hash * prime + (equivalentLabels == null ? 0 : equivalentLabels.hashCode());
        hash = hash * prime + (label == null ? 0 : label.hashCode());
        return hash;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != getClass()) {
            return false;
        }
        XmppSecurityLabelExtension that = (XmppSecurityLabelExtension) other;
        boolean isEqual = true;
        isEqual &= (this.displayMarking == null ? that.displayMarking == null : this.displayMarking
                .equals(that.displayMarking));
        isEqual &= (this.label == null ? that.label == null : this.label.equals(that.label));
        isEqual &= (this.equivalentLabels == null ? that.equivalentLabels == null
                : this.equivalentLabels.equals(that.equivalentLabels));
        return isEqual;
    }

}
