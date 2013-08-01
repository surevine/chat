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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * Test case for the {@link XEPDisplayMarkingFactory} class.
 */
public class XEPDisplayMarkingFactoryTest {

    /**
     * Test security label text.
     */
    private static final String TEST_LABEL_TEXT = "TEST SECURITY LABEL";

    /**
     * The class under test.
     */
    private XEPDisplayMarkingFactory displayMarkingFactory;

    /**
     * Set up the test fixtures.
     */
    @Before
    public void setUp() {
        displayMarkingFactory = new XEPDisplayMarkingFactory();
    }

    /**
     * Teardown the test fixtures.
     */
    @After
    public void teardown() {
        displayMarkingFactory = null;
    }

    /**
     * Tests the createDisplayMarking method.
     */
    @Test
    public void testCreateDisplayMarking() {
        // An unknown label type
        ISecurityLabel securityLabel = mock(ISecurityLabel.class);
        IDisplayMarkingRenderer renderer = mock(IDisplayMarkingRenderer.class);

        when(securityLabel.getLabel()).thenReturn(TEST_LABEL_TEXT);

        IDisplayMarking marking = displayMarkingFactory.createDisplayMarking(securityLabel,
                renderer);

        assertThat("marking is null!", marking, is(not(nullValue())));
        assertThat("Display marking text should be the security label label", marking.getMarking(),
                is(TEST_LABEL_TEXT));
    }

    /**
     * Test the createDisplayMarking method which takes an XmlElement.
     */
    @Test
    public void testCreateDisplayMarkingFromXmlElement() {
        final String testFgColour = "black";
        final String testBgColour = "white";
        final String testText = "TEST DISPLAY MARKING";

        IXmlElement element = Mockito.mock(IXmlElement.class);

        when(element.getAttribute("fgcolor")).thenReturn(testFgColour);
        when(element.getAttribute("bgcolor")).thenReturn(testBgColour);
        when(element.getText()).thenReturn(testText);

        IDisplayMarking displayMarking = displayMarkingFactory.createDisplayMarking(element);

        assertThat("displayMarking is null!", displayMarking, is(not(nullValue())));

        verify(element).getAttribute("fgcolor");
        verify(element).getAttribute("bgcolor");
        verify(element).getText();

        assertEquals("FgColor is incorrect", testFgColour, displayMarking.getFgColour());
        assertEquals("BgColor is incorrect", testBgColour, displayMarking.getBgColour());
        assertEquals("Display Text is incorrect", testText, displayMarking.getMarking());
    }

    /**
     * Test the createDisplayMarking method which takes an XmlElement in the case that there are no
     * colours within the display marking.
     */
    @Test
    public void testCreateDisplayMarkingFromXmlElementNoColours() {
        final String testText = "TEST DISPLAY MARKING";

        IXmlElement element = Mockito.mock(IXmlElement.class);

        when(element.getText()).thenReturn(testText);

        IDisplayMarking displayMarking = displayMarkingFactory.createDisplayMarking(element);

        assertThat("displayMarking is null!", displayMarking, is(not(nullValue())));

        verify(element).getAttribute("fgcolor");
        verify(element).getAttribute("bgcolor");
        verify(element).getText();

        assertNull("FgColor should be null", displayMarking.getFgColour());
        assertNull("BgColor should be null", displayMarking.getBgColour());
        assertEquals("Display Text is incorrect", testText, displayMarking.getMarking());
    }

}
