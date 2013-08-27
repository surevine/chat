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

package com.surevine.chat.common.xmpp.security.label;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.surevine.chat.common.xmpp.security.ISecurityLabel;
import com.surevine.chat.common.xmpp.security.ISecurityLabelXmlReader;
import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * This class is responsible for creating {@link SecurityLabel} objects.
 */
public class SecurityLabelFactory implements ISecurityLabelXmlReader {

    /**
     * The protective marking attribute name.
     */
    private static final String XMLNS = "xmlns";

    /**
     * The factory to create prefixes.
     */
    private PrefixFactory prefixFactory;

    /**
     * The factory to create protective markings.
     */
    private ProtectiveMarkingFactory protectiveMarkingFactory;

    /**
     * The factory to create caveats.
     */
    private CaveatFactory caveatFactory;

    /**
     * The factory to create supplemental markings.
     */
    private SupplementalMarkingFactory supplementalMarkingFactory;

    /**
     * Constructs a new <code>SecurityLabelFactory</code> object.
     *
     * @param protectiveMarkingFactory
     *            The protective marking factory.
     * @param caveatFactory
     *            The caveat factory.
     * @param supplementalMarkingFactory
     *            The supplemental marking factory.
     */
    @Inject
    public SecurityLabelFactory(final PrefixFactory prefixFactory,
    		final ProtectiveMarkingFactory protectiveMarkingFactory,
            final CaveatFactory caveatFactory,
            final SupplementalMarkingFactory supplementalMarkingFactory) {
        this.prefixFactory = prefixFactory;
    	this.protectiveMarkingFactory = protectiveMarkingFactory;
        this.caveatFactory = caveatFactory;
        this.supplementalMarkingFactory = supplementalMarkingFactory;
    }

    /**
     * Creates a new {@link SecurityLabel} from the given XML element.
     *
     * @param xmlElement
     *            The XML element.
     * @return The security label.
     */
    public SecurityLabel createSecurityLabel(final IXmlElement xmlElement) {
        if (!canReadSecurityLabel(xmlElement)) {
            return null;
        }

        IXmlElement innerElement = xmlElement.getFirstChild(ISecurityLabel.SECURITY_LABEL);
        
        // Set the Protective Marking
        Prefix prefix = null;
        IXmlElement prefixElement = innerElement
                .getFirstChild(ISecurityLabel.PREFIX);
        if (prefixElement != null) {
        	prefix = prefixFactory
                    .createPrefix(prefixElement);
        }
        
        // Set the Protective Marking
        ProtectiveMarking protectiveMarking = null;
        IXmlElement protectiveMarkingElement = innerElement
                .getFirstChild(ISecurityLabel.PROTECTIVE_MARKING);
        if (protectiveMarkingElement != null) {
            protectiveMarking = protectiveMarkingFactory
                    .createProtectiveMarking(protectiveMarkingElement);
        }

        // Set the Caveats
        List<Caveat> caveats = new ArrayList<Caveat>();
        List<? extends IXmlElement> caveatElements = innerElement
                .getChildren(ISecurityLabel.CAVEAT);
        for (IXmlElement caveatElement : caveatElements) {
            caveats.add(caveatFactory.createCaveat(caveatElement));
        }

        // Set the Supplemental Markings
        List<SupplementalMarking> supplementalMarkings = new ArrayList<SupplementalMarking>();
        List<? extends IXmlElement> supplementalMarkingElements = innerElement
                .getChildren(ISecurityLabel.SUPPLEMENTAL_MARKING);
        for (IXmlElement supplementalMarkingElement : supplementalMarkingElements) {
            supplementalMarkings.add(supplementalMarkingFactory
                    .createSupplementalMarking(supplementalMarkingElement));
        }

        return new SecurityLabel(prefix, protectiveMarking, caveats, supplementalMarkings);
    }

    /**
     * {@inheritDoc}.
     */
    public boolean canReadSecurityLabel(final IXmlElement xmlElement) {
        IXmlElement labelChild = xmlElement.getFirstChild(ISecurityLabel.SECURITY_LABEL);

        if (labelChild == null) {
            return false;
        }

        return (labelChild.getAttribute(XMLNS).equals(ISecurityLabel.SECURITY_LABEL_NAMESPACE));
    }

}
