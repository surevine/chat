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
package com.surevine.chat.common.xmpp.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
 * A test case for the class {@linkXmppSecurityLabelExtension}.
 */
public class XmppSecurityLabelExtensionTest {

    /**
     * The XEP-0258 security label element name.
     */
    private static final String XEP_0258_XML_ELEMENT = "securitylabel";

    /**
     * The XEP-0258 XML namespace.
     */
    private static final String XEP_0258_XML_NAMESPACE = "urn:xmpp:sec-label:0";

    /**
     * The class under test.
     */
    private XmppSecurityLabelExtension extension;

    /**
     * The (mock) display marking.
     */
    @Mock
    private IDisplayMarking marking;

    /**
     * The (mock) security label.
     */
    @Mock
    private ISecurityLabel securityLabel;

    /**
     * The (mock) list of equivalent labels.
     */
    private List<ISecurityLabel> equivalentLabels;

    /**
     * Initialise the mock objects and instantiate the class under test.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        equivalentLabels = new ArrayList<ISecurityLabel>();
        extension = new XmppSecurityLabelExtension(securityLabel, marking, equivalentLabels);
    }

    /**
     * Clean up after ourselves.
     */
    @After
    public void tearDown() {
        extension = null;
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructorWithSecurityLabelAndDisplayMarking() {
        extension = new XmppSecurityLabelExtension(securityLabel, marking);

        assertEquals("Label not initialised correctly", securityLabel, extension.getLabel());
        assertEquals("Display Marking not initialised correctly", marking, extension
                .getDisplayMarking());
        assertTrue("Equivalent Labels should be an empty list",
                (extension.getEquivalentLabels() != null)
                        && (extension.getEquivalentLabels().size() == 0));
    }

    /**
     * Tests the constructor.
     */
    @Test
    public void testConstructorWithSecurityLabelAndDisplayMarkingAndEquivalentLabels() {
        extension = new XmppSecurityLabelExtension(securityLabel, marking, equivalentLabels);

        assertEquals("Label not initialised correctly", securityLabel, extension.getLabel());
        assertEquals("Display Marking not initialised correctly", marking, extension
                .getDisplayMarking());
        assertEquals("Equivalent Labels not initialised correctly", equivalentLabels, extension
                .getEquivalentLabels());
    }

    /**
     * Tests the display marking is retrieved correctly.
     */
    @Test
    public void testGetDisplayMarking() {
        assertEquals("getDisplayMarking not returning the correct object", marking, extension
                .getDisplayMarking());
    }

    /**
     * Tests the equivalent labels are retrieved correctly.
     */
    @Test
    public void testGetEquivalentLabels() {
        assertEquals("getEquivalentLabels not returning the correct object", equivalentLabels,
                extension.getEquivalentLabels());
    }

    /**
     * Tests the label is retrieved correctly.
     */
    @Test
    public void testGetLabel() {
        assertEquals("getLabel not returning the correct object", securityLabel, extension
                .getLabel());
    }

    /**
     * Test the getXmlNodeName method.
     */
    @Test
    public void testGetXmlNodeName() {
        assertEquals("Node name not correct", XEP_0258_XML_ELEMENT, extension.getXmlNodeName());
    }

    /**
     * Test the getXmlNodeName method.
     */
    @Test
    public void testGetXmlNamespace() {
        assertEquals("XML Namespace not correct", XEP_0258_XML_NAMESPACE, extension
                .getXmlNamespace());
    }

    /**
     * Test the addToXmlElement method.
     */
    @Test
    public void testAddToXmlElement() {
        IXmlElement parentElement = Mockito.mock(IXmlElement.class);
        IXmlElement extensionElement = Mockito.mock(IXmlElement.class);
        IXmlElement labelElement = Mockito.mock(IXmlElement.class);

        // Return the extension element when requested
        when(parentElement.addChild(XEP_0258_XML_ELEMENT, XEP_0258_XML_NAMESPACE)).thenReturn(
                extensionElement);

        // Return the label element when requested
        when(extensionElement.addChild("label")).thenReturn(labelElement);

        // Return an equivalent label if requested
        final List<IXmlElement> equivalentLabelElements = new ArrayList<IXmlElement>();

        when(extensionElement.addChild("equivalentlabel")).thenAnswer(new Answer<IXmlElement>() {
            public IXmlElement answer(final InvocationOnMock invocation) throws Throwable {
                IXmlElement equivalentLabelElement = Mockito.mock(IXmlElement.class);
                equivalentLabelElements.add(equivalentLabelElement);

                return equivalentLabelElement;
            }
        });

        // Do the deed
        extension.addToXmlElement(parentElement);

        // Check that the various objects have been added
        verify(parentElement).addChild(XEP_0258_XML_ELEMENT, XEP_0258_XML_NAMESPACE);
        verify(marking).addToXmlElement(extensionElement);
        verify(extensionElement).addChild("label");
        verify(securityLabel).addToXmlElement(labelElement);
    }

}
