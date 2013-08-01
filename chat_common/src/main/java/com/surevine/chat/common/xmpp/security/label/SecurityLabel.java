/*
 * Chat Common
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
package com.surevine.chat.common.xmpp.security.label;

import java.util.Collections;
import java.util.List;

import com.surevine.chat.common.xmpp.security.ISecurityLabel;
import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * This class represents a security label.
 */
public class SecurityLabel implements ISecurityLabel {

    /**
     * The XML element name.
     */
    private static final String XML_ELEMENT_NAME = "name";

    /**
     * The XML element type.
     */
    private static final String XML_ELEMENT_TYPE = "type";

    /**
     * The prefix
     */
    private Prefix prefix;
    
    /**
     * The protective marking.
     */
    private ProtectiveMarking protectiveMarking;

    /**
     * The list of caveats.
     */
    private List<Caveat> caveats;

    /**
     * The list of supplemental markings.
     */
    private List<SupplementalMarking> supplementalMarkings;

    /**
     * Default constructor for GWT's serialisation.
     */
    @SuppressWarnings("unused")
    private SecurityLabel() {
        // NO-OP
    }

    /**
     * Constructs a <code>SecurityLabel</code> with a protective marking and a list of supplemental
     * markings.
     *
     * @param protectiveMarking
     *            The protective marking.
     * @param supplementalMarkings
     *            The supplemental markings.
     */
    public SecurityLabel(final ProtectiveMarking protectiveMarking,
            final List<SupplementalMarking> supplementalMarkings) {
        this(protectiveMarking, null, supplementalMarkings);
    }

    /**
     * Constructs a <code>SecurityLabel</code> with a protective marking, a list of caveats and a
     * list of supplemental markings.
     *
     * @param protectiveMarking
     *            The protective marking.
     * @param caveats
     *            The caveats.
     * @param supplementalMarkings
     *            The supplemental markings.
     */
    public SecurityLabel(final ProtectiveMarking protectiveMarking, final List<Caveat> caveats,
            final List<SupplementalMarking> supplementalMarkings) {
        this(null, protectiveMarking, caveats, supplementalMarkings);
    }
    
    /**
     * Constructs a <code>SecurityLabel</code> with a prefix, protective marking and a list of supplemental
     * markings.
     *
     * @param prefix
     *            The prefix.
     * @param protectiveMarking
     *            The protective marking.
     * @param supplementalMarkings
     *            The supplemental markings.
     */
    public SecurityLabel(final Prefix prefix, final ProtectiveMarking protectiveMarking,
            final List<SupplementalMarking> supplementalMarkings) {
        this(prefix, protectiveMarking, null, supplementalMarkings);
    }

    /**
     * Constructs a <code>SecurityLabel</code> with a prefix, protective marking, a list of caveats and a
     * list of supplemental markings.
     *
     * @param prefix
     *            The prefix.
     * @param protectiveMarking
     *            The protective marking.
     * @param caveats
     *            The caveats.
     * @param supplementalMarkings
     *            The supplemental markings.
     */
    public SecurityLabel(final Prefix prefix, final ProtectiveMarking protectiveMarking, final List<Caveat> caveats,
            final List<SupplementalMarking> supplementalMarkings) {
        if (protectiveMarking == null) {
            throw new IllegalArgumentException("Protective Marking cannot be null");
        }
        if (supplementalMarkings == null) {
            throw new IllegalArgumentException("Supplemental Markings cannot be null");
        }
        this.prefix = prefix;
        this.protectiveMarking = protectiveMarking;
        this.supplementalMarkings = supplementalMarkings;

        if (caveats == null) {
            this.caveats = Collections.emptyList();
        } else {
            this.caveats = caveats;
        }
    }
    
    /**
     * Gets the caveats for this security label.
     *
     * @return The list of caveats.
     */
    public List<Caveat> getCaveats() {
        return caveats;
    }

    /**
     * Gets the protective marking for this security label.
     *
     * @return The protective marking.
     */
    public ProtectiveMarking getProtectiveMarking() {
        return protectiveMarking;
    }

    /**
     * Gets the list of supplemental markings for this security label.
     *
     * @return The list of supplemental markings.
     */
    public List<SupplementalMarking> getSupplementalMarkings() {
        return supplementalMarkings;
    }

    /**
     * Gets the prefix for this security label.
     *
     * @return The prefix.
     */
    public Prefix getPrefix() {
        return prefix;
    }
    
    /**
     * {@inheritDoc}.
     */
    public String getLabel() {
        StringBuffer label = new StringBuffer();

        if(prefix != null) {
        	label.append(prefix.getName());
        	label.append(" ");
        }
        
        label.append(protectiveMarking.getName());

        for (Caveat caveat : caveats) {
            label.append(" ");
            label.append(caveat.getName());
        }

        boolean isFirstMarking = true;
        
        for (SupplementalMarking marking : supplementalMarkings) {
        	if(isFirstMarking) {
        		label.append(" ");
        		isFirstMarking = false;
        	} else {
        		label.append("/");
        	}
            label.append(marking.getName());
        }

        return label.toString();
    }

    /**
     * {@inheritDoc}.
     */
    public void addToXmlElement(final IXmlElement parentElement) {
        IXmlElement element = parentElement.addChild(ISecurityLabel.SECURITY_LABEL,
                ISecurityLabel.SECURITY_LABEL_NAMESPACE);

        // Set the Protective Marking
        if(prefix != null) {
	        IXmlElement prefixEl = element.addChild(ISecurityLabel.PREFIX);
	        IXmlElement prefixNameEl = prefixEl.addChild(XML_ELEMENT_NAME);
	        prefixNameEl.setText(prefix.getName());
        }

        // Set the Protective Marking
        IXmlElement protectiveMarkingEl = element.addChild(ISecurityLabel.PROTECTIVE_MARKING);
        IXmlElement protectiveMarkingNameEl = protectiveMarkingEl.addChild(XML_ELEMENT_NAME);
        protectiveMarkingNameEl.setText(protectiveMarking.getName());

        // Set the Caveats
        for (Caveat caveat : caveats) {
            IXmlElement caveatEl = element.addChild(ISecurityLabel.CAVEAT);
            IXmlElement caveatNameEl = caveatEl.addChild(XML_ELEMENT_NAME);

            caveatNameEl.setText(caveat.getName());
        }

        // Set the Supplemental Markings
        for (SupplementalMarking marking : supplementalMarkings) {
            IXmlElement markingEl = element.addChild(SUPPLEMENTAL_MARKING);
            markingEl.setAttribute(XML_ELEMENT_TYPE, marking.getType().toString());
            IXmlElement markingNameEl = markingEl.addChild(XML_ELEMENT_NAME);
            markingNameEl.setText(marking.getName());
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = hash * prime + (prefix == null ? 0 : prefix.hashCode());
        hash = hash * prime + (caveats == null ? 0 : caveats.hashCode());
        hash = hash * prime + (protectiveMarking == null ? 0 : protectiveMarking.hashCode());
        hash = hash * prime + (supplementalMarkings == null ? 0 : supplementalMarkings.hashCode());
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
        SecurityLabel that = (SecurityLabel) other;
        boolean isEqual = true;
        isEqual &= (this.prefix == null ? that.prefix == null
                : this.prefix.equals(that.prefix));
        isEqual &= (this.caveats == null ? that.caveats == null : this.caveats.equals(that.caveats));
        isEqual &= (this.protectiveMarking == null ? that.protectiveMarking == null
                : this.protectiveMarking.equals(that.protectiveMarking));
        isEqual &= (this.supplementalMarkings == null ? that.supplementalMarkings == null
                : this.supplementalMarkings.equals(that.supplementalMarkings));
        return isEqual;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("SecurityLabel=[caveats=");
        buffer.append(caveats);
        buffer.append(", protectiveMarking=");
        buffer.append(protectiveMarking);
        buffer.append(", supplementalMarkings=");
        buffer.append(supplementalMarkings);
        buffer.append(", prefix=");
        buffer.append(prefix);
        buffer.append("]");
        return buffer.toString();
    }

}
