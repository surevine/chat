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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Test case for the class {@link XmppSecurityLabelExtensionFactory}.
 */
public class XmppSecurityLabelExtensionFactoryTest {

    /**
     * The class under test.
     */
    private XmppSecurityLabelExtensionFactory extensionFactory;

    /**
     * The mocked display marking factory.
     */
    @Mock
    private XEPDisplayMarkingFactory displayMarkingFactory;

    /**
     * The mocked security label factory.
     */
    @Mock
    private XEPSecurityLabelFactory securityLabelFactory;

    /**
     * Set up the test fixtures.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        extensionFactory = new XmppSecurityLabelExtensionFactory(displayMarkingFactory,
                securityLabelFactory);
    }

    /**
     * Teardown the test fixtures.
     */
    @After
    public void teardown() {
        extensionFactory = null;
    }

    /**
     * Test the factory method which creates an extension from an existing security label.
     */
    @Test
    public void testCreateXMPPSecurityLabelExtensionFromSecurityLabel() {
        ISecurityLabel securityLabel = Mockito.mock(ISecurityLabel.class);
        IDisplayMarking displayMarking = Mockito.mock(IDisplayMarking.class);
        IDisplayMarkingRenderer renderer = Mockito.mock(IDisplayMarkingRenderer.class);

        when(displayMarkingFactory.createDisplayMarking(securityLabel, renderer)).thenReturn(
                displayMarking);

        IXmppSecurityLabelExtension extension = extensionFactory.createXMPPSecurityLabelExtension(
                securityLabel, renderer);

        // Check the factory was called correctly
        verify(displayMarkingFactory).createDisplayMarking(securityLabel, renderer);

        // Check the new extension object was initialised correctly
        assertEquals("Display marking was not correct", displayMarking, extension
                .getDisplayMarking());
    }

}
