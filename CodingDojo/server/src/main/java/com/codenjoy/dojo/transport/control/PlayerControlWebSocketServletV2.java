package com.codenjoy.dojo.transport.control;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.transport.ApplicationContextListener;
import com.codenjoy.dojo.transport.auth.AuthenticationService;
import com.codenjoy.dojo.transport.auth.SecureAuthenticationService;
import com.codenjoy.dojo.transport.ws.PlayerSocket;
import com.codenjoy.dojo.transport.ws.PlayerSocketCreator;
import com.codenjoy.dojo.transport.ws.PlayerTransport;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class PlayerControlWebSocketServletV2 extends WebSocketServlet {

    private static final String CONTROL_PLAYER_BEAN_NAME_V2 = "controlPlayerTransportV2";

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        if (isWSV2Enabled()) {
            PlayerSocketCreator creator =
                    new PlayerSocketCreator(getPlayerTransport(),
                            getAuthenticationService(),
                            PlayerSocket.SERVER_SEND_FIRST);
            webSocketServletFactory.setCreator(creator);
        }
    }

    private boolean isWSV2Enabled() {
        return ApplicationContextListener.getContext().containsBean(CONTROL_PLAYER_BEAN_NAME_V2);
    }

    private PlayerTransport getPlayerTransport() {
        return ApplicationContextListener.getContext().getBean("controlPlayerTransportV2", PlayerTransport.class);
    }

    public AuthenticationService getAuthenticationService() {
        return ApplicationContextListener.getContext().getBean(SecureAuthenticationService.class);
    }
}
