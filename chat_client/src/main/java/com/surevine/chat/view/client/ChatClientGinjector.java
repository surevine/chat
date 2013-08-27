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

import com.calclab.emite.browser.client.AutoConfig;
import com.calclab.hablar.client.HablarGinjector;
import com.calclab.hablar.icons.client.AvatarProviderRegistry;
import com.google.gwt.inject.client.GinModules;
import com.surevine.chat.common.xmpp.ChatCommonGinModule;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.view.client.security.ISecurityLabelManager;
import com.surevine.chat.view.client.xmpp.security.ISecurityLabelService;

@GinModules({ChatClientGinModule.class,ChatCommonGinModule.class})
public interface ChatClientGinjector extends HablarGinjector {

    ISecurityLabelManager getSecurityLabelManager();

    XmppSecurityLabelExtensionFactory getXmppSecurityLabelExtensionFactory();

    ISecurityLabelService getSecurityLabelService();
    
    AvatarProviderRegistry getAvatarProviderRegistry();
    
    AutoConfig getAutoConfig();
}
