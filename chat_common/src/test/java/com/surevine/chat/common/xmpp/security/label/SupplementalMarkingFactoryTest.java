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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * Test case for the {@link OpenGroupMarkingFactory} class.
 */
public class SupplementalMarkingFactoryTest {

	@Mock
	private OpenGroupMarkingFactory openGroupMarkingFactory;
	
    /**
     * The class under test.
     */
    private SupplementalMarkingFactory supplementalMarkingFactory;

    /**
     * Set up the test environment.
     */
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
        supplementalMarkingFactory = new SupplementalMarkingFactory(openGroupMarkingFactory);
    }

    /**
     * Clean up after ourselves.
     */
    @After
    public void tearDown() {
        supplementalMarkingFactory = null;
    }

    /**
     * Test the createSupplementalMarking method.
     */
    @Test
    public void testCreateSupplementalMarkingOpenGroup() {
        IXmlElement element = Mockito.mock(IXmlElement.class);
        OpenGroupMarking supplementalMarking = Mockito.mock(OpenGroupMarking.class);
        when(
                openGroupMarkingFactory.createOpenGroupMarking(
                        element)).thenReturn(supplementalMarking);
        when(element.getAttribute("type")).thenReturn("openGroup");

        SupplementalMarking result = supplementalMarkingFactory.createSupplementalMarking(element);

        assertEquals("Supplemental Marking is incorrect!", supplementalMarking, result);
        verify(openGroupMarkingFactory).createOpenGroupMarking(
                element);
    }

}
