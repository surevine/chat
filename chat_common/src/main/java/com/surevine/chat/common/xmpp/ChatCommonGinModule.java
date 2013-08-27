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

package com.surevine.chat.common.xmpp;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.surevine.chat.common.xmpp.security.ISecurityLabelXmlReader;
import com.surevine.chat.common.xmpp.security.XEPDisplayMarkingFactory;
import com.surevine.chat.common.xmpp.security.XEPSecurityLabelFactory;
import com.surevine.chat.common.xmpp.security.XmppSecurityLabelExtensionFactory;
import com.surevine.chat.common.xmpp.security.label.SecurityLabelFactory;

public class ChatCommonGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(XEPDisplayMarkingFactory.class).in(Singleton.class);
		bind(XmppSecurityLabelExtensionFactory.class).in(Singleton.class);
	}

	@Provides
	@Inject
	protected XEPSecurityLabelFactory provideXEPSecurityLabelFactory(final SecurityLabelFactory securityLabelFactory) {
        List<ISecurityLabelXmlReader> securityLabelXmlReaders = new ArrayList<ISecurityLabelXmlReader>();

        securityLabelXmlReaders.add(securityLabelFactory);

        return new XEPSecurityLabelFactory(securityLabelXmlReaders);
	}
}
