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
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * Test case for the {@link XEPSecurityLabelFactory} class.
 */
public class XEPSecurityLabelFactoryTest {

    /**
     * The class under test.
     */
    private XEPSecurityLabelFactory securityLabelFactory;

    /**
     * The security label readers to test.
     */
    private List<ISecurityLabelXmlReader> securityLabelXmlReaders;

    /**
     * Set up the the test environment.
     */
    @Before
    public void setUp() {
        securityLabelXmlReaders = new ArrayList<ISecurityLabelXmlReader>();
        securityLabelXmlReaders.add(Mockito.mock(ISecurityLabelXmlReader.class));
        securityLabelXmlReaders.add(Mockito.mock(ISecurityLabelXmlReader.class));
        securityLabelXmlReaders.add(Mockito.mock(ISecurityLabelXmlReader.class));
        securityLabelFactory = new XEPSecurityLabelFactory(securityLabelXmlReaders);
    }

    /**
     * Clean up after ourselves.
     */
    @After
    public void tearDown() {
        securityLabelFactory = null;
    }

    /**
     * Test creating a security label from an xml element if the first one matches.
     */
    @Test
    public void testCreateSecurityLabelFirst() {
        IXmlElement element = Mockito.mock(IXmlElement.class);

        ISecurityLabel securityLabel = Mockito.mock(ISecurityLabel.class);

        when(securityLabelXmlReaders.get(0).canReadSecurityLabel(element)).thenReturn(true);
        when(securityLabelXmlReaders.get(0).createSecurityLabel(element)).thenReturn(securityLabel);

        ISecurityLabel result = securityLabelFactory.createSecurityLabel(element);

        assertEquals("Correct security label not returned", securityLabel, result);

        verify(securityLabelXmlReaders.get(0), times(1)).canReadSecurityLabel(element);
        verify(securityLabelXmlReaders.get(1), never()).canReadSecurityLabel(element);
        verify(securityLabelXmlReaders.get(2), never()).canReadSecurityLabel(element);

        verify(securityLabelXmlReaders.get(0), times(1)).createSecurityLabel(element);
        verify(securityLabelXmlReaders.get(1), never()).createSecurityLabel(element);
        verify(securityLabelXmlReaders.get(2), never()).createSecurityLabel(element);
    }

    /**
     * Test creating a security label from an xml element if the third one matches.
     */
    @Test
    public void testCreateSecurityLabelThird() {
        IXmlElement element = Mockito.mock(IXmlElement.class);

        ISecurityLabel securityLabel = Mockito.mock(ISecurityLabel.class);

        when(securityLabelXmlReaders.get(2).canReadSecurityLabel(element)).thenReturn(true);
        when(securityLabelXmlReaders.get(2).createSecurityLabel(element)).thenReturn(securityLabel);

        ISecurityLabel result = securityLabelFactory.createSecurityLabel(element);

        assertEquals("Correct security label not returned", securityLabel, result);

        verify(securityLabelXmlReaders.get(0), times(1)).canReadSecurityLabel(element);
        verify(securityLabelXmlReaders.get(1), times(1)).canReadSecurityLabel(element);
        verify(securityLabelXmlReaders.get(2), times(1)).canReadSecurityLabel(element);

        verify(securityLabelXmlReaders.get(0), never()).createSecurityLabel(element);
        verify(securityLabelXmlReaders.get(1), never()).createSecurityLabel(element);
        verify(securityLabelXmlReaders.get(2), times(1)).createSecurityLabel(element);
    }

    /**
     * Test creating a security label from an xml element when none match.
     */
    @Test
    public void testCreateSecurityLabelNone() {
        IXmlElement element = Mockito.mock(IXmlElement.class);

        ISecurityLabel result = securityLabelFactory.createSecurityLabel(element);

        assertNull("Security label should be null", result);

        verify(securityLabelXmlReaders.get(0), times(1)).canReadSecurityLabel(element);
        verify(securityLabelXmlReaders.get(1), times(1)).canReadSecurityLabel(element);
        verify(securityLabelXmlReaders.get(2), times(1)).canReadSecurityLabel(element);

        verify(securityLabelXmlReaders.get(0), never()).createSecurityLabel(element);
        verify(securityLabelXmlReaders.get(1), never()).createSecurityLabel(element);
        verify(securityLabelXmlReaders.get(2), never()).createSecurityLabel(element);
    }

}
