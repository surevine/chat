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
package com.surevine.chat.selenium;

import com.thoughtworks.selenium.SeleneseTestCase;

/**
 * Selenium tests for testing chat client login.
 */
public class ChatLoginIT extends SeleneseTestCase {

    private static final String PASSWORD = "password";

	private static final String TEST_USERNAME = "testuser1-org1";

    private static final String TEST_PASSWORD = System.getProperty("test.password");
	
	private static final String USERNAME = "username";

	/**
     * Host IP Address for the Selenium Server.
     */
    
    private String chatEC2Host = System.getProperty("chat.host");
    
    private String chatEC2URLBase = "http://" + chatEC2Host + "/chat/";

    /**
     * Browser start command for Selenium.
     */
    private String browserStartCommand = "*firefox";

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setUp() throws Exception {
    	setUp(chatEC2URLBase, browserStartCommand);
    }

    /**
     * Tests the chat page will re-direct to the cas login page.
     *
     * @throws Exception
     *             If an error occurs.
     */
    public void testCasPage() throws Exception {
        selenium.open(chatEC2URLBase);
        selenium.waitForPageToLoad("30000");
        assertTrue(selenium.isTextPresent("Sign in to your account"));
    }

    /**
     * Test that opens the Chat page (that will re-direct to the cas login page), then login and
     * check it was successful (the session is ready).
     *
     * @throws Exception
     *             If an error occurs.
     */
    public void testCasChatLogin() throws Exception {
        selenium.open(chatEC2URLBase);
        selenium.type(USERNAME, TEST_USERNAME);
        selenium.type(PASSWORD, TEST_PASSWORD);
        selenium.click("submit");
        // Wait ten seconds
        wait(10);

        assertTrue(selenium.isTextPresent("Session: ready"));
    }
    
    /**
     * Test to ensure that the login for chat is case insensitive.
     * 
     * Take the normal username and convert to uppercase before submitting.
     */
    public void testCaseInsensitiveCasChatLogin() throws Exception {
        selenium.open(chatEC2URLBase);
        selenium.type(USERNAME, TEST_USERNAME.toUpperCase());
        selenium.type(PASSWORD, TEST_PASSWORD);
        selenium.click("submit");
        // Wait ten seconds
        wait(10);

        assertTrue(selenium.isTextPresent("Session: ready"));   	
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void tearDown() throws Exception {
        selenium.close();
        super.tearDown();
    }

    /**
     * Wait for the passed in seconds.
     *
     * @param seconds
     *            Seconds to wait for.
     * @throws InterruptedException
     */
    private void wait(final int seconds) throws InterruptedException {

        int second = 0;
        while (true) {
            if (second >= seconds) {
                break;
            }
            Thread.sleep(1000);
            second++;
        }
    }
}
