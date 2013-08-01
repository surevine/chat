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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * Test case for the class {@link SecurityLabel}.
 */
public class SecurityLabelTest {
	
    /**
     * Test protective marking.
     */
    private static final String TEST_PREFIX = "TP";

    /**
     * Test protective marking.
     */
    private static final String TEST_PROTECTIVE_MARKING = "PROTECTIVE MARKING";

    /**
     * Test caveats.
     */
    private static final String[] TEST_CAVEATS = {"CAONE1", "CAVTWO2", "CTHREE3" };

    /**
     * Test supplemental markings.
     */
    private static final String[] TEST_SUPP_MARKINGS = {"SO", "TWO", "STH", "FOU", "FI" };

    /**
     * The XML element name.
     */
    private static final String SECURITY_LABEL_ELEMENT_NAME = "securityLabel";

    /**
     * The XML element namespace.
     */
    private static final String SECURITY_LABEL_NAMESPACE =
        "http://www.surevine.com/chat/common/xmpp/security/label/securitylabel.xsd";

    /**
     * The class under test.
     */
    private SecurityLabel securityLabel;

    /**
     * The mocked prefix.
     */
    @Mock
    private Prefix prefix;

    /**
     * The mocked protective marking.
     */
    @Mock
    private ProtectiveMarking protectiveMarking;

    /**
     * The list of (mocked) caveats.
     */
    private List<Caveat> caveats;

    /**
     * The list of (mocked) supplemental markings.
     */
    private List<SupplementalMarking> supplementalMarkings;

    /**
     * Set up the test case - populates a test object full of mock markings and things.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Init prefix
        when(prefix.getName()).thenReturn(TEST_PREFIX);
        
        // Init protective marking
        when(protectiveMarking.getName()).thenReturn(TEST_PROTECTIVE_MARKING);

        // Init caveat list
        caveats = new ArrayList<Caveat>();

        for (String i : TEST_CAVEATS) {
            Caveat j = Mockito.mock(Caveat.class);
            when(j.getName()).thenReturn(i);
            caveats.add(j);
        }

        // Init supplemental marking list
        supplementalMarkings = new ArrayList<SupplementalMarking>();

        for (String i : TEST_SUPP_MARKINGS) {
            SupplementalMarking j = Mockito.mock(SupplementalMarking.class);
            when(j.getName()).thenReturn(i);
            when(j.getType()).thenReturn(SupplementalMarkingType.OPEN_GROUP);
            supplementalMarkings.add(j);
        }

        // Init security label
        securityLabel = new SecurityLabel(prefix, protectiveMarking, caveats, supplementalMarkings);
    }

    /**
     * Tear down the test case.
     */
    @After
    public void tearDown() {
        securityLabel = null;
        protectiveMarking = null;
        caveats = null;
        supplementalMarkings = null;
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructorWithProtectiveMarkingAndSupplementalMarking() {
        securityLabel = new SecurityLabel(protectiveMarking, supplementalMarkings);

        assertEquals("Protective Marking not initialised correctly", protectiveMarking,
                securityLabel.getProtectiveMarking());
        assertEquals("Supplemental Markings not initialised correctly", supplementalMarkings,
                securityLabel.getSupplementalMarkings());
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructorWithProtectiveMarkingAndCaveatsAndSupplementalMarkings() {
        securityLabel = new SecurityLabel(protectiveMarking, caveats, supplementalMarkings);

        assertEquals("Protective Marking not initialised correctly", protectiveMarking,
                securityLabel.getProtectiveMarking());
        assertEquals("Caveats not initialised correctly", caveats, securityLabel.getCaveats());
        assertEquals("Supplemental Markings not initialised correctly", supplementalMarkings,
                securityLabel.getSupplementalMarkings());
    }
    
    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructorWithPrefixAndProtectiveMarkingAndSupplementalMarking() {
        securityLabel = new SecurityLabel(prefix, protectiveMarking, supplementalMarkings);

        assertEquals("Prefix not initialised correctly", prefix,
                securityLabel.getPrefix());
        assertEquals("Protective Marking not initialised correctly", protectiveMarking,
                securityLabel.getProtectiveMarking());
        assertEquals("Supplemental Markings not initialised correctly", supplementalMarkings,
                securityLabel.getSupplementalMarkings());
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructorWithPrefixAndProtectiveMarkingAndCaveatsAndSupplementalMarkings() {
        securityLabel = new SecurityLabel(prefix, protectiveMarking, caveats, supplementalMarkings);

        assertEquals("Prefix not initialised correctly", prefix,
                securityLabel.getPrefix());
        assertEquals("Protective Marking not initialised correctly", protectiveMarking,
                securityLabel.getProtectiveMarking());
        assertEquals("Caveats not initialised correctly", caveats, securityLabel.getCaveats());
        assertEquals("Supplemental Markings not initialised correctly", supplementalMarkings,
                securityLabel.getSupplementalMarkings());
    }
    
    /**
     * Tests the caveats are correctly retrieved.
     */
    @Test
    public void testGetCaveats() {
        assertEquals("Caveats not getting correctly", caveats, securityLabel.getCaveats());
    }

    /**
     * Tests the protective marking is correctly retrieved.
     */
    @Test
    public void testGetPrefix() {
        assertEquals("Prefix not getting correctly", prefix, securityLabel
                .getPrefix());
    }

    /**
     * Tests the protective marking is correctly retrieved.
     */
    @Test
    public void testGetProtectiveMarking() {
        assertEquals("Protective Marking not getting correctly", protectiveMarking, securityLabel
                .getProtectiveMarking());
    }

    /**
     * Tests the supplementat markings are correctly retrieved.
     */
    @Test
    public void testGetSupplementalMarkings() {
        assertEquals("Supplemental Markings not getting correctly", supplementalMarkings,
                securityLabel.getSupplementalMarkings());
    }

    /**
     * Tests the label is correctly retrieved.
     */
    @Test
    public void testGetLabel() {
        String label = securityLabel.getLabel();

        verify(prefix).getName();
        
        verify(protectiveMarking).getName();

        for (Caveat i : caveats) {
            verify(i).getName();
        }

        for (SupplementalMarking i : supplementalMarkings) {
            verify(i).getName();
        }

        final int three = 3;
        final int four = 4;
        String expected = TEST_PREFIX + " " + TEST_PROTECTIVE_MARKING + " " + TEST_CAVEATS[0] + " " + TEST_CAVEATS[1]
                + " " + TEST_CAVEATS[2] + " " + TEST_SUPP_MARKINGS[0] + "/" + TEST_SUPP_MARKINGS[1]
                + "/" + TEST_SUPP_MARKINGS[2] + "/" + TEST_SUPP_MARKINGS[three] + "/"
                + TEST_SUPP_MARKINGS[four];

        assertEquals("Label not created as expected", expected, label);
    }

    /**
     * Test the addToXmlElement method.
     */
    @Test
    public void testAddToXmlElement() {
        IXmlElement parentElement = Mockito.mock(IXmlElement.class);
        IXmlElement element = Mockito.mock(IXmlElement.class);

        when(parentElement.addChild(SECURITY_LABEL_ELEMENT_NAME, SECURITY_LABEL_NAMESPACE))
                .thenReturn(element);

        // +++ Add the prefix mock
        IXmlElement prefixElement = Mockito.mock(IXmlElement.class);
        when(element.addChild("prefix")).thenReturn(prefixElement);

        IXmlElement prefixNameElement = Mockito.mock(IXmlElement.class);
        when(prefixElement.addChild("name")).thenReturn(prefixNameElement);
        // ---

        // +++ Add the protective marking mock
        IXmlElement protectiveMarkingElement = Mockito.mock(IXmlElement.class);
        when(element.addChild("protectiveMarking")).thenReturn(protectiveMarkingElement);

        IXmlElement protectiveMarkingNameElement = Mockito.mock(IXmlElement.class);
        when(protectiveMarkingElement.addChild("name")).thenReturn(protectiveMarkingNameElement);
        // ---

        // +++ Add the caveat mocks
        final List<IXmlElement> caveatElements = new ArrayList<IXmlElement>();
        final List<IXmlElement> caveatNameElements = new ArrayList<IXmlElement>();

        when(element.addChild("caveat")).thenAnswer(new Answer<IXmlElement>() {
            public IXmlElement answer(final InvocationOnMock invocation) throws Throwable {
                IXmlElement caveatElement = Mockito.mock(IXmlElement.class);
                caveatElements.add(caveatElement);

                IXmlElement caveatNameElement = Mockito.mock(IXmlElement.class);
                caveatNameElements.add(caveatNameElement);
                when(caveatElement.addChild("name")).thenReturn(caveatNameElement);

                return caveatElement;
            }
        });
        // ---

        // +++ Add the supplemental marking mocks
        final List<IXmlElement> suppMarkingElements = new ArrayList<IXmlElement>();
        final List<IXmlElement> suppMarkingNameElements = new ArrayList<IXmlElement>();

        when(element.addChild("supplementalMarking")).thenAnswer(new Answer<IXmlElement>() {
            public IXmlElement answer(final InvocationOnMock invocation) throws Throwable {
                IXmlElement suppMarkingElement = Mockito.mock(IXmlElement.class);
                suppMarkingElements.add(suppMarkingElement);

                IXmlElement suppMarkingNameElement = Mockito.mock(IXmlElement.class);
                suppMarkingNameElements.add(suppMarkingNameElement);
                when(suppMarkingElement.addChild("name")).thenReturn(suppMarkingNameElement);

                return suppMarkingElement;
            }
        });
        // ---

        securityLabel.addToXmlElement(parentElement);

        // Ensure that it has interacted with all the various elements
        verify(parentElement).addChild(SECURITY_LABEL_ELEMENT_NAME, SECURITY_LABEL_NAMESPACE);

        // Prefix
        verify(element).addChild("prefix");
        verify(prefixElement).addChild("name");
        verify(prefixNameElement).setText(TEST_PREFIX);

        // Protective marking
        verify(element).addChild("protectiveMarking");
        verify(protectiveMarkingElement).addChild("name");
        verify(protectiveMarkingNameElement).setText(TEST_PROTECTIVE_MARKING);

        // Caveats
        verify(element, times(caveatElements.size())).addChild("caveat");

        if (caveats.size() >= caveatElements.size()) {
            for (int i = 0; i < caveatElements.size(); ++i) {
                verify(caveatElements.get(i)).addChild("name");
                verify(caveatNameElements.get(i)).setText(TEST_CAVEATS[i]);
            }
        }

        // Supplementary Markings
        verify(element, times(suppMarkingElements.size())).addChild("supplementalMarking");

        if (supplementalMarkings.size() >= suppMarkingElements.size()) {
            for (int i = 0; i < suppMarkingElements.size(); ++i) {
                verify(suppMarkingElements.get(i)).addChild("name");
                verify(suppMarkingNameElements.get(i)).setText(TEST_SUPP_MARKINGS[i]);
            }
        }
    }

}
