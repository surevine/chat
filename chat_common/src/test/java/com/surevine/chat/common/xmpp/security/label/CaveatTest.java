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

package com.surevine.chat.common.xmpp.security.label;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link Caveat} class.
 */
public class CaveatTest {

    /**
     * Test caveat name.
     */
    private static final String TEST_CAVEAT_NAME = "TEST CAVEAT";

    /**
     * The class under test.
     */
    private Caveat caveat;

    /**
     * Set up the test fixtures.
     */
    @Before
    public void setUp() {
        caveat = new Caveat(TEST_CAVEAT_NAME);
    }

    /**
     * Tears down the test fixtures.
     */
    @After
    public void tearDown() {
        caveat = null;
    }

    /**
     * Tests that a caveat is correctly constructed and the name is correctly retrieved.
     */
    @Test
    public void testConstructorAndGetter() {
        assertEquals("Caveat name not initialised correctly", TEST_CAVEAT_NAME, caveat.getName());
    }

}
