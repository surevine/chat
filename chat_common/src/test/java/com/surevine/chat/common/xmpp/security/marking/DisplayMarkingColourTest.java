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
package com.surevine.chat.common.xmpp.security.marking;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link DisplayMarkingColour} class.
 */
public class DisplayMarkingColourTest {

    /**
     * Red.
     */
    private static final String RED = "#FF0000";

    /**
     * Blue.
     */
    private static final String BLUE = "#0000FF";

    /**
     * Black.
     */
    private static final String BLACK = "#FFFFFF";

    /**
     * White.
     */
    private static final String WHITE = "#000000";

    /**
     * Default display colour under test.
     */
    private DisplayMarkingColour defaultColour;

    /**
     * Custom display marking colour under test.
     */
    private DisplayMarkingColour colour;

    /**
     * Sets up the test fixtures.
     */
    @Before
    public void setup() {
        defaultColour = new DisplayMarkingColour();
        colour = new DisplayMarkingColour(RED, BLUE);
    }

    /**
     * Tears down the test fixtures.
     */
    @After
    public void teardown() {
        defaultColour = null;
        colour = null;
    }

    /**
     * Tests that the default constructor sets correct default colour values.
     */
    @Test
    public void testDefaultConstructor() {
        assertEquals("Default background colour incorrect", WHITE, defaultColour
                .getBackgroundColour());
        assertEquals("Default foreground colour incorrect", BLACK, defaultColour
                .getForegroundColour());
    }

    /**
     * Tests that the full constructor correctly sets the colours.
     */
    @Test
    public void testConstructor() {
        assertEquals("Background colour incorrect", RED, colour.getBackgroundColour());
        assertEquals("Foreground colour incorrect", BLUE, colour.getForegroundColour());
    }

    /**
     * Tests that the foreground colour is correctly returned.
     */
    @Test
    public void testGetForeground() {
        assertEquals("Foreground colour incorrect", BLUE, colour.getForegroundColour());
    }

    /**
     * Test that the background colour is correctly returned.
     */
    @Test
    public void testGetBackground() {
        assertEquals("Background colour incorrect", RED, colour.getBackgroundColour());
    }

}
