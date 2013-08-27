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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.surevine.chat.common.xmpp.security.ISecurityLabel;
import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * Test case for the {@link CaveatFactory} class.
 */
public class SecurityLabelFactoryTest {

    /**
     * The prefix factory.
     */
    @Mock
    private PrefixFactory prefixFactory;

    /**
     * The protective marking factory.
     */
    @Mock
    private ProtectiveMarkingFactory protectiveMarkingFactory;

    /**
     * The caveat factory.
     */
    @Mock
    private CaveatFactory caveatFactory;

    /**
     * The supplemental marking factory.
     */
    @Mock
    private SupplementalMarkingFactory supplementalMarkingFactory;

    /**
     * The class under test.
     */
    private SecurityLabelFactory securityLabelFactory;

    /**
     * Set up the test environment.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        securityLabelFactory = new SecurityLabelFactory(prefixFactory, protectiveMarkingFactory, caveatFactory,
                supplementalMarkingFactory);
    }

    /**
     * Clean up after ourselves.
     */
    @After
    public void tearDown() {
        securityLabelFactory = null;
    }

    /**
     * Test the createSecurityLabel method.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testCreateSecurityLabel() {
    	IXmlElement outerElement = Mockito.mock(IXmlElement.class);
    	
        IXmlElement element = Mockito.mock(IXmlElement.class);
    	when(outerElement.getFirstChild("securityLabel")).thenReturn(element);
    	
    	when(element.getAttribute("xmlns")).thenReturn(ISecurityLabel.SECURITY_LABEL_NAMESPACE);

        // +++ Prefix
        IXmlElement prefixElement = Mockito.mock(IXmlElement.class);
        Prefix prefix = Mockito.mock(Prefix.class);

        when(element.getFirstChild("prefix")).thenReturn(prefixElement);

        when(prefixFactory.createPrefix(prefixElement))
                .thenReturn(prefix);
        // ---

        // +++ Protective Marking
        IXmlElement protectiveMarkingElement = Mockito.mock(IXmlElement.class);
        ProtectiveMarking protectiveMarking = Mockito.mock(ProtectiveMarking.class);

        when(element.getFirstChild("protectiveMarking")).thenReturn(protectiveMarkingElement);

        when(protectiveMarkingFactory.createProtectiveMarking(protectiveMarkingElement))
                .thenReturn(protectiveMarking);
        // ---

        // +++ SecurityLabels
        List caveatElements = new ArrayList();
        List<Caveat> caveats = new ArrayList<Caveat>();

        for (int i = 0; i < 5; ++i) {
            IXmlElement caveatElement = Mockito.mock(IXmlElement.class);
            Caveat caveat = Mockito.mock(Caveat.class);

            when(caveatFactory.createCaveat(caveatElement)).thenReturn(caveat);

            caveatElements.add(caveatElement);
            caveats.add(caveat);
        }

        when(element.getChildren("caveat")).thenReturn(caveatElements);
        // ---

        // +++ Supplemental Markings
        List supplementalMarkingElements = new ArrayList();
        List<SupplementalMarking> supplementalMarkings = new ArrayList<SupplementalMarking>();

        for (int i = 0; i < 5; ++i) {
            IXmlElement supplementalMarkingElement = Mockito.mock(IXmlElement.class);
            SupplementalMarking supplementalMarking = Mockito.mock(SupplementalMarking.class);

            when(supplementalMarkingFactory.createSupplementalMarking(supplementalMarkingElement))
                    .thenReturn(supplementalMarking);

            supplementalMarkingElements.add(supplementalMarkingElement);
            supplementalMarkings.add(supplementalMarking);
        }

        when(element.getChildren("supplementalMarking")).thenReturn(supplementalMarkingElements);
        // ---

        // Run the method to test
        SecurityLabel securityLabel = securityLabelFactory.createSecurityLabel(outerElement);

        assertNotNull("SecurityLabel is null", securityLabel);

        verify(element).getFirstChild("protectiveMarking");

        /*
         * Not 100% sure whether we should be using assertEquals or assertSame here. assertEquals
         * makes more sense (we're not worried about it being the same object as long as it
         * represents the same values) but this relies on a correct implementation of equals()
         * within ProtectiveMarking
         */
        if (securityLabel != null) {
            assertEquals("Protective marking not correct", protectiveMarking, securityLabel
                    .getProtectiveMarking());
        }

        verify(element).getChildren("caveat");

        for (Object thisElement : caveatElements) {
            verify(caveatFactory).createCaveat((IXmlElement) thisElement);
        }

        if (securityLabel != null) {
            assertEquals("Caveat list is incorrect", caveats, securityLabel.getCaveats());
        }

        verify(element).getChildren("supplementalMarking");

        for (Object thisElement : supplementalMarkingElements) {
            verify(supplementalMarkingFactory).createSupplementalMarking((IXmlElement) thisElement);
        }

        if (securityLabel != null) {
            assertEquals("Supplemental marking list is incorrect", supplementalMarkings,
                    securityLabel.getSupplementalMarkings());
        }
    }

}
