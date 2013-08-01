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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link OpenGroupMarking} class.
 */
public class OpenGroupMarkingTest {

    /**
     * The test open group marking.
     */
    private static final String TEST_MARKING_NAME = "TEST OPEN GROUP MARKING";

    /**
     * The class under test.
     */
    private OpenGroupMarking marking;

    /**
     * Initialise the class to be tested.
     */
    @Before
    public void setUp() {
        marking = new OpenGroupMarking(TEST_MARKING_NAME);
    }

    /**
     * Clean up after ourselves.
     */
    @After
    public void tearDown() {
        marking = null;
    }

    /**
     * Tests the constructor correctly initialises the objects and that the name is correctly
     * retrieved.
     */
    @Test
    public void testConstructorAndGetter() {
        assertEquals("Name not initialised correctly", TEST_MARKING_NAME, marking.getName());
        assertEquals("Type not initialised correctly", SupplementalMarkingType.OPEN_GROUP, marking
                .getType());
    }

}
