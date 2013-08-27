/*
 * Chat Client
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
package com.surevine.chat.view.client.xmpp.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.calclab.emite.core.client.packet.IPacket;
import com.calclab.emite.core.client.packet.NoPacket;
import com.calclab.emite.core.client.packet.PacketMatcher;

/**
 * Test case for the {@link EmiteXmlElement} class.
 */
public class EmiteXmlElementTest {

    /**
     * The class under test.
     */
    private EmiteXmlElement element;

    /**
     * The (mocked) emite IPacket.
     */
    @Mock
    private IPacket packet;

    /**
     * Initialise the class under test.
     *
     * @throws Exception
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        element = new EmiteXmlElement(packet);
    }

    /**
     * Clean up after ourselves.
     *
     * @throws Exception
     */
    @After
    public void tearDown() {
        element = null;
    }

    /**
     * Test the constructor.
     */
    @Test
    public void testConstructor() {
        assertEquals("IPacket not initialised correctly", packet, element.getPacket());
    }

    /**
     * Test the addChild method with just an element name.
     */
    @Test
    public void testAddChildWithName() {
        String testString = "TEST";

        IPacket childPacket = Mockito.mock(IPacket.class);

        when(packet.addChild(testString, null)).thenReturn(childPacket);

        EmiteXmlElement child = element.addChild(testString);

        verify(packet).addChild(testString, null);

        assertEquals("Child not passed back", childPacket, child.getPacket());
    }

    /**
     * Test the addChild method with an element name and namespace.
     */
    @Test
    public void testAddChildWithNameAndNamespace() {
        String testString1 = "TEST1";
        String testString2 = "TEST2";

        IPacket childPacket = Mockito.mock(IPacket.class);

        when(packet.addChild(testString1, testString2)).thenReturn(childPacket);

        EmiteXmlElement child = element.addChild(testString1, testString2);

        verify(packet).addChild(testString1, testString2);

        assertEquals("Child not passed back", childPacket, child.getPacket());
    }

    /**
     * Test the getAttribute method.
     */
    @Test
    public void testGetAttribute() {
        String testString1 = "TEST1";
        String testString2 = "TEST2";

        when(packet.getAttribute(testString1)).thenReturn(testString2);

        String result = element.getAttribute(testString1);

        verify(packet).getAttribute(testString1);

        assertEquals("getAttribute not working", testString2, result);
    }

    /**
     * Test the getAttributes method.
     */
    @Test
    public void testGetAttributes() {
        HashMap<String, String> map = new HashMap<String, String>();

        when(packet.getAttributes()).thenReturn(map);

        Map<String, String> result = element.getAttributes();

        verify(packet).getAttributes();

        assertEquals("getAttributes not working", map, result);
    }

    /**
     * Test the getChildren method.
     *
     * Note: suppressing warnings as there is some oddity regarding the list generic and Mockito.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void testGetChildrenWithTenChildren() {
        List list = new ArrayList<IPacket>();

        // Add some (mock) children
        for (int i = 0; i < 10; ++i) {
            IPacket child = Mockito.mock(IPacket.class);
            list.add(child);
        }

        when(packet.getChildren()).thenReturn(list);

        List<? extends EmiteXmlElement> result = element.getChildren();

        verify(packet).getChildren();

        for (int i = 0; i < 10; ++i) {
            EmiteXmlElement resultElement = result.get(i);

            assertEquals("Element " + i + " not retrieved correctly", list.get(i),
                    resultElement.getPacket());
        }
    }

    /**
     * Test the getChildren method.
     *
     * Note: suppressing warnings as there is some oddity regarding the list generic and Mockito.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void testGetChildrenWithZeroChildren() {
        List list = Collections.emptyList();

        when(packet.getChildren()).thenReturn(list);

        List<? extends EmiteXmlElement> result = element.getChildren();

        verify(packet).getChildren();

        assertTrue("getChildren should return zero elements", result.size() == 0);
    }

    /**
     * Test the getText method.
     */
    @Test
    public void testGetText() {
        String testString = "TEST1";

        when(packet.getText()).thenReturn(testString);

        String result = element.getText();

        verify(packet).getText();

        assertEquals("getText not working correctly", testString, result);
    }

    /**
     * Test the setAttribute method.
     */
    @Test
    public void testSetAttribute() {
        String testString1 = "TEST1";
        String testString2 = "TEST2";

        EmiteXmlElement result = element.setAttribute(testString1, testString2);

        verify(packet).setAttribute(testString1, testString2);

        assertSame("Fluent interface not correct on setAttribute", element, result);
    }

    /**
     * Test the setText method.
     */
    @Test
    public void testSetText() {
        String testString = "TEST";

        EmiteXmlElement result = element.setText(testString);

        verify(packet).setText(testString);

        assertSame("Fluent interface not correct on setText", element, result);
    }

    /**
     * Test the getChildren method with an element name parameter.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void testGetChildrenWithElementNameForTenChildren() {
        String testString = "TEST";

        List list = new ArrayList<IPacket>();

        // Add some (mock) children
        for (int i = 0; i < 10; ++i) {
            IPacket child = Mockito.mock(IPacket.class);
            list.add(child);
        }

        when(packet.getChildren(any(PacketMatcher.class))).thenReturn(list);

        List<? extends EmiteXmlElement> result = element.getChildren(testString);

        // Let's check that the packet matcher was correctly used
        ArgumentCaptor<PacketMatcher> packetMatcherCaptor = ArgumentCaptor
                .forClass(PacketMatcher.class);

        verify(packet).getChildren(packetMatcherCaptor.capture());

        PacketMatcher packetMatcher = packetMatcherCaptor.getValue();

        // Have to black-box test the packet matcher as I can't inspect it
        IPacket matchPacket = Mockito.mock(IPacket.class);

        // We will set it up to return the correct string then a wrong string
        when(matchPacket.getName()).thenReturn(testString).thenReturn("thisisabogusstring");

        assertTrue("PacketMatcher doesn't match " + testString, packetMatcher.matches(matchPacket));
        assertFalse("PacketMatcher matches random strings!", packetMatcher.matches(matchPacket));

        for (int i = 0; i < 10; ++i) {
            EmiteXmlElement resultElement = result.get(i);

            assertEquals("Element " + i + " not retrieved correctly", list.get(i),
                    resultElement.getPacket());
        }
    }

    /**
     * Test the getChildren method with an element name parameter.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void testGetChildrenWithElementNameForZeroChildren() {
        String testString = "TEST";

        List list = Collections.emptyList();

        when(packet.getChildren(any(PacketMatcher.class))).thenReturn(list);

        List<? extends EmiteXmlElement> result = element.getChildren(testString);

        // Let's check that the packet matcher was correctly used
        ArgumentCaptor<PacketMatcher> packetMatcherCaptor = ArgumentCaptor
                .forClass(PacketMatcher.class);

        verify(packet).getChildren(packetMatcherCaptor.capture());

        PacketMatcher packetMatcher = packetMatcherCaptor.getValue();

        // Have to black-box test the packet matcher
        IPacket matchPacket = Mockito.mock(IPacket.class);

        // We will set it up to return the correct string then a wrong string
        when(matchPacket.getName()).thenReturn(testString).thenReturn("thisisabogusstring");

        assertTrue("PacketMatcher doesn't match " + testString, packetMatcher.matches(matchPacket));
        assertFalse("PacketMatcher matches random strings!", packetMatcher.matches(matchPacket));

        assertTrue("getChildren should return zero elements", result.size() == 0);
    }

    /**
     * Test the getFirstChild method where the child exists.
     */
    @Test
    public void testGetFirstChildExists() {
        String testString = "TEST";

        IPacket childPacket = Mockito.mock(IPacket.class);

        when(packet.getFirstChild(testString)).thenReturn(childPacket);

        EmiteXmlElement child = element.getFirstChild(testString);

        verify(packet).getFirstChild(testString);

        assertEquals("Child not passed back", childPacket, child.getPacket());
    }

    /**
     * Test the getFirstChild method where the child doesn't exist.
     */
    @Test
    public void testGetFirstChildDoesntExist() {
        String testString = "TEST";

        when(packet.getFirstChild(testString)).thenReturn(NoPacket.INSTANCE);

        EmiteXmlElement child = element.getFirstChild(testString);

        verify(packet).getFirstChild(testString);

        assertNull("Child should be null", child);
    }

    /**
     * Test the getName method.
     */
    @Test
    public void testGetName() {
        String testString = "TEST";

        when(packet.getName()).thenReturn(testString);

        String name = element.getName();

        verify(packet).getName();

        assertEquals("Child not passed back", testString, name);
    }

}
