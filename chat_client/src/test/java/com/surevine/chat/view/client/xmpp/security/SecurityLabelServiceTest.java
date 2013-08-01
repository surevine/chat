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
package com.surevine.chat.view.client.xmpp.security;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;

/**
 * Test case for the {@link SecurityLabelService} class.
 */
public class SecurityLabelServiceTest {

    /**
     * The class under test.
     */
    private SecurityLabelService service;

    /**
     * The (mocked) emite session.
     */
    @Mock
    private XmppSession session;

    /**
     * The (mocked) XmppSecurityLabelExtensionFactory.
     */
    @Mock
    private XmppSecurityLabelExtensionFactory xmppSecurityLabelExtensionFactory;

    /**
     * Set up the various test classes.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new SecurityLabelService(session, xmppSecurityLabelExtensionFactory);
    }

    /**
     * Clean up after ourselves.
     */
    @After
    public void tearDown() {
        service = null;
    }

    /**
     * Test the constructor.
     */
    @Test
    public void testConstructor() {
        service = new SecurityLabelService(session, xmppSecurityLabelExtensionFactory);

        assertEquals("Session not initialised properly", session, service.session);
        assertEquals("Extension factory not initialised properly",
                xmppSecurityLabelExtensionFactory, service.xmppSecurityLabelExtensionFactory);
    }
}
