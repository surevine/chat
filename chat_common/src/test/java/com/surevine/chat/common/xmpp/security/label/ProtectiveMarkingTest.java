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
 * Test case for the {@link ProtectiveMarking} class.
 */
public class ProtectiveMarkingTest {

    /**
     * Test protective marking.
     */
    private static final String TEST_MARKING_NAME = "TEST MARKING";

    /**
     * The class under test.
     */
    private ProtectiveMarking caveat;

    /**
     * Set up the test fixtures.
     */
    @Before
    public void setUp() {
        caveat = new ProtectiveMarking(TEST_MARKING_NAME);
    }

    /**
     * Tear down the test fixtures.
     */
    @After
    public void tearDown() {
        caveat = null;
    }

    /**
     * Tests that the class correctly constructs objects and that the name is correctly retrieved.
     */
    @Test
    public void testConstructorAndGetter() {
        assertEquals("ProtectiveMarking name not initialised correctly", TEST_MARKING_NAME, caveat
                .getName());
    }

}
