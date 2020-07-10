/*
 * Copyright @ 2018 - present 8x8, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jitsi.videobridge.rest.root.colibri.mucclient;

import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.jitsi.videobridge.rest.MockBinder;
import org.jitsi.videobridge.util.ClientConnectionProvider;
import org.jitsi.videobridge.xmpp.ClientConnectionImpl;
import org.jitsi.xmpp.util.IQUtils;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MucClientHandleIqTest extends JerseyTest
{
    protected ClientConnectionProvider clientConnectionProvider;
    protected ClientConnectionImpl clientConnection;
    protected static final String BASE_URL = "/colibri/muc-client";

    @Override
    protected Application configure()
    {
        clientConnectionProvider = mock(ClientConnectionProvider.class);
        clientConnection = mock(ClientConnectionImpl.class);
        when(clientConnectionProvider.get()).thenReturn(clientConnection);

        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig() {
            {
                register(new MockBinder<>(clientConnectionProvider, ClientConnectionProvider.class));
                register(MucClient.class);
            }
        };
    }

    @Test
    public void testHandleIq() throws IOException {
        ClientConnectionImpl cc = new ClientConnectionImpl();
        String iqContent = new String(Files.readAllBytes(Paths.get("./jireconIQ.xml")), StandardCharsets.UTF_8);;
        IQUtils.parse(iqContent, null)
        cc.handleIq(null);
    }

}