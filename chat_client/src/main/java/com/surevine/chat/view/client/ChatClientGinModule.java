/*
 * Chat Client
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

package com.surevine.chat.view.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.surevine.chat.view.client.security.ISecurityLabelManager;
import com.surevine.chat.view.client.security.SecurityLabelManager;
import com.surevine.chat.view.client.xmpp.security.ISecurityLabelService;
import com.surevine.chat.view.client.xmpp.security.SecurityLabelService;

public class ChatClientGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ISecurityLabelService.class).to(SecurityLabelService.class).in(Singleton.class);
        bind(ISecurityLabelManager.class).to(SecurityLabelManager.class).in(Singleton.class);
    }
/*    
    @Provides
    @Named("Pair")
    @Inject
    protected ChatSelectionStrategy providePairChatSelectionStrategy(final ISecurityLabelService securityLabelService) {
        final ChatSelectionStrategy strategy = new SecureChatSelectionStrategy(
                securityLabelService,
                SecureChatSelectionStrategy.MESSAGE_TYPE_CHAT);
        
        return strategy;
    }
    
    @Provides
    @Named("Room")
    @Inject
    protected ChatSelectionStrategy provideGroupChatSelectionStrategy(final ISecurityLabelService securityLabelService) {
        final ChatSelectionStrategy strategy = new SecureChatSelectionStrategy(
                securityLabelService,
                SecureChatSelectionStrategy.MESSAGE_TYPE_GROUPCHAT);
        
        return strategy;
    }
*/    
}
