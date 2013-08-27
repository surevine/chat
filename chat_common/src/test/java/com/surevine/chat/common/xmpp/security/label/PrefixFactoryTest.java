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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.surevine.chat.common.xmpp.xml.IXmlElement;

/**
 * Test case for the {@link PrefixFactory} class.
 */
public class PrefixFactoryTest {

    /**
     * The class under test.
     */
    private PrefixFactory prefixFactory;

    /**
     * Set up the test environment.
     */
    @Before
    public void setUp() {
        prefixFactory = new PrefixFactory();
    }

    /**
     * Clean up after ourselves.
     */
    @After
    public void tearDown() {
        prefixFactory = null;
    }

    /**
     * Test the createPrefix method.
     */
    @Test
    public void testCreatePrefix() {
        final String testText = "TEST";
        IXmlElement element = Mockito.mock(IXmlElement.class);
        IXmlElement nameElement = Mockito.mock(IXmlElement.class);
        when(element.getFirstChild("name")).thenReturn(nameElement);
        when(nameElement.getText()).thenReturn(testText);

        Prefix prefix = prefixFactory.createPrefix(element);

        assertNotNull("Prefix is null", prefix);
        verify(element).getFirstChild("name");
        verify(nameElement).getText();
        assertEquals("Name is incorrect", testText, prefix.getName());
    }

}
