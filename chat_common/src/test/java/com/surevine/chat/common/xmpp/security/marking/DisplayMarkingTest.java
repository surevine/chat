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

package com.surevine.chat.common.xmpp.security.marking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * Test for the class {@link DisplayMarking}.
 */
public class DisplayMarkingTest {

    /**
     * Test display marking text.
     */
    private static final String TEST_TEXT = "TEST DISPLAY MARKING";

    /**
     * Test colour.
     */
    private static final String FG_BLACK = "#FFFFFF";

    /**
     * Test colour.
     */
    private static final String BG_WHITE = "#000000";

    /**
     * Test display colour.
     */
    private static final DisplayMarkingColour COLOUR = new DisplayMarkingColour(BG_WHITE, FG_BLACK);

    /**
     * The class under test.
     */
    private DisplayMarking displayMarking;

    /**
     * Sets up each test case prior to execution.
     *
     * @throws Exception
     *             Thrown if an error occurs whilst setting up the test case.
     */
    @Before
    public void setUp() throws Exception {
        displayMarking = new DisplayMarking(TEST_TEXT, COLOUR);
    }

    /**
     * Clean up after ourselves.
     *
     * @throws Exception
     *             Thrown if an error occurs whilst tearing down the test case.
     */
    @After
    public void tearDown() throws Exception {
        displayMarking = null;
    }

    /**
     * Test for the complete constructor of {@link DisplayMarking} which takes the display text and
     * the colour.
     */
    @Test
    public void testDisplayMarkingConstructorComplete() {
        assertEquals("Display Text is not correct", TEST_TEXT, displayMarking.getMarking());
        assertEquals("Foreground Colour is not correct", FG_BLACK, displayMarking.getFgColour());
        assertEquals("Background Colour is not correct", BG_WHITE, displayMarking.getBgColour());
    }

    /**
     * Test the getBgColour() method retrieves the background colour correctly.
     */
    @Test
    public void testGetBgColour() {
        assertEquals("Background Colour is not correct", BG_WHITE, displayMarking.getBgColour());
    }

    /**
     * Test the getDisplayText() method retrieves the display text correctly.
     */
    @Test
    public void testGetDisplayText() {
        assertEquals("Display Text is not correct", TEST_TEXT, displayMarking.getMarking());
    }

    /**
     * Test the getFgColour() method retrieves the foreground colour correctly.
     */
    @Test
    public void testGetFgColour() {
        assertEquals("Foreground Colour is not correct", FG_BLACK, displayMarking.getFgColour());
    }

    /**
     * Test the equals method.
     */
    @Test
    public void testEquals() {
        // These are all the same
        DisplayMarking a = new DisplayMarking(TEST_TEXT, COLOUR);
        DisplayMarking b = new DisplayMarking(TEST_TEXT, COLOUR);
        DisplayMarking c = new DisplayMarking(TEST_TEXT, COLOUR);

        // This has a different text
        DisplayMarking d = new DisplayMarking(TEST_TEXT + " DIFFERENT", COLOUR);

        // Reflexive
        assertTrue("Reflexive: a equals a is not true", a.equals(a));

        // Symmetric
        assertTrue("Symmetric: a equals b is not true", a.equals(b));
        assertTrue("Symmetric: b equals a is not true", b.equals(a));

        // Transitive
        assertTrue("Transitive: a equals b is not true", a.equals(b));
        assertTrue("Transitive: b equals c is not true", a.equals(c));
        assertTrue("Transitive: a equals c is not true", a.equals(c));

        // Consistent
        assertTrue("Consistent #1: a equals b is not true", a.equals(b));
        assertTrue("Consistent #2: a equals b is not true", a.equals(b));
        assertTrue("Consistent #3: a equals b is not true", a.equals(b));

        // Null returns false
        assertFalse("a equals null is not false", a.equals(null));

        // Negatives
        assertFalse("Text not taken into account", a.equals(d));
    }

    /**
     * Test the addToXmlElement method with text and colours.
     */
    @Test
    public void testAddToXmlElement() {
        IXmlElement parentElement = Mockito.mock(IXmlElement.class);
        IXmlElement element = Mockito.mock(IXmlElement.class);

        when(parentElement.addChild("displaymarking")).thenReturn(element);

        displayMarking.addToXmlElement(parentElement);

        verify(parentElement).addChild("displaymarking");
        verify(element).setText(TEST_TEXT);
        verify(element).setAttribute("fgcolor", displayMarking.getFgColour());
        verify(element).setAttribute("bgcolor", displayMarking.getBgColour());
    }

}
